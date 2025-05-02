package network;

import viewmodel.GameViewModel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class GameClient extends JFrame {
    private GameViewModel viewModel = new GameViewModel(); // Handles the game logic in the view
    private JButton[][] buttons = new JButton[3][3]; // 3x3 grid of buttons for the GUI
    private BufferedReader in; // To read data from the server
    private PrintWriter out; // To send data to the server
    private char mySymbol; // Stores the player's symbol (X or O)
    private boolean myTurn; // Keeps track of whose turn it is

    // Constructor to initialize the client and GUI
    public GameClient() {
        setTitle("XO Multiplayer");
        setLayout(new GridLayout(3, 3));
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initBoard(); // Initializes the GUI board

        try {
            // Connect to server on localhost
            Socket socket = new Socket("127.0.0.1", 5000);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Get the player's symbol (X or O) from the server
            String symbolLine = in.readLine();
            if (symbolLine != null && symbolLine.startsWith("SYMBOL:")) {
                mySymbol = symbolLine.charAt(symbolLine.length() - 1);
                myTurn = (mySymbol == 'X'); // X always starts
                viewModel.setCurrentPlayer('X');
                JOptionPane.showMessageDialog(this, "You are player " + mySymbol);
            } else {
                throw new IOException("Symbol not received from server.");
            }

            // Thread to listen for server messages
            new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        String[] parts = line.split(",");
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);

                        viewModel.makeMove(row, col);
                        updateButtons(); // Updates the buttons based on the board

                        if (viewModel.getWinner() != '-') {
                            highlightWinningCells(viewModel.getWinner()); // Highlight the winning cells
                            showResultDialog(viewModel.getWinner() + " wins!");
                        } else if (viewModel.isDraw()) {
                            showResultDialog("Draw!");
                        }

                        viewModel.switchPlayer(); // Switch turns
                        myTurn = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Connection failed");
            e.printStackTrace();
        }

        setVisible(true);
    }

    // Initializes the game board with buttons
    private void initBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("-");
                buttons[i][j] = btn;
                final int row = i, col = j;
                btn.setFont(new Font("Arial", Font.BOLD, 40));

                // Action listener for each button
                btn.addActionListener(e -> {
                    if (!myTurn || !btn.getText().equals("-")) return; // Do nothing if it's not the player's turn or button is clicked

                    if (viewModel.makeMove(row, col)) {
                        btn.setText(String.valueOf(mySymbol)); // Update button text to player's symbol
                        out.println(row + "," + col); // Send the move to the server

                        // Check for winner or draw
                        if (viewModel.getWinner() != '-') {
                            highlightWinningCells(viewModel.getWinner()); // Highlight the winning cells
                            showResultDialog(viewModel.getWinner() + " wins!");
                        } else if (viewModel.isDraw()) {
                            showResultDialog("Draw!");
                        }

                        viewModel.switchPlayer(); // Switch turns
                        myTurn = false; // Update turn status
                    }
                });

                add(btn); // Add button to the layout
            }
    }

    // Updates the GUI buttons based on the current board state
    private void updateButtons() {
        char[][] board = viewModel.getBoard();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                buttons[i][j].setText(String.valueOf(board[i][j])); // Update button labels
    }

    // Highlights the winning cells with green color
    private void highlightWinningCells(char winner) {
        char[][] board = viewModel.getBoard();
        for (int i = 0; i < 3; i++) {
            // Check for horizontal win
            if (board[i][0] == winner && board[i][1] == winner && board[i][2] == winner) {
                buttons[i][0].setBackground(Color.GREEN);
                buttons[i][1].setBackground(Color.GREEN);
                buttons[i][2].setBackground(Color.GREEN);
            }
            // Check for vertical win
            if (board[0][i] == winner && board[1][i] == winner && board[2][i] == winner) {
                buttons[0][i].setBackground(Color.GREEN);
                buttons[1][i].setBackground(Color.GREEN);
                buttons[2][i].setBackground(Color.GREEN);
            }
        }
        // Check for diagonal win
        if (board[0][0] == winner && board[1][1] == winner && board[2][2] == winner) {
            buttons[0][0].setBackground(Color.GREEN);
            buttons[1][1].setBackground(Color.GREEN);
            buttons[2][2].setBackground(Color.GREEN);
        }
        if (board[0][2] == winner && board[1][1] == winner && board[2][0] == winner) {
            buttons[0][2].setBackground(Color.GREEN);
            buttons[1][1].setBackground(Color.GREEN);
            buttons[2][0].setBackground(Color.GREEN);
        }
    }

    // Shows a dialog with the result of the game (win or draw)
    private void showResultDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
        viewModel.resetGame(); // Resets the game model
        resetButtons(); // Resets the GUI buttons
    }

    // Resets the GUI buttons to their default state
    private void resetButtons() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("-");
                buttons[i][j].setEnabled(true); // Enable buttons for a new game
                buttons[i][j].setBackground(null); // Reset background color
            }
    }
}
