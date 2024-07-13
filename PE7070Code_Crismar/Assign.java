/**
 * Assign
 * This class handles the creation of all moves in the game
 * 
 * @author Lauren Scott
 * @version student final
 */
public class Assign {
    private int col, row; // The column and row being assigned
    private Minesweeper game; // The Minesweeper game instance
    Slot[][] moves; // 2D Array to store the game's moves

    /**
     * Constructor for Assign class.
     * This initializes the game, row, column, and moves. It then assigns the state to the selected slot.
     * @param game - the Minesweeper game instance
     * @param row - the row the user has selected
     * @param col - the column the user has selected
     * @param state - the state to be assigned
     */
    public Assign(Minesweeper game, int row, int col, String state) {
        this.game = game;
        this.col = col; 
        this.row = row; 
        this.moves = game.getMoves(); 
        assignMove(state);
    }

    /**
     * assignMove
     * This method assigns the state to the specified slot in the game.
     * @param state the state to set in the move (e.g., "M" for mine, "G" for guess)
     */
    public void assignMove(String state) {
        // Check if moves array is valid and position is within bounds
        if (moves != null && row >= 0 && row < moves.length && col >= 0 && col < moves[0].length) {
            moves[row][col].setState(state); // Set the state
        } else {
            System.out.println("Invalid move position or moves array not initialized.");
        }
    }

    /**
     * getRow
     * This method returns the current row value for this move.
     * @return the row value
     */
    public int getRow() {
        return row; // Return current row
    }

    /**
     * getCol
     * This method returns the current column value for this move.
     * @return the column value
     */
    public int getCol() {
        return col; // Return current column
    }
}
