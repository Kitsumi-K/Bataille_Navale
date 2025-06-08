package bataille.view;

import bataille.Config;
import bataille.addon.Case;
import bataille.addon.Position;
import bataille.game.Board;
import bataille.game.Boat;
import bataille.player.Player;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;


public class BoardView extends JPanel{
    private Integer largeur;
    private Config config;
    private Integer DIM;
    private Integer MARGE;
    private Integer ECART;
    private Integer PLATEAU;
    private Board board;
    private Player player;
    private Boolean visible;
    private Integer largeurCercle;
    private Integer centre;

    public BoardView(Board board, Player player, Boolean visible, MouseAdapter listener) throws Exception{
        config = new Config();
        DIM = config.getInteger("dimension");
        MARGE = DIM/2;
        ECART = MARGE + MARGE/2;
        largeur = config.getInteger("tailleTabLargeur"); 
        PLATEAU = (largeur+1) * DIM;
        this.largeurCercle=config.getInteger("tailleDiametreCercle");
        System.out.println(largeurCercle);
        this.centre=DIM/2;
        this.board = board;
        this.player = player;
        this.visible = visible;
        setSize(PLATEAU+100, PLATEAU+100);
        setVisible(true);
        this.addMouseListener(listener);
    }
    public BoardView(Board board, Player player, Boolean visible) throws Exception{
        this(board, player, visible, null);
    }
    private Boolean horizontal(Boat boat){
        ArrayList<Position> pos = boat.getListPosition();
        if(pos.get(0).getX() == pos.get(pos.size()-1).getX()) return true;
        return false;
    }
    public void paint(Graphics g){
        char car='A';
        int a=0;
        int c=0;
        g.drawLine(DIM,DIM,DIM,PLATEAU);
        g.drawLine(DIM,DIM,PLATEAU,DIM);
        g.setFont(new Font("ComicSans", Font.PLAIN, 20)); 
        while(a<largeur){
            g.drawString(""+car,MARGE+DIM+DIM*a,ECART);
            car = (char) (car+1);
            c++;
            g.drawString(String.valueOf(c),ECART,MARGE+DIM+DIM*a);
            a++;
        }
        for(int i=DIM;i<=PLATEAU;i+=DIM){    
        g.drawLine(DIM,i,PLATEAU,i);
        g.drawLine(i,DIM,i,PLATEAU);

        }
        ArrayList<ArrayList<Case>> plateau = board.getPlateau(player);
        for (int i = 0; i < board.getLargeur(); i ++) {
            for (int j = 0; j < board.getLongueur(); j++) {
                if(plateau.get(i).get(j).getTir()){
                    if(plateau.get(i).get(j).getBateau() != null){
                        //rond rouge
                        g.setColor(Color.RED);
                        g.fillOval((j+1)*DIM+centre-largeurCercle/2,(i+1)*DIM+centre-largeurCercle/2,largeurCercle,largeurCercle);
                        g.setColor(Color.BLACK);
                    }else{
                        //rond vert
                        g.setColor(Color.GREEN);
                        g.fillOval((j+1)*DIM+centre-largeurCercle/2,(i+1)*DIM+centre-largeurCercle/2,largeurCercle,largeurCercle);
                        g.setColor(Color.BLACK);
                    }
                }
            }
        }
        for (Boat b : board.getListBoatPlayer(player)) {
            ArrayList<Position> lst = b.getListPosition();
            if(horizontal(b)){
                if(lst.get(0).getY() < lst.get(lst.size()-1).getY()){
                    if(visible == true){
                        g.drawOval(DIM*(lst.get(0).getY() + 1)+5,DIM*(lst.get(0).getX() + 1)+5,DIM*lst.size()-10,DIM-10);
                    }else if(b.coule()){
                        g.drawOval(DIM*(lst.get(0).getY() + 1)+5,DIM*(lst.get(0).getX() + 1)+5,DIM*lst.size()-10,DIM-10);
                    }
                }else{
                    if(visible == true){
                        g.drawOval(DIM*(lst.get(lst.size()-1).getY() + 1)+5,DIM*(lst.get(lst.size()-1).getX() + 1)+5,DIM*lst.size()-10,DIM-10);
                    }else if(b.coule()){
                        g.drawOval(DIM*(lst.get(lst.size()-1).getY() + 1)+5,DIM*(lst.get(lst.size()-1).getX() + 1)+5,DIM*lst.size()-10,DIM-10);
                    }
                }
                
            }else{
                if(lst.get(0).getX() < lst.get(lst.size()-1).getX()){
                    if(visible == true){
                        g.drawOval(DIM*(lst.get(0).getY() + 1)+5,DIM*(lst.get(0).getX() + 1)+5,DIM-10, DIM*lst.size()-10);
                    }else if(b.coule()){
                        g.drawOval(DIM*(lst.get(0).getY() + 1)+5,DIM*(lst.get(0).getX() + 1)+5,DIM-10, DIM*lst.size()-10);
                    }
                }else{
                    if(visible == true){
                        g.drawOval(DIM*(lst.get(lst.size()-1).getY() + 1)+5,DIM*(lst.get(lst.size()-1).getX() + 1)+5,DIM-10,DIM*lst.size()-10);
                    }else if(b.coule()){
                        g.drawOval(DIM*(lst.get(lst.size()-1).getY() + 1)+5,DIM*(lst.get(lst.size()-1).getX() + 1)+5,DIM-10,DIM*lst.size()-10);
                    }
                }}
        }
    }
}