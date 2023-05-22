package cn.edu.wku;

import cn.edu.wku.controller.ConsoleController;
import cn.edu.wku.controller.ConsolePlayer;
import cn.edu.wku.player.NetworkPlayer;
import cn.edu.wku.util.GamePackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class GameStarter {

    public static void startLocalGame(String name1, String name2) {
        // Randomly select color
        boolean color = Math.random() >= 0.5;
        // Start Game
        new ConsoleController(new ConsolePlayer(color, name1), new ConsolePlayer(color, name2));
    }

    public static void startNetWorkGameHOST(String name) throws IOException {
        // Wait for pair to connect
        ServerSocket serverSocket = new ServerSocket(8083);
        System.out.printf("Server Started at port %d\n", serverSocket.getLocalPort());
        // Get Socket Connection
        Socket socket = serverSocket.accept();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Receive SYN
        String remoteName = GamePackage.getGamePackage(bufferedReader.readLine()).getName();
//        System.out.println("SYN");
        // Determine Color
        boolean color = Math.random() >= 0.5;
        // Send SYN ACK
        bufferedWriter.write(GamePackage.getSYNACK(name, !color).toString());
        bufferedWriter.flush();
//        System.out.println("SYN ACK");
//        System.out.println(color);
        // Start Game
        if (color) new ConsoleController(new ConsolePlayer(true, name), new NetworkPlayer(false, remoteName, false, bufferedReader, bufferedWriter));
        else new ConsoleController(new NetworkPlayer(true, remoteName, true, bufferedReader, bufferedWriter), new ConsolePlayer(false, name));
        // Release IO resources
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
        serverSocket.close();
    }

    public static void startNetworkGameClient(String name, String host, int port) throws IOException {
        // Open Socket
        Socket socket = new Socket(host, port);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // Sent SYN
        bufferedWriter.write(GamePackage.getSYN(name).toString());
        bufferedWriter.flush();
        // Receive SYN ACK
        GamePackage gamePackage = GamePackage.getGamePackage(bufferedReader.readLine());
        String remoteName = gamePackage.getName();
        boolean color = gamePackage.getColor();
//        System.out.println(color);
        // Send BARE ACK if White
        if (!gamePackage.getColor()) {
            bufferedWriter.write(GamePackage.getBareAck().toString());
            bufferedWriter.flush();
            // Start Game
            new ConsoleController(new NetworkPlayer(true, remoteName, true, bufferedReader, bufferedWriter), new ConsolePlayer(false, name));
        }
        else {
            new ConsoleController(new ConsolePlayer(true, name), new NetworkPlayer(false, remoteName, true, bufferedReader, bufferedWriter));
        }
        // Start Game
    }

}
