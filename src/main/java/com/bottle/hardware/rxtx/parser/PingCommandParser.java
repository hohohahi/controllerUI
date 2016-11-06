package com.bottle.hardware.rxtx.parser;

import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class PingCommandParser extends AbstractBaseBean implements ICommandParser {

	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_Ping_;
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
		}
		else {
			throw new RuntimeException("PingCommandParser: not supported aid. aid:" + aid);
		}
		
		return vo;
	}
}
