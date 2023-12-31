package cz.cvut.fel.pjv.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JFrame;

/**
 The main entry point for the game.
 It creates a window, sets up the game panel, and starts the game.
 */
public class Main {
    public static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");

        // Creating a window
        JFrame window = new JFrame();

        // Window setting
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("The Sailor's Way");

        // Creating a game panel.
        GamePanel gamePanel = new GamePanel();

        // Adding a game panel to the window.
        window.add(gamePanel);

        // Set the window to the center of the screen and make the window visible..
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGame();
    }
}