package com.passwordcracker.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe utilitaire pour calculer le hash MD5 d'une chaîne de caractères.
 * Utilisée par toutes les stratégies de cassage pour comparer un mot
 * candidat au hash cible.
 */
public class MD5Util {

    // Empêche l'instanciation : classe purement utilitaire (méthode statique).
    private MD5Util() {
    }

    /**
     * Calcule le hash MD5 d'une chaîne et le retourne sous forme
     * hexadécimale (32 caractères, minuscules).
     *
     * @param input la chaîne à hasher
     * @return le hash MD5 en hexadécimal
     */
    public static String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                // 0xFF & b évite les soucis de signe lors de la conversion
                // d'un byte Java (signé) en valeur hexadécimale.
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    // Un octet peut donner un seul caractère hexa (ex: "a"
                    // au lieu de "0a") : il faut le compléter par un 0
                    // pour toujours avoir 2 caractères par octet.
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // MD5 fait partie des algorithmes garantis par toute JVM standard,
            // donc ce cas ne devrait jamais se produire en pratique.
            throw new RuntimeException("Erreur : Algorithme MD5 indisponible.", e);
        }
    }
}
