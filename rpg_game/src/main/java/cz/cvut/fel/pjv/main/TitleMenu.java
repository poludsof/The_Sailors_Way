package cz.cvut.fel.pjv.main;

import cz.cvut.fel.pjv.object.Key;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.BreakIterator;

public class TitleMenu {
    public Rectangle playButton;
    public Rectangle helpButton;
    public Rectangle quitButton;
    private Font Bruno;

    public TitleMenu(GamePanel gp) {
        playButton = new Rectangle(gp.screen_width / 2 - 105, 390, 220, 80);
        helpButton = new Rectangle(gp.screen_width / 2 - 105, 510, 220, 80);
        quitButton = new Rectangle(gp.screen_width / 2 - 105, 630, 220, 80);
    }

    public void show(Graphics g, GamePanel gp) {

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Bruno.ttf");
            Bruno = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        Graphics2D g2 = (Graphics2D) g;

//        Font f = new Font("arial", Font.BOLD, 100);
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

        g2.draw(playButton);
        g2.draw(helpButton);
        g2.draw(quitButton);
    }
}
