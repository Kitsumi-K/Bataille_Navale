package bataille.game;

import bataille.addon.Position;
import bataille.Config;
import bataille.addon.Case;
import java.util.ArrayList;
import bataille.player.*;

public class Board {
    private Integer largeur, longueur;
    private ArrayList<ArrayList<Case>> plateauPlayer1;
    private ArrayList<ArrayList<Case>> plateauPlayer2;

    private ArrayList<Boat> listBoatPlayer1;
    private ArrayList<Boat> listBoatPlayer2;

    private Player player1;
    private Player player2;
    private Player currentPlayer;

    /**
     * @param largeur  est de type {@linkplain Integer}, permet de set la largeur
     *                 des plateaux de jeu des deux joueurs;
     * @param longueur est de type {@linkplain Integer}, permet de set la longueur
     *                 des plateaux de jeu des deux joueurs;
     * @param p1       est de type {@linkplain Player}, permet de set un joueur;
     * @param p2       est de type {@linkplain Player}, permet de set un joueur;
     */
    public Board(Integer largeur, Integer longueur, Player p1, Player p2) {
        plateauPlayer1 = new ArrayList<>();
        plateauPlayer2 = new ArrayList<>();
        this.largeur = largeur;
        this.longueur = longueur;

        player1 = p1;
        player2 = p2;
        currentPlayer = p1;

        listBoatPlayer1 = new ArrayList<>();
        listBoatPlayer2 = new ArrayList<>();

        for (int i = 0; i < largeur; i++) {
            plateauPlayer1.add(new ArrayList<>());
            plateauPlayer2.add(new ArrayList<>());
            for (int j = 0; j < longueur; j++) {
                plateauPlayer1.get(i).add(new Case());
                plateauPlayer2.get(i).add(new Case());
            }
        }
    }

    /**
     * @param config est de classe {@linkplain Config}, permet de recuperer des
     *               valeurs de bases set dans le fichier {@code config.properties}
     * @param p1     est de type {@linkplain Player}, permet de set un joueur;
     * @param p2     est de type {@linkplain Player}, permet de set un joueur;
     * @throws Exception dans le cas ou la config n'est pas faite correctement;
     */
    private Board(Config config, Player p1, Player p2) throws Exception {
        this(config.getInteger("tailleTabLargeur"), config.getInteger("tailleTabLongueur"), p1, p2);
    }

    /**
     * @param p1 est de type {@linkplain Player}, permet de set un joueur;
     * @param p2 est de type {@linkplain Player}, permet de set un joueur;
     * @throws Exception dans le cas ou la config du fichier
     *                   {@code config.properties} est incorrecte;
     */
    public Board(Player p1, Player p2) throws Exception {
        this(new Config(), p1, p2);
    }

    /**
     * Obtenir la largeur des plateaux de jeux;
     * 
     * @return {@linkplain Integer}
     */
    public Integer getLargeur() {
        return largeur;
    }

    /**
     * Obtenir la longueur des plateaux de jeux;
     * 
     * @return {@linkplain Integer}
     */
    public Integer getLongueur() {
        return longueur;
    }

    /**
     * Obtenir le plateau du joueur 1
     * 
     * @return {@linkplain ArrayList} d' {@linkplain ArrayList} de {@linkplain Case}
     */
    public ArrayList<ArrayList<Case>> getPlateauPlayer1() {
        return plateauPlayer1;
    }

    /**
     * Obtenir le plateau du joueur 2
     * 
     * @return {@linkplain ArrayList} d' {@linkplain ArrayList} de {@linkplain Case}
     */
    public ArrayList<ArrayList<Case>> getPlateauPlayer2() {
        return plateauPlayer2;
    }

    /**
     * Obtenir le plateau du joueur player
     * 
     * @param player est de type {@linkplain Player} permet de recuperer le plateau de ce joueur
     * 
     * @return {@linkplain ArrayList} d' {@linkplain ArrayList} de {@linkplain Case}
     */
    public ArrayList<ArrayList<Case>> getPlateau(Player player){
        if(player.equals(player1)) return getPlateauPlayer1();
        return getPlateauPlayer2();
    }
    /**
     * Obtenir le joueur 1;
     * 
     * @return {@linkplain Player};
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Obtenir le joueur 2;
     * 
     * @return {@linkplain Player};
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Obtenir le joueur qui doit jouer (le joueur actuel);
     * 
     * @return {@linkplain Player};
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Obtenir la liste des bateaux du joueur 1;
     * 
     * @return {@linkplain ArrayList} de {@linkplain Boat}
     */
    public ArrayList<Boat> getListBoatPlayer1() {
        return listBoatPlayer1;
    }

