import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
/**
 * This class creates and manages the grid of buttons for the game.
 * Each button represents a different cell in the game and updates
 * its state based on the different actions.
 * Works with the Minesweeper game logic to observe and respond to state changes in each cell (Slot).
 * Updates the buttons based on the game state and handle user interactions through the GUI.
 * 
 * @author Mircea-Rafael Crismar
 * @version 1.0
 */
public class GameGrid extends JPanel {
    private JButton[][] buttons; // Grid of buttons
    private Minesweeper game; // Minesweeper game instance
    private final MinesweeperGUI parent; // Parent GUI

    // Constructor sets up the game grid
    public GameGrid(Minesweeper game, MinesweeperGUI parent) {
        this.game = game;
        this.parent = parent;
        initializeBoard();
    }

    // Initializes the board with buttons
    private void initializeBoard() {
        int size = game.getGameSize();
        setLayout(new GridLayout(size, size)); // Set grid layout
        buttons = new JButton[size][size]; // Create button grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(e -> parent.handleButtonClick(row, col)); // Add click listener
                add(buttons[i][j]); // Add button to panel
                game.getMoves()[i][j].addObserver(new SlotObserver(buttons[i][j])); // Observe slot
                updateButton(i, j); // Update button state
            }
        }
    }

    // Sets a new game and reinitializes the board
    public void setGame(Minesweeper game) {
        this.game = game;
        removeAll();
        initializeBoard();
        revalidate();
        repaint();
    }

    // Updates the state of a button
    public void updateButton(int row, int col) {
        String state = game.getCellState(row, col);
        JButton button = buttons[row][col];
        button.setText(state.equals("") ? "" : state);
    }

    // Updates all buttons on the board
    public void updateAllButtons() {
        for (int i = 0; i < game.getGameSize(); i++) {
            for (int j = 0; j < game.getGameSize(); j++) {
                updateButton(i, j);
            }
        }
    }

    // Returns the game instance
    public Minesweeper getGame() {
        return game;
    }

    // Observer class to update button when slot state changes
    private static class SlotObserver implements Observer {
        private final JButton button;

        public SlotObserver(JButton button) {
            this.button = button;
        }

        @Override
        public void update(Observable o, Object arg) {
            Slot slot = (Slot) o;
            button.setText(slot.getState().equals("") ? "" : slot.getState());
        }
    }
}
