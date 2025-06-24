package com.softwaretesting.testing.customerManagement.controller;

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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
class CustomerManagementControllerIT {

    @Autowired
    private WebTestClient client;
    @Autowired
    private CustomerRepository repository;

    @Test
    @DisplayName("GET /api/v1/customers/list should return list of customers each with id, userName, name and phoneNumber")
    void listReturnsAllCustomersWithProperties() {
        client.get()
                .uri("/api/v1/customers/list/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Map.class)
                .value(customers -> {
                    for (Object obj : customers) {
                        Map<String, Object> customer = (Map<String, Object>) obj;

                        assertThat(customer).containsKeys("id", "userName", "name", "phoneNumber");

                        assertThat(customer.get("id")).isInstanceOf(Number.class);
                        assertThat(customer.get("userName")).isInstanceOf(String.class);
                        assertThat(customer.get("name")).isInstanceOf(String.class);
                        assertThat(customer.get("phoneNumber")).isInstanceOf(String.class);
                    }
                });
    }

    @Test
    @DisplayName("GET /api/v1/customers/9890 should return customer with id 9890")
    void customerShouldHaveCorrectProperties() {
        client.get()
                .uri("/api/v1/customers/9890")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Map.class)
                .value(response -> {
                    assertThat(response).containsOnlyKeys("id", "userName", "name", "phoneNumber");

                    assertEquals(9890, response.get("id"));
                    assertEquals("f9890", response.get("userName"));
                    assertEquals("l9890", response.get("name"));
                    assertEquals("+490009890", response.get("phoneNumber"));
                });
    }

    @Test
    @DisplayName("GET /api/v1/customers/99999 should return 404 with 'Not Found' error")
    void shouldReturn404ForNonExistentCustomer() {
        client.get()
                .uri("/api/v1/customers/99999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.error").isEqualTo("Not Found");
    }

    @Test
    @DisplayName("POST /api/v1/customers should create a customer and return 200 with correct data")
    @DirtiesContext // resets server context after test
    void shouldCreateCustomerSuccessfully() {
        String requestBody = "{\"userName\":\"testUserName\",\"name\":\"testName\",\"phoneNumber\":\"+49000000042\"}";

        client.post()
                .uri("/api/v1/customers")
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
    @DisplayName("POST /api/v1/customers should return 400 when given a taken phone number")
    @DirtiesContext
    void testAddCustomerWithTakenNumberReturns400() {
        String requestBody = "{\"userName\":\"testUserName\",\"name\":\"testName\",\"phoneNumber\":\"+490009890\"}";

        client.post()
                .uri("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(requestBody))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("POST /api/v1/customers should add customer to repository")
    @DirtiesContext
    void testAddCustomerIsAddedToRepository() {
        String requestBody = "{\"userName\":\"testUserName\",\"name\":\"testName\",\"phoneNumber\":\"+49000000042\"}";

        AtomicLong createdCustomerId = new AtomicLong();
        client.post()
                .uri("/api/v1/customers")
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

    @Test
    @DisplayName("DELETE /api/v1/customers/17418 should remove customer from CustomerRepository")
    @DirtiesContext
    void testDeleteRemovesCustomerFromRepository() {
        client.delete()
                .uri("/api/v1/customers/17418")
                .exchange()
                .expectStatus().isOk();

        assertFalse(repository.existsById(17418L));
    }

    @Test
    @DisplayName("DELETE /api/v1/customers/99999 should return 404 when removing non existing customer")
    @DirtiesContext
    void testDeleteReturns404() {
        assertFalse(repository.existsById(99999L)); // make sure customer actually doesn't exist

        client.delete()
                .uri("/api/v1/customers/99999")
                .exchange()
                .expectStatus().isNotFound();
    }
}