    /**
     * Obtenir la liste des bateaux du joueur 2;
     * 
     * @return {@linkplain ArrayList} de {@linkplain Boat}
     */
    public ArrayList<Boat> getListBoatPlayer2() {
        return listBoatPlayer2;
    }

    /**
     * Obtenir la liste des bateaux du joueur passé en argument;
     * @param player est de type {@linkplain Player}, permet de recuperer la liste des bateaux du player;
     * @return {@linkplain ArrayList} de {@linkplain Boat}
     */
    public ArrayList<Boat> getListBoatPlayer(Player player) {
        if(player.equals(player1)) return getListBoatPlayer1();
        return getListBoatPlayer2();
    }
    /**
     * Fonction pour obtenir un plateau sous forme de {@linkplain String} 
     * @param player est un joueur de type {@linkplain Player}
     * @param afficheBateau est pour choisir si on affiche les bateaux (type {@linkplain Boolean})
     * @return le plateau du joueur Player
     */
    private String getPlateauStr(Player player, Boolean afficheBateau){
        ArrayList<ArrayList<Case>> plateauPlayer;
        if(player.equals(this.player1)){
            plateauPlayer = plateauPlayer1;
        }else {
            plateauPlayer = plateauPlayer2;
        }
        char caractere = 'A';
        String txt = "   ";
        for (int i = 0; i < largeur; i++) {
            txt += caractere + " ";
            caractere = (char) (caractere + 1);
        }
        txt += "\n";

        for (int i = 0; i < largeur; i++) {
            txt += i + 1 + (i >= 9 ? " " : "  ");
            for (int j = 0; j < longueur; j++) {
                if (plateauPlayer.get(i).get(j).getTir()) {
                    if (plateauPlayer.get(i).get(j).getBateau() == null) {
                        txt += "X ";
                    } else {
                        txt += "! ";
                    }
                } else if (plateauPlayer.get(i).get(j).getBateau() != null) {
                    
                    txt += (afficheBateau ? "O " : "  ");
                } else {
                    txt += "  ";
                }
            }
            txt += "\n";
        }
        return txt;
    }

    /**
     * <p>Placer un bateau sur le plateau du joueur passé en argument;</p>
     * <p>Si dx et dy sont configures, le bateau ne peut etre placé;</p>
     * <p>Si dx = dy, alors le bateau ne peut etre placé;</p>
     * @param player est de type {@linkplain Player}, permet de mettre le bateau sur
     *               son plateau personnelle;
     * @param boat   est de type {@linkplain Boat}, permet de mettre dans chaque
     *               case (si possible) une reference a ce bateau;
     * @param pos est de type {@linkplain Position}, contient les coordonnees x et y d'une case;
     * @param dx     est de type {@linkplain Integer}, permet de definir une
     *               direction pour placer les references du bateau;
     * @param dy     est de type {@linkplain Integer}, permet de definir une
     *               direction pour placer les references du bateau;
     * @return {@linkplain Boolean};
     */
    public Boolean addBateau(Player player, Boat boat, Position pos, Integer dx, Integer dy) {
        ArrayList<ArrayList<Case>> p;
        ArrayList<Boat> lstBateau;

        if (dx != 0 && dy != 0) {
            return false;
        } else if (dx == 0 && dy == 0) {
            return false;
        }
        if (player.equals(player1)) {
            p = getPlateauPlayer1();
            lstBateau = listBoatPlayer1;
        } else {
            p = getPlateauPlayer2();
            lstBateau = listBoatPlayer2;
        }

        ArrayList<Case> lstCase = new ArrayList<>();
        Case caseXY;
        for (int i = 0; i < boat.getTaille(); i++) {
            //System.out.println("x = "+ pos.getX()+", y = "+ pos.getY() + ",dx = "+dx + ", dy = "+dy);
            if (pos.getX() < 0 || pos.getY() < 0) {
                boat.clearListPosition();
                return false;
            } else if (pos.getX() >= largeur || pos.getY() >= longueur) {
                boat.clearListPosition();
                return false;
            }
            caseXY = p.get(pos.getX()).get(pos.getY());
            if (caseXY.getBateau() != null) {
                boat.clearListPosition();
                return false;
            } else {
                boat.addListPosition(new Position(pos.getX(), pos.getY()));
                lstCase.add(caseXY);
            }

            pos.addX(dx);
            pos.addY(dy);
        }
        for (Case case1 : lstCase) {
            case1.setBateau(boat);
        }

        lstBateau.add(boat);

        return true;

    }

