package com.bottle.hardware.rxtx;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bottle.common.constants.ICommonConstants;
import com.bottle.hardware.rxtx.vo.ByteArrayParseResultVO;

public class ByteDataParserTest {
	ByteDataParser parse = new ByteDataParser();
	
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@Test(expected = RuntimeException.class)
	public void test_NoStartByte_EndByte() {
		byte [] inputSrcByteArray = new byte[]{0x00};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}

	@Test(expected = RuntimeException.class)
	public void test_HasStartByte_NoEndByte() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._SerialComm_Data_StartByte_};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test(expected = RuntimeException.class)
	public void test_HasStartByte_HasEndByte_NoPID_NoAID_NoLengthBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._SerialComm_Data_StartByte_, ICommonConstants._SerialComm_Data_EndByte_};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test(expected = RuntimeException.class)
	public void test_HasStartByte_HasEndByte_HasPID_NoAID_NoLengthBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._SerialComm_Data_StartByte_, ICommonConstants._Zero_Byte_, ICommonConstants._SerialComm_Data_EndByte_};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test(expected = RuntimeException.class)
	public void test_HasStartByte_HasEndByte_HasPID_HasAUI_NoLengthBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._SerialComm_Data_StartByte_, 
											   ICommonConstants._Zero_Byte_, 
											   ICommonConstants._Zero_Byte_, 
											   ICommonConstants._SerialComm_Data_EndByte_};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test(expected = RuntimeException.class)
	public void test_HasStartByte_HasEndByte_HasPID_HasAID_OneLengthBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._SerialComm_Data_StartByte_, 
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test
	public void test_HasStartByte_HasEndByte_HasPID_HasAID_HasLengthBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._SerialComm_Data_StartByte_, 
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test
	public void test_MoreBits_BeforeStartBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_, 
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test
	public void test_MoreBits_BeforeStartBits_AfterEndBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_, 
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test
	public void test_MultipleStartBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_, 
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test
	public void test_MultipleEndBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_, 
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test
	public void test_MultipleStartBits_MultipleEndBits() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test
	public void test_MultipleValidNum() {
		thrown.expect(RuntimeException.class);
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test(expected = RuntimeException.class)
	public void test_ErrorLengthDefinition() {
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   0x01,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											};
		parse.extractValidByteArrayFromSrc(inputSrcByteArray);
	}
	
	@Test
	public void test() {
		byte pid = 0x30;
		byte aid = 0x40;
		byte data_bit_1 = 0x61;
		byte data_bit_2 = 0x62;
		byte data_bit_3 = 0x63;
		byte [] dataArray = new byte[]{data_bit_1, data_bit_2, data_bit_3};
		byte [] inputSrcByteArray = new byte[]{ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_StartByte_,
											   pid,
											   aid,
											   0x03,
											   ICommonConstants._Zero_Byte_,
											   data_bit_1,
											   data_bit_2,
											   data_bit_3,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											   ICommonConstants._SerialComm_Data_EndByte_,
											   ICommonConstants._Zero_Byte_,
											};
		ByteArrayParseResultVO result = parse.extractValidByteArrayFromSrc(inputSrcByteArray);
		Assert.assertNotNull(result);
		Assert.assertEquals((long)pid, (long)result.getPid());
		Assert.assertNotEquals((long)pid, (long)result.getAid());
		Assert.assertEquals((long)aid, (long)result.getAid());
		Assert.assertNotEquals((long)aid, (long)result.getPid());
		
		Assert.assertArrayEquals(dataArray, result.getDataArray());
	}
}
