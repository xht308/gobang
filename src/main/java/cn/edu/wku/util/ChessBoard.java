package cn.edu.wku.util;

import java.util.Arrays;

public class ChessBoard {

    public static final boolean BLACK_CHESS = true;
    public static final boolean WHITE_CHESS = true;

    // The standard chess board
    private final int[][] chessBoard = new int[19][19];
    private Move lastMove = null;

    public ChessBoard() {
        for (int[] ints : chessBoard) {
            Arrays.fill(ints, 0);
        }
    }

    // Drop a chess piece
    private void setColor(Move move, boolean color) {
        this.chessBoard[move.getRowNum()][move.getColNum()] = color?2:1;
    }

    // Get the entire chess board
    public int[][] getBoardAsArray() {
        return chessBoard;
    }

    // Check if a place has already been occupied by other chess piece
    public boolean isPlaceOccupied(Move move) {
        return isPlaceOccupied(move.getRowNum(), move.getColNum());
    }

    public boolean isPlaceOccupied(int rowIndex, int colIndex) {
        return chessBoard[rowIndex][colIndex] != 0;
    }

    // Invoked by user when they drop a chess on the board
    public void dropChess(Move move, boolean color) {
        if(!isPlaceOccupied(move)) {
            this.lastMove = move;
            setColor(move, color);
        }
    }

    // Get the color of chess piece on the specified place
    public int getColor(Move move) {
        return chessBoard[move.getRowNum()][move.getColNum()];
    }

    public int getColor(Move move, int rowOffset, int colOffset) {
        return chessBoard[move.getRowNum()+rowOffset][move.getColNum()+colOffset];
    }

    private static boolean inBoard(Move move, int rowOffset, int colOffset) {
        int newRowIndex = move.getRowNum() + rowOffset;
        int newColIndex = move.getColNum() + colOffset;
        return newRowIndex >= 0 && newRowIndex < 19 && newColIndex >= 0 && newColIndex < 19;
    }

    // Check whether the last move make the player win
    public boolean checkWin() {
        Move move = lastMove;
        boolean flag = false;

        int color = getColor(move);
        System.out.println(color);

        //Determine whether there are five pieces connected in the horizontal direction
        int i = 1;
        int countHorizontal = 1;
        while(inBoard(move, i, 0) && color == getColor(move, i, 0)) {
            countHorizontal++;
            i++;
        }
        i = 1;
        while(inBoard(move, -i, 0) && color == getColor(move, -i, 0)) {
            countHorizontal++;
            i++;
        }
        if(countHorizontal >= 5) {
            flag = true;
        }

        //Determine whether there are five pieces connected in the vertical direction
        int j = 1;
        int countVertical = 1;
        while(inBoard(move, 0, j) && color == getColor(move, 0, j)) {
            countVertical++;
            j++;
        }
        j = 1;
        while(inBoard(move, 0, -j) && color == getColor(move, 0, -j)) {
            countVertical++;
            j++;
        }
        if(countVertical >= 5) {
            flag = true;
        }

        //Determine whether there are five pieces connected in the left slant direction
        int m = 1;
        int n = 1;
        int countLeftSlant = 1;
        while(inBoard(move, m, -n) && color == getColor(move, m, -n)) {
            countLeftSlant++;
            m++;
            n++;
        }
        m = 1;
        n = 1;
        while(inBoard(move, -m, n) && color == getColor(move, -m, n)) {
            countLeftSlant++;
            m++;
            n++;
        }
        if(countLeftSlant >= 5) {
            flag = true;
        }

        //Determine whether there are five pieces connected in the left slant direction
        int p = 1;
        int q = 1;
        int countRightSlant = 1;
        while(inBoard(move, p, q) && color == getColor(move, p, q)) {
            countRightSlant++;
            p++;
            q++;
        }
        m = 1;
        n = 1;
        while(inBoard(move, -p, -q) && color == getColor(move, -p, -q)) {
            countRightSlant++;
            p++;
            q++;
        }
        if(countRightSlant >= 5) {
            flag = true;
        }

        return flag;
    }

}
