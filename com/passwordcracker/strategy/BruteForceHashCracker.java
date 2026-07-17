package com.passwordcracker.strategy;

import com.passwordcracker.core.HashCracker;
import com.passwordcracker.util.MD5Util;

public class BruteForceHashCracker implements HashCracker {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_LENGTH = 4;

    @Override
    public String crack(String targetHash) {
        for (int length = 1; length <= MAX_LENGTH; length++) {
            String result = tryAllCombinations("", length, targetHash);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private String tryAllCombinations(String currentCombo, int targetLength, String targetHash) {
        if (currentCombo.length() == targetLength) {
            if (MD5Util.hash(currentCombo).equalsIgnoreCase(targetHash)) {
                return currentCombo;
            }
            return null;
        }
        for (int i = 0; i < ALPHABET.length(); i++) {
            String newCombo = currentCombo + ALPHABET.charAt(i);
            String result = tryAllCombinations(newCombo, targetLength, targetHash);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}