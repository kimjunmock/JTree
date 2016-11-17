package aniboom;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AniGame implements Runnable, KeyListener, WindowListener {
    private boolean isRunning;
    private boolean isDone;
    private AniFrame frame;
    private Image imgBuffer;

    private AniBluck anibluck;
    private AniTrans anitrans;
    private boolean canSwitch = true;

    private Color bgColor = new Color(255,255,255);
    public AniGame() {
        frame = new AniFrame("Ani boom ", new Dimension(800, 600));
        frame.addKeyListener(this);
        frame.addWindowListener(this);
        isRunning = true;
        isDone = false;
        frame.setVisible(true);
        imgBuffer = frame.createImage(frame.getWidth(), frame.getHeight());
        anibluck = new AniBluck(8, 8);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.setVisible(false);
        isRunning = false;
        frame.dispose();
        isDone = true;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        while(true) {
            if(isDone)
                System.exit(0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void run() {
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
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void draw() {
        Graphics2D art = (Graphics2D) imgBuffer.getGraphics();
        art.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        art.setColor(bgColor);
        art.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        anibluck.draw(frame, art);
        art = (Graphics2D) frame.getGraphics();
        art.drawImage(imgBuffer, 0, 0, frame.getWidth(), frame.getHeight(), null);
        art.dispose();
       
    }
}
