package com.bottle.hardware.rxtx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;
import com.bottle.hardware.rxtx.parser.ICommandParser;
import com.bottle.hardware.rxtx.parser.IParserSelector;
import com.bottle.hardware.rxtx.vo.ByteArrayParseResultVO;
import com.bottle.hardware.rxtx.vo.RxTxResponseVO;

@Service
public class ByteDataParser extends AbstractBaseBean implements IByteDataParser {
	@Autowired
	private IParserSelector parserSelector;
	
	@Override
	public RxTxResponseVO parse(byte[] srcByteArray) {				
		final ByteArrayParseResultVO byteResult = extractValidByteArrayFromSrc(srcByteArray);
		ICommandParser parser = parserSelector.select(byteResult.getPid());
		RxTxResponseVO vo = parser.run(byteResult.getAid(), byteResult.getDataArray());
		vo.setByteParseResultVO(byteResult);
		
		return vo;
	}
	
	public void validateInputByteArrayLength(final int length) {
		if (length < 6) {			
			throw new RuntimeException("Invalid data: datal length is invalid length:" + length);
		}
	}
	
	//srcByteArray -> content  -> data
	public ByteArrayParseResultVO extractValidByteArrayFromSrc(byte [] srcByteArray) {
		super.validateObject(srcByteArray);
		
		int length = srcByteArray.length;
		validateInputByteArrayLength(length);
		
		int validNum = 0;
		final ByteArrayParseResultVO resultVO = new ByteArrayParseResultVO();
		for (int startPos = 0; startPos < length; startPos++) {
			final byte currentStartByte = srcByteArray[startPos];
			if (ICommonConstants._SerialComm_Data_StartByte_ != currentStartByte) {
				continue;
			}
			
			for (int endPos = startPos; endPos < length; endPos++) {
				final byte currentEndByte = srcByteArray[endPos];
				if (ICommonConstants._SerialComm_Data_EndByte_ != currentEndByte) {
					continue;
				}

				final int validDataLength = endPos - startPos + 1 - 4;
				
				if (validDataLength < 0) {
					continue;
				}
				
				byte [] content = new byte[validDataLength];
				System.arraycopy(srcByteArray, startPos+3, content, 0, validDataLength);
				if (true == isContentLengthValid(content)) {
					byte [] rtnBytes = new byte[validDataLength-2];
					if (validDataLength-2 != 0) {
						System.arraycopy(content, 2, rtnBytes, 0, validDataLength-2);
					}		
					validNum++;
					resultVO.setDataLength(validDataLength);
					resultVO.setDataArray(rtnBytes);
					resultVO.setContentArray(content);
					resultVO.setPid(srcByteArray[startPos+1]);
					resultVO.setAid(srcByteArray[startPos+2]);
				}
				else {
					System.out.println("not valid. startPos:" + startPos + "--endPos:" + endPos);
				}
			}
		}
		
		if (validNum != 1) {
			String extraMessage = "";
			for (byte ele : srcByteArray) {
				extraMessage += ele + "--";
			}
			throw new RuntimeException("error in extract data from input byte array. validNum:" + validNum + "--length:" + length + "--data:" + extraMessage);
		}
		
		return resultVO;
	}
	
	public boolean isContentLengthValid(final byte [] content) {
		super.validateObject(content);
		int length = content.length;
		
		if (length < 2) {
			throw new RuntimeException("length of content is invalid. length:" + length);
		}
		
		byte lowByte = content[0];
		byte highByte = content[1];
		
		int actualLength = ((int)highByte * 256) + (int)lowByte;
		
		boolean isValid = false;
		if (actualLength != (length - 2)) {
			isValid = false;
		}
		else {
			isValid = true;
		}
		
		return isValid;
	}

	public static void main(String [] args) {
		ByteDataParser parse = new ByteDataParser();
		byte [] inputSrcByteArray = new byte[]{0x56, 0x68, 0x68, 0x10, 0x11, 0x01, 0x00, 0x01, 0x16, 0x16};
		final ByteArrayParseResultVO vo = parse.extractValidByteArrayFromSrc(inputSrcByteArray);
		System.out.println(vo);
	}
}
