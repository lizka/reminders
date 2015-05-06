package pl.upc.meetup.reminders.conf;

import io.dropwizard.Configuration;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author pnajda
 */
public class RemindersConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty("auth-id")
    private String authId;

    @NotEmpty
    @JsonProperty("auth-key")
    private String authIey;

    public String getAuthId() {
        return authId;
    }

    public String getAuthIey() {
        return authIey;
    }
}
