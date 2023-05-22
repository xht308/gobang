package cn.edu.wku.controller;

import cn.edu.wku.player.Player;
import cn.edu.wku.util.ChessBoard;
import cn.edu.wku.util.Move;

public abstract class GameController {

    private ChessBoard chessBoard = new ChessBoard();
    private Player blackPlayer;
    private Player whitePlayer;
//    private ArrayList<Move> history = new ArrayList<>();

    public GameController(Player blackPlayer, Player whitePlayer) {
        //Initialize Players
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        setMessage("Wait players to get ready");
        // Block the game until both players are ready
        waitReady();
        // Start Game
        Player winner = gobang();
        displayResult(winner);
    }

    // Block the controller until both players are ready
    private void waitReady() {
        while (!(blackPlayer.isReady() && whitePlayer.isReady()));
    }

    protected abstract void setMessage(String message);

    protected abstract void displayMove(Move move, boolean color);

    protected abstract void displayResult(Player winner);

    private Player gobang() {
        Player winner = null;
        Move lastMove = null;
        while (true) {
            // Display black turn
            setMessage(String.format("Wait black player (%s)", blackPlayer.getName()));
            // Get Black Move
            lastMove = blackPlayer.getMove(lastMove);
            // Drop Black Move
            chessBoard.dropChess(lastMove, ChessBoard.BLACK_CHESS);
            // Display Black Move
            displayMove(lastMove, ChessBoard.BLACK_CHESS);
            // Check Win
            if (chessBoard.checkWin()) {
                winner = blackPlayer;
                // Send last move to white player
                whitePlayer.sendMove(lastMove);
                // End the Game
                break;
            }
            // Display black turn
            setMessage(String.format("Wait white player (%s)", whitePlayer.getName()));
            // Get White Move
            lastMove = whitePlayer.getMove(lastMove);
            // Drop White Move
            chessBoard.dropChess(lastMove, ChessBoard.WHITE_CHESS);
            //Display White Move
            displayMove(lastMove,  ChessBoard.WHITE_CHESS);
            // Check Win
            if (chessBoard.checkWin()) {
                winner = whitePlayer;
                // Send last move to black player
                blackPlayer.sendMove(lastMove);
                // End the game
                break;
            }
        }
        return winner;
    }

}
