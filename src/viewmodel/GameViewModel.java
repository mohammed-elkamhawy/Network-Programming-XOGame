/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewmodel;

/**
 *
 * @author mohammed-elkamhawy
 * @author ahamed-ashrf
 * @author mohamed-hezema
 * @author omar-ashba
 */

import model.GameModel;

public class GameViewModel {
    private GameModel model = new GameModel();

    public boolean makeMove(int row, int col) {
        return model.playMove(row, col);
    }

    public char[][] getBoard() {
        return model.getBoard();
    }

    public char getCurrentPlayer() {
        return model.getCurrentPlayer();
    }

    public char getWinner() {
        return model.checkWinner();
    }

    public boolean isDraw() {
        return model.isBoardFull() && model.checkWinner() == '-';
    }

    public void switchPlayer() {
        model.switchPlayer();
    }

    public void resetGame() {
        model.resetBoard();
    }

    public void setCurrentPlayer(char player) {
        model.setCurrentPlayer(player);
    }
}
