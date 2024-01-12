package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Life implements Layout{

    private int lifeX;
    private int lifeY;

    private int distance;

    private int life;

    private BufferedImage imageLife;

    public Life(int lifeX, int lifeY, int distance){
        this.lifeX = lifeX;
        this.lifeY = lifeY;
        this.distance = distance;
        this.life = 3;
        this.imageLife = LoadSave.getLoadSave().GetSpriteAtlas("heart/heart2.png");
    }

    public void update(){

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.green);
        for(int i = 0; i < life; i++){
            //g.fillRect(lifeX + i*distance, lifeY, 30 , 30);
            g.drawImage(imageLife,lifeX - i*distance,lifeY,null);
        }

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

    public int getLife(){
        return life;
    }

    public void setLife(int value){
        this.life = value;
    }
    
}
