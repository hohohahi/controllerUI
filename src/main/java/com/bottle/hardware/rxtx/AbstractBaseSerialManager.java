package com.bottle.hardware.rxtx;

import org.springframework.beans.factory.annotation.Autowired;

import com.bottle.business.common.service.IOperationRecorder;
import com.bottle.business.common.vo.SerialCommandOperationVO;
import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.hardware.rxtx.command.IMachineCommandSender;
import com.bottle.hardware.rxtx.command.ISerialCommandSenderManager;

public abstract class AbstractBaseSerialManager extends AbstractBaseBean implements IMachineCommandSender {
	protected byte aid = 0x00;
	protected TemplateVO template = new TemplateVO();
	
	@Autowired
	private ISerialCommConnector connector;
	
	@Autowired
	private ISerialCommandSenderManager senderManager;
	
	@Autowired
	protected IOperationRecorder operationRecorder;
	
	private long timestamp_AddedToQueue = 0L;
	
	abstract public ICommonConstants.MachineCommandEnum getCommandType();

	@Override
	final public void setTemplate(final TemplateVO template) {
		this.template = template;
	}
	
	@Override
	public void initialize() {
		super.initialize();
	}
	
	@Override
	public void send(byte aid) {
		//
	}
	
	@Override
	public void send(TemplateVO template) {
		//
	}
	
	final public void resetAddedIntoQueueTimestamp() {
		timestamp_AddedToQueue = System.currentTimeMillis();
	}
	
	protected String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	
	@Override
	final public boolean isTimeout() {
		boolean isTimeout = false;
		final long currentTimestamp = System.currentTimeMillis();		
		final long threshold = super.configurationManager.getConfigurationVO().getSerialCommandTimeout_InMilliSecond();
		final long elapseTime_InMilliSecond = currentTimestamp - timestamp_AddedToQueue;
		if (elapseTime_InMilliSecond > threshold) {
			isTimeout = true;
		}
		else {
			isTimeout = false;
		}

		return isTimeout;
	}
	
	@Override
	public byte getAid() {
		return this.aid;
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
		
		sendBytes(startCommand);
		
		final SerialCommandOperationVO operationVO = new SerialCommandOperationVO();
        operationVO.setType(ICommonConstants.OperationTypeEnum._Operation_Type_SerialCommand_);
        operationVO.setDirection(ICommonConstants.SerialCommandOperationDirectionEnum._Operation_SerialCommand_Direction_Down_);
        operationVO.setTimestampStr(super.dateConverter.getCurrentTimestampInNineteenBitsInGMT());
        operationVO.setIsSuccess(false);
        operationVO.setPid(pid);
        operationVO.setAid(aid);
        operationVO.setData(new byte[]{data1, data2});
        
        operationRecorder.log(operationVO);
	}
	
	final public void sendBytes(byte [] byteArray) {
		if (null == byteArray) {
			throw new NullPointerException("byteArray is null.");
		}		
		
        if (true == senderManager.isSenderAlreadyInListenerList(this)) {
        	throw new RuntimeException("commandSender is already in listener status. commandType:" + getCommandType());
        }
        
		try {
			int dataLength = byteArray.length;
			byte [] fullByteArray = new byte[dataLength + 2];
			fullByteArray[0] = ICommonConstants._SerialComm_Data_StartByte_;
			
			for (int index = 0; index < dataLength; index ++) {
				fullByteArray[index+1] = byteArray[index];
			}
			
			fullByteArray[dataLength+1] = ICommonConstants._SerialComm_Data_EndByte_;
			
			
			
			connector.send(fullByteArray, this);			
			resetAddedIntoQueueTimestamp();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		
        senderManager.addCommandResponseListener(this);
	}
}
