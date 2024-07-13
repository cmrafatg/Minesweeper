import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The following tests are meant for the Minesweeper game.
 * They test different functionalities of the game including initializing,
 * game size calculation, move making, and many more each explained below.
 * 
 * @version 1.0
 */
public class MinesweeperTest {
    private Minesweeper minesweeper;

    /**
     * This method initializes a new game for each test.
     */
    @BeforeEach
    public void setUp() {
        minesweeper = new Minesweeper();
    }

    /**
     * Tests that the size of the game matches the one in the file.
     */
    @Test
    public void testCalculateGameSize() {
        assertEquals(5, minesweeper.getGameSize());
    }

    /**
     * Tests that the current state of the different cells matches the ones in the level file.
     */
    @Test
    public void testReadLevelFile() {
        String[][] gameBoard = minesweeper.readLevelFile();
        assertEquals("M", gameBoard[0][0]);
        assertEquals("1", gameBoard[0][1]);
        assertEquals("-", gameBoard[0][2]);
        assertEquals("2", gameBoard[0][3]);
        assertEquals("M", gameBoard[0][4]);
    }

    /**
     * Test if the new game board has all cells initialized with ""
     * as that should be their current state.
     */
    @Test
    public void testBoardInitialization() {
        Slot[][] moves = minesweeper.getMoves();
        for (int i = 0; i < minesweeper.getGameSize(); i++) {
            for (int j = 0; j < minesweeper.getGameSize(); j++) {
                assertEquals("", moves[i][j].getState());
            }
        }
    }

    /**
     * Tests that flagged cells are marked with "?" and cannot be guessed again.
     */
    @Test
    public void testIncorrectFlagging() {
        minesweeper.makeMove("0", "0", "F"); // Make a flag move on a mine
        assertEquals("?", minesweeper.getCellState(0, 0)); // Checks if the state updates
        String result = minesweeper.makeMove("0", "0", "G"); // Makes a guess move on the same cell
        assertEquals("You need to unflag this cell in order to guess.", result); // It should not allow you to guess a flagged cell
    }

    /**
     * Test if the game handles commands in different cases.
     */
    @Test
    public void testCaseSensitivity() {
        String result1 = minesweeper.makeMove("4", "0", "G");
        String result2 = minesweeper.makeMove("4", "0", "g");
        assertEquals(result2, "This cell has already been revealed.");
    }

    /**
     * Tests if the game does not allow repeated selection of the same cell.
     */
    @Test
    public void testClickOnTheSameCell() {
        String result = minesweeper.makeMove("1", "4", "G");
        assertEquals("You have lost one life. \nNew life total: 2", result);
        result = minesweeper.makeMove("1", "4", "G");
        assertEquals("This cell has already been revealed.", result);
    }

    /**
     * Tests if the game displays the win message when all non-mine cells are uncovered.
     */
    @Test
    public void testWinningCondition() {
        // uncover all non-mine cells
        minesweeper.makeMove("0", "1", "G");
        minesweeper.makeMove("0", "2", "G");
        minesweeper.makeMove("0", "3", "G");
        minesweeper.makeMove("1", "0", "G");
        minesweeper.makeMove("1", "1", "G");
        minesweeper.makeMove("1", "2", "G");
        minesweeper.makeMove("1", "3", "G");
        minesweeper.makeMove("2", "0", "G");
        minesweeper.makeMove("2", "1", "G");
        minesweeper.makeMove("2", "2", "G");
        minesweeper.makeMove("2", "3", "G");
        minesweeper.makeMove("2", "4", "G");
        minesweeper.makeMove("3", "0", "G");
        minesweeper.makeMove("3", "1", "G");
        minesweeper.makeMove("3", "2", "G");
        minesweeper.makeMove("3", "3", "G");
        minesweeper.makeMove("3", "4", "G");
        minesweeper.makeMove("4", "1", "G");
        minesweeper.makeMove("4", "2", "G");
        minesweeper.makeMove("4", "3", "G");
        minesweeper.makeMove("4", "4", "G");

        assertEquals("won", minesweeper.checkWin());
    }
}
