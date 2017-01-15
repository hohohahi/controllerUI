package com.bottle.hardware.rxtx.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class ReturnThrowBottleActionDetectedCommandParser extends AbstractBaseCommandParser {
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_ReturnStatus_ThrowBottleActionDetected_;
	}
	
	@Override
	public RxTxResponseVO run(byte aid, byte[] dataArea) {
		super.validateObject(dataArea);
		RxTxResponseVO vo = super.run(aid, dataArea);
		
		final MessageVO message = new MessageVO();
		
		message.setMessageSource(MessageSourceEnum._MessageSource_PlayerPanel_);
		message.setSubMessageType(SubMessageTypeEnum._SubMessageType_PlayerPanel_ThrowBottleActionDetected_);
		
		messageManager.push(message);
		
		return vo;
	}
}
