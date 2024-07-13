import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import java.io.*;

/**
 * MinesweeperGUI Class
 * This class creates is responsible to create a graphical user interface for the game. 
 * It initializes the game and sets up the main window, configures all components, including the 
 * game board, action menu, and message area. 
 * It handles most of the game actions such as clicking on grid cells and switching modes.
 * It also updates the game status, checks for win/loss conditions
 * and displays the menu to: save, load, reset, and undo moves in the game. 
 */
public class MinesweeperGUI extends JFrame {
    private Minesweeper game;  // Instance of the Minesweeper game
    private Stack<int[]> previousMoves;  // Stack of previous moves
    private JLabel modeLabel;  // Label for current mode (Guess/Flag)
    private JTextArea messageArea;  // Message area
    private String currentMode;  // Current mode (Guess or Flag)
    private GameStats gameStatus;  //  Game status panel
    private GameGrid gameGrid;  // Panel to display the game grid
    private ButtonsMenu buttonsMenu;  // Action buttons menu
    private JButton modeButton;  // Button to switch between Guess and Flag modes

    /**
     * Initializes the game and user graphical interface creating the main window.
     */
    public MinesweeperGUI() {
        initializeGame();  // Initialize the game 
        initializeUI();  // Initialize the components of the UI
        setTitle("Minesweeper Game"); 
        setSize(600, 500);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setVisible(true);  
    }

    /**
     *  Makes an instance of the Minesweeper game.
     */
    private void initializeGame() {
        game = new Minesweeper();  // Creates a new Minesweeper game
        previousMoves = new Stack<>();  // Creates a stack for previous moves
        currentMode = "Guess a square";  // Initial mode is set to "Guess a square"
    }

    /**
     *Creates and sets up the all the components
     */
    private void initializeUI() {
        setLayout(new BorderLayout());

        // title and game status in the north of the panel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("MinesweeperTG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        northPanel.add(titleLabel, BorderLayout.NORTH);

        northPanel.add(Box.createRigidArea(new Dimension(0, 10)), BorderLayout.CENTER);

        gameStatus = new GameStats(game, currentMode);
        modeLabel = gameStatus.getModeLabel();
        northPanel.add(gameStatus, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);  

        // Create and set up the game grid panel
        gameGrid = new GameGrid(game, this);
        // 2D array of buttons
        add(gameGrid, BorderLayout.CENTER);
        // Add the game grid to the center of the frame

        // Create and set up the buttons menu panel
        buttonsMenu = new ButtonsMenu(this);
        modeButton = buttonsMenu.getModeButton();
        add(buttonsMenu, BorderLayout.EAST);  
        // Add the buttons menu to the east side of the frame

        // Create and set up the message area
        MessageArea messageAreaSpace = new MessageArea();
        messageArea = messageAreaSpace.getMessageArea();
        add(new JScrollPane(messageArea), BorderLayout.SOUTH);  
        // Add the message area to the south of the frame
    }

    /**
     * This function handles the clicks on the game board.
     * It records the move and then updates the game status.
     * After it does this it outputs the result of the move while checking for the win/loss scenarios.
     */
    public void handleButtonClick(int row, int col) {
        // Adds move to the stack if it is different from the last one, this keeps our undo function cleaner
        if (previousMoves.isEmpty() || !isSameMove(previousMoves.peek(), row, col)) {
            previousMoves.push(new int[]{row, col});
        }
        String result;
        if (currentMode.equals("Guess a square")) {
            result = game.makeMove(Integer.toString(row), Integer.toString(col), "G");
        } else {
            result = game.makeMove(Integer.toString(row), Integer.toString(col), "F");
        }
        //Makes the move and gets the result of it

        // Update the game status and message area
        updateStatus();
        messageArea.append(result + "\n");
        checkGameStatus();  // Checks if the game has been won or lost
    }

    /**
     * This method is used to display the player's stats: lives, mode and current level.
     */
    public void updateStatus() {
        // Update the lives and mode in the game status panel
        gameStatus.updateLives(game.getLives());
        modeLabel.setText("Mode: " + currentMode);
        modeButton.setText(currentMode.equals("Guess a square") ? "Switch to Flag" : "Switch to Guess");

        // Update the difficulty level in the game status panel
        String difficulty = game.getLevel().equals("Levels/em1.txt") ? "Easy" : game.getLevel().equals("Levels/em2.txt") ? "Medium" : "Hard";
        gameStatus.updateDifficulty(difficulty);
    }

