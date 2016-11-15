package bluckboom;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bluckboom.BluckFrame;
import bluckboom.BluckBluck;
import java.awt.color.*;
public class BluckBoom implements  Runnable, WindowListener{
	BluckFrame frame;
	BluckBluck bluckbluck;
	Image img;
	BluckSub sub;
	boolean Bswitch =true ;
	public void start(){
		 frame = new BluckFrame("애니팡 ", new Dimension(800, 600));
	     
	     frame.addWindowListener(this);
	     
	     frame.setVisible(true);
	     img = frame.createImage(frame.getWidth(), frame.getHeight());
	     bluckbluck= new BluckBluck(8, 8);
	}
    @Override
    public void windowClosing(WindowEvent e) {
        frame.setVisible(false);
        
        frame.dispose();
        
    }

    @Override
    public void windowClosed(WindowEvent e) {
        while(true) {
            System.exit(0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 자동 생성된 메소드 스텁
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 자동 생성된 메소드 스텁
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 자동 생성된 메소드 스텁
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 자동 생성된 메소드 스텁
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO 자동 생성된 메소드 스텁
		
	}
	@Override
	public void run() {
		 while(Bswitch) {
	            BluckSub mouseCoords = bluckbluck.getsub(frame.mouseX, frame.mouseY);
	            if(mouseCoords != null) {
	                if(sub == null) {
	                    if(frame.isClicking()) {
	                        sub = mouseCoords;
	                    }
	                } else if(frame.isClicking() && !mouseCoords.equals(sub) && Bswitch) {
	                    frame.setTitle("" + BluckMove.getblucksub(sub, mouseCoords));
	                    bluckbluck.switchbluck(sub, BluckMove.getblucksub(sub, mouseCoords));
	                    Bswitch = false;
	                    sub = mouseCoords;
	                }
	            }
	            if(sub != null && !frame.isClicking()) {
	                sub = null;
	            }
	            if(!frame.isClicking() && !Bswitch) {
	            	Bswitch = true;
	            }
	            
	           drow();
        try {
            Thread.sleep(10);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
	}

}
	public void drow(){
		Graphics2D art = (Graphics2D) img.getGraphics();
        art.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //그림을 말끔하게 만들어준다.
        art.setColor(Color.BLACK);
        
        art.fillRect( 0	, 0, frame.getWidth(), frame.getHeight());
        BluckBluck.draw(frame,art);
        
        art = (Graphics2D) frame.getGraphics();
        
	}
}
