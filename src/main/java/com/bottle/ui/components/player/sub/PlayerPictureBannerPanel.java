package com.bottle.ui.components.player.sub;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;

public class PlayerPictureBannerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	private List<String> filenameList = new ArrayList<String>();
	private String curFilename = "";
	private int curPos = 0;

	private int height = 0;
	private int weight = 0;
	private int leftExpiredTime_InSecond = 0;
	private Timer changePictureTimer = new Timer();
	public PlayerPictureBannerPanel() {
		setLayout(null);
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
		leftExpiredTime_InSecond = 0;
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
            
            g.setFont(new Font("Tahoma", Font.BOLD, 36));
            g.drawString("Exit in " + leftExpiredTime_InSecond + " seconds", 50, 50);
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

	public int getLeftExpiredTime_InSecond() {
		return leftExpiredTime_InSecond;
	}

	public void setLeftExpiredTime_InSecond(int leftExpiredTime_InSecond) {
		this.leftExpiredTime_InSecond = leftExpiredTime_InSecond;
	}
}
