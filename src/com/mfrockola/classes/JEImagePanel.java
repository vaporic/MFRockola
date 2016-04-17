package com.mfrockola.classes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

	@SuppressWarnings("serial")
	public class JEImagePanel extends JPanel{  

		private Image image=null;  

		    private Icon icon;  

		/** Creates a new instance of JEImagePanel */  

		    public JEImagePanel(String nombreImagen)
		    {  
		    	this.setIcon(new ImageIcon(nombreImagen));
		    }  

		public JEImagePanel(URL resource) 
		{
			this.setIcon(new ImageIcon(resource));
		}

		protected void paintComponent(Graphics g) {  

		        Graphics2D g2 =(Graphics2D) g;  

		        if(getImage()!=null)  

		            g2.drawImage(getImage(), 0, 0, getWidth(), getHeight(), null);  

		    }  

		public Image getImage() {  

		        return image;  

		    }  

		public void setImage(Image image) {  

		        this.image = image;  

		    }  

		public Icon getIcon() {  

		        return icon;  

		    }  

		public void setIcon(Icon icon){  

		        this.icon=icon;  

		        setImage(((ImageIcon)icon).getImage());  

		    }  
}
