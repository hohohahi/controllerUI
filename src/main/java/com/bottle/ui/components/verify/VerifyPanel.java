package com.bottle.ui.components.verify;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bottle.business.common.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.IUserConstants;
import com.bottle.hardware.camera.ICameraConnector;
import com.bottle.ui.constants.IUIConstants;

public class VerifyPanel extends JPanel{
	private static final long serialVersionUID = -4865441036264933143L;
	
	private ICameraConnector cameraConnector;
	private IMessageQueueManager messageManager;
	private VideoPanel videoPanel;
	private JLabel messageLabel;
	private JButton btnNewButton;
	private int expiredTimeInSeconds = 0;
	
	public VerifyPanel() {
		setLayout(null);
		
		videoPanel = new VideoPanel();
		videoPanel.setBounds(IUIConstants._VideoPanel_Offset_X, 0, IUIConstants._VideoPanel_Width, IUIConstants._VideoPanel_Height);
		add(videoPanel);
		videoPanel.setLayout(null);
		
		int offset_X = IUIConstants._VideoPanel_Offset_X + IUIConstants._VideoPanel_Width + IUIConstants._VerifyPanel_ReturnButton_Offset_X_LeftToVideoPanel_;
		
		btnNewButton = new JButton("\u9000\u51FA\u9A8C\u8BC1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitVerify();
			}
		});
		
		btnNewButton.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 24));
		btnNewButton.setBounds(offset_X, 209, 173, 100);
		add(btnNewButton);
				
		messageLabel = new JLabel("");
		messageLabel.setBounds(offset_X, 371, IUIConstants._VerifyPanel_MessageLabel_Width_, IUIConstants._VerifyPanel_MessageLabel_Height_);
		
		messageLabel.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 24));
		add(messageLabel);		
	}		

	public VideoPanel getVideoPanel() {
		return videoPanel;
	}	

	public JLabel getMessageLabel() {
		return messageLabel;
	}

	public void setCameraConnector(ICameraConnector cameraConnector) {
		this.cameraConnector = cameraConnector;
	}
	
	public void initExpireTimer() {
		expiredTimeInSeconds = 0;
		Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {              	            	
            	showWarningLable(IUIConstants._VerifyPanel_ExpireTime - expiredTimeInSeconds);
            	if (IUIConstants._VerifyPanel_ExpireTime == expiredTimeInSeconds) {
            		timer.cancel();
            		exitVerify();
            	}
            	expiredTimeInSeconds++;
            }  
        }, 0, 1000); 
	}
	
	public void showWarningLable(int leftTime) {		
		final String name = "\u8BF7\u5728" + leftTime + "\u79D2\u5185\u5B8C\u6210\u9A8C\u8BC1";
		messageLabel.setText(name);
	}
	
	public void showEx(){
		this.setVisible(true);
		this.validate();
		cameraConnector.start();
		initExpireTimer();
	}
	
	public void hideEx() {
		this.setVisible(false);
		this.validate();
		cameraConnector.stop();
	}
	
	public void init(ICameraConnector cameraConnector) {
		cameraConnector.setPanel(this);
		this.cameraConnector = cameraConnector;
	}

	public void setMessageManager(IMessageQueueManager messageManager) {
		this.messageManager = messageManager;
	}
	
	public void exitVerify() {
		MessageVO vo = new MessageVO();
		vo.setId(IUserConstants._Role_None_);
		vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
		messageManager.push(vo);
	}
}
