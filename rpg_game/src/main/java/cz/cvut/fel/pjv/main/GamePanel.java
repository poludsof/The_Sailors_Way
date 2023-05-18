package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Entity;
import cz.cvut.fel.pjv.entity.Player;
import cz.cvut.fel.pjv.enemy.Boss;
import cz.cvut.fel.pjv.object.GameObjects;
import cz.cvut.fel.pjv.object.House;
import cz.cvut.fel.pjv.tile.TileManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

public class GamePanel extends JPanel {
    public State state;

    // SIZE OF THE TILES
    private final int originalTileSize = 20; // 20 x 20 character

    public final int tileSize = originalTileSize * 4; // // 20 * 4 = 80 --> 80 x 80, just to fit the GamePanel size

    // SCREEN OPTIONS
    private final int colScreen = 14;
    private final int rowScreen = 10;
    public final int screenWidth = tileSize * colScreen; // 1120 pixels
    public final int screenHeight = tileSize * rowScreen; // 800 pixels
    public final int worldCol = 100,  worldRow = 100; // size of the map (in tiles)

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

    /**
     Initializes the game by setting default values for the player, objects, pirates, boss and sound.
     Sets the state of the game to TITLE. Called e.g. when player restarts a game.
     */
    public void setupGame() {
        state = State.TITLE;
        player.setDefaultValues(filenamePlayer);
        objPlacer.PlaceObject(filenameObjects);
        objPlacer.PlacePirate(filenamePirates);
        if (boss == null)  // When the boss dies, it must be recreated to start a new game.
            boss = new Boss(this);
        boss.setDefaultValues(filenameBoss);
        sound.setMusic(3);
    }

    /**
     Starts the game by creating a Timer with a delay of 16 milliseconds
     to update the game state and repaint the graphics.
     */
    public void startGame() {
        Timer timer = new Timer(16, (e) -> {
            update();  // Functions are called every 16 milliseconds -> about 60 times per second (fps == 60).
            repaint();
        });
        timer.start();
    }

    /**
     Loads the game, loads the player, objects, pirates and boss
     based on the downloaded files, and sets the game state to "GAME".
     */
    public void loadGame() {
        player.setDefaultValues(filenameLoadPlayer);
        objPlacer.PlaceObject(filenameLoadObjects);
        objPlacer.PlacePirate(filenameLoadPirates);
        boss.setDefaultValues(filenameLoadBoss);
        state = State.GAME;
    }

    /**
     Updates the game by calling the update method for the player, pirates and boss consequently.
     */
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

