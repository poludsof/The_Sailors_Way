package cz.cvut.fel.pjv.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    // Variables to track key presses.
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;

    public boolean mapButton, rumButton, swordButton;  // Variables to track button presses.

    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    /**
     * Setting attributes according to pressed keys.
     */
    @Override
    public void keyPressed(KeyEvent k) {
        int key = k.getKeyCode();

        // Detect the pressed key and set the appropriate variable.
        if (key == KeyEvent.VK_W) { upPressed = true; }
        if (key == KeyEvent.VK_S) { downPressed = true; }
        if (key == KeyEvent.VK_A) { leftPressed = true; }
        if (key == KeyEvent.VK_D) { rightPressed = true; }
        if (key == KeyEvent.VK_SPACE) { spacePressed = true; }

        if (key == KeyEvent.VK_J) { rumButton = true; }
        if (key == KeyEvent.VK_L) { mapButton = true; }
        if (key == KeyEvent.VK_K) { swordButton = true; }
    }

    /**
     * Setting attributes according to released keys.
     */
    @Override
    public void keyReleased(KeyEvent k) {
        int key = k.getKeyCode();
        // Detect the released key and set the corresponding variable.
        if (key == KeyEvent.VK_W) { upPressed = false; }
        if (key == KeyEvent.VK_S) { downPressed = false; }
        if (key == KeyEvent.VK_A) { leftPressed = false; }
        if (key == KeyEvent.VK_D) { rightPressed = false; }
        if (key == KeyEvent.VK_SPACE) { spacePressed = false; }
        if (key == KeyEvent.VK_L) { mapButton = false; }
    }
}
