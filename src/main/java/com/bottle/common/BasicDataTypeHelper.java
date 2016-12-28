package com.bottle.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/***
 * 
 * @author Rainman.
 * 
 * Basic bean, and can not be embedded in AbstractBaseBean
 *
 */
@Component
public class BasicDataTypeHelper implements IBasicDataTypeHelper {
	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public double add_dd_d(double src1, double src2) {
		final BigDecimal src1_Big = createBigDecimal(src1);
		final BigDecimal src2_Big = createBigDecimal(src2);
		
		final BigDecimal rtn_Big = add_bb_b(src1_Big, src2_Big);
		return rtn_Big.doubleValue();
	}
	
	private <T> BigDecimal createBigDecimal(T type){
		final BigDecimal big = new BigDecimal(type + "");
		return big;
	}
	
	private BigDecimal add_bb_b(final BigDecimal src1, final BigDecimal src2){
		return src1.add(src2);
	}

	@Override
	public double sub_dd_d(double src1, double src2) {
		final BigDecimal src1_Big = createBigDecimal(src1);
		final BigDecimal src2_Big = createBigDecimal(src2);
		
		final BigDecimal rtn_Big = sub_bb_b(src1_Big, src2_Big);
		return rtn_Big.doubleValue();
	}
	
	@Override
	public double roundD_OneBit(double src) {
		final BigDecimal rtn_Big = createBigDecimal(src);
		
		return rtn_Big.setScale(1, RoundingMode.HALF_UP).doubleValue();
	}
	
	private BigDecimal sub_bb_b(final BigDecimal src1, final BigDecimal src2){
		return src1.subtract(src2);
	}

	@Override
	public boolean isEqualZero_d_delta(double src, double delta) {
		final BigDecimal src_Big = createBigDecimal(src);
		final BigDecimal delta_Big = createBigDecimal(delta);
		
		BigDecimal srcFinal_Big;
		if ((true == this.isEuqalZero(src))
				|| (true == this.isGreaterThanZero(src))){
			srcFinal_Big = src_Big;
		}
		else{
			srcFinal_Big = src_Big.multiply(_Big_MinusOne);
		}
		
		final int compareResult = srcFinal_Big.compareTo(delta_Big);
		
		boolean isEqual = true;
		if (compareResult == 0 || compareResult == -1){
			isEqual = true;
		}
		else{
			isEqual = false;
		}
		
		return isEqual;
	}

	@Override
	public boolean isEqual_dd_delta(final double src1, final double src2, final double delta) {
		final double dif = sub_dd_d(src1, src2);
		final boolean isEqual = isEqualZero_d_delta(dif, delta);
	
		return isEqual;
	}

	@Override
	public <T> boolean isGreaterThanZero(T src) {
		final BigDecimal src_Big = createBigDecimal(src + "");
		
		boolean isGreaterThanZero = true;
		if (src_Big.compareTo(_Big_Zero) == 1){
			isGreaterThanZero = true;
		}
		else{
			isGreaterThanZero = false;
		}
		
		return isGreaterThanZero;
	}

	@Override
	public <T> boolean isEuqalZero(T src) {
		final BigDecimal src_Big = createBigDecimal(src + "");
		
		boolean isEqualZero = true;
		if (src_Big.compareTo(_Big_Zero) == 0){
			isEqualZero = true;
		}
		else{
			isEqualZero = false;
		}
		
		return isEqualZero;
	}

	@Override
	public <T> boolean isLessThanZero(T src) {
		final BigDecimal src_Big = createBigDecimal(src + "");
		
		boolean isLessThanZero = true;
		if (src_Big.compareTo(_Big_Zero) == -1){
			isLessThanZero = true;
		}
		else{
			isLessThanZero = false;
		}
		
		return isLessThanZero;
	}

	@Override
	public float convert_d_f(double src) {
		final BigDecimal src_Big = createBigDecimal(src);
		return src_Big.floatValue();
	}

	@Override
	public double convert_f_d(float src) {
		final BigDecimal src_Big = createBigDecimal(src);
		return src_Big.doubleValue();
	}

	@Override
	public <T> boolean isGreater(T src1, T src2) {
		final BigDecimal src1_Big = createBigDecimal(src1);
		final BigDecimal src2_Big = createBigDecimal(src2);
		
		final int result = src1_Big.compareTo(src2_Big);
		
		boolean isGreater = true;
		if (1 == result){
			isGreater = true;
		}
		else{
			isGreater = false;
		}
		
		return isGreater;
	}

	@Override
	public <T> boolean isEqual(T src1, T src2) {
		final BigDecimal src1_Big = createBigDecimal(src1);
		final BigDecimal src2_Big = createBigDecimal(src2);
		
		final int result = src1_Big.compareTo(src2_Big);
		
		boolean isEqual = true;
		if (0 == result){
			isEqual = true;
		}
		else{
			isEqual = false;
		}
		
		return isEqual;
	}
	
	@Override
	final public double convert_d_minus(final double src) {
		final BigDecimal src_Big = createBigDecimal(src);
		final BigDecimal result_Big = src_Big.multiply(_Big_MinusOne);
		
		return result_Big.doubleValue();
	}

	@Override
	final public double div_dd_d(final double src1, final double src2) {
		return div_dd_d_scale_roundMode(src1, src2, _default_scale, _default_RoundingMode);
	}

	@Override
	final public double mul_dd_d(final double src1, final double src2) {
		final BigDecimal src1_Big = createBigDecimal(src1);
		final BigDecimal src2_Big = createBigDecimal(src2);
		
		final BigDecimal result_Big = src1_Big.multiply(src2_Big);
		return result_Big.doubleValue();
	}

	@Override
	public double div_dd_d_scale_roundMode(final double src1, final double src2, final int scale, final RoundingMode roundingMode) {
		final BigDecimal src1_Big = createBigDecimal(src1);
		final BigDecimal src2_Big = createBigDecimal(src2);
		
		final BigDecimal result_Big = src1_Big.divide(src2_Big, scale, roundingMode);
		return result_Big.doubleValue();
	}

	@Override
	public String convert_byte_String(byte[] byteArray) {
		if (null == byteArray) {
			throw new NullPointerException("byteArray is null.");
		}
		
		int length = byteArray.length;
		
		String rtnString = "";
		if (length > 0) {
			char[] tChars=new char[length];
			for(int i=0; i<length; i++) {
				tChars[i]=(char)byteArray[i];
			}				

			rtnString = new String(tChars);
		}
		else {
			rtnString = "";
		}
	
		return rtnString;
	}

	@Override
	public byte[] convert_String_byteArray(String srcString) {
		if (null == srcString) {
			throw new NullPointerException("srcString is null.");
		}
		
		final int length = srcString.length();
		char [] charArray = new char[length];
		byte [] byteArray = new byte[length];
		srcString.getChars(0, length, charArray, 0);
		
		for (int i = 0; i < length; i++) {
			byteArray[i] = (byte)charArray[i];
		}

		return byteArray;
	}
	
	public static void main(String [] args) {
		BasicDataTypeHelper helper = new BasicDataTypeHelper();
		final String srcString = "abcdefg123456";
		byte [] rtnByteArray = helper.convert_String_byteArray(srcString);
		final String rtnString = helper.convert_byte_String(rtnByteArray);
		
		System.out.println(srcString);
		System.out.println(rtnString);
	}
}
