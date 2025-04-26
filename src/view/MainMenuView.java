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
            new GameView(); // تشغل اللعبة الفردية
            dispose();
        });

        multiBtn.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog("Enter Server IP: "); //Local Host Ip
            if (ip != null && !ip.isEmpty()) {
                new GameClient(ip); // عميل اللعب الجماعي
                dispose();
            }
        });

        add(singleBtn);
        add(multiBtn);
        setVisible(true);
    }
}

