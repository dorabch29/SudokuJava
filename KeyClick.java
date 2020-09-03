import java.awt.event.*;



public class KeyClick implements KeyListener{
    private GameHandler handler;


    public KeyClick(GameHandler handler){
        this.handler = handler; 
    }

    @Override
    public void keyPressed(KeyEvent e){
        int num; 
        switch(e.getKeyCode()){ case 49: num = 1; break; case 50:num = 2; break;
            case 51:num = 3;break; case 52:num = 4; break;case 53:num = 5;break;
            case 54:num = 6;break; case 55: num = 7;break;case 56:num = 8;break;
            case 57 :num = 9; break;
            //Delete case, passing a value of 0 resets the cell and the int[][]
             case 8: num = 0;
              break;  default: num = -1;
        }  
        if(!handler.isSelectedLocked() && num != -1) handler.updateByKeyClick(handler.getSelectedCell(), num);
    }
    @Override
    public void keyReleased(KeyEvent e){
    }
    @Override
    public void keyTyped(KeyEvent e){
    }
}