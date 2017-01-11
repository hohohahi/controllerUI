package com.bottle.ui.components.player.sub;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PictureBackgroundPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private List<String> filenameList = new ArrayList<String>();
	private String curFilename = "";
	private int curPos = 0;
	private Image image=null;
	private int height = 0;
	private int weight = 0;
	
	public PictureBackgroundPanel() {
		setLayout(null);
		initTimeThread();
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
	
	public void initTimeThread() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable( ) {  
            public void run() {
            	curPos++;
            	if (curPos == filenameList.size()) {
            		curPos = 0;
            	}
            	
            	if (filenameList.size() == 0) {
            		curFilename = "SystemInfo.png";
            	}
            	else {
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
    public void paint(Graphics g){
        try {
        	
        	final String relativePath = "resources/images/" + curFilename;
        	File systemInfoPictureFile = new File(relativePath);
        	if (false == systemInfoPictureFile.exists()) {
        		URL pathURL = ClassLoader.getSystemResource("");
        		
        		File classPath = new File(pathURL.getFile());
        		String fullPath = classPath.getParent() + File.separator + relativePath;
        		systemInfoPictureFile = new File(fullPath);
        	}
            image=ImageIO.read(systemInfoPictureFile);
            
            
            g.drawImage(image, 0, 0, this.weight, this.height, null);
            System.out.println(relativePath);
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
