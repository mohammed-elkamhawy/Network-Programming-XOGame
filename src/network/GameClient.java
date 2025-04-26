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

import viewmodel.GameViewModel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class GameClient extends JFrame {
    private GameViewModel viewModel = new GameViewModel();
    private JButton[][] buttons = new JButton[3][3];
    private BufferedReader in;
    private PrintWriter out;
    private char mySymbol;
    private boolean myTurn;

    public GameClient(String serverAddress) {
        setTitle(" XO Multiplayer");
        setLayout(new GridLayout(3, 3));
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initBoard();

        try {
            Socket socket = new Socket(serverAddress, 5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // تحديد رمز اللاعب عشوائيًا
            mySymbol = JOptionPane.showConfirmDialog(this, "Do you Want To Start...?", "Choose..", JOptionPane.YES_NO_OPTION) == 0 ? 'X' : 'O';
            myTurn = mySymbol == 'X';
            viewModel.setCurrentPlayer('X');

            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        String[] parts = line.split(",");
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);

                        viewModel.makeMove(row, col);
                        updateButtons();

                        if (viewModel.getWinner() != '-') {
                            showResultDialog(viewModel.getWinner() + " wins!");
                        } else if (viewModel.isDraw()) {
                            showResultDialog("Draw!");
                        }

                        viewModel.switchPlayer();
                        myTurn = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "فشل الاتصال بالسيرفر");
            e.printStackTrace();
        }

        setVisible(true);
    }

    private void initBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("-");
                buttons[i][j] = btn;
                final int row = i, col = j;
                btn.setFont(new Font("Arial", Font.BOLD, 40));

                btn.addActionListener(e -> {
                    if (!myTurn || !btn.getText().equals("-")) return;

                    if (viewModel.makeMove(row, col)) {
                        btn.setText(String.valueOf(mySymbol));
                        out.println(row + "," + col);

                        if (viewModel.getWinner() != '-') {
                            showResultDialog(viewModel.getWinner() + " wins!");
                        } else if (viewModel.isDraw()) {
                            showResultDialog("Draw!");
                        }

                        viewModel.switchPlayer();
                        myTurn = false;
                    }
                });

                add(btn);
            }
    }

    private void updateButtons() {
        char[][] board = viewModel.getBoard();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setText(String.valueOf(board[i][j]));
    }

    private void showResultDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
        viewModel.resetGame();
        resetButtons();
    }

    private void resetButtons() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("-");
                buttons[i][j].setEnabled(true);
            }
    }
}
