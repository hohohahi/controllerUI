package com.bottle.ui.components.admin;

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
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.business.verify.ISecretTriggerManager;
import com.bottle.common.IDateConverter;
import com.bottle.common.ILoggerHelper;
import com.bottle.common.IResourceLoader;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.ui.components.admin.config.ParameterConfigPanel;
import com.bottle.ui.components.admin.serialdebug.SerialCommandDebugPanel;
import com.bottle.ui.components.admin.template.TemplateManagementPanel;
import com.bottle.ui.components.common.AbstractBasePanel;
import com.bottle.ui.components.common.ClickFontLabel;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.exit.ExitDialog;

import gnu.io.SerialPortEvent;
import javax.swing.JPanel;

@Component
public class AdminPanel extends AbstractBasePanel {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ILoggerHelper loggerHelper;	
	
	@Autowired
	private IResourceLoader resourceLoader;
	
	@Autowired
	private IDateConverter dateConverter;
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	@Autowired
	private JTabbedPane tabbedpane;
	
	@Autowired
	private SerialCommandDebugPanel serialCommandDebugPanel;
	
	@Autowired
	private ParameterConfigPanel parameterConfigPanel;
	
	@Autowired
	private TemplateManagementPanel templateManagementPanel;
	
	@Autowired
	private ISecretTriggerManager adminPanelVerifyManager;
	
	final Logger logger = Logger.getLogger(this.getClass());
	
	private Icon connectedIcon = new ImageIcon();
	private Icon disconnectedIcon = new ImageIcon();
	
	private final ClickFontLabel lblServerStatus = new ClickFontLabel(1);
	private final ClickFontLabel lblSerialPortStatus = new ClickFontLabel(2);
	private final ClickFontLabel lblMachineStatus = new ClickFontLabel(3);
	private final JLabel lblSystemInfoLog = new FontLabel();
	private boolean isLastOperationSuccessful = false;
	private JPanel headPane = new JPanel();
	public AdminPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		
		headPane.setBounds(0, 0, 1050, 50);
		add(headPane);
	}
	
	public void initStatusLabels(){
		updateStatusLable(lblServerStatus, ICommonConstants._ConnectionStatus_Offline_);
		updateStatusLable(lblMachineStatus, ICommonConstants._ConnectionStatus_Offline_);
		updateStatusLable(lblSerialPortStatus, ICommonConstants._ConnectionStatus_Offline_);
		
		lblServerStatus.setTriggerManager(adminPanelVerifyManager);
		lblMachineStatus.setTriggerManager(adminPanelVerifyManager);
		lblSerialPortStatus.setTriggerManager(adminPanelVerifyManager);
	}
	
	public void initTab() {
		tabbedpane.setBounds(52, 30, 998, 1650);
		
		tabbedpane.add("\u4E32\u53E3\u547D\u4EE4\u8C03\u8BD5", serialCommandDebugPanel); //serial command debug
		tabbedpane.add("\u6A21\u7248\u7BA1\u7406", templateManagementPanel);   //model management
		tabbedpane.add("\u64CD\u4F5C\u65E5\u5FD7", new JLabel(""));   //operation log
		tabbedpane.add("\u53C2\u6570\u8BBE\u7F6E", parameterConfigPanel);   //parameter setting
		tabbedpane.add("\u8FD4\u56DE\u4E3B\u754C\u9762", new JLabel(""));  //return to main UI
		tabbedpane.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				int index = tabbedpane.getSelectedIndex();
				if (index == (tabbedpane.getTabCount()-1)) {
					MessageVO vo = new MessageVO();
					vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_.getId());
					vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
					vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
					messageManager.push(vo);
					
					tabbedpane.setSelectedIndex(0);
				}
				
				templateManagementPanel.flush();
			}
			
		});
		this.tabbedpane.setTabPlacement(2);
		add(tabbedpane);	
		
		lblServerStatus.setBounds(10, 5, 68, 23);		
		headPane.add(lblServerStatus);
				
		lblSerialPortStatus.setBounds(83, 5, 68, 23);
		headPane.add(lblSerialPortStatus);
		
		lblMachineStatus.setBounds(156, 5, 68, 23);
		headPane.add(lblMachineStatus);

		lblSystemInfoLog.setOpaque(true);
		lblSystemInfoLog.setVerticalTextPosition(JLabel.CENTER);
		lblSystemInfoLog.setHorizontalAlignment(JLabel.CENTER);
		lblSystemInfoLog.setBounds(234, 5, 939, 33);		
		lblSystemInfoLog.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
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
		headPane.add(lblSystemInfoLog);
	}
	
	@PostConstruct
	public void initialize() {
		super.addMessageListener();
		initTab();
		initStatusLabels();
		initStatusIcons();
	}

	@Override
	public void process(MessageVO vo) {
		if (null == vo) {
			throw new NullPointerException("vo is null.");
		}
		
		final ICommonConstants.MessageSourceEnum messageSource =vo.getMessageSource();
		if (false == messageSource.equals(getMessageType())) {
			return;
		}
		
		final ICommonConstants.SubMessageTypeEnum subMessageType = vo.getSubMessageType();
		switch (subMessageType) {
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

	@Override
	public MessageSourceEnum getMessageType() {
		return MessageSourceEnum._MessageSource_AdminPanel_;
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
