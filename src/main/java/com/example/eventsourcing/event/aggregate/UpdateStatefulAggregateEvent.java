package com.example.eventsourcing.event.aggregate;

import com.example.eventsourcing.aggregate.Aggregate;
import com.example.eventsourcing.event.StatefulEvent;
import com.example.eventsourcing.aggregate.StatefulAggregate;

public abstract class UpdateStatefulAggregateEvent<A extends Aggregate & StatefulAggregate<S>, S extends Enum<S>> extends UpdateAggregateEvent<A> implements StatefulEvent<S> {

    @Override
    public A update(A aggregate) {
        if (!canTransitionFrom(aggregate.getState())) {
            throw new IllegalStateException(
                    aggregate.getClass().getName() +
                    " can't transition from " + aggregate.getState().name() +
                    " to " + getState().name());
        }
        return super.update(aggregate);
    }
}
