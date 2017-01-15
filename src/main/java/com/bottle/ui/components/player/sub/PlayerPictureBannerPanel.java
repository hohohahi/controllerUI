package com.bottle.ui.components.player.sub;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PlayerPictureBannerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private List<String> filenameList = new ArrayList<String>();
	private String curFilename = "";
	private int curPos = 0;

	private int height = 0;
	private int weight = 0;
	private Timer changePictureTimer = new Timer();
	public PlayerPictureBannerPanel() {
		setLayout(null);
		
		List<String> imageNameList = new ArrayList<String>();
		imageNameList.add("playerbanner.png");
		imageNameList.add("greenearth.jpg");
		setImageFileNameList(imageNameList);
		
		curFilename = imageNameList.get(0);
		setWeight(625);
		setHeight(800);	
	}
	
	public void initChangeBannerPictureTimeThread() {
		changePictureTimer = new Timer();  
		changePictureTimer.schedule(new TimerTask() {  
            public void run() {              	            	
            	if (filenameList.size() == 0) {
            		curFilename = "SystemInfo.png";
            	}
            	else {
            		curPos++;
                	if (curPos >= filenameList.size()) {
                		curPos = 0;
                	}
                	
            		curFilename = filenameList.get(curPos);
            	}
            	      
            	validatePicture();
            }  
        }, 0, 6000);
	}
	
	public void resetTimer() {
		changePictureTimer.cancel();
		curPos = 0;
	}
	
	public void validatePicture() {
		this.invalidate();
		this.validate();
		this.repaint();
	}
	
	public Image loadImage(final String filename) {
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
        	g.clearRect( 0, 0, this.weight, this.height);
        	final Image mainImage = loadImage(curFilename);        	                  
            g.drawImage(mainImage, 0, 0, this.weight, this.height, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    public void setImageFileNameList(List<String> imageNameList) {
    	this.filenameList = imageNameList;
    }
    
    public void setHeight(final int height) {
    	this.height = height;
    }
    
    public void setWeight(final int weight) {
    	this.weight = weight;
    }
}
