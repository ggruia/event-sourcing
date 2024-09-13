package com.example.eventsourcing.aggregate;

public interface StatefulEvent<S extends Enum<S>> {
    S getState();
    boolean canTransitionFrom(S state);
}
