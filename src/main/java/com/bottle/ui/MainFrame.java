package com.bottle.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.common.ILoggerHelper;
import com.bottle.hardware.camera.ICameraConnector;
import com.bottle.ui.components.AdminPanel;
import com.bottle.ui.components.PlayerPanel;
import com.bottle.ui.components.VerifyPanel;
import com.bottle.ui.constants.IUIConstants;

@Component
public class MainFrame extends JFrame {
	private static final long serialVersionUID = -632890096779473173L;
	
	final Logger logger = Logger.getLogger(this.getClass());
	
	private JPanel bossPane;
	private VerifyPanel veryfyPanel = new VerifyPanel();
	private PlayerPanel playerPane = new PlayerPanel();
	private AdminPanel adminPane = new AdminPanel();
	
	
	@Autowired
	private ICameraConnector cameraConnector;
	
	@Autowired
	private ILoggerHelper loggerHelper;	
	
	@PostConstruct
	public void initialize() {
		final String className = this.getClass().getName();
		String outputMessage = className + " initialized";

		logger.debug(outputMessage);
		
		initCamera();
	}
	
	public void initCamera() {
		veryfyPanel.init(cameraConnector);
	}
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, 925, 666);
		
		 getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		bossPane = new JPanel();
		bossPane.setBorder(new EmptyBorder(5, 5, 5, 5));			
		setContentPane(bossPane);
		bossPane.setLayout(null);		
		
		
		playerPane.setBounds(0, 64, 909, 563);		
		playerPane.setLayout(null);
		veryfyPanel.setBounds(0, 64, 909, 563);		
		veryfyPanel.setLayout(null);
		adminPane.setBounds(0, 64, 909, 563);		
		adminPane.setLayout(null);
		
		bossPane.add(playerPane);
		bossPane.add(veryfyPanel);
		bossPane.add(adminPane);

		JButton btnNewButton = new JButton("\u7528\u6237\u754C\u9762");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switchMode(IUIConstants.UIWorkModeEnum._WorkMode_Player_);							
			}
		});
		btnNewButton.setBounds(83, 11, 112, 23);
		bossPane.add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("\u4E8C\u7EF4\u7801\u767B\u5F55");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchMode(IUIConstants.UIWorkModeEnum._WorkMode_Verify_);		
			}
		});
		btnNewButton_2.setBounds(242, 11, 152, 23);
		bossPane.add(btnNewButton_2);
	}
	
	public void switchMode(IUIConstants.UIWorkModeEnum mode) {
		switch (mode){
			case _WorkMode_Player_:
			{
				playerPane.setVisible(true);
				playerPane.validate();
				veryfyPanel.hideEx();
				adminPane.setVisible(false);	
				break;
			}
			case _WorkMode_Verify_:
			{
				playerPane.setVisible(false);
				veryfyPanel.showEx();
				adminPane.setVisible(false);	
			}
			default:
				break;
		}
	}
}
