package bataille.game;

import java.util.ArrayList;

import bataille.addon.Position;

public class Boat {
    private int touche;
    private int taille;
    private ArrayList<Position> listPosition;

    /**
     * @param taille est de type {@linkplain Integer}
     */
    public Boat(int taille){
        this.taille=taille;
        this.touche=0;
        listPosition = new ArrayList<>();
    }

    /**
     * Obtenir la taille du bateau;
     * @return {@linkplain Integer}
     */
    public int getTaille(){
        return this.taille;
    }

    /**
     * Obtenir le nombre de touches effectue sur le bateau;
     * @return {@linkplain Integer}
     */
    public int getTouche(){
        return this.touche;
    }

    /*public void setTouche(int touche){
        this.touche=touche;
    }*/

    /**
     * @return {@linkplain ArrayList} de {@linkplain Position}
     */
    public ArrayList<Position> getListPosition() {
        return listPosition;
    }

    public void addListPosition(Position pos) {
        this.listPosition.add(pos);
    }

    public void clearListPosition(){
        listPosition.clear();
    }

    /**
     * Obtenir l'etat du bateau;
     * <p>{@code true => le bateau est coule};</p>
     * <p>{@code false => le bateau n'est pas coule};</p>
     * @return {@linkplain Boolean}
     */
    public boolean coule(){
        if(this.touche==this.taille){
            return true;
        }
        return false;
    }

    /**
     * Ajoute une touche au bateau;
     */
    public void touche(){
        this.touche++;
    }
}
