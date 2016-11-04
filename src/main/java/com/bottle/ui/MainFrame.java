package com.bottle.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.IMessageListener;
import com.bottle.business.common.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.ILoggerHelper;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ILanguageConstants;
import com.bottle.common.constants.IUserConstants;
import com.bottle.hardware.camera.ICameraConnector;
import com.bottle.ui.components.admin.AdminPanel;
import com.bottle.ui.components.player.PlayerPanel;
import com.bottle.ui.components.verify.VerifyPanel;
import com.bottle.ui.constants.IUIConstants;

@Component
public class MainFrame extends JFrame implements IMessageListener {
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
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	private final JLabel lblServerConnectionStatus = new JLabel("\u670D\u52A1\u5668\u8FDE\u63A5\u72B6\u6001");
	private final JLabel lblUnconnected = new JLabel();
	private final JLabel label = new JLabel("");
	
	@PostConstruct
	public void initialize() {
		final String className = this.getClass().getName();
		String outputMessage = className + " initialized";

		logger.debug(outputMessage);
		
		initCamera();
		initMessage();
		
		switchMode(IUIConstants.UIWorkModeEnum._WorkMode_Player_);
	}
	
	public void initMessage() {
		messageManager.addListener(this);
		veryfyPanel.setMessageManager(messageManager);
	}
	
	public void initCamera() {
		veryfyPanel.init(cameraConnector);
	}
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setUndecorated(true);
		setBounds(0, 0, 1079, 773);
		
		 getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		bossPane = new JPanel();
		bossPane.setBorder(new EmptyBorder(5, 5, 5, 5));			
		setContentPane(bossPane);
		bossPane.setLayout(null);		
		
		
		playerPane.setBounds(0, IUIConstants._Banner_Height_, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		playerPane.setLayout(null);
		veryfyPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		veryfyPanel.setBounds(0, IUIConstants._Banner_Height_, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		veryfyPanel.setLayout(null);
		adminPane.setBounds(0, IUIConstants._Banner_Height_, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		adminPane.setLayout(null);
		
		bossPane.add(playerPane);
		bossPane.add(veryfyPanel);
		bossPane.add(adminPane);
		
		lblServerConnectionStatus.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
		lblServerConnectionStatus.setBounds(37, 15, 169, 33);
		
		bossPane.add(lblServerConnectionStatus);
		lblUnconnected.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
		lblUnconnected.setForeground(Color.RED);
		lblUnconnected.setBounds(216, 20, 126, 23);
		
		bossPane.add(lblUnconnected);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switchMode(IUIConstants.UIWorkModeEnum._WorkMode_Verify_);	
			}
		});
		label.setBounds(0, 0, 26, 23);
		
		bossPane.add(label);
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
				break;
			}
			case _WorkMode_Admin_:
			{
				playerPane.setVisible(false);
				veryfyPanel.hideEx();
				adminPane.setVisible(true);
				break;
			}
			default:
				break;
		}
	}

	@Override
	public void process(MessageVO vo) {
		if (null == vo) {
			throw new NullPointerException("vo is null.");
		}
		
		final MessageSourceEnum messageType = vo.getMessageSource();
		if (false == messageType.equals(getMessageType())) {
			return;
		}
		
		final ICommonConstants.SubMessageTypeEnum subMessageType = vo.getSubMessageType();
		switch (subMessageType) {
			case _SubMessageType_MainFrame_Panel_:
			{
				final long role = vo.getId();
				
				if (IUserConstants._Role_Admin_ == role) {
					switchMode(IUIConstants.UIWorkModeEnum._WorkMode_Admin_);
				} else if (IUserConstants._Role_None_ == role) {
					switchMode(IUIConstants.UIWorkModeEnum._WorkMode_Player_);
				}
				
				break;
			}
			case _SubMessageType_MainFrame_ServerStatus_:
			{
				break;
			}
		default:
			break;
		}
	}
	
	public void updateServerStatusLable(boolean isOnline) {
		if (true == isOnline) {
			lblUnconnected.setText(ILanguageConstants._ServerStatus_Connected_);
			lblUnconnected.setCo
		}
		else {
			
		}
	}

	@Override
	public MessageSourceEnum getMessageType() {
		return ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_;
	}
}
