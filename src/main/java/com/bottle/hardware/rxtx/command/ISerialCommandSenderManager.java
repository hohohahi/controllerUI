package com.bottle.hardware.rxtx.command;

import com.bottle.common.constants.ICommonConstants;

public interface ISerialCommandSenderManager {
	public boolean isSenderAlreadyInListenerList(final IMachineCommandSender commandSender);
	public void addCommandResponseListener(IMachineCommandSender commandSender);
	public IMachineCommandSender getCommandListenerAndRemoveIt(ICommonConstants.MachineCommandEnum commandType);
}
