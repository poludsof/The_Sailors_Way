package cz.cvut.fel.pjv.main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import static cz.cvut.fel.pjv.main.Main.LOGGER;

/**
 The TitleMenu class represents the title screen menu of the game.
 It displays the game options and images, allowing the player to navigate through the menu.
 */
public class TitleMenu {
    private final Font Bruno; // custom font

    /**
     Constructs a new TitleMenu object.
     It loads the custom font, "Bruno.ttf", that is used to display text in the title menu.
     */
    public TitleMenu() {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Bruno.ttf");
            assert is != null;
            Bruno = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            LOGGER.error("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     Displays the title menu screen with the game options and images.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void show(Graphics2D g2, GamePanel gp) {

        g2.setFont(Bruno);  // Apply the font.
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 85F));
        g2.setColor(Color.white);
        g2.drawString("The Sailor's Way", gp.screenWidth / 2 - 455, gp.screenHeight / 2 - 200);

        // Draw the PLAY, RULES, and QUIT buttons with the adjusted font size and positions.
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.drawString("PLAY", gp.screenWidth / 2 - 85, gp.screenHeight / 2 + 50);
        g2.drawString("QUIT", gp.screenWidth / 2 - 73, gp.screenHeight / 2 + 290);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        g2.drawString("RULES", gp.screenWidth / 2 - 95, gp.screenHeight / 2 + 167);

        // Draw the hat and sailor images in their positions on the screen.
        g2.drawImage(ImageManager.hat_sailor, gp.screenWidth /2 - 70, gp.screenHeight /2 - 200, gp.tileSize * 2, gp.tileSize * 2,null);
        g2.drawImage(ImageManager.sailor, 100, gp.screenHeight /2, gp.tileSize * 3, gp.tileSize * 3,null);

