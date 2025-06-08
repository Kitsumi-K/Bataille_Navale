package bataille.addon;

import bataille.game.Boat;
public class Case {
    private Boolean tir;
    private Boat bateau;

    /**
     * Constructeur de base;
     * {@code this.tir =  false};
     * {@code bateau = null}
     */
    public Case(){
        tir = false;
        bateau = null;
    }

    /**
     * @return {@linkplain Boat}
     */
    public Boat getBateau() {
        return bateau;
    }

    /**
     * Fonction permettant de savoir si la case est touché ou non
     * {@code true => case touché | false => case non touché}
     * @return {@linkplain Boolean}
     */
    public Boolean getTir() {
        return tir;
    }

    /**
     * @param bateau est un {@linkplain Boat}, permet de mettre une reference d'un bateau sur cette {@linkplain Case}
     */
    public void setBateau(Boat bateau) {
        this.bateau = bateau;
    }

    /**
     * @param tir est un {@linkplain Boolean}, permet de changé le statut de la case;
     */
    public void setTir(Boolean tir) {
        this.tir = tir;
        if(bateau != null){
            bateau.touche();
        }
    }
}
