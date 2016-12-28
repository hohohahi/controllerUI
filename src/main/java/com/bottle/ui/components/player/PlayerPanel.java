package com.bottle.ui.components.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;

import org.apache.maven.surefire.shade.org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.service.IMessageListener;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.money.IReturnMoneyService;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.hardware.rxtx.command.ICommandSelector;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.ui.components.common.AbstractBasePanel;
import com.bottle.ui.components.common.FontButton;
import com.bottle.ui.components.player.sub.PhoneNumberInputDlg;
import com.bottle.ui.components.player.sub.RealProductionInfoPanel;

@Component
public class PlayerPanel extends AbstractBasePanel implements IMessageListener{
	private static final long serialVersionUID = 1L;
	private boolean isStarted = false;
	
	@Autowired
	private ICommandSelector machineCommandSelector;
	
	final FontButton startCommandButton = new FontButton("\u5F00\u59CB\u6295\u74F6");
	final FontButton stopCommandButton = new FontButton("\u505C\u6B62\u6295\u74F6");
	final FontButton backCommandButton = new FontButton("\u8FD4\u56DE\u4E3B\u754C\u9762");
	final FontButton returnMoneyCommandButton = new FontButton("\u8FD4\u5229");
	JFrame parentFrame = (JFrame)this.getParent();
	@Autowired
	private RealProductionInfoPanel realProductionInfoPanel;
	
	@Autowired
	private IReturnMoneyService returnMoneyService;
	
	@PostConstruct
	public void initialize() {
		messageManager.addListener(this);
		initLabel();
	}
	
	public void showStartAndStopButtons() {
		startCommandButton.setEnabled(!isStarted);
		startCommandButton.validate();
		stopCommandButton.setEnabled(isStarted);
		stopCommandButton.validate();
	}
	
	public void initLabel() {
		realProductionInfoPanel.setBounds(100, 38, 1400, 593);
		realProductionInfoPanel.setLayout(null);
		add(realProductionInfoPanel);
	}
	
	public PlayerPanel() {
		setLayout(null);
				
		startCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Start_);
				sender.send();
				startCommandButton.setEnabled(false);
			}
		});
		startCommandButton.setBounds(311, 590, 160, 70);
		add(startCommandButton);
		
		
		stopCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Stop_);
				sender.send();
				stopCommandButton.setEnabled(false);
			}
		});
		
		stopCommandButton.setBounds(510, 590, 160, 70);
		add(stopCommandButton);
		backCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageVO vo = new MessageVO();
				vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_.getId());
				vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
				vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
				messageManager.push(vo);
			}
		});
		
		backCommandButton.setBounds(906, 590, 160, 70);
		add(backCommandButton);
		returnMoneyCommandButton.addActionListener(new ActionListener() {
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
		
		returnMoneyCommandButton.setBounds(712, 590, 160, 70);
		add(returnMoneyCommandButton);				
		
		showStartAndStopButtons();
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
				super.sendSystemInfo("error in start command.");
			}
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_PlayerPanel_StopCommandButton_.equals(subMessageType)) {
			stopCommandButton.setEnabled(true);
			isStarted = !vo.getIsBooleanParam3();
			showStartAndStopButtons();
			if (true == isStarted) {
				super.sendSystemInfo("error in stop command.");
			}
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_PlayerPanel_RealProductionInfoPanel_.equals(subMessageType)) {
			realProductionInfoPanel.processChildMessage(vo);
		}
	}

	@Override
	public MessageSourceEnum getMessageType() {
		return MessageSourceEnum._MessageSource_PlayerPanel_;
	}

	@Override
	public void processChildMessage(MessageVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParent(java.awt.Component parent) {
		this.parentFrame = (JFrame)parent;
	}
}
