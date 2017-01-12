package com.bottle.ui.components.welcome;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.ui.components.common.FontButton;
import com.bottle.ui.components.player.sub.PictureBackgroundPanel;
import com.bottle.ui.constants.IUIConstants;

@Component
public class WelcomePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Autowired
	private PictureBackgroundPanel bannerPicturePanel;
	
	final FontButton startCommandButton = new FontButton("\u5F00\u59CB\u6295\u74F6");
	@PostConstruct
	public void initialize() {
		List<String> imageNameList = new ArrayList<String>();
		//imageNameList.add("SystemInfo1.png");
		imageNameList.add("SystemInfo.png");
		imageNameList.add("t1.JPG");	
		
		bannerPicturePanel.setBounds(0, 0, IUIConstants._Total_Width_, IUIConstants._Total_Height_);
		bannerPicturePanel.setImageFileNameList(imageNameList);
		bannerPicturePanel.setWeight(IUIConstants._Total_Width_);
		bannerPicturePanel.setHeight(IUIConstants._Total_Height_);
		add(bannerPicturePanel);
	}
	
	public WelcomePanel() {
		setLayout(null);
	}
}
