import java.io.FileWriter;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.io.IOException;
import org.junit.jupiter.api.Test;

/**
 * The test class AssignTest is used to test the functionality of Assign class.
 * This class is meant to handle the assignment of moves in the Minesweeper game.
 * 
 * @version 2.0
 */
public class AssignTest {
    private Assign assign;
    private Minesweeper game;
    private final String levelFilePath = "Levels/em1.txt";

    @BeforeEach
    public void setUp() throws IOException {
        game = new Minesweeper(); // Initialize with default level file if needed
        assign = new Assign(game, 4, 4, "1"); // Use a valid index within the grid bounds
    }

    @Test
    public void testConstructor() {
        assertEquals(4, assign.getRow()); // Check if the row is set correctly
        assertEquals("1", game.getIndividualMove(4, 4)); // Verify the state at the specified position
    }

    @Test
    public void testAssignMove() {
        assign.assignMove("2");
        assertEquals("2", game.getIndividualMove(4, 4)); // Verify the new state at the specified position
    }

    @Test
    public void testGetRow() {
        assertEquals(4, assign.getRow()); // Check if the row getter works correctly
    }
}
