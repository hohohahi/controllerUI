package com.bottle.hardware.rxtx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.hardware.rxtx.parser.ICommandParser;
import com.bottle.hardware.rxtx.parser.IParserSelector;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class ByteDataParser extends AbstractBaseBean implements IByteDataParser {
	@Autowired
	private IParserSelector parserSelector;
	
	@Override
	public RxTxResponseVO parse(byte[] data) {		
		if (null == data) {
			throw new RuntimeException("data is null.");
		}
		
		int length = data.length;
		if (length < 6) {
			String dataStr = "";
			for (byte item : data) {
				dataStr += item + "--";
			}
			throw new RuntimeException("Invalid data: data:" + dataStr + "--length:" + length);
		}
		
		byte startByte = data[0];
		byte endByte = data[length - 1];
		if ((ICommonConstants._SerialComm_Data_StartByte_ != startByte)
				|| (ICommonConstants._SerialComm_Data_EndByte_ != endByte)) {
			throw new RuntimeException("Invalid start byte or end byte. startByte:" + startByte + "--endByte:" + endByte);
		}
		
		byte pid = data[1];
		byte aid = data[2];
		byte [] content = new byte[length - 4]; 
		System.arraycopy(data, 3, content, 0, length - 4);
		byte [] dataArea = validateDataLengthAndGetData(content);
		
		ICommandParser parser = parserSelector.select(pid);
		RxTxResponseVO vo = parser.run(aid, dataArea);
		
		return vo;
	}
	
	public byte [] validateDataLengthAndGetData(final byte [] content) {
		if (null == content) {
			throw new NullPointerException("content is null.");
		}
		
		int length = content.length;
		if (length < 2) {
			throw new RuntimeException("the data length area is not two bits long.");
		}
		byte lowByte = content[0];
		byte highByte = content[1];
		int actualLength = ((int)highByte * 16) + (int)lowByte;
		
		if (actualLength != (length - 2)) {
			throw new RuntimeException("invoid length. content:" + content + "--actualLength:" + actualLength + "--ideal length:" + (length-2));
		}
		
		byte [] rtnBytes = new byte[actualLength];
		if (actualLength != 0) {
			System.arraycopy(content, 2, rtnBytes, 0, actualLength);
		}		
		
		return rtnBytes;
	}
	
	public void getCommandTypeByPIDAndAID(final byte pid, final byte aid, final RxTxResponseVO vo, byte [] dataArea) {
		
		if (pid == 0x20 && aid == 0x10) {
			vo.setCommandType(ICommonConstants.MachineCommandEnum._MachineCommand_ReturnResult_);
			if (null == dataArea || dataArea.length == 0){
				throw new RuntimeException("return result command.data area is null or empty");
			}
			
			int length = dataArea.length;
			byte firstElement = dataArea[0];
			if (firstElement != 0x00) {
				if (length > 1) {
					throw new RuntimeException("return result command. first element is not zero, but length is more than 1.");
				}
				
				vo.setIsSuccess(false);
				vo.setErrorCode((long)firstElement);
			}
			else {
				if (length == 1) {
					throw new RuntimeException("return result command. first element is zero, but length is 1.");
				}
				else {
					vo.setIsSuccess(true);
					int actualLength = length - 1;
					byte [] actualData = new byte[actualLength];
					System.arraycopy(dataArea, 1, actualData, 0, actualLength);
					
					String response = super.dataTypeHelper.convert_byte_String(actualData);
					vo.setResponse(response);
				}
			}

		}
		else if (pid == 0x30 && aid == 0x11) {
			vo.setCommandType(ICommonConstants.MachineCommandEnum._MachineCommand_DownloadTemplate_);
			vo.setIsSuccess(true);
		}
		else if (pid == 0x30 && aid == 0x12) {
			vo.setCommandType(ICommonConstants.MachineCommandEnum._MachineCommand_DownloadTemplate_);
			vo.setIsSuccess(false);
			
			if (null == dataArea || dataArea.length == 0){
				throw new RuntimeException("downlad template command.data area is null or empty. pid:" + pid + "--aid:" + aid);
			}
			
			int length = dataArea.length;
			byte firstElement = dataArea[0];
			
			if (length != 1) {
				throw new RuntimeException("downlad template command. length is more than 1.");
			}
			
			if (firstElement == 0x00) {
				throw new RuntimeException("downlad template command. first element can not be zero.");				
			}
			else {
				vo.setErrorCode((long)firstElement);
			}
		}
		else if (pid == 0x50 && aid == 0x11) {
			vo.setCommandType(ICommonConstants.MachineCommandEnum._MachineCommand_Ping_);
			vo.setIsSuccess(true);
		}
	}
	
}
