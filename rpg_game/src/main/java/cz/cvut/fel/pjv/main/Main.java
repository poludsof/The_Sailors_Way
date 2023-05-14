package cz.cvut.fel.pjv.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JFrame;
import java.io.IOException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("This is an INFO level log message!");
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
        gamePanel.startGameTread();
    }
}