package com.softwaretesting.testing.customerManagement.service;

import com.softwaretesting.testing.dao.CustomerRepository;
import com.softwaretesting.testing.exception.BadRequestException;
import com.softwaretesting.testing.exception.CustomerNotFoundException;
import com.softwaretesting.testing.model.Customer;
import com.softwaretesting.testing.validator.CustomerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerManagementServiceImpTest {

    @InjectMocks
    private CustomerManagementServiceImp underTest;

    @Mock
    private CustomerRepository customerRepositoryExternal;

    @Mock
    private CustomerValidator customerValidatorExternal;

    private Customer customer;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        customer = new Customer(1L, "Max.M", "Max Mustermann", "+1234567");
    }

    @Test
    void testIfCustomerCanBeFoundByUserName(){
        when(customerRepositoryExternal.findByUserName(customer.getUserName()))
                .thenReturn(Optional.of(customer));

        doNothing().when(customerValidatorExternal)
                .validate404(Optional.of(customer), "User-Name", customer.getUserName());

        var result = underTest.findByUserName(customer.getUserName());

        assertEquals(customer, result);
        verify(customerRepositoryExternal).findByUserName(customer.getUserName());
        verify(customerValidatorExternal).validate404(Optional.of(customer), "User-Name", customer.getUserName());
    }

    @Test
    void throwsErrorMessageWhenCustomerIsNotFoundByUserName(){
        when(customerRepositoryExternal.findByUserName(customer.getUserName())).thenReturn(Optional.empty());

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "java.util.Optional with User-Name'Max.M' does not exist.", null))
                .when(customerValidatorExternal)
                .validate404(Optional.empty(),"User-Name", customer.getUserName());

        var exception = assertThrows(ResponseStatusException.class,
                () -> underTest.findByUserName(customer.getUserName()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("404 NOT_FOUND \"java.util.Optional with User-Name'Max.M' does not exist.\""
                , exception.getMessage());
    }

    @Test
    void testIfCustomerCanBeFoundByID(){
        when(customerRepositoryExternal.findById(1L))
                .thenReturn(Optional.of(customer));

        doNothing().when(customerValidatorExternal)
                .validate404(Optional.of(customer), "id", String.valueOf(customer.getId()));

        var result = underTest.findById(1L);

        assertEquals(customer, result);
        verify(customerRepositoryExternal).findById(1L);
        verify(customerValidatorExternal).validate404(Optional.of(customer),  "id",
                String.valueOf(customer.getId()));
    }

    @Test
    void throwsErrorMessageWhenCustomerIsNotFoundByID(){
        when(customerRepositoryExternal.findById(customer.getId())).thenReturn(Optional.empty());

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "java.util.Optional with id'1' does not exist.", null))
                .when(customerValidatorExternal)
                .validate404(Optional.empty(),"id", String.valueOf(customer.getId()));

        var exception = assertThrows(ResponseStatusException.class,
                () -> underTest.findById(customer.getId()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("404 NOT_FOUND \"java.util.Optional with id'1' does not exist.\""
                , exception.getMessage());
    }

    @Test
    void testIfCustomerCanBeSelectByPhoneNumber(){
        when(customerRepositoryExternal.selectCustomerByPhoneNumber(customer.getPhoneNumber()))
                .thenReturn(Optional.of(customer));

        doNothing().when(customerValidatorExternal)
                .validate404(Optional.of(customer), "phone number", customer.getPhoneNumber());

        var result = underTest.selectCustomerByPhoneNumber(customer.getPhoneNumber());

        assertEquals(customer, result);
        verify(customerRepositoryExternal).selectCustomerByPhoneNumber(customer.getPhoneNumber());
        verify(customerValidatorExternal).validate404(Optional.of(customer), "phone number",
                customer.getPhoneNumber());
    }

    @Test
    void throwsErrorMessageWhenCustomerIsNotSelectByPhoneNumber(){
        when(customerRepositoryExternal.selectCustomerByPhoneNumber(customer.getPhoneNumber()))
                .thenReturn(Optional.empty());

        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,
                "java.util.Optional with phone number'+1234567' does not exist.", null))
                .when(customerValidatorExternal)
                .validate404(Optional.empty(),"phone number", customer.getPhoneNumber());

        var exception = assertThrows(ResponseStatusException.class,
                () -> underTest.selectCustomerByPhoneNumber(customer.getPhoneNumber()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("404 NOT_FOUND \"java.util.Optional with phone number'+1234567' does not exist.\""
                , exception.getMessage());
    }

    @Test
    void testIfNewCustomerCanBeAdded(){
        when(customerRepositoryExternal.selectCustomerByPhoneNumber(customer.getPhoneNumber()))
                .thenReturn(Optional.empty());

        when(customerRepositoryExternal.save(customer)).thenReturn(customer);

        var result = underTest.addCustomer(customer);

        assertEquals(customer, result);
        verify(customerRepositoryExternal).selectCustomerByPhoneNumber(customer.getPhoneNumber());
        verify(customerRepositoryExternal).save(customer);
    }

    @Test
    void throwsBadRequestExceptionIfPhoneNumberAlreadyExists(){
        when(customerRepositoryExternal.selectCustomerByPhoneNumber(customer.getPhoneNumber()))
                .thenReturn(Optional.of(customer));

        var exception = assertThrows(BadRequestException.class, () -> underTest.addCustomer(customer));

        assertEquals("Phone Number +1234567 taken", exception.getMessage());
        verify(customerRepositoryExternal).selectCustomerByPhoneNumber(customer.getPhoneNumber());
        verify(customerRepositoryExternal, never()).save(customer);
    }

    @Test
    void testIfCustomerCanBeDeleted(){
        when(customerRepositoryExternal.existsById(customer.getId())).thenReturn(true);

        doNothing().when(customerRepositoryExternal).deleteById(customer.getId());

        underTest.delete(customer.getId());

        verify(customerRepositoryExternal).existsById(customer.getId());
        verify(customerRepositoryExternal).deleteById(customer.getId());
    }

    @Test
    void throwCustomerNotFoundExceptionIfCustomerToDeleteDoesNotExist(){
        when(customerRepositoryExternal.existsById(customer.getId())).thenReturn(false);

        var exception = assertThrows(CustomerNotFoundException.class,
                () -> underTest.delete(customer.getId()));
        assertEquals( "Customer with id 1 does not exists", exception.getMessage());
    }

    @Test
    void testIfMultipleCustomerCanBeSaved(){
        var customer2 = new Customer(2L, "Max.M2", "Max Mustermann2", "+12345672");
        var customer3 = new Customer(3L, "Max.M1", "Max Mustermann3", "+12345673");

        var customerList = Arrays.asList(customer,customer2, customer3);

        when(customerRepositoryExternal.saveAll(customerList)).thenReturn(customerList);

        var result = underTest.saveAll(customerList);

        assertEquals(3, result.size());
        assertTrue(result.contains(customer));
        assertTrue(result.contains(customer2));
        assertTrue(result.contains(customer3));
    }

    @Test
    void throwsNullPointerExceptionWhenCustomerListIsNull(){

        doThrow(new NullPointerException("Cannot invoke \"com.softwaretesting.testing.dao.CustomerRepository.saveAll(java.lang.Iterable)" +
                "\"because \"this.customerRepository\" is null")).when(customerRepositoryExternal).saveAll(null);

        var exception = assertThrows(NullPointerException.class,
                () ->  underTest.saveAll(null));

        assertEquals("Cannot invoke \"com.softwaretesting.testing.dao.CustomerRepository.saveAll(java.lang.Iterable)" +
                "\"because \"this.customerRepository\" is null", exception.getMessage());
    }

    @Test
    void testIfAllCustomersCanBeFoundAndReturnedAsAList(){
        var customer2 = new Customer(2L, "Max.M2", "Max Mustermann2", "+12345672");
        var customer3 = new Customer(3L, "Max.M1", "Max Mustermann3", "+12345673");

        var customerList = Arrays.asList(customer,customer2, customer3);

        when(customerRepositoryExternal.findAll()).thenReturn(customerList);

        var result = underTest.list();

        assertEquals(3, result.size());
        assertTrue(result.contains(customer));
        assertTrue(result.contains(customer2));
        assertTrue(result.contains(customer3));
    }

}