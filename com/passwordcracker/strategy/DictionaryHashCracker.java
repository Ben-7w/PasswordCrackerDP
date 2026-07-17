package com.passwordcracker.strategy;

import com.passwordcracker.core.HashCracker;
import com.passwordcracker.util.MD5Util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Stratégie de cassage par dictionnaire : lit un fichier texte contenant
 * un mot par ligne, hash chaque mot en MD5 et le compare au hash cible.
 */
public class DictionaryHashCracker implements HashCracker {

    private static final String DICTIONARY_FILE = "dictionnaire.txt";

    @Override
    public String crack(String targetHash) {
        int attempts = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(DICTIONARY_FILE))) {
            String word;
            while ((word = reader.readLine()) != null) {
                // CORRECTION : trim() supprime les espaces et retours chariot
                // parasites (ex: fichier édité sous Windows avec des fins de
                // ligne \r\n). Sans ce trim, le mot lu contient un "\r" final
                // invisible, son hash MD5 ne correspond alors JAMAIS au hash
                // cible même si le mot est le bon.
                word = word.trim();
                if (word.isEmpty()) {
                    continue;
                }

                attempts++;
                String currentHash = MD5Util.hash(word);
                if (currentHash.equalsIgnoreCase(targetHash)) {
                    System.out.println("[INFO] " + attempts + " mots testes depuis le dictionnaire.");
                    return word;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur : Impossible de lire le fichier " + DICTIONARY_FILE);
        }

        System.out.println("[INFO] " + attempts + " mots testes depuis le dictionnaire.");
        return null;
    }
}

