import javax.swing.*;
import java.awt.*;

/**
 * The ButtonsMenu class creates a panel containing all the control buttons for game.
 * The buttons are used to: save, load, undo, reset the game, switch modes, change levels,
 * show instructions, and quit the game.
 * 
 * @author Mircea-Rafael Crismar
 * @version 1.0
 */
public class ButtonsMenu extends JPanel {
    private MinesweeperGUI parent; // The parent GUI
    private static final Dimension BUTTON_SIZE = new Dimension(150, 47); // Set size for buttons
    private JButton modeButton; // Button to switch between guess and flag mode
    private JButton levelButton; // Button to change game levels

    /**
     * Makes the ButtonsMenu.
     * Sets up buttons and adds them to the panel.
     * @param parent - the parent GUI
     */
    public ButtonsMenu(MinesweeperGUI parent) {
        this.parent = parent; // Set parent GUI
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Set layout

        JButton saveButton = ButtonMaker.createButton("Save", e -> parent.saveGame());
        saveButton.setPreferredSize(BUTTON_SIZE);
        saveButton.setMaximumSize(BUTTON_SIZE);
        add(saveButton);

        JButton loadButton = ButtonMaker.createButton("Load", e -> parent.loadGame());
        loadButton.setPreferredSize(BUTTON_SIZE);
        loadButton.setMaximumSize(BUTTON_SIZE);
        add(loadButton);

        JButton undoButton = ButtonMaker.createButton("Undo", e -> parent.undoMove());
        undoButton.setPreferredSize(BUTTON_SIZE);
        undoButton.setMaximumSize(BUTTON_SIZE);
        add(undoButton);

        JButton resetButton = ButtonMaker.createButton("Reset", e -> parent.resetGame());
        resetButton.setPreferredSize(BUTTON_SIZE);
        resetButton.setMaximumSize(BUTTON_SIZE);
        add(resetButton);

        modeButton = ButtonMaker.createButton("Switch to Flag", e -> parent.switchMode());
        modeButton.setPreferredSize(BUTTON_SIZE);
        modeButton.setMaximumSize(BUTTON_SIZE);
        add(modeButton);

        levelButton = ButtonMaker.createButton("Next Level", e -> parent.toggleLevel());
        levelButton.setPreferredSize(BUTTON_SIZE);
        levelButton.setMaximumSize(BUTTON_SIZE);
        add(levelButton);

        JButton instructionsButton = ButtonMaker.createButton("Instructions", e -> parent.showInstructions());
        instructionsButton.setPreferredSize(BUTTON_SIZE);
        instructionsButton.setMaximumSize(BUTTON_SIZE);
        add(instructionsButton);

        JButton quitButton = ButtonMaker.createButton("Quit", e -> System.exit(0));
        quitButton.setPreferredSize(BUTTON_SIZE);
        quitButton.setMaximumSize(BUTTON_SIZE);
        add(quitButton);
    }

    /**
     * Method to get the Mode button.
     * @return the mode button
     */
    public JButton getModeButton() {
        return modeButton;
    }

    /**
     * Method to get the Level button.
     * @return the level button
     */
    public JButton getLevelButton() {
        return levelButton;
    }
}//end of class