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

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    int player_x = 100;
    int player_y = 100;
    int player_speed = 4;


    public GamePanel() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.cyan);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameTread() {
        gameThread = new Thread(this); // passing GamePanel class to this thread's constructor
        gameThread.start(); // automatically call run() method
    }

    double drawInterval = 1000000000 / (double) FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;
    @Override
    public void run() { // GAME LOOP == core of game
        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                --delta;
                ++drawCount;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {
        if (keyH.upPressed)
            player_y -= player_speed;
        else if (keyH.downPressed)
            player_y += player_speed;
        else if (keyH.rightPressed)
            player_x += player_speed;
        else if (keyH.leftPressed)
            player_x -= player_speed;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.blue);

        g2.fillRect(player_x, player_y, tileSize, tileSize);

        g2.dispose();

    }
}
