package com.bottle.ui.components.welcome;

import java.awt.Color;
import java.awt.Font;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.ui.components.common.FontButton;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.player.sub.PictureBackgroundPanel;
import com.bottle.ui.constants.IUIConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class WelcomePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	final FontButton startCommandButton = new FontButton("\u5F00\u59CB\u6295\u74F6");
	@PostConstruct
	public void initialize() {

	}
	
	public WelcomePanel() {
		setLayout(null);
		
		FontLabel lblWelcomeMessageLabel = new FontLabel("\u6B22\u8FCE\u4F7F\u7528\u667A\u80FD\u996E\u6599\u74F6\u56DE\u6536\u673A", 60);		
		lblWelcomeMessageLabel.setBounds(150, 20, 777, 146);
		lblWelcomeMessageLabel.setForeground(Color.BLUE);
		add(lblWelcomeMessageLabel);
		
		PictureBackgroundPanel panel = new PictureBackgroundPanel();
		
		panel.setBounds(0, 0, IUIConstants._Total_Width_, IUIConstants._Total_Height_);
		add(panel);				
	}
}
