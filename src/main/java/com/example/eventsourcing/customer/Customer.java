package com.example.eventsourcing.customer;

import com.example.eventsourcing.aggregate.Aggregate;
import com.example.eventsourcing.aggregate.StatefulAggregate;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Customer extends Aggregate implements StatefulAggregate<CustomerState> {
    String name;
    String email;
    CustomerState state;

    public CustomerState getState() {
        return state;
    }

    public void setState(CustomerState state) {
        this.state = state;
    }
}