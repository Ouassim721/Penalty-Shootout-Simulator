import java.util.Random;
import java.util.Scanner;

public class PenaltyShootout {
    private static final char[] DIRECTIONS = { 'G', 'D', 'M' };
    private static final char[] PUISSANCES = { 'P', 'M', 'F' };
    private static final int NOMBRE_TIRS = 5;

    private int score;
    private Scanner scanner;
    private Random random;

    public PenaltyShootout() {
        this.score = 0;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    //fonctions principale de Tir au but
    public void commencerMatch() {
        System.out.println("⚽ Bienvenue dans le jeu de tir au but ! ⚽\n");
        System.out.println("Vous avez " + NOMBRE_TIRS + " tirs pour marquer.\n");

        for (int i = 0; i < NOMBRE_TIRS; i++) {
            System.out.println("\n--- 🔥 Tir #" + (i + 1) + " ---");
            effectuerTir();
        }

        afficherScoreFinal();
        scanner.close();
    }

    //fonctions de le la tir au but
    private void effectuerTir() {
        char choixDirection = demanderChoix("Choisissez où tirer : Gauche (G), Droite (D) ou Milieu (M) : ", DIRECTIONS);
        char choixPuissance = demanderChoix("Sélectionnez la puissance du tir : Puissant (P), Moyen (M) ou Faible (F) : ", PUISSANCES);
        char choixGardien = DIRECTIONS[random.nextInt(DIRECTIONS.length)];

        System.out.println("\n🧤 Le gardien se prépare...");
        pause(2000);
        System.out.println("Le gardien plonge vers : " + choixGardien);

        if (tirReussi(choixDirection, choixGardien, choixPuissance)) {
            System.out.println("🥳 BUT ! Vous avez marqué !");
            score++;
        } else {
            System.out.println("☹️ Le gardien a arrêté le tir !");
        }
    }

    //Calculer la probabilite de la reussite de la frappe
    private boolean tirReussi(char choixDirection, char choixGardien, char choixPuissance) {
        if (choixDirection == choixGardien) {
            // Tirs puissants ont une petite chance de passer malgré le gardien
            return choixPuissance == 'P' && random.nextDouble() > 0.7;
        } else {
            // Si puissance faible, il y a une petite chance de rater le cadre
            return choixPuissance != 'F' || random.nextDouble() > 0.2;
        }
    }

    //Demander a l'utilisateur de choisir la directions de tir
    private char demanderChoix(String message, char[] options) {
        char choix;
        while (true) {
            System.out.print(message);
            String input = scanner.next().toUpperCase();
            if (input.length() > 0 && contient(options, input.charAt(0))) {
                choix = input.charAt(0);
                break;
            }
            System.out.println("❌ Choix invalide. Essayez encore.");
        }
        return choix;
    }

    //Verification si la saisit appartient au choix valides
    private boolean contient(char[] array, char value) {
        for (char c : array) {
            if (c == value) return true;
        }
        return false;
    }

    //la fonction responsable de delay pour le plongemant du gardien
    private void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println("Erreur de pause.");
        }
    }

    //Affichage du score finale
    private void afficherScoreFinal() {
        System.out.println("\n🏁 FIN DU MATCH !");
        System.out.println("⚽ Votre score final : " + score + "/" + NOMBRE_TIRS);
    }
}
