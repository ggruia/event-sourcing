package com.example.eventsourcing.commons;

import com.example.eventsourcing.aggregate.Aggregate;
import com.example.eventsourcing.event.aggregate.AggregateEvent;
import com.example.eventsourcing.event.aggregate.DeleteAggregateEvent;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ObjectComparator {

    public static <A extends Aggregate, E extends AggregateEvent<A>> boolean areFieldsEqual(A aggregate, E event) {
        if (aggregate == null) {
            return event instanceof DeleteAggregateEvent<?>;
        }

        Set<Field> eventFields = getAllFields(event.getClass(), false);
        for (Field eventField : eventFields) {
            eventField.trySetAccessible();
            try {
                Object eventValue = eventField.get(event);
                Field aggregateField;
                try {
                    aggregateField = aggregate.getClass().getDeclaredField(eventField.getName());
                    aggregateField.trySetAccessible();
                } catch (NoSuchFieldException e) {
                    return false;
                }

                Object aggregateValue = aggregateField.get(aggregate);
                if (!Objects.equals(eventValue, aggregateValue)) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field value", e);
            }
        }

        return true;
    }

    private static Set<Field> getAllFields(Class<?> clazz, boolean checkSuper) {
        Set<Field> fields = new HashSet<>();
        if (clazz == null) {
            return fields;
        }

        do {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        } while(clazz != null && checkSuper);
        return fields;
    }
}