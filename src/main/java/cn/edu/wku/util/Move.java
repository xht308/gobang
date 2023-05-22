package cn.edu.wku.util;

/**
 * This class represents a move made by the player.
 * Then it will be used by the game controller to actually make the move.
 */
public class Move {

    private final int rowNum;
    private final int colNum;

    public Move(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Move)) return false;
        Move move = (Move)obj;
        return move.getRowNum() == rowNum && move.getColNum() == colNum;
    }

}
