import javax.swing.*;
import java.awt.*;

/**
 * The MessageArea class makes creates an area to display all the messages to the user
 * 
 * @author Mircea-Rafael Crismar
 * @version 2.0
 */
public class MessageArea extends JPanel {
    private final JTextArea messageArea; // Area to show the messages

    /**
     * Sets up the text area with font, size, and layout.
     */
    public MessageArea() {
        messageArea = new JTextArea(5, 20); // Text area with 5 rows and 20 columns
        messageArea.setFont(new Font("Courier New", Font.BOLD, 12)); // Set font style and size
        messageArea.setEditable(false); // Make text area not editable
        setLayout(new BorderLayout()); // Set layout for the panel
        add(new JScrollPane(messageArea), BorderLayout.CENTER); // Add scroll pane with text area to the center
    }

    /**
     * Method to get the text area.
     * @return the message area
     */
    public JTextArea getMessageArea() {
        return messageArea; // Return the text area
    }
}
