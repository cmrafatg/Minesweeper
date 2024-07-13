/**
 * The InstructionsWindow class makes a window to show game instructions.
 * 
 * @author Mircea-Rafael Crismar
 * @version 2.0
 */
import javax.swing.*;
import java.awt.*;

public class InstructionsWindow extends JFrame {
    /**
     * Creates the window size, title, and text area with instructions.
     */
    public InstructionsWindow() {
        setTitle("Instructions"); // Window title
        setSize(400, 300); // Window size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the window when done

        // Create the text area for instructions
        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setText("Instructions:\n\n" +
                "1. Click on a cell in guess mode to reveal it.\n" +
                "2. Switch to Flag mode if you think you found a mine.\n" +
                "3. You can't guess a flagged cell unless you unflag it.\n" +
                "4. Avoid revealing mines to stay alive.\n" +
                "5. Use the 'Switch to' button to change modes.\n" +
                "6. The game ends when all non-mine squares are revealed or you lose all your lives.\n");
        instructionsArea.setEditable(false); // Make the text area not editable
        instructionsArea.setLineWrap(true); // Wrap lines to fit the area
        instructionsArea.setWrapStyleWord(true); // Wrap at word boundaries
        add(new JScrollPane(instructionsArea), BorderLayout.CENTER); // Add scroll pane with text area to the center

        // Create the OK button to close the window
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose()); // Close the window when the button is clicked
        add(okButton, BorderLayout.SOUTH); // Add the button to the bottom

        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true); // Show the window
    }
}