        // Draw the background frames of the buttons and the text at their positions on the screen.
        g2.drawImage(ImageManager.frame, gp.screenWidth / 2 - 105, 395, 220, 80,null);
        g2.drawImage(ImageManager.frame, gp.screenWidth / 2 - 105, 515, 220, 80,null);
        g2.drawImage(ImageManager.frame, gp.screenWidth / 2 - 105, 635, 220, 80,null);
    }

    /**
     Draws a screen with two buttons NEW|LOAD to select the type of game.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void drawLoad(Graphics2D g2, GamePanel gp) {

        // Draw the game title.
        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 85F));
        g2.setColor(Color.white);
        g2.drawString("The Sailor's Way", gp.screenWidth / 2 - 455, gp.screenHeight / 2 - 200);

        // Draw the "NEW" and "LOAD" button.
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 45));
        g2.drawString("NEW", gp.screenWidth / 2 - 70, gp.screenHeight / 2 + 50);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        g2.drawString("LOAD", gp.screenWidth / 2 - 80, gp.screenHeight / 2 + 167);

        // Draw the frames of the buttons.
        g2.drawImage(ImageManager.frame, gp.screenWidth / 2 - 105, 395, 220, 80,null);
        g2.drawImage(ImageManager.frame, gp.screenWidth / 2 - 105, 515, 220, 80,null);
    }

    /**
     Displays a button for pausing the game.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void showPauseButton(Graphics2D g2, GamePanel gp) {
        g2.drawImage(ImageManager.pause_button, gp.screenWidth - 100, 20, gp.tileSize, gp.tileSize,null);
    }

    /**
     Displays a button for opening the player's inventory.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void showChestButton(Graphics2D g2, GamePanel gp) {
        g2.drawImage(ImageManager.chest_button, gp.screenWidth - 100, gp.tileSize * 2 - 25, gp.tileSize, gp.tileSize,null);
    }

    /**
     Displays pausing state on the screen.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void drawPauseScreen(Graphics2D g2, GamePanel gp) {
        // Set the font and colors of the text to be displayed on the pause screen.
        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        // Display the text.
        g2.setColor(Color.white);
        g2.drawString("PAUSE", gp.screenWidth / 2 - 170, gp.screenHeight / 2 - 150);

        // Display the shadow of the text.
        g2.setColor(Color.black);
        g2.drawString("PAUSE", gp.screenWidth / 2 - 165, gp.screenHeight / 2 - 145);

        g2.drawImage(ImageManager.play_button, gp.screenWidth / 2 - 40, gp.screenHeight / 2 + 110, gp.tileSize + 15, gp.tileSize + 15,null);
    }

    /**
     Draws the help button screen which contains a brief storyline and images of a hat and an arrow.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void drawRulesButton(Graphics2D g2, GamePanel gp) {

        // Set the initial x and y positions for drawing the storyline.
        int x = 50;
        int y = 165;

        // Set the font, size, color and draw the title of the game.
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
        // Draw the story line by line with a shift of the y-axis position for each line.
        for (String line : story.split("\n"))
            g2.drawString(line, x, y += g2.getFontMetrics().getHeight());

        g2.drawImage(ImageManager.hat_sailor, gp.screenWidth / 2 - 103, 0, gp.tileSize - 10, gp.tileSize - 10, null);
        g2.drawImage(ImageManager.arrow, gp.screenWidth - 190, gp.screenHeight - 150, gp.tileSize * 2 - 10, gp.tileSize * 2 - 10, null);
    }

    /**
     Draw the next page (after the rules of the game) with a description of the player's control.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void drawNextPage(Graphics2D g2, GamePanel gp) {
        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        g2.setColor(Color.white);

        // Draw the title of the page.
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        g2.drawString("Player Control", 50, 100);

        // Draw the images of the key button, exit button, and hat.
        g2.drawImage(ImageManager.key_button, 100, 140, gp.tileSize * 3 + 40, gp.tileSize * 2 + 20, null);
        g2.drawImage(ImageManager.exit_button, gp.screenWidth - 110, 20, gp.tileSize, gp.tileSize, null);
        g2.drawImage(ImageManager.hat_sailor, 220, 0, gp.tileSize - 10, gp.tileSize - 10, null);

        // Display information on keyboard controls for the player.
        g2.drawImage(ImageManager.space_button, 700, 160, gp.tileSize * 3 + 40, gp.tileSize, null);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        g2.drawString("SPACE - attack", 710, 300);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25F));
        String[] keyboard = new String[4];
        keyboard[0] = "W - up";
        keyboard[1] = "A - left";
        keyboard[2] = "S - down";
        keyboard[3] = "D - right";

        // Set the initial x and y positions for drawing the keyboard.
        int x = 450, y = 180;
        for (String s : keyboard) {
            g2.drawString(s, x, y += g2.getFontMetrics().getHeight());
        }
    }


    /**
     Draws the "Game Over" screen with the option to restart or quit the game.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void drawGameOver(Graphics2D g2, GamePanel gp) {
        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        // Create a semi-transparent black rectangle that covers the entire screen to create a darker effect.
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // The following lines draw the text "GAME OVER" in black and then in red,
        // slightly shifted to create a shadow effect.
        g2.setColor(Color.RED);
        g2.drawString("GAME OVER", 280, gp.screenHeight / 2 - 150);

        g2.setColor(Color.black);
        g2.drawString("GAME OVER", 285, gp.screenHeight / 2 - 145);

        g2.setColor(Color.white);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        g2.drawString("RESTART", gp.screenWidth / 2 - 138, gp.screenHeight / 2 + 167);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.drawString("QUIT", gp.screenWidth / 2 - 73, gp.screenHeight / 2 + 290);

    }

    /**
     Draws the "Happy End" screen with a congratulatory message, restart and quit options.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void drawHappyEnd(Graphics2D g2, GamePanel gp) {

        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        // Create a semi-transparent black rectangle that covers the entire screen to create a darker effect.
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // The following lines draw the text "WELL DONE" in black and then in red,
        // slightly shifted to create a shadow effect.
        g2.setColor(Color.GREEN);
        g2.drawString("WELL DONE", 275, gp.screenHeight / 2 - 150);

        g2.setColor(Color.black);
        g2.drawString("WELL DONE", 280, gp.screenHeight / 2 - 145);

        g2.setColor(Color.white);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        g2.drawString("RESTART", gp.screenWidth / 2 - 138, gp.screenHeight / 2 + 167);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.drawString("QUIT", gp.screenWidth / 2 - 73, gp.screenHeight / 2 + 290);
    }

    /**
     Shows the load button on the screen on the right during the game.
     @param g2 the Graphics2D object used to draw on the screen
     @param gp the GamePanel object containing information about the game's dimensions and settings
     */
    public void showLoadButton(Graphics2D g2, GamePanel gp) {
        g2.setFont(Bruno);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        g2.setColor(Color.white);

        // Draw "save", "exit" and "and" text on the screen at the appropriate positions.
        g2.drawString("save", gp.screenWidth - 95, gp.tileSize * 3 + 25);
        g2.drawString("exit", gp.screenWidth - 88, gp.tileSize * 3 + 73);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        g2.drawString("and", gp.screenWidth - 83, gp.tileSize * 3 + 48);

        // Create a Rectangle object for the load button and draw it around the text.
        Rectangle loadButton = new Rectangle(gp.screenWidth - 100, gp.tileSize * 3, gp.tileSize, gp.tileSize);
        g2.draw(loadButton);
    }
}
