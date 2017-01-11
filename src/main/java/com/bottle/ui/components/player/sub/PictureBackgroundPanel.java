package com.bottle.ui.components.player.sub;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PictureBackgroundPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Image image=null;
	public PictureBackgroundPanel() {
		setLayout(null);
		
		addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(123);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
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
