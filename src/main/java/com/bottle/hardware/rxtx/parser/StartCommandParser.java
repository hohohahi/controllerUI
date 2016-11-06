package com.bottle.hardware.rxtx.parser;

import org.springframework.stereotype.Service;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;

@Service
public class StartCommandParser extends AbstractBaseCommandParser {

	@Override
	public ICommonConstants.MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_Start_;
	}
}
