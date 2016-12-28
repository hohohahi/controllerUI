package com.bottle.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageListener;
import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.business.verify.ISecretTriggerManager;
import com.bottle.common.IDateConverter;
import com.bottle.common.ILoggerHelper;
import com.bottle.common.IResourceLoader;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.hardware.rxtx.ISerialCommConnector;
import com.bottle.ui.components.admin.AdminPanel;
import com.bottle.ui.components.common.ClickFontLabel;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.exit.ExitDialog;
import com.bottle.ui.components.player.PlayerPanel;
import com.bottle.ui.components.verify.VerifyDialog;
import com.bottle.ui.components.welcome.WelcomePanel;
import com.bottle.ui.constants.IUIConstants;

import gnu.io.SerialPortEvent;

@Component
public class MainFrame extends JFrame implements IMessageListener {
	private static final long serialVersionUID = -632890096779473173L;
	
	final Logger logger = Logger.getLogger(this.getClass());
	
	private JPanel bossPane;
	
	@Autowired
	private ISerialCommConnector serialConnector;
	
	@Autowired
	private WelcomePanel welcomePane;
	
	@Autowired
	private PlayerPanel playerPane;
	
	@Autowired
	private AdminPanel adminPane;
		
	@Autowired
	private ILoggerHelper loggerHelper;	
	
	@Autowired
	private IDateConverter dateConverter;
	
	@Autowired
	private IResourceLoader resourceLoader;
	
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	@Autowired
	private ISerialCommConnector serialCommConnector;
	
	@Autowired
	private VerifyDialog verifyDialog;
	
	@Autowired
	private ISecretTriggerManager adminPanelVerifyManager;
	
	private final ClickFontLabel lblServerStatus = new ClickFontLabel(1);
	private final ClickFontLabel lblSerialPortStatus = new ClickFontLabel(2);
	private final ClickFontLabel lblMachineStatus = new ClickFontLabel(3);
	private final JLabel lblCurrentDate = new FontLabel();
	private final JLabel lblSystemInfoLog = new FontLabel();
	private boolean isLastOperationSuccessful = false;
	private Icon connectedIcon = new ImageIcon();
	private Icon disconnectedIcon = new ImageIcon();
	
	@PostConstruct
	public void initialize() {
		final String className = this.getClass().getName();
		String outputMessage = className + " initialized";

		logger.debug(outputMessage);
		
		initWelcomePanel();
		initPlayerPanel();
		initAdminPanel();
		initStatusIcons();
		initMessage();
		
		switchMode(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_);
		initStatusLabels();
		initTimeThread();
		initSerialCommPort();
	}

	public void initSerialCommPort() {
		serialCommConnector.initSerialPort();
	}
	
	public void initStatusLabels(){
		updateStatusLable(lblServerStatus, ICommonConstants._ConnectionStatus_Offline_);
		updateStatusLable(lblMachineStatus, ICommonConstants._ConnectionStatus_Offline_);
		updateStatusLable(lblSerialPortStatus, ICommonConstants._ConnectionStatus_Offline_);
		
		lblServerStatus.setTriggerManager(adminPanelVerifyManager);
		lblMachineStatus.setTriggerManager(adminPanelVerifyManager);
		lblSerialPortStatus.setTriggerManager(adminPanelVerifyManager);
	}
	
	public void initStatusIcons() {
		try {
			final File redLabelImageFile = resourceLoader.loadResourceAsFile("redLabel.png");
			final BufferedImage redLabelImage=ImageIO.read(redLabelImageFile);
			disconnectedIcon = new ImageIcon(redLabelImage);
			
			final File greenLabelImageFile = resourceLoader.loadResourceAsFile("greenLabel.png");
			final BufferedImage greenLabelImage=ImageIO.read(greenLabelImageFile);
			connectedIcon = new ImageIcon(greenLabelImage);
		} catch (IOException e) {
			loggerHelper.logging(logger, e, Level.ERROR, e.getMessage());
		}
	}
	
