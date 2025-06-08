package bataille.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import bataille.addon.Position;
import bataille.Config;
import bataille.game.Board;
import bataille.game.Boat;

public class Utilisateur implements Player {

    private String name;
    private Scanner scan;

    public Utilisateur(String name, Scanner scan) {
        this.name = name;
        this.scan = scan;
    }

    public Utilisateur(Scanner scan) {
        this("Utilisateur", scan);
    }
    public Utilisateur(String name){
        this(name, null);
    }

    public Boolean effectuerTir(Board board, Position pos) {
        if (board.isValid(pos.getX(), pos.getY())) {
            board.execute(pos.getX(), pos.getY());
            return true;
        }
        return false;
    }

    public void tir(Board board) {
        String next;
        Boolean fin = false;
        do {
            try {
                System.out.println("Selectionnez votre emplacement de tir (ex: A1)");
                next = scan.next();
                if (!Character.isLetter(next.charAt(0))) {
                    throw new Exception();
                }
                fin = effectuerTir(board,
                        new Position(Integer.parseInt(next.split("" + next.charAt(0))[1]) - 1, next.charAt(0) % 65));

            } catch (Exception e) {

            }

        } while (!fin);

    }

    @Override
    public void placementBateauAleatoire(Board board) throws Exception {
        Boolean fin = false;
        Integer[] c;
        Position pos = new Position();
        Integer j = 0;
        Random rand = new Random();
        for (Object taille : new Config().getList("tailleBateau", true)) {
            // System.out.println("taille = " + taille + " Bateau n"+j);
            Boat boat = new Boat((Integer) taille);
            Integer[] h = { -1, 0 };
            Integer[] b = { 1, 0 };
            Integer[] g = { 0, -1 };
            Integer[] d = { 0, 1 };
            while (!fin) {
                ArrayList<Integer[]> lstPos = new ArrayList<>();
                lstPos.add(h);
                lstPos.add(b);
                lstPos.add(g);
                lstPos.add(d);
                Collections.shuffle(lstPos);
                pos.setX(rand.nextInt(board.getLargeur()));
                pos.setY(rand.nextInt(board.getLongueur()));

                for (int i = 0; i < lstPos.size(); i++) {

                    // c = lstPos.get(rand.nextInt(lstPos.size()));
                    c = lstPos.get(i);

                    lstPos.remove(c);
                    fin = board.addBateau(this, boat, pos, c[0], c[1]);

                    if (fin) {
                        lstPos.clear();
                    }
                }

            }
            fin = false;
            j++;
        }
    };

    @Override
    public void placementBateau(Board board) throws Exception {
        String next, dir;
        Integer dx, dy;
        Position pos;
        Boolean fin = false;
        Integer i = 1;
        for (Object tailleBateau : new Config().getList("tailleBateau", true)) {
            Boat boat = new Boat((Integer) tailleBateau);
            System.out.println("Bateau n°" + i);
            System.out.println(board);
            do {
                System.out.println("Selectionnez votre emplacement pour placer le bateau de taille" + tailleBateau
                        + "(ex: A1) => ");
                next = scan.next();
                if (!Character.isLetter(next.charAt(0))) {
                    throw new Exception();
                }

                Integer x = Integer.parseInt(next.split("" + next.charAt(0))[1]) - 1;
                Integer y = next.charAt(0) % 65;
                pos = new Position(x, y);
                System.out.println("Quel direction pour le bateau (h = haut, b = bas, d = droite, g = gauche) => ");
                dir = scan.next();
                switch (dir) {
                    case "h":
                        dx = -1;
                        dy = 0;
                        break;
                    case "b":
                        dx = 1;
                        dy = 0;
                        break;
                    case "d":
                        dx = 0;
                        dy = 1;
                        break;
                    case "g":
                        dx = 0;
                        dy = -1;
                        break;

                    default:
                        dx = 1;
                        dy = 1;

                }
                fin = board.addBateau(this, boat, pos, dx, dy);
                try {

                } catch (Exception e) {
                    System.out.println(e);
                }

            } while (!fin);
            i++;
        }
    }

    @Override
    public String toString() {
        return "Utilisateur :" + name;
    }

}