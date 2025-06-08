package bataille.player;

import bataille.game.Board;

import bataille.addon.Position;

public interface Player{
    /**
     * @param board est de type {@linkplain Board}, permet de passer le plateau de jeu au joueur afin de jouer;
     */
    abstract void tir(Board board);

    
    /**
     * @param board est de type {@linkplain Board}
     * @param pos de type {@linkplain Position}, permet de set les coordonnees x et y d'une case;
     * <p>{@code resultat = true => le coup c'est bien effectué};</p>
     * <p>{@code resultat = false => le coup ne s'est pas effectué}</p>
     * @return {@linkplain Boolean}
     */
    abstract Boolean effectuerTir(Board board, Position pos);
    /**
     * @param board est de type {@linkplain Board}, permet de passer le plateau de jeu au joueur afin de placer ses bateaux;
     * @throws Exception dans le cas ou il y a un bug dans la config
     */
    abstract void placementBateau(Board board) throws Exception;

    abstract void placementBateauAleatoire(Board board) throws Exception;
}
