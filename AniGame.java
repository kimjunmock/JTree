package aniboom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class AniGame {
	private boolean isRunning;
	AniFrame frame;
	static Graphics2D art;
	private Image imgBuffer;
	AniBluck anibluck;
	private AniTrans anitrans;
	private boolean canSwitch = true;
	static ArrayList<Integer> filescore = new ArrayList<Integer>() ;
	String line  = "";
	String kk = "";
	String save = "";
	BufferedWriter writer = null;
	public AniGame() {

		frame = new AniFrame("aniboom", new Dimension(1200, 800));        
		isRunning = true;
		frame.setVisible(true);

		imgBuffer = frame.createImage(frame.getWidth(),frame.getHeight());
		anibluck = new AniBluck(12, 12);
	} 

	public void run()  throws Exception{
		BufferedReader reader = new BufferedReader(new FileReader("word.txt"));
		StringTokenizer tokenizer = null;
		while(true) {
			line = reader.readLine();

			if (line==null) break;

			tokenizer = new StringTokenizer(line," ");

		}
		reader.close();

		int tk = tokenizer.countTokens();
		for(int i = 0;i<tk;i++){
			kk = tokenizer.nextToken();
			filescore.add((Integer.parseInt(kk)));
		}
		System.out.print(filescore);
		Collections.sort(filescore);
		Collections.reverse(filescore);
		while(isRunning) {
			AniTrans mouseCoords = anibluck.getCoordsAt(frame.mouseX, frame.mouseY);

			if(mouseCoords != null) {
				if(anitrans == null) {
					if(frame.isClicking()) {
						anitrans = mouseCoords;
					}
				} else if(frame.isClicking() && !mouseCoords.equals(anitrans) && canSwitch) {
					frame.setTitle("" + AniMove.animoving(anitrans, mouseCoords));
					anibluck.switchbluck(anitrans, AniMove.animoving(anitrans, mouseCoords));
					canSwitch = false;
					anitrans = mouseCoords;
				}
			}
			if(anitrans != null && !frame.isClicking()) {
				anitrans = null;
			}
			if(!frame.isClicking() && !canSwitch) {
				canSwitch = true;
			}
			draw();
			
			if(AniBluck.moveCount ==0){

				filescore.add(AniBluck.score);
				HashSet<Integer> hs = new HashSet<Integer>();
				hs.addAll(filescore); 
				filescore.clear(); 
				filescore.addAll(hs); 
				System.out.print(filescore);
				for(int i = 0;i<filescore.size();i++){
					save += Integer.toString(filescore.get(i))+" ";
				}

				Object stringArray[] = { "다시하기 ", "닫기 " };
				int s = JOptionPane.showOptionDialog(frame, "당신의 점수는 : "+AniBluck.score, "종료 ",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray,
						stringArray[0]);

				
				writer = new BufferedWriter(new FileWriter("word.txt"));
				writer.write(save);
				writer.flush();
				writer.close();

				if(s ==JOptionPane.NO_OPTION){
					System.exit(1);
				}
				if(s == JOptionPane.YES_OPTION){
					Collections.sort(filescore);
					Collections.reverse(filescore);
					AniBluck.score = 0;
					AniBluck.moveCount = 30;
					AniBluck.first = true;

					for(int i = 2; i < AniBluck.sizeX-2; i++) {
						for(int j = 2; j < AniBluck.sizeY-2; j++) {
							AniBluck.bluck[i][j] =	AniBluck.randombluck();
						}
					}

				}
			}
		}       
}


	private void draw() {
		art = (Graphics2D) imgBuffer.getGraphics();
		art.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		art.setColor(Color.white);
		art.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		anibluck.draw(frame, art);

		art = (Graphics2D) frame.getGraphics();
		art.drawImage(imgBuffer, 0, 0,frame.getWidth(),frame.getHeight(),null);      
	}

}