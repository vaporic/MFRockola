package com.mfrockola.classes;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Angel C on 20/05/2016.
 */
public class TextFieldKey extends JTextField {

    private Frame frame;
    private char keyChar;
    private int keyCode;

    public TextFieldKey(Frame parentDialog, char keyChar, int keyCode) {

        setKeyChar(keyChar);
        setKeyCode(keyCode);
        frame = parentDialog;
        setHorizontalAlignment(SwingConstants.CENTER);
        setEditable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PressKeyDialog dialog = new PressKeyDialog(frame,true);
                dialog.setVisible(true);
                dialog.pack();
                if (dialog.keyCodeDialog!=0)
                    setKeyCode(dialog.keyCodeDialog);
                    setText(Utils.printKeyCharCode(dialog.keyCodeDialog));
            }
        });

    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public char getKeyChar() {
        return keyChar;
    }

    public void setKeyChar(char keyChar) {
        this.keyChar = keyChar;
    }

    private class PressKeyDialog extends JDialog {

        private JLabel label;
        public char keyCharDialog;
        public int keyCodeDialog;

        public PressKeyDialog (Frame parent, boolean modal) {

            super(parent,modal);

            initComponents();

            setLocationRelativeTo(parent);

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    keyCharDialog = e.getKeyChar();
                    keyCodeDialog = e.getKeyCode();
                    dispose();
                }
            });
        }

        public void initComponents() {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setSize(200,100);
            label = new JLabel("Presione una tecla");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            getContentPane().add(label,BorderLayout.CENTER);
        }
    }
}
