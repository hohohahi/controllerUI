package com.bottle.ui;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageListener;
import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.common.IResourceLoader;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.hardware.rxtx.ISerialCommConnector;
import com.bottle.ui.components.admin.AdminPanel;
import com.bottle.ui.components.exit.ExitDialog;
import com.bottle.ui.components.player.PlayerPanel;
import com.bottle.ui.components.verify.VerifyDialog;
import com.bottle.ui.components.welcome.WelcomePanel;
import com.bottle.ui.constants.IUIConstants;

@Component
public class MainFrame extends JFrame implements IMessageListener {
	private static final long serialVersionUID = -632890096779473173L;
	
	final Logger logger = Logger.getLogger(this.getClass());
	
	private JPanel bossPane;
	
	@Autowired
	private WelcomePanel welcomePane;
	
	@Autowired
	private PlayerPanel playerPane;
	
	@Autowired
	private AdminPanel adminPane;
	
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
	
	@PostConstruct
	public void initialize() {
		final String className = this.getClass().getName();
		String outputMessage = className + " initialized";

		logger.debug(outputMessage);
		
		initWelcomePanel();
		initPlayerPanel();
		initAdminPanel();
		initMessage();
		
		switchMode(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_);
		initSerialCommPort();
	}

	public void initSerialCommPort() {
		serialCommConnector.initSerialPort();
	}

	public void initWelcomePanel() {
		welcomePane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));				
		welcomePane.setBounds(0, 0, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		welcomePane.setLayout(null);
		bossPane.add(welcomePane);
	}
	
	public void initAdminPanel() {
		adminPane.setBounds(0, 0, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		adminPane.setLayout(null);
		bossPane.add(adminPane);
	}
	
	public void initPlayerPanel() {
		playerPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));				
		playerPane.setBounds(0, 0, IUIConstants._Total_Width_, IUIConstants._Total_Height_);		
		playerPane.setLayout(null);
		bossPane.add(playerPane);
		playerPane.setParent(this);
	}
	
	public void initMessage() {
		messageManager.addListener(this);
	}
	
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(0, 0, IUIConstants._Total_Width_, IUIConstants._Total_Height_);
		//GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//getGraphicsConfiguration().getDevice().setFullScreenWindow(this);
		bossPane = new JPanel();		
		setContentPane(bossPane);
		bossPane.setLayout(null);
		
		
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

	@Override
	public MessageSourceEnum getMessageType() {
		return ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_;
	}
}
