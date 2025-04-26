/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started... Waiting for players...");

            Socket player1 = serverSocket.accept();
            System.out.println("Player 1 connected");

            Socket player2 = serverSocket.accept();
            System.out.println("Player 2 connected");

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
    private PrintWriter out;
    private PrintWriter opponentOut;

    public ClientHandler(Socket socket, Socket opponentSocket) {
        this.socket = socket;
        this.opponentSocket = opponentSocket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
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
