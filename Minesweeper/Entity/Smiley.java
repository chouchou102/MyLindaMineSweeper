package Entity;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Date;

import Features.AudioPlayer;
import utilz.LoadSave;

public class Smiley implements Layout{

    private int smileyX;
    private int smileyY;

    private int smileyCenterX;
    private int smileyCenterY;

    private int mx = 0;
    private int my = 0;

    private Mine mine;
    private Flagger flagger;
    private TimeCounter timeCounter;
    private GameState gameState;
    private Board board;
    private Life life;
    private AudioPlayer player;
    
    private boolean happiness = true;

    private BufferedImage imageSmiley;

    public Smiley(int smileyX, int smileyY, Mine mine, Flagger flagger, TimeCounter timeCounter, GameState gameState, Life life, AudioPlayer player){
        this.smileyX = smileyX;
        this.smileyY = smileyY;
        this.smileyCenterX = smileyX + 25;
        this.smileyCenterY = smileyY + 25;

        this.mine = mine;
        this.flagger = flagger;
        this.timeCounter = timeCounter;
        this.gameState = gameState;
        this.life = life;
        this.player = player;

        this.gameState.setSmiley(this);
        imageSmiley = LoadSave.getLoadSave().GetSpriteAtlas("smiley/playing.png");
    }

    public void update(){
        if (happiness){
            if (gameState.getVictory()){
                imageSmiley = LoadSave.getLoadSave().GetSpriteAtlas("smiley/win.png");
            }
            else{
                imageSmiley = LoadSave.getLoadSave().GetSpriteAtlas("smiley/playing.png");
            }
        }
        else{
            if (gameState.getDefeat() && board.getLife().getLife() != 0){
                imageSmiley = LoadSave.getLoadSave().GetSpriteAtlas("smiley/undo.png");
            }
            else{
                imageSmiley = LoadSave.getLoadSave().GetSpriteAtlas("smiley/lose.png");
            }
        }
    }

    public void draw(Graphics g){       
        g.drawImage(imageSmiley,smileyX,smileyY,null);
        if (inSmiley()){
            g.setColor(new Color(0,0,0,50));
            g.fillOval(smileyX, smileyY, 50, 50);
        }
    }

    public void mouseMoved(MouseEvent e){
        mx = e.getX() - 7;
        my = e.getY() - 30;         
    }

    public void mouseClicked(MouseEvent e){
        if (inSmiley()){
            AudioPlayer temp = new AudioPlayer("button_click", 2);
            temp.setVolume(1f);
            temp.playEffect();
            if (gameState.getVictory()){
                this.player.playSong(0);
            }
            else if (gameState.getDefeat() && life.getLife() == 0){
                this.player.playSong(0);                
            }
            resetAll();
        }
    }

    public void resetAll(){
        mine.reset();
        gameState.setResetter(true);
        gameState.setVicMesY(-200);
        gameState.setVictory(false);
        gameState.setDefeat(false);
        gameState.setPlaying(true);
        
        timeCounter.setStartDate(new Date());
        timeCounter.setEndDate(0);
        
        flagger.setFlagger(false);
        
        board.clearStack();

        life.setLife(3);

        happiness = true;
        
        gameState.setResetter(false);
    }

    public void resetUndo(){
        gameState.setResetter(true);
        gameState.setVicMesY(-200);
        gameState.setVictory(false);
        gameState.setDefeat(false);
        gameState.setPlaying(true);
        
        happiness = true;

        gameState.setResetter(false);        
    }

    public boolean inSmiley(){
        int dif = (int) Math.sqrt(Math.abs(mx - smileyCenterX)*Math.abs(mx - smileyCenterX) + Math.abs(my - smileyCenterY)*Math.abs(my - smileyCenterY)); 
        if (dif < 25){
            return true;
        }
        return false;
    }

    public boolean getHappiness(){
        return happiness;
    }

    public void setHappiness(boolean value){
        this.happiness = value;
    }

    public void setBoard(Board board){
        this.board = board;
    }
}
