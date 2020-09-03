import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;



public class GameWindow extends Canvas {
    /**
     *
     */
    private static final long serialVersionUID = 8529923697181779901L;

    public GameWindow(GameCanvas game, int HEIGHT, int WIDTH , LinkedList<Clickable> ClickableList ) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setSize(WIDTH,  HEIGHT); 
        frame.setResizable(false);
        frame.add(game);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null); 
        frame.setTitle("Sudoku");
        for(int i = 0 ; i < ClickableList.size() ; i ++){
            frame.add(ClickableList.get(i));
            
        }
        frame.setVisible(true); 
        game.start(); 


    }
    

}