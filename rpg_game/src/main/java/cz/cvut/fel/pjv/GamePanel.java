package cz.cvut.fel.pjv;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 16; // 16 x 16 character
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // // 16 * 3 = 48 --> 48 x 48
    final int screen_col = 16;
    final int screen_row = 12;

    // Size of game screen
    final int screen_width = tileSize * screen_col; // 768 pixels
    final int screen_height = tileSize * screen_row; // 576 pixels

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.cyan);
        this.setDoubleBuffered(true);
    }

    public void startGameTread() {
        gameThread = new Thread(this); // passing GamePanel class to this thread's constructor
        gameThread.start(); // automatically call run() method
    }

    @Override
    public void run() { // GAME LOOP == core of game
        while (gameThread != null) {
            update();
            repaint();
        }
    }
    public void update() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.blue);

        g2.fillRect(100, 100, tileSize, tileSize);

        g2.dispose();

    }
}
