import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.util.Stack;

/**
 * This class provides a text-based user interface for the player to interact with the game.
 * @author Lauren Scott
 * @version student sample code
 */
public class UI {
    private final Minesweeper thegame; // this is the game model
    private final Scanner reader; // this scanner is used to read the terminal
    private final Stack<int[]> previousMoves; // this keeps track of all the moves made

    /**
     * Constructor for the class UI
     */
    public UI() {
        thegame = new Minesweeper();
        reader = new Scanner(System.in);
        // this is the user's choice from the menu
        String menuChoice = "";
        previousMoves = new Stack<>(); // new stack to keep track of moves

        while (!menuChoice.equalsIgnoreCase("Q") && thegame.checkWin().equals("continue")) {
            displayGame();
            menu();
            menuChoice = getChoice();
        }
        if (thegame.checkWin().equals("won")) {
            winningAnnouncement();
        } else if (thegame.checkWin().equals("lives")) {
            livesAnnouncement();
        }
    }

    /**
     * Method that outputs an announcement when the user has won the game
     */
    public void winningAnnouncement() {
        System.out.println("\nCongratulations, you solved the level");
    }

    /**
     * Method that outputs an announcement when the user has lost due to lack of lives
     */
    public void livesAnnouncement() {
        System.out.println("\nYou have run out of lives, the game is over");
    }

    /**
     * Method that displays the game for the user to play
     */
    public void displayGame() {
        System.out.print("\n\nCol    ");
        for (int r = 0; r < thegame.getGameSize(); r++) {
            System.out.print(r + " ");
        }
        for (int i = 0; i < thegame.getGameSize(); i++) {
            System.out.print("\nRow  " + i);
            for (int c = 0; c < thegame.getGameSize(); c++) {
                System.out.print(" " + thegame.getCellState(i, c));
            }
        }
        System.out.println(); // Ensure the display is refreshed correctly
    }

    /**
     * Method that displays the menu to the user
     */
    public void menu() {
        System.out.println("\nPlease select an option: \n" + "[F] Flag a mine\n" + "[G] Guess a square\n" + "[S] Save game\n" + "[L] Load saved game\n" + "[U] Undo move\n" + "[C] Clear game\n" + "[Q] Quit game\n");
    }

    /**
     * Method that gets the user's choice from the menu and conducts the activities accordingly
     * @return the choice the user has selected
     */
    public String getChoice() {
        String choice = reader.next().toUpperCase();
        if (choice.equalsIgnoreCase("F")) {
            int row = getValidCoordinate("row");
            int col = getValidCoordinate("column");
            if (row != -1 && col != -1) {
                System.out.print(thegame.makeMove(Integer.toString(row), Integer.toString(col), choice));
                if (previousMoves.isEmpty() || !isSameMove(previousMoves.peek(), row, col)) {
                    previousMoves.push(new int[]{row, col});
                }
            }
        } else if (choice.equalsIgnoreCase("G")) {
            int row = getValidCoordinate("row");
            int col = getValidCoordinate("column");
            if (row != -1 && col != -1) {
                System.out.print(thegame.makeMove(Integer.toString(row), Integer.toString(col), choice));
                if (previousMoves.isEmpty() || !isSameMove(previousMoves.peek(), row, col)) {
                    previousMoves.push(new int[]{row, col});
                }
                displayGame(); // Refresh the display after a move
            }
        } else if (choice.equalsIgnoreCase("S")) {
            saveGame();
        } else if (choice.equalsIgnoreCase("U")) {
            undoMove();
        } else if (choice.equalsIgnoreCase("L")) {
            loadGame();
        } else if (choice.equalsIgnoreCase("C")) {
            clearGame();
        } else if (choice.equalsIgnoreCase("Q")) {
            System.exit(1);
        }
        return choice;
    }

