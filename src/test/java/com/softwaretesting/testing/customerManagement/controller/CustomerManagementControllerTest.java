package com.softwaretesting.testing.customerManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaretesting.testing.customerManagement.service.CustomerManagementService;
import com.softwaretesting.testing.dto.outbound.CustomerOutDTO;
import com.softwaretesting.testing.exception.BadRequestException;
import com.softwaretesting.testing.exception.CustomerNotFoundException;
import com.softwaretesting.testing.model.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerManagementController.class)
class CustomerManagementControllerTest {

    @Autowired
    private MockMvc _mockMvc;

    @MockBean
    private CustomerManagementService _customerManagementService;

    @Autowired
    private ObjectMapper _objectMapper;

    @Test
    void getCustomerByID() throws Exception{
        var customer = new Customer(1L, "Max.M", "Max Mustermann", "+491234567");

        Mockito.when(_customerManagementService.findById(1L)).thenReturn(customer);

        _mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Max.M"))
                .andExpect(jsonPath("$.name").value("Max Mustermann"))
                .andExpect(jsonPath("$.phoneNumber").value("+491234567"));
    }

    @Test
    void getCustomerByIDButCustomerNotFound() throws Exception{
        Mockito.when(_customerManagementService.findById(1L))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "java.util.Optional with id'1' does not exist."));

        _mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void registerNewCustomer() throws Exception{
        var dto = new CustomerOutDTO( 2009L,"Max.M", "Max Mustermann", "+491234567");
        var customer = new Customer(2009L,"Max.M", "Max Mustermann", "+491234567");

        when(_customerManagementService.addCustomer(any(Customer.class))).thenReturn(customer);

        _mockMvc.perform(post("/api/v1/customers")
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

        when(_customerManagementService.addCustomer(any(Customer.class)))
                .thenThrow( new BadRequestException("Phone Number +491234567 taken"));

        _mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(_objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listAllCustomers() throws Exception{
        var customer = new Customer(1L,"Max.M", "Max Mustermann", "+491234567");
        var customer2 = new Customer(2L, "tkl", "Tammo Klaaßen", "+4912345678");

        when(_customerManagementService.list()).thenReturn(Stream.of(customer, customer2).collect(Collectors.toSet()));

        _mockMvc.perform(get("/api/v1/customers/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].userName").value("Max.M"))
                .andExpect(jsonPath("$.[1].userName").value("tkl"));
    }

    @Test
    void deleteCustomerByID() throws Exception{
        doNothing().when(_customerManagementService).delete(1L);

        _mockMvc.perform(delete("/api/v1/customers/1")).andExpect(status().isOk());
    }

    @Test
    void deleteCustomerByIDButCustomerIsNotFound() throws Exception{
        doThrow(new CustomerNotFoundException("Customer with id 1 does not exists"))
                .when(_customerManagementService).delete(1L);

        _mockMvc.perform(delete("http://localhost:8080/api/v1/customers/1"))
                .andExpect(status().isNotFound());
    }

}