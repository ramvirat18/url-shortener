package com.ramesh.url_shortener.util;

import java.security.SecureRandom;

public class ShortCodeGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH=7;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate()
    {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<LENGTH; i++)
        {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }

}
