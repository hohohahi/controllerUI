package com.bottle.hardware.rxtx.parser;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class QueryTemplateListCommandParser extends AbstractBaseBean implements ICommandParser {
	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_QueryTemplateList_;
	}

	@Override
	public RxTxResponseVO run(byte aid, byte[] dataArea) {
		RxTxResponseVO vo = new RxTxResponseVO();
		final ICommonConstants.MachineCommandEnum commandType = this.getCommandType();
		if (aid == commandType.getAid_Success()) {
			vo.setCommandType(commandType);
			vo.setIsSuccess(true);
			
			if (dataArea == null || dataArea.length == 0) {
				throw new RuntimeException("dataArea is null or empty. dataArea:" + dataArea);
			}
			
			if (dataArea.length != 1024) {
				throw new RuntimeException("dataArea length is not valid, it should be 1024, but it's real lengh:" + dataArea.length);
			}
			
			List<Long> paramList = new ArrayList<Long>(dataArea.length);
			for (byte element : dataArea) {
				paramList.add((long)element);				
			}
			
			vo.setParam2List(paramList);
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
