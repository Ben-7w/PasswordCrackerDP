package com.passwordcracker.strategy;

import com.passwordcracker.core.HashCracker;
import com.passwordcracker.util.MD5Util;

/**
 * Stratégie de cassage par force brute : essaie toutes les combinaisons
 * possibles de lettres minuscules, de longueur 1 à MAX_LENGTH, jusqu'à
 * trouver celle dont le hash MD5 correspond au hash cible.
 *
 * Limite volontaire : alphabet = lettres minuscules uniquement,
 * longueur max = 4. Au-delà, le nombre de combinaisons explose
 * (26^5 = plus de 11 millions) et le temps de calcul devient trop long
 * pour une démonstration.
 */
public class BruteForceHashCracker implements HashCracker {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final int MAX_LENGTH = 4;

    // CORRECTION : compteur de tentatives, absent de la version initiale.
    // Il permet d'afficher une statistique de cassage cohérente avec celle
    // de DictionaryHashCracker (nombre de mots/combinaisons testés).
    private long attempts = 0;

    @Override
    public String crack(String targetHash) {
        attempts = 0;
        for (int length = 1; length <= MAX_LENGTH; length++) {
            String result = tryAllCombinations("", length, targetHash);
            if (result != null) {
                System.out.println("[INFO] " + attempts + " combinaisons testees par force brute.");
                return result;
            }
        }
        System.out.println("[INFO] " + attempts + " combinaisons testees par force brute.");
        return null;
    }

    /**
     * Génère récursivement toutes les combinaisons de longueur targetLength
     * et compare leur hash MD5 au hash cible.
     *
     * @param currentCombo combinaison en cours de construction
     * @param targetLength longueur de combinaison recherchée
     * @param targetHash   hash MD5 cible
     * @return la combinaison trouvée, ou null si aucune ne correspond
     */
    private String tryAllCombinations(String currentCombo, int targetLength, String targetHash) {
        if (currentCombo.length() == targetLength) {
            attempts++;
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
