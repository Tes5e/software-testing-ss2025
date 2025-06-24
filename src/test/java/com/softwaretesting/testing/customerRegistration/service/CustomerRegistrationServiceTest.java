package com.softwaretesting.testing.customerRegistration.service;

import com.softwaretesting.testing.dao.CustomerRepository;
import com.softwaretesting.testing.exception.BadRequestException;
import com.softwaretesting.testing.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerRegistrationServiceTest {

    @InjectMocks
    private CustomerRegistrationService underTest;

    @Mock
    private CustomerRepository customerRepositoryExternal;

    private Customer customer;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        customer = new Customer(1L, "Max.M", "Max Mustermann", "+1234567");
    }

    @Test
    void throwIllegalStateExceptionIfCustomerWithSamePhoneNumberAndNameExists(){
        when(customerRepositoryExternal.selectCustomerByPhoneNumber(customer.getPhoneNumber()))
                .thenReturn(Optional.of(customer));

        var exception = assertThrows(IllegalStateException.class, () ->{
           underTest.registerNewCustomer(customer);
        });

        assertEquals("You are already registered", exception.getMessage());
        verify(customerRepositoryExternal).selectCustomerByPhoneNumber(customer.getPhoneNumber());
        verify(customerRepositoryExternal, never()).save(customer);
    }

    @Test
    void throwBadRequestExceptionIfDifferentCustomerWithSamePhoneNumberExists(){
        var customer2 = new Customer();
        customer2.setName("Phillip Meier");
        customer2.setPhoneNumber("+1234567");

        when(customerRepositoryExternal.selectCustomerByPhoneNumber(customer.getPhoneNumber()))
                .thenReturn(Optional.of(customer));

        var exception = assertThrows(BadRequestException.class, () ->{
           underTest.registerNewCustomer(customer2);
        });

        assertEquals("Phone Number +1234567 taken", exception.getMessage());
        verify(customerRepositoryExternal).selectCustomerByPhoneNumber(customer.getPhoneNumber());
        verify(customerRepositoryExternal, never()).save(customer2);
    }

    @Test
    void testRegisterNewCustomer(){
        when(customerRepositoryExternal.selectCustomerByPhoneNumber(customer.getPhoneNumber()))
                .thenReturn(Optional.empty());

        when(customerRepositoryExternal.save(customer))
                .thenReturn(customer);

        var result = underTest.registerNewCustomer(customer);

        assertEquals(customer, result);
        verify(customerRepositoryExternal).selectCustomerByPhoneNumber(customer.getPhoneNumber());
        verify(customerRepositoryExternal).save(customer);
    }
}