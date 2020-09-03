import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Point; 
import java.awt.event.*;



public class MouseClick implements MouseListener{

    private GameHandler handler; 
    private final int CELL_SPACE; 

    public MouseClick(GameHandler handler, int cellSpacing){
        super();
        this.handler = handler;
        CELL_SPACE  =cellSpacing; 
    }
   
    public void mouseClicked(MouseEvent e) { 
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int xCord =  e.getX();
        int yCord = e.getY();
        int toSelect = ((xCord / CELL_SPACE) + (yCord /CELL_SPACE * 9));
        if(0 <= toSelect && toSelect < 81)
        handler.switchSelected(toSelect);  
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }



}