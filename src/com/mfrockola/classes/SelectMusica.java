package com.mfrockola.classes;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SelectMusica
{
	JPanel panel = new JPanel();
	JLabel selectorMusica = new JLabel("- - - -");
	String valores [] = {"-","-","-","-"};
	String numero = String.format("%s %s %s %s", valores[0],valores[1],valores[2],valores[3]);
	int contador = 0;
	boolean reproducir = false;
	private int keyDelete;
	
	public SelectMusica (int keyDelete)
	{
		selectorMusica.setFont(new Font("Consolas", Font.BOLD, 30));
		selectorMusica.setOpaque(false);
		selectorMusica.setForeground(Color.WHITE);
		selectorMusica.setHorizontalAlignment(SwingUtilities.CENTER);
		this.keyDelete = keyDelete;
	}
	
	public void reiniciarValores()
	{
		valores [0] = "-";
		valores [1] = "-";
		valores [2] = "-";
		valores [3] = "-";
	}
	
	public JLabel numeros()
	{
		selectorMusica.setText(numero);
		return selectorMusica;
	}
	
	public String manejadorDeEvento(KeyEvent evento)
	{
		
		if (evento.getKeyCode()== 48 || evento.getKeyCode()==96)
		{
			valores[contador] = "0";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
		}
		else if (evento.getKeyCode()== 49 || evento.getKeyCode()==97)
		{
			valores[contador] = "1";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
			}
		else if (evento.getKeyCode()== 50 || evento.getKeyCode()==98)
		{
			valores[contador] = "2";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
			}
		else if (evento.getKeyCode()== 51 || evento.getKeyCode()==99)
		{
			valores[contador] = "3";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
		}
		else if (evento.getKeyCode()== 52 || evento.getKeyCode()==100)
		{
			valores[contador] = "4";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
		}
		else if (evento.getKeyCode()== 53 || evento.getKeyCode()==101)
		{
			valores[contador] = "5";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
		}
		else if (evento.getKeyCode()== 54 || evento.getKeyCode()==102)
		{
			valores[contador] = "6";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
		}
		else if (evento.getKeyCode()== 55 || evento.getKeyCode()==103)
		{
			valores[contador] = "7";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
		}
		else if (evento.getKeyCode()== 56 || evento.getKeyCode()==104)
		{
			valores[contador] = "8";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
		}
		else if (evento.getKeyCode()== 57 || evento.getKeyCode()==105)
		{
			valores[contador] = "9";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
		}
		
		
		if (evento.getKeyCode()==keyDelete && contador > 0)
		{
			contador--;
			valores[contador] = "-";
			numero = String.format("%s %s %s %s",valores[0],valores[1],valores[2],valores[3]);
		}
		else
		{
			if (contador < 3)
				++contador;
			else
			{
				reproducir = true;
				contador = 0;
			}	
		}
		
		
		return numero;
	}
}