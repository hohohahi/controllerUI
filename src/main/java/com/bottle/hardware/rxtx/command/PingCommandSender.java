package com.bottle.hardware.rxtx.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.hardware.rxtx.AbstractBaseSerialManager;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class PingCommandSender extends AbstractBaseSerialManager implements IMachineCommandSender {
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public void onReceive(final RxTxResponseVO responseVO) {
		if (null == responseVO) {
			throw new NullPointerException("responseVO is null.");
		}
		
		final MessageVO vo = new MessageVO();
		vo.setMessageSource(MessageSourceEnum._MessageSource_PingService_);
		
		messageManager.push(vo);
	}

	@Override
	public void send() {
		byte [] startCommand = new byte[4];
		MachineCommandEnum commandType = getCommandType();
		byte pid = commandType.getPid();
		byte aid = commandType.getAid();
		byte data1 = ICommonConstants._Zero_Byte_;
		byte data2 = ICommonConstants._Zero_Byte_;
		startCommand[0] = pid;
		startCommand[1] = aid;
		startCommand[2] = data1;
		startCommand[3] = data2;
		
		super.sendBytes(startCommand, this);
	}

	@Override
	public MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_Ping_;
	}
}
