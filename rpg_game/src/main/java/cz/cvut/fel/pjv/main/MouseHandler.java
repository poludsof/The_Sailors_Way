package cz.cvut.fel.pjv.main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    GamePanel gp;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent m) {

    }

    @Override
    public void mousePressed(MouseEvent m) {
        int mouseX = m.getX();
        int mouseY = m.getY();

//        playButton = new Rectangle(gp.screen_width / 2 - 105, 390, 220, 80);
//        helpButton = new Rectangle(gp.screen_width / 2 - 105, 510, 220, 80);
//        quitButton = new Rectangle(gp.screen_width / 2 - 105, 630, 220, 80);

        if (mouseX >= gp.screen_width / 2 - 105 && mouseX <= gp.screen_width / 2 + 115) {
            if (mouseY >= 390 && mouseY <= 470) {
                gp.state = GamePanel.State.GAME;
            }
            else if (mouseY >= 510 && mouseY <= 590) {
                gp.state = GamePanel.State.HELP;
            }
            else if (mouseY >= 630 && mouseY <= 710) {
                System.exit(1);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent m) {

    }

    @Override
    public void mouseEntered(MouseEvent m) {

    }

    @Override
    public void mouseExited(MouseEvent m) {

    }
}
