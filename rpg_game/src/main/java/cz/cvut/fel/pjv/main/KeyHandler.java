package cz.cvut.fel.pjv.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed; // Variables to track key presses.
    @Override
    public void keyTyped(KeyEvent k) {

    }

    @Override
    public void keyPressed(KeyEvent k) { // Override the keyPressed method of the KeyListener interface.
        int key = k.getKeyCode();

        // Detect the pressed key and set the appropriate variable.
        if (key == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (key == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (key == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (key == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (key == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent k) { // Override the keyReleased method of the KeyListener interface.
        int key = k.getKeyCode();

        // Detect the released key and set the corresponding variable.
        if (key == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (key == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (key == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (key == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (key == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }
}
