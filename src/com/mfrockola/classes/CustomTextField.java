package com.mfrockola.classes;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Angel C on 16/05/2016.
 */
public class CustomTextField extends JTextField {

    private int columns;

    public CustomTextField(int columns) {
        setColumns(columns);
        addKeyListener(new ColumnsKeyAdapter());
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    private class ColumnsKeyAdapter extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            int length = getText().length();

            if (length >= columns) {
                e.consume();
            }
        }
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }
}
