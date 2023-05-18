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

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public Dictionary<String, Integer> inventory = new Hashtable<>();
    public int key_count, dead_pirate_count, rum_count, rum_time, map_count, sword_count, level, max_health;
    public boolean rum_time_start = false;

    public final int screenX; // todo why final
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp); // todo what is this
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Set the player's solid area
        solidArea = new Rectangle();
        solidArea.x = 20;
        solidArea.y = 24;
        solidArea.height = 56;
        solidArea.width = 40;

//        setDefaultValues("rpg_game/src/dataJson/new_game.json");
        getPlayerImage();
    }

    /**
     * Sets default values for the player's properties.
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
            max_health = (int) ((long) jsonObject.get("max_health"));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        // Coordinates of the initial position of the player on the map
        direction = "down";
        timeToDamage = false;
        attackArea.width = 40;
        attackArea.height = 40;
        fillInventory();
    }

    public void fillInventory() {
        inventory.put("Key", key_count);
        inventory.put("Map", map_count);
        inventory.put("Rum", rum_count);
        inventory.put("Sword", sword_count);
    }

    /**
     * Loads the player's image from the resource folder.
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
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the player's movements and animations based on the user's input.
     */
    public void update() {
        if (heart_count <= 0)
            gp.state = State.GAME_OVER;

        else if (gp.checker.CheckCollisionBoat(this))
            gp.state = State.HAPPY_END;

        if (timeToDamage) {
            damageCounter++;
            if (damageCounter == 60) { //  каждую секунду получает удар
                timeToDamage = false;
                damageCounter = 0;
            }
        }

        if (attacking) {
            int idx_m = attacking();
            if (idx_m >= 0)
                gp.pirates[idx_m].fightPirate(idx_m);
            if (idx_m == -100) {
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

            if (keyH.spacePressed)
                attacking = true;

            if (keyH.rumButton && rum_count >= 1) {
                --rum_count;
                inventory.put("Rum", rum_count);
                speed += 4;
                rum_time_start = true;
                rum_time = 0;
                LOGGER.info("You drank the rum, your speed increased.");
            }

            if (keyH.swordButton && sword_count >= 1) {
                attackArea.width = 60;
                attackArea.height = 60;
            }

            //end of RUM
            if (rum_time_start && rum_time > 5 * 60) { // the effect of the rum lasts for 5 seconds
                speed -= 4;
                rum_time_start = false;
            } rum_time++;

            collision = false;
            gp.checker.CheckCollisionTile(this);

            gp.checker.CheckCollisionHouse(this);

            int idx_obj = gp.checker.CheckCollisionObj(this);
            GameObjects.pickUpObj(idx_obj, gp);

            if (!collision)
                doMove();

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1)
                    spriteNum = 2;
                else if (spriteNum == 2)
                    spriteNum = 1;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (timeToDamage) {
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
            g2.setComposite(alphaComposite);
            g2.setColor(Color.RED);
            g2.fillRect(screenX + 10, screenY, gp.tileSize - 20, gp.tileSize);
        }

        switch (direction) {
            case "up" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                if (attacking) image = attack_up;
            }
            case "down" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                if (attacking) image = attack_down;
            }
            case "left" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                if (attacking) image = attack_left;
            }
            case "right" -> {
                if (!attacking) {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
                if (attacking) image = attack_right;
            }
        }

        if (image == attack_up) g2.drawImage(attack_up, screenX, screenY - gp.tileSize, gp.tileSize, gp.tileSize * 2, null);
        else if (image == attack_down) g2.drawImage(attack_down, screenX, screenY, gp.tileSize, gp.tileSize * 2, null);
        else if (image == attack_left) g2.drawImage(attack_left, screenX - gp.tileSize, screenY, gp.tileSize * 2, gp.tileSize, null);
        else if (image == attack_right) g2.drawImage(attack_right, screenX, screenY, gp.tileSize * 2, gp.tileSize, null);
        else g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    private int attacking() {
        int idx_pirate = -1;
        boolean fight_boss = false;
        spriteCounter++;
        if (spriteCounter <= 25) {
            int currWorldX = worldX;
            int currWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            idx_pirate = gp.checker.CheckCollisionEntity(this);
            fight_boss = gp.checker.CheckCollisionBoss(this);

            worldX = currWorldX;
            worldY = currWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 25){
            gp.sound.setMusic(7);
            spriteCounter = 0;
            attacking = false;
        }

        if (fight_boss)
            return -100;
        return idx_pirate;
    }

    public void doMove() { //change coordinates relative to the direction
        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }
    }

    private boolean keyIsPressed() {
        return keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed;
    }
}

