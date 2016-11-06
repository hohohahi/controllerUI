package com.bottle.hardware.rxtx.parser;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

public interface ICommandParser {
	ICommonConstants.MachineCommandEnum getCommandType();
	RxTxResponseVO run(byte aid, byte [] dataArea);
}
