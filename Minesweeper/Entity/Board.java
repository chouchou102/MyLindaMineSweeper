package Entity;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Stack;

import Features.AudioPlayer;
import utilz.LoadSave;

public class Board implements Layout{

    private int width;
    private int height;
    private int spacing;
    private int dimension;
    
    private Mine mine;
    private Flagger flagger;
    private Undo undo;
    private Level level;
    private Smiley smiley;
    private GameState gameState;
    private Life life;
    private AudioPlayer player;

    private int mx = 0;
    private int my = 0;

    private int mode = 2;

    private int max;
    private int constMax;
    private int step = 0;

    private int label;

    private BufferedImage gbox;
    private BufferedImage wbox;
    private BufferedImage cbox;
    private BufferedImage bg;
    private BufferedImage bomb;
    private BufferedImage flags;

    private Stack gameSteps = new Stack();

    public Board(int width, int height,int spacing, int dimension ,Mine mine, Flagger flagger, Undo undo, Level level,Smiley smiley, GameState gameState, Life life, AudioPlayer player){
        this.width = width;
        this.height = height;
        this.spacing = spacing;
        this.dimension = dimension;
        this.mine = mine;
        this.flagger = flagger;
        this.undo = undo;
        this.level = level;
        this.smiley = smiley;
        this.gameState = gameState;
        this.life = life;
        this.player = player;

        this.label = (width - (mine.getColumn() + 1)*spacing - mine.getColumn()*dimension)/2;

        this.max = Math.max(mine.getColumn(), mine.getRow());
        this.constMax = max*max + max;

        this.mine.setBoard(this);
        this.smiley.setBoard(this);

        this.gbox = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/gbox.png");
        this.wbox = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/wbox.png");
        this.cbox = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/cbox.png");
        this.bg = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/bg.png");
        this.bomb = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/medbomb.png");
        this.flags = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/medflags.png");
    }

    public void update(){
        if (mode == 1 || mode == 4){
            gbox = LoadSave.getLoadSave().GetSpriteAtlas("easymode/gbox.png");
            wbox = LoadSave.getLoadSave().GetSpriteAtlas("easymode/wbox.png");
            cbox = LoadSave.getLoadSave().GetSpriteAtlas("easymode/cbox.png");
            bg = LoadSave.getLoadSave().GetSpriteAtlas("easymode/bg.png");
            bomb = LoadSave.getLoadSave().GetSpriteAtlas("easymode/ebomb.png");
            flags = LoadSave.getLoadSave().GetSpriteAtlas("easymode/eflags.png");
        }
        else if (mode == 2){
            gbox = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/gbox.png");
            wbox = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/wbox.png");
            cbox = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/cbox.png");
            bg = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/bg.png");
            bomb = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/medbomb.png");
            flags = LoadSave.getLoadSave().GetSpriteAtlas("mediummode/medflags.png");
        }
        else if (mode == 3){
            gbox = LoadSave.getLoadSave().GetSpriteAtlas("spicymode/gbox.png");
            wbox = LoadSave.getLoadSave().GetSpriteAtlas("spicymode/wbox.png");
            cbox = LoadSave.getLoadSave().GetSpriteAtlas("spicymode/cbox.png");
            bg = LoadSave.getLoadSave().GetSpriteAtlas("spicymode/bg.png");
            bomb = LoadSave.getLoadSave().GetSpriteAtlas("spicymode/sbomb.png");
            flags = LoadSave.getLoadSave().GetSpriteAtlas("spicymode/sflags.png");
        }  

        if (gameState.getDefeat() && gameState.getCheck()){
            int temp = life.getLife();
            temp--;
            life.setLife(temp);
            gameState.setCheck(false);
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, width, height);
        
        g.drawImage(bg,0,0,null);  
        for(int i = 0; i < mine.getColumn(); i++){
            for(int j = 0; j < mine.getRow(); j++){
                drawCell(g, i, j);

                if (mine.getRevealed()[i][j] == true){
                    g.setColor(Color.black);
                    if (mine.getMines()[i][j] == 0 && mine.getNeighbors()[i][j] != 0){
                        drawNum(g, i, j);
                    }
                    else if (mine.getMines()[i][j] == 0 && mine.getNeighbors()[i][j] == 0){
                        mine.emptyCell(i, j);
                        g.drawImage(wbox,label + spacing + i*(dimension + spacing), height - (j + 1)*(dimension + spacing),null);  
                    }
                    else if (mine.getMines()[i][j] == 1) {
                        drawMine(g, i, j);
                    }
                }

                if (mine.getFlagged()[i][j]){
                    drawFlag(g, i, j);                       
                }                
            
            }
        }

    }

