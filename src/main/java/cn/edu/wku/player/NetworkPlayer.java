package cn.edu.wku.player;

import cn.edu.wku.util.GamePackage;
import cn.edu.wku.util.Move;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class NetworkPlayer extends Player {

    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean ready;

    public NetworkPlayer(boolean isBlack, String name, boolean ready, BufferedReader reader, BufferedWriter writer) {
        super(isBlack, name);
        this.ready = ready;
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void sendMove(Move lastMove) {
        // Send last move to remote
        if (lastMove != null) {
            try {
                writer.write(GamePackage.getACK(lastMove).toString());
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Move getMove(Move lastMove) {
        try {
            // Send last move to remote
            sendMove(lastMove);
            // Get remote move
            GamePackage gamePackage = GamePackage.getGamePackage(reader.readLine());
            // Return remote move
            return gamePackage.getMove();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isReady() {
        if (ready) return true;
        try {
            // Wait #3 Package from remote (BARE_ACK)
            GamePackage gamePackage = GamePackage.getGamePackage(reader.readLine());
            if (gamePackage != GamePackage.BARE_ACK) throw new RuntimeException("Wrong inputting package");
            ready = true;
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
