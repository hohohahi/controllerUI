package com.bottle.ui.components.verify;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.bottle.ui.constants.IUIConstants;

public class VideoPanel extends JPanel {
	public VideoPanel() {
	}
	private static final long serialVersionUID = 1L;
	private Image image = null;
	
	@Override
    public void paint(Graphics g){
		int width = 0;
		int height = 0;
		
        g.drawImage(image, 0, 0, IUIConstants._VideoPanel_Width, IUIConstants._VideoPanel_Height, null);
    }

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
