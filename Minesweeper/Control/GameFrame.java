package Control;

import javax.swing.JFrame;

public class GameFrame extends JFrame{
    
    GamePanel panel;

    GameFrame(){
        panel = new GamePanel(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set-up the sign "x" as exit

        this.setResizable(false); // set ability to resize the frame
        //this.setIconImage(new ImageIcon("Image/GUI/pvz_logo.png").getImage());
        this.setTitle("MineSweeper"); // set title of the frame

        this.add(panel);

        this.pack(); // pack the size of the frame as the same as the size of the panel

        this.setLocationRelativeTo(null); //set frame in the center of the screen
        this.setVisible(true); //set visible of the frame
        this.setLayout(null); //set null 
    }

}
