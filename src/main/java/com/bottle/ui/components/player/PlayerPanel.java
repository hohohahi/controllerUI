package com.bottle.ui.components.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.IMessageListener;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.hardware.rxtx.command.ICommandSelector;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.ui.components.common.AbstractBasePanel;
import com.bottle.ui.components.common.CommandButton;

@Component
public class PlayerPanel extends AbstractBasePanel implements IMessageListener{
	private static final long serialVersionUID = 1L;
	private boolean isStarted = false;
	
	@Autowired
	private ICommandSelector machineCommandSelector;
	
	final CommandButton startCommandButton = new CommandButton("\u5F00\u59CB\u6295\u74F6");
	final CommandButton stopCommandButton = new CommandButton("\u505C\u6B62\u6295\u74F6");
	final CommandButton backCommandButton = new CommandButton("\u8FD4\u56DE\u4E3B\u754C\u9762");
	final CommandButton returnMoneyCommandButton = new CommandButton("\u8FD4\u5229");
	@PostConstruct
	public void initialize() {
		messageManager.addListener(this);
	}
	
	public void showStartAndStopButtons() {
		startCommandButton.setEnabled(!isStarted);
		startCommandButton.validate();
		stopCommandButton.setEnabled(isStarted);
		stopCommandButton.validate();
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
		startCommandButton.setBounds(311, 559, 160, 70);
		add(startCommandButton);
		
		
		stopCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Stop_);
				sender.send();
				stopCommandButton.setEnabled(false);
			}
		});
		
		stopCommandButton.setBounds(510, 559, 160, 70);
		add(stopCommandButton);
		backCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		backCommandButton.setBounds(906, 559, 160, 70);
		add(backCommandButton);
		returnMoneyCommandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		returnMoneyCommandButton.setBounds(712, 559, 160, 70);
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
	}

	@Override
	public MessageSourceEnum getMessageType() {
		return MessageSourceEnum._MessageSource_PlayerPanel_;
	}
}
