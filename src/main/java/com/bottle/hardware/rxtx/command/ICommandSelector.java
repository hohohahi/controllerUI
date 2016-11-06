package com.bottle.hardware.rxtx.command;

import com.bottle.common.constants.ICommonConstants;

public interface ICommandSelector {
	IMachineCommandSender select(ICommonConstants.MachineCommandEnum commandType);
}
