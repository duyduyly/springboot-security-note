package com.example.security.utils;

import java.util.Random;

public class CommonUtils {
    public static String randomNumber(int min, int max) {
        Random random = new Random();
        int number = min + random.nextInt(max);
        return String.valueOf(number);
    }
}
