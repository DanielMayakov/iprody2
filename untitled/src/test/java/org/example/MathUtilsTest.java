package org.example;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilsTest {

    @Test
    public void testFactorial() {
        Assert.assertEquals(120, MathUtils.factorial(5));
        Assert.assertEquals(1, MathUtils.factorial(0));
        Assert.assertThrows(IllegalArgumentException.class, () -> MathUtils.factorial(-1));
    }

    @Test
    public void testDivide() {
        Assert.assertEquals(2.0, MathUtils.divide(10, 5));
        Assert.assertThrows(ArithmeticException.class, () -> MathUtils.divide(10, 0));
    }

    @Test
    public void testPower() {
        Assert.assertEquals(8.0, MathUtils.power(2, 3));
        Assert.assertEquals(1.0, MathUtils.power(5, 0));
    }

    @Test
    public void testSqrt() {
        Assert.assertEquals(3.0, MathUtils.sqrt(9));
        Assert.assertThrows(IllegalArgumentException.class, () -> MathUtils.sqrt(-1));
    }

    @Test
    public void testIsEven() {
        Assert.assertTrue(MathUtils.isEven(4));
        Assert.assertFalse(MathUtils.isEven(5));
    }

    // Искусственный провальный тест
    @Test
    public void failingTest() {
        Assert.fail("This test is designed to fail.");
    }
}
