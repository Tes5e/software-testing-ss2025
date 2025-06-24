package com.softwaretesting.testing.validator;

import com.softwaretesting.testing.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerValidatorTest {

    private CustomerValidator underTest;

    @BeforeEach
    void setup(){
        underTest = new CustomerValidator();
    }

    @Test
    void throwsExceptionIfObjectIsEmpty(){
      var exception = assertThrows(ResponseStatusException.class, () ->
              underTest.validate404(Optional.empty(), "USER_NAME", "Tammo")
      );

      assertEquals(404, exception.getStatus().value());
      assertEquals("404 NOT_FOUND \"java.util.Optional with USER_NAME'Tammo' does not exist.\"",
              exception.getMessage());
    }

    @Test
    void doeNotThrowIfPresent(){
        assertDoesNotThrow(() -> underTest.validate404(Optional.of(new Customer()), "USER_NAME", "Tammo"));
    }


}