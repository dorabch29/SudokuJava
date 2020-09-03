import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

public class GameCanvas extends Canvas implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 4123790921675742447L;
    private final int HEIGHT = 575;
    private final int WIDTH = 600;
    private final int BOARD_BOUND = 450; 
    private final int SPACING = BOARD_BOUND / 9; 
    private final int BUTTON_SPACE = 112; 

    public GameHandler gameHandler; 
    boolean running = false;
    private Thread gameLoop;

    public GameCanvas() {

        setLocation(0, 0);
        setSize(BOARD_BOUND, 563); 
       // Creating Buttons and the handler
        GameHandler handler = new GameHandler(SPACING);
        LinkedList<Clickable> ClickableList = new LinkedList<Clickable>();
        
        // Create all clickable buttons
        Clickable NewGameButton1 = new Clickable("New Easy");
        NewGameButton1.setName("New Easy");
        Clickable NewGameButton2 = new Clickable("New Med");
        NewGameButton2.setName("New Med");
        Clickable NewGameButton3 = new Clickable("New Hard");
        NewGameButton3.setName("New Hard");
        Clickable CheckButton = new Clickable("Check Board");
        CheckButton.setName("Check Board");
        Clickable SolveButton = new Clickable("Solve Board");
        SolveButton.setName("Solve Board");
        ClickableList.add(NewGameButton1);
        ClickableList.add(NewGameButton2);
        ClickableList.add(NewGameButton3);
        ClickableList.add(CheckButton);
        ClickableList.add(SolveButton);
        //Initializing buttons
        ClickableListener listener = new ClickableListener();
        for (int i = 0; i < ClickableList.size(); i++) {
            ClickableList.get(i).addActionListener(listener);
            ClickableList.get(i).addHandler(handler);
            ClickableList.get(i).setSize(150, BUTTON_SPACE);
            ClickableList.get(i).setLocation(450, i *  BUTTON_SPACE);
            ClickableList.get(i).setBackground(new Color(196, 212, 226));
            ClickableList.get(i).setVisible(true);
        }
        // Adding clickables to handler
        handler.addClickables(ClickableList);


        //  Creating the frame and adding the handler to Canvas ADDING BUTTON LISTENER
        
        GameWindow window = new GameWindow(this, HEIGHT, WIDTH , ClickableList);
        gameHandler = handler; 


        //Adding Timer 
        handler.addTimer(new Timer());

        //Initializing sudoku board
        handler.initializeSudoku();


        //Adding listeners
        addKeyListener(new KeyClick(handler)); 
        addMouseListener(new MouseClick(handler, SPACING));
        


    } 

    public synchronized void start() {
        running = true; 
        gameLoop = new Thread(this);
        gameLoop.setName("gameLoop");
        gameLoop.start();
    }

    public void run() {
        while (running) {
            render();
        }
        stop();
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return; 
        } 
        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK); 
        gameHandler.render(g); 
        renderLines(g); 
        g.dispose();
        bs.show(); 

    }

    public void renderLines(Graphics g){
        //Drawing boundaries
        g.drawLine(0, BOARD_BOUND, BOARD_BOUND, BOARD_BOUND); 
        g.drawLine(BOARD_BOUND, 0, BOARD_BOUND, BOARD_BOUND); 

        //Drawing Lines
        for(int i = SPACING; i  < BOARD_BOUND; i += SPACING){
            int lineWidth = 1; 
            if(i == SPACING * 3 || i == SPACING * 6) lineWidth = 5; 
            g.fillRect(i, 0, lineWidth, BOARD_BOUND ); 
            g.fillRect(0, i, BOARD_BOUND, lineWidth); 

        }

    }

    public synchronized void stop(){
        try{
            gameLoop.join(); 
            running = false; 
        }catch(Exception e){
            e.printStackTrace(); 
        }
    }

    public static void main(String[] args) {
        new GameCanvas(); 
    }


}