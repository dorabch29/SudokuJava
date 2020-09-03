import java.awt.*; 

public class Cell implements Visible {
    private boolean selected = false;
    private int value;
    private boolean isLocked;
    private int position; 
    private final int SPACING; 
    private boolean GameWon = false;
    private boolean GameLost = false; 
    private long GameLostTimer; 




    Cell(int pos, int size){
        position = pos; 
        SPACING = size; 
    }
    Cell(int pos, int size, int value){
        position = pos; 
        SPACING = size; 
        this.value = value; 
    }

    public void lock(){
        isLocked = true; 
    }

    public boolean isLocked(){
        return isLocked;
    }

    public void unLock(){
        isLocked = false;
    }
    public void setValue(int n){
        value = n; 
    }

    public boolean isSelected(){
        return selected; 
    }

    public void select(){
        selected = true; 
    }

    public void unSelect(){
        selected = false; 
    }

    public void setToVictoryState(){
        GameWon = true;
    }

    public void reset(){
        value = 0; 
        GameWon = false;
        GameLost = false; 
        isLocked = false; 
    }

    public void setToFailedState(){
        GameLost = true;
        GameLostTimer= System.currentTimeMillis(); 
    }

    public void render(Graphics g){
        if(GameWon){
            g.setColor(Color.GREEN);
        }
        else if(GameLost && (System.currentTimeMillis() - GameLostTimer < 1000)){
            g.setColor(Color.RED);
        }
        else if(selected){
            g.setColor(Color.BLUE); 
        }
        else{ 
            g.setColor(Color.WHITE); 
        }


        g.fillRect(position % 9 * SPACING, ( position / 9) * SPACING, SPACING, SPACING);
        g.setColor(Color.black); 
        int fontSize; 
        if(isLocked) fontSize = 22;
        else fontSize = 18; 
        if(value != 0){
            g.setFont( new Font("MonoSpaced", Font.BOLD, fontSize)); 
            g.drawString("" + value, (position  % 9 * SPACING) + 10 ,(1 + position / 9) * SPACING - 10 );
        }
    }
}