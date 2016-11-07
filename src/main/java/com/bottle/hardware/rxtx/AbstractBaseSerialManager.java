package com.bottle.hardware.rxtx;

import org.springframework.beans.factory.annotation.Autowired;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;

public abstract class AbstractBaseSerialManager extends AbstractBaseBean implements IMachineCommandSender {
	@Autowired
	private ISerialCommConnector connector;
	
	@Override
	public void initialize() {
		super.initialize();
	}
	
	@Override
	public void send(byte aid) {
		//
	}
	
	public void send() {
		final MachineCommandEnum commandType = getCommandType();
		byte [] startCommand = new byte[4];
		byte pid = commandType.getPid();
		byte aid = commandType.getAid();
		byte data1 = ICommonConstants._Zero_Byte_;
		byte data2 = ICommonConstants._Zero_Byte_;
		startCommand[0] = pid;
		startCommand[1] = aid;
		startCommand[2] = data1;
		startCommand[3] = data2;
		
		sendBytes(startCommand, this);
	}
	
	final public void sendBytes(byte [] byteArray, final IMachineCommandSender commandSender) {
		if (null == byteArray) {
			throw new NullPointerException("byteArray is null.");
		}		
		
		try {
			int dataLength = byteArray.length;
			byte [] fullByteArray = new byte[dataLength + 2];
			fullByteArray[0] = ICommonConstants._SerialComm_Data_StartByte_;
			
			for (int index = 0; index < dataLength; index ++) {
				fullByteArray[index+1] = byteArray[index];
			}
			
			fullByteArray[dataLength+1] = ICommonConstants._SerialComm_Data_EndByte_;
			
			connector.send(fullByteArray, commandSender);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
}
