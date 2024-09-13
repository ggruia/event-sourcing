package com.example.eventsourcing.event.aggregate;

import com.example.eventsourcing.aggregate.Aggregate;

public abstract class DeleteAggregateEvent<A extends Aggregate> extends UpdateAggregateEvent<A> {

    @Override
    public A update(A aggregate) {
        return null;
    }
}