    private void drawCell(Graphics g,int i,int j){
        g.drawImage(gbox,label + spacing + i*(dimension + spacing), height - (j + 1)*(dimension + spacing),null);       

        if (mx >= label + spacing + i*(dimension + spacing) && mx <= label + spacing + i*(dimension + spacing) + dimension && my >= height - (j + 1)*(dimension + spacing) && my <= height - (j + 1)*(dimension + spacing) + dimension){
            g.drawImage(cbox,label + spacing + i*(dimension + spacing), height - (j + 1)*(dimension + spacing),null); 
        }

        if (mine.getMines()[i][j] == 1){
            g.setColor(Color.yellow);
            //g.fillRect(label + spacing + i*(dimension + spacing), height - (j + 1)*(dimension + spacing),dimension,dimension);
        }
    }

    private void drawNum(Graphics g, int i, int j){
        switch (mine.getNeighbors()[i][j]) {
            case 1:
                g.setColor(Color.blue);
                break;
            case 2:
                g.setColor(Color.green);
                break;
            case 3:
                g.setColor(Color.red);
                break;               
            case 4:
                g.setColor(new Color(0,0,128));
                break;
            case 5:
                g.setColor(new Color(178,34,34));
                break;               
            case 6:
                g.setColor(new Color(72,209,204));
                break;  
            case 7:
                g.setColor(Color.darkGray);
                break;                                                        
            default:
                break;
        }
        if (mode == 2){
            g.setFont(new Font("Tahoma", Font.BOLD, 40));
        }
        else if (mode == 3){
            g.setFont(new Font("Tahoma", Font.BOLD, 25));
        }
        else if (mode == 1){
            g.setFont(new Font("Tahoma", Font.BOLD, 70));
        }
        g.drawImage(wbox,label + spacing + i*(dimension + spacing), height - (j + 1)*(dimension + spacing),null);  
        g.drawString(Integer.toString(mine.getNeighbors()[i][j]),label + spacing + dimension/5 + i*(dimension + spacing), 5*dimension/6 + height - (j + 1)*(dimension + spacing));        
    }

    private void drawFlag(Graphics g, int i, int j){                                   
        g.drawImage(flags,label + spacing + i*(dimension + spacing), height - (j + 1)*(dimension + spacing),null); 
    }

    private void drawMine(Graphics g, int i, int j){ 
        g.drawImage(bomb,label + spacing + i*(dimension + spacing), height - (j + 1)*(dimension + spacing),null);  
            
    }

    public void mouseMoved(MouseEvent e){
        mx = e.getX() - 7;
        my = e.getY() - 30;         
    }

