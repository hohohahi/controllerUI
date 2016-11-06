package com.bottle.ui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import com.bottle.common.IDateConverter;
import com.bottle.common.ILoggerHelper;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ILanguageConstants;
import com.bottle.hardware.camera.ICameraConnector;
import com.bottle.ui.components.admin.AdminPanel;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.player.PlayerPanel;
import com.bottle.ui.components.verify.VerifyPanel;
import com.bottle.ui.components.welcome.WelcomePanel;
import com.bottle.ui.constants.IUIConstants;

@Component
public class MainFrame extends JFrame implements IMessageListener {
	private static final long serialVersionUID = -632890096779473173L;
	
	final Logger logger = Logger.getLogger(this.getClass());
	
	private JPanel bossPane;
	
	private VerifyPanel veryfyPanel = new VerifyPanel();
	
	@Autowired
	private WelcomePanel welcomePane;
	
	@Autowired
	private PlayerPanel playerPane;
	
	private AdminPanel adminPane = new AdminPanel();
	
	
	@Autowired
	private ICameraConnector cameraConnector;
	
	@Autowired
	private ILoggerHelper loggerHelper;	
	
	@Autowired
	private IDateConverter dateConverter;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	private final JLabel lblServerStatusTitle = new FontLabel("\u670D\u52A1\u5668\u8FDE\u63A5\u72B6\u6001");
	private final JLabel lblServerStatus = new FontLabel();
	private final JLabel lblMachineStatusTitle = new FontLabel("\u4E3B\u63A7\u8FDE\u63A5\u72B6\u6001");
	private final JLabel lblMachineStatus = new FontLabel();
	private final JLabel lblCurrentDate = new FontLabel();
	private final JLabel lblSystemInfoLog = new FontLabel();
	
	private final JLabel label = new JLabel("");
	
	@PostConstruct
	public void initialize() {
		final String className = this.getClass().getName();
		String outputMessage = className + " initialized";

		logger.debug(outputMessage);
		
		initWelcomePanel();
		initPlayerPanel();
		initCamera();
		initMessage();
		
		switchMode(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_);
		updateStatusLable(lblServerStatus, ICommonConstants._ConnectionStatus_Offline_);
		updateStatusLable(lblMachineStatus, ICommonConstants._ConnectionStatus_Offline_);
		
		initTimeThread();
	}
	
	public void initWelcomePanel() {
		welcomePane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));				
		welcomePane.setBounds(0, IUIConstants._Banner_Height_, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		welcomePane.setLayout(null);
		bossPane.add(welcomePane);
	}
	
	public void initPlayerPanel() {
		playerPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));				
		playerPane.setBounds(0, IUIConstants._Banner_Height_, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		playerPane.setLayout(null);
		bossPane.add(playerPane);
	}
	
	
	public void initTimeThread() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(new Runnable( ) {  
            public void run() {
            	 lblCurrentDate.setText(dateConverter.getCurrentTimestampInNineteenBitsInGMT());
            }  
        },  
        0, 1, TimeUnit.SECONDS);
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
		setBounds(0, 0, 1439, 773);
		
		 getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		bossPane = new JPanel();
		bossPane.setBorder(new EmptyBorder(5, 5, 5, 5));			
		setContentPane(bossPane);
		bossPane.setLayout(null);		
		
		veryfyPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		veryfyPanel.setBounds(0, 74, 1425, 768);		
		veryfyPanel.setLayout(null);
		adminPane.setBounds(0, IUIConstants._Banner_Height_, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		adminPane.setLayout(null);
				
		bossPane.add(veryfyPanel);
		bossPane.add(adminPane);
		
		lblServerStatusTitle.setBounds(37, 5, 169, 33);		
		bossPane.add(lblServerStatusTitle);
		
		lblServerStatus.setBounds(216, 10, 188, 23);		
		bossPane.add(lblServerStatus);
				
		lblMachineStatusTitle.setBounds(37, 35, 169, 33);
		bossPane.add(lblMachineStatusTitle);
				
		lblMachineStatus.setBounds(216, 40, 188, 23);
		bossPane.add(lblMachineStatus);
		
		lblCurrentDate.setBounds(1108, 0, 258, 33);
		bossPane.add(lblCurrentDate);
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				switchMode(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Verify_);	
			}
		});
		label.setBounds(0, 0, 26, 23);
		
		bossPane.add(label);
		
		lblSystemInfoLog.setBounds(898, 35, 468, 33);
		bossPane.add(lblSystemInfoLog);
	}
	
	public void switchMode(final ICommonConstants.MainFrameActivePanelEnum activePanel) {
		switch (activePanel){
			case _MainFrame_ActivePanel_Welcome_:
			{
				welcomePane.setVisible(true);
				welcomePane.validate();
				playerPane.setVisible(false);
				veryfyPanel.hideEx();
				adminPane.setVisible(false);				
				break;
			}
			case _MainFrame_ActivePanel_Player_:
			{
				welcomePane.setVisible(false);
				playerPane.setVisible(true);
				playerPane.validate();
				veryfyPanel.hideEx();
				adminPane.setVisible(false);	
				break;
			}
			case _MainFrame_ActivePanel_Verify_:
			{
				welcomePane.setVisible(false);
				playerPane.setVisible(false);
				veryfyPanel.showEx();
				adminPane.setVisible(false);
				break;
			}
			case _MainFrame_ActivePanel_Admin_:
			{
				welcomePane.setVisible(false);
				playerPane.setVisible(false);
				veryfyPanel.hideEx();
				adminPane.setVisible(true);
				adminPane.validate();
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
				switchMode(ICommonConstants.MainFrameActivePanelEnum.getActivePanelEnumById(vo.getParam1()));
				break;
			}
			case _SubMessageType_MainFrame_ServerStatus_:
			{
				updateStatusLable(lblServerStatus, vo.getParam1());
				break;
			}
			case _SubMessageType_MainFrame_MachineStatus_:
			{
				updateStatusLable(lblMachineStatus, vo.getParam1());
				break;
			}
			case _SubMessageType_MainFrame_SystemInfoLog_:
			{
				updateMessageLable(lblSystemInfoLog, vo.getMessage());
				break;
			}
		default:
			break;
		}
	}
	
	public void updateMessageLable(final JLabel label, final String message) {
		final String timestampStr = dateConverter.getCurrentTimestampInNineteenBitsInGMT();
		label.setText(timestampStr + "  " + message);
	}
	
	public void updateStatusLable(final JLabel label, final long status) {
		if (ICommonConstants._ConnectionStatus_Online_ == status) {
			label.setText(ILanguageConstants._ConnectionStatus_Connected_);
			label.setForeground(Color.GREEN);
		}
		else if (ICommonConstants._ConnectionStatus_Offline_ == status) {
			label.setText(ILanguageConstants._ConnectionStatus_Disconnected_);
			label.setForeground(Color.RED);
		}
		else {
			throw new RuntimeException("Unsupported server status. status:" + status);
		}
		
		label.validate();
	}

	@Override
	public MessageSourceEnum getMessageType() {
		return ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_;
	}
}
