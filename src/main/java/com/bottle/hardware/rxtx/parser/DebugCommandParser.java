package com.bottle.hardware.rxtx.parser;

import org.springframework.stereotype.Service;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;

@Service
public class DebugCommandParser extends AbstractBaseDebugCommandParser {

	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_Debug_Command_;
	}
}
