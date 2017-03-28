package com.bottle.ui.components.player.sub;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PlayerTemplateDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private List<String> filenameList = new ArrayList<String>();
	private Map<String, String> barCodeAndfilenameMap = new HashMap<String, String>();
	private String curFilename = "";

	private int height = 0;
	private int weight = 0;
	public PlayerTemplateDisplayPanel(int width, int height) {
		setLayout(null);
		
		//nongfushanquan550ml.jpg
		
		List<String> imageNameList = new ArrayList<String>();
		imageNameList.add("playerbanner.png");
		imageNameList.add("greenearth625x1000.png");
		imageNameList.add("greenword625x1000.png");
		
		barCodeAndfilenameMap.put("6921168509256", "nongfushanquan550ml.jpg");
		barCodeAndfilenameMap.put("6954767415772", "kekoukele600ml.jpg");
		barCodeAndfilenameMap.put("6940159410043", "baishikele2L.jpg");
		barCodeAndfilenameMap.put("6954767410173", "kekoukele300ml.jpg");
		barCodeAndfilenameMap.put("6921168520015", "nongfushanquan1.5L.jpg");
		barCodeAndfilenameMap.put("3179730110796", "perrier300ml.jpg");
		barCodeAndfilenameMap.put("6925303730574", "tongyiasamu500ml.jpg");
		
		
						
		setImageFileNameList(imageNameList);
		
		curFilename = "default.jpg";
		setWeight(width);
		setHeight(height);	
	}
	
	public void validatePicture() {
		this.invalidate();
		this.validate();
		this.repaint();
	}
	
	public Image loadImage(final String filename) {
		Image image = null;
		try {
			final String relativePath = "resources/images/template/" + filename;
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
    
    public void updatePictureByBarCode(final String barCode) {
    	final String filename = barCodeAndfilenameMap.get(barCode);
    	if (null == filename) {
    		throw new NullPointerException("filename is null.");
    	}
    	
    	curFilename = filename;
    	validatePicture();
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
