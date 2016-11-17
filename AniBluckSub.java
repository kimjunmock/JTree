 package aniboom;


import java.awt.*;

public class AniBluckSub {
    private Color color;
    public AniBluckSub(Color c) {
        color = c;
    }

    public void draw(Graphics2D art, int x, int y, int bWidth) {
        art.setColor(color);
        int border = bWidth / 5;
        art.fillRect(x + border, y + border, bWidth - border * 2, bWidth - border * 2);
    }
    public String toString() {
        return "" + color.getRGB();
    }

}