package aniboom;

import java.awt.*;
import static java.awt.Color.*;
import java.util.Random;
import static aniboom.AniMove.*;

public class AniBluck {
    private int sizeX, sizeY;
    private int boxWidth, centerPos;
    private final int MIN_BOX_WIDTH = 14;
    private String alignment;
    private AniBluckSub[][] bluck;
    private final Color[] allowedColors = new Color[]{yellow, gray, green, red, black, blue, pink};
    public AniBluck(int sizex1, int sizey1) {
        sizeX = sizex1;
        sizeY = sizey1;
        bluck = new AniBluckSub[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
            	bluck[i][j] =	randombluck();
            }
        }
    }
    public void draw(AniFrame frame, Graphics2D art) {
        boxWidth = getBoxWidth(frame.getWidth(), frame.getHeight() );
        centerPos = getCenterPos(frame.getWidth(), frame.getHeight() );
        art.setColor(black);
        art.drawRect((alignment.equals("horizontal") ? 0 : centerPos),
                (alignment.equals("vertical") ? 0 : centerPos),boxWidth * sizeX,boxWidth * sizeY);
        for(int i = 0; i < sizeX; i++) {
            for(int j = 0; j < sizeY; j++) {
                if(i == 0) {
                    art.setColor(black);
                    art.drawLine(alignment.equals("horizontal") ? 0 : centerPos,
                            (alignment.equals("vertical") ? 0 : centerPos) + boxWidth * (j + 1),
                            (alignment.equals("horizontal") ? 0 : centerPos) + boxWidth * sizeX,
                            (alignment.equals("vertical") ? 0 : centerPos) + boxWidth * (j + 1));
                }
                bluck[i][j].draw(art,
                        (alignment.equals("horizontal") ? 0 : centerPos) + boxWidth * i,
                        (alignment.equals("vertical") ? 0 : centerPos) + boxWidth * j, boxWidth);
            }
            art.setColor(black);
            art.drawLine((alignment.equals("horizontal") ? 0 : centerPos) + boxWidth * (i + 1),
                    alignment.equals("vertical") ? 0 : centerPos,
                    (alignment.equals("horizontal") ? 0 : centerPos) + boxWidth * (i + 1),
                    (alignment.equals("vertical") ? 0 : centerPos) + boxWidth * sizeY);
        }
    }
    private int getBoxWidth(int frameX, int frameY) {
        if((frameX / sizeX) * sizeY < frameY) {
            alignment = "horizontal";
            return frameX / sizeX < MIN_BOX_WIDTH ? MIN_BOX_WIDTH : frameX / sizeX;
        } else {
            alignment = "vertical";
            return frameY / sizeY < MIN_BOX_WIDTH ? MIN_BOX_WIDTH : frameY / sizeY;
        }
    }
    private int getCenterPos(int frameX, int frameY) {
        int absoluteCenter;
        switch(alignment) {
            case "horizontal":
                absoluteCenter = frameY / 2;
                return absoluteCenter - (sizeY / 2 * boxWidth);
            case "vertical":
                absoluteCenter = frameX / 2;
                return absoluteCenter - (sizeX / 2 * boxWidth);
        }
        return 0;
    }
    public AniTrans getCoordsAt(int x, int y) {
        if(alignment == null)
            return null;
        if(alignment.equals("horizontal")) {
            int x1 = x / boxWidth;
            int y1 = (y - centerPos) / boxWidth;
            if(x1 >= sizeX || x1 < 0 || y1 >= sizeY || y1 < 0)
                return null;
            return new AniTrans(x1, y1);
        } else {
            int x1 = (x - centerPos) / boxWidth;
            int y1 = y / boxWidth;
            if(x1 >= sizeX || x1 < 0 || y1 >= sizeY || y1 < 0)
                return null;
            return new AniTrans(x1, y1);
        }
    }
    public void switchbluck(AniTrans coords, int direction) {
        int x1 = coords.x, y1 = coords.y;
        int x2, y2;
        switch(direction) {
            case UP:
                x2 = coords.x;
                y2 = coords.y - 1;
                break;
            case RIGHT:
                x2 = coords.x + 1;
                y2 = coords.y;
                break;
            case DOWN:
                x2 = coords.x;
                y2 = coords.y + 1;
                break;
            case LEFT:
                x2 = coords.x - 1;
                y2 = coords.y;
                break;
            default:
                x2 = coords.x;
                y2 = coords.y;
                break;
        }
        AniBluckSub temp = bluck[x1][y1];
        bluck[x1][y1] = bluck[x2][y2];
        bluck[x2][y2] = temp;
    }
    public AniBluckSub randombluck() {
        Random r = new Random();
        return new AniBluckSub(allowedColors[r.nextInt(allowedColors.length)]);
    }
}
