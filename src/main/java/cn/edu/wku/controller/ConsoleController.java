package cn.edu.wku.controller;

import cn.edu.wku.player.Player;
import cn.edu.wku.util.Move;

public class ConsoleController extends GameController{

    public ConsoleController(Player blackPlayer, Player whitePlayer) {
        super(blackPlayer, whitePlayer);
    }

    @Override
    protected void setMessage(String message) {
        System.out.printf("[Controller] %s%n", message);
    }

    @Override
    protected void displayMove(Move move, boolean color) {
        System.out.printf("%s player made a move on (%d, %d)%n", color?"Black":"White", move.getRowNum(), move.getColNum());
    }

    @Override
    protected void displayResult(Player winner) {
        System.out.printf("Game Ended! The winner is %s!", winner.getName());
    }

}
