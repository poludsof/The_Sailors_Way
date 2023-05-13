package cz.cvut.fel.pjv.main;

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

        if (mouseX >= gp.screen_width / 2 - 105 && mouseX <= gp.screen_width / 2 + 115) {
            if (mouseY >= 390 && mouseY <= 470) {
                if (gp.state == GamePanel.State.TITLE) {
                    gp.state = GamePanel.State.LOAD;
                }
                else if (gp.state == GamePanel.State.LOAD) {
                    gp.state = GamePanel.State.GAME;
                    gp.stopMusic();
                }
            }
            else if (mouseY >= 510 && mouseY <= 590) {
                if (gp.state == GamePanel.State.TITLE) {
                    gp.state = GamePanel.State.HELP;
                }
                else if (gp.state == GamePanel.State.LOAD) {
                    gp.stopMusic();
                    gp.fileName = "rpg_game/target/load_game.json";
                    gp.restart();
                }
            }
            else if (mouseY >= 630 && mouseY <= 710) {
                if (gp.state == GamePanel.State.TITLE || gp.state == GamePanel.State.GAME_OVER || gp.state == GamePanel.State.HAPPY_END)
                    System.exit(1);
            }
        }

        if (mouseX >= gp.screen_width - 100 && mouseX <= gp.screen_width - 20) {
            if (mouseY >= 20 && mouseY <= 100) {
                if (gp.state == GamePanel.State.GAME)
                    gp.state = GamePanel.State.PAUSE;
            }
            //(gp.screen_width - 100, gp.tileSize * 2 - 25, gp.tileSize, gp.tileSize);
            if (mouseY >= gp.tileSize * 2 - 25 && mouseY <= gp.tileSize * 3 - 25) {
                if (gp.state == GamePanel.State.GAME)
                    gp.state = GamePanel.State.INVENTORY;
                else if (gp.state == GamePanel.State.INVENTORY)
                    gp.state = GamePanel.State.GAME;
            }
            //loadButton = new Rectangle(gp.screen_width - 100, gp.tileSize * 3, gp.tileSize, gp.tileSize);
            if (mouseY >= gp.tileSize * 3 && mouseY <= gp.tileSize * 4) {
                gp.loadPlayerData();
                gp.loadObjectData();
                gp.loadPirateData();
                gp.loadBossData();
                System.exit(1);
            }
        }

        //reloadButton = new Rectangle(gp.screen_width / 2 - 40, gp.screen_height / 2 + 120, 80, 80);
        if (mouseX >= gp.screen_width / 2 - 40 && mouseX <= gp.screen_width / 2 + 40) {
            if (mouseY >= gp.screen_height / 2 + 120 && mouseY <= gp.screen_height / 2 + 200) {
                if (gp.state == GamePanel.State.PAUSE)
                    gp.state = GamePanel.State.GAME;
            }
        }

        //arrowButton = new Rectangle(gp.screen_width - 180, gp.screen_height - 115, gp.tileSize*2-25, gp.tileSize);
        if (mouseX >= gp.screen_width - 180 && mouseX <= gp.screen_width - 180 + gp.tileSize*2-25) {
            if (mouseY >= gp.screen_height - 115 && mouseY <= gp.screen_height - 115 + gp.tileSize) {
                if (gp.state == GamePanel.State.HELP){
                    gp.state = GamePanel.State.NEXT_HELP_PAGE;
                }
            }
        }

        //(gp.screen_width - 110, 20, gp.tileSize, gp.tileSize);
        if (mouseX >= gp.screen_width - 110 && mouseX <= gp.screen_width + gp.tileSize) {
            if (mouseY >= 20 && mouseY <= gp.tileSize + 20) {
                if (gp.state == GamePanel.State.NEXT_HELP_PAGE)
                    gp.state = GamePanel.State.TITLE;
            }
        }

        //Rectangle(gp.screen_width / 2 - 145, gp.screen_height / 2 + 115, 295, 70);
        if (mouseX >= gp.screen_width / 2 - 145 && mouseX <= gp.screen_width / 2 + 150) {
            if (mouseY >= gp.screen_height / 2 + 115 && mouseY <= gp.screen_height / 2 + 185) {
                if (gp.state == GamePanel.State.GAME_OVER || gp.state == GamePanel.State.HAPPY_END) {
                    gp.fileName = "rpg_game/target/new_game.json";
                    gp.restart();
                }
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
