import javax.swing.*;
import java.awt.*;
/**
 * This class is in charge of displaying different stats to the player.
 * These include: number of lives left, current level/difficulty and mode (guess or flag)
 * 
 * @author Mircea-Rafael Crismar
 * @version 1.0
 */

public class GameStats extends JPanel {
    private final JLabel modeLabel; // Label for the current mode
    private final JLabel difficultyLabel; // Label for the difficulty level
    private final JPanel lives; // Panel to display lives

    // Constructor sets up the game status panel
    public GameStats(Minesweeper game, String currentMode) {
        setLayout(new GridLayout(1, 3)); // Layout on 3 columns

        lives = new JPanel();
        lives.setLayout(new FlowLayout(FlowLayout.LEFT)); // Aligns components to the left

        JLabel livesTextLabel = new JLabel("Lives: ");
        lives.add(livesTextLabel); // Adds label for lives
        updateLives(game.getLives()); // Updates the lives display
        add(lives);

        difficultyLabel = new JLabel("Difficulty: Easy", SwingConstants.CENTER);
        add(difficultyLabel); 

        modeLabel = new JLabel("Mode: " + currentMode, SwingConstants.RIGHT);
        modeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        add(modeLabel); 
    }

    // Updates the lives display
    public void updateLives(int livesCount) {
        lives.removeAll(); // Clears the current lives display
        JLabel livesTextLabel = new JLabel("Lives: ");
        lives.add(livesTextLabel); // Adds label for lives
        for (int i = 0; i < livesCount; i++) {
            JLabel heartLabel = new JLabel(scaleImage(new ImageIcon("heartpixel.png")));
            // Adds a heart icon for each life
            lives.add(heartLabel);
        }
        lives.revalidate();
        lives.repaint(); // Refreshes the lives panel
    }

    // Scales an image
    private ImageIcon scaleImage(ImageIcon icon) {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg); // Returns the scaled image icon
    }

    // Returns the mode label
    public JLabel getModeLabel() {
        return modeLabel;
    }

    // Updates the difficulty label
    public void updateDifficulty(String difficulty) {
        difficultyLabel.setText("Difficulty: " + difficulty);
    }
}
