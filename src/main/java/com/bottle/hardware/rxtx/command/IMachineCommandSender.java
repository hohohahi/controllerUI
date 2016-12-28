package com.bottle.hardware.rxtx.command;

import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

public interface IMachineCommandSender {
	ICommonConstants.MachineCommandEnum getCommandType();
	void send();
	void send(byte aid);
	void send(TemplateVO template);
	void onReceive(RxTxResponseVO responseVO);
	void resetAddedIntoQueueTimestamp();
	boolean isTimeout();
	byte getAid();
	void setTemplate(final TemplateVO template);
}
