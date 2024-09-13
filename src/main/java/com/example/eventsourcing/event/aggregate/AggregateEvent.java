package com.example.eventsourcing.event.aggregate;

import com.example.eventsourcing.event.Event;
import com.example.eventsourcing.aggregate.Aggregate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.ParameterizedType;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AggregateEvent<A extends Aggregate> extends Event {
    String aggregateId;

    public final AggregateEvent<A> copyFrom(Object source) {
        BeanUtils.copyProperties(source, this);
        return this;
    }

    public final A copyTo(A target) {
        BeanUtils.copyProperties(this, target);
        return target;
    }

    public A apply(A aggregate) {
        return copyTo(aggregate);
    }

    @JsonIgnore
    public final Class<A> getAggregateClass() {
        return (Class<A>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