    /**
     * Tir sur le plateau ennemi et change ensuite de joueur courant (Attention, pas
     * de verification de coups ici, tester via isValid pour voir si le coup est
     * possible);
     * 
     * @param x est de type {@linkplain Integer}, permet de choisir les coordonnées
     *          d'un tir a effectuer;
     * @param y est de type {@linkplain Integer}, permet de choisir les coordonnées
     *          d'un tir a effectuer;
     */
    public void execute(Integer x, Integer y) {
        if (currentPlayer.equals(player1)) {
            plateauPlayer2.get(x).get(y).setTir(true);
            currentPlayer = player2;
        } else {
            plateauPlayer1.get(x).get(y).setTir(true);
            currentPlayer = player1;
        }
    }

    /**
     * Fonction permettant de savoir si un coups est jouable ou non sur le plateau
     * adverse au joueur courant;
     * 
     * @param x est de type {@linkplain Integer}, permet de choisir les coordonnees
     *          d'un coups;
     * @param y est de type {@linkplain Integer}, permet de choisir les coordonnees
     *          d'un coups;
     * <p>{@code true => le coup est possible};</p>
     * <p>{@code false => le coup n'est pas possible, ou les coordonnees sont en dehors du plateau};</p>
     * @return {@linkplain Boolean};
     */
    public Boolean isValid(Integer x, Integer y) {
        if (x >= 0 && x < plateauPlayer1.size() && y >= 0 && y < plateauPlayer1.get(0).size()) {
            if (currentPlayer.equals(player1)) {
                if (!plateauPlayer2.get(x).get(y).getTir()) { // si la case n'est pas deja touché
                    return true;
                }
            } else {
                if (!plateauPlayer1.get(x).get(y).getTir()) { // si la case n'est pas deja touche
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Retourne un joueur si l'un des joueurs a gagné;
     * 
     * <p>{@code null => pas de gagnant pour le moment};</p>
     * <p>{@linkplain Player} {@code player => un joueur a gagne};</p>
     * @return {@linkplain Player};
     */
    public Player getWinner() {
        Player jActPlayer;
        ArrayList<Boat> lstAct;
        Boolean stop = true;
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                jActPlayer = player1;
                lstAct = listBoatPlayer2;
            } else {
                jActPlayer = player2;
                lstAct = listBoatPlayer1;
            }

            for (Boat boat : lstAct) {
                if (boat.coule() == false) {
                    stop = false;
                }
            }

            if (stop) {
                return jActPlayer;
            }
            stop = true;

        }
        return null;
    }

    /**
     * Retourne si le jeu est termine;
     * 
     * <p>{@code true => le jeu est fini};</p>
     * <p>{@code false => le jeu continu};</p>
     * @return {@linkplain Boolean}
     */
    public Boolean isOver() {
        if (getWinner() == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Fonction utile que dans le cas ou l'on veut jouer en version terminale
     * @throws Exception dans le cas ou les valeurs de la config pour les bateaux ne sont pas correctement set
     */
    public void play() throws Exception{

        if(new Config().getString("placementBateau").equals("true")){
            getPlayer2().placementBateau(this);
            getPlayer1().placementBateau(this);
        }else{
            getPlayer1().placementBateauAleatoire(this);
            getPlayer2().placementBateauAleatoire(this);
        }
        
        while (!isOver()){
            System.out.println(this);
            getCurrentPlayer().tir(this);
        }
        Player winner = getWinner();
        System.out.println("Le joueur : "+winner+" a gagné");
        

    }
    @Override
    public String toString() {
        String plateau1;
        String plateau2;
        String debug = "";
        try {
            Config config = new Config();
            debug = config.getString("debug");
        } catch (Exception e) {
            
        }
        if(debug.equals("true")){
            System.out.println("Dans le debug");
            plateau1 = getPlateauStr(player1, true);
            plateau2 = getPlateauStr(player2, true);
        }else if (currentPlayer.equals(player1)){
            plateau1 = getPlateauStr(player1, true);
            plateau2 = getPlateauStr(player2, false);
        }else{
            plateau1 = getPlateauStr(player1, false);
            plateau2 = getPlateauStr(player2, true);
        }
         
        return player1 + "\n" + plateau1 + "\n\n" + player2 + "\n" + plateau2;
    }
}
