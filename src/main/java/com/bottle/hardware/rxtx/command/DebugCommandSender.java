package com.bottle.hardware.rxtx.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.service.IOperationRecorder;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.common.vo.SerialCommandOperationVO;
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
		System.out.println("Debug reponse received: responseVO:" + responseVO);
		if (null == responseVO) {
			throw new NullPointerException("responseVO is null.");
		}
		
		final MessageVO vo = new MessageVO();
		
		vo.setMessageSource(MessageSourceEnum._MessageSource_AdminPanel_Tab_DebugCommandPanel_);		
		vo.setParam1(responseVO.getErrorCode());
		final byte aid = (byte)responseVO.getParam1();
		
		if (DebugCommandEnum._OpenDoor_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_OpenDoorButton_);
		}
		else if (DebugCommandEnum._CloseDoor_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_CloseDoorButton_);
		}
		else if (DebugCommandEnum._PlatformDown_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_PlatformDownButton_);
		}
		else if (DebugCommandEnum._PlatformUp_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_PlatformUpButton_);
		}
		else if (DebugCommandEnum._MovePositive_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_MovePositive_);
		}
		else if (DebugCommandEnum._MoveNegative_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_MoveNegative_);
		}
		else if (DebugCommandEnum._StopMove_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_StopMove_);
		}
		else if (DebugCommandEnum._MoveWheel_.getAid_Return() == aid) {
			vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_DebugCommandPanel_MoveWheel_);
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
		this.aid = aid;
		
		final MachineCommandEnum commandType = getCommandType();
		byte [] startCommand = new byte[4];
		byte pid = commandType.getPid();		
		byte data1 = ICommonConstants._Zero_Byte_;
		byte data2 = ICommonConstants._Zero_Byte_;
		startCommand[0] = pid;
		startCommand[1] = aid;
		startCommand[2] = data1;
		startCommand[3] = data2;
		
		sendBytes(startCommand);
		
		 final SerialCommandOperationVO operationVO = new SerialCommandOperationVO();
         operationVO.setType(ICommonConstants.OperationTypeEnum._Operation_Type_SerialCommand_);
         operationVO.setDirection(ICommonConstants.SerialCommandOperationDirectionEnum._Operation_SerialCommand_Direction_Down_);
         operationVO.setTimestampStr(super.dateConverter.getCurrentTimestampInNineteenBitsInGMT());
         operationVO.setIsSuccess(false);
         operationVO.setPid(pid);
         operationVO.setAid(aid);
         operationVO.setData(new byte[]{data1, data2});
         
         operationRecorder.log(operationVO);
	}
}
