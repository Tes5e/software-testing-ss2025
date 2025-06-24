package com.softwaretesting.testing.dao;

import com.softwaretesting.testing.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    private final String name = "Max Mustermann";
    private final String userName = "max.M";
    private final String phoneNumber = "+494105552671";

    private Customer _createCustomer(){
        var customer = new Customer();
        customer.setName(name);
        customer.setUserName(userName);
        customer.setPhoneNumber(phoneNumber);

        return customer;
    }

    @Test
    void testFindByUserNameCheckIfNameIsCorrect(){
        underTest.save(_createCustomer());

        var found = underTest.findByUserName(userName);

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(name, found.get().getName());
    }

    @Test
    void testFindByUserNameCheckIfUserNameIsCorrect(){
        underTest.save(_createCustomer());

        var found = underTest.findByUserName(userName);

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(userName, found.get().getUserName());
    }

    @Test
    void testFindByUserNameCheckIfPhoneNumberIsCorrect(){
        underTest.save(_createCustomer());

        var found = underTest.findByUserName(userName);

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(phoneNumber, found.get().getPhoneNumber());
    }

    @Test
    void testFindByUserNameIsEmptyIfUserDoesntExist(){
        var found = underTest.findByUserName("nicht-existent");
        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    void testFindByUserNameIsEmptyIfUserIsNULL(){
        var found = underTest.findByUserName(null);
        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    void testFindByUserNameIsEmptyIfUserIsEmpty(){
        var found = underTest.findByUserName("");
        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    void testSelectCustomerByPhoneNumberCheckIfNameIsCorrect(){
        underTest.save(_createCustomer());

        var found = underTest.selectCustomerByPhoneNumber(phoneNumber);

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(name, found.get().getName());
    }

    @Test
    void testSelectCustomerByPhoneNumberIfUserNameIsCorrect(){
        underTest.save(_createCustomer());

        var found = underTest.selectCustomerByPhoneNumber(phoneNumber);

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(userName, found.get().getUserName());
    }

    @Test
    void testSelectCustomerByPhoneNumberIfPhoneNumberIsCorrect(){
        underTest.save(_createCustomer());

        var found = underTest.selectCustomerByPhoneNumber(phoneNumber);

        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(phoneNumber, found.get().getPhoneNumber());
    }

    @Test
    void testSelectCustomerByPhoneNumberIfPhoneNumberDoesntExist(){
        var found = underTest.selectCustomerByPhoneNumber("+494104552671");
        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    void testSelectCustomerByPhoneNumberIfPhoneNumberIsNULL(){
        var found = underTest.selectCustomerByPhoneNumber(null);
        Assertions.assertTrue(found.isEmpty());
    }

    @Test
    void testSelectCustomerByPhoneNumberIfPhoneNumberIsEmpty(){
        var found = underTest.selectCustomerByPhoneNumber("");
        Assertions.assertTrue(found.isEmpty());
    }


}