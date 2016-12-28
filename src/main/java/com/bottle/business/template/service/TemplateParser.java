package com.bottle.business.template.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bottle.business.common.vo.PositionInfoVO;
import com.bottle.business.template.exception.DataLengthNotEqualActualLengthException;
import com.bottle.business.template.exception.NotSupportIsMetalTypeException;
import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.AbstractBaseBean;
import com.bottle.common.constants.ICommonConstants;

@Service
public class TemplateParser extends AbstractBaseBean implements ITemplateParser {
	public long getLongFromLowByteAndHighByte(final byte lowByte, final byte highByte) {
		return (long)((getLongFromByte(highByte) * 256) + getLongFromByte(lowByte));
	}

	public long getLongFromByte(byte value) {
		return (long)(value&0xff);
	}
	
	@Override
	public byte[] getByteArrayFromTemplate(final TemplateVO template) {
		super.validateObject(template);
		
		//0. initial buffer
		final int originalSize = 1024*1024;
		int realBytesNum = 0;
		final byte [] originalByteArray = new byte[originalSize];
		for (int i = 0; i < originalSize;  i++) {
			originalByteArray[i] = ICommonConstants._Zero_Byte_;
		}
		
		//id 0-1
		final long id = template.getId();
		final byte [] idByteArray = this.getLowBitAndHighBitFromNumber(id);
		originalByteArray[0] = idByteArray[0];
		originalByteArray[1] = idByteArray[1];
		realBytesNum += 2;
		
		//barcode 2-14
		final String barCode = template.getBarCode();
		validateBarCodeLength(barCode);
		final byte [] barCodeBytesArray = super.dataTypeHelper.convert_String_byteArray(barCode);
		System.arraycopy(barCodeBytesArray, 0, originalByteArray, 2, barCodeBytesArray.length);
		realBytesNum += barCodeBytesArray.length;
		
		//isMetal 15
		final long isMetal = template.getIsMetal();
		originalByteArray[realBytesNum] = (byte)isMetal;
		realBytesNum += 1;
		
		//weight 16-17
		final long weight = template.getWeight();
		byte [] weightByteArray = this.getLowBitAndHighBitFromNumber(weight);
		originalByteArray[realBytesNum] = weightByteArray[0];
		originalByteArray[realBytesNum+1] = weightByteArray[1];
		realBytesNum += 2;
		
		//image
		final long posNum = template.getPosNum();
		final List<PositionInfoVO> positionList = template.getPositionInfoList();
		byte [] imageArray = getImageByteArrayFromPositionList(positionList);
		validatePosionInfo(posNum, positionList, imageArray);
		final byte [] posNumArray = this.getLowBitAndHighBitFromNumber(posNum);
		originalByteArray[realBytesNum] = posNumArray[0];
		originalByteArray[realBytesNum+1] = posNumArray[1];
		realBytesNum += 2;
		
		final byte [] fixedImageArray = getFixedImageArrayFromValidImageArray(imageArray);
		System.arraycopy(fixedImageArray, 0, originalByteArray, realBytesNum, fixedImageArray.length);
		realBytesNum += fixedImageArray.length;
		
		byte [] rtnArray = new byte[realBytesNum];
		System.arraycopy(originalByteArray, 0, rtnArray, 0, realBytesNum);
		
		return rtnArray;
	}
	
	public byte [] getFixedImageArrayFromValidImageArray(final byte [] validImageArray) {
		super.validateObject(validImageArray);
		final int validLength = validImageArray.length;
		if (validLength > 1024*4) {
			throw new RuntimeException("getFixedImageArrayFromValidImageArray: validImageArray excceeds the max(fixed) length. valid length:" + validLength 
											+ "--fixed length:" + 1024);
		}
		byte [] rtnFixedImageArray = new byte[1024*4];
		
		final int fixedLength = rtnFixedImageArray.length;
		for (int i = 0; i < fixedLength;  i++) {
			rtnFixedImageArray[i] = ICommonConstants._Zero_Byte_;
		}
		
		System.arraycopy(validImageArray, 0, rtnFixedImageArray, 0, validLength);
		return rtnFixedImageArray;
	}
	public void validatePosionInfo(long posNum, final List<PositionInfoVO> positionList, byte [] imageArray) {
		super.validateObject(positionList);
		super.validateObject(imageArray);
		
		final long listSize = positionList.size();
		final long arraySize = imageArray.length/4;
		
		if (posNum != listSize) {
			throw new RuntimeException("validatePosionInfo: posNum is not equal listSize. posNum:" + posNum + "--listSize:" + listSize);
		}
		
		if (posNum != arraySize) {
			throw new RuntimeException("validatePosionInfo: posNum is not equal arraySize. posNum:" + posNum + "--arraySize*4:" + arraySize);
		}
	}
	
	public byte [] getImageByteArrayFromPositionList(final List<PositionInfoVO> positionList) {
		super.validateObject(positionList);
		
		final int size = positionList.size();
		byte [] rtnArray = new byte [size*4];
		for (int i = 0; i < size; i++) {
			final PositionInfoVO vo = positionList.get(i);
			super.validateObject(vo);
			final long xPos = vo.getX();
			final byte [] xPosByteArray = this.getLowBitAndHighBitFromNumber(xPos);
			final long yPos = vo.getY();
			final byte [] yPosByteArray = this.getLowBitAndHighBitFromNumber(yPos);
			rtnArray[i*4 + 0] = xPosByteArray[0];
			rtnArray[i*4 + 1] = xPosByteArray[1];
			rtnArray[i*4 + 2] = yPosByteArray[0];
			rtnArray[i*4 + 3] = yPosByteArray[1];
		}
		
		return rtnArray;
	}
	