    /**
     This method overrides the paintComponent method of JPanel
     and is responsible for painting the components of the game
     and releases the resources occupied by the Graphics2D object after use.
     Depending on the state of the game, it draws different menus, objects, and buttons.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Clear the panel's surface.

        // Creates a Graphics2D object from the Graphics object.
        // Provides additional functionality for drawing 2D graphics and images on the panel.
        Graphics2D g2 = (Graphics2D) g;

        if (state == State.LOAD)
            menu.drawLoad(g2, this);  // Show a menu with a choice: start a new game/load a previous game.

        if (state == State.RULES)
            menu.drawRulesButton(g2, this);

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

            // Drawing special objects on the map.
            ship.draw(g2, this);
            house.draw(g2, this);

            // While the game is in progress, the player is being drawn.
            if (state != State.HAPPY_END)
                player.draw(g2);
            if (state == State.HAPPY_END)
                menu.drawHappyEnd(g2, this);  // Draw the final panel.

            // While the boss is not dead, the boss picture is being drawn.
            if (boss != null)
                boss.draw(g2, 2);

            // Draw each pirate.
            for (Entity pirate : pirates) {
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

        // Releasing resources occupied by g2
        g2.dispose();
    }

    /**
     The method is called to display player's level on the screen.
     It loads necessary fonts and images, and displays the current level number.
     */
    private void drawLevels(Graphics2D g2, GamePanel gp) {
        // Setting the font for text.
        final Font Bruno;
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Bruno.ttf");
            assert is != null;
            Bruno = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        // Draw images on the screen.
        g2.drawImage(ImageManager.back_level, 260, 30, gp.tileSize, gp.tileSize,null);
        g2.drawImage(ImageManager.background, 18, 30, gp.tileSize * 3, gp.tileSize,null);
        g2.drawImage(ImageManager.hat, 260, -26, gp.tileSize, gp.tileSize,null);

        // Displaying text on the screen.
        g2.setFont(Bruno);
        g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 47F));
        g2.drawString("LEVEL: ", 30, 87);
        g2.drawString(Integer.toString(gp.player.level), 285, 87);
    }

    /**
     The method is called to display the player's health in the form of 3 hearts on the screen.
     */
    public void drawHearts(Graphics2D g2, GamePanel gp) {

        int heartX = 25; // Initial positions for starting to draw the player's health.
        int heartY = 115;

        // Draw empty hearts of health.
        for (int i = 0; i < 3; ++i) {
            g2.drawImage(ImageManager.empty_heart, heartX, heartY, gp.tileSize, gp.tileSize, null);
            heartX += gp.tileSize + 10;
        } heartX = 25;

        // Filling hearts with health.
        int count = 0;
        while (count < gp.player.heart_count) {
            // Draw one half of the heart.
            g2.drawImage(ImageManager.half_heart, heartX, heartY, gp.tileSize, gp.tileSize, null);
            ++count;
            if (count < gp.player.heart_count) {  // Draw the other half of the heart.
                g2.drawImage(ImageManager.full_heart, heartX, heartY, gp.tileSize, gp.tileSize, null);
            }
            ++count;
            heartX += gp.tileSize + 10;
        }

    }

    /**
     Displays a map image on the game panel.
     @throws RuntimeException if the map image file was not found or cannot be read.
     */
    private void showMap(Graphics2D g2, GamePanel gp) {
        // Draw image on the screen.
        g2.drawImage(ImageManager.open_map, 350, 150, gp.tileSize * 6, gp.tileSize * 6, null);
    }

    /**
     Loads the player's data and writes it into a JSON file.
     @throws RuntimeException if there is an IOException while writing to the file.
     */
    public void loadPlayerData() {
        JSONObject playerDetails = new JSONObject();
        playerDetails.put("worldX", player.worldX / tileSize);
        playerDetails.put("worldY", player.worldY / tileSize);
        playerDetails.put("speed", player.speed);
        playerDetails.put("heart_count", player.heart_count);
        playerDetails.put("key_count", player.key_count);
        playerDetails.put("rum_count", player.rum_count);
        playerDetails.put("map_count", player.map_count);
        playerDetails.put("sword_count",player.sword_count);
        playerDetails.put("level", player.level);
        playerDetails.put("dead_pirate_count", player.dead_pirate_count);
        playerDetails.put("max_health", player.max_health);
        try (FileWriter file = new FileWriter(filenameLoadPlayer)) {
            file.write(playerDetails.toJSONString());
            file.flush();
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     Loads object data and writes it as a JSON file.
     The coordinates of each object and its type (name) are saved.
     */
    public void loadObjectData() {
        JSONArray arrObj = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        for (int i = 10; i < objArray.length; ++i) {
            if (objArray[i] != null) {
                JSONObject obj = new JSONObject();  // Create a JSON object for each item.
                JSONArray arrItems = new JSONArray();

                arrItems.add(objArray[i].worldX / tileSize);  // Store coordinates on the map in the JSON array.
                arrItems.add(objArray[i].worldY / tileSize);
                obj.put("coordinates", arrItems);

                if (objArray[i].name_object.equals("Key")) obj.put("Name", "Key");
                if (objArray[i].name_object.equals("Map")) obj.put("Name", "Map");
                if (objArray[i].name_object.equals("Sword")) obj.put("Name", "Sword");
                if (objArray[i].name_object.equals("Heart")) obj.put("Name", "Heart");
                if (objArray[i].name_object.equals("BlackKey")) obj.put("Name", "BlackKey");
                if (objArray[i].name_object.equals("Rum")) obj.put("Name", "Rum");

                arrObj.add(obj);  // Add an object with its coordinates and name to the array.
            }
        }
        jsonObj.put("Objects", arrObj);
        try (FileWriter file = new FileWriter(filenameLoadObjects)) {
            file.write(jsonObj.toJSONString());
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     Saves pirate data to a JSON file.
     */
    public void loadPirateData() {
        JSONArray arrObj = new JSONArray();
        JSONObject jsonObj = new JSONObject();

        for (Entity pirate : pirates) {
            if (pirate != null) {
                JSONObject obj = new JSONObject();  // Create a JSON object for each pirate.
                JSONArray arrPirates = new JSONArray();

                arrPirates.add(pirate.worldX / tileSize);  // Store coordinates in the JSON array.
                arrPirates.add(pirate.worldY / tileSize);

                obj.put("coordinates", arrPirates);  // Store coordinates and health in the JSON object.
                obj.put("health", pirate.heart_count);

                arrObj.add(obj);  // All the JSON objects (pirate) are added to a JSON array.
            }
        }
        jsonObj.put("Pirates", arrObj);
        try (FileWriter file = new FileWriter(filenameLoadPirates)) {
            file.write(jsonObj.toJSONString());
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     Loads boss data and writes it to a file in JSON format.
     @throws RuntimeException if an error occurs while writing the file.
     */
    public void loadBossData() {
        JSONObject bossDetails = new JSONObject();
        bossDetails.put("worldX", boss.worldX / tileSize);
        bossDetails.put("worldY", boss.worldY / tileSize);
        bossDetails.put("speed", boss.speed);
        bossDetails.put("heart_count", boss.heart_count);

        try (FileWriter file = new FileWriter(filenameLoadBoss)) {
            file.write(bossDetails.toJSONString());
            file.flush();
        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
