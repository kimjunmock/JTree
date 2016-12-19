package aniboom;


import java.awt.*;

public class AniBluckSub {
	Color color;
	

	public AniBluckSub(Color c) {
		color = c;
	}

	public void draw(Graphics2D art, int x, int y, int Width) {
		art.setColor(color);
		int bord = Width / 5;
		art.fillRect(x + bord, y + bord, Width - bord * 2, Width - bord * 2);
	}

	public String toString() {
		return "" + color.getRGB();
	}

}