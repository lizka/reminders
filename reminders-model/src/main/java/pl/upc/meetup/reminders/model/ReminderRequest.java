package pl.upc.meetup.reminders.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReminderRequest {
    @JsonProperty("title")
    private String title;

    @JsonCreator
    public ReminderRequest(@JsonProperty("title") final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
