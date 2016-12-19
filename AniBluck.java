package aniboom;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import static java.awt.Color.*;
import java.util.Random;
import javax.imageio.ImageIO;


import static aniboom.AniMove.*;

public class AniBluck {
	public static int sizeX;
	public static int sizeY;
	private static int boxWidth;
	private static int center;
	private final static int minWidth = 14;
	private static BufferedImage image;
	static boolean first = true;
	private static String angle;
	public static AniBluckSub[][] bluck;
	public static int score = 0;
	public static int moveCount = 30;
	public static boolean play = false;
	public static AniBluckSub empty = new AniBluckSub(Color.orange);
	private final static Color[] allowedColors = new Color[]{yellow, gray, green, darkGray, magenta, blue, pink}; //black  =  제일밑에가서 가로세로 폭파.
	
	public AniBluck(int sizex1, int sizey1) {

		sizeX = sizex1;
		sizeY = sizey1;
		bluck = new AniBluckSub[sizeX][sizeY];

		for(int v=0;v<12;v++){
			for(int w = 0;w<12;w++){
				bluck[v][w] = new AniBluckSub(red);
			}
		}
		for(int i = 2; i < sizeX-2; i++) {
			for(int j = 2; j < sizeY-2; j++) {
				bluck[i][j] =	randombluck();
			}
		}
	}
	public void draw(AniFrame frame, Graphics2D art) {
		File input = new File("poto.PNG");
		try{
			image = ImageIO.read(input);
		}catch(IOException e){
			e.printStackTrace();
		}

		boxWidth = getBoxWidth(frame.getWidth(), frame.getHeight() );
		center = getcenter(frame.getWidth(), frame.getHeight() );      

		art.setColor(lightGray);
		art.fillRect(1000, 0, 200, 100);
		art.setColor(BLACK);
		art.drawRect(1000, 0, 200, 100);
		art.drawString("남은 이동 횟수",1070,40);
		art.drawString(Integer.toString(moveCount),1080,60);
		art.setColor(BLACK);
		art.drawString("랜덤 십자 폭탄 ", 1090, 300);
		art.drawRect(1050,220,150,150);
		art.setColor(lightGray);
		art.fillRect(1001,101,200,100);
		art.setColor(black);
		art.drawRect(1000, 101, 200, 100);
		art.drawString("점수 ", 1070, 140);
		art.drawString(Integer.toString(score), 1080, 160);
		art.drawImage(image, 75, 0, 975, 800, frame);
		art.drawString("새 게임 ",30,150);
		art.drawString("리셋 ",30,350);
		art.drawString("종료 ",30,550);
		art.drawString("최고점수(1~5등) ", 1070, 400);
		art.drawString("1등 : "+AniGame.filescore.get(0),1070,430);
		art.drawString("2등 : "+AniGame.filescore.get(1),1070,460);
		art.drawString("3등 : "+AniGame.filescore.get(2),1070,490);
		art.drawString("4등 : "+AniGame.filescore.get(3),1070,520);
		art.drawString("5등 : "+AniGame.filescore.get(4),1070,550);
		art.drawRect((angle.equals("horizontal") ? 0 : center)+120, 
				(angle.equals("vertical") ? 0 : center)+120,boxWidth * sizeX-240,boxWidth * sizeY-240); //버티컬 수직 호라이즌 수
		for(int i = 2; i < sizeX-2; i++) {
			for(int j = 2; j < sizeY-2; j++) {
				if(i == 2) {
					art.setColor(black);
					art.drawLine(angle.equals("horizontal") ? 0 : (center+120),
							(angle.equals("vertical") ? 0 : center) + boxWidth * (j),
							(angle.equals("horizontal") ? 0 : center) + boxWidth * sizeX-120,
							(angle.equals("vertical") ? 0 : center) + boxWidth * (j));
				}
				bluck[i][j].draw(art,
						(angle.equals("horizontal") ? 0 : center) + boxWidth * i,
						(angle.equals("vertical") ? 0 : center) + boxWidth * j, boxWidth);

			}
			art.setColor(black);
			art.drawLine((angle.equals("horizontal") ? 0 : center) + boxWidth * (i),
					(angle.equals("vertical") ? 0 : (center))+120,
					(angle.equals("horizontal") ? 0 : center) + boxWidth * (i),
					((angle.equals("vertical") ? 0 : center) + boxWidth * sizeY)-120);
		}

		change();
		orangeswap();

	}
	private static int getBoxWidth(int frameX, int frameY) {
		if((frameX / sizeX) * sizeY < frameY) {
			angle = "horizontal";
			return frameX / sizeX < minWidth ? minWidth : frameX / sizeX;
		} else {
			angle = "vertical";
			return frameY / sizeY < minWidth ? minWidth : frameY / sizeY;
		}
	}
	private static int getcenter(int frameX, int frameY) {
		int absoluteCenter;
		switch(angle) {
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
		if(angle == null)
			return null;
		if(angle.equals("horizontal")) {
			int x1 = x / boxWidth;
			int y1 = (y - center) / boxWidth;
			if(x1 >= sizeX || x1 < 0 || y1 >= sizeY || y1 < 0)
				return null;
			return new AniTrans(x1, y1);
		} else {
			int x1 = (x - center) / boxWidth;
			int y1 = y / boxWidth;
			if(x1 >= sizeX || x1 < 0 || y1 >= sizeY || y1 < 0)
				return null;
			return new AniTrans(x1, y1);
		}
	}
	public void switchbluck(AniTrans moving, int direction) {
		int x1 = moving.x, y1 = moving.y;
		int x2, y2;
		switch(direction) {
		case UP:
			x2 = moving.x;
			y2 = moving.y - 1;
			break;
		case RIGHT:
			x2 = moving.x + 1;
			y2 = moving.y;
			break;
		case DOWN:
			x2 = moving.x;
			y2 = moving.y + 1;
			break;
		case LEFT:
			x2 = moving.x - 1;
			y2 = moving.y;
			break;
		default:
			x2 = moving.x;
			y2 = moving.y;
			break;
		}
		first = false;
		if(x1<11&&x1>1&&y1>1&&y1<11){
			moveCount--;
			AniBluckSub temp = bluck[x1][y1];
			bluck[x1][y1] = bluck[x2][y2];
			bluck[x2][y2] = temp;
			fchange(x1,y1);
			fchange(x2,y2);
			change();

			if(play == false){
				temp = bluck[x1][y1];
				bluck[x1][y1] = bluck[x2][y2];
				bluck[x2][y2] = temp;
				moveCount++;
			}
			orangeswap();
		}
	}
	public static AniBluckSub randombluck() {
		Random r = new Random();
		return new AniBluckSub(allowedColors[r.nextInt(allowedColors.length)]);
	}
	public void fchange(int i,int j){
		play = false;

		
		String center = bluck[i][j].color.toString(),west1= bluck[i-1][j].color.toString(),west2= bluck[i-2][j].color.toString();
		String east1= bluck[i+1][j].color.toString(),east2= bluck[i+2][j].color.toString();
		String up1= bluck[i][j-1].color.toString(),up2= bluck[i][j-2].color.toString();
		String down1= bluck[i][j+1].color.toString(),down2= bluck[i][j+2].color.toString();
		//5블록이 맞을 경우 
		if(center.equals(west2)&&center.equals(west1)&&center.equals(down1)&&center.equals(down2)){
			bluck[i-2][j] = empty;
			bluck[i-1][j] = empty; 
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			bluck[i][j+2] = empty;
			play = true;
			fiveboom(i,j);
		}
		else if(center.equals(west2)&&center.equals(west1)&&center.equals(up1)&&center.equals(up2)){
			bluck[i-2][j] = empty;
			bluck[i-1][j] = empty; 
			bluck[i][j] = empty;
			bluck[i][j-1] = empty;
			bluck[i][j-2] = empty;
			play = true;
			fiveboom(i,j);
		}
		else if(center.equals(east2)&&center.equals(east1)&&center.equals(down1)&&center.equals(down2)){
			bluck[i+2][j] = empty;
			bluck[i+1][j] = empty; 
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			bluck[i][j+2] = empty;
			play = true;
			fiveboom(i,j);
		}
		else if(center.equals(east2)&&center.equals(east1)&&center.equals(up1)&&center.equals(up2)){
			bluck[i+2][j] = empty;
			bluck[i+1][j] = empty; 
			bluck[i][j] = empty;
			bluck[i][j-1] = empty;
			bluck[i][j-2] = empty;
			play = true;
			fiveboom(i,j);
		}
		else if(center.equals(east2)&&center.equals(east1)&&center.equals(west1)&&center.equals(west2)){
			bluck[i-2][j] = empty;
			bluck[i-1][j] = empty; 
			bluck[i][j] = empty;
			bluck[i+1][j] = empty;
			bluck[i+2][j] = empty;
			play = true;
			fiveboom(i,j);
		}
		else if(center.equals(down2)&&center.equals(down1)&&center.equals(up1)&&center.equals(up2)){
			bluck[i][j-2] = empty;
			bluck[i][j-1] = empty; 
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			bluck[i][j+2] = empty;
			play = true;
			fiveboom(i,j);
		}
		else if(center.equals(down2)&&center.equals(down1)&&center.equals(west1)&&center.equals(east1)){
			bluck[i+1][j] = empty;
			bluck[i-1][j] = empty; 
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			bluck[i][j+2] = empty;
			play = true;
			fiveboom(i,j);
		}
		else if(center.equals(east1)&&center.equals(west1)&&center.equals(up1)&&center.equals(up2)){
			bluck[i+1][j] = empty;
			bluck[i-1][j] = empty; 
			bluck[i][j] = empty;
			bluck[i][j-1] = empty;
			bluck[i][j-2] = empty;
			play = true;
			fiveboom(i,j);

		}
		else if(center.equals(up1)&&center.equals(down1)&&center.equals(west1)&&center.equals(west2)){
			bluck[i-2][j] = empty;
			bluck[i-1][j] = empty; 
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			bluck[i][j-1] = empty;
			play = true;
			fiveboom(i,j);
		}
		else if(center.equals(east1)&&center.equals(east2)&&center.equals(up1)&&center.equals(down1)){
			bluck[i+1][j] = empty;
			bluck[i+2][j] = empty; 
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			bluck[i][j-1] = empty;
			play = true;
			fiveboom(i,j);
		}
		//4블록이 맞을경우 
		if(center.equals(east2)&&center.equals(east1)&&center.equals(west1)){
			bluck[i-1][j] = empty; 
			bluck[i][j] = empty;
			bluck[i+1][j] = empty;
			bluck[i+2][j] = empty;
			play = true;
			fourheightboom(i,j);
		}
		else if(center.equals(east1)&&center.equals(west1)&&center.equals(west2)){
			bluck[i-2][j] = empty; 
			bluck[i-1][j] = empty;
			bluck[i][j] = empty;
			bluck[i+1][j] = empty;
			play = true;
			fourheightboom(i,j);
		}
		else if(center.equals(up2)&&center.equals(up1)&&center.equals(down1)){
			bluck[i][j-2] = empty; 
			bluck[i][j-1] = empty;
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			play = true;
			fourwidthboom(i,j);
		}
		else if(center.equals(up1)&&center.equals(down1)&&center.equals(down2)){
			bluck[i][j-1] = empty; 
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			bluck[i][j+2] = empty;
			play = true;
			fourwidthboom(i,j);
		}
		//3블록이 맞을경우 
		if(center.equals(west1)&&center.equals(east1)){
			bluck[i][j] = empty; 
			bluck[i-1][j] = empty;
			bluck[i+1][j] = empty;
			play = true;

		}
		else if(center.equals(up1)&&center.equals(down1)){
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			bluck[i][j-1] = empty;
			play = true;

		}
		else if(center.equals(west2)&&center.equals(west1)){
			bluck[i][j] = empty;
			bluck[i-1][j] = empty;
			bluck[i-2][j] = empty;
			play = true;

		}
		else if(center.equals(east1)&&center.equals(east2)){
			bluck[i][j] = empty;
			bluck[i+1][j] = empty;
			bluck[i+2][j] = empty;
			play = true;

		}
		else if(center.equals(down2)&&center.equals(down1)){
			bluck[i][j] = empty;
			bluck[i][j+1] = empty;
			bluck[i][j+2] = empty;
			play = true;

		}
		else if(center.equals(up1)&&center.equals(up2)){
			bluck[i][j] = empty;
			bluck[i][j-1] = empty;
			bluck[i][j-2] = empty;
			play = true;

		}

	}

	public static void change(){

		play = false;
		

		for(int i=2;i<sizeX-2;i++){
			for(int j=2;j<sizeY-2;j++){
				String center = bluck[i][j].color.toString(),west1= bluck[i-1][j].color.toString(),west2= bluck[i-2][j].color.toString();
				String east1= bluck[i+1][j].color.toString(),east2= bluck[i+2][j].color.toString();
				String up1= bluck[i][j-1].color.toString(),up2= bluck[i][j-2].color.toString();
				String down1= bluck[i][j+1].color.toString(),down2= bluck[i][j+2].color.toString();
				//5블록이 맞을 경우 
				if(center.equals(west2)&&center.equals(west1)&&center.equals(down1)&&center.equals(down2)){
					bluck[i-2][j] = empty;
					bluck[i-1][j] = empty; 
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					bluck[i][j+2] = empty;
					play = true;

				}
				else if(center.equals(west2)&&center.equals(west1)&&center.equals(up1)&&center.equals(up2)){
					bluck[i-2][j] = empty;
					bluck[i-1][j] = empty; 
					bluck[i][j] = empty;
					bluck[i][j-1] = empty;
					bluck[i][j-2] = empty;
					play = true;

				}
				else if(center.equals(east2)&&center.equals(east1)&&center.equals(down1)&&center.equals(down2)){
					bluck[i+2][j] = empty;
					bluck[i+1][j] = empty; 
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					bluck[i][j+2] = empty;
					play = true;

				}
				else if(center.equals(east2)&&center.equals(east1)&&center.equals(up1)&&center.equals(up2)){
					bluck[i+2][j] = empty;
					bluck[i+1][j] = empty; 
					bluck[i][j] = empty;
					bluck[i][j-1] = empty;
					bluck[i][j-2] = empty;
					play = true;

				}
				else if(center.equals(east2)&&center.equals(east1)&&center.equals(west1)&&center.equals(west2)){
					bluck[i-2][j] = empty;
					bluck[i-1][j] = empty; 
					bluck[i][j] = empty;
					bluck[i+1][j] = empty;
					bluck[i+2][j] = empty;
					play = true;

				}
				else if(center.equals(down2)&&center.equals(down1)&&center.equals(up1)&&center.equals(up2)){
					bluck[i][j-2] = empty;
					bluck[i][j-1] = empty; 
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					bluck[i][j+2] = empty;
					play = true;

				}
				else if(center.equals(down2)&&center.equals(down1)&&center.equals(west1)&&center.equals(east1)){
					bluck[i+1][j] = empty;
					bluck[i-1][j] = empty; 
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					bluck[i][j+2] = empty;
					play = true;

				}
				else if(center.equals(east1)&&center.equals(west1)&&center.equals(up1)&&center.equals(up2)){
					bluck[i+1][j] = empty;
					bluck[i-1][j] = empty; 
					bluck[i][j] = empty;
					bluck[i][j-1] = empty;
					bluck[i][j-2] = empty;
					play = true;

				}
				else if(center.equals(up1)&&center.equals(down1)&&center.equals(west1)&&center.equals(west2)){
					bluck[i-2][j] = empty;
					bluck[i-1][j] = empty; 
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					bluck[i][j-1] = empty;
					play = true;

				}
				else if(center.equals(east1)&&center.equals(east2)&&center.equals(up1)&&center.equals(down1)){
					bluck[i+1][j] = empty;
					bluck[i+2][j] = empty; 
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					bluck[i][j-1] = empty;
					play = true;

				}
				//4블록이 맞을경우 
				else if(center.equals(east2)&&center.equals(east1)&&center.equals(west1)){
					bluck[i-1][j] = empty; 
					bluck[i][j] = empty;
					bluck[i+1][j] = empty;
					bluck[i+2][j] = empty;
					play = true;

				}
				else if(center.equals(east1)&&center.equals(west1)&&center.equals(west2)){
					bluck[i-2][j] = empty; 
					bluck[i-1][j] = empty;
					bluck[i][j] = empty;
					bluck[i+1][j] = empty;
					play = true;

				}
				else if(center.equals(up2)&&center.equals(up1)&&center.equals(down1)){
					bluck[i][j-2] = empty; 
					bluck[i][j-1] = empty;
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					play = true;

				}
				else if(center.equals(up1)&&center.equals(down1)&&center.equals(down2)){
					bluck[i][j-1] = empty; 
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					bluck[i][j+2] = empty;
					play = true;

				}
				//3블록이 맞을경우 
				if(center.equals(west1)&&center.equals(east1)){
					bluck[i][j] = empty; 
					bluck[i-1][j] = empty;
					bluck[i+1][j] = empty;
					play = true;

				}
				else if(center.equals(up1)&&center.equals(down1)){
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					bluck[i][j-1] = empty;
					play = true;

				}
				else if(center.equals(west2)&&center.equals(west1)){
					bluck[i][j] = empty;
					bluck[i-1][j] = empty;
					bluck[i-2][j] = empty;
					play = true;

				}
				else if(center.equals(east1)&&center.equals(east2)){
					bluck[i][j] = empty;
					bluck[i+1][j] = empty;
					bluck[i+2][j] = empty;
					play = true;

				}
				else if(center.equals(down2)&&center.equals(down1)){
					bluck[i][j] = empty;
					bluck[i][j+1] = empty;
					bluck[i][j+2] = empty;
					play = true;

				}
				else if(center.equals(up1)&&center.equals(up2)){
					bluck[i][j] = empty;
					bluck[i][j-1] = empty;
					bluck[i][j-2] = empty;
					play = true;

				}

			}
		}
	}
	public static void fiveboom(int i,int j){
		
		for(int k = 2;k<10;k++){
			bluck[i][k] = empty;
			bluck[k][9] = empty;
		}
	}
	public static void fourwidthboom(int i,int j){
	
		for(int k = 2;k<10;k++){
			bluck[k][j] = empty;
		}
	}
	public static void fourheightboom(int i,int j){
	
		for(int k = 2;k<10;k++){
			bluck[i][k] = empty;
		}
	}
	public static void orangeswap(){
		for(int i = 0;i<8;i++){
			for(int a = 2;a<10;a++){
				if(bluck[a][2].color.equals(Color.orange)){   
					bluck[a][2] = randombluck();
					if(first ==false)
						score+=100;
				}

			}	
			for(int j = 2;j<sizeX-2;j++){
				for(int k = 2;k<sizeY-2;k++){
					if(bluck[j][k].color.equals(Color.orange)){
						AniBluckSub temp = bluck[j][k-1];
						bluck[j][k-1] = bluck[j][k];
						bluck[j][k] = temp;	
					}	
				}
			}
		}
	}

}
