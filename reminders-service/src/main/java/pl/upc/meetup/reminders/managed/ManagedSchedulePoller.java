package pl.upc.meetup.reminders.managed;

import io.dropwizard.lifecycle.Managed;
import pl.upc.meetup.reminders.data.RemindersDAO;
import pl.upc.meetup.reminders.model.Reminder;

import java.net.URLEncoder;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

public class ManagedSchedulePoller implements Managed {

    private static final Logger LOG = LoggerFactory.getLogger(ManagedSchedulePoller.class);

    private final ScheduledExecutorService executorService;
    private final RemindersDAO remindersDAO;
    private final Client client;
    private final String authId;
    private final String authKey;

    public ManagedSchedulePoller(final RemindersDAO remindersDAO, final Client client, final String authId, final String authKey) {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.remindersDAO = remindersDAO;
        this.client = client;
        this.authId = authId;
        this.authKey = authKey;
    }

    @Override
    public void start() throws Exception {
        executorService.scheduleWithFixedDelay(this::run, 15L, 15L, TimeUnit.SECONDS);
        LOG.info("Started managed resource ManagedSchedulePoller");
    }

    @Override
    public void stop() throws Exception {
        executorService.shutdown();
        LOG.info("Stopped managed resource ManagedSchedulePoller");
    }

    private void run() {
        LOG.info("Getting schedule for reminders");
        remindersDAO.readAll()
                .stream()
                .forEach(reminder -> {
                    try {
                        final Reminder enhancedReminder = enhanceReminderWithDataFromSchedule(reminder);
                        remindersDAO.updateReminder(enhancedReminder);
                    } catch (final Exception ex) {
                        LOG.error("Failed to update reminder with id: " + reminder.getReminderId(), ex);
                    }
                });
    }

    private Reminder enhanceReminderWithDataFromSchedule(final Reminder reminder) throws Exception {
        final Response response = client
                .target("http://api.lgi.io/kraken/v2/schedule/data/PL/broadcasts.json")
                .queryParam("limit", 1)
                .queryParam("fields", "video.title,start,end,channel.ref,channel.name").queryParam("video.title", URLEncoder.encode(reminder.getTitle(), "UTF-8"))
                .request().header("x-auth-id", authId).header("x-auth-key", authKey)
                .buildGet()
                .invoke();

        LOG.info(response.toString());

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {

            JsonNode node = response.readEntity(JsonNode.class);

            final String start = node.withArray("data").elements().next().get("start").asText();
            final String end = node.withArray("data").elements().next().get("end").asText();
            final String channel = node.withArray("data").elements().next().get("channel").get("name").asText();
            final String title = node.withArray("data").elements().next().get("video").get("title").asText();

            final Reminder enhancedReminder = new Reminder(reminder.getReminderId(), reminder.getUserId(), title, start, end, channel);
            LOG.info("Got schedule for reminder, enhanced: {}", enhancedReminder);
            return enhancedReminder;
        } else {
            throw new Exception("Failed to read schedule for reminder with id: " + reminder.getReminderId());
        }

    }
}
