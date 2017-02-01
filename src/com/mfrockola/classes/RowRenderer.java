package com.mfrockola.classes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/*
	This class renders the background of the cells that make up the music lists
 */

// Extend JLabel and implement ListCellRenderer
public class RowRenderer extends JLabel implements ListCellRenderer {
	// We put the two background colors as private variables
	private Color mColor1;
	private Color mColor2;

	// Public constructor with a font, a foreground color, and two background colors as attributes
	public RowRenderer(Font font, Color foreground, Color color1, Color color2) {
		// As we extend from JLabel we can call your methods
		setOpaque(true);
		setFont(font);
		setForeground(foreground);
		this.mColor1 = color1;
		this.mColor2 = color2;
	}

	// We override the ListCellRenderer method that is in charge of determining the state of the cells of a list.
	// Accordingly, we apply the corresponding colors
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		// If there are no songs in the list, it indicates, but, the name of the object is placed,
		// in this case, the song
		if (value == null) {
			setText("Sin canciones en reproducción");
		} else setText(value.toString());

		// If the number of the row in the list is even, the first color is placed, but the second color is placed.
		if(index % 2 == 0 && !isSelected)
			setBackground(mColor1);
		else if (isSelected) {
			setBackground(Color.BLUE);
		} else {
			setBackground(mColor2);
		}
		return this;
	}
}
