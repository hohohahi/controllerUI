package com.bottle.ui.components.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageListener;
import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IConfigurationManager;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.data.vo.ProductionDataVO;
import com.bottle.business.data.vo.RealtimeStasticDataVO;
import com.bottle.business.money.IReturnMoneyService;
import com.bottle.common.IBasicDataTypeHelper;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ILanguageConstants;
import com.bottle.hardware.rxtx.command.ICommandSelector;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.common.MyTableWrapper;
import com.bottle.ui.components.player.component.CBScrollBarUI;
import com.bottle.ui.components.player.sub.PhoneNumberInputDlg;
import com.bottle.ui.components.player.sub.PlayerPictureBannerPanel;
import com.bottle.ui.components.player.sub.RealCheckResultListTableModel;
import com.bottle.ui.components.player.sub.RealCheckResultTableCandidate;
import com.bottle.ui.constants.IUIConstants;

@Component
public class PlayerPanel extends JPanel implements IMessageListener{
	private static final long serialVersionUID = 1L;
	private boolean isStarted = false;
	
	@Autowired
	private IBasicDataTypeHelper basicDataTypeHelper;
	
	@Autowired
	private ICommandSelector machineCommandSelector;
	
	@Autowired
	protected IMessageQueueManager messageManager;
	
	@Autowired
	private IReturnMoneyService returnMoneyService;
	
	@Autowired
	private IProductionDataManager productionDataManager;
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	@Autowired
	private PhoneNumberInputDlg phoneNumberInputDlg;
	
	JLabel ValidNumTitleLabel = new FontLabel("\u6295\u74F6\u6570", 72);
	JLabel validNumLabel = new FontLabel("b", 72);
	JLabel MoneyTitleLabel = new FontLabel("\u91D1\u989D", 72);
	JLabel moneyLabel = new FontLabel("d", 72);
	FontLabel expireTimeLabel = new FontLabel(36);
	
	private MyTableWrapper realCheckResultTableWrapper;
	private JTable realCheckResultTable;;
	
	final CircleButton returnProfitButton = new CircleButton("\u8FD4\u5229", Color.BLUE, Color.GRAY, Color.LIGHT_GRAY, new Dimension(200, 200));
	final CircleButton donationButton = new CircleButton("\u6350\u8D60", new Color(80, 240, 60), Color.GRAY, Color.LIGHT_GRAY, new Dimension(200, 200));
	private PlayerPictureBannerPanel bannerPanel = new PlayerPictureBannerPanel();
	private int expiredTime_InSecond = 0;
	private Timer expireTimer = new Timer();
	@PostConstruct
	public void initialize() {
		messageManager.addListener(this);
		updateStatisticData();
		updateRealCheckResultTable();
	}
	
