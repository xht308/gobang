package cn.edu.wku.player;

import cn.edu.wku.util.Move;

/**
 * @author Andy
 *
 * This class defines the standard behavior of the players (not necessarily a human).
 */
public abstract class Player {

    // The color of chess pieces that the player is using
    // true  - black
    // false - white
    private boolean color;
    // Name used by the player in the game
    private String name;

    // Set up the basic information about the player
    public Player(boolean isBlack, String name) {
        this.color = isBlack;
        this.name = name;
    }

    // Send a move to the other player but not require response
    //      Used when ending a game
    public abstract void sendMove(Move lastMove);

    // Ask the user to make a move
    //      At the same time inform user the last move
    public abstract Move getMove(Move lastMove);

    // Get whether color used by the user
    public boolean getColor() {
        return color;
    }

    // Get the name of the user
    public String getName() {
        return name;
    }

    public abstract boolean isReady();
}