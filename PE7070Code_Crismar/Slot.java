import java.util.Observable;

/**
 * This class was created to represent a single cell in the Minesweeper game.
 * It keeps track of the position and state. 
 * The state can take the values: empty, flagged, or a mine.
 * 
 * @author Lauren Scott & Mircea-Rafael Crismar
 * @version student final
 */
public class Slot extends Observable {
    private String state; // What is in the cell right now
    private final int row;
    private final int col; // Where the cell is in the game grid

    /**
     * This initializes a new cell with a row, column, and state.
     * 
     * @param row The row number of the cell
     * @param col The column number of the cell
     * @param state What is in the cell at the start
     */
    public Slot(int row, int col, String state) {
        this.row = row;
        this.col = col;
        this.state = state;
    }

    /**
     * This is a getter for the state of the cell.
     * @return The state of the cell as a String
     */
    public String getState() {
        return state;
    }

    /**
     * This changes the state of the cell and tells others if it changed.
     * @param state The new state to set for the cell
     */
    public void setState(String state) {
        if (!this.state.equals(state)) { // Only tell others if the state actually changes
            this.state = state;
            setChanged();
            notifyObservers();//Notifies observers when the state changes
        }
    }

    /**
     * This gets the row number of the cell.
     * @return The row number of the cell
     */
    public int getRow() {
        return row;
    }

    /**
     * This gets the column number of the cell.
     * @return The column number of the cell
     */
    public int getCol() {
        return col;
    }
}
