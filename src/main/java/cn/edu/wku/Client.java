package cn.edu.wku;

import java.io.IOException;

public class Client {

    public static void main(String[] args) throws IOException {
        GameStarter.startNetworkGameClient("Client Player", "localhost", 8083);
    }

}