	public PlayerPanel() {		
		//this.setPreferredSize(new Dimension(IUIConstants._Total_Width_, IUIConstants._Total_Height_));
		//this.setPreferredSize();
		setLayout(null);
		this.setAutoscrolls(true);
		this.setPreferredSize(new Dimension(IUIConstants._Total_Width_, IUIConstants._Total_Height_));
		ValidNumTitleLabel.setBounds(59, 83, 250, 69);		
		ValidNumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(ValidNumTitleLabel);
		validNumLabel.setBounds(421, 83, 195, 69);
		validNumLabel.setText("0");
		validNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(validNumLabel);								
		MoneyTitleLabel.setBounds(59, 246, 234, 69);
		MoneyTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(MoneyTitleLabel);				
		moneyLabel.setBounds(421, 246, 195, 69);
		moneyLabel.setText("0");
		moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(moneyLabel);
		
		realCheckResultTableWrapper = createWrapper();
	    this.realCheckResultTable = realCheckResultTableWrapper.getTable();
	    realCheckResultTable.setBounds(30, 48, 536, 388);
	    
		JScrollPane scrollPane_server=new JScrollPane(realCheckResultTable);
		scrollPane_server.setBounds(639, 12, 401, 695);
		scrollPane_server.getVerticalScrollBar().setUI(new CBScrollBarUI());
	    this.add(scrollPane_server);
	    
		returnProfitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				phoneNumberInputDlg.reset();
				phoneNumberInputDlg.setVisible(true);
				
				String phoneNumberStr = phoneNumberInputDlg.getPhoneNumber();
				if (StringUtils.isNotEmpty(phoneNumberStr)) {
					System.out.println(phoneNumberStr);
					returnMoneyService.pay(phoneNumberStr);
				}
				else {
					System.out.println("empty");
				}
			}
		});
		
		returnProfitButton.setBounds(445, 1380, 250, 250);
		returnProfitButton.setEnabled(false);
		add(returnProfitButton);
		donationButton.setBounds(759, 1380, 250, 250);
		donationButton.setEnabled(false);
		add(donationButton);
		
		expireTimeLabel.setBounds(5, 0, 400, 40);
		
		add(expireTimeLabel);
		
		bannerPanel.setBounds(5, 395, 625, 800);	
		add(bannerPanel);
		
		JButton btnNewButton = new JButton("\u8BF7\u8F93\u5165\u6709\u6548\u7684\u624B\u673A\u53F7\u7801()");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				expiredTime_InSecond = 0;
				returnProfitButton.setEnabled(true);
				returnProfitButton.repaint();
			}
		});
		btnNewButton.setBounds(339, 12, 98, 28);
		add(btnNewButton);
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
		if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_PlayerPanel_StartCommandButton_.equals(subMessageType)) {
			//startCommandButton.setEnabled(true);
			isStarted = vo.getIsBooleanParam3();
			//showStartAndStopButtons();
			if (false == isStarted) {
				//super.sendSystemInfo("error in start command.");
			}
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_PlayerPanel_StopCommandButton_.equals(subMessageType)) {
			//stopCommandButton.setEnabled(true);
			isStarted = !vo.getIsBooleanParam3();
			//showStartAndStopButtons();
			if (true == isStarted) {
				//super.sendSystemInfo("error in stop command.");
			}
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_PlayerPanel_RealProductionInfoPanel_.equals(subMessageType)) {
			updateRealCheckResultTable();
			updateStatisticData();
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_PlayerPanel_ThrowBottleActionDetected_.equals(subMessageType)) {
			expiredTime_InSecond = 0;
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_PlayerPanel_InvalidBottleTakenAwayDetected_.equals(subMessageType)) {
			//expiredTime_InSecond = 0;
		}
	}

	public void updateRealCheckResultTable() {
		realCheckResultTableWrapper.clear();
		realCheckResultTable.invalidate();

		//existedTemplateTableWrapper.setTableModel(new ModelListTableModel());
		
		final List<ProductionDataVO> historyList = productionDataManager.getHistoryRealtimeStasticData();
		for (final ProductionDataVO vo : historyList) {
			realCheckResultTableWrapper.add(new RealCheckResultTableCandidate(vo.getTemplateName(), vo.getPrice()));
		}
		
		int scrollBarWidth = UIManager.getInt("ScrollBar.width");
		System.out.println(scrollBarWidth);
		
		 realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTableWrapper.add(new RealCheckResultTableCandidate("Test", 1.0d));
		    realCheckResultTable.invalidate();
	}

	public void updateStatisticData() {
		RealtimeStasticDataVO data = productionDataManager.getRealtimeStasticDataVO();
		validNumLabel.setText(data.getTotalValidNum() + "");
		validNumLabel.validate();
		moneyLabel.setText(data.getTotalMoney() + "");
		moneyLabel.validate();
		
		
		if (true == basicDataTypeHelper.isGreaterThanZero(data.getTotalMoney())) {
			donationButton.setEnabled(true);
			donationButton.repaint();
			returnProfitButton.setEnabled(true);
			returnProfitButton.repaint();
		}
		else {
			donationButton.setEnabled(false);
			returnProfitButton.setEnabled(false);
			donationButton.repaint();
			returnProfitButton.repaint();
		}
	}
	
	@Override
	public MessageSourceEnum getMessageType() {
		return MessageSourceEnum._MessageSource_PlayerPanel_;
	}
	
	@SuppressWarnings("serial")
	public MyTableWrapper createWrapper() {
		return new MyTableWrapper(new ArrayList<String>(){{add(ILanguageConstants._RealProductionInfoPanel_ModelName_);
														   add(ILanguageConstants._RealProductionInfoPanel_Price_);}}, 
							              new ArrayList<Integer>(){{add(280); add(60);}}, new RealCheckResultListTableModel());
	}
	
	public void active() {
		initExpireTimer();
		bannerPanel.initChangeBannerPictureTimeThread();
		
		if (true == productionDataManager.getIsSerialPortInitialized()) {
			final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Start_);
			sender.send();
		}		
	}
	
	public void deactive() {
		expireTimer.cancel();
		expiredTime_InSecond = 0;
		bannerPanel.resetTimer();
		
		if (true == productionDataManager.getIsSerialPortInitialized()) {
			final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Stop_);
			sender.send();
		}		
	}
	
	public void initExpireTimer() {
		int playerPanelIdelTime_InSeconds = configurationManager.getConfigurationVO().getPlayerPanelIdelTime_InSeconds();
		expireTimer = new Timer();  
		expireTimer.schedule(new TimerTask() {  
            public void run() {              	            	
            	expiredTime_InSecond++;
            	bannerPanel.repaint();
            	updateExpireLabel(playerPanelIdelTime_InSeconds - expiredTime_InSecond);
            	if (expiredTime_InSecond > playerPanelIdelTime_InSeconds) {            		
            		expiredTime_InSecond = 0;
            		MessageVO vo = new MessageVO();
    				vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_.getId());
    				vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
    				vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
    				messageManager.push(vo);
    			
    				expireTimer.cancel();
            	}
            }  
        }, 0, 1000); 
	}
	
	public void updateExpireLabel(int leftTimeInSeconds) {
		expireTimeLabel.setText("  " + leftTimeInSeconds + "秒后返回欢迎界面！");
		expireTimeLabel.repaint();
		expireTimeLabel.invalidate();
	}
}
