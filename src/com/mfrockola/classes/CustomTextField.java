package com.mfrockola.classes;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class is used in the configuration window to have TextField with only centered and limited values
 */
public class CustomTextField extends JTextField {

    // Private variable that will determine how many values ​​the TextField allows
    private int columns;

    // To the constructor we pass as argument the amount of values ​​that will accept the TextField
    public CustomTextField(int columns) {
        setColumns(columns);
        // Adds the listener that will determine if the character limit has been reached
        addKeyListener(new ColumnsKeyAdapter());

        // set CENTER the position of text
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    // If the character limit is exceeded, then keyEvent is consumed.
    private class ColumnsKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            int length = getText().length();

            if (length >= columns) {
                e.consume();
            }
        }
    }

    // public methods to obtain the number of columns from external classes
    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
