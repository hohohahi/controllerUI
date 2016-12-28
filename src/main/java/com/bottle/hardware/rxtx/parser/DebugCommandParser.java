package com.bottle.hardware.rxtx.parser;

import org.springframework.stereotype.Service;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class DebugCommandParser extends AbstractBaseDebugCommandParser {

	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_Debug_Command_;
	}
	
	public RxTxResponseVO run(byte aid, byte[] dataArea) {
		super.validateObject(dataArea);
		RxTxResponseVO vo = super.run(aid, dataArea);
		
		final int length = dataArea.length;
		if (1 != length) {
			throw new RuntimeException("AbstractBaseDebugCommandParser::run: length of dataArea is not 1.");
		}
		
		byte element = dataArea[0];
		vo.setErrorCode((long)element);
		vo.setParam1((long)aid);
		vo.setCommandType(getCommandType());
		return vo;
	}
}
