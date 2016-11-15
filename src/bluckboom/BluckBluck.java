package bluckboom;
import java.awt.*;
import java.util.Random;

import java.awt.Color.*;
import bluckboom.BluckMove;

public class BluckBluck {
	
	int bluckwidth = 0;
	int center = 0;
	String angle;
	int rex = 0;
	int rey = 0;
    int limsize = 15;
	Bluck[][] bluck;
	Color[] ColorList = new Color[]{Color.yellow, Color.orange,Color.green,Color.red,Color.white,Color.blue,Color.pink};
	public BluckBluck(int x, int y) {
		rex = x;
		rey = y;
		bluck = new Bluck[x][y];
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
            	bluck[i][j] = RandomBluck();
            }
        }
    }
	public  void draw(BluckFrame frame, Graphics2D art) {
        bluckwidth = getbluckwidth(frame.getWidth(), frame.getHeight());
        center = getcenter(frame.getWidth(), frame.getHeight());
        art.setColor(Color.black);
        art.drawRect((angle.equals("수평") ? 0 : center),
                (angle.equals("수직") ? 0 : center),bluckwidth * rex,bluckwidth * rey);
        for(int i = 0; i < rex; i++) {
            for(int j = 0; j < rey; j++) {
                if(i == 0) {
                    art.setColor(Color.PINK);
                    art.drawLine(angle.equals("수평") ? 0 : center,
                            (angle.equals("수직") ? 0 : center) + bluckwidth * (j + 1),
                            (angle.equals("수평") ? 0 : center) + bluckwidth * rex,
                            (angle.equals("수직") ? 0 : center) + bluckwidth * (j + 1));
                }
                bluck[i][j].draw(art,
                        (angle.equals("수평") ? 0 : center) + bluckwidth * i,
                        (angle.equals("수직") ? 0 : center) + bluckwidth * j, bluckwidth);
            }
            art.setColor(Color.PINK);
            art.drawLine((angle.equals("수평") ? 0 : center) + bluckwidth * (i + 1),
                    angle.equals("수직") ? 0 : center,
                    (angle.equals("수평") ? 0 : center) + bluckwidth * (i + 1),
                    (angle.equals("수직") ? 0 : center) + bluckwidth * rey);
        }
        
    }
	public  int getbluckwidth(int frameX, int frameY) {
        if((frameX / rex) * rey < frameY) {
            angle = "수평";
            return frameX / rex < limsize ? limsize : frameX / rex;
        } else {
            angle = "수직";
            return frameY / rey < limsize? limsize: frameY / rex;
        }
    }
    public int getcenter(int frameX, int frameY) {
        int absoluteCenter;
        switch(angle) {
            case "수평":
                absoluteCenter = frameY / 2;
                return absoluteCenter - (rey / 2 * bluckwidth);
            case "수직":
                absoluteCenter = frameX / 2;
                return absoluteCenter - (rex / 2 * bluckwidth);
        }
		return 0;
    }
    public BluckSub getsub(int x, int y) {
        if(angle == null)
            return null;
        if(angle.equals("수평")) {
            int x1 = x / bluckwidth;
            int y1 = (y - center) / bluckwidth;
            if(x1 >= rex || x1 < 0 || y1 >= rey || y1 < 0)
                return null;
            return new BluckSub(x1, y1);
        } else {
            int x1 = (x - center) / bluckwidth;
            int y1 = y / bluckwidth;
            if(x1 >= rex || x1 < 0 || y1 >= rey || y1 < 0)
                return null;
            return new BluckSub(x1, y1);
        }
    }
    public void switchbluck(BluckSub sub, int bluckmove) {
        int x1 = sub.x, y1 = sub.y;
        int x2, y2;
        switch(bluckmove) {
            case 1:
                x2 = sub.x;
                y2 = sub.y - 1;
                break;
            case 2:
                x2 = sub.x + 1;
                y2 = sub.y;
                break;
            case 3:
                x2 = sub.x;
                y2 = sub.y + 1;
                break;
            case 4:
                x2 = sub.x - 1;
                y2 = sub.y;
                break;
            default:
                x2 = sub.x;
                y2 = sub.y;
                break;
        }
        Bluck temp = bluck[x1][y1];
        bluck[x1][y1] = bluck[x2][y2];
        bluck[x2][y2] = temp;
    }
	public Bluck RandomBluck() {
        Random r = new Random();
        return new Bluck(ColorList[r.nextInt(ColorList.length)]);
    }
}
