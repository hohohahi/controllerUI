package com.bottle.hardware.rxtx.command;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

public interface IMachineCommandSender {
	ICommonConstants.MachineCommandEnum getCommandType();
	void send();
	void onReceive(RxTxResponseVO responseVO);
}
