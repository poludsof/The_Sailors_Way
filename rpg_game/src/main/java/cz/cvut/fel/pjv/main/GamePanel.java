package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.enemy.Boss;
import cz.cvut.fel.pjv.object.GameObjects;
import cz.cvut.fel.pjv.object.House;
import cz.cvut.fel.pjv.object.RDoor;
import cz.cvut.fel.pjv.tile.TileManager;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class GamePanel extends JPanel {
    public enum State {
        TITLE, GAME, HELP, PAUSE, NEXT_HELP_PAGE,
        HAPPY_END, GAME_OVER,
        INVENTORY, LOAD
    }
    public State state;

    // SIZE OF THE TILES
    private final int originalTileSize = 20; // 20 x 20 character
    private final int scale = 4;

    public final int tileSize = originalTileSize * scale; // // 20 * 4 = 80 --> 80 x 80

    // SCREEN OPTIONS
    private final int colScreen = 14;
    private final int rowScreen = 10;
    public int screenWidth = tileSize * colScreen; // 1120 pixels
    public int screenHeight = tileSize * rowScreen; // 800 pixels
    public int worldCol = 100,  worldRow = 100; // size of the map (in tiles) // todo maybe delete

    // HANDLERS
    KeyHandler keyH = new KeyHandler();
    MouseHandler keyM = new MouseHandler(this);

    // CREATING OBJECTS
    TileManager tileM = new TileManager(this);
    public Sound sound = new Sound();
    public CollisionChecker checker = new CollisionChecker(this);
    public MapObjectPlacer objPlacer = new MapObjectPlacer(this);
    public GameObjects[] objArray = new GameObjects[100]; // array for storing game objects in the game.
    public Ship ship = new Ship(this);
    public House house = new House(this);
    public Player player = new Player(this, keyH);
    public Boss boss = new Boss(this);
    public Entity[] pirates = new Entity[20];
    private final TitleMenu menu = new TitleMenu();

    // DEFAULT FILENAMES
    String filenamePlayer = "rpg_game/src/dataJson/new_game.json";
    String filenameObjects = "rpg_game/src/dataJson/new_object_data.json";
    String filenamePirates = "rpg_game/src/dataJson/new_pirate_data.json";
    String filenameBoss = "rpg_game/src/dataJson/new_boss_data.json";
    String filenameLoadPlayer = "rpg_game/src/dataJson/load_game.json";
    String filenameLoadObjects = "rpg_game/src/dataJson/load_object_data.json";
    String filenameLoadPirates = "rpg_game/src/dataJson/load_pirate_data.json";
    String filenameLoadBoss = "rpg_game/src/dataJson/load_boss_data.json";


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));  // Panel size in pixels.
        this.setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyM);
        this.setFocusable(true);
    }

    public void setupGame() {
        System.out.println("new");
        player.setDefaultValues(filenamePlayer);
        objPlacer.PlaceObject(filenameObjects);
        objPlacer.PlacePirate(filenamePirates);
        boss.setDefaultValues(filenameBoss);
        state = State.TITLE;
        sound.setMusic(3);
    }

    public void startGame() {
        Timer timer = new Timer(16, (e) -> {
            update();  // Functions are called every 16 milliseconds -> about 60 times per second (fps == 60).
            repaint();
        });
        timer.start();
    }

    public void restart() {
        player.setDefaultValues(filenameLoadPlayer);
        objPlacer.PlaceObject(filenameLoadObjects);
        objPlacer.PlacePirate(filenameLoadPirates);
        boss.setDefaultValues(filenameLoadBoss); // todo error with update boss
        state = GamePanel.State.GAME;
    }

    private void update() {
        if (state == State.GAME) {
            player.update();
            for (Entity pirate : pirates) {
                if (pirate != null)
                    pirate.update();
            }
            if (boss != null)
                boss.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Clear the panel's surface.
        Graphics2D g2 = (Graphics2D) g;  // Create a Graphics2D object from the Graphics object.
                            // to provides additional functionality for drawing 2D graphics and images on the panel.

        if (state == State.LOAD)
            menu.drawLoad(g2, this);  // Show a menu with the choice: start a new game/load a previous game.

        if (state == State.HELP)
            menu.drawHelpButton(g2, this);

        if (state == State.NEXT_HELP_PAGE)
            menu.drawNextPage(g2, this);

        if (state == State.TITLE)
            menu.show(g2, this);  // Menu with buttons: Play/Rules/Quit

        if (state == State.GAME || state == State.PAUSE || state == State.GAME_OVER || state == State.HAPPY_END || state == State.INVENTORY) {
            tileM.draw(g2);  // Drawing the map of the tiles.

            for (GameObjects obj : objArray) {  // Drawing each object that is on the map.
                if (obj != null)
                    obj.draw(g2, this);
            }

            ship.draw(g2, this);  // Drawing special objects on the map.
            house.draw(g2, this);

            if (state != State.HAPPY_END)  // While the game is in progress, the player is being drawn.
                player.draw(g2);
            else menu.drawHappyEnd(g2, this);  // Draw the final panel.

            if (boss != null)  // While the boss is not dead, the boss picture is being drawn.
                boss.draw(g2, 2);

            for (Entity pirate : pirates) {  // Draw each pirate.
                if (pirate != null)
                    pirate.draw(g2, 1);
            }

            if (state == State.GAME_OVER)
                menu.drawGameOver(g2, this);

            if (state == State.PAUSE) {
                menu.drawPauseScreen(g2, this);
            } else {  // Control buttons on the right side.
                menu.showPauseButton(g2, this);
                menu.showChestButton(g2, this);
                menu.showLoadButton(g2, this);
            }

            if (state == State.INVENTORY)
                Inventory.drawInventory(g2, this);

            if (keyH.mapButton && player.map_count >= 1)
                showMap(g2, this);

            // Draw player parameters.
            drawHearts(g2, this);
            drawLevels(g2, this);
        }

        g2.dispose(); // Releasing resources occupied by g2
    }

    private void drawLevels(Graphics2D g2, GamePanel gp) {
        final Font Bruno;
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Bruno.ttf");
            assert is != null;
            Bruno = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        BufferedImage hat, back_level, background;
        try {
            hat = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("level/hat_sailor.png")));
            back_level = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("level/back_level.png")));
            background = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("level/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.drawImage(back_level, 260, 30, gp.tileSize, gp.tileSize,null);
        g2.drawImage(background, 18, 30, gp.tileSize * 3, gp.tileSize,null);
        g2.drawImage(hat, 260, -26, gp.tileSize, gp.tileSize,null);

        g2.setFont(Bruno);
        g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 47F));
        g2.drawString("LEVEL: ", 30, 87);
        g2.drawString(Integer.toString(gp.player.level), 285, 87);
    }

    public void drawHearts(Graphics2D g2, GamePanel gp) {
        BufferedImage full_heart, half_heart, empty_heart;
        try {
            empty_heart = ImageIO.read(Objects.requireNonNull(RDoor.class.getClassLoader().getResourceAsStream("heart/empty_heart.png")));
            half_heart = ImageIO.read(Objects.requireNonNull(RDoor.class.getClassLoader().getResourceAsStream("heart/half_heart.png")));
            full_heart = ImageIO.read(Objects.requireNonNull(RDoor.class.getClassLoader().getResourceAsStream("heart/full_heart.png")));
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
            open_map = ImageIO.read(Objects.requireNonNull(RDoor.class.getClassLoader().getResourceAsStream("objects/open_map.jpg")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(open_map, 350, 150, gp.tileSize * 6, gp.tileSize * 6, null);
    }

    public void loadPlayerData() {
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
        playerDetails.put("dead_pirate_count", Integer.toString(player.dead_pirate_count));
        playerDetails.put("max_health", Integer.toString(player.max_health));
        try (FileWriter file = new FileWriter(filenameLoadPlayer)) {
            file.write(playerDetails.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadObjectData() {
        System.out.println("save obj");
        JSONArray big_obj = new JSONArray();
        JSONObject bigger_obj = new JSONObject();

        for (int i = 10; i < objArray.length; ++i) {
            if (objArray[i] != null) {
                System.out.println("obj num: " + i);

                JSONObject obj = new JSONObject();
                JSONArray subjects = new JSONArray();
                subjects.add(objArray[i].worldX / tileSize);
                subjects.add(objArray[i].worldY / tileSize);
                obj.put("coordinates", subjects);
                if (objArray[i].name_object.equals("Key")) obj.put("Name", "Key");
                if (objArray[i].name_object.equals("Map")) obj.put("Name", "Map");
                if (objArray[i].name_object.equals("Sword")) obj.put("Name", "Sword");
                if (objArray[i].name_object.equals("Heart")) obj.put("Name", "Heart");
                if (objArray[i].name_object.equals("BlackKey")) obj.put("Name", "BlackKey");
                if (objArray[i].name_object.equals("Rum")) obj.put("Name", "Rum");

                big_obj.add(obj);
            }
        }
        bigger_obj.put("Array", big_obj);
        try (FileWriter file = new FileWriter(filenameLoadObjects)) {
            file.write(bigger_obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPirateData() {
        JSONArray big_obj = new JSONArray();
        JSONObject bigger_obj = new JSONObject();

        for (Entity pirate : pirates) {
            if (pirate != null) {
                JSONObject obj = new JSONObject();
                JSONArray subjects = new JSONArray();
                subjects.add(pirate.worldX / tileSize);
                subjects.add(pirate.worldY / tileSize);
                obj.put("coordinates", subjects);
                obj.put("health", pirate.heart_count);

                big_obj.add(obj);
            }
        }
        bigger_obj.put("Pirates", big_obj);
        try (FileWriter file = new FileWriter(filenameLoadPirates)) {
            file.write(bigger_obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBossData() {
        JSONObject bossDetails = new JSONObject();
        bossDetails.put("worldX", Integer.toString(boss.worldX / tileSize));
        bossDetails.put("worldY", Integer.toString(boss.worldY / tileSize));
        bossDetails.put("speed", Integer.toString(boss.speed));
        bossDetails.put("heart_count", Integer.toString(boss.heart_count));

        try (FileWriter file = new FileWriter(filenameLoadBoss)) {
            file.write(bossDetails.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
