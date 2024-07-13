/**
 * The ButtonMaker class is a method to create JButtons with text and action listener.
 * It helps simplify the creation of buttons in the GUI.
 * 
 * @author Mircea-Rafael Crismar
 * @version 1.0
 */
import javax.swing.*;
import java.awt.event.ActionListener;

public class ButtonMaker {
    /**
     * Creates a JButton with the given text and attaches an ActionListener to it.
     *
     * @param text the text on the button
     * @param listener the ActionListener attached to the button
     * @return the created JButton
     */
    public static JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }
}
