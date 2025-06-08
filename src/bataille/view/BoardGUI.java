package bataille.view;


import java.awt.*;
import javax.swing.*;


public class BoardGUI extends JFrame{


    public BoardGUI(BoardView view1, BoardView view2){
        super("Bataille Navale");
        setVisible(true);
        setSize(2000,1200);
        Container cp=this.getContentPane();
        cp.setLayout(new GridLayout(1,2));
        cp.add(view1);
        cp.add(view2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
