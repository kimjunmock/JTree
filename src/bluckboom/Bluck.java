package bluckboom;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bluck {
	Color color;
	
    public Bluck(Color c) {
        color = c;
    }
    public void draw(Graphics2D art, int x, int y, int width) {
        art.setColor(color);
        int rect = width/5 ;
        art.fillRect(x + rect, y + rect,width - rect * 2,width -rect * 2);
    }
  
}
