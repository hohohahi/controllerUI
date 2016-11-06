package com.bottle.hardware.rxtx.parser;

import org.springframework.stereotype.Service;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;

@Service
public class StopCommandParser extends AbstractBaseCommandParser implements ICommandParser {
	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_Stop_;
	}
}
