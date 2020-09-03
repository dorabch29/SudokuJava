import java.awt.event.*;

public class ClickableListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Clickable button = (Clickable)e.getSource(); 
        if(!button.isLocked()){
            if (button.getName().equals("Check Board")){
                button.getHandler().checkGame(); 
            }
            else if (button.getName().equals("Solve Board")){
                button.getHandler().solveBoard();
                try{Thread.sleep(500);}catch(Exception excpetion){}
                button.getHandler().checkGame(); 
            }
            else if (button.getName().equals("New Easy")){
                button.getHandler().initializeSudoku(0);
            }
            else if (button.getName().equals("New Med")){
                button.getHandler().initializeSudoku(1);
            }
            else if (button.getName().equals("New Hard")){
                button.getHandler().initializeSudoku(2);
            }
     }
    }

}