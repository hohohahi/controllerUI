package com.bottle.hardware.rxtx.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.DebugCommandEnum;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.hardware.rxtx.AbstractBaseSerialManager;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class DebugCommandSender extends AbstractBaseSerialManager {
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public void onReceive(final RxTxResponseVO responseVO) {
		if (null == responseVO) {
			throw new NullPointerException("responseVO is null.");
		}
		
		final MessageVO vo = new MessageVO();
		
		vo.setMessageSource(MessageSourceEnum._MessageSource_AdminPanel_);		
		vo.setParam1(responseVO.getErrorCode());
		final byte aid = (byte)responseVO.getParam1();
		
		if (DebugCommandEnum._OpenDoor_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_OpenDoorButton_);
		}
		else if (DebugCommandEnum._CloseDoor_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_CloseDoorButton_);
		}
		else if (DebugCommandEnum._PlatformDown_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_PlatformDownButton_);
		}
		else if (DebugCommandEnum._PlatformUp_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_PlatformUpButton_);
		}
		else if (DebugCommandEnum._MovePositive_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_MovePositive_);
		}
		else if (DebugCommandEnum._MoveNegative_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_MoveNegative_);
		}
		else if (DebugCommandEnum._StopMove_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_StopMove_);
		}
		else if (DebugCommandEnum._MoveWheel_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_MoveWheel_);
		}
		else if (DebugCommandEnum._StopWheel_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_StopWheel_);
		}
		else if (DebugCommandEnum._OpenLight_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_OpenLight_);
		}
		else if (DebugCommandEnum._CloseLight_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_CloseLight_);
		}
		
		messageManager.push(vo);
	}

	@Override
	public MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_Debug_Command_;
	}
	
	@Override
	public void send(byte aid) {
		final MachineCommandEnum commandType = getCommandType();
		byte [] startCommand = new byte[4];
		byte pid = commandType.getPid();		
		byte data1 = ICommonConstants._Zero_Byte_;
		byte data2 = ICommonConstants._Zero_Byte_;
		startCommand[0] = pid;
		startCommand[1] = aid;
		startCommand[2] = data1;
		startCommand[3] = data2;
		
		sendBytes(startCommand, this);
	}
}
