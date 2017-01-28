package com.mfrockola.classes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;

/*
	This class will be used to execute MFRockola. Since the configuration must be external, a splash is made so that the
	user can press a key that opens the configuration window. It also has additional controls that determine if some
 	MFRockola requirements are well configured
*/


@SuppressWarnings("serial")
public class Splash extends JFrame implements Runnable {
	// Determines whether the mouse can be moved or locked in the lower right corner of the screen
	public static boolean moveMouse = true;

	// constructor without attributes
	public Splash() {
        initComponents();
	}

	// Generate the splash window and init components
	public void initComponents() {
		// Will load JPanel external class that has the ability to have a background image
		JEImagePanel panel = new JEImagePanel(
				this.getClass().getResource("/com/mfrockola/imagenes/fondoSmall.jpg"));

		setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/mfrockola/imagenes/icono.png")));
		panel.setLayout(new BorderLayout());
		JLabel labelText = new JLabel("Presione Q para configurar MFRockola ");
		labelText.setForeground(Color.WHITE);
		labelText.setFont(new Font("Calibri", Font.BOLD, 16));
		labelText.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(labelText,BorderLayout.SOUTH);
		getContentPane().add(panel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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

		final Timer temporizador = new Timer(1000*2, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File("config.mfr");
				if (file.exists()) {
					file = null;
					new Interfaz();
					dispose();
				} else {
					file = null;
					moveMouse = false;
					new Configuracion();
					dispose();
				}
			}
		});
		
		temporizador.setRepeats(false);
		temporizador.start();
		
		addKeyListener(new KeyAdapter()	{
			public void keyReleased(KeyEvent evento) {
				if (evento.getKeyCode()==81) {
					temporizador.stop();
					moveMouse = false;
					new Configuracion();
					dispose();
				}
				else {
					evento.consume();
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
			robot.mouseMove((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
					(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		}
	}
}
