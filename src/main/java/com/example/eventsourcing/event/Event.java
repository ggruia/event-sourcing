package com.example.eventsourcing.event;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class Event {
    final String eventId;
    final LocalDateTime timestamp;

    protected Event() {
        eventId = java.util.UUID.randomUUID().toString();
        timestamp = LocalDateTime.now();
    }
}