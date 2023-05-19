package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;
import cz.cvut.fel.pjv.main.KeyHandler;
import cz.cvut.fel.pjv.main.State;
import cz.cvut.fel.pjv.object.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents player in the game.
 */
public class Player extends Entity {
    private final int bossIdx = -100;

    GamePanel gp;
    KeyHandler keyH;

    public Dictionary <String, Integer> inventory = new Hashtable<>();

    public int key_count, dead_pirate_count, rum_count, rum_time, map_count, sword_count, level, max_health;

    public boolean rum_time_start = false;

    public int screenX;
    public int screenY;

    /**
     Constructs a Player object.
     @param gp the GamePanel instance
     @param keyH the KeyHandler instance
     */
    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);  // Call the constructor of the superclass (Entity).
        this.gp = gp;
        this.keyH = keyH;

        max_health = 6;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Set the player's solid area
        solidArea = new Rectangle(20, 24, 56, 40);

        getPlayerImage();
    }

    /**
     Sets default values for the player's properties by reading from a JSON file.
     @param filename the name of the JSON file
     */
    public void setDefaultValues(String filename) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(filename))  {

            //Read JSON file
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            worldX = (int) ((long) jsonObject.get("worldX")) * gp.tileSize; // 10
            worldY = (int) ((long) jsonObject.get("worldY")) * gp.tileSize; // 93
            speed = (int) ((long) jsonObject.get("speed"));
            heart_count = (int) ((long) jsonObject.get("heart_count"));
            key_count = (int) ((long) jsonObject.get("key_count"));
            rum_count = (int) ((long) jsonObject.get("rum_count"));
            map_count = (int) ((long) jsonObject.get("map_count"));
            sword_count = (int) ((long) jsonObject.get("sword_count"));
            level = (int) ((long) jsonObject.get("level"));
            dead_pirate_count = (int) ((long) jsonObject.get("dead_pirate_count"));

        } catch (ParseException | IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            e.printStackTrace();
        }

        // Coordinates of the initial position of the player on the map
        direction = "down";
        timeToDamage = false;
        attackArea.width = 40;
        attackArea.height = 40;

        fillInventory();
    }

    /**
     Fills the player's inventory with the current counts of items.
     */
    public void fillInventory() {
        inventory.put("Key", key_count);
        inventory.put("Map", map_count);
        inventory.put("Rum", rum_count);
        inventory.put("Sword", sword_count);
    }

    /**
     Loads the player's image from the resource folder.
     */
    private void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/sailor_up1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/sailor_up2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/sailor_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/sailor_left2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/sailor_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/sailor_right2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/sailor_down1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/sailor_down2.png")));

            attack_up = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/attack_up.png")));
            attack_down = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/attack_down.png")));
            attack_left = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/attack_left.png")));
            attack_right = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/attack_right.png")));

        } catch (IOException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     Updates the player's movements and animations based on the user's input.
     */
    public void update() {

        // If the player's health is zero or less, the game is over.
        if (heart_count <= 0) {
            gp.state = State.GAME_OVER;
            LOGGER.info("The game is a failure. You have 0 health points.");
        }

        // If the player collides with the ship, the game ends with a happy ending.
        else if (gp.checker.CheckCollisionShip(this)) {
            gp.state = State.HAPPY_END;
            LOGGER.info("The game was completed successfully.");
        }

        // If a player gets hit, he has time (16 * 60 = 0,96 sec) to recover.
        if (timeToDamage) {
            damageCounter++;
            if (damageCounter == 60) {
                timeToDamage = false;
                damageCounter = 0;
            }
        }

        // If the player is in the attacking position, then check if it was successful in hitting someone.
        if (attacking) {
            int idx_m = attacking();
            if (idx_m >= 0)
                gp.pirates[idx_m].fightPirate(idx_m);
            if (idx_m == bossIdx) {
                gp.boss.fightBoss();
            }
        }

        else if (keyIsPressed()) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed)
                direction = "right";

            // If the space bar is pressed, the player switches to the attack position.
            if (keyH.spacePressed)
                attacking = true;

            //  If the button to drink rum is pressed and there is at least one bottle in the inventory.
            if (keyH.rumButton && rum_count >= 1) {
                LOGGER.info("You drank the rum. Your speed has increased.");

                // Remove one bottle and refresh the contents of the inventory.
                --rum_count;
                inventory.put("Rum", rum_count);

                // Increases the player's speed after consuming rum.
                speed += 4;

                // The action of rum is limited, so after the player drank the rum, the countdown begins.
                rum_time_start = true;
                rum_time = 0;

                LOGGER.info("You drank the rum, your speed increased.");
            }

            //  If the button to apply the sword is pressed and there is at least one sword in the inventory.
            if (keyH.swordButton && sword_count >= 1) {
                LOGGER.info("You used your sword. Your attack range has increased.");

                // Increases the attack range of the player.
                attackArea.width = 60;
                attackArea.height = 60;
            }

            // If the rum effect is still going on, and it has already lasted (5 * 60 * 16 =) 3.6 seconds.
            if (rum_time_start && rum_time > 5 * 60) {
                LOGGER.info("The effect of the rum were over. Your speed has become normal.");
                // Decrease the player's speed back to normal after the rum effect ends.
                speed -= 4;
                rum_time_start = false;
            } rum_time++;

            collision = false;
            gp.checker.CheckCollisionTile(this);

            gp.checker.CheckCollisionHouse(this);

            int idx_obj = gp.checker.CheckCollisionObj(this);
            GameObjects.pickUpObj(idx_obj, gp);

            // If the player is not in a collision with an object, he continues to move in a certain direction.
            if (!collision)
                doMove();

            // Increase the animation counter by 1 each frame.
            spriteCounter++;
            if (spriteCounter == 10) {  // Every (16 * 20 =) 0.32 seconds the character changes animation.
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;

                // Reset the frame counter back to 0
                spriteCounter = 0;
            }
        }
    }

    /**
     Draws the player on the screen.
     @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // If the player has taken damage, it will turn red, and at this time he cannot get another hit.
        if (timeToDamage) {
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
            g2.setComposite(alphaComposite);
            g2.setColor(Color.RED);
            g2.fillRect(screenX + 10, screenY, gp.tileSize - 20, gp.tileSize);
        }

        // Choose the appropriate player image based on the direction and attack status.
        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                } else image = attack_up;
            }
            case "down" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                } else image = attack_down;
            }
            case "left" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                } else image = attack_left;
            }
            case "right" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                } else image = attack_right;
            }
        }

        // Draw the player image on the screen
        if (image == attack_up) g2.drawImage(attack_up, screenX, screenY - gp.tileSize, gp.tileSize, gp.tileSize * 2, null);
        else if (image == attack_down) g2.drawImage(attack_down, screenX, screenY, gp.tileSize, gp.tileSize * 2, null);
        else if (image == attack_left) g2.drawImage(attack_left, screenX - gp.tileSize, screenY, gp.tileSize * 2, gp.tileSize, null);
        else if (image == attack_right) g2.drawImage(attack_right, screenX, screenY, gp.tileSize * 2, gp.tileSize, null);
        else g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        // After drawing, the transparency composite is reset to ensure normal rendering.
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    /**
     Performs the player's attacking action and checks for collisions with entities and the boss.
     @return The index of the pirate that the player is attacking, or bossIdx if attacking the boss.
     */
    private int attacking() {
        int idx_pirate = -1;
        boolean fight_boss = false;

        spriteCounter++;
        // Execute the attack animation for the first 25 frames (0.4 sec).
        if (spriteCounter <= 25) {
            int tmpWorldX = worldX;
            int tmpWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Setting up the attack area, which is then checked for collisions with the enemy.
            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            // The size of the player's solid area changes during the attack.
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // Check for collisions with pirates and the boss
            idx_pirate = gp.checker.CheckCollisionEntity(this);
            fight_boss = gp.checker.CheckCollisionBoss(this);

            // Reset the initial values of the player when the attack ended.
            worldX = tmpWorldX;
            worldY = tmpWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        } else {  // End of the attack animation
            gp.sound.setMusic(7);  // Set the attack sound effect
            spriteCounter = 0;
            attacking = false;  // Stop the attacking action
        }

        if (fight_boss)
            return bossIdx;

        return idx_pirate;
    }

    /**
     Moves the player's coordinates based on the current direction and speed.
     */
    public void doMove() {
        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }
    }

    /**
     Checks if any movement keys or the space bar are pressed.
     @return true if any movement keys or space bar are pressed, false otherwise.
     */
    private boolean keyIsPressed() {
        return keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed;
    }
}

