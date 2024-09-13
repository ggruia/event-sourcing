package com.example.eventsourcing.customer;

import com.example.eventsourcing.aggregate.CodeValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerState implements CodeValue {
    ORDER_PLACED("CUSTOMER_ORDER_PLACED"),
    ORDER_RECEIVED("CUSTOMER_ORDER_RECEIVED"),
    ORDER_CONFIRMED("CUSTOMER_ORDER_CONFIRMED"),
    ORDER_CANCELED("CUSTOMER_ORDER_CANCELED");

    final String code;
}
