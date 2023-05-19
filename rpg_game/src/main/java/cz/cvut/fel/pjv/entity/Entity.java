package cz.cvut.fel.pjv.entity;

import cz.cvut.fel.pjv.main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 Represents an entity in the game.
 */
public class Entity {

    public enum Type {
        PIRATE, BOSS
    }
    public Type entityType;

    public GamePanel gp;

    // Entity parameters.
    public int worldX, worldY;
    public int speed;
    public int heart_count;

    // The direction of the character and the images of his movement.
    public String direction;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attack_up, attack_down, attack_left, attack_right;

    // Parameters for drawing entity animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // After entity has taken damage, it should take some time for the entity to recover.
    // During this time the entity can no longer take damage.
    public boolean timeToDamage = false;
    public int damageCounter = 0;  // When this value equals a certain number of frames,
                                    // the entity will be recovered and can take damage again

    public Rectangle solidArea;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);

    public boolean collision = true;
    public boolean attacking = false;
    public boolean showHealth = false;

    /**
     Constructor of Entity with the specified GamePanel instance.
     @param gp the GamePanel instance
     */
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    /**
     Draws the entity on the screen.
     @param g2 the Graphics2D object to draw the entity
     @param scale the scale factor for resizing the field for drawing the number of lives
     */
    public void draw(Graphics2D g2, int scale) {
        // Position of the entity on the screen relative to the player.
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Check if the entity is within the visible area of the player's screen.
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            BufferedImage image = null;

            // Determine which image to use based on the direction of the entity.
            switch (direction) {
                case "up" -> {
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                }
                case "down" -> {
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                }
                case "left" -> {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                }
                case "right" -> {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                }
            }

            // Draw the health bar if showHealth flag is set to true.
            // Health is shown only after the entity has received the first hit.
            if (showHealth) {
                g2.setColor(Color.black);
                g2.fillRect(screenX, screenY - 20, gp.tileSize * scale, 12);

                int fill_health = (gp.tileSize - 4) / (3 * scale);
                g2.setColor(Color.red);
                g2.fillRect(screenX + 2, screenY - 18, (fill_health * heart_count + 10) * scale, 8);
            }

            // If the entity has taken damage, it will turn red, and at this time he cannot get another hit.
            if (timeToDamage) {
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
                g2.setComposite(alphaComposite);
                g2.setColor(Color.RED);
                g2.fillRect(screenX + (10 * scale), screenY, (gp.tileSize - 20) * scale, gp.tileSize * scale);
            }

            // Draw the entity image on the screen.
            g2.drawImage(image, screenX, screenY, gp.tileSize * scale, gp.tileSize * scale, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }
    }

    /**
     Updates the entity's state on each frame of the game loop.
     */
    public void update() {
        randomAction();  // Perform a random action for the entity.
        randomBossAction();  // Perform a random action specific to the boss entity.

        if (timeToDamage) {
            damageCounter++;
            if (damageCounter == 80) {  // After 80 frames (equivalent to 16 * 80 = 1.28 second), reset the damage state.
                timeToDamage = false;
                damageCounter = 0;
            }
        }

        collision = false;
        gp.checker.CheckCollisionTile(this);
        gp.checker.CheckCollisionObj(this);

        // Check for collision with the player.
        boolean fight = gp.checker.CheckCollisionPlayer(this);

        // If the entity is a pirate or boss and there was a collision with a player, then the fight begins.:
        if ((this.entityType == Type.PIRATE || this.entityType == Type.BOSS) && fight) {

            // If a player is in a position where he is open to receive a hit.
            if (!gp.player.timeToDamage) {
                if (gp.player.heart_count > 0) {
                    --gp.player.heart_count;
                    LOGGER.info("You were attacked, you lose 1 health point.");

                    gp.sound.setMusic(8);  // get damage
                }

                // The player received a hit, so we change his state in which he will not receive another hit.
                gp.player.timeToDamage = true;
            }
        }

        if (!collision) {
            // Move the entity based on its current direction and speed.
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        // Increase the animation counter by 1 each frame.
        spriteCounter++;
        if (spriteCounter == 20) {  // Every (16 * 20 =) 0.32 seconds the character changes animation.
            if (spriteNum == 1)
                spriteNum = 2;
            else if (spriteNum == 2)
                spriteNum = 1;

            // Reset the frame counter back to 0
            spriteCounter = 0;
        }
    }

    /**
     Performs a random action for the entity. (e.g. random pirate movements)
     */
    public void randomAction() {}

    /**
     Initiates a fight with a pirate at the specified index.
     @param idx the index of the pirate to fight with
     */
    public void fightPirate(int idx) {}

    /**
     Performs a random action for the boss entity.
     */
    public void randomBossAction() {}

}
