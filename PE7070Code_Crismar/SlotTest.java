import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotTest is used to test the functionality of Slot class.
 * This is a simple class that is meant to hold the position and state of each slot.
 * 
 * @author Mircea-Rafael Crismar
 * @version 2.0
 */
public class SlotTest {
    //Create a simple slot
    private Slot testSlot;

    @BeforeEach
    public void setUp() {
        testSlot = new Slot(2, 2, ""); 
        //This will create a slot on position 0,0 with the state ""
    }

    @Test
    //Tests to see if the current state is the one expected
    public void testGetState() {
        assertEquals("", testSlot.getState(), "Initial state should be ''.");
    }

    @Test
    //Tests to see if the state changes to a new one, in this particular case "M"
    public void testSetState() {
        testSlot.setState("M");
        assertEquals("M", testSlot.getState());
    }

    @Test
    //Testing the getter for row.
    public void testGetRow() {
        assertEquals(2, testSlot.getRow());
    }

    @Test
    //Testing the getter for collumn.
    public void testGetCol() {
        assertEquals(2, testSlot.getCol());
    }
}
