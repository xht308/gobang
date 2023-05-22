package cn.edu.wku.controller;

import cn.edu.wku.player.Player;
import cn.edu.wku.util.Move;

import java.util.Scanner;

/**
 * NOTICE: This Player implementation has no error checking! TESTING USE ONLY!
 */
public class ConsolePlayer extends Player {

    // Shared Console Scanner
    private static Scanner consoleInput;

    public ConsolePlayer(boolean isBlack, String name) {
        super(isBlack, name);
        if (consoleInput == null) consoleInput = new Scanner(System.in);
    }

    @Override
    public void sendMove(Move lastMove) {

    }

    @Override
    public Move getMove(Move lastMove) {
        System.out.print("Enter next move: ");
        return new Move(consoleInput.nextInt(), consoleInput.nextInt());
    }

    @Override
    public boolean isReady() {
        return true;
    }
}
