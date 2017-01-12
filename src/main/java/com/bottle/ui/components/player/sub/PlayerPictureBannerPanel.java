package com.bottle.ui.components.player.sub;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;

public class PlayerPictureBannerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	private List<String> filenameList = new ArrayList<String>();
	private String curFilename = "";
	private int curPos = 0;

	private int height = 0;
	private int weight = 0;
	
	public PlayerPictureBannerPanel() {
		setLayout(null);
		initTimeThread();
	}
	
	public void initTimeThread() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable( ) {  
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
        },  
        0, 5, TimeUnit.SECONDS);
	}
	
	public void validatePicture() {
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
