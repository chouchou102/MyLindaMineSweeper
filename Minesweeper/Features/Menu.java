package Features;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Date;

import Entity.Layout;
import Entity.TimeCounter;
import utilz.LoadSave;

public class Menu implements Layout{

    private int mx = 0;
    private int my = 0;

    private BufferedImage bg;
    private BufferedImage start;
    private BufferedImage quit;

    private BufferedImage imageStart;
    private BufferedImage imageQuit;

    private int width = 240;
    private int height = 47;

    private int startX = 130;
    private int startY = 275;

    private int quitX = 130;
    private int quitY = 375;    

    private boolean stateMenu = true;

    private TimeCounter time;

    public Menu(){

        bg = LoadSave.getLoadSave().GetSpriteAtlas("menu/bg_menu.png");
        imageStart = LoadSave.getLoadSave().GetSpriteAtlas("menu/start_button.png");
        imageQuit = LoadSave.getLoadSave().GetSpriteAtlas("menu/quit_button.png");
        start = imageStart.getSubimage(0, 0, width, height);
        quit = imageQuit.getSubimage(0, 0, width, height);
    }

    public void update(){
        if (inStart()){
            start = imageStart.getSubimage(width, 0, width, height);
        }
        else{
            start = imageStart.getSubimage(0, 0, width, height);
        }
        if (inQuit()){
            quit = imageQuit.getSubimage(width, 0, width, height);
        }
        else{
            quit = imageQuit.getSubimage(0, 0, width, height);
        }
    }

    public void draw(Graphics g){
        g.drawImage(bg, 0, 0, null);
        g.drawImage(start, startX, startY, null);
        g.drawImage(quit, quitX, quitY, null); 
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (inStart()){
            AudioPlayer temp = new AudioPlayer("button_click", 2);
            temp.setVolume(1f);
            temp.playEffect();
            stateMenu = false;
            time.setStartDate(new Date());
        }

        if (inQuit()){
            AudioPlayer temp = new AudioPlayer("button_click", 2);
            temp.setVolume(1f);
            temp.playEffect();
            System.exit(0);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mx = e.getX() - 7;
        my = e.getY() - 30; 
    }

    public boolean inStart(){
        if (mx <= startX + width && mx >= startX && my >= startY && my <= startY + height){
            return true;
        }
        return false;
    }

    public boolean inQuit(){
        if (mx <= quitX + width && mx >= quitX && my >= quitY && my <= quitY + height){
            return true;
        }
        return false;
    }

    public boolean getMenuState(){
        return stateMenu;
    }

    public void setMenuState(boolean value){
        this.stateMenu = value;
    }

    public void setTime(TimeCounter time){
        this.time = time;
    }
}
