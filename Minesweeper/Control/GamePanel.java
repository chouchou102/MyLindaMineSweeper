package Control;

import javax.swing.JPanel;

import Entity.Board;
import Entity.Flagger;
import Entity.GameState;
import Entity.Level;
import Entity.Life;
import Entity.Mine;
import Entity.Smiley;
import Entity.TimeCounter;
import Entity.Undo;
import Features.AudioPlayer;
import Features.Menu;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GamePanel extends JPanel implements Runnable{
    
    public final int PANEL_WIDTH = 900;
    public final int PANEL_HEIGHT = 700; //550

    private Menu menu;

    private Mine mine;
    private Board board;
    private Flagger flagger;
    private Smiley smiley;
    private Undo undo;
    private Level level;
    private TimeCounter timeCounter;
    private GameState gameState;
    private Life life;
    private AudioPlayer player;

    private Thread gameThread;

    private int dimension;

    public GamePanel(GameFrame frame){
        
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT)); //set the size of this class
        this.setBackground(Color.black); //set background as black
        this.setDoubleBuffered(true); //set true: all the drawing from this component will be done in an offscreen painting buffer
        //in short, enabling this can improve game's rendering performance

        Move move = new Move();
        Click click = new Click();
        frame.addMouseMotionListener(move);
        frame.addMouseListener(click);

        player = new AudioPlayer("", 1);
        player.playSong(0);

        menu = new Menu();

        life = new Life(750, 10, 50);
        undo = new Undo((int) (PANEL_WIDTH/2 + 100)/2, (int) 5/2);
        level = new Level((int) (PANEL_WIDTH/2 - 50)/2, (int) 5/2);
        mine = new Mine(18, 14);
        flagger = new Flagger(345, (int) 5/2);
        
        gameState = new GameState(mine, player);
        timeCounter = new TimeCounter(800, 5, gameState,menu);
        smiley = new Smiley((int) (PANEL_WIDTH - 50)/2, (int) 5/2, mine, flagger,timeCounter,gameState,life, player);

        int spacing = 5;
        dimension = (int) (PANEL_HEIGHT - 55 - mine.getRow()*spacing)/mine.getRow();
        board = new Board(PANEL_WIDTH,PANEL_HEIGHT, spacing, dimension, mine, flagger, undo, level ,smiley, gameState, life, player);

        startGameThread();
    }

    public void update(){
        if (menu.getMenuState()){
            menu.update();
        }
        else{
            gameState.update();
            board.update();
            timeCounter.update();
            smiley.update();
            undo.update();
            level.update();            
        }
    }

    public void paintComponent(Graphics g){
        if (menu.getMenuState()){
            menu.draw(g);
        }
        else{
            board.draw(g);
            flagger.draw(g);
            undo.draw(g);
            level.draw(g);
            smiley.draw(g);
            life.draw(g);
            timeCounter.draw(g);
            gameState.draw(g);            
        }
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            if (!gameState.getResetter()){
                gameState.checkVictoryStatus();
            }
            update();
        }
    }

    public class Move implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (menu.getMenuState()){
                menu.mouseMoved(e);
            }
            else{
                smiley.mouseMoved(e);
                board.mouseMoved(e);
                level.mouseMoved(e);
                if (life.getLife() != 0){
                    mine.mouseMoved(e);
                    flagger.mouseMoved(e);
                    undo.mouseMoved(e);
                }                
            }
        }
        
    }

    public class Click implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if (menu.getMenuState()){
                menu.mouseClicked(e);
            }
            else{
                smiley.mouseClicked(e);
                board.mouseClicked(e);
                level.mouseClicked(e); 
                if (life.getLife() != 0){
                    mine.mouseClicked(e);
                    flagger.mouseClicked(e);
                    undo.mouseClicked(e); 
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
}
