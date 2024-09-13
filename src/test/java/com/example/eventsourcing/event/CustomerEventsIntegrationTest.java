package com.example.eventsourcing.event;

import com.example.eventsourcing.*;
import com.example.eventsourcing.customer.*;
import com.example.eventsourcing.customer.event.CustomerCreated;
import com.example.eventsourcing.customer.event.CustomerDeleted;
import com.example.eventsourcing.customer.event.CustomerUpdated;
import com.example.eventsourcing.customer.event.state.CustomerConfirmedOrder;
import com.example.eventsourcing.customer.event.state.CustomerReceivedOrder;
import com.example.eventsourcing.event.aggregate.AggregateEventManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CustomerEventsIntegrationTest extends PostgreSQLTestContainer {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AggregateEventManager aggregateEventManager;

    @Test
    void customerCreated() {
        var customerCreated = new CustomerCreated()
                .setName("name")
                .setEmail("email");
        aggregateEventManager.handle(customerCreated);

        List<Customer> customers = customerRepository.findAll();
        assertThat(customers).hasSize(1);
        var newCustomer = customers.getFirst();

        assertThat(newCustomer.getId()).isNotBlank();
        assertThat(newCustomer)
                .hasFieldOrPropertyWithValue("version", 1)
                .hasFieldOrPropertyWithValue("name", "name")
                .hasFieldOrPropertyWithValue("email", "email")
                .hasFieldOrPropertyWithValue("state", CustomerState.ORDER_PLACED);
    }

    @Nested
    class CustomerUpdates {
        final String CUSTOMER_ID = "ID1";

        @BeforeEach
        public void init() {
            var customerCreated = new CustomerCreated()
                    .setName("name")
                    .setEmail("email")
                    .setAggregateId(CUSTOMER_ID);
            aggregateEventManager.handle(customerCreated);
        }

        @Test
        void customerUpdated() {
            aggregateEventManager.handle(new CustomerUpdated()
                    .setName("new name")
                    .setEmail("new email")
                    .setAggregateId(CUSTOMER_ID));

            List<Customer> customers = customerRepository.findAll();
            assertThat(customers).hasSize(1);
            var updatedCustomer = customers.getFirst();

            assertThat(updatedCustomer)
                    .hasFieldOrPropertyWithValue("id", CUSTOMER_ID)
                    .hasFieldOrPropertyWithValue("version", 2)
                    .hasFieldOrPropertyWithValue("name", "new name")
                    .hasFieldOrPropertyWithValue("email", "new email");
        }

        @Test
        void customerUpdatedOrderConfirmed() {
            aggregateEventManager.handle(new CustomerConfirmedOrder()
                    .setAggregateId(CUSTOMER_ID));

            List<Customer> customers = customerRepository.findAll();
            assertThat(customers).hasSize(1);
            var updatedCustomer = customers.getFirst();

            assertThat(updatedCustomer)
                    .hasFieldOrPropertyWithValue("id", CUSTOMER_ID)
                    .hasFieldOrPropertyWithValue("version", 2)
                    .hasFieldOrPropertyWithValue("state", CustomerState.ORDER_CONFIRMED);
        }

        @Test
        void customerUpdatedOrderReceivedFailing() {
            assertThatExceptionOfType(IllegalStateException.class)
                    .isThrownBy(() -> aggregateEventManager.handle(new CustomerReceivedOrder()
                            .setAggregateId(CUSTOMER_ID)))
                    .withMessageContaining("can't transition");

            List<Customer> customers = customerRepository.findAll();
            assertThat(customers).hasSize(1);
            var updatedCustomer = customers.getFirst();

            assertThat(updatedCustomer)
                    .hasFieldOrPropertyWithValue("id", CUSTOMER_ID)
                    .hasFieldOrPropertyWithValue("version", 1)
                    .hasFieldOrPropertyWithValue("state", CustomerState.ORDER_PLACED);
        }

        @Test
        void customerDeleted() {
            aggregateEventManager.handle(new CustomerDeleted()
                    .setAggregateId(CUSTOMER_ID));

            List<Customer> customers = customerRepository.findAll();
            assertThat(customers).isEmpty();
        }
    }
}
