package com.ppa.calculationservice;

public class ValidationUtils {
    public static void validateNumbers(int... numbers) {
        for (int num : numbers) {
            if (num < 0 || num > 100) {
                throw new IllegalArgumentException("The valid numeric range is from 0 to 100.");
            }
        }
    }
}
