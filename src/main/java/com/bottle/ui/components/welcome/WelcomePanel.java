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
import com.bottle.ui.components.player.sub.SystemInfoPanel;
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
		
		SystemInfoPanel panel = new SystemInfoPanel();
		
		panel.setBounds(200, 325, IUIConstants._Total_Width_-200, 350);
		add(panel);				
		
		FontLabel lblWelcomeMessageLabel = new FontLabel("\u6B22\u8FCE\u4F7F\u7528\u667A\u80FD\u996E\u6599\u74F6\u56DE\u6536\u673A", 60);		
		lblWelcomeMessageLabel.setBounds(241, 11, 777, 146);
		lblWelcomeMessageLabel.setForeground(Color.BLUE);
		add(lblWelcomeMessageLabel);
		
		JButton btnNewButton = new JButton("\u8FDB\u5165\u64CD\u4F5C\u754C\u9762");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clickEnterOperationPageButton();
			}
		});
		btnNewButton.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 33));
		btnNewButton.setBounds(489, 178, 321, 87);
		add(btnNewButton);
	}
	
	public void clickEnterOperationPageButton() {
		final MessageVO vo = new MessageVO();
		vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
		vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
		vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Player_.getId());
		messageManager.push(vo);
	}
}