	public void validateBarCodeLength(final String barCode) {
		if (true == StringUtils.isEmpty(barCode)) {
			throw new RuntimeException("barCode is empty. barCode:" + barCode);
		}
		
		if (barCode.length() != 13) {
			throw new RuntimeException("invalid barCode length. barCode:" + barCode);
		}
	}
	
	public byte [] getLowBitAndHighBitFromNumber(long number) {
		long lowBit_Long = (number)&0xff;
		byte lowBit = (byte)lowBit_Long;
		long highBit_Long = ((number)&0xff00)>>8;
		byte highBit = (byte)highBit_Long;
		byte [] rtnArray = new byte[2];
		rtnArray[0] = lowBit;
		rtnArray[1] = highBit;
		
		return rtnArray;
	}
	
	public static void main(String [] args) {
		
		TemplateParser parse = new TemplateParser();
		byte [] rtnByteArray = parse.getLowBitAndHighBitFromNumber(1000);
		long rtnLong = parse.getLongFromLowByteAndHighByte(rtnByteArray[0], rtnByteArray[1]);
		System.out.println(rtnLong);
	}
	
	@Override
	public TemplateVO getTemplateFromByteArray(byte[] dataArea) {
		super.validateObject(dataArea);
		int length = dataArea.length;
		final TemplateVO templateVO = new TemplateVO();
		
		if (length < 20) {
			throw new RuntimeException("TemplateParser::getTemplateFromByteArray invalid length. length:" + length);
		}
		
		//0-1
		final byte lowByte_Id = dataArea[0];
		final byte highByte_Id = dataArea[1];
		final long id = getLongFromLowByteAndHighByte(lowByte_Id, highByte_Id);
		templateVO.setId(id);
		
		//2-14
		final byte [] barCodeByteArray = new byte[13];
		System.arraycopy(dataArea, 2, barCodeByteArray, 0, 13);
		String barCode = super.dataTypeHelper.convert_byte_String(barCodeByteArray);
		templateVO.setBarCode(barCode);
		
		//15
		byte isMetal = dataArea[15];
		templateVO.setIsMetal((long)isMetal);
		validateMetalType((int)isMetal);
		
		//16-17
		final byte lowByte_Weight = dataArea[16];
		final byte highByte_Weight = dataArea[17];
		final long weight = getLongFromLowByteAndHighByte(lowByte_Weight, highByte_Weight);
		templateVO.setWeight(weight);
		
		//18-19
		final byte lowByte_ImageCharacteristicLength = dataArea[18];
		final byte highByte_ImageCharacteristicLength = dataArea[19];
		final long imageCharacteristicLength = getLongFromLowByteAndHighByte(lowByte_ImageCharacteristicLength, highByte_ImageCharacteristicLength);
		
		//20-end
		//validateTotalLength(20, (int)imageCharacteristicLength, length);
		long actualImageLength = imageCharacteristicLength*4;
		final byte [] imageArray = new byte[(int)actualImageLength];
		System.arraycopy(dataArea, 20, imageArray, 0, (int)actualImageLength);
		final List<PositionInfoVO> positionList = getPositionInfoListFromImageByteArray(imageArray);
		templateVO.setPositionInfoList(positionList);
		templateVO.setPosNum(positionList.size());
		
		//timestamp
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = null;
		ts = Timestamp.valueOf(df.format(new Date()));
		templateVO.setCreatedDate(ts);
		templateVO.setModifiedDate(ts);
		
		//default price
		templateVO.setPrice(1.0d);
		
		return templateVO;
	}
	
	public void validateMetalType(int metalType) {
		if (metalType != 0 && metalType != 1) {
			throw new NotSupportIsMetalTypeException("metalType:" + metalType);
		}
	}
	
	public void validateTotalLength(int startPos,  int imageLength, int totalLength) {
		final int expectedLengh = startPos + imageLength;
		if (expectedLengh != totalLength) {
			final String errorMessage = "invalid length. startPos:" + startPos + "--imageLength:" + imageLength + "--totalLength:" + totalLength;
			throw new DataLengthNotEqualActualLengthException(errorMessage);
		}
	}
	
	public List<PositionInfoVO> getPositionInfoListFromImageByteArray(final byte [] imageArray) {
		super.validateObject(imageArray);
		final int length = imageArray.length;
		if (length%4 != 0) {
			throw new RuntimeException("length is illegal. length:" + length + "--imageArray:"+ imageArray);
		}
		
		final List<PositionInfoVO> positionInfoList = new ArrayList<PositionInfoVO>();
		for (int index = 0; index<length; index+=4){
			final byte lowByte_X = imageArray[index+0];
			final byte highByte_X = imageArray[index+1];
			final long x = getLongFromLowByteAndHighByte(lowByte_X, highByte_X);
			
			final byte lowByte_Y = imageArray[index+2];
			final byte highByte_Y = imageArray[index+3];
			final long y = getLongFromLowByteAndHighByte(lowByte_Y, highByte_Y);
			
			final PositionInfoVO pos = new PositionInfoVO(x, y);
			positionInfoList.add(pos);
		}
		
		return positionInfoList;
	}
}
 