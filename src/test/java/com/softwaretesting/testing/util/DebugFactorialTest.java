package com.softwaretesting.testing.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DebugFactorialTest {

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 1",
            "2, 2",
            "5, 120",
            "-5, 1",
            "-1, 1"
    })
    void testFactorial(int input, int expectedFactorial) {
        assertEquals(expectedFactorial, DebugFactorial.factorial(input));
    }

    @Test
    void testMainOutput(){
        var outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        DebugFactorial.main(new String[]{});
        String expectedOutput = "Factorial of 5 is 120" + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));
        System.setOut(System.out);
    }

}