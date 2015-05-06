package pl.upc.meetup.reminders;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import pl.upc.meetup.reminders.conf.RemindersConfiguration;
import pl.upc.meetup.reminders.data.RemindersDAO;
import pl.upc.meetup.reminders.managed.ManagedSchedulePoller;
import pl.upc.meetup.reminders.resource.RemindersResource;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.JerseyClientBuilder;

public class RemindersService extends Application<RemindersConfiguration> {

    public static void main(final String args[]) throws Exception {
        new RemindersService().run(args);
    }

    @Override
    public void run(final RemindersConfiguration configuration,
                    final Environment environment) {

        final RemindersDAO remindersDAO = new RemindersDAO();
        environment.jersey().register(new RemindersResource(remindersDAO));

        final Client client = JerseyClientBuilder.createClient();

        final ManagedSchedulePoller schedulePoller = new ManagedSchedulePoller(remindersDAO, client, configuration.getAuthId(), configuration.getAuthIey());
        environment.lifecycle().manage(schedulePoller);
    }
}
