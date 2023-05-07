package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TitleMenu {
    public Rectangle playButton;
    public Rectangle helpButton;
    public Rectangle quitButton;
    public Rectangle reloadButton;
    private Font Bruno;

    public TitleMenu(GamePanel gp) {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Bruno.ttf");
            Bruno = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        playButton = new Rectangle(gp.screen_width / 2 - 105, 390, 220, 80);
        helpButton = new Rectangle(gp.screen_width / 2 - 105, 510, 220, 80);
        quitButton = new Rectangle(gp.screen_width / 2 - 105, 630, 220, 80);
        reloadButton = new Rectangle(gp.screen_width / 2 - 40, gp.screen_height / 2 + 120, 80, 80);
    }

    public void show(Graphics g, GamePanel gp) {
        BufferedImage daniil;
        BufferedImage andrej;

        try {
            andrej = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/andrej.png"));
            daniil = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("player/daniil2.png"));
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

        g.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g.drawString("HELP", gp.screen_width / 2 - 83, gp.screen_height / 2 + 167);

        g.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g.drawString("QUIT", gp.screen_width / 2 - 73, gp.screen_height / 2 + 287);

        g2.drawImage(andrej, gp.screen_width/2 - 530, gp.screen_height/2 - 70, gp.tileSize*2, gp.tileSize*5,null);
        g2.drawImage(daniil, gp.screen_width/2 - 453, gp.screen_height/2 - 250, gp.tileSize*4 + 15, gp.tileSize*4 + 10,null);

        g2.draw(playButton);
        g2.draw(helpButton);
        g2.draw(quitButton);
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

}
