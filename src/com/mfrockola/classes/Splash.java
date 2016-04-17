package com.mfrockola.classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
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

@SuppressWarnings("serial")
public class Splash extends JFrame implements Runnable
{
	public Splash()
	{
		URL url = this.getClass().getResource("/com/mfrockola/imagenes/fondoSmall.jpg");
		System.out.println(url);
		JEImagePanel panel = new JEImagePanel(url);

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
	
	
	public static void main(String args [])
	{
		try
		{
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		new Thread(new Splash()).start();
	}
	
	public void run()
	{
		final Timer temporizador = new Timer(1000*10, new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				new Interfaz();
				dispose();
			}
		});
		
		temporizador.setRepeats(false);
		temporizador.start();
		
		addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent evento)
			{
				if (evento.getKeyCode()==81)
				{
					temporizador.stop();
					new Configuracion();
					dispose();
				}
				else
				{
					evento.consume();
				}
			}
		});
	}
}
