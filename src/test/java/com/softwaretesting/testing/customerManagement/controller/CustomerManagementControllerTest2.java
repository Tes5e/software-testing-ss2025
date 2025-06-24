package com.softwaretesting.testing.customerManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaretesting.testing.customerManagement.service.CustomerManagementService;
import com.softwaretesting.testing.model.Customer;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// I was uncertain about the validity of my initial tests, so I decided to approach the problem from a different angle.
// I created a second test class for each of the original tests, and the idea occurred to me after midnight,
// so I left the other tests as they were.

@SpringBootTest
@AutoConfigureMockMvc
class CustomerManagementControllerTest2 {

    @Autowired
    private MockMvc _mockMvc;

    @Autowired
    private CustomerManagementService _customerManagementService;

    @Autowired
    private ObjectMapper _objectMapper;

    @Test
    void listAllCustomers() throws Exception{
        _mockMvc.perform(get("/api/v1/customers/list"))
                .andExpect(status().isOk());
    }

    @Test
    void getCustomerByID() throws Exception{
        var customer = new Customer(4000L, "Max.M", "Max Mustermann", "+491234567");
        _customerManagementService.addCustomer(customer);

        _mockMvc.perform(get("/api/v1/customers/4000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Max.M"))
                .andExpect(jsonPath("$.name").value("Max Mustermann"))
                .andExpect(jsonPath("$.phoneNumber").value("+491234567"));
    }

    @Test
    void getCustomerByIDButCustomerNotFound() throws Exception{
        _mockMvc.perform(get("/api/v1/customers/50000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void registerNewCustomer() throws Exception{
        _mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"user12345\",\"name\":\"Tammo Klaassen\",\"phoneNumber\":\"+45666889\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("user12345"))
                .andExpect(jsonPath("$.name").value("Tammo Klaassen"))
                .andExpect(jsonPath("$.phoneNumber").value("+45666889"));
    }

    @Test
    void registerNewCustomerPhoneNumberAlreadyTaken() throws Exception{

        _mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"user1222\",\"name\":\"Jens Meyer\",\"phoneNumber\":\"+491255889\"}"))
                .andExpect(status().isOk());


        _mockMvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"user123\",\"name\":\"Phillip Meier\",\"phoneNumber\":\"+491255889\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCustomerByID() throws Exception{
        _mockMvc.perform(delete("/api/v1/customers/1")).andExpect(status().isOk());
    }


    @Test
    void deleteCustomerByIDButCustomerIsNotFound() throws Exception{
        _mockMvc.perform(delete("/api/v1/customers/50000"))
                .andExpect(status().isNotFound());
    }

}