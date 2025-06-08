package bataille;

import java.util.Scanner;
import java.util.ArrayList;

import bataille.game.Board;
import bataille.player.*;
import bataille.view.BoardController;

public class Jeu {

    private static Player selectionJoueur(ArrayList<String> listeJoueur, Scanner scanner,String nomJoueur){
        while (true) {
            try {
                System.out.println("Selection joueur " + nomJoueur);
                for (int j = 0; j < listeJoueur.size(); j++) {
                    System.out.println(j + "=>" + listeJoueur.get(j));
                }
                int joueur = scanner.nextInt();


                if (joueur == 0) {
                    return new AIPlayer(20, 20);
                }
                if (joueur == 1) {
                    return new RandomPlayer(20, 20);
                }
                if (joueur == 2) {
                    if (listeJoueur.size() == 3){
                        System.out.println("Votre nom ?");
                        String nom = scanner.next();
                        return new Utilisateur(nom, scanner);
                    }
                }
                System.out.println("Vous avez choisi de jouer avec un autre joueur, veuillez choisir un autre joueur");
                clear();

            } catch (Exception e) {
                clear();
                System.out.println("Probleme, reessayé");
            }

        }

    }

    public static void main(String[] args) throws Exception {
        // initiallisation variable
        Scanner scanner = new Scanner(System.in);
        Player joueur1 = null;
        Player joueur2 = null;

        // Liste joueur:
        ArrayList<String> listeJoueur = new ArrayList<>();
        listeJoueur.add("AIPlayer");
        listeJoueur.add("RandomPlayer");

        clear();
        // selection des joueurs
        Boolean stop = false;
        while (!stop) {
            try {
                System.out.println("Selection du mode de jeu:\n1->Jeu via interface graphique\n2->Jeu via terminale");
                Integer jeu = Integer.parseInt(scanner.next());
                switch (jeu) {
                    case 1:
                        Player player1 = new Utilisateur(scanner);
                        Player player2 = selectionJoueur(listeJoueur, scanner, "adverse (Hors Utilisateur)");
                        new BoardController(new Board(player1, player2));
                        stop = true;
                        break;
                    case 2:
                        listeJoueur.add("Humain");
                        joueur1 = selectionJoueur(listeJoueur, scanner, "1");
                        joueur2 = selectionJoueur(listeJoueur, scanner, "2");
                        new Board(joueur1, joueur2).play();
                        stop = true;
                        break;
                    default:
                        clear();
                        System.out.println("Je n'ai pas compris reessayé");
                        break;
                }
            } catch (Exception e) {
                clear();
                System.out.println("Erreur, reessayé");
            }
            
        }
    }

    /**
     * Permet d'effacer le terminal (saute juste suffisament de lignes pour faire un
     * pseudo-clear du terminal);
     */
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}