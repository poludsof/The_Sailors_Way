package cz.cvut.fel.pjv.main;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        // Creating a window
        JFrame window = new JFrame();

        // Window setting
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PJV");

        // Creating a game panel.
        GamePanel gamePanel = new GamePanel();

        // Adding a game panel to the window.
        window.add(gamePanel);

        // Set the window to the center of the screen and make the window visible..
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameTread();
    }
}