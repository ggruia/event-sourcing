package com.example.eventsourcing.customer.event.state;

import com.example.eventsourcing.customer.Customer;
import com.example.eventsourcing.customer.CustomerState;
import com.example.eventsourcing.event.aggregate.UpdateStatefulAggregateEvent;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCanceledOrder extends UpdateStatefulAggregateEvent<Customer, CustomerState> {

    @Override
    public CustomerState getState() {
        return CustomerState.ORDER_CANCELED;
    }

    @Override
    public boolean canTransitionFrom(CustomerState state) {
        return CustomerState.ORDER_PLACED.equals(state);
    }
}
