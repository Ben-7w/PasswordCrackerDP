package com.passwordcracker.core;

import com.passwordcracker.strategy.BruteForceHashCracker;
import com.passwordcracker.strategy.DictionaryHashCracker;

/**
 * Fabrique simple (patron Simple Factory).
 * Centralise la création des objets HashCracker : le reste du programme
 * ne doit jamais faire "new DictionaryHashCracker()" ou
 * "new BruteForceHashCracker()" directement, mais passer par create().
 */
public class HashCrackerFactory {

    // Constructeur privé pour empêcher l'instanciation de cette classe utilitaire
    private HashCrackerFactory() {
    }

    /**
     * Crée l'instance de HashCracker correspondant à la méthode demandée.
     *
     * @param method "DICO" pour le dictionnaire, "BRUTE" pour la force brute
     * @return l'instance de HashCracker correspondante
     */
    public static HashCracker create(String method) {
        // Vérifie qu'une méthode a bien été fournie
        if (method == null) {
            throw new IllegalArgumentException("La methode de cassage ne peut pas etre nulle.");
        }

        // equalsIgnoreCase rend la comparaison insensible à la casse (dico, DICO, Dico...)
        if (method.equalsIgnoreCase("DICO")) {
            return new DictionaryHashCracker();
        } else if (method.equalsIgnoreCase("BRUTE")) {
            return new BruteForceHashCracker();
        } else {
            // Méthode inconnue : on arrête tout de suite avec un message clair
            throw new IllegalArgumentException("Methode de cassage inconnue : " + method + ". Utilisez DICO ou BRUTE.");
        }
    }
}
