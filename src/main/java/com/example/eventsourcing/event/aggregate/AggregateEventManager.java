package com.example.eventsourcing.event.aggregate;

import com.example.eventsourcing.aggregate.Aggregate;
import com.example.eventsourcing.event.EventLogger;
import com.example.eventsourcing.event.EventManager;
import com.example.eventsourcing.event.Event;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AggregateEventManager implements EventManager, EventLogger {
    final List<Event> eventLog = new ArrayList<>();
    final JdbcAggregateTemplate jdbcRepository;

    @Override
    public void handle(Event event) {
        if (event instanceof AggregateEvent<?> aggregateEvent) {
            apply(aggregateEvent);
        } else {
            throw new UnsupportedOperationException("Unsupported event type for AggregateEventManager");
        }
    }

    @SneakyThrows
    public <A extends Aggregate> A apply(AggregateEvent<A> aggregateEvent) {
        Class<A> aggregateClass = aggregateEvent.getAggregateClass();
        A aggregate = null;

        if (aggregateEvent instanceof CreateAggregateEvent<A>) {
            aggregate = aggregateClass.getDeclaredConstructor().newInstance();
            return apply(null, aggregate, aggregateEvent);
        } else if (aggregateEvent instanceof UpdateAggregateEvent<A> updateAggregateEvent) {
            aggregate = jdbcRepository.findById(updateAggregateEvent.getAggregateId(), aggregateClass);
            if (aggregate == null) {
                throw new NoSuchElementException("Aggregate with id " + updateAggregateEvent.getAggregateId() + " not found");
            }
            return apply(aggregate.getId(), aggregate, aggregateEvent);
        }
        logEvent(aggregateEvent);
        return aggregate;
    }

    private <A extends Aggregate> A apply(String aggregateId, A aggregate, AggregateEvent<A> aggregateEvent) {
        A newAggregate = aggregateEvent.apply(aggregate);
        aggregateId = aggregate != null ? aggregate.getId() : aggregateId;
        persist(aggregateId, aggregateEvent.getAggregateClass(), newAggregate);
        return aggregate;
    }

    private <A extends Aggregate> void persist(String aggregateId, Class<A> aggregateClass, A aggregate) {
        if (aggregate == null) {
            jdbcRepository.deleteById(aggregateId, aggregateClass);
        } else {
            aggregate.setId(aggregateId);
            aggregate.setUpdatedAt(LocalDateTime.now());

            jdbcRepository.save(aggregate);
        }
    }

    @Override
    public void logEvent(Event event) {
        eventLog.add(event);
    }

    @Override
    public List<Event> fetchEventLog() {
        return new ArrayList<>(eventLog);
    }
}
