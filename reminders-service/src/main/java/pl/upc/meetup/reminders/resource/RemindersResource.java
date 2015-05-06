package pl.upc.meetup.reminders.resource;

import pl.upc.meetup.reminders.data.RemindersDAO;
import pl.upc.meetup.reminders.model.Reminder;
import pl.upc.meetup.reminders.model.ReminderRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

@Path("/users/{userId}")
public class RemindersResource {

    private static final Logger LOG = LoggerFactory.getLogger(RemindersResource.class);

    private final RemindersDAO remindersDAO;

    public RemindersResource(RemindersDAO remindersDAO) {
        this.remindersDAO = remindersDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "getRemindersForUser")
    public Response getRemindersForUser(@PathParam("userId") final String userId) {
        LOG.info("Getting reminders for user: {}", userId);
        final List<Reminder> reminderList = remindersDAO.readRemindersForUser(userId);
        return Response.ok().entity(reminderList).build();
    }

    @POST
    @Path("/reminders/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Timed(name = "createReminderForUser")
    public Response createReminderForUser(@PathParam("userId") final String userId,
                                          final ReminderRequest reminderRequest) throws URISyntaxException {
        LOG.info("Inputting reminderRequest: {}", reminderRequest);
        final Reminder reminder = Reminder.of(userId, reminderRequest.getTitle());
        remindersDAO.createReminder(reminder);
        final String createdResourceLocation = "/users/"+userId+"/reminders/"+reminder.getReminderId();
        return Response.created(URI.create(createdResourceLocation)).build();
    }

    @GET
    @Path("/reminders/{reminderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "getReminder")
    public Response getReminder(@PathParam("userId") final String userId,
                                @PathParam("reminderId") final String reminderId) {
        LOG.info("Getting reminders for user: {} with id: {}", userId, reminderId);
        final Optional<Reminder> reminder = remindersDAO.readReminder(reminderId);
        return Response.ok().entity(reminder.orElseThrow(() -> new WebApplicationException("no reminder found with given id"))).build();
    }
}
