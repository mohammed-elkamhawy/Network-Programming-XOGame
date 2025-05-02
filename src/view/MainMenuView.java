package view;

/**
 *
 * @author mohammed-elkamhawy
 * @author ahamed-ashrf
 * @author mohamed-hezema
 * @author omar-ashba
 */

import javax.swing.*;
import java.awt.*;
import network.GameClient;

public class MainMenuView extends JFrame {
    public MainMenuView() {
        setTitle("XO Game - Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1, 10, 10));

        JButton singleBtn = new JButton("Single Player");
        JButton multiBtn = new JButton("Multiplayer");

        singleBtn.setFont(new Font("Arial", Font.BOLD, 16));
        multiBtn.setFont(new Font("Arial", Font.BOLD, 16));

        singleBtn.addActionListener(e -> {
            new GameView(); // Launch single player game
            dispose();
        });

        multiBtn.addActionListener(e -> {
            // No need to enter IP anymore, we are using localhost
            new GameClient(); // Local multiplayer client
            dispose();
        });

        add(singleBtn);
        add(multiBtn);
        setVisible(true);
    }
}
