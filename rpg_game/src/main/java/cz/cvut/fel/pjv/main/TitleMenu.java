package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TitleMenu {
//    public Rectangle playButton;
//    public Rectangle helpButton;
//    public Rectangle quitButton;
//    public Rectangle reloadButton;
//    public Rectangle arrowButton;
    private final Font Bruno;

    public TitleMenu(GamePanel gp) {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Bruno.ttf");
            Bruno = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
//        playButton = new Rectangle(gp.screen_width / 2 - 105, 390, 220, 80);
//        helpButton = new Rectangle(gp.screen_width / 2 - 105, 510, 220, 80);
//        quitButton = new Rectangle(gp.screen_width / 2 - 105, 630, 220, 80);
//        reloadButton = new Rectangle(gp.screen_width / 2 - 40, gp.screen_height / 2 + 120, 80, 80);
//        arrowButton = new Rectangle(gp.screen_width - 180, gp.screen_height - 115, gp.tileSize*2-25, gp.tileSize);
//        exitButton = new Rectangle(gp.screen_width - 110, 20, gp.tileSize, gp.tileSize);
    }

    public void show(Graphics g, GamePanel gp) {
//        BufferedImage daniil;
//        BufferedImage andrej;

        BufferedImage hat, sailor, under;

        try {
//            andrej = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/andrej.png"));
//            daniil = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/daniil2.png"));
            hat = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("objects/hat.png"));
            sailor = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/sailor_down2.png"));
            under = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("buttons/under.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Graphics2D g2 = (Graphics2D) g;

        g.setFont(Bruno);
        g.setFont(g2.getFont().deriveFont(Font.BOLD, 85F));
        g.setColor(Color.white);
        g.drawString("The Sailor's Way", gp.screen_width / 2 - 455, gp.screen_height / 2 - 200);

        g.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g.drawString("PLAY", gp.screen_width / 2 - 85, gp.screen_height / 2 + 50);

        g.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        g.drawString("RULES", gp.screen_width / 2 - 95, gp.screen_height / 2 + 167);

        g.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g.drawString("QUIT", gp.screen_width / 2 - 73, gp.screen_height / 2 + 290);

//        g2.drawImage(andrej, gp.screen_width/2 - 530, gp.screen_height/2 - 70, gp.tileSize*2, gp.tileSize*5,null);
//        g2.drawImage(daniil, gp.screen_width/2 - 453, gp.screen_height/2 - 250, gp.tileSize*4 + 15, gp.tileSize*4 + 10,null);
        g2.drawImage(hat, gp.screen_width/2 - 70, gp.screen_height/2 - 200, gp.tileSize * 2, gp.tileSize * 2,null);
        g2.drawImage(sailor, 100, gp.screen_height/2, gp.tileSize * 3, gp.tileSize * 3,null);

        g2.drawImage(under, gp.screen_width / 2 - 105, 395, 220, 80,null);
        g2.drawImage(under, gp.screen_width / 2 - 105, 515, 220, 80,null);
        g2.drawImage(under, gp.screen_width / 2 - 105, 635, 220, 80,null);

    }

    public void showPauseButton(Graphics2D g2, GamePanel gp) {
        BufferedImage pause_button;
        try {
            pause_button = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("buttons/pause_button.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g2.drawImage(pause_button, gp.screen_width - 100, 20, gp.tileSize, gp.tileSize,null);
    }

    public void drawPauseScreen(Graphics g, GamePanel gp) {
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(Bruno);

        g.setColor(Color.black);
        g.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        g.drawString("PAUSE", gp.screen_width / 2 - 165, gp.screen_height / 2 - 145);

        g.setColor(Color.white);
        g.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        g.drawString("PAUSE", gp.screen_width / 2 - 170, gp.screen_height / 2 - 150);

        BufferedImage play_button;
        try {
            play_button = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("buttons/play_button.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.drawImage(play_button, gp.screen_width / 2 - 40, gp.screen_height / 2 + 110, gp.tileSize + 15, gp.tileSize + 15,null);
        //g2.draw(reloadButton);
    }

    public void drawHelpButton(Graphics g, GamePanel gp) {
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage hat, arrow;
        try {
            hat = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("objects/hat.png"));
            arrow = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("buttons/arrow.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int x = 50;
        int y = 165;

        g.setFont(Bruno);
        g.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        g.setColor(Color.white);
        g.drawString("The Sailor's Way", 50, 100);

        g.setFont(g2.getFont().deriveFont(Font.BOLD, 27F));
        String[] text = new String[14];
        text[0] = "A sailor named Herbert embarked on a long voyage";
        text[1] = "on his ship to carry valuable cargo to the other";
        text[2] = "side of the world. Suddenly, however, the ship was";
        text[3] = "attacked by pirates who seized Herbert's ship.";
        text[4] = "";
        text[5] = "Herbert found himself on an island and realized that";
        text[6] = "he had to find a way to get his ship back and recover";
        text[7] = "everything that had been taken from him. Herbert set";
        text[8] = "out on his journey and many challenges and dangers";
        text[9] = "awaited him.";
        text[10] = "";
        text[11] = "Eventually Herbert found the base of the";
        text[12] = "pirates who had taken his ship and began his mission";
        text[13] = "to get his ship back.";
        for (String s : text) {
            g.drawString(s, x, y);
            y += 40;
        }

        g2.drawImage(hat, gp.screen_width / 2 - 103, 0, gp.tileSize - 10, gp.tileSize - 10, null);
        g2.drawImage(arrow, gp.screen_width - 190, gp.screen_height - 150, gp.tileSize * 2 - 10, gp.tileSize * 2 - 10, null);
    }

    public void drawNextPage(Graphics g, GamePanel gp) {
        //Hero Control
        Graphics2D g2 = (Graphics2D) g;
        g.setFont(Bruno);
        g.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        g.setColor(Color.white);
        int x = 450, y = 180;

        BufferedImage key_button, hat, exit_button, space_button;
        try {
            hat = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("objects/hat.png"));
            key_button = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("buttons/wasd.png"));
            exit_button = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("buttons/exit_button.png"));
            space_button = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("buttons/space.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        g.drawString("Hero Control", 50, 100);

        g2.drawImage(key_button, 100, 140, gp.tileSize * 3 + 40, gp.tileSize * 2 + 20, null);
        g2.drawImage(exit_button, gp.screen_width - 110, 20, gp.tileSize, gp.tileSize, null);
        g2.drawImage(hat, 220, 0, gp.tileSize - 10, gp.tileSize - 10, null);

        g2.drawImage(space_button, 700, 160, gp.tileSize * 3 + 40, gp.tileSize, null);
        g.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        g.drawString("SPACE - attack", 710, 300);

        g.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        String[] keyboard = new String[4];
        keyboard[0] = "W - up";
        keyboard[1] = "A - left";
        keyboard[2] = "S - down";
        keyboard[3] = "D - right";
        for (String s : keyboard) {
            g.drawString(s, x, y);
            y += 40;
        }
    }

    public void drawGameOver(Graphics g, GamePanel gp) {
        BufferedImage exit_button;

        Graphics2D g2 = (Graphics2D) g;
        g.setFont(Bruno);
        g.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        g.setColor(Color.black);
        g.drawString("GAME OVER", 285, gp.screen_height / 2 - 145);

        g.setColor(Color.RED);
        g.drawString("GAME OVER", 280, gp.screen_height / 2 - 150);

        try {
            exit_button = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("buttons/exit_button.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        g2.drawImage(exit_button, gp.screen_width / 2 - 40, gp.screen_height / 2 + 110, gp.tileSize + 15, gp.tileSize + 15,null);
    }
}
