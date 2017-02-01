package com.mfrockola.classes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class JEImagePanel extends JPanel {

    // Variable that stores the panel background
    private Image mImage;

    // Store the image in an object Icon that is then passed as an argument to the Image object
    private Icon mIcon;

	JEImagePanel(URL resource) {
		mImage = null;
        // Set the value of the icon object according to the path that is passed in the constructor
        setIcon(new ImageIcon(resource));
	}

    // We override the paintComponent method to draw the image we have stored in the Image object
    protected void paintComponent(Graphics g) {
		Graphics2D g2 =(Graphics2D) g;
		if(getImage()!=null)
			g2.drawImage(getImage(), 0, 0, getWidth(), getHeight(), null);
	}

    // private methods to use the private variables of the JEImagePanel class
	private Image getImage() {
		return mImage;
	}

	private void setImage(Image image) {
		this.mImage = image;
	}

	private Icon getIcon() {
		return mIcon;
	}

    // In this method after setting the object Icon, we call the method setImage and we pass as argument the object Icon
	private void setIcon(Icon icon) {
		this.mIcon=icon;
		setImage(((ImageIcon)getIcon()).getImage());
	}
}
