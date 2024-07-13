import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This is the class Minesweeper, and it handles the functionality and logic of the game.
 * It is a complex class that deals with all the logic and checking the conditions for the game
 * while processing the user inputs.
 * 
 * @author Lauren Scott and Mircea-Rafael Crismar
 * @version Student Final Code
 */
public class Minesweeper {
    private String[][] gameBoard; // This array stores the board currently displayed to the game
    private Slot[][] playerBoard; // This is the board of moves for the game
    private Scanner reader; // This scanner is used to read the game and level files
    private int gameSize; // This will be the size of the game
    private String level; // This is the level file, changeable for easy and hard
    private int lives = 3; //This initializes the lives to 3

    /**
     * Giving we need to initialize games from level 1 when we start the game
     * and level 2 or 3 as we progress we have a default constructor.
     */
    public Minesweeper() {
        this("Levels/em1.txt");
    }

    /**
     * Another constructor which takes as a parameter the level we want to initialize.
     * @param level The level file to load.
     */
    public Minesweeper(String level) {
        this.level = level;
        initializeGame();
    }

    /**
     * Initializes the game by reading the level file, 
     */
    private void initializeGame() {
        try {
            reader = new Scanner(new File(level)); 
            // Creates the scanner with a specific level file 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        gameSize = calculateGameSize(); // Calculates the game size
        gameBoard = new String[gameSize][gameSize]; // Initialize the game board 
        playerBoard = new Slot[gameSize][gameSize]; // Initialize the player board
        initializePlayerBoard(); // Sets up the player board
        readLevelFile(); // Uses level file to create the game board
    }

    /**
     * Initializes the player board by creating a slot object for every cell in the grid.
     * It initializes the state of the cell to "".
     */
    private void initializePlayerBoard() {
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                playerBoard[i][j] = new Slot(i, j, ""); // Create a new Slot object for each cell
            }
        }
    }

    /**
     * @return the moves in the game
     */
    public Slot[][] getMoves() {
        return playerBoard;
    }

    /**
     * This method gets an individual cell's state
     * @param row - the row of the move
     * @param col - the column of the move
     * @return The state of that cell
     */
    public String getIndividualMove(int row, int col) {
        return playerBoard[row][col].getState();
    }

    /**
     * This method reads the game size from the file 
     * @return the size of the puzzle
     */
    public int calculateGameSize() {
        return Integer.parseInt(reader.next());
    }

    /**
     * This method provides access to the gameSize from other classes
     * @return the size of the puzzle
     */
    public int getGameSize() {
        return gameSize;
    }

    /**
     * This method reads the level file to populate the game
     * @return The moves stored in the file
     */
    public String[][] readLevelFile() {
        while (reader.hasNext()) {
            int row = Integer.parseInt(reader.next());
            int col = Integer.parseInt(reader.next());
            String move = reader.next();
            
            gameBoard[row][col] = move;
            playerBoard[row][col].setState("");
        }
        return gameBoard;
    }

    /**
     * This method checks whether the game has been won
     * @return whether the game has been won
     */ 
    public String checkWin() {
        if (lives <= 0) {
            return "lost";
        }

        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if (!gameBoard[i][j].equals("M") && playerBoard[i][j].getState().equals("")) {
                    return "continue";
                }
            }
        }
        return "won";
    }

    /**
     * 
     * Changes the game to the next difficulty.
     */
    public void changeLevel() {
        if (level.equals("Levels/em1.txt")) {
            level = "Levels/em2.txt";
            System.out.println("Difficulty: Medium");
        } else if (level.equals("Levels/em2.txt")) {
            level = "Levels/em3.txt";
            System.out.println("Difficulty: Hard");
        } else {
            System.out.println("You've completed all levels!");
            System.exit(0);
        }
        initializeGame();
    }

    /**
     * Resets the number of lives to the initial value of 3.
     */
    public void resetLives() {
        this.lives = 3;
    }

    /**
     * This method allows a user to make a move in the game
     * @param row - the row of the move
     * @param col - the column of the move
     * @param guess - the guess they are making
     * @return the result of the move
     */
    public String makeMove(String row, String col, String guess) {
        int enteredRow = Integer.parseInt(row);
        int enteredCol = Integer.parseInt(col);
        String currentState = playerBoard[enteredRow][enteredCol].getState();
        // Checks for the flag mode
        if (guess.equals("F")) {
            if (currentState.equals("?")) {
                new Assign(this, enteredRow, enteredCol, "");
                return "Cell not flagged.";//In case the cell is already flagged it removes the flag
            } else if (currentState.equals("")) {
                new Assign(this, enteredRow, enteredCol, "?");//Otherwise it adds the flag
                return "Cell flagged.";
            }
        }

        if (currentState.equals("?") && guess.equals("G")) {
            return "You need to unflag this cell in order to guess.";
            //Message in case the user tries to guess a flagged cell
        }

        if (!currentState.equals("") && !currentState.equals("?")) {
            return "This cell has already been revealed.";
            //Message in case the user clicks on a revealed cell again
        }

        if (guess.equals("G")) {
            if (gameBoard[enteredRow][enteredCol].equals("M")) {
                lives -= 1;
                new Assign(this, enteredRow, enteredCol, "M");
                return "You have lost one life. \nNew life total: " + lives;
                //Logic for losing a life when you guess a mine
            } else if (gameBoard[enteredRow][enteredCol].equals("-")) {
                new Assign(this, enteredRow, enteredCol, "-");
                //Logic for empty spaces
            } else {
                new Assign(this, enteredRow, enteredCol, gameBoard[enteredRow][enteredCol]);
                //Logic for displaying the state/number of that cell
            }
            return "Good move, keep guessing!";
        } 
        return "This method it not a permitted option";
        //Error message if the guess is anything else but "M" or "G"
    }

    /**
     * This method gets the current state of an individual cell
     * @param row - the row of the cell
     * @param col - the column of the cell
     * @return the state of the cell
     */
    public String getCellState(int row, int col) {
        return playerBoard[row][col].getState();
    }

    /**
     * This method gets the current number of lives
     * @return the number of lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets the number of lives.
     * @param lives the number of lives to set
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * This method is used to undo the last move made.
     * @param row the row of the cell to undo
     * @param col the column of the cell to undo
     * @param wasBomb whether the cell was a bomb
     */
    public void undoMove(int row, int col, boolean wasBomb) {
        //If the last move was a bomb it will give a life back
        if (wasBomb) {
            lives += 1;
        }
        playerBoard[row][col].setState("");//Resets to initial state
    }

    /**
     * Gets the game level fil.
     * @return the current level file
     */
    public String getLevel() {
        return level;
    }

    /**
     * Resets the game with a level file
     * @param level the level file to reset the game with
     */
    public void resetGame(String level) {
        this.level = level;
        initializeGame();
    }
}
