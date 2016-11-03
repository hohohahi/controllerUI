package com.bottle.ui.components;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bottle.hardware.camera.ICameraConnector;

public class VerifyPanel extends JPanel{
	private static final long serialVersionUID = -4865441036264933143L;
	
	private ICameraConnector cameraConnector;
	private VideoPanel videoPanel;
	private JLabel messageLabel;
	
	public VerifyPanel() {
		setLayout(null);
		JButton btnNewButton = new JButton("Test Show");
		btnNewButton.setBounds(527, 98, 89, 23);
		add(btnNewButton);
		
		videoPanel = new VideoPanel();
		videoPanel.setBounds(89, 39, 422, 334);
		add(videoPanel);
		
		messageLabel = new JLabel("");
		messageLabel.setBounds(545, 195, 129, 47);
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
	
	public void init(ICameraConnector cameraConnector) {
		cameraConnector.setPanel(this);
		this.cameraConnector = cameraConnector;
	}
}
