package com.example.eventsourcing.event;

import java.util.List;

public interface EventLogger {
    /**
     * Log the event.
     */
    void logEvent(Event event);

    /**
     * Return the event log.
     */
    List<Event> fetchEventLog();
}