    /**
     *This method verifies if the player won or lost the game 
     *Depending on the case: displays messages, changes levels, or resets the game.
     */
    private void checkGameStatus() {
        // Check the game status
        String status = game.checkWin();
        if (status.equals("won")) {
            JOptionPane.showMessageDialog(this, "Congratulations! You won the game!");
            game.changeLevel();  // Change to the next level
            gameGrid.setGame(game);
            gameGrid.updateAllButtons();  // Updates game board
            updateStatus();
        } else if (status.equals("lost")) {
            JOptionPane.showMessageDialog(this, "You have run out of lives. Game over!");
            resetGame();  // Reset the game
        }
    }

    /**
     * This function resets the current game by setting the 
     * board to the initial empty state and displays a message.
     */
    public void resetGame() {
        game = new Minesweeper(game.getLevel());
        gameGrid.setGame(game);
        gameGrid.updateAllButtons();
        updateStatus();
        messageArea.append("Game reset successfully.\n");
    }

    /**
     * This function saves the current game to its respective file, 
     * following this it outputs a success or failure message.
     */
    public void saveGame() {
        try {
            // Save the current game state to a file
            GameFileHandler.saveGame(game.getMoves(), game.getLevel(), game.getLives());

            // Extract the level number from the file name
            String levelFileName = game.getLevel();
            String levelNumber = levelFileName.replace("Levels/em", "").replace(".txt", "");

            messageArea.append("Game saved successfully for Level " + levelNumber + "!\n");
        } catch (IOException e) {
            messageArea.append("Failed to save the game.\n");
        }
    }

    /**
     * Loads the saved game, updates the game grid, 
     * and appends a message indicating the game has been loaded or not.
     */
    public void loadGame() {
        GameFileHandler.loadGameToGUI(game.getLevel(), game, gameGrid, messageArea, previousMoves);
        updateStatus();  
    }

    /**
     * Undoes the last move made in the game and updates the game grid. 
     */
    public void undoMove() {
        // Check if there are previous moves to undo
        if (!previousMoves.isEmpty()) {
            // Undo the last move
            int[] lastMove = previousMoves.pop();
            int row = lastMove[0];
            int col = lastMove[1];
            boolean wasBomb = game.getCellState(row, col).equals("M");
            game.undoMove(row, col, wasBomb);
            gameGrid.updateButton(row, col);
            updateStatus();
            messageArea.append("Previous move undone.\n");
        } else {
            messageArea.append("No moves to undo.\n");
        }
    }

    /**
     * This function is used to move between game levels.
     * Once the level is changed it resets the lives, updates the game board to the new level
     * and shows a level change message.
     */
    public void toggleLevel() {
        // Change the game level and update the UI components
        switch (game.getLevel()) {
            case "Levels/em1.txt" -> {
                game.changeLevel();
                buttonsMenu.getLevelButton().setText("Next Level");
            }
            case "Levels/em2.txt" -> {
                game.changeLevel();
                buttonsMenu.getLevelButton().setText("Next Level");
            }
            case "Levels/em3.txt" -> {
                game = new Minesweeper("Levels/em1.txt");
                buttonsMenu.getLevelButton().setText("Next Level");
            }
        }
        game.resetLives();  // Reset lives to 3
        gameGrid.setGame(game);
        gameGrid.updateAllButtons();
        updateStatus();
        messageArea.append("Level changed. Lives reset to 3.\n");
    }

    /**
     * This is a simple function that creates a new window that displays the game instructions.
     */
    public void showInstructions() {
        new InstructionsWindow(); 
    }

    /**
     * This is used to switch between "Guess a square" and "Flag a mine" following this it updates the status .
     */
    public void switchMode() {
        currentMode = currentMode.equals("Guess a square") ? "Flag a mine" : "Guess a square";
        updateStatus();
    }

    /**
     * getter Minesweeper game instance.
     */
    public Minesweeper getGame() {
        return game;
    }

    /**
     * Checks if the current move is the same as the previous one.
     */
    private boolean isSameMove(int[] lastMove, int row, int col) {
        return lastMove[0] == row && lastMove[1] == col;
    }

    /**
     * The main method to launch the Minesweeper GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MinesweeperGUI());
    }
}//end of class
