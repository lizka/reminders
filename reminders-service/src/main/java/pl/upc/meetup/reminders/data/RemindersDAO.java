package pl.upc.meetup.reminders.data;

import pl.upc.meetup.reminders.model.Reminder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RemindersDAO {

    private Map<String, Reminder> reminders = new ConcurrentHashMap<>();

    public void createReminder(final Reminder reminder) {
        reminders.put(reminder.getReminderId(), reminder);
    }

    public List<Reminder> readRemindersForUser(final String userId) {
        return reminders.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getUserId().equalsIgnoreCase(userId))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    public Optional<Reminder> readReminder(final String reminderId) {
        return reminders.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(reminderId))
                .findFirst()
                .map(Map.Entry::getValue);
    }

    public Collection<Reminder> readAll() {

        return reminders.values();
    }

    public void updateReminder(final Reminder reminder) {
        reminders.put(reminder.getReminderId(), reminder);
    }
}
