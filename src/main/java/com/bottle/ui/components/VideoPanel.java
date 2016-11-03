package com.bottle.ui.components;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class VideoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image = null;
	
	@Override
    public void paint(Graphics g){
        g.drawImage(image, 0, 0, 550, 400, null);
    }

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
