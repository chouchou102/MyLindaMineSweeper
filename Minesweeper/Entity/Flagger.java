package Entity;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import Features.AudioPlayer;
import utilz.LoadSave;


public class Flagger implements Layout{
    
    private int flaggerX;
    private int flaggerY;

    private int flaggerCenterX;
    private int flaggerCenterY;

    private boolean flagger = false;

    private int mx = 0;
    private int my = 0;

    private BufferedImage imageFlagger;

    public Flagger(int flaggerX, int flaggerY){
        this.flaggerX = flaggerX;
        this.flaggerY = flaggerY;
        this.flaggerCenterX = flaggerX + 25;
        this.flaggerCenterY = flaggerY + 25;

        imageFlagger = LoadSave.getLoadSave().GetSpriteAtlas("button/flagsbutton.png").getSubimage(0, 0, 50, 50);
    }

    public void update(){

    }
    
    public void draw(Graphics g){ 
        g.drawImage(imageFlagger,flaggerX,flaggerY,null);
        if (inFlagger()){
            g.setColor(new Color(0,0,0,50));
            g.fillOval(flaggerX, flaggerY, 50, 50);
        }
    }

    public void mouseMoved(MouseEvent e){
        mx = e.getX() - 7;
        my = e.getY() - 30;         
    }

    public void mouseClicked(MouseEvent e){
        if (inFlagger()){
            if (!flagger){
                flagger = true;
                imageFlagger = LoadSave.getLoadSave().GetSpriteAtlas("button/flagsbutton.png").getSubimage(50*1, 0, 50, 50);
            }else{
                flagger = false;
                imageFlagger = LoadSave.getLoadSave().GetSpriteAtlas("button/flagsbutton.png").getSubimage(0, 0, 50, 50);
            }
            AudioPlayer temp = new AudioPlayer("button_click", 2);
            temp.setVolume(1f);
            temp.playEffect();
        }
    }

    public boolean inFlagger(){
        int dif = (int) Math.sqrt(Math.abs(mx - flaggerCenterX)*Math.abs(mx - flaggerCenterX) + Math.abs(my - flaggerCenterY)*Math.abs(my - flaggerCenterY)); 
        if (dif < 25){
            return true;
        }
        return false;
    }

    public boolean getFlagger(){
        return flagger;
    }

    public void setFlagger(boolean value){
        this.flagger = value;
    }
}
