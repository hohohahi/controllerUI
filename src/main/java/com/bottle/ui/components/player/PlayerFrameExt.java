package com.bottle.ui.components.player;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageListener;
import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.data.vo.ProductionDataVO;
import com.bottle.business.data.vo.RealtimeStasticDataVO;
import com.bottle.business.money.IReturnMoneyService;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ILanguageConstants;
import com.bottle.hardware.rxtx.command.ICommandSelector;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.ui.components.common.FontButton;
import com.bottle.ui.components.common.FontLabel;
import com.bottle.ui.components.common.MyTableWrapper;
import com.bottle.ui.components.player.sub.PhoneNumberInputDlg;
import com.bottle.ui.components.player.sub.RealCheckResultListTableModel;
import com.bottle.ui.components.player.sub.RealCheckResultTableCandidate;
import com.bottle.ui.constants.IUIConstants;

public class PlayerFrameExt extends JFrame implements IMessageListener{
	private static final long serialVersionUID = 1L;
	private boolean isStarted = false;
	
	@Autowired
	private ICommandSelector machineCommandSelector;
	
	@Autowired
	protected IMessageQueueManager messageManager;
	
	@Autowired
	private IReturnMoneyService returnMoneyService;
	
	@Autowired
	private IProductionDataManager productionDataManager;
	
	JLabel ValidNumTitleLabel = new FontLabel("\u6709\u6548\u6295\u74F6\u6570", 42);
	JLabel validNumLabel = new FontLabel("b", 42);
	JLabel MoneyTitleLabel = new FontLabel("\u8FD4\u5229\u91D1\u989D", 42);
	JLabel moneyLabel = new FontLabel("d", 42);
	
	private MyTableWrapper realCheckResultTableWrapper;
	private JTable realCheckResultTable;;
	
	final FontButton startCommandButton = new FontButton("\u5F00\u59CB\u6295\u74F6");
	
	final CircleButton returnProfitButton = new CircleButton("\u8FD4\u5229", Color.BLUE, Color.GRAY, new Dimension(200, 200));
	final CircleButton donationButton = new CircleButton("\u6350\u8D60", new Color(80, 240, 60), Color.GRAY, new Dimension(200, 200));
	@PostConstruct
	public void initialize() {
		messageManager.addListener(this);
		updateStatisticData();
	}
	
	public void showStartAndStopButtons() {
		startCommandButton.setBounds(80, 369, 160, 70);
		startCommandButton.setEnabled(!isStarted);
		startCommandButton.validate();
	}
	
	public PlayerFrameExt() {
		
		//this.setPreferredSize(new Dimension(IUIConstants._Total_Width_, IUIConstants._Total_Height_));
		//this.setPreferredSize();
		getContentPane().setLayout(null);
		setUndecorated(true);
		
		ValidNumTitleLabel.setBounds(-12, 65, 335, 69);		
		ValidNumTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(ValidNumTitleLabel);
		validNumLabel.setBounds(287, 65, 118, 69);
		validNumLabel.setText("0");
		validNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(validNumLabel);								
		MoneyTitleLabel.setBounds(-12, 176, 335, 69);
		MoneyTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(MoneyTitleLabel);				
		moneyLabel.setBounds(287, 176, 118, 69);
		moneyLabel.setText("0");
		moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(moneyLabel);
		
		realCheckResultTableWrapper = createWrapper();
	    this.realCheckResultTable = realCheckResultTableWrapper.getTable();
	    realCheckResultTable.setBounds(30, 48, 536, 388);
	    
		JScrollPane scrollPane_server=new JScrollPane(realCheckResultTable);
		scrollPane_server.setBounds(440, 71, 600, 424);
	    getContentPane().add(scrollPane_server);
	    
		startCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Start_);
				sender.send();
				startCommandButton.setEnabled(false);
			}
		});
		getContentPane().add(startCommandButton);
		
		showStartAndStopButtons();
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
		getContentPane().add(returnProfitButton);
		donationButton.setBounds(759, 1300, 250, 250);
		getContentPane().add(donationButton);
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
			startCommandButton.setEnabled(true);
			isStarted = vo.getIsBooleanParam3();
			showStartAndStopButtons();
			if (false == isStarted) {
				//super.sendSystemInfo("error in start command.");
			}
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_PlayerPanel_StopCommandButton_.equals(subMessageType)) {
			isStarted = !vo.getIsBooleanParam3();
			showStartAndStopButtons();
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
		return null;
	}
}
