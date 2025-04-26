/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

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

public class GameView extends JFrame {
    private GameViewModel viewModel = new GameViewModel();
    private JButton[][] buttons = new JButton[3][3];

    public GameView() {
        setTitle(" XO Single Player");
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

    private void showResultDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
        viewModel.resetGame();
        resetButtons();
    }

    private void resetButtons() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                btn.setText("-");
    }
}
