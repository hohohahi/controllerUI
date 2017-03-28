package com.bottle.ui.components.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

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
import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.IBasicDataTypeHelper;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.common.constants.ILanguageConstants;
import com.bottle.hardware.rxtx.command.ICommandSelector;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.common.MyTableWrapper;
import com.bottle.ui.components.player.component.CBScrollBarUI;
import com.bottle.ui.components.player.sub.PhoneNumberInputDlg;
import com.bottle.ui.components.player.sub.PlayerPictureBannerPanel;
import com.bottle.ui.components.player.sub.PlayerTemplateDisplayPanel;
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
	
	@Autowired
	private InvalidBottleWarningDlg invalidBottleWarningDlg;
	
	JLabel ValidNumTitleLabel = new FontLabel("\u6295\u74F6\u6570", 72);
	JLabel validNumLabel = new FontLabel("b", 72);
	JLabel MoneyTitleLabel = new FontLabel("\u91D1\u989D", 72);
	JLabel moneyLabel = new FontLabel("d", 72);
	FontLabel expireTimeLabel = new FontLabel(36);
	
	private MyTableWrapper realCheckResultTableWrapper; 
	private JTable realCheckResultTable;;
	
	final CircleButton returnProfitButton = new CircleButton("\u8FD4\u5229", Color.BLUE, Color.GRAY, Color.LIGHT_GRAY, new Dimension(200, 200));
	final CircleButton donationButton = new CircleButton("\u6350\u8D60", new Color(80, 240, 60), Color.GRAY, Color.LIGHT_GRAY, new Dimension(200, 200));
	private PlayerPictureBannerPanel bannerPanel = new PlayerPictureBannerPanel(625, 1000);
	private PlayerTemplateDisplayPanel templateDisplayPanel = new PlayerTemplateDisplayPanel(400, 500);
	private int expiredTime_InSecond = 0;
	private Timer expireTimer = new Timer();
	
	private List<ProductionDataVO> testProductionList = new ArrayList<ProductionDataVO>();
	
	public void initProductionList() {
		ProductionDataVO productionDataVO = new ProductionDataVO();												
		productionDataVO.setTimestampStr("123456");
		productionDataVO.setBarCode("6921168509256");
		productionDataVO.setTemplateName("nongfushanquan550ml");
		productionDataVO.setIsSuccessful(true);	
		
		testProductionList.add(productionDataVO);		
		
		productionDataVO = new ProductionDataVO();												
		productionDataVO.setTimestampStr("123456");
		productionDataVO.setBarCode("6954767415772");
		productionDataVO.setTemplateName("kekoukele600ml");
		productionDataVO.setIsSuccessful(true);	
		
		testProductionList.add(productionDataVO);
		
		productionDataVO = new ProductionDataVO();												
		productionDataVO.setTimestampStr("123456");
		productionDataVO.setBarCode("6940159410043");
		productionDataVO.setTemplateName("baishikele2L");
		productionDataVO.setIsSuccessful(true);	
		
		testProductionList.add(productionDataVO);
		
		productionDataVO = new ProductionDataVO();												
		productionDataVO.setTimestampStr("123456");
		productionDataVO.setBarCode("6954767410173");
		productionDataVO.setTemplateName("kekoukele300ml");
		productionDataVO.setIsSuccessful(true);	
		
		testProductionList.add(productionDataVO);
		
		productionDataVO = new ProductionDataVO();												
		productionDataVO.setTimestampStr("123456");
		productionDataVO.setBarCode("6921168520015");
		productionDataVO.setTemplateName("nongfushanquan1.5L");
		productionDataVO.setIsSuccessful(true);	
		
		testProductionList.add(productionDataVO);
	}
	@PostConstruct
	public void initialize() {
		initProductionList();
		messageManager.addListener(this);
		updateStatisticData();
		updateRealCheckResultTable();
		
		validNumLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*
				MessageVO vo = new MessageVO();
				vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
				vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_VerifyDialog_);
				messageManager.push(vo);
				*/
				
				final ProductionDataVO data = testProductionList.get(testProductionList.size()-1);
				productionDataManager.push(data);
				testProductionList.remove(data);
				updateRealCheckResultTable();
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
		
		moneyLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MessageVO vo = new MessageVO();
				vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
				vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_ExitDlg_);
				messageManager.push(vo);
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
		scrollPane_server.setBounds(639, 12, 401, 800);
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
		
		returnProfitButton.setBounds(445, 1410, 250, 250);
		returnProfitButton.setEnabled(false);
		add(returnProfitButton);
		donationButton.setBounds(759, 1410, 250, 250);
		donationButton.setEnabled(false);
		add(donationButton);
		
		expireTimeLabel.setBounds(5, 1600, 400, 40);
		
		add(expireTimeLabel);
		
		bannerPanel.setBounds(5, 395, 625, 1000);	
		add(bannerPanel);
		
		templateDisplayPanel.setBounds(639, 850, 400, 500);
		add(templateDisplayPanel);
		
		realCheckResultTable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				final String barCode = getBarCodeFromProductionData_BySelection(realCheckResultTable.getSelectedRow());
				templateDisplayPanel.updatePictureByBarCode(barCode);
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
	    }
	    );
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
			//but the message is not working when modelness dlg is shown
			detectInvalidBottleTakenAwayAction();
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_PlayerPanel_InvalidBottle_.equals(subMessageType)) {
			expiredTime_InSecond = 0;
			final long errorCode = vo.getParam1();
			if (errorCode >= 0) {
				final String errorMessage = ILanguageConstants.errorCodeAndErroMessageMap.get(errorCode);
				invalidBottleWarningDlg.setErrorCode(errorCode);
				invalidBottleWarningDlg.setErrorMessage(errorMessage);
				invalidBottleWarningDlg.updateUI();				
			}
			
			int playerPanelErrorBottleIdelTime_InSeconds = configurationManager.getConfigurationVO().getPlayerPanelErrorBottleIdelTime_InSeconds();
			initExpireTimer(playerPanelErrorBottleIdelTime_InSeconds);
			invalidBottleWarningDlg.setVisible(true);

		}
	}
	
	public void detectInvalidBottleTakenAwayAction() {
		invalidBottleWarningDlg.setVisible(false);
		invalidBottleWarningDlg.setThoughtToBeShown(false);
		
		cancelExpireTimer();
	}
	
	public void updateRealCheckResultTable() {
		realCheckResultTableWrapper.clear();
		realCheckResultTable.invalidate();

		//existedTemplateTableWrapper.setTableModel(new ModelListTableModel());
		
		final List<ProductionDataVO> historyList = productionDataManager.getHistoryRealtimeStasticData();
		for (final ProductionDataVO vo : historyList) {
			realCheckResultTableWrapper.add(new RealCheckResultTableCandidate(vo.getTemplateName(), vo.getBarCode(), vo.getPrice()));
		}
		
		if (historyList.size() > 0) {
			realCheckResultTable.setRowSelectionInterval(0, 0);
		}		
	}

	public String getBarCodeFromProductionData_BySelection(int sel) {
		String barCode = "";
		final List<ProductionDataVO> historyList = productionDataManager.getHistoryRealtimeStasticData();
		if (historyList != null) {
			final int size = historyList.size();
			
			if (sel >= size) {
				throw new RuntimeException("pos is bigger than history size. pos:" + sel + "--size:" + size);
			}
			else {
				final ProductionDataVO vo = historyList.get(sel);
				if (null == vo) {
					throw new NullPointerException("vo is null.");
				}
			
				barCode = vo.getBarCode();
			}
		}
		
		return barCode;
	}
	
	public void clearInvalidBottleWarningDlg() {
		invalidBottleWarningDlg.setVisible(false);
		invalidBottleWarningDlg.setThoughtToBeShown(false);
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
			
			this.cancelExpireTimer();
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
		bannerPanel.initChangeBannerPictureTimeThread();
		
		if (true == productionDataManager.getIsSerialPortInitialized()) {
			final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Start_);
			sender.send();
		}		
		
		if (true == invalidBottleWarningDlg.isThoughtToBeShown()) {
			final MessageVO message = new MessageVO();
			message.setMessageSource(MessageSourceEnum._MessageSource_PlayerPanel_);
			message.setSubMessageType(SubMessageTypeEnum._SubMessageType_PlayerPanel_InvalidBottle_);
			message.setParam1(-1L);			
			messageManager.push(message);
		}
		else {
			int playerPanelIdelTime_InSeconds = configurationManager.getConfigurationVO().getPlayerPanelIdelTime_InSeconds();
			initExpireTimer(playerPanelIdelTime_InSeconds);
		}
	}
	
	public void deactive() {
		cancelExpireTimer();
		bannerPanel.resetTimer();
		
		if (true == productionDataManager.getIsSerialPortInitialized()) {
			final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Stop_);
			sender.send();
		}		
	}
	
	public void cancelExpireTimer() {
		expireTimer.cancel();
		expiredTime_InSecond = 0;
		expireTimeLabel.setVisible(false);
	}
	
	public void initExpireTimer(final int playerPanelIdelTime_InSeconds) {
		expireTimer.cancel();
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
    				
    				if (true == invalidBottleWarningDlg.isVisible()) {
    					invalidBottleWarningDlg.setVisible(false);
    					invalidBottleWarningDlg.setThoughtToBeShown(true);
    				}
            	}
            }  
        }, 0, 1000); 
	}
	
	public void updateExpireLabel(int leftTimeInSeconds) {
		expireTimeLabel.setVisible(true);
		expireTimeLabel.setText("  " + leftTimeInSeconds + "\u79D2\u540E\u8FD4\u56DE\u6B22\u8FCE\u754C\u9762!");
		expireTimeLabel.repaint();
		expireTimeLabel.invalidate();
	}
}
