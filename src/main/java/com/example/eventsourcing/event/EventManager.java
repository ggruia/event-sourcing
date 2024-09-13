package com.example.eventsourcing.event;

public interface EventManager {
    /**
     * Handle (apply) an event.
     */
    void handle(Event event);
}