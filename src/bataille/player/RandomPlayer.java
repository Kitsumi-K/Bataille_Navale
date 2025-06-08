package bataille.player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

import bataille.game.Board;
import bataille.game.Boat;
import bataille.Config;
import bataille.addon.Position;

public class RandomPlayer implements Player {
    protected Integer indice;
    protected ArrayList<Position> listeCoup;
    protected Random rand;

    public RandomPlayer(Integer largeur, Integer longueur) {
        indice = 0;
        listeCoup = new ArrayList<>();
        rand = new Random();
        for (int i = 0; i < largeur; i++) {
            for (int j = 0; j < longueur; j++) {
                listeCoup.add(new Position(i, j));
            }
        }
        Collections.shuffle(listeCoup);
    }

    public RandomPlayer() throws Exception{
        this(new Config().getInteger("tailleTabLargeur"), new Config().getInteger("tailleTabLongueur"));
    }
    @Override
    public Boolean effectuerTir(Board board, Position pos) {
        board.execute(pos.getX(), pos.getY());
        indice++;
        return true;
    }

    @Override
    public void tir(Board board) {
        effectuerTir(board, listeCoup.get(indice));

    }

    @Override
    public void placementBateauAleatoire(Board board) throws Exception {
        Boolean fin = false;
        Integer[] c;
        Position pos = new Position();
        Integer j = 0;
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
        placementBateauAleatoire(board);
    }

    @Override
    public String toString() {
        return "Joueur :" + this.hashCode();
    }
}
