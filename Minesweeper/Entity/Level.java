package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Level implements Layout{

    private int easyMine = 10;
    private int mediumMine = 40;
    private int hardMine = 99;

    private int easyWidth = 10;
    private int mediumWidth = 18;
    private int hardWidth = 24;

    private int easyHeight = 8;
    private int mediumHeight = 14;
    private int hardHeight = 20;

    private int levelX;
    private int levelY;

    private BufferedImage[] imageLevel;
    private BufferedImage image;

    private int levelCenterX;
    private int levelCenterY;

    private int mx = 0;
    private int my = 0;

    public Level(int levelX, int levelY){
        this.levelX = levelX;
        this.levelY = levelY;
        this.levelCenterX = levelX + 25;
        this.levelCenterY = levelY + 25;
        
        loadImageLevel();
    }

	private void loadImageLevel() {
		BufferedImage temp = LoadSave.getLoadSave().GetSpriteAtlas("button/level.png");
		imageLevel = new BufferedImage[3];
		for (int i = 0; i < imageLevel.length; i++)
			imageLevel[i] = temp.getSubimage(i*50, 0, 50, 50);

        image = imageLevel[0];
	}   
    
    public void update(){
    
    }

    public void draw(Graphics g){
        g.drawImage(image,levelX,levelY,null);
        if (inLevel()){
            g.setColor(new Color(0,0,0,50));
            g.fillOval(levelX, levelY, 50, 50);
        }
    }

    public void mouseMoved(MouseEvent e){
        mx = e.getX() - 7;
        my = e.getY() - 30;         
    }

    public void mouseClicked(MouseEvent e){

    }

    public boolean inLevel(){
        int dif = (int) Math.sqrt(Math.abs(mx - levelCenterX)*Math.abs(mx - levelCenterX) + Math.abs(my - levelCenterY)*Math.abs(my - levelCenterY)); 
        if (dif < 25){
            return true;
        }
        return false;
    }

    public int getEasyMine(){
        return easyMine;
    }

    public int getMediumMine(){
        return mediumMine;
    }

    public int getHardMine(){
        return hardMine;
    }

    public int getEasyWidth(){
        return easyWidth;
    }

    public int getMediumWidth(){
        return mediumWidth;
    }

    public int getHardWidth(){
        return hardWidth;
    }

    public int getEasyHeight(){
        return easyHeight;
    }

    public int getMediumHeight(){
        return mediumHeight;
    }

    public int getHardHeight(){
        return hardHeight;
    }

}
