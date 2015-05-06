package pl.upc.meetup.reminders.model;

/**
 *
 * @author pnajda
 */
public class Saying {
    private final long id;
    private final String content;

    public Saying() {
        this.id = 0;
        this.content = null;
    }

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
