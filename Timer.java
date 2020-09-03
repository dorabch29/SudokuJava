import java.awt.*;

public class Timer implements Visible {
    private long START;
    private boolean Stop = false; 
    private String lastTime;



    public Timer(){
        START = System.currentTimeMillis(); 
    }

    public void stop(){
        Stop = true; 
    }

    public void restart(){
        START = System.currentTimeMillis();
        Stop = false; 
    }

    public void render(Graphics g){
        if(Stop){
            g.setColor(Color.BLACK);
            g.setFont(new Font("MonoSpace", Font.BOLD, 45 ));
            g.clearRect(0, 450, 300, 450);
            g.drawString(lastTime, 20, 500 );

        }else{
        long currentMillis = System.currentTimeMillis() - START; 
        int secondsPassed = (int)(currentMillis / 1000);
        int minutes = (secondsPassed / 60) % 60; 
        int hours = (minutes / 60) % 24; 
        int seconds = secondsPassed % 60; 
        String secondsString = "" + seconds; 
        String minutesString =  "" + minutes; 
        String hoursString = "" + hours;
        if(seconds  < 10){
             secondsString = "0" + seconds; 
        }
        if(minutes < 10){
             minutesString = "0" + minutes; 
        }
        if(hours < 10){
            hoursString = "0" + hours; 
        }
        String timeString = hoursString + ":" + minutesString + ":" + secondsString;
        lastTime = timeString;  
        g.setColor(Color.BLACK);
        g.setFont(new Font("MonoSpace", Font.BOLD, 45 ));
        g.clearRect(0, 450, 300, 450);
        g.drawString(timeString, 20, 500 );
        }
    }
    
}