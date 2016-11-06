package com.bottle.ui.components.player.sub;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SystemInfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Image image=null;
	public SystemInfoPanel() {
		setLayout(null);
	}

    public void paint(Graphics g){
        try {
        	final String relativePath = "resources/images/SystemInfo.png";
        	File systemInfoPictureFile = new File(relativePath);
        	if (false == systemInfoPictureFile.exists()) {
        		URL pathURL = ClassLoader.getSystemResource("");
        		
        		File classPath = new File(pathURL.getFile());
        		String fullPath = classPath.getParent() + File.separator + relativePath;
        		systemInfoPictureFile = new File(fullPath);
        	}
            image=ImageIO.read(systemInfoPictureFile);
            
            
            g.drawImage(image, 0, 0, 1000, 350, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
