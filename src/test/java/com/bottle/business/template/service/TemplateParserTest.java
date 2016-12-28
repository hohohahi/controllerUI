package com.bottle.business.template.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.bottle.business.common.vo.PositionInfoVO;
import com.bottle.business.template.exception.DataLengthNotEqualActualLengthException;
import com.bottle.business.template.exception.NotSupportIsMetalTypeException;
import com.bottle.business.template.vo.TemplateVO;
import com.bottle.common.BasicDataTypeHelper;

public class TemplateParserTest {
	private TemplateParser parser = new TemplateParser();
	@Rule
	public ExpectedException thrown= ExpectedException.none();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		parser.setDataTypeHelper(new BasicDataTypeHelper());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=RuntimeException.class)
	public void test_ValidateTotalLength_Exception() {
		parser.validateTotalLength(0, 1, 0);
	}
	
	@Test
	public void test_ValidateTotalLength_OK() {
		parser.validateTotalLength(1, 2, 3);
	}
	
	@Test
	public void test_GetPositionInfoListFromImageByteArray_DataNull() {
		thrown.expect(NullPointerException.class);
		parser.getPositionInfoListFromImageByteArray(null);
	}
	
	@Test
	public void test_GetPositionInfoListFromImageByteArray_SizeInvalid() {	
		byte [] dataArray = null;
		dataArray = new byte[]{0x00, 0x01, 0x02};
		thrown.expect(RuntimeException.class);
		parser.getPositionInfoListFromImageByteArray(dataArray);
	}
	
	@Test
	public void test_GetPositionInfoListFromImageByteArray_Normal() {	
		byte [] dataArray = new byte[]{1, 2, 3, 4};
		final List<PositionInfoVO> positionInfoVOList = parser.getPositionInfoListFromImageByteArray(dataArray);
		Assert.assertNotNull(positionInfoVOList);
		Assert.assertEquals(positionInfoVOList.size(), 1);
		final PositionInfoVO positionVO = positionInfoVOList.get(0);
		long x = 2*256 + 1;
		long y = 4*256 + 3;
		Assert.assertEquals(positionVO.getX(), x);
		Assert.assertEquals(positionVO.getY(), y);
	}
	
	@Test
	public void test_GetLongFromHighByteAndLowByte() {
		final byte lowByte = 0x01;
		final byte highByte = 0x02;
		final long rtnLong = parser.getLongFromLowByteAndHighByte(lowByte, highByte);
		final long expectedValue = ((long)highByte)*256 + (long)lowByte;
		System.out.println(rtnLong);
		System.out.println(expectedValue);
		Assert.assertEquals(rtnLong, expectedValue);
	}
	
	@Test
	public void test_getTemplateFromByteArray_NullInput() {
		thrown.expect(NullPointerException.class);
		parser.getTemplateFromByteArray(null);
	}
	
	@Test
	public void test_getTemplateFromByteArray_IncorrectLength() {
		thrown.expect(RuntimeException.class);
		byte [] dataArray = new byte[]{1, 2, 3, 4};
		parser.getTemplateFromByteArray(dataArray);
	}
	
	@Test
	public void test_getTemplateFromByteArray_NotSupportedIsMetalType() {
		thrown.expect(NotSupportIsMetalTypeException.class);
		byte [] dataArray = new byte[]{1, 2,   //0, 1  id
				                      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',   //2-14 barCode 
				                      3,  //15  isMetal
				                      4, 5,  //16, 17 weight 
				                      4, 1,  //18, 19, length of image
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		
		parser.getTemplateFromByteArray(dataArray);
	}
	
	@Test
	@Ignore
	public void test_getTemplateFromByteArray_ImageLengthValid() {
		thrown.expect(DataLengthNotEqualActualLengthException.class);
		byte [] dataArray = new byte[]{1, 2,   //0, 1  id
				                      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',   //2-14 barCode 
				                      1,  //15  isMetal
				                      4, 5,  //16, 17 weight 
				                      4, 1,  //18, 19, length of image
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8};
		
		parser.getTemplateFromByteArray(dataArray);
	}
	
	@Test
	@Ignore
	public void test_getTemplateFromByteArray_Normal() {
		byte [] dataArray = new byte[]{1, 2,   //0, 1  id
				                      'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',   //2-14 barCode 
				                      1,  //15  isMetal
				                      4, 5,  //16, 17 weight 
				                      20, 0,  //18, 19, length of image
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				                      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3
				                      };
		
		final TemplateVO template = parser.getTemplateFromByteArray(dataArray);
		
		//id
		final long id_expected = 2*256 + 1;
		Assert.assertEquals(template.getId(), id_expected);
		
		//bar code
		final String barCode_expected = "abcdefghijklm";
		Assert.assertEquals(barCode_expected, template.getBarCode());
		Assert.assertNotEquals("xxxxyyy", template.getBarCode());
		
		//isMetal
		Assert.assertEquals(template.getIsMetal(), 1);
		Assert.assertNotEquals(template.getIsMetal(), 2);
		
		//weight
		final long weight_expected = 5*256 + 4;
		Assert.assertEquals(template.getWeight(), weight_expected);
		Assert.assertNotEquals(template.getWeight(), 888);
		
		//position
		final List<PositionInfoVO> positionList = template.getPositionInfoList();
		Assert.assertNotNull(positionList);
		Assert.assertEquals(positionList.size(), 5);
		Assert.assertEquals(positionList.get(0).getX(), 1*256);
		Assert.assertEquals(positionList.get(0).getY(), 3*256+2);
		Assert.assertEquals(positionList.get(1).getX(), 5*256+4);
		Assert.assertEquals(positionList.get(1).getY(), 7*256+6);
		Assert.assertEquals(positionList.get(2).getX(), 9*256+8);
		Assert.assertEquals(positionList.get(2).getY(), 1*256+0);
		Assert.assertEquals(positionList.get(3).getX(), 3*256+2);
		Assert.assertEquals(positionList.get(3).getY(), 5*256+4);
		Assert.assertEquals(positionList.get(4).getX(), 7*256+6);
		Assert.assertEquals(positionList.get(4).getY(), 9*256+8);
	}
	
	@Test
	public void test_GetHighBitAndLowBitFromNumber() {
		long originalValue = 1000;  //1000=256*3 + 232
		byte [] rtnArray = parser.getLowBitAndHighBitFromNumber(originalValue);
		Assert.assertNotNull(rtnArray);
		Assert.assertEquals(rtnArray.length, 2);
		Assert.assertEquals(parser.getLongFromByte(rtnArray[0]), 0xe8);
		Assert.assertEquals(parser.getLongFromByte(rtnArray[1]), 0x03);
		long rtnLong = parser.getLongFromLowByteAndHighByte(rtnArray[0], rtnArray[1]);
		Assert.assertEquals(rtnLong, originalValue);
	}
	
	@Test
	public void test_GetImageByteArrayFromPositionList() {
		final List<PositionInfoVO> positionList = new ArrayList<PositionInfoVO>();
		positionList.add(new PositionInfoVO(1, 2));
		positionList.add(new PositionInfoVO(3, 4));
		positionList.add(new PositionInfoVO(5, 6));
		byte [] rtnArray = parser.getImageByteArrayFromPositionList(positionList);
		Assert.assertNotNull(rtnArray);
		Assert.assertEquals(rtnArray.length, 3*4);
		
		final long xPos1 = parser.getLongFromLowByteAndHighByte(rtnArray[0], rtnArray[1]);
		final long yPos1 = parser.getLongFromLowByteAndHighByte(rtnArray[2], rtnArray[3]);
		final long xPos2 = parser.getLongFromLowByteAndHighByte(rtnArray[4], rtnArray[5]);
		final long yPos2 = parser.getLongFromLowByteAndHighByte(rtnArray[6], rtnArray[7]);
		final long xPos3 = parser.getLongFromLowByteAndHighByte(rtnArray[8], rtnArray[9]);
		final long yPos3 = parser.getLongFromLowByteAndHighByte(rtnArray[10], rtnArray[11]);

		Assert.assertEquals(xPos1, 1);
		Assert.assertEquals(yPos1, 2);
		Assert.assertEquals(xPos2, 3);
		Assert.assertEquals(yPos2, 4);
		Assert.assertEquals(xPos3, 5);
		Assert.assertEquals(yPos3, 6);
	}
	
	@Test
	public void test_getByteArrayFromTemplate() {
		final TemplateVO template = new TemplateVO();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = null;
		template.setBarCode("1234567890abc");		
		ts = Timestamp.valueOf(df.format(new Date()));				
		List<PositionInfoVO> positionList = new ArrayList<PositionInfoVO>();
		positionList.add(new PositionInfoVO(1L, 2L));
		positionList.add(new PositionInfoVO(3L, 4L));
		positionList.add(new PositionInfoVO(5L, 6L));
		positionList.add(new PositionInfoVO(7L, 8L));
		positionList.add(new PositionInfoVO(9L, 500L));
		template.setPosNum(positionList.size());
		template.setCreatedDate(ts);
		template.setCreatedBy(2L);
		template.setPositionInfoList(positionList);
		template.setIsMetal(1L);
		template.setDescription("test");
		template.setName("farmer water");
		template.setModifiedBy(3L);
		template.setModifiedDate(ts);
		template.setWeight(4L);
		
		byte [] rtnBytes = parser.getByteArrayFromTemplate(template);
		final TemplateVO rtnTemplate = parser.getTemplateFromByteArray(rtnBytes);
		Assert.assertEquals(template.getId(), rtnTemplate.getId());
		Assert.assertEquals(template.getBarCode(), rtnTemplate.getBarCode());
		Assert.assertEquals(template.getIsMetal(), rtnTemplate.getIsMetal());
		Assert.assertEquals(template.getWeight(), rtnTemplate.getWeight());
		Assert.assertEquals(template.getPosNum(), rtnTemplate.getPosNum());
		System.out.println(rtnTemplate);
	}
}
