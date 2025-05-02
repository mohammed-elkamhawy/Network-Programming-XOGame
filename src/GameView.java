package view;

import viewmodel.GameViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameView extends JFrame {
    private GameViewModel viewModel = new GameViewModel();
    private JButton[][] buttons = new JButton[3][3];

    public GameView() {
        setTitle("XO Single Player");
        setSize(400, 400);
        setLayout(new GridLayout(3, 3));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initBoard();

        setVisible(true);
    }

    private void initBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton btn = new JButton("-");
                btn.setFont(new Font("Arial", Font.BOLD, 40));
                buttons[row][col] = btn;

                final int r = row, c = col;

                btn.addActionListener(e -> {
                    if (btn.getText().equals("-") && viewModel.makeMove(r, c)) {
                        btn.setText(String.valueOf(viewModel.getCurrentPlayer()));

                        if (viewModel.getWinner() != '-') {
                            highlightWinningCells(viewModel.getWinner());
                            showResultDialog(viewModel.getWinner() + " wins!");
                        } else if (viewModel.isDraw()) {
                            showResultDialog("Draw!");
                        } else {
                            viewModel.switchPlayer();
                        }
                    }
                });

                add(btn);
            }
        }
    }

    private void highlightWinningCells(char winner) {
        char[][] board = viewModel.getBoard();
        List<Point> winningCells = new ArrayList<>();

        // Rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == winner && board[i][1] == winner && board[i][2] == winner) {
                winningCells.add(new Point(i, 0));
                winningCells.add(new Point(i, 1));
                winningCells.add(new Point(i, 2));
            }
        }

        // Columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == winner && board[1][i] == winner && board[2][i] == winner) {
                winningCells.add(new Point(0, i));
                winningCells.add(new Point(1, i));
                winningCells.add(new Point(2, i));
            }
        }

        // Diagonals
        if (board[0][0] == winner && board[1][1] == winner && board[2][2] == winner) {
            winningCells.add(new Point(0, 0));
            winningCells.add(new Point(1, 1));
            winningCells.add(new Point(2, 2));
        }
        if (board[0][2] == winner && board[1][1] == winner && board[2][0] == winner) {
            winningCells.add(new Point(0, 2));
            winningCells.add(new Point(1, 1));
            winningCells.add(new Point(2, 0));
        }

        for (Point p : winningCells) {
            buttons[p.x][p.y].setBackground(Color.GREEN);
        }
    }

    private void showResultDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
        viewModel.resetGame();
        resetButtons();
    }

    private void resetButtons() {
        for (JButton[] row : buttons)
            for (JButton btn : row) {
                btn.setText("-");
                btn.setBackground(null);
            }
    }
}
