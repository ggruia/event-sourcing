package com.example.eventsourcing.event.aggregate;

import com.example.eventsourcing.aggregate.Aggregate;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class CreateAggregateEvent<A extends Aggregate> extends AggregateEvent<A> {

    @Override
    public final A apply(A aggregate) {
        if (aggregate.getVersion() != null) {
            throw new RuntimeException("Aggregate " + aggregate.getClass().getName() +
                                       " ID " + aggregate.getId() +
                                       " version " + aggregate.getVersion() +
                                       " already exists.");
        }
        return create(aggregate);
    }

    public A create(A aggregate) {
        aggregate.setId(StringUtils.isBlank(getAggregateId()) ? UUID.randomUUID().toString() : getAggregateId());
        aggregate.setCreatedAt(LocalDateTime.now());
        aggregate.setUpdatedAt(aggregate.getCreatedAt());
        aggregate.setVersion(1);
        return copyTo(aggregate);
    }
}