    public void mouseClicked(MouseEvent e){
        if (life.getLife() != 0){
            if (inBoxX() != -1 && inBoxY() != -1){
                if (gameState.getPlaying())
                {
                    if (flagger.getFlagger() && !mine.getRevealed()[inBoxX()][inBoxY()])
                    {
                        if (!mine.getFlagged()[inBoxX()][inBoxY()]){
                            mine.getFlagged()[inBoxX()][inBoxY()] = true;
                            AudioPlayer temp = new AudioPlayer("taophang", 2);
                            temp.setVolume(0.9f);
                            temp.playEffect();
                        }
                        else{
                            mine.getFlagged()[inBoxX()][inBoxY()] = false;
                            AudioPlayer temp = new AudioPlayer("remove", 2);
                            temp.setVolume(0.9f);
                            temp.playEffect();
                        }
                    } 
                    
                    else 
                    {
                        if (!mine.getFlagged()[inBoxX()][inBoxY()])
                        {
                            if (!mine.getRevealed()[inBoxX()][inBoxY()]){
                                mine.getRevealed()[inBoxX()][inBoxY()] = true;

                                AudioPlayer temp;

                                if (mine.getNeighbors()[inBoxX()][inBoxY()] == 0){
                                    
                                    if (mine.getMines()[inBoxX()][inBoxY()] == 1)
                                    {
                                        pushStask(inBoxX(), inBoxY(), 0);
                                        temp = new AudioPlayer("achetchi", 2);
                                        temp.setVolume(1f);
                                        temp.playEffect();
                                    }
                                    else
                                    {
                                        pushStask(0, 0, 2*constMax);
                                        pushStask(inBoxX(), inBoxY(), constMax);
                                        temp = new AudioPlayer("kimchidoao", 2);
                                        temp.setVolume(1f);
                                        temp.playEffect();
                                    }

                                }
                                else 
                                {
                                    if (mine.getMines()[inBoxX()][inBoxY()] == 1){
                                        temp = new AudioPlayer("caigivay", 2);
                                        temp.setVolume(1f);
                                        temp.playEffect();                                        
                                    }
                                    else{
                                        temp = new AudioPlayer("pop", 2);
                                        temp.setVolume(1f);
                                        temp.playEffect();                                        
                                    }
                                    pushStask(inBoxX(), inBoxY(), 0);
                                }                            
                            }
                            else {
                                
                                if (mine.fillCell(inBoxX(), inBoxY())){
                                    pushStask(0, 0, 2*constMax);
                                    mine.emptyCell(inBoxX(), inBoxY());                                
                                }
                            }

                        }
                    }                
                }
            }  

            if (undo.inUndo()){
                AudioPlayer temp = new AudioPlayer("button_click", 2);
                temp.setVolume(1f);
                temp.playEffect();
                int[] a;
                if (((Integer)gameSteps.peek()) >= constMax){
                    while(((Integer)gameSteps.peek()) >= constMax){
                        if ((Integer)gameSteps.peek() == 2*constMax){
                            a = popStack();
                            //mine.getRevealed()[a[0]][a[1]] = false;
                            break;                           
                        }
                        if (step == 2){
                            break;
                        }
                        a = popStack();
                        mine.getRevealed()[a[0]][a[1]] = false;
                    }
                }
                else {
                        a = popStack();
                        if (mine.getMines()[a[0]][a[1]] == 1){
                            smiley.resetUndo();
                            mine.getRevealed()[a[0]][a[1]] = false;
                        }
                        else{ 
                            if (gameState.getVictory()){
                                player.playSong(0);
                                smiley.resetUndo();
                            }
                            mine.getRevealed()[a[0]][a[1]] = false;
                        }     
                        
                }
            }
            
        }
        if (level.inLevel()){
            if (gameState.getVictory()){
                player.playSong(0);
            }
            else if (gameState.getDefeat() && life.getLife() == 0){
                player.playSong(0);                
            }
            
            mode++;
            if (mode == 1 || mode == 4){
                mine.resize(level.getEasyWidth(), level.getEasyHeight(),level.getEasyMine());
                mode = 1;
            }
            else if (mode == 2){
                mine.resize(level.getMediumWidth(), level.getMediumHeight(),level.getMediumMine());
            }
            else if (mode == 3){
                mine.resize(level.getHardWidth(), level.getHardHeight(),level.getHardMine());
            }
            this.max = Math.max(mine.getColumn(), mine.getRow());
            this.constMax = max*max + max;
            resize();
            smiley.resetAll();
            AudioPlayer temp = new AudioPlayer("button_click", 2);
            temp.setVolume(1f);
            temp.playEffect();
        }            

              
    }

    public void resize(){
        dimension = (int) (height - 55 - mine.getRow()*spacing)/mine.getRow();
        label = (width - (mine.getColumn() + 1)*spacing - mine.getColumn()*dimension)/2;
    }

    public  int[] popStack(){
        int[] a = {-1,-1};
        int num = (Integer) gameSteps.pop();
        if (num >= constMax){
            num -= constMax;
        }
        for(int i = 0; i < mine.getColumn(); i++){
            for(int j = 0; j < mine.getRow(); j++){
                    if (num == (max*i + j)){
                        a[0] = i;
                        a[1] = j;
                }
            }
        }

        step--;
        return a;

    }

    public void pushStask(int column, int row, int value){
        int num = max*column + row + value;
        gameSteps.push(num);
        step++;
    }

    public void clearStack(){
        while (!gameSteps.empty()){
            gameSteps.pop();
        }
        step = 0;
    }

    public int inBoxX(){
        for(int i = 0; i < mine.getColumn(); i++){
            for(int j = 0; j < mine.getRow(); j++){
                if (mx >= label + spacing + i*(dimension + spacing) && mx <= label + spacing + i*(dimension + spacing) + dimension && my >= height - (j + 1)*(dimension + spacing) && my <= height - (j + 1)*(dimension + spacing) + dimension){
                    return i;
                }
            }
        }
        return -1;
    }

    public int inBoxY(){
        for(int i = 0; i < mine.getColumn(); i++){
            for(int j = 0; j < mine.getRow(); j++){
                if (mx >= label + spacing + i*(dimension + spacing) && mx <= label + spacing + i*(dimension + spacing) + dimension && my >= height - (j + 1)*(dimension + spacing) && my <= height - (j + 1)*(dimension + spacing) + dimension){
                    return j;
                }
            }
        }
        return -1;
    }

    public int getConstantMax(){
        return constMax;
    }

    public Life getLife(){
        return life;
    }

    public int getLabel(){
        return label;
    }

    public int getSpacing(){
        return spacing;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int getDimension(){
        return dimension;
    }

    public Flagger getFlagger(){
        return flagger;
    }
}

