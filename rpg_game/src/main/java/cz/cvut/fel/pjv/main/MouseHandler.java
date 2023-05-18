package cz.cvut.fel.pjv.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    GamePanel gp;

    /**
     Constructs a new MouseHandler with the specified GamePanel.
     @param gp the GamePanel to attach this MouseHandler to.
     */
    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    /**
     Invoked when a mouse button has been pressed on the GamePanel.
     Determines which button was pressed and changes the game state accordingly.
     @param m the MouseEvent that occurred.
     */
    @Override
    public void mousePressed(MouseEvent m) {
        int mouseX = m.getX(); // Get the current mouse position.
        int mouseY = m.getY();

        // TITLE MENU with Play/Rules/Quit buttons
        if (mouseX >= gp.screenWidth / 2 - 105 && mouseX <= gp.screenWidth / 2 + 115) {
            if (mouseY >= 390 && mouseY <= 470) {
                // After pressing the Play button, you will be able to select Start new game/Load previous game
                if (gp.state == State.TITLE) {
                    gp.state = State.LOAD;
                } else if (gp.state == State.LOAD) {  // Pressing the NEW GAME button.
                    gp.state = State.GAME;
                    gp.sound.pauseTrack();
                }
            }
            else if (mouseY >= 510 && mouseY <= 590) {
                if (gp.state == State.TITLE) {  // Pressing the RULES button
                    gp.state = State.RULES;
                } else if (gp.state == State.LOAD) {  // Pressing the LOAD GAME button.
                    gp.sound.pauseTrack();
                    gp.loadGame();
                }
            }

            // The QUIT button at the title menu and at the last menu when the game ends successfully or unsuccessfully.
            else if (mouseY >= 630 && mouseY <= 710) {
                if (gp.state == State.TITLE || gp.state == State.GAME_OVER || gp.state == State.HAPPY_END)
                    System.exit(1);
            }
        }

        // Selecting the buttons on the right side of the screen
        if (mouseX >= gp.screenWidth - 100 && mouseX <= gp.screenWidth - 20) {

            // Pressing the PAUSE button.
            if (mouseY >= 20 && mouseY <= 100) {
                if (gp.state == State.GAME)
                    gp.state = State.PAUSE;
            }

            // Pressing the INVENTORY button.
            else if (mouseY >= gp.tileSize * 2 - 25 && mouseY <= gp.tileSize * 3 - 25) {
                if (gp.state == State.GAME)  // Pressing to open inventory.
                    gp.state = State.INVENTORY;
                else if (gp.state == State.INVENTORY) // Pressing to close inventory.
                    gp.state = State.GAME;
            }

            // Pressing the SAVE and EXIT button.
            else if (mouseY >= gp.tileSize * 3 && mouseY <= gp.tileSize * 4) {
                gp.loadPlayerData();
                gp.loadObjectData();
                gp.loadPirateData();
                gp.loadBossData();
                System.exit(1);
            }
        }

        // Pressing the CONTINUE button (triangle on the pause screen).
        if (mouseX >= gp.screenWidth / 2 - 40 && mouseX <= gp.screenWidth / 2 + 40) {
            if (mouseY >= gp.screenHeight / 2 + 120 && mouseY <= gp.screenHeight / 2 + 200) {
                if (gp.state == State.PAUSE)
                    gp.state = State.GAME;
            }
        }

        // Pressing the NEXT PAGE button in the rules of the game section.
        if (mouseX >= gp.screenWidth - 180 && mouseX <= gp.screenWidth - 180 + gp.tileSize*2-25) {
            if (mouseY >= gp.screenHeight - 115 && mouseY <= gp.screenHeight - 115 + gp.tileSize) {
                if (gp.state == State.RULES)
                    gp.state = State.NEXT_HELP_PAGE;
            }
        }

        // Pressing the RETURN button. After viewing the rules, return to the title menu.
        if (mouseX >= gp.screenWidth - 110 && mouseX <= gp.screenWidth + gp.tileSize) {
            if (mouseY >= 20 && mouseY <= gp.tileSize + 20) {
                if (gp.state == State.NEXT_HELP_PAGE)
                    gp.state = State.TITLE;
            }
        }

        // Pressing the RESTART button.
        if (mouseX >= gp.screenWidth / 2 - 145 && mouseX <= gp.screenWidth / 2 + 150) {
            if (mouseY >= gp.screenHeight / 2 + 115 && mouseY <= gp.screenHeight / 2 + 185) {
                if (gp.state == State.GAME_OVER || gp.state == State.HAPPY_END) {
                    gp.sound.pauseTrack();
                    gp.setupGame();
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent m) { }

    @Override
    public void mouseEntered(MouseEvent m) { }

    @Override
    public void mouseExited(MouseEvent m) { }

    @Override
    public void mouseClicked(MouseEvent m) { }
}
