package com.bottle.ui.components.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.border.BevelBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bottle.business.common.IMessageListener;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.DebugCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.hardware.rxtx.command.ICommandSelector;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.ui.components.common.AbstractBasePanel;
import com.bottle.ui.components.common.CommandButton;

@Component
public class AdminPanel extends AbstractBasePanel implements IMessageListener {
	private static final long serialVersionUID = 1L;
	private CommandButton openDoorButton = new CommandButton("\u6253\u5F00\u8231\u95E8");
	private CommandButton closeDoorButton = new CommandButton("\u5173\u95ED\u8231\u95E8");
	private CommandButton platformDownButton = new CommandButton("\u76AE\u5E26\u5E73\u53F0\u4E0B\u964D");
	private CommandButton platformUpButton = new CommandButton("\u76AE\u5E26\u5E73\u53F0\u4E0A\u5347");
	private CommandButton movePositiveButton = new CommandButton("\u76AE\u5E26\u6B63\u5411\u8FD0\u884C");
	private CommandButton moveNegativeButton = new CommandButton("\u76AE\u5E26\u53CD\u5411\u8FD0\u884C");
	private CommandButton stopMoveButton = new CommandButton("\u76AE\u5E26\u505C\u6B62");
	private CommandButton moveWheelButton = new CommandButton("\u6EDA\u8F6E\u8F6C\u52A8");
	private CommandButton stopWheelButton = new CommandButton("\u6EDA\u8F6E\u505C\u6B62");
	private CommandButton openLightButton = new CommandButton("\u6253\u5F00\u5149\u6E90");
	private CommandButton closeLightButton = new CommandButton("\u5173\u95ED\u5149\u6E90");
	private CommandButton returnButton = new CommandButton("\u8FD4\u56DE\u4E3B\u754C\u9762");
	
	@Autowired
	private ICommandSelector machineCommandSelector;
	
	public AdminPanel() {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		
		openDoorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._OpenDoor_.getAid());
				openDoorButton.setEnabled(false);
			}
		});
		
		openDoorButton.setBounds(99, 52, 185, 71);
		add(openDoorButton);
		closeDoorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._CloseDoor_.getAid());
				closeDoorButton.setEnabled(false);
			}
		});
							
		closeDoorButton.setBounds(356, 52, 185, 71);
		add(closeDoorButton);
		
		
		platformDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._PlatformDown_.getAid());
				platformDownButton.setEnabled(false);
			}
		});
		platformDownButton.setBounds(99, 154, 185, 71);
		add(platformDownButton);
		
		
		platformUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._PlatformUp_.getAid());
				platformUpButton.setEnabled(false);
			}
		});
		platformUpButton.setBounds(356, 154, 185, 71);
		add(platformUpButton);
		
		movePositiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._MovePositive_.getAid());
				movePositiveButton.setEnabled(false);
			}
		});
		movePositiveButton.setBounds(99, 262, 156, 71);
		add(movePositiveButton);
		
		moveNegativeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._MoveNegative_.getAid());
				moveNegativeButton.setEnabled(false);
			}
		});
		moveNegativeButton.setBounds(265, 262, 152, 71);
		add(moveNegativeButton);
		
		stopMoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._StopMove_.getAid());
				stopMoveButton.setEnabled(false);
			}
		});		
		stopMoveButton.setBounds(427, 262, 114, 71);
		add(stopMoveButton);
		
		moveWheelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._MoveWheel_.getAid());
				moveWheelButton.setEnabled(false);
			}
		});
		moveWheelButton.setBounds(99, 366, 185, 71);
		add(moveWheelButton);
		
		stopWheelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._StopWheel_.getAid());
				stopWheelButton.setEnabled(false);
			}
		});
		stopWheelButton.setBounds(356, 366, 185, 71);
		add(stopWheelButton);
		
		openLightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._OpenLight_.getAid());
				openLightButton.setEnabled(false);
			}
		});		
		openLightButton.setBounds(99, 467, 185, 71);
		add(openLightButton);
		
		closeLightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final IMachineCommandSender sender = machineCommandSelector.select(ICommonConstants.MachineCommandEnum._MachineCommand_Debug_Command_);
				sender.send(DebugCommandEnum._CloseLight_.getAid());
				closeLightButton.setEnabled(false);
			}
		});		
		closeLightButton.setBounds(356, 467, 185, 71);
		add(closeLightButton);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MessageVO vo = new MessageVO();
				vo.setParam1(ICommonConstants.MainFrameActivePanelEnum._MainFrame_ActivePanel_Welcome_.getId());
				vo.setMessageSource(ICommonConstants.MessageSourceEnum._MessageSource_MainFrame_);
				vo.setSubMessageType(ICommonConstants.SubMessageTypeEnum._SubMessageType_MainFrame_Panel_);
				messageManager.push(vo);
			}
		});
		
		returnButton.setBounds(695, 467, 185, 71);
		add(returnButton);
	}
	
	@PostConstruct
	public void initialize() {
		messageManager.addListener(this);
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
		if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_OpenDoorButton_.equals(subMessageType)) {
			openDoorButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_CloseDoorButton_.equals(subMessageType)) {
			closeDoorButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_PlatformDownButton_.equals(subMessageType)) {
			platformDownButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_PlatformUpButton_.equals(subMessageType)) {
			platformUpButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_MovePositive_.equals(subMessageType)) {
			movePositiveButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_MoveNegative_.equals(subMessageType)) {
			moveNegativeButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_StopMove_.equals(subMessageType)) {
			stopMoveButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_MoveWheel_.equals(subMessageType)) {
			moveWheelButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_StopWheel_.equals(subMessageType)) {
			stopWheelButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_OpenLight_.equals(subMessageType)) {
			openLightButton.setEnabled(true);
		}
		else if (true == ICommonConstants.SubMessageTypeEnum._SubMessageType_AdminPanel_CloseLight_.equals(subMessageType)) {
			closeLightButton.setEnabled(true);
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
