package cn.edu.wku.viewer;

import cn.edu.wku.util.Move;

public interface Viewer {

    public void displayMove(Move move, boolean color);

    public void displayMessage(String message);

}
