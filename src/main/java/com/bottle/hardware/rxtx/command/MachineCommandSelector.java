package com.bottle.hardware.rxtx.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;

@Service
public class MachineCommandSelector extends AbstractBaseBean implements ICommandSelector {
	@Autowired
	private List<IMachineCommandSender> commandSenderList;
	
	@Override
	public IMachineCommandSender select(MachineCommandEnum commandType) {
		if (null == commandSenderList) {
			throw new NullPointerException("commandSenderList is null.");
		}
		
		IMachineCommandSender rtnSender = null;
		for (IMachineCommandSender sender : commandSenderList) {
			final MachineCommandEnum curCommandType = sender.getCommandType();
			if (true == commandType.equals(curCommandType)) {
				rtnSender = sender;
				break;
			}
		}
		
		if (null == rtnSender) {
			throw new NullPointerException("rtnSender is null.");
		}
		
		return rtnSender;
	}

}