	public void initWelcomePanel() {
		welcomePane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));				
		welcomePane.setBounds(0, IUIConstants._Banner_Height_, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		welcomePane.setLayout(null);
		bossPane.add(welcomePane);
	}
	
	public void initAdminPanel() {
		adminPane.setBounds(0, IUIConstants._Banner_Height_, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		adminPane.setLayout(null);
		bossPane.add(adminPane);
	}
	
	public void initPlayerPanel() {
		playerPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));				
		playerPane.setBounds(0, IUIConstants._Banner_Height_, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		playerPane.setLayout(null);
		bossPane.add(playerPane);
		playerPane.setParent(this);
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
	}
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, 2000, 1500);
		//GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		bossPane = new JPanel();
		//bossPane.setBorder(new EmptyBorder(5, 5, 5, 5));			
		setContentPane(bossPane);
		bossPane.setLayout(null);
		
		lblServerStatus.setBounds(10, 5, 68, 23);		
		bossPane.add(lblServerStatus);
				
		lblSerialPortStatus.setBounds(83, 5, 68, 23);
		bossPane.add(lblSerialPortStatus);
		
		lblMachineStatus.setBounds(156, 5, 68, 23);
		bossPane.add(lblMachineStatus);
		
		
		
		lblCurrentDate.setBounds(1179, 0, 246, 33);
		bossPane.add(lblCurrentDate);

		lblSystemInfoLog.setOpaque(true);
		lblSystemInfoLog.setVerticalTextPosition(JLabel.CENTER);
		lblSystemInfoLog.setHorizontalAlignment(JLabel.CENTER);
		lblSystemInfoLog.setBounds(234, 5, 939, 33);		
		lblSystemInfoLog.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				SerialPortEvent event = new SerialPortEvent(null, 1, false, false);
				serialConnector.serialEvent(event);
				if (true == lblSystemInfoLog.getForeground().equals(new Color(60, 60, 60))) {
					lblSystemInfoLog.setForeground(new Color(250, 250, 250));
					lblSystemInfoLog.setBackground(new Color(250, 250, 250));
				}
				else {
					lblSystemInfoLog.setForeground(new Color(60, 60, 60));
					if (true == isLastOperationSuccessful) {
						lblSystemInfoLog.setBackground(Color.GREEN);
					}
					else {
						lblSystemInfoLog.setBackground(Color.RED);
					}
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		bossPane.add(lblSystemInfoLog);
	}
	
	public void switchMode(final ICommonConstants.MainFrameActivePanelEnum activePanel) {
		switch (activePanel){
			case _MainFrame_ActivePanel_Welcome_:
			{
				welcomePane.setVisible(true);
				welcomePane.validate();
				playerPane.setVisible(false);
				adminPane.setVisible(false);				
				break;
			}
			case _MainFrame_ActivePanel_Player_:
			{
				welcomePane.setVisible(false);
				playerPane.setVisible(true);
				playerPane.validate();
				adminPane.setVisible(false);	
				break;
			}
			case _MainFrame_ActivePanel_Admin_:
			{
				welcomePane.setVisible(false);
				playerPane.setVisible(false);
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
			case _SubMessageType_MainFrame_SerialPortStatus_:
			{
				updateStatusLable(lblSerialPortStatus, vo.getParam1());
				break;
			}
			case _SubMessageType_MainFrame_MachineStatus_:
			{
				updateStatusLable(lblMachineStatus, vo.getParam1());
				break;
			}
			case _SubMessageType_MainFrame_SystemInfoLog_:
			{
				updateSystemInfoLable(vo.getMessage(), vo.getIsBooleanParam3());
				break;
			}
			case _SubMessageType_MainFrame_VerifyDialog_:
			{
				verifyDialog.setVisible(true);
				break;
			}	
			case _SubMessageType_MainFrame_ExitDlg_:
			{
				ExitDialog dlg = new ExitDialog(configurationManager.getConfigurationVO());
				dlg.setVisible(true);
				System.exit(0);
				break;
			}
		default:
			break;
		}
	}
	
	public void updateSystemInfoLable(final String message, boolean isSuccessful) {
		System.out.println("message:" + message + "--isSuccessful:" + isSuccessful);
		
		isLastOperationSuccessful = isSuccessful;
		final String timestampStr = dateConverter.getCurrentTimestampInNineteenBitsInGMT();
		
		lblSystemInfoLog.setText(timestampStr + "  " + message);
		lblSystemInfoLog.setForeground(new Color(60, 60, 60));
		if (true == isSuccessful) {
			lblSystemInfoLog.setBackground(Color.GREEN);
		}
		else {
			lblSystemInfoLog.setBackground(Color.RED);
		}
			
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		
		int hideDelay = configurationManager.getConfigurationVO().getHideDelayInSecond_SystemInfoMessage();
		scheduler.schedule(new Runnable( ) {  
            public void run() {
            	lblSystemInfoLog.setForeground(new Color(250, 250, 250));
				lblSystemInfoLog.setBackground(new Color(250, 250, 250));				
            }  
        }, hideDelay, TimeUnit.SECONDS);        
	}
	
	public void updateStatusLable(final JLabel label, final long status) {
		if (ICommonConstants._ConnectionStatus_Online_ == status) {
			label.setIcon(connectedIcon);
		}
		else if (ICommonConstants._ConnectionStatus_Offline_ == status) {
			label.setIcon(disconnectedIcon);
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
