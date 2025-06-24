package com.softwaretesting.testing.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MiscTest {

    @Test
    void testSumPositiveNumbers(){
        var expectedResult = 5;
        var integer1 = 3;
        var integer2 = 2;
        assertEquals(expectedResult, Misc.sum(integer1,integer2));
    }

    @Test
    void testSumWithZero(){
        var expectedResult = 0;
        var integerValue = 0;
        assertEquals(expectedResult, Misc.sum(integerValue, integerValue));
    }

    @Test
    void testSumNegativeAndPositive(){
        var expectedResult = -1;
        var negativeInteger = -2;
        var positivInteger = 1;
        assertEquals(expectedResult, Misc.sum(negativeInteger, positivInteger));
    }

    @Test
    void testDividePositiveNumbers(){
        var expectedResult = 2.0;
        var integer1 = 6;
        var integer2 = 3;
        assertEquals(expectedResult, Misc.divide(integer1, integer2));
    }

    @Test
    void testDivideNegativeNumbers(){
        var expectedResult = 2.0;
        var negativeInteger1 = -6;
        var negativeInteger2 = -3;
        assertEquals(expectedResult, Misc.divide(negativeInteger1, negativeInteger2));
    }

    @Test
    void tesDividePositiveAndNegativeNumbers(){
        var expectedResult = -2.0;
        var positiveInteger = 6;
        var negativeInteger = -3;
        assertEquals(expectedResult, Misc.divide(positiveInteger, negativeInteger));
    }

    @Test
    void testDivideZeroNumerator(){
        var expectedResult = 0.0;
        var integer1 = 0;
        var integer2 = 6;
        assertEquals(expectedResult, Misc.divide(integer1, integer2));
    }

    @Test
    void testDivideByZeroThrowsException(){
        var exception =  assertThrows(RuntimeException.class, () -> {
            Misc.divide(10, 0);
        });
        assertEquals("This operation would result in division by zero error.", exception.getMessage());
    }

    @Test
    void testColorRedIsSupported(){
        assertTrue(Misc.isColorSupported(Misc.Color.RED));
    }

    @Test
    void testYellowIsSupported(){
        assertTrue(Misc.isColorSupported(Misc.Color.YELLOW));
    }

    @Test
    void tesBlueIsSupported(){
        assertTrue(Misc.isColorSupported(Misc.Color.BLUE));
    }

    @Test
    void testNullThrowsException(){
        var exception = assertThrows(IllegalArgumentException.class, () -> {
            Misc.isColorSupported(null);
        });

        assertEquals("color cannot be null", exception.getMessage());
    }

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
        assertEquals(expectedFactorial, Misc.calculateFactorial(input));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 13, 17, 23, 29, 97})
    void testIsPrime(int input){
        assertTrue(Misc.isPrime(input, 2));
    }

    @ParameterizedTest
    @ValueSource(ints = {-7, -1, 0, 1, 4, 6, 9, 12, 15, 25, 49, 100, 121, 200})
    void testIsNotPrime(int input){
        assertFalse(Misc.isPrime(input, 2));
    }

    @Test
    void testEvenNumber(){
        var evenNumber = 4;
        assertTrue(Misc.isEven(evenNumber));
    }

    @Test
    void testOddNumber(){
        var oddNumber = 3;
        assertFalse(Misc.isEven(oddNumber));
    }

    @Test
    void test0IsEven(){
        assertTrue(Misc.isEven(0));
    }

    @Test
    void testNegativeEvenNumber(){
        var negativeEvenNumber= -2;
        assertTrue(Misc.isEven(negativeEvenNumber));
    }

    @Test
    void testNegativeOddNumber(){
        var negativeOddNumber = -3;
        assertFalse(Misc.isEven(negativeOddNumber));
    }

}