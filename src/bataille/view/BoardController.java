package bataille.view;

import java.awt.event.*;

import bataille.Config;
import bataille.game.Board;


public class BoardController extends MouseAdapter {
    private Board model;
    private BoardGUI gui;
    private Config config;
    private Integer DIM;
    private BoardView view1;
    private BoardView view2;
    private Boolean fin;
    public BoardController(Board model) throws Exception{	
        this.model=model;
        this.config=new Config();
        this.DIM=config.getInteger("dimension");
        fin = false;
        model.getPlayer1().placementBateauAleatoire(model);
        model.getPlayer2().placementBateauAleatoire(model);

        view1 = new BoardView(model, model.getPlayer2(), false, this);
        view2 = new BoardView(model, model.getPlayer1(), true);

        gui = new BoardGUI(view1, view2);

        if(config.getString("debug").equals("true")) System.out.println(model);
    }
    @Override
    public void mouseClicked(MouseEvent e){
        if(fin) return;

        Integer x=e.getX();
        Integer y=e.getY();

        Integer cy=(x/DIM)-1;
        Integer cx=(y/DIM)-1;
        
        if(model.isValid(cx,cy)){
        	model.execute(cx,cy);
            if(model.isOver()){
                finJeu();
            }
        	model.getCurrentPlayer().tir(model);
            gui.repaint();
            if(model.isOver()){
                finJeu();
            }
        }
        try {
            if(config.getString("debug").equals("true"))  System.out.println(model);
        } catch (Exception ex) {
            // rien a voir ici {Bruit de criquet}
        }
        
    }

    public void finJeu(){
        fin = true;
    }
}

