package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.object.Objects;
import cz.cvut.fel.pjv.tile.TileManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    public static enum State {
        TITLE,
        GAME,
        HELP
    }
    public State state;

    final int originalTileSize = 20; // 20 x 20 character
    final int scale = 4;

    public final int tileSize = originalTileSize * scale; // // 20 * 4 = 80 --> 80 x 80
    public int screen_col = 14;
    public int screen_row = 10;

    // Size of game screen
    public int screen_width = tileSize * screen_col; // 768 pixels
    public int screen_height = tileSize * screen_row; // 576 pixels

    public int worldCol = 100;
    public int worldRow = 100;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    MouseHandler keyM = new MouseHandler(this);
    Sound sound = new Sound();
    Thread gameThread;
    public CollisionChecker checker = new CollisionChecker(this);
    public PlaceOnTheMap ASetter = new PlaceOnTheMap(this);
    public Objects[] obj_arr = new Objects[10];
    public Boat boat = new Boat();
    public Player player = new Player(this, keyH);
    private final TitleMenu menu = new TitleMenu(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyM);
        this.setFocusable(true);
    }

    public void setupGame() {
        ASetter.PlaceObject();
        state = State.TITLE;
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
//                System.out.println("FPS:" + drawCount);
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
        if (state == State.TITLE) {
            menu.show(g, this);
        } else if (state == State.GAME) {
            tileM.draw(g2);
            for (Objects obj : obj_arr) {
                if (obj != null) {
                    obj.draw(g2, this);
                }
            }
            boat.draw(g2, this);

            player.draw(g2);
            g2.dispose();
        }
    }

    public void playMusic(int idx) {
        sound.setMusic(idx);
    }
}
