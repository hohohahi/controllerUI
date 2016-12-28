package com.bottle.ui.components.verify;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JDialog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.hardware.camera.ICameraConnector;
import com.bottle.ui.components.common.AbstractBasePanel;

@Component
public class VideoBarCodeVerifyPanel extends AbstractBasePanel{
	private static final long serialVersionUID = -4865441036264933143L;
	
	@Autowired
	private ICameraConnector cameraConnector;
	
	private VideoPanel videoPanel;
	
	private JButton button;
	private JDialog dlg;
	
	
	public VideoBarCodeVerifyPanel() {
		setLayout(null);
		
		videoPanel = new VideoPanel();
		videoPanel.setBounds(28, 11, 448, 447);
		add(videoPanel);
		videoPanel.setLayout(null);
		
		button = new JButton("\u542F\u52A8\u89C6\u9891\u626B\u7801");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cameraConnector.start();
			}
		});
		button.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 24));
		button.setBounds(498, 176, 237, 100);
		add(button);
	}		

	@PostConstruct
	public void initialize() {
		cameraConnector.setPanel(this);
	}
	
	public void setDlg(JDialog dlg) {
		this.dlg = dlg;
	}

	public VideoPanel getVideoPanel() {
		return videoPanel;
	}	
	
	public void showEx(){
		this.setVisible(true);
		this.validate();
		cameraConnector.start();
	}
	
	public void hideEx() {
		this.setVisible(false);
		this.validate();
		cameraConnector.stop();
	}

	public void setMessageManager(IMessageQueueManager messageManager) {
		this.messageManager = messageManager;
	}

	@Override
	public void process(MessageVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MessageSourceEnum getMessageType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processChildMessage(MessageVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(java.awt.Component parent) {
		// TODO Auto-generated method stub
		
	}
}
