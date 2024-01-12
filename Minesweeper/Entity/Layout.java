package Entity;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Layout {
    public void update();
    public void draw(Graphics g);
    public void mouseClicked(MouseEvent e);
    public void mouseMoved(MouseEvent e);
}
