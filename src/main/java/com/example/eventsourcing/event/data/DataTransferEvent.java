package com.example.eventsourcing.event.data;

import com.example.eventsourcing.event.Event;
import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public abstract class DataTransferEvent extends Event {
    final String message;
    final String sender;
}