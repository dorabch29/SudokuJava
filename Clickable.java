import javax.swing.*; 

public  class Clickable extends JButton {
    private GameHandler  h;
    private  boolean isLocked = false; 
    Clickable(String name){
        super(name); 
    }

    public void addHandler(GameHandler handler){
        h = handler; 
    }

    public void lock(){
        isLocked = true;
    }
    public void unLock(){
        isLocked = false; 
    }

    public boolean isLocked(){
        return isLocked;
    }

    public GameHandler getHandler(){
        return h; 
    }
}