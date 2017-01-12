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
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ILanguageConstants;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.common.MyTableWrapper;
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
	protected IMessageQueueManager messageManager;
	
	@Autowired
	private IReturnMoneyService returnMoneyService;
	
	@Autowired
	private IProductionDataManager productionDataManager;
	
	@Autowired
	private IConfigurationManager configurationManager;
	
	JLabel ValidNumTitleLabel = new FontLabel("\u6709\u6548\u6295\u74F6\u6570", 42);
	JLabel validNumLabel = new FontLabel("b", 42);
	JLabel MoneyTitleLabel = new FontLabel("\u8FD4\u5229\u91D1\u989D", 42);
	JLabel moneyLabel = new FontLabel("d", 42);
	
	private MyTableWrapper realCheckResultTableWrapper;
	private JTable realCheckResultTable;;
	
	final CircleButton returnProfitButton = new CircleButton("\u8FD4\u5229", Color.BLUE, Color.GRAY, new Dimension(200, 200));
	final CircleButton donationButton = new CircleButton("\u6350\u8D60", new Color(80, 240, 60), Color.GRAY, new Dimension(200, 200));
	private PlayerPictureBannerPanel bannerPanel = new PlayerPictureBannerPanel();
	private int expiredTime_InSecond = 0;
	@PostConstruct
	public void initialize() {
		messageManager.addListener(this);
		updateStatisticData();
	}
	
	public PlayerPanel() {
		
		//this.setPreferredSize(new Dimension(IUIConstants._Total_Width_, IUIConstants._Total_Height_));
		//this.setPreferredSize();
		setLayout(null);
		this.setAutoscrolls(true);
		this.setPreferredSize(new Dimension(IUIConstants._Total_Width_, IUIConstants._Total_Height_));
		ValidNumTitleLabel.setBounds(-12, 65, 335, 69);		
		ValidNumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(ValidNumTitleLabel);
		validNumLabel.setBounds(287, 65, 118, 69);
		validNumLabel.setText("0");
		validNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(validNumLabel);								
		MoneyTitleLabel.setBounds(-12, 176, 335, 69);
		MoneyTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(MoneyTitleLabel);				
		moneyLabel.setBounds(287, 176, 118, 69);
		moneyLabel.setText("0");
		moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(moneyLabel);
		
		realCheckResultTableWrapper = createWrapper();
	    this.realCheckResultTable = realCheckResultTableWrapper.getTable();
	    realCheckResultTable.setBounds(30, 48, 536, 388);
	    
		JScrollPane scrollPane_server=new JScrollPane(realCheckResultTable);
		scrollPane_server.setBounds(440, 12, 600, 586);
	    this.add(scrollPane_server);
	    
		returnProfitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PhoneNumberInputDlg dlg = new PhoneNumberInputDlg();				
				dlg.setVisible(true);
				
				String phoneNumberStr = dlg.getPhoneNumber();
				if (StringUtils.isNotEmpty(phoneNumberStr)) {
					System.out.println(phoneNumberStr);
					returnMoneyService.pay(phoneNumberStr);
				}
				else {
					System.out.println("empty");
				}
			}
		});
		
		returnProfitButton.setBounds(445, 1300, 250, 250);
		add(returnProfitButton);
		donationButton.setBounds(759, 1300, 250, 250);
		add(donationButton);
		
		bannerPanel.setBounds(5, 295, 423, 675);
		List<String> imageNameList = new ArrayList<String>();
		imageNameList.add("playerbanner.png");
		imageNameList.add("greenearth.jpg");
		bannerPanel.setImageFileNameList(imageNameList);
		bannerPanel.setWeight(423);
		bannerPanel.setHeight(675);
		add(bannerPanel);
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
	}

	public void updateRealCheckResultTable() {
		realCheckResultTableWrapper.clear();
		realCheckResultTable.invalidate();

		//existedTemplateTableWrapper.setTableModel(new ModelListTableModel());
		
		final List<ProductionDataVO> historyList = productionDataManager.getHistoryRealtimeStasticData();
		long index = 0;
		for (final ProductionDataVO vo : historyList) {
			index++;
			realCheckResultTableWrapper.add(new RealCheckResultTableCandidate(index, vo.getTemplateName(), vo.getErrorCode(), vo.getPrice()));
		}
	}

	public void updateStatisticData() {
		RealtimeStasticDataVO data = productionDataManager.getRealtimeStasticDataVO();
		validNumLabel.setText(data.getTotalValidNum() + "");
		validNumLabel.validate();
		moneyLabel.setText(data.getTotalMoney() + "");
		moneyLabel.validate();
	}
	
	@Override
	public MessageSourceEnum getMessageType() {
		return MessageSourceEnum._MessageSource_PlayerPanel_;
	}
	
	@SuppressWarnings("serial")
	public MyTableWrapper createWrapper() {
		return new MyTableWrapper(new ArrayList<String>(){{add(ILanguageConstants._RealProductionInfoPanel_Order_);
														   add(ILanguageConstants._RealProductionInfoPanel_ModelName_); 
														   add(ILanguageConstants._RealProductionInfoPanel_ErrorCode_);
														   add(ILanguageConstants._RealProductionInfoPanel_Price_);}}, 
							              new ArrayList<Integer>(){{add(50); add(160); add(97); add(60);}}, new RealCheckResultListTableModel());
	}
	
	public void initExpireTimer() {
		int playerPanelIdelTime_InSeconds = configurationManager.getConfigurationVO().getPlayerPanelIdelTime_InSeconds();
		Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {              	            	
            	expiredTime_InSecond++;
            	bannerPanel.setLeftExpiredTime_InSecond(playerPanelIdelTime_InSeconds - expiredTime_InSecond);
            	bannerPanel.repaint();
            	
            	System.out.println("expiredTime_InSecond:" + expiredTime_InSecond);
            	if (expiredTime_InSecond > playerPanelIdelTime_InSeconds) {            		
            		expiredTime_InSecond = 0;
            		MessageVO vo = new MessageVO();
    				vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_.getId());
    				vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
    				vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
    				messageManager.push(vo);
    			
            		timer.cancel();
            	}
            }  
        }, 0, 1000); 
	}
}
