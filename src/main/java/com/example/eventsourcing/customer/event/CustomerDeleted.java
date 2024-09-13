package com.example.eventsourcing.customer.event;

import com.example.eventsourcing.customer.Customer;
import com.example.eventsourcing.event.aggregate.DeleteAggregateEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDeleted extends DeleteAggregateEvent<Customer> {

}
