package pl.upc.meetup.reminders.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Reminder {

    @JsonProperty("reminderId")
    private String reminderId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("start")
    private String start;

    @JsonProperty("end")
    private String end;

    @JsonProperty("channel")
    private String channel;

    @JsonCreator
    public Reminder(final String reminderId, final String userId, final String title, final String start, final String end, final String channel) {
        this.reminderId = reminderId;
        this.userId = userId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.channel = channel;
    }

    public String getReminderId() {
        return reminderId;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "reminderId='" + reminderId + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }

    public static Reminder of(final String userId, final String title) {
        return new Reminder(UUID.randomUUID().toString(), userId, title, null, null, null);
    }
}
