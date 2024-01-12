package Entity;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Date;

import Features.AudioPlayer;
import utilz.LoadSave;

public class GameState implements Layout{
    
    private boolean victory = false;
    private boolean defeat = false;
    private boolean playing = true;

    private boolean resetter = false;

    private String vicMes = "Nothing yes!";

    private int vicMesX = 475;
    private int vicMesY = -200;

    private Mine mine;
    private Smiley smiley;
    private AudioPlayer player;

    private boolean checkLife = false;

    private BufferedImage statusImg;

    private Date endDate;

    private boolean changeSound = false;

    public GameState(Mine mine, AudioPlayer player){
        this.mine = mine;
        this.player = player;
    }

    public void update(){
        if (victory){
            statusImg = LoadSave.getLoadSave().GetSpriteAtlas("status/win.png");
            vicMes = "Next Level?";
            if (changeSound){
                this.player.stopSong();
                AudioPlayer temp = new AudioPlayer("maycobiettaocho", 2);
                temp.setVolume(1f);
                temp.playEffect();
                changeSound = false;
            }
        }
        else if (defeat && mine.getBoard().getLife().getLife() == 0){
            statusImg = LoadSave.getLoadSave().GetSpriteAtlas("status/lose.png");
            vicMes = "Play Again?";
            if (changeSound){
                this.player.stopSong();
                AudioPlayer temp = new AudioPlayer("lose", 2);
                temp.setVolume(1f);
                temp.playEffect();
                changeSound = false;
            }
        }
        else if (defeat && mine.getBoard().getLife().getLife() != 0){
            statusImg = null;
            vicMes = "Try harder!!!";
        }
    }

    public void draw(Graphics g){
        if (victory){
            g.setColor(Color.green);
        } else if (defeat){
            g.setColor(new Color(255, 152, 67));
        }

        if (victory || defeat){
            vicMesY = - 60 + (int) (new Date().getTime() - endDate.getTime())/10;
            if (vicMesY > 30){
                vicMesY = 30;
            }
            g.setFont(new Font("Tahoma", Font.BOLD, 70));

            FontMetrics fontMetrics = g.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(vicMes);
            int textHeight = fontMetrics.getHeight();

            int x = (mine.getBoard().getWidth() - textWidth) / 2;
            int y = (mine.getBoard().getHeight() - textHeight) / 2;

            // Fill color for the text background
            g.fillRect(x - 10, y - fontMetrics.getAscent(), textWidth + 20, textHeight);

            g.setColor(Color.white);
            g.drawString(vicMes, x, y);

            g.setColor(new Color(0, 0, 0, 50));
            int boardX = mine.getBoard().getLabel() + mine.getBoard().getSpacing();
            int width = mine.getBoard().getWidth() - 2 * boardX;
            int boardY = mine.getBoard().getHeight() - mine.getRow() * (mine.getBoard().getDimension() + mine.getBoard().getSpacing());
            int height = mine.getBoard().getHeight() - mine.getBoard().getSpacing() - boardY;
            g.fillRect(boardX, boardY, width, height);

            g.drawImage(statusImg, 195, 200, null);
        }        
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void checkVictoryStatus(){
        if (!defeat){
            for(int i = 0; i < mine.getColumn(); i++){
                for(int j = 0; j < mine.getRow(); j++){
                    if (mine.getRevealed()[i][j] && mine.getMines()[i][j] == 1){
                        defeat = true;
                        playing = false;
                        checkLife = true;
                        changeSound = true;
                        smiley.setHappiness(false);
                        endDate = new Date();
                    }
                }
            }
        }

        if (!defeat){
            if ((mine.totalBoxesRevealed() >= mine.getColumn()*mine.getRow() - mine.totalMines() || (mine.totalMineFlaggered() == mine.totalMines() && mine.getFirst() == 1)) && !victory){
                victory = true;
                playing = false;
                changeSound = true;
                endDate = new Date();
            }            
        }
        System.out.println("vic:" + victory + " defeat:" + defeat);
    }

    public boolean getVictory(){
        return victory;
    }

    public boolean getDefeat(){
        return defeat;
    }

    public void setVictory(boolean value){
        this.victory = value;
    }

    public void setDefeat(boolean value){
        this.defeat = value;
    }

    public void setSmiley(Smiley value){
        this.smiley = value;
    }

    public void setVicMesY(int value){
        this.vicMesY = value;
    }

    public boolean getResetter(){
        return resetter;
    }

    public void setResetter(boolean value){
        this.resetter = value;
    }

    public boolean getPlaying(){
        return playing;
    }

    public void setPlaying(boolean value){
        this.playing = value;
    }

    public boolean getCheck(){
        return checkLife;
    }

    public void setCheck(boolean value){
        this.checkLife = value;
    }
}
