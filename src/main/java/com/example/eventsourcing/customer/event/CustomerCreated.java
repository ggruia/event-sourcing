package com.example.eventsourcing.customer.event;

import com.example.eventsourcing.customer.Customer;
import com.example.eventsourcing.customer.CustomerState;
import com.example.eventsourcing.event.aggregate.CreateAggregateEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreated extends CreateAggregateEvent<Customer> {
    String name;
    String email;

    final CustomerState state = CustomerState.ORDER_PLACED;
}
