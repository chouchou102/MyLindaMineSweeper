package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

public class Undo implements Layout{

    private int undoX;
    private int undoY;

    private BufferedImage[] imageUndo;
    private BufferedImage image;

    private int undoCenterX;
    private int undoCenterY;

    private int mx = 0;
    private int my = 0;

    public Undo(int undoX, int undoY){
        this.undoX = undoX;
        this.undoY = undoY;
        this.undoCenterX = undoX + 25;
        this.undoCenterY = undoY + 25;

        loadImageUndo();
    }

	private void loadImageUndo() {
		BufferedImage temp = LoadSave.getLoadSave().GetSpriteAtlas("button/undo.png");
		imageUndo = new BufferedImage[3];
		for (int i = 0; i < imageUndo.length; i++)
			imageUndo[i] = temp.getSubimage(i*50, 0, 50, 50);

        image = imageUndo[0];
	}   
    
    public void update(){
    }

    public void draw(Graphics g){
        g.drawImage(image,undoX,undoY,null);
        if (inUndo()){
            g.setColor(new Color(0,0,0,50));
            g.fillOval(undoX, undoY, 50, 50);
        }
    }

    public void mouseMoved(MouseEvent e){
        mx = e.getX() - 7;
        my = e.getY() - 30;         
    }

    public void mouseClicked(MouseEvent e){

    }

    public boolean inUndo(){
        int dif = (int) Math.sqrt(Math.abs(mx - undoCenterX)*Math.abs(mx - undoCenterX) + Math.abs(my - undoCenterY)*Math.abs(my - undoCenterY)); 
        if (dif < 25){
            return true;
        }
        return false;
    }
}
