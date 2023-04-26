package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.object.Objects;
import cz.cvut.fel.pjv.tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{

    final int originalTileSize = 20; // 20 x 20 character
    final int scale = 4;

    public final int tileSize = originalTileSize * scale; // // 20 * 4 = 80 --> 80 x 80
    public int screen_col = 14;
    public int screen_row = 10;

    // Size of game screen
    public int screen_width = tileSize * screen_col; // 768 pixels
    public int screen_height = tileSize * screen_row; // 576 pixels

    public final int worldCol = 55;
    public final int worldRow = 33;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker checker = new CollisionChecker(this);
    public AssertSetter ASetter = new AssertSetter(this);
    public Player player = new Player(this, keyH);
    public Objects[] obj_arr = new Objects[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        ASetter.setObject();
    }

    public void startGameTread() {
        gameThread = new Thread(this); // passing GamePanel class to this thread's constructor
        gameThread.start(); // automatically call run() method
//        run();
    }

    double drawInterval = 1000000000 / (double) FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;
    @Override
    // the game loop that updates the game and redraws the screen
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
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);

        for (int i = 0; i < obj_arr.length; ++i) {
            if (obj_arr[i] != null) {
                obj_arr[i].draw(g2, this);
            }
        }

        player.draw(g2);
        g2.dispose();
    }
}
