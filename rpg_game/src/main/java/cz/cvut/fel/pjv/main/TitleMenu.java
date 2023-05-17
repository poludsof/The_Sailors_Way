package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TitleMenu {
    private final Font Bruno;
    public TitleMenu() {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Bruno.ttf");
            Bruno = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    public void show(Graphics2D g2, GamePanel gp) {
        BufferedImage hat, sailor, under;
        try {
            hat = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("objects/hat.png")));
            sailor = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("player/sailor_down2.png")));
            under = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("buttons/under.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.setFont(Bruno);   // Apply the font.
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 85F));
        g2.setColor(Color.white);
        g2.drawString("The Sailor's Way", gp.screenWidth / 2 - 455, gp.screenHeight / 2 - 200);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.drawString("PLAY", gp.screenWidth / 2 - 85, gp.screenHeight / 2 + 50);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        g2.drawString("RULES", gp.screenWidth / 2 - 95, gp.screenHeight / 2 + 167);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.drawString("QUIT", gp.screenWidth / 2 - 73, gp.screenHeight / 2 + 290);

        g2.drawImage(hat, gp.screenWidth /2 - 70, gp.screenHeight /2 - 200, gp.tileSize * 2, gp.tileSize * 2,null);
        g2.drawImage(sailor, 100, gp.screenHeight /2, gp.tileSize * 3, gp.tileSize * 3,null);

        g2.drawImage(under, gp.screenWidth / 2 - 105, 395, 220, 80,null);
        g2.drawImage(under, gp.screenWidth / 2 - 105, 515, 220, 80,null);
        g2.drawImage(under, gp.screenWidth / 2 - 105, 635, 220, 80,null);
    }

    public void drawLoad(Graphics2D g2, GamePanel gp) {
        BufferedImage under;
        try {
            under = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("buttons/under.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 85F));
        g2.setColor(Color.white);
        g2.drawString("The Sailor's Way", gp.screenWidth / 2 - 455, gp.screenHeight / 2 - 200);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45));
        g2.drawString("NEW", gp.screenWidth / 2 - 70, gp.screenHeight / 2 + 50);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        g2.drawString("LOAD", gp.screenWidth / 2 - 80, gp.screenHeight / 2 + 167);

        g2.drawImage(under, gp.screenWidth / 2 - 105, 395, 220, 80,null);
        g2.drawImage(under, gp.screenWidth / 2 - 105, 515, 220, 80,null);
    }

    public void showPauseButton(Graphics2D g2, GamePanel gp) {
        BufferedImage pause_button;
        try {
            pause_button = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("buttons/pause_button.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(pause_button, gp.screenWidth - 100, 20, gp.tileSize, gp.tileSize,null);
    }

    public void showChestButton(Graphics2D g2, GamePanel gp) {
        BufferedImage chest_button;
        try {
            chest_button = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("buttons/chest.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(chest_button, gp.screenWidth - 100, gp.tileSize * 2 - 25, gp.tileSize, gp.tileSize,null);
    }

    public void drawPauseScreen(Graphics2D g2, GamePanel gp) {
        g2.setFont(Bruno);

        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        g2.drawString("PAUSE", gp.screenWidth / 2 - 165, gp.screenHeight / 2 - 145);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        g2.drawString("PAUSE", gp.screenWidth / 2 - 170, gp.screenHeight / 2 - 150);

        BufferedImage play_button;
        try {
            play_button = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("buttons/play_button.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.drawImage(play_button, gp.screenWidth / 2 - 40, gp.screenHeight / 2 + 110, gp.tileSize + 15, gp.tileSize + 15,null);
    }

    public void drawHelpButton(Graphics2D g2, GamePanel gp) {
        BufferedImage hat, arrow;
        try {
            hat = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("objects/hat.png")));
            arrow = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("buttons/arrow.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int x = 50;
        int y = 165;

        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        g2.setColor(Color.white);
        g2.drawString("The Sailor's Way", 50, 100);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 27F));
        String story = """
                A sailor named Herbert embarked on a long voyage
                on his ship to carry valuable cargo to the other
                side of the world. Suddenly, however, the ship was
                attacked by pirates who seized Herbert's ship.
                
                Herbert found himself on an island and realized that
                he had to find a way to get his ship back and recover
                everything that had been taken from him. Herbert set
                out on his journey and many challenges and dangers
                awaited him.
                
                Eventually Herbert found the base of the
                pirates who had taken his ship and began his mission
                to get his ship back.
                """;
        for (String line : story.split("\n"))
            g2.drawString(line, x, y += g2.getFontMetrics().getHeight());

        g2.drawImage(hat, gp.screenWidth / 2 - 103, 0, gp.tileSize - 10, gp.tileSize - 10, null);
        g2.drawImage(arrow, gp.screenWidth - 190, gp.screenHeight - 150, gp.tileSize * 2 - 10, gp.tileSize * 2 - 10, null);
    }

    public void drawNextPage(Graphics2D g2, GamePanel gp) {
        //Hero Control
        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        g2.setColor(Color.white);
        int x = 450, y = 180;

        BufferedImage key_button, hat, exit_button, space_button;
        try {
            hat = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("objects/hat.png")));
            key_button = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("buttons/wasd.png")));
            exit_button = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("buttons/exit_button.png")));
            space_button = ImageIO.read(Objects.requireNonNull(Player.class.getClassLoader().getResourceAsStream("buttons/space.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        g2.drawString("Hero Control", 50, 100);

        g2.drawImage(key_button, 100, 140, gp.tileSize * 3 + 40, gp.tileSize * 2 + 20, null);
        g2.drawImage(exit_button, gp.screenWidth - 110, 20, gp.tileSize, gp.tileSize, null);
        g2.drawImage(hat, 220, 0, gp.tileSize - 10, gp.tileSize - 10, null);

        g2.drawImage(space_button, 700, 160, gp.tileSize * 3 + 40, gp.tileSize, null);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        g2.drawString("SPACE - attack", 710, 300);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        String[] keyboard = new String[4];
        keyboard[0] = "W - up";
        keyboard[1] = "A - left";
        keyboard[2] = "S - down";
        keyboard[3] = "D - right";
        for (String s : keyboard) {
            g2.drawString(s, x, y += g2.getFontMetrics().getHeight());
        }
    }

    public void drawGameOver(Graphics2D g2, GamePanel gp) {
        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.black);
        g2.drawString("GAME OVER", 285, gp.screenHeight / 2 - 145);

        g2.setColor(Color.RED);
        g2.drawString("GAME OVER", 280, gp.screenHeight / 2 - 150);

        g2.setColor(Color.white);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        g2.drawString("RESTART", gp.screenWidth / 2 - 138, gp.screenHeight / 2 + 167);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.drawString("QUIT", gp.screenWidth / 2 - 73, gp.screenHeight / 2 + 290);

    }

    public void drawHappyEnd(Graphics2D g2, GamePanel gp) {

        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.black);
        g2.drawString("WELL DONE", 280, gp.screenHeight / 2 - 145);

        g2.setColor(Color.GREEN);
        g2.drawString("WELL DONE", 275, gp.screenHeight / 2 - 150);

        g2.setColor(Color.white);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        g2.drawString("RESTART", gp.screenWidth / 2 - 138, gp.screenHeight / 2 + 167);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.drawString("QUIT", gp.screenWidth / 2 - 73, gp.screenHeight / 2 + 290);
    }

    public void showLoadButton(Graphics2D g2, GamePanel gp) {
        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        g2.setColor(Color.white);

        g2.drawString("save", gp.screenWidth - 95, gp.tileSize * 3 + 25);
        g2.drawString("exit", gp.screenWidth - 88, gp.tileSize * 3 + 73);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        g2.drawString("and", gp.screenWidth - 83, gp.tileSize * 3 + 48);

        Rectangle loadButton = new Rectangle(gp.screenWidth - 100, gp.tileSize * 3, gp.tileSize, gp.tileSize);;
        g2.draw(loadButton);
    }
}
