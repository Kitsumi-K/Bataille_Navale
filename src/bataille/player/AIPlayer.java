package bataille.player;

import java.util.*;

import bataille.game.Board;
import bataille.game.Boat;
import bataille.addon.Position;

public class AIPlayer extends RandomPlayer {

    private ArrayList<Position> listeCoupCouler;
    private ArrayList<Position> listeJoue;
    private Integer coupInitial;
    private Integer longueurBateau;
    private Integer[] dir;
    private ArrayList<Integer[]> dirList;
    private Integer largeur;
    private Integer longueur;
    private Boolean checkDir;
    private Iterator<Integer[]> it;

    public AIPlayer(Integer largeur, Integer longueur) {
        super(largeur, longueur);

        this.largeur = largeur; // largeur du bateau
        this.longueur = longueur; // longueur du bateau
        listeCoupCouler = new ArrayList<>(); // utilisé pr débuggage et pour test, pourrait être remplacée par un
                                             // booléen
        listeJoue = new ArrayList<>(); // liste des coups joué
        dir = null; // un tableau d'integer contenant les ajout au coordonnées selon la direction
        coupInitial = null; // Int représentant le coup initial touchant le bateau
        longueurBateau = 0; // longueur du bateau permettant de savoir de combien on s'éloigne du coup
                            // initial
        checkDir = false; // booléen permettant de verifier si l'on itére sur les direction ou si la
                          // direction est déjà établie et si on continue dans cette diréction du coup

        dirList = new ArrayList<>(); // liste des direction permettant d'itérer sur les directions
        Integer[] h = { -1, 0 }; // haut
        Integer[] d = { 0, 1 }; // droite
        Integer[] b = { 1, 0 }; // bas
        Integer[] g = { 0, -1 }; // gauche
        dirList.add(h);
        dirList.add(d);
        dirList.add(b);
        dirList.add(g);
        dirList.add(null);

        it = dirList.iterator(); // initialisation de l'itérateur dur les direction
    }

    @Override
    public Boolean effectuerTir(Board board, Position pos) {
        board.execute(pos.getX(), pos.getY());
        return true;
    }

    /**
     * 
     * @param board est de type {@linkplain Board}
     * @param pos   est de type {@linkplain Position}
     *              <p>
     *              {@code resultat = true -> le tir a coulé un bateau}
     *              </p>
     *              <p>
     *              {@code resultat = false -> le tir n'a pas coulé de bateau}
     *              </p>
     * @return {@linkplain Boolean}
     */
    public Boolean aCoule(Board board, Position pos) {

        Boat bateau = board.getPlateau(board.getCurrentPlayer()).get(pos.getX()).get(pos.getY()).getBateau();
        if (bateau != null) {
            return bateau.coule();
        }
        return false;
    }

    /**
     * 
     * @param board est de type {@linkplain Board}
     * @param pos   est de type {@linkplain Position}
     *              <p>
     *              {@code resultat = true -> le tir a touché un bateau}
     *              </p>
     *              <p>
     *              {@code resultat = false -> le tir n'a pas touché de bateau}
     *              </p>
     * @return {@linkplain Boolean}
     */
    public Boolean aTouche(Board board, Position pos) {
        Boat bateau = board.getPlateau(board.getCurrentPlayer()).get(pos.getX()).get(pos.getY()).getBateau();
        if (bateau != null) {
            return true;
        }
        return false;
    }

