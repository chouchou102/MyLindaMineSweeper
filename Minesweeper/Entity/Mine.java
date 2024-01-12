package Entity;

import java.util.Random;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Mine implements Layout{

    private int[][] mines;
    private int[][] neighbors;
    private boolean[][] revealed;
    private boolean[][] flagged;
    private int row;
    private int column;


    private int firstX;
    private int firstY;    
    private int first;

    private Board board;

    private int total;

    public Mine(int column, int row) {
        this.row = row;
        this.column = column;
        mines = new int[column][row];
        neighbors = new int[column][row];
        revealed = new boolean[column][row];
        flagged = new boolean[column][row];
    
        this.total = 40;
        first = 0;
    }

    public void resize(int column, int row, int total){
        this.row = row;
        this.column = column;
        mines = new int[column][row];
        neighbors = new int[column][row];
        revealed = new boolean[column][row];
        flagged = new boolean[column][row];

        this.total = total;
    }

    public void firstClick(int x, int y){
        Random rand = new Random();

        int count = 0;
        for(int i = 0; i < column; i++){
            for(int j = 0; j < row; j++){
                if (i <= x + 1 && i >= x - 1 && j <= y + 1 && j >= y - 1){
                    mines[i][j] = 0;
                    revealed[i][j] = true;
                    flagged[i][j] = false;
                    continue;                    
                }

                if (count < total){
                    if (rand.nextInt(100) < 20){
                        mines[i][j] = 1;
                        count++;
                    } else {
                        mines[i][j] = 0;
                    }
                }
                else{
                    mines[i][j] = 0;
                }

                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }

        while (count < total){
            int a = rand.nextInt(column);
            int b = rand.nextInt(row);
            if (a <= x + 1 && a >= x - 1 && b <= y + 1 && b >= y - 1){
                continue;                    
            }
            if (mines[a][b] == 1){
                continue;
            }
            mines[a][b] = 1;
            count++;
        }
        
        for(int i = 0; i < column; i++){
            for(int j = 0; j < row; j++){
                int neighs = 0;
                for(int m = 0; m < column; m++){
                    for(int n = 0; n < row; n++){
                        if (isN(i, j, m, n) == true){
                            neighs++;
                        }
                    }
                }
                neighbors[i][j] = neighs;
            }
        }
                
    }

    public void reset(){
        for(int i = 0; i < column; i++){
            for(int j = 0; j < row; j++){
                mines[i][j] = 0;
                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }
        for(int i = 0; i < column; i++){
            for(int j = 0; j < row; j++){
                int neighs = 0;
                for(int m = 0; m < column; m++){
                    for(int n = 0; n < row; n++){
                        if (isN(i, j, m, n) == true){
                            neighs++;
                        }
                    }
                }
                neighbors[i][j] = neighs;
            }
        }
        first = 0;
        board.clearStack();
    }  
    
    public void update(){

    }
    
    public void draw(Graphics g){

    }

    public void mouseMoved(MouseEvent e){
         
    }

    public void mouseClicked(MouseEvent e){
        if (board.inBoxX() != -1 && board.inBoxY() != -1){
            if (first == 0 && !board.getFlagger().getFlagger()){
                firstX = board.inBoxX();
                firstY = board.inBoxY();
                firstClick(firstX, firstY);
                first++;
                
            }
        }
    }

    public void emptyCell(int i, int j){
        for(int a = i - 1; a <= i + 1;a++){
            for(int b = j - 1; b <= j + 1;b++){
                if (a == i && b == j){
                    continue;
                }

                if (a < column && a >= 0 && b < row && b >= 0){
                    if (getMines()[a][b] == 0){
                        if (!getRevealed()[a][b]){
                                getRevealed()[a][b] = true;
                                board.pushStask(a,b, board.getConstantMax());
                        }
                    }                    
                }
            }
        }
    }

    public boolean fillCell(int i, int j){
        
        if (getNeighbors()[i][j] == 0){
            return false;
        }

        int countFlaggedMine = 0;
        int countFlagged = 0;
        int reveal = 0;
        for(int a = i - 1; a <= i + 1;a++){
            for(int b = j - 1; b <= j + 1;b++){
                if (a == i && b == j){
                    continue;
                }

                if (a < column && a >= 0 && b < row && b >= 0){
                    if (getMines()[a][b] == 1 && getFlagged()[a][b]){
                        countFlaggedMine++;
                    }

                    if (getFlagged()[a][b]){
                        countFlagged++;
                    }
                     
                    if (getRevealed()[a][b]){
                        reveal++;
                    }
                    
                }
            }
        }        

        if (countFlaggedMine == getNeighbors()[i][j] && (reveal != (8 - getNeighbors()[i][j])) && countFlaggedMine == countFlagged){
            return true;
        }

        return false;
    }

    public int totalMines(){
        return total;
    }

    public int totalBoxesRevealed(){
        int total = 0;
        for(int i = 0; i < column; i++){
            for(int j = 0; j < row; j++){
                if (revealed[i][j]){
                    total++;
                }
            }
        }
        return total;
    }

    public int totalMineFlaggered(){
         int total = 0;
        for(int i = 0; i < column; i++){
            for(int j = 0; j < row; j++){
                if (flagged[i][j] && mines[i][j] == 1){
                    total++;
                }
            }
        }
        return total;       
    }

    public boolean isN(int mX, int mY, int cX, int cY){
        if (mX - cX < 2 && mX - cX > -2 && mY - cY < 2 && mY - cY > -2 && mines[cX][cY] == 1 && (mX != cX || mY != cY)){
            return true;
        }

        return false;
    }

    public void setBoard(Board value){
        this.board = value;
    }

    public Board getBoard(){
        return board;
    }

    public int[][] getMines(){
        return mines;
    }

    public int[][] getNeighbors(){
        return neighbors;
    }

    public boolean[][] getRevealed(){
        return revealed;
    }

    public boolean[][] getFlagged(){
        return flagged;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public int getFirst(){
        return first;
    }


}