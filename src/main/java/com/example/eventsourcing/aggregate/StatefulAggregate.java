package com.example.eventsourcing.aggregate;

public interface StatefulAggregate<S extends Enum<S>> {
    S getState();
}