    @Override
    public void tir(Board board) {
        if (listeCoupCouler.isEmpty()) {
            System.out.println("Aleatoire");

            Position pos = new Position();
            for (int i = 0; i < listeCoup.size(); i++) {
                pos = listeCoup.get(i);
                if (!listeJoue.contains(pos)) {
                    indice = i;
                    break;
                }
            }
            effectuerTir(board, pos);
            listeJoue.add(pos);

            if (aTouche(board, pos)) {
                System.out.println("TOUCHEEEEEEEEEEEEEEEE");
                if (!aCoule(board, pos)) {
                    listeCoupCouler.add(pos);
                    coupInitial = indice;
                    checkDir = true;
                    dir = it.next();
                }
            }
        } else {
            System.out.println("entrée");

            Integer x = listeCoup.get(coupInitial).getX();
            Integer y = listeCoup.get(coupInitial).getY();
            Boolean played = false;
            Position posTir = null;

            if (checkDir) {
                while (!played) {
                    System.out.println("Check Dir");
                    if (dir != null)
                        System.out.println("dir : " + dir[0] + "," + dir[1]);
                    for (Position pos : listeCoup) {
                        if (x + dir[0] >= 0 || x + dir[0] < largeur || y + dir[1] >= 0
                                || y + dir[1] < longueur) {
                            if (pos.getX() == x + dir[0] && pos.getY() == y + dir[1]) {
                                posTir = pos;
                                break;
                            }
                        }
                    }

                    if (posTir != null)
                        System.out.println("posTir : " + posTir);
                    else {
                        System.out.println("hors jeu");
                    }

                    played = testTir(board, played, posTir);
                    if (dir == null) {
                        this.tir(board);
                        break;
                    }
                    if (played)
                        longueurBateau = 2;
                    System.out.println("liste coup couler : " + listeCoupCouler);
                }
            } else {
                System.out.println("coule");
                // Integer[] g = { 0, -1 };

                while (!played) {

                    if (dir != null)
                        System.out.println("dir : " + dir[0] + "," + dir[1]);
                    else
                        System.out.println("dir : null");
                    System.out.println("longueurBateau : " + longueurBateau);
                    for (Position pos : listeCoup) {
                        if ((x + dir[0] * longueurBateau) >= 0 || (x + dir[0] * longueurBateau) < largeur
                                || (y + dir[1] * longueurBateau) >= 0
                                || (y + dir[1] * longueurBateau) < longueur) {
                            if (pos.getX() == x + dir[0] * longueurBateau
                                    && pos.getY() == y + dir[1] * longueurBateau) {
                                posTir = pos;
                                break;
                            }
                        }
                    }

                    if (posTir != null)
                        System.out.println("posTir : " + posTir);
                    else {
                        System.out.println("hors jeu");
                    }

                    played = testTir(board, played, posTir);
                    if (!played) {
                        checkDir = true;
                        longueurBateau = 0;
                        this.tir(board);
                        break;
                    } else {
                        longueurBateau++;
                    }
                    System.out.println("liste coup couler : " + listeCoupCouler);
                }
            }
        }

    }

    /**
     * 
     * @param board  est de type {@linkplain Board}
     * @param played est de type {@linkplain Boolean}
     * @param pos    est de type {@linkplain Position}
     *               <p>
     *               {@code resultat = true -> le tir a été effectué}
     *               </p>
     * @return {@linkplain Boolean}
     */
    public Boolean testTir(Board board, Boolean played, Position pos) {
        if (!listeJoue.contains(pos) && dir != null && pos != null) {
            System.out.println("TIREEEE");
            effectuerTir(board, pos);
            listeJoue.add(pos);
            played = true;
            System.out.println("coup initial : " + listeCoup.get(coupInitial));
            if (aTouche(board, pos) && !aCoule(board, listeCoup.get(coupInitial))) {
                System.out.println("TOUCHEEEEEEEEEEEEEEEE HEHE");
                checkDir = false;
                listeCoupCouler.add(pos);
                return played;

            } else if (aCoule(board, listeCoup.get(coupInitial))) {
                System.out.println("COULEEEEEEEEEEEEEEEE");
                listeCoupCouler.clear();
                indice = coupInitial + 1;
                longueurBateau = 0;
                checkDir = true;
                it = dirList.iterator();
                return played;
            }
            longueurBateau = 0;
            if (it.hasNext())
                dir = it.next();
            return played;
        } else {
            if (pos != null && dir == null) {
                listeCoupCouler.clear();
                indice = coupInitial;
                dir = null;
                longueurBateau = 0;
                checkDir = true;
                return true;
            }
            if (it.hasNext())
                dir = it.next();
            else
                it = dirList.iterator();
            return false;
        }

    }

    @Override
    public String toString() {
        return "Joueur : IA optimisé n°" + this.hashCode();
    }

}
