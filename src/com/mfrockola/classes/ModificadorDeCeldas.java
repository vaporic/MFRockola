package com.mfrockola.classes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

@SuppressWarnings({ "rawtypes", "serial" })
public class ModificadorDeCeldas extends JLabel implements ListCellRenderer
{
	private Color color1;
	private Color color2;
	
	public ModificadorDeCeldas(Font fuente,Color foreground, Color color1, Color color2)
	{
		setOpaque(true);
		setFont(fuente);
		setForeground(foreground);
		this.color1 = color1;
		this.color2 = color2;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) 
	{


		if (value == null) {
			setText("Sin canciones en reproducción");
		} else setText(value.toString());
		
		if(index % 2 == 0 && !isSelected)
			setBackground(color1);
		else if (isSelected) {
			setBackground(Color.BLUE);
		} else {
			setBackground(color2);
		}

		return this;
	}

}
