package com.mfrockola.classes;

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

    public TextFieldKey(Frame parentDialog) {

        frame = parentDialog;
        setHorizontalAlignment(SwingConstants.CENTER);
        setEditable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PressKeyDialog dialog = new PressKeyDialog(frame,true);
                dialog.setVisible(true);
                dialog.pack();
                if (dialog.keyCode!=0)
                    setText(String.valueOf(dialog.keyCode));
            }
        });

    }

    private class PressKeyDialog extends JDialog {

        private JLabel label;
        public int keyCode;

        public PressKeyDialog (Frame parent, boolean modal) {

            super(parent,modal);

            initComponents();

            setLocationRelativeTo(parent);

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    keyCode = e.getKeyCode();
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
