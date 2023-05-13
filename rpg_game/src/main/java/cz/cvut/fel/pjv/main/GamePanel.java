package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.monster.Boss;
import cz.cvut.fel.pjv.object.GameObjects;
import cz.cvut.fel.pjv.object.House;
import cz.cvut.fel.pjv.object.RDoor;
import cz.cvut.fel.pjv.tile.TileManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel implements Runnable{
    public static enum State {
        TITLE,
        GAME,
        HELP,
        PAUSE,
        NEXT_HELP_PAGE,
        HAPPY_END,
        GAME_OVER,
        INVENTORY,
        LOAD
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
    public GameObjects[] obj_arr = new GameObjects[100];
    private Inventory inventory = new Inventory();
    public Boat boat = new Boat(this);
    public House house = new House(this);
    public Player player = new Player(this, keyH);
    public Boss boss = new Boss(this);
    public Entity[] pirates = new Entity[20];
    private final TitleMenu menu = new TitleMenu(this);
    public String jsonfile = "";

    public GamePanel() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyM);
        this.setFocusable(true);
    }

    public void setupGame() {
        ASetter.PlaceObject("new_object_data.json");
        ASetter.PlacePirate("new_pirate_data.json");
        state = State.TITLE;
        playMusic(3);
        //sound.setMusic(3);
    }

    public void startGameTread() {
        gameThread = new Thread(this); // passing GamePanel class to this thread's constructor
        gameThread.start(); // automatically call run() method
//        run();
    }

    public void restart() {
        player.setDefaultValues(jsonfile);
        ASetter.PlaceObject("load_object_data.json");
        ASetter.PlacePirate("load_pirate_data.json");
        boss.setDefaultValues();
        state = GamePanel.State.GAME;
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
    private void update() {

        if (state == State.GAME) {
            player.update();
            for (Entity monster : pirates) {
                if (monster != null)
                    monster.update();
            }
            if (boss != null)
                boss.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (state == State.LOAD)
            menu.drawLoad(g, this);

        if (state == State.TITLE) {
            menu.show(g, this);

        } else if (state == State.GAME || state == State.PAUSE || state == State.GAME_OVER || state == State.HAPPY_END || state == State.INVENTORY) {
            tileM.draw(g2);
            for (GameObjects obj : obj_arr) {
                if (obj != null) {
                    obj.draw(g2, this);
                }
            }

            boat.draw(g2, this);
            house.draw(g2, this);
            if (state != State.HAPPY_END)
                player.draw(g2);
            if (boss != null)
                boss.draw(g2, 2);

            for (Entity monster : pirates) {
                if (monster != null)
                    monster.draw(g2, 1);
            }

            if (state == State.HAPPY_END) {
                menu.drawHappyEnd(g, this);
            }

            if (state == State.GAME_OVER) {
                menu.drawGameOver(g, this);
            }

            if (state == State.PAUSE) {
                menu.drawPauseScreen(g, this);
            } else {
                menu.showPauseButton(g2, this);
                menu.showChestButton(g2, this);
                menu.showLoadButton(g2, this);
            }

            if (state == State.INVENTORY) {
                inventory.drawInventory(g2, this);
            }

            if (keyH.mapButton && player.map_count >= 1) {
                showMap(g2, this);
            }

            drawHearts(this, g2);
            drawLevels(g, this);

        }
        else if (state == State.HELP) {
            menu.drawHelpButton(g, this);
        }
        else if (state == State.NEXT_HELP_PAGE) {
            menu.drawNextPage(g, this);
        }

        g2.dispose();
    }

    private void drawLevels(Graphics g, GamePanel gp) {
        final Font Bruno;
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Bruno.ttf");
            Bruno = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        BufferedImage hat, back_level, background;
        try {
            hat = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("level/hat_sailor.png"));
            back_level = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("level/back_level.png"));
            background = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("level/background.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(back_level, 260, 30, gp.tileSize, gp.tileSize,null);
        g2.drawImage(background, 18, 30, gp.tileSize * 3, gp.tileSize,null);
        g2.drawImage(hat, 260, -26, gp.tileSize, gp.tileSize,null);

        g.setFont(Bruno);
        g.setColor(Color.BLACK);
        g.setFont(g2.getFont().deriveFont(Font.BOLD, 47F));
        g.drawString("LEVEL: ", 30, 87);
        g.drawString(Integer.toString(gp.player.level), 285, 87);
    }

    public void drawHearts(GamePanel gp, Graphics2D g2) {
        BufferedImage full_heart, half_heart, empty_heart;
        try {
            empty_heart = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("heart/empty_heart.png"));
            half_heart = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("heart/half_heart.png"));
            full_heart = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("heart/full_heart.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int heartX = 25;
        int heartY = 115;
        int count = 0;

        for (int i = 0; i < 3; ++i) {
            g2.drawImage(empty_heart, heartX, heartY, gp.tileSize, gp.tileSize, null);
            heartX += gp.tileSize + 10;
        } heartX = 25;

        while (count < gp.player.heart_count) {
            g2.drawImage(half_heart, heartX, heartY, gp.tileSize, gp.tileSize, null);
            ++count;
            if (count < gp.player.heart_count) {
                g2.drawImage(full_heart, heartX, heartY, gp.tileSize, gp.tileSize, null);
            }
            ++count;
            heartX += gp.tileSize + 10;
        }

    }

    private void showMap(Graphics2D g2, GamePanel gp) {
        BufferedImage open_map;
        try {
            open_map = ImageIO.read(RDoor.class.getClassLoader().getResourceAsStream("objects/open_map.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.drawImage(open_map, 350, 150, gp.tileSize * 6, gp.tileSize * 6, null);

    }

    public void playMusic(int idx) {
        sound.setMusic(idx);
    }

    public void stopMusic() {
        sound.pauseTrack();
    }

    public void loadCurrentData() {
        JSONObject playerDetails = new JSONObject();
        playerDetails.put("worldX", Integer.toString(player.worldX / tileSize));
        playerDetails.put("worldY", Integer.toString(player.worldY / tileSize));
        playerDetails.put("speed", Integer.toString(player.speed));
        playerDetails.put("heart_count", Integer.toString(player.heart_count));
        playerDetails.put("key_count", Integer.toString(player.key_count));
        playerDetails.put("rum_count", Integer.toString(player.rum_count));
        playerDetails.put("map_count", Integer.toString(player.map_count));
        playerDetails.put("sword_count", Integer.toString(player.sword_count));
        playerDetails.put("level", Integer.toString(player.level));

        try (FileWriter file = new FileWriter("load_game.json")) {
            file.write(playerDetails.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadObjectData() {
        JSONArray big_obj = new JSONArray();
        JSONObject bigger_obj = new JSONObject();

        for (int i = 10; i < obj_arr.length; ++i) {
            if (obj_arr[i] != null) {
                JSONObject obj = new JSONObject();
                JSONArray subjects = new JSONArray();
                subjects.add(obj_arr[i].worldX / tileSize);
                subjects.add(obj_arr[i].worldY / tileSize);
                obj.put("coordinates", subjects);
                if (obj_arr[i].name_object.equals("Key")) obj.put("Name", "Key");
                if (obj_arr[i].name_object.equals("Map")) obj.put("Name", "Map");
                if (obj_arr[i].name_object.equals("Sword")) obj.put("Name", "Sword");
                if (obj_arr[i].name_object.equals("Heart")) obj.put("Name", "Heart");
                if (obj_arr[i].name_object.equals("BlackKey")) obj.put("Name", "BlackKey");
                if (obj_arr[i].name_object.equals("Rum")) obj.put("Name", "Rum");

                big_obj.add(obj);
            }
        }
        bigger_obj.put("Array", big_obj);
        try (FileWriter file = new FileWriter("load_object_data.json")) {
            file.write(bigger_obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPirateData() {
        JSONArray big_obj = new JSONArray();
        JSONObject bigger_obj = new JSONObject();

        for (int i = 0; i < pirates.length; ++i) {
            if (pirates[i] != null) {
                JSONObject obj = new JSONObject();
                JSONArray subjects = new JSONArray();
                subjects.add(pirates[i].worldX / tileSize);
                subjects.add(pirates[i].worldY / tileSize);
                obj.put("coordinates", subjects);
                obj.put("health", pirates[i].heart_count);

                big_obj.add(obj);
            }
        }
        bigger_obj.put("Pirates", big_obj);
        try (FileWriter file = new FileWriter("load_pirate_data.json")) {
            file.write(bigger_obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
