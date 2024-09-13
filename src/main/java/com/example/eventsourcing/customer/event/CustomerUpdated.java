package com.example.eventsourcing.customer.event;

import com.example.eventsourcing.customer.Customer;
import com.example.eventsourcing.event.aggregate.UpdateAggregateEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdated extends UpdateAggregateEvent<Customer> {
    String name;
    String email;
}
