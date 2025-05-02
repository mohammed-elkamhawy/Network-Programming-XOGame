package network;

/**
 *
 * @author mohammed-elkamhawy
 * @author ahamed-ashrf
 * @author mohamed-hezema
 * @author omar-ashba
 */

import java.io.*;
import java.net.*;

public class GameServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000, 2, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("Server started on localhost... Waiting for players...");

            Socket player1 = serverSocket.accept();
            System.out.println("Player 1 connected");

            Socket player2 = serverSocket.accept();
            System.out.println("Player 2 connected");

            // Assign symbols to clients
            PrintWriter out1 = new PrintWriter(player1.getOutputStream(), true);
            PrintWriter out2 = new PrintWriter(player2.getOutputStream(), true);
            out1.println("SYMBOL:X");
            out2.println("SYMBOL:O");

            // Create threads to relay messages
            Thread t1 = new Thread(new ClientHandler(player1, player2));
            Thread t2 = new Thread(new ClientHandler(player2, player1));
            t1.start();
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;
    private Socket opponentSocket;
    private BufferedReader in;
    private PrintWriter opponentOut;

    public ClientHandler(Socket socket, Socket opponentSocket) {
        this.socket = socket;
        this.opponentSocket = opponentSocket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            opponentOut = new PrintWriter(opponentSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                opponentOut.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
