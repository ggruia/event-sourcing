package com.example.eventsourcing.event.aggregate;

import com.example.eventsourcing.aggregate.Aggregate;

import java.time.LocalDateTime;

public abstract class UpdateAggregateEvent<A extends Aggregate> extends AggregateEvent<A> {

    @Override
    public final A apply(A aggregate) {
        if (aggregate.getVersion() == null) {
            throw new RuntimeException("Aggregate " + aggregate.getClass().getName() +
                                       " ID " + aggregate.getId() +
                                       " not found.");
        }
        return update(aggregate);
    }

    public A update(A aggregate) {
        aggregate.setUpdatedAt(LocalDateTime.now());
        aggregate.incrementVersion();
        return copyTo(aggregate);
    }
}
