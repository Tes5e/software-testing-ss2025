package com.softwaretesting.testing.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberValidatorTest {

    private PhoneNumberValidator underTest;

    @BeforeEach
    void setup(){
        underTest = new PhoneNumberValidator();
    }

    @Test
    void itShouldStartWithAPlus(){
        var phoneNumber = "+494105552671";
        assertTrue(underTest.validate(phoneNumber));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "494155552671",
            "+49-415-555-267-1",
            "+49 415 555 267 1",
            "+4941X55T26N1",
            "+494155552671456",
            "+0941555526714"
    })
    void itShouldRejectNumber(String phoneNumber){
        assertFalse(underTest.validate(phoneNumber));
    }

    @Test
    void itShouldAcceptNumberWithMaxLength(){
        var phoneNumber = "+49415555267146";
        assertTrue(underTest.validate(phoneNumber));
    }

    @Test
    void itShouldThrowNullPointerExceptionWhenNumberIsNull(){
        String phoneNumber = null;

        NullPointerException exception =  assertThrows(NullPointerException.class, () ->{
            underTest.validate(phoneNumber);
        });

        assertEquals("Phone number must not be null", exception.getMessage());
    }






}