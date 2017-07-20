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
import java.util.Timer;
import java.util.TimerTask;
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

@Component
public class PictureBackgroundPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	private List<String> filenameList = new ArrayList<String>();
	private String curFilename = "";
	private int curPos = 0;

	private int height = 0;
	private int weight = 0;
	private Timer changePictureTimer = new Timer();
	
	public PictureBackgroundPanel() {
		setLayout(null);
		addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				final MessageVO vo = new MessageVO();
				vo.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
				vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
				vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Player_.getId());
				messageManager.push(vo);
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
        }, 0, 5000);
	}
	
	public void resetTimer() {
		changePictureTimer.cancel();
		curPos = 0;
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
        	g.clearRect( 0, 0, this.weight, this.height);
        	final Image mainImage = loadImage(curFilename);        	                  
            g.drawImage(mainImage, 0, 0, this.weight, this.height, null);
            
            /*
            final Image title = loadImage("title.png");        	                  
            g.drawImage(title, 150, 60, 750, 100, null);
            
            final Image foot = loadImage("foot.png");        	                  
            g.drawImage(foot, 0, 1500, 1050, 100, null);
            */
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
