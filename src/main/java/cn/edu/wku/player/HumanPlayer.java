package cn.edu.wku.player;

import cn.edu.wku.util.Move;
import cn.edu.wku.viewer.ChessViewer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class HumanPlayer extends Player implements MouseListener {

    public HumanPlayer(boolean isBlack, String name, ChessViewer viewer) {
        super(isBlack, name);
        // Listen to mouse events on the viewer
        viewer.addMouseListener(this);
    }

    @Override
    public void sendMove(Move lastMove) {

    }

    @Override
    public Move getMove(Move lastMove) {
        return null;
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
