package org.example;

public class MathUtils {

    // Факториал числа
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be non-negative");
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Деление двух чисел
    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }

    // Возведение числа в степень
    public static double power(double base, int exponent) {
        return Math.pow(base, exponent);
    }

    // Квадратный корень числа
    public static double sqrt(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of a negative number");
        }
        return Math.sqrt(number);
    }

    // Проверка числа на четность
    public static boolean isEven(int number) {
        return number % 2 == 0;
    }
}
