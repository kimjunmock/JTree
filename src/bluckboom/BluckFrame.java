package bluckboom;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class BluckFrame  extends Frame implements MouseMotionListener, MouseListener, MouseWheelListener{
	int mouseX;
    int mouseY;
    int mouseButton;
    int move;
    boolean clicked;
    boolean clicking;
    
    public BluckFrame(String title, Dimension size) {
        setTitle(title);
        setSize(size);
        addMouseMotionListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
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
        clicked = true;
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
        move = arg0.getWheelRotation();
    }

    public boolean hasClicked() {
        return clicked;
    }
    public void setHasClicked(boolean b) {
       clicked = b;
    }

    public boolean isClicking() {
        return clicking;
    }

    public int getMouseButton() {
        return mouseButton;
    }
}