    /**
     * Method to get a valid row or column coordinate from the user.
     * @param type The type of coordinate to get (either "row" or "column").
     * @return The valid coordinate, or -1 if invalid.
     */
    private int getValidCoordinate(String type) {
        int coord = -1;
        // Prompt the user for the coordinate
        System.out.print("Which " + type + " you want to select? ");
    
        // Check if the input is an integer
        if (reader.hasNextInt()) {
            coord = reader.nextInt();
            // Validate the coordinate
            if (coord < 0 || coord >= thegame.getGameSize()) {
                System.out.println("Invalid " + type + ". You need to input a number between 0 and " + (thegame.getGameSize() - 1));
                coord = -1;
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
            reader.next(); // Clear the invalid input
        }
        return coord;
    }

    /**
     * This method deals with saving the current board to a certain file. 
     * It accomplishes this by going through each cell starting from the left on the first row and writing 
     * its state to the file leaving an empty space between them. Once a row is complete it moves 
     * onto the next one, this helps keeping the position of the cells saved as well without any 
     * additional data. 
     * Once it finishes if it is successful, it outputs a message to the user.
     */
    public void saveGame() {
        // Creates a BufferedWriter object named 'writer' to write game state data to the file "gameSaved.txt"
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("gameSaved.txt"))) {
            // Create a double loop that goes through each cell in the game board
            for (int i = 0; i < thegame.getGameSize(); i++) {
                for (int j = 0; j < thegame.getGameSize(); j++) {
                    // Write the state of the cell 
                    writer.write(thegame.getCellState(i, j) + " ");
                }
                // Once the whole row is saved it moves onto the next one
                writer.newLine();
            }
            System.out.println("Game saved successfully!"); // Message to output if the game was saved
        } catch (IOException e) {
            System.out.println("Game did not save!"); // Print an error message if saving fails
        }
    }

    /**
     * undoMove 
     * This ‘undoMove’ method was created to allow the user to reverse their last move during the game. 
     * While the user is playing the game each move, they make is added onto a stack. 
     * A stack is a dynamic data structure that follows the last in first out principle, which means the last move will always 
     * be on the top. This allows us to remove the last move from the stack and reset the state of that cell to the initial value 
     * which is “”. After all of this is executed successfully a message is sent to the user, once there are no more items in the 
     * stack, the user gets an appropriate message.
     */
    public void undoMove() {
        if (!previousMoves.isEmpty()) {
            // Removes the last element from the stack
            int[] lastElement = previousMoves.pop();
            // Saves the coordinates of it
            int row = lastElement[0];
            int col = lastElement[1];
    
            // Reset the state of the cell to its initial state
            String initialState = "";
            new Assign(thegame, row, col, initialState);
    
            // Confirm that the previous move was removed
            System.out.println("Previous move was removed.");
        } else {
            // Notify if there are no previous moves to remove anymore
            System.out.println("No previous moves to remove.");
        }
    }

    /**
     * loadGame
     * To be implemented by student - this method should load a previous saved game
     * This method is similar with the saveGame one but this one goes through each row in the file and reads the states
     * These states are then assigned to their respective cells based on their position in the file
     * It also keeps track of the number of rows that were read, so it matches the size of the board game
     * Depending on the success of the function a message is displayed to inform the user
     */
    public void loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader("gameSaved.txt"))) {
            int gameSize = thegame.getGameSize();
            String line;
            int i = 0;

            while ((line = reader.readLine()) != null && i < gameSize) {
                String[] states = line.split(" ");
                for (int j = 0; j < gameSize && j < states.length; j++) {
                    new Assign(thegame, i, j, states[j]);
                }
                i++;
            }

            if (i == gameSize) {
                System.out.println("Game loaded from saved file.");
            } else {
                System.out.println("Unexpected error occurred.");
            }

        } catch (IOException e) {
            System.out.println("Failed to load the saved game.");
        }
    }

    /**
     * clearGame
     * To be implemented by student - this method should clear the game board and any record of moves, to reset the game
     * The method creates a double loop to go through each cell of the board and resets the states of them to the initial value
     * The stack of moves is also cleared followed by a success message.
     */
    public void clearGame() {
        // Resets every cell in the game to the initial state
        for (int i = 0; i < thegame.getGameSize(); i++) {
            for (int j = 0; j < thegame.getGameSize(); j++) {
                new Assign(thegame, i, j, "");
            }
        }
        // Resets the stack of moves
        previousMoves.clear();
        System.out.println("Game restarted successfully.");
    }

    /**
     * The main method within the Java application. 
     * It's the core method of the program and calls all others.
     */
    public static void main(String[] args) {
        new UI();
    }

    /**
     * Method to check if the current move is the same as the previous move
     * @param lastMove The last move made
     * @param row The row of the current move
     * @param col The column of the current move
     * @return true if the current move is the same as the last move, false otherwise
     */
    private boolean isSameMove(int[] lastMove, int row, int col) {
        return lastMove[0] == row && lastMove[1] == col;
    }
} // end of class
