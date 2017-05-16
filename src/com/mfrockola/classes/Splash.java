package com.mfrockola.classes;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;

/**
	This class will be used to execute MFRockola. Since the configuration must be external, a splash is made so that the
	user can press a key that opens the configuration window. It also has additional controls that determine if some
 	MFRockola requirements are well configured
*/

public class Splash extends JFrame implements Runnable {
	// Determines whether the mouse can be moved or locked in the lower right corner of the screen
	static boolean moveMouse = true;

	// constructor without attributes
	Splash() {
        initComponents();
	}

	// Generate the splash window and init components
	private void initComponents() {
		// Will load JPanel external class that has the ability to have a background image
		BackgroundImagePanel panel = new BackgroundImagePanel(
				this.getClass().getResource("/com/mfrockola/imagenes/fondoSmall.png"));

		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/mfrockola/imagenes/icono.png")));
		panel.setLayout(new BorderLayout());
		JLabel labelText = new JLabel("Presione Q para configurar MFRockola ");
		labelText.setForeground(Color.WHITE);
		labelText.setFont(new Font("Calibri", Font.BOLD, 16));
		labelText.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(labelText,BorderLayout.SOUTH);
		getContentPane().add(panel);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(480,300);
		setLocationRelativeTo(null);
		setUndecorated(true);
		this.setVisible(true);
	}
	
	// Starts MFRockola
    public static void main(String args [])	{
		try {
            // Change the look and feel of windows to the running environment that is Windows
            JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

        // Execute an additional thread for the time we have to press Q and hold the mouse in the lower right corner
        new Thread(new Splash()).start();
	}

    // thread for the time we have to press Q and hold the mouse in the lower right corner
	public void run() {

		final Timer timer = new Timer(1000*2, e -> {
            File file = new File("config.json");
            if (file.exists()) {
                file = null;
                new Interface();
                dispose();
            } else {
                file = null;
                moveMouse = false;
                new SettingsWindow();
                dispose();
            }
        });
		
		timer.setRepeats(false);
		timer.start();

		// These instructions listen to the keyboard events, in the event that the Q key is pressed,
		// the configuration window opens and unlocks the movement of the mouse
		addKeyListener(new KeyAdapter()	{
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode()==81) {
					timer.stop();
					moveMouse = false;
					new SettingsWindow();
					dispose();
				}
				else {
					e.consume();
				}
			}
		});

		Robot robot = null;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		while (moveMouse) {
			if (robot != null) {
				robot.mouseMove((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                        (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
			}
		}
	}
}
