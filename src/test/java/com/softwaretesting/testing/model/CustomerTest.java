package com.softwaretesting.testing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testConstructorAndGetters(){
        var customer = new Customer(1L, "Max.M", "Max Mustermann", "+123456789");

        assertEquals(Long.valueOf(1), customer.getId());
        assertEquals("Max.M", customer.getUserName());
        assertEquals("Max Mustermann", customer.getName());
        assertEquals("+123456789", customer.getPhoneNumber());
    }

    @Test
    void testConstructorAndSetters(){
        var customer = new Customer();
        customer.setId(2L);
        customer.setUserName("Max.M");
        customer.setName("Max Mustermann");
        customer.setPhoneNumber("+123456789");

        assertEquals(Long.valueOf(2), customer.getId());
        assertEquals("Max.M", customer.getUserName());
        assertEquals("Max Mustermann", customer.getName());
        assertEquals("+123456789", customer.getPhoneNumber());
    }

    @Test
    void testHashFunctionShouldBeEqual(){
        var customer1 = new Customer(1L, "Max.M", "Max Mustermann", "+123456789");
        var customer2 = new Customer(1L, "Max.M", "Max Mustermann", "+123456789");

        assertEquals(customer1, customer2);
        assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    void testHashFunctionShouldNotBeEqual(){
        var customer1 = new Customer(1L, "Max.M", "Max Mustermann", "+123456789");
        var customer2 = new Customer(2L, "Max.M", "Max Mustermann", "+123456789");

        assertNotEquals(customer1, customer2);
        assertNotEquals(customer1.hashCode(), customer2.hashCode());
    }

    @Test
    void testEqualsFunctionWhenObjectIsNull(){
        var customer1 = new Customer();
        assertFalse(customer1.equals(null));
    }

    @Test
    void testToStringFunction(){
        Customer customer = new Customer(1L, "Max.M", "Max Mustermann", "+123456789");
        String str = customer.toString();
        assertTrue(str.contains("id=1"));
        assertTrue(str.contains("userName='Max.M'"));
        assertTrue(str.contains("name='Max Mustermann'"));
        assertTrue(str.contains("phoneNumber='+123456789'"));
    }
}