package Entity;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Date;

import Features.Menu;

public class TimeCounter implements Layout{

    private int timeX;
    private int timeY;    

    private int sec;
    private Date startDate = new Date();
    private int endDate = 0;

    private GameState gameState;
    private Menu menu;

    public TimeCounter(int timeX, int timeY, GameState gameState, Menu menu){
        this.timeX = timeX;
        this.timeY = timeY;
        this.sec = 0;

        this.gameState = gameState;
        this.menu = menu;
        this.menu.setTime(this);
    }

    public void update(){
        if (!gameState.getDefeat() && !gameState.getVictory()){
            sec = (int) (new Date().getTime() - startDate.getTime())/1000 + endDate;
        }
        else{
            endDate = sec;
            startDate = new Date();
        }

        if (sec > 999){
            sec = 999;
        }
  
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillRect(timeX, timeY,80,50);

        g.setColor(Color.white);
        if (gameState.getVictory()){
            g.setColor(Color.green);
        }
        else if (gameState.getDefeat()) {
            g.setColor(Color.red);
        }
        g.setFont(new Font("Tahoma", Font.BOLD, 40));

        String time = Integer.toString(sec);
        if (sec < 10){
            time = "00" + Integer.toString(sec);
        }
        else if (sec >= 10 && sec < 100){
            time = "0" + Integer.toString(sec);
        }
        g.drawString(time,timeX,timeY + 40);        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }

    public void setStartDate(Date value){
        this.startDate = value;
    }

    public void setEndDate(int sec){
        this.endDate = sec;
    }

}
