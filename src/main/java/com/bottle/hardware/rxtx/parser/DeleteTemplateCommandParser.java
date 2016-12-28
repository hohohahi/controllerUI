package com.bottle.hardware.rxtx.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.data.service.IProductionDataManager;
import com.bottle.business.template.service.ITemplateParser;
import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class DeleteTemplateCommandParser extends AbstractBaseBean implements ICommandParser {
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_DeleteTemplate_;
	}

	@Override
	public RxTxResponseVO run(byte aid, byte[] dataArea) {
		RxTxResponseVO vo = new RxTxResponseVO();
		final ICommonConstants.MachineCommandEnum commandType = this.getCommandType();
		if (aid == commandType.getAid_Success()) {
			vo.setCommandType(commandType);
			vo.setIsSuccess(true);
		}
		else if (aid == commandType.getAid_Failure()) {
			vo.setCommandType(commandType);
			vo.setIsSuccess(false);
			
			if (null == dataArea){
				throw new NullPointerException("dataArea is null.");
			}
			
			if (dataArea.length != 1) {
				throw new RuntimeException("dataArea length is invalid. length:" + dataArea.length);
			}
			
			long errorCode = (long)dataArea[0];
			vo.setErrorCode(errorCode);
			if (errorCode == 1) {
				vo.setErrorMessage("Error when reading bar code.");
			}
			else {
				vo.setErrorMessage("Unknow error.");
			}
		}
		else {
			throw new RuntimeException("LearnTemplateCommandParser: not supported aid. aid:" + aid);
		}
		
		return vo;
	}
}
