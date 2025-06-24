package com.softwaretesting.testing.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiscTest {

    private Misc miscTest;

    @BeforeEach
    void setUp() {
        miscTest = new Misc();
    }

    // isEven
    // test function isEven with the number 2
    @Test
    @DisplayName("Test 1: isEven with number 2 -> True")
    void isEvenValid2() {
        boolean isEvenValid2 = miscTest.isEven(2);
        assertTrue(isEvenValid2);
    }

    // test the function isEven with an uneven number, expect false
    @Test
    @DisplayName("Test 2: isEven with an uneven number -> False")
    void isUnevenInvalid() {
        boolean isUnevenInvalid = miscTest.isEven(1);
        assertFalse(isUnevenInvalid);
    }

    // test the function isEven with an even number, expect false
    @Test
    @DisplayName("Test 3: isEven with an even number -> True")
    void isEvenValid() {
        boolean isEvenValid2 = miscTest.isEven(16);
        assertTrue(isEvenValid2);
    }

    // sum
    // test sum with two positive numbers
    @Test
    @DisplayName("Test 4: sum with two positive numbers")
    void sumWithTwoPositiveNumbers() {
        int sumTwoPositiveNumbers_Actual = miscTest.sum(3, 6);
        int sumTwoPositiveNumbers_Expected = 9;
        assertEquals(sumTwoPositiveNumbers_Expected, sumTwoPositiveNumbers_Actual);
    }

    // test sum with two negative numbers
    @Test
    @DisplayName("Test 5: sum with two negative numbers")
    void sumWithTwoNegativeNumbers() {
        int sumTwoNegativeNumbers_Actual = miscTest.sum(-2, -8);
        int sumTwoNegativeNumbers_Expected = -10;
        assertEquals(sumTwoNegativeNumbers_Expected, sumTwoNegativeNumbers_Actual);
    }

    // test sum with a negative and a positive number
    @Test
    @DisplayName("Test 6: sum with a negative and a positive number")
    void sumWithNegativePositiveNumbers() {
        int sumNegativePositiveNumbers_Actual = miscTest.sum(2, -8);
        int sumNegativePositiveNumbers_Expected = -6;
        assertEquals(sumNegativePositiveNumbers_Expected, sumNegativePositiveNumbers_Actual);
    }

    // isColorSupported
    // test isColorSupported with correct color red
    @Test
    @DisplayName("Test 7: isColorSupported with red -> True")
    void isColorSupportedRed() {
        boolean isColorSupportedRed = miscTest.isColorSupported(Misc.Color.RED);
        assertTrue(isColorSupportedRed);
    }

    // test isColorSupported with correct color yellow
    @Test
    @DisplayName("Test 8: isColorSupported with yellow -> True")
    void isColorSupportedYellow() {
        boolean isColorSupportedYellow = miscTest.isColorSupported(Misc.Color.YELLOW);
        assertTrue(isColorSupportedYellow);
    }

    // test isColorSupported with correct color blue
    @Test
    @DisplayName("Test 9: isColorSupported with blue -> True")
    void isColorSupportedBlue() {
        boolean isColorSupportedBlue = miscTest.isColorSupported(Misc.Color.BLUE);
        assertTrue(isColorSupportedBlue);
    }

    /*
    // testing a not supported color seems important and needed for branch coverage, but not sure how, without adding
    // a color in enum Color in the Misc class, is that allowed?
    // test isColorSupported with incorrect color green
    @Test
    @DisplayName("Test 10: isColorSupported with green -> False")
    public void isColorSupportedGreen() {
        boolean isColorSupportedGreen = miscTest.isColorSupported(Misc.Color.GREEN);
        assertFalse(isColorSupportedGreen);
    }
     */



    // test isColorSupported with null
    @Test
    @DisplayName("Test 11: isColorSupported with null")
    void isColorSupportedNull() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> miscTest.isColorSupported(null)
        );
        assertEquals("color cannot be null", exception.getMessage());
    }

    // divide
    // test divide with random number and divideBy with 0, RuntimeException
    @Test
    @DisplayName("Test 12: divide with divideBy = 0 and divide = 10")
    void divideByZero() {
        RuntimeException exceptionZero = assertThrows(
                RuntimeException.class,
                () -> miscTest.divide(10, 0)
        );
        assertEquals("This operation would result in division by zero error.", exceptionZero.getMessage());
    }

    // test divide with divide = 0 and divideBy = 0, RuntimeException
    @Test
    @DisplayName("Test 13: divide with divideBy = 0 and divide = 0")
    void divideByZeroZero() {
        RuntimeException exceptionZeroZero = assertThrows(
                RuntimeException.class,
                () -> miscTest.divide(0, 0)
        );
        assertEquals("This operation would result in division by zero error.", exceptionZeroZero.getMessage());
    }

    // test divide with two positive integers
    @Test
    @DisplayName("Test 14: divide with two positive values, result integer")
    void divideWithTwoPositiveValues() {
        double divideWithTwoPositiveValues_Actual = miscTest.divide(16, 4);
        double divideWithTwoPositiveValues_Expected = 4;
        assertEquals(divideWithTwoPositiveValues_Expected, divideWithTwoPositiveValues_Actual);
    }

    // test divide with one positive and one negative integers
    @Test
    @DisplayName("Test 15: divide with divide = negative, result integer")
    void divideWithPositiveNegativeValues() {
        double divideWithPositiveNegativeValues_Actual = miscTest.divide(-16, 4);
        double divideWithPositiveNegativeValues_Expected = -4;
        assertEquals(divideWithPositiveNegativeValues_Expected, divideWithPositiveNegativeValues_Actual);
    }

    // test divide with one positive and one negative integers
    @Test
    @DisplayName("Test 16: divide with divideBy = negative, result integer")
    void divideWithPositiveNegativeValues2() {
        double divideWithPositiveNegativeValues_Actual = miscTest.divide(16, -4);
        double divideWithPositiveNegativeValues_Expected = -4;
        assertEquals(divideWithPositiveNegativeValues_Expected, divideWithPositiveNegativeValues_Actual);
    }

    // test divide two positive values and expect double as result
    // function can return double, but how it is now, it will perform integer division, returns only int and therefore below 1 is 0
    // solution: add in class return (double) divide / divideBy
    @Test
    @DisplayName("Test 17: divide with two positive values, result integer")
    void divideWithTwoPositiveValuesDouble() {
        double divideWithTwoPositiveValues_Actual = miscTest.divide(4, 16);
        double divideWithTwoPositiveValues_Expected = 0.0;
        assertEquals(divideWithTwoPositiveValues_Expected, divideWithTwoPositiveValues_Actual);
    }

    // test divide with two negative integers
    @Test
    @DisplayName("Test 18: divide with two negative values, result integer")
    void divideWithTwoNegativeValues() {
        double divideWithTwoNegativeValues_Actual = miscTest.divide(-16, -4);
        double divideWithTwoNegativeValues_Expected = 4;
        assertEquals(divideWithTwoNegativeValues_Expected, divideWithTwoNegativeValues_Actual);
    }

    // calculateFractional
    // test calculateFractional with 0 -> 1
    @Test
    @DisplayName("Test 19: calculateFractional with num = 0")
    void calculateFractionalWith0() {
        long calculateFractionalWith0_Actual = miscTest.calculateFactorial(0);
        long calculateFractionalWith0_Expected = 1;
        assertEquals(calculateFractionalWith0_Expected, calculateFractionalWith0_Actual);
    }

    // test calculateFractional with 1 -> 1
    @Test
    @DisplayName("Test 20: calculateFractional with num = 1")
    void calculateFractionalWith1() {
        long calculateFractionalWith1_Actual = miscTest.calculateFactorial(1);
        long calculateFractionalWith1_Expected = 1;
        assertEquals(calculateFractionalWith1_Expected, calculateFractionalWith1_Actual);
    }

    // test calculateFractional with random number
    @Test
    @DisplayName("Test 21: calculateFractional with num = 5")
    void calculateFractionalWith5() {
        long calculateFractionalWith5_Actual = miscTest.calculateFactorial(5);
        long calculateFractionalWith5_Expected = 120;
        assertEquals(calculateFractionalWith5_Expected, calculateFractionalWith5_Actual);
    }

    // test calculateFractional with negative number -> 1
    @Test
    @DisplayName("Test 22: calculateFractional with negative value")
    void calculateFractionalNegative() {
        long calculateFractionalNegative_Actual = miscTest.calculateFactorial(-3);
        long calculateFractionalNegative_Expected = 1;
        assertEquals(calculateFractionalNegative_Expected, calculateFractionalNegative_Actual);
    }

    @Test
    @DisplayName("Test 22: calculateFractional with negative value")
    void calculateFractionalNegativeOne() {
        long calculateFractionalNegative_Actual = miscTest.calculateFactorial(-1);
        long calculateFractionalNegative_Expected = 1;
        assertEquals(calculateFractionalNegative_Expected, calculateFractionalNegative_Actual);
    }

    // isPrime
    //test the function isPrime with 2 -> positive (special case)
    @Test
    @DisplayName("Test 23: isPrime with 2 -> True")
    void isPrime2() {
        boolean isPrime2Valid = miscTest.isPrime(2, 2);
        assertTrue(isPrime2Valid);
    }

    //test the function isPrime with 3 -> positive
    @Test
    @DisplayName("Test 24: isPrime with 3 -> True")
    void isPrime3() {
        boolean isPrime3Valid = miscTest.isPrime(3, 2);
        assertTrue(isPrime3Valid);
    }

    //test the function isPrime with 13 -> positive
    @Test
    @DisplayName("Test 25: isPrime with 13 -> True")
    void isPrime13() {
        boolean isPrime13Valid = miscTest.isPrime(13, 2);
        assertTrue(isPrime13Valid);
    }

    //test the function isPrime with even 4 -> negative
    @Test
    @DisplayName("Test 26: isPrime with 4 -> False")
    void isPrime4() {
        boolean isPrime4Invalid = miscTest.isPrime(4, 2);
        assertFalse(isPrime4Invalid);
    }

    //test the function isPrime with uneven 9 -> negative
    @Test
    @DisplayName("Test 27: isPrime with 9 -> False")
    void isPrime9() {
        boolean isPrime9Invalid = miscTest.isPrime(9, 2);
        assertFalse(isPrime9Invalid);
    }

    //test the function isPrime with 0 -> negative (special case)
    @Test
    @DisplayName("Test 28: isPrime with 0 -> False")
    void isPrime0() {
        boolean isPrime0Invalid = miscTest.isPrime(0, 2);
        assertFalse(isPrime0Invalid);
    }

    //test the function isPrime with 1 -> negative (special case)
    @Test
    @DisplayName("Test 29: isPrime with 1 -> False")
    void isPrime1() {
        boolean isPrime1Invalid = miscTest.isPrime(1, 2);
        assertFalse(isPrime1Invalid);
    }

    //test the function isPrime with negative number -> negative
    @Test
    @DisplayName("Test 30: isPrime with negative number -> False")
    void isPrimeNegative() {
        boolean isPrimeNegativeInvalid = miscTest.isPrime(-7, 2);
        assertFalse(isPrimeNegativeInvalid);
    }

    @Test
    @DisplayName("Test 31: isPrime with 1 -> False")
    void isPrimeLarger() {
        boolean isPrime1Invalid = miscTest.isPrime(49, 2);
        assertFalse(isPrime1Invalid);
    }
}