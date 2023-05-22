package cn.edu.wku;

import java.io.IOException;

public class Host {

    public static void main(String[] args) throws IOException {
//        new ConsoleController(new ConsolePlayer(true, "Player 1"), new ConsolePlayer(false, "Player 2"));
//        GameStarter.startLocalGame("Player 1", "Player 2");
        GameStarter.startNetWorkGameHOST("Host Player");
    }

}
