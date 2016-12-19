package aniboom;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class AniFrame extends JFrame implements MouseMotionListener, MouseListener, MouseWheelListener, ActionListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 6678631299307313964L;
	public int mouseX;
	public int mouseY;
	private int mouseButton;
	public int scrollAmt;
	private boolean hasClicked;
	private boolean clicking;
	JButton button ;
	JButton button2 ;
	JButton button3 ;
	JButton button4 ;
	public AniFrame(String title, Dimension size) {
		setTitle(title);
		setSize(size);

		button = new JButton();
		button.setText("새 게임 ");
		button2 = new JButton();
		button2.setText("리셋");
		button3 = new JButton();
		button3.setText("종료 ");
		button4 = new JButton();
		button4.setText("렌덤 십자 폭탄");
		add(button);
		add(button2);
		add(button3);
		add(button4);
		setLayout(null);
		button.setSize(75, 200);
		button.setLocation(0, 0);
		button2.setSize(75, 200);
		button2.setLocation(0, 200);
		button3.setSize(75, 200);
		button3.setLocation(0, 400);
		button4.setSize(150, 150);
		button4.setLocation(1050,200);
		
		button.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		mouseX = me.getX();
		mouseY = me.getY();
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		mouseX = me.getX();
		mouseY = me.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		hasClicked = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		clicking = true;
		mouseButton = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clicking = false;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		scrollAmt = arg0.getWheelRotation();
	}

	public boolean hasClicked() {
		return hasClicked;
	}

	public void setHasClicked(boolean b) {
		hasClicked = b;
	}

	public boolean isClicking() {
		return clicking;
	}

	public int getMouseButton() {
		return mouseButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == button){
			AniBluck.score = 0;
			AniBluck.moveCount = 30;
			AniBluck.first = true;

			for(int i = 2; i < AniBluck.sizeX-2; i++) {
				for(int j = 2; j < AniBluck.sizeY-2; j++) {
					AniBluck.bluck[i][j] =	AniBluck.randombluck();
				}
			}

		}

		if(e.getSource() == button2){

			System.out.println(AniBluck.score+AniBluck.moveCount);
			AniBluck.first = true;
			for(int i = 2; i < AniBluck.sizeX-2; i++) {
				for(int j = 2; j < AniBluck.sizeY-2; j++) {
					AniBluck.bluck[i][j] =	AniBluck.randombluck();
				}
			}

		}

		if(e.getSource() == button3){
			dispose();
			System.exit(0);
		}
		if(e.getSource() == button4){
			int i = (int)(Math.random()*9)+2;
			int j = (int)(Math.random()*9)+2;
			
			for(int k = 2;k<10;k++){
				AniBluck.bluck[i][k]= AniBluck.empty;
				AniBluck.bluck[k][j] = AniBluck.empty;
			}
			
		}
	}
}
