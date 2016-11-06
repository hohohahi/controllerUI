package com.bottle.hardware.rxtx.parser;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

public abstract class AbstractBaseCommandParser extends AbstractBaseBean implements ICommandParser{
	public abstract ICommonConstants.MachineCommandEnum getCommandType();
	
	public RxTxResponseVO run(byte aid, byte[] dataArea) {
		RxTxResponseVO vo = new RxTxResponseVO();
		
		ICommonConstants.MachineCommandEnum commandType = getCommandType();
		if (aid == commandType.getAid_Success()) {
			vo.setCommandType(commandType);
			vo.setIsSuccess(true);
		}
		else if (aid == commandType.getAid_Failure()) {
			vo.setCommandType(commandType);
			vo.setIsSuccess(false);
		}
		
		return vo;
	}
}
