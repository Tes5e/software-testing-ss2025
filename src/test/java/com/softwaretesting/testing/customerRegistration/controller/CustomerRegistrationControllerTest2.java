package com.softwaretesting.testing.customerRegistration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaretesting.testing.customerRegistration.service.CustomerRegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// I was uncertain about the validity of my initial tests, so I decided to approach the problem from a different angle.
// I created a second test class for each of the original tests, and the idea occurred to me after midnight,
// so I left the other tests as they were.
@SpringBootTest
@AutoConfigureMockMvc
class CustomerRegistrationControllerTest2 {

    @Autowired
    private MockMvc _mockMvc;

    @Autowired
    private CustomerRegistrationService _customerRegistrationService;

    @Autowired
    private ObjectMapper _objectMapper;

    @Test
    void registerNewCustomer() throws Exception {
        _mockMvc.perform(post("/api/v1/customer-registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"Max.M\",\"name\":\"Max Mustermann\",\"phoneNumber\":\"+491234567\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Max.M"))
                .andExpect(jsonPath("$.name").value("Max Mustermann"))
                .andExpect(jsonPath("$.phoneNumber").value("+491234567"));
    }

    @Test
    void registerNewCustomerPhoneNumberAlreadyTaken() throws Exception{
        _mockMvc.perform(post("/api/v1/customer-registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"Max.M2\",\"name\":\"Max Mustermann2\",\"phoneNumber\":\"+4912345678\"}"))
                .andExpect(status().isOk());

        _mockMvc.perform(post("/api/v1/customer-registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"Max.M3\",\"name\":\"Max Mustermann3\",\"phoneNumber\":\"+4912345678\"}"))
                .andExpect(status().isBadRequest());
    }
}