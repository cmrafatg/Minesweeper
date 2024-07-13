import javax.swing.JTextArea;
import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This is the class that handles all the file processing needed for the game.
 * It has methods for saving and loading the game to and from files.
 * It has other secondary methods to check for data validation and error handling
 * 
 * @version 2.0 (Final)
 * @autor Mircea-Rafael Crismar
 */
public class GameFileHandler {

    /**
     * This method creates the save file name based on the current level.
     * It replaces "em" with "lvl" and removes the ".txt" and appends "saved.txt" instead.
     * 
     * @param level is the original level file name
     * @return the name of file where the progress was saved for that level
     */
    private static String getSaveFileName(String level) {
        // Replace "em" with "lvl" and ".txt" with "saved.txt"
        return level.replace("em", "lvl").replace(".txt", "saved.txt");
    }

    /**
     * This method handles the game being saved to the file.
     * 
     * @param playerBoard represents the current game board
     * @param level is the level file
     * @param lives the number of lives left
     * @throws IOException is used in case any input or output errors occur
     */
    public static void saveGame(Slot[][] playerBoard, String level, int lives) throws IOException {
        String fileName = getSaveFileName(level); // Gets the save file for the current level
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(level); // Writes the level 
            writer.newLine(); // Goes on the next line
            writer.write(String.valueOf(lives)); // Writes the number of lives
            writer.newLine();
            int size = playerBoard.length;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    writer.write(playerBoard[i][j].getState() + " "); // Write the state of each slot in the right position
                    
                }
                writer.newLine(); // Goes to the next row in the board
            }
        }
    }

    /**
     * This method was created to load the cell states from the file.
     * 
     * @param level the name of the level file
     * @return a list of strings with the cells states
     * @throws IOException if any error occurs
     * 
     */
    public static List<String[]> loadGame(String level) throws IOException {
        String fileName = getSaveFileName(level); // gets the save file name
        List<String[]> gameState = new ArrayList<>();//creates an empty list
        
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String lvl = reader.readLine(); // Read the level name
            gameState.add(new String[]{lvl});//Adds it to the game
            String lives = reader.readLine(); // Read the number of lives
            gameState.add(new String[]{lives});//Adds it to the game
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(" "); // Split each line into individual cell states
                gameState.add(row);
            }
        }
        
        return gameState;
    }

    /**
     * This method is used to load the cell states into the GUI grid.
     * 
     * @param game the instance of the Minesweeper 
     * @param gameGrid the grid containing all the cells
     * @param messageArea the area that displays all messages to the user
     * @param previousMoves a stack used to keep track of all the moves
     */
    public static void loadGameToGUI(String level, Minesweeper game, GameGrid gameGrid, JTextArea messageArea, Stack<int[]> previousMoves) {
        if (!isSaveFilePresent(level)) {
            messageArea.append("There is no saved game to load for this current level.\n");
            return;
        }
        try {
            List<String[]> gameState = loadGame(level); // Load the game state from the file 
            
            int elementCount = gameState.stream().skip(2).mapToInt(row -> row.length).sum(); 
            // Count the number of elements excluding the first two rows
            if (elementCount == 0) {
                messageArea.append("There is no saved version for this level.\n");
                return;
                //in case there are 0 elemts it returns a message
            }
            
            String loadedLevel = gameState.get(0)[0];//updates the              
            int lives = Integer.parseInt(gameState.get(1)[0]);
            
            game.resetGame(loadedLevel); // Set/Reset the game with the loaded level
            game.setLives(lives); // Set/Reset the number of lives
            
            for (int i = 2; i < gameState.size(); i++) {
                String[] states = gameState.get(i);
                for (int j = 0; j < states.length; j++) {
                    new Assign(game, i - 2, j, states[j]); // Assign the state to the cell in the game grid
                }
            }
            gameGrid.setGame(game); // Set the game in the game grid
            gameGrid.updateAllButtons(); // Update the GUI buttons
            previousMoves.clear(); // Clear previous moves
            
            String levelNumber = loadedLevel.replace("Levels/em", "").replace(".txt", "");
            messageArea.append("Game loaded for Level " + levelNumber + " with " + lives + " lives!\n");
        } catch (IOException e) {
            messageArea.append("Failed to load the saved game.\n");
        }
    }

    /**
     * This is a helper method to check if a save file exists for a given level.
     * 
     * @param level the name of the level file
     * @return true if the save file exists and false if it does not
     */
    public static boolean isSaveFilePresent(String level) {
        String fileName = getSaveFileName(level); 
        File file = new File(fileName);
        return file.exists(); 
    }

    /**
     * This method checks the state of each cell to match the allowed characters
     * 
     * @param cell the state of the cell
     * @return true if the cell state is valid, false if not
     */
    private static boolean isValidCellState(String cell) {
        return cell.equals("M") || cell.equals("-") || cell.equals("?") || cell.equals("") || cell.matches("[0-8]");
    }
}
