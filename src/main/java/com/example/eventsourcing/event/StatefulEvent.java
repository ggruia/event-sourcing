package com.example.eventsourcing.event;

public interface StatefulEvent<S extends Enum<S>> {
    S getState();
    boolean canTransitionFrom(S state);
}
