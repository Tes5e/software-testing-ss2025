package com.softwaretesting.testing.customerRegistration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaretesting.testing.customerRegistration.service.CustomerRegistrationService;
import com.softwaretesting.testing.dto.outbound.CustomerOutDTO;
import com.softwaretesting.testing.exception.BadRequestException;
import com.softwaretesting.testing.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerRegistrationController.class)
class CustomerRegistrationControllerTest {

    @Autowired
    private MockMvc _mockMvc;

    @MockBean
    private CustomerRegistrationService _customerRegistrationService;

    @Autowired
    private ObjectMapper _objectMapper;

    @Test
    void registerNewCustomer() throws Exception {
        var dto = new CustomerOutDTO( 2009L,"Max.M", "Max Mustermann", "+491234567");
        var customer = new Customer(2009L,"Max.M", "Max Mustermann", "+491234567");

        when(_customerRegistrationService.registerNewCustomer(any(Customer.class))).thenReturn(customer);

        _mockMvc.perform(post("/api/v1/customer-registration")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(_objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Max.M"))
                .andExpect(jsonPath("$.name").value("Max Mustermann"))
                .andExpect(jsonPath("$.phoneNumber").value("+491234567"));
    }

    @Test
    void registerNewCustomerPhoneNumberAlreadyTaken() throws Exception{
        var dto = new CustomerOutDTO( 2009L,"Max.M", "Max Mustermann", "+491234567");

        when(_customerRegistrationService.registerNewCustomer(any(Customer.class)))
                .thenThrow( new BadRequestException("Phone Number +491234567 taken"));

        _mockMvc.perform(post("/api/v1/customer-registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(_objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}