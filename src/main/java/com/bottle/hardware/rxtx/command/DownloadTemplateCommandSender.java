package com.bottle.hardware.rxtx.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.business.common.service.IMessageQueueManager;
import com.bottle.business.common.vo.MessageVO;
import com.bottle.business.common.vo.SerialCommandOperationVO;
import com.bottle.business.template.service.ITemplateParser;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.common.constants.ICommonConstants.MachineCommandEnum;
import com.bottle.common.constants.ICommonConstants.MessageSourceEnum;
import com.bottle.common.constants.ICommonConstants.SubMessageTypeEnum;
import com.bottle.common.constants.ILanguageConstants;
import com.bottle.hardware.rxtx.AbstractBaseSerialManager;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class DownloadTemplateCommandSender extends AbstractBaseSerialManager {
	@Autowired
	private IMessageQueueManager messageManager;
	
	@Autowired
	private ITemplateParser templateParser;
	
	@Override
	public void onReceive(final RxTxResponseVO responseVO) {
		if (null == responseVO) {
			throw new NullPointerException("responseVO is null.");
		}
		
		final MessageVO vo = new MessageVO();
		
		vo.setMessageSource(MessageSourceEnum._MessageSource_AdminPanel_Tab_TemplateManagementPanel_);
		vo.setSubMessageType(SubMessageTypeEnum._SubMessageType_AdminPanel_Tab_TemplateManagementPanel_DownloadTemplateButton_);
		vo.setBooleanParam3(responseVO.getIsSuccess());
		messageManager.push(vo);
		
		final MessageVO systemInfoMessage = new MessageVO();		
		systemInfoMessage.setMessageSource(MessageSourceEnum._MessageSource_MainFrame_);
		systemInfoMessage.setSubMessageType(SubMessageTypeEnum._SubMessageType_MainFrame_SystemInfoLog_);
		
		String message = "";
		if (false == responseVO.getIsSuccess()) {
			message = ILanguageConstants._TemplateManagementPanel_DownloadTemplate_Failed_ + "--error code:" + responseVO.getErrorCode();
			systemInfoMessage.setBooleanParam3(false);
			
			systemInfoMessage.setMessage(message);
			messageManager.push(systemInfoMessage);
		}		
	}

	@Override
	public MachineCommandEnum getCommandType() {
		return MachineCommandEnum._MachineCommand_DownloadTemplate_;
	}
	
	@Override
	public void send() {
		final MachineCommandEnum commandType = getCommandType();
		byte [] commandBytes = new byte[4];
		byte pid = commandType.getPid();
		byte aid = commandType.getAid();
		byte data1 = ICommonConstants._Zero_Byte_;
		byte data2 = ICommonConstants._Zero_Byte_;
		commandBytes[0] = pid;
		commandBytes[1] = aid;
		commandBytes[2] = data1;
		commandBytes[3] = data2;
		
		//sendBytes(commandBytes);
		
		byte [] dataArea = templateParser.getByteArrayFromTemplate(template);		
		final int dataLength = dataArea.length;
		final byte [] lengthArray = templateParser.getLowBitAndHighBitFromNumber(dataLength);
		super.validateObject(dataArea);
		byte [] toBeSentByteArray = new byte [dataLength+4];
		toBeSentByteArray[0] = pid;
		toBeSentByteArray[1] = aid;
		toBeSentByteArray[2] = lengthArray[0];
		toBeSentByteArray[3] = lengthArray[1];
		System.arraycopy(dataArea, 0, toBeSentByteArray, 4, dataLength);
		
		final StringBuilder buf = new StringBuilder();
		buf.append("DownloadTemplateCommandSender::send: to be send data. length:" + toBeSentByteArray.length + "--content:");
		for (byte element : toBeSentByteArray) {
			byte [] toBeConvertedArray = new byte[1];
			toBeConvertedArray[0] = element;

			buf.append(bytesToHexString(toBeConvertedArray)).append("--");
		}
		System.out.println(buf.toString());
		sendBytes(toBeSentByteArray);
		
		 final SerialCommandOperationVO operationVO = new SerialCommandOperationVO();
         operationVO.setType(ICommonConstants.OperationTypeEnum._Operation_Type_SerialCommand_);
         operationVO.setDirection(ICommonConstants.SerialCommandOperationDirectionEnum._Operation_SerialCommand_Direction_Down_);
         operationVO.setTimestampStr(super.dateConverter.getCurrentTimestampInNineteenBitsInGMT());
         operationVO.setIsSuccess(false);
         operationVO.setPid(pid);
         operationVO.setAid(aid);
         
         byte [] contentArray = new byte [dataLength+2];
         System.arraycopy(contentArray, 0, toBeSentByteArray, 2, dataLength+2);
         operationVO.setData(contentArray);
         
         operationRecorder.log(operationVO);
	}
}
