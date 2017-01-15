package com.bottle.ui.components.player.sub;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BarCodePicturePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private int imageHeight = 250;
	private int imageWidth = 250;
	private String filename = "";
	public BarCodePicturePanel(String filename, final int width, final int height) {
		setLayout(null);
		this.filename = filename;
		this.imageWidth = width;
		this.imageHeight = height;
	}
	
	public void validatePicture() {
		this.validate();
		this.repaint();
	}
	
	public Image loadImage() {
		Image image = null;
		try {
			final String relativePath = "resources/images/" + filename;
			File systemInfoPictureFile = new File(relativePath);
			if (false == systemInfoPictureFile.exists()) {
				URL pathURL = ClassLoader.getSystemResource("");
				
				File classPath = new File(pathURL.getFile());
				String fullPath = classPath.getParent() + File.separator + relativePath;
				systemInfoPictureFile = new File(fullPath);
			}
			
			image = ImageIO.read(systemInfoPictureFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return image;
	}
    public void paint(Graphics g){
        try {
        	g.clearRect( 0, 0, this.imageWidth, this.imageHeight);
        	final Image mainImage = loadImage();        	                  
            g.drawImage(mainImage, 0, 0, this.imageWidth, this.imageHeight, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
