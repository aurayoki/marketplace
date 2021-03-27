package com.jm.marketplace.util.chat;

import java.util.Random;

public class GenerateChatChannelID {
    private final static String[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");
    private final static byte length = 16;
    private final static Random random = new Random();

    public static String getRandomChannelID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            sb.append(ALPHABET[(random.nextInt(ALPHABET.length))]);
        }

        return sb.toString();
    }
}
