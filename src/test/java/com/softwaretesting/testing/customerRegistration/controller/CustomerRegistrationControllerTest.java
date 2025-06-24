package com.softwaretesting.testing.customerRegistration.controller;

import com.softwaretesting.testing.dao.CustomerRepository;
import com.softwaretesting.testing.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
class CustomerRegistrationControllerTest {

    @Autowired
    private WebTestClient client;
    @Autowired
    private CustomerRepository repository;

    @Test
    @DisplayName("POST /api/v1/customer-registration/ should create a customer and return 200 with correct data")
    @DirtiesContext
        // resets server context after test
    void shouldCreateCustomerSuccessfully() {
        String requestBody = "{\"userName\":\"testUserName\",\"name\":\"testName\",\"phoneNumber\":\"+49000000042\"}";

        client.post()
                .uri("/api/v1/customer-registration/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(requestBody))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.userName").isEqualTo("testUserName")
                .jsonPath("$.name").isEqualTo("testName")
                .jsonPath("$.phoneNumber").isEqualTo("+49000000042");
    }

    @Test
    @DisplayName("POST /api/v1/customer-registration/ should return 400 when given a taken phone number")
    @DirtiesContext
    void testAddCustomerWithTakenNumberReturns400() {
        String requestBody = "{\"userName\":\"testUserName\",\"name\":\"testName\",\"phoneNumber\":\"+490009890\"}";

        client.post()
                .uri("/api/v1/customer-registration/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(requestBody))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("POST /api/v1/customer-registration/ should add customer to repository")
    @DirtiesContext
    void testAddCustomerIsAddedToRepository() {
        String requestBody = "{\"userName\":\"testUserName\",\"name\":\"testName\",\"phoneNumber\":\"+49000000042\"}";

        AtomicLong createdCustomerId = new AtomicLong();
        client.post()
                .uri("/api/v1/customer-registration/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(requestBody))
                .exchange()
                .expectBody()
                .jsonPath("$.id")
                .value(id -> createdCustomerId.set(((Number) id).longValue()));

        Optional<Customer> customer = repository.findById(createdCustomerId.get());

        assertTrue(customer.isPresent());
        assertEquals("testUserName", customer.get().getUserName());
        assertEquals("testName", customer.get().getName());
        assertEquals("+49000000042", customer.get().getPhoneNumber());
    }
}