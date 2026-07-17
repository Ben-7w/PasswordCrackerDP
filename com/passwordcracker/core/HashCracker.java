package com.passwordcracker.core;

/**
 * Interface commune à toutes les stratégies de cassage de mot de passe.
 * Toute classe qui veut proposer une méthode de cassage (dictionnaire,
 * force brute, etc.) doit implémenter cette interface.
 */
public interface HashCracker {

    /**
     * Tente de retrouver le mot de passe original à partir de son hash.
     *
     * @param hash le hash MD5 à casser
     * @return le mot de passe trouvé, ou null si aucune correspondance
     */
    String crack(String hash);
}
