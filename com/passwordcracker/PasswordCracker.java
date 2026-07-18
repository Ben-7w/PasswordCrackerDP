package com.passwordcracker;

import com.passwordcracker.core.HashCracker;
import com.passwordcracker.core.HashCrackerFactory;

/**
 * Point d'entrée en ligne de commande (CLI) de l'outil PasswordCracker.
 *
 * Usage :
 *   java com.passwordcracker.PasswordCracker -m <BRUTE|DICO> -h <hashMD5>
 */
public class PasswordCracker {

    public static void main(String[] args) {
        // On attend exactement 4 arguments : -m <methode> -h <hash>
        if (args.length != 4) {
            System.out.println("Usage : java com.passwordcracker.PasswordCracker -m <BRUTE|DICO> -h <hashMD5>");
            return;
        }

        String method = null;
        String hash = null;

        // Lecture des arguments sans imposer d'ordre : -m et -h peuvent
        // être passés dans n'importe quel ordre.
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-m") && i + 1 < args.length) {
                method = args[i + 1];
            } else if (args[i].equals("-h") && i + 1 < args.length) {
                hash = args[i + 1];
            }
        }

        if (method == null || hash == null) {
            System.out.println("Erreur dans les arguments fournis.");
            return;
        }

        try {
            // La Factory choisit la bonne stratégie (Dictionnaire ou Force
            // Brute) sans que cette classe ait besoin de connaître les
            // classes concrètes.
            HashCracker cracker = HashCrackerFactory.create(method);

            long debut = System.currentTimeMillis();
            String motDePasse = cracker.crack(hash);
            long fin = System.currentTimeMillis();
            long dureeMs = fin - debut;

            if (motDePasse != null) {
                System.out.println("Password found: " + motDePasse);
            } else {
                System.out.println("Password not found");
            }

            System.out.println("Temps d'execution : " + dureeMs + " ms");
            System.out.println("Methode utilisee : " + method.toUpperCase());

        } catch (IllegalArgumentException e) {
            // Méthode inconnue ou nulle, renvoyée par HashCrackerFactory.
            System.out.println("Erreur : " + e.getMessage());
        } catch (Exception e) {
            // CORRECTION : filet de sécurité pour toute erreur imprévue
            // (ex: fichier dictionnaire.txt absent du dossier d'exécution)
            // afin que le programme affiche un message clair au lieu de
            // planter avec une pile d'erreurs brute.
            System.out.println("Erreur inattendue : " + e.getMessage());
        }
    }
}
