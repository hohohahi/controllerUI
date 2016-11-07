package com.bottle.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

/***
 * 
 * @author Rainman
 *
 */
public interface IBasicDataTypeHelper {
	double _precision_sixBits = 0.000001d;
	
	double _precision_threeBits = 0.001d;
	
	BigDecimal _Big_Zero = new BigDecimal(0 + "");
	final int _default_scale = 14;
	final RoundingMode _default_RoundingMode = RoundingMode.UP;
	
	BigDecimal _Big_MinusOne = new BigDecimal(-1 + "");
	/***
	 * double add.
	 * @param src1
	 * @param src2
	 * @return
	 */
	double add_dd_d(final double src1, final double src2);
	
	/***
	 * double subtraction.
	 * @param src1
	 * @param src2
	 * @return
	 */
	double sub_dd_d(final double src1, final double src2);
	
	/***
	 * double div. this would take the pre-defined scale and roundMode.
	 * @param src1
	 * @param src2
	 * @return double result.
	 */
	double div_dd_d(final double src1, final double src2);
	
	/***
	 * double div.
	 * @param src1
	 * @param src2
	 * @param scale -- scale
	 * @param roundingMode -- roundMode
	 * @return
	 */
	double div_dd_d_scale_roundMode(final double src1, final double src2, final int scale, final RoundingMode roundingMode);
	
	/***
	 * double mul.
	 * @param src1
	 * @param src2
	 * @return double result.
	 */
	double mul_dd_d(final double src1, final double src2);
	
	/***
	 * to check, whether src is equal 0, with the precision "delta"
	 * @param src
	 * @param delta
	 * @return
	 */
	boolean isEqualZero_d_delta(final double src, final double delta);
	
	/***
	 * check whether two double values, is equal or not. with precision "delta"
	 * @param src1
	 * @param src2
	 * @param delta
	 * @return true: equal; false: not equal.
	 */
	boolean isEqual_dd_delta(final double src1, final double src2, final double delta);
	
	/***
	 * to check, whether src1 is equal src2(without delta)
	 * @param src1
	 * @param src2
	 * @return
	 */
	<T> boolean isEqual(final T src1, final T src2);
	
	/***
	 * to check, whether src1 is greater than src2.
	 * @param src1
	 * @param src2
	 * @return true: src1 is greater than src2. false: src1 is not greater than src2(equal or less than).
	 */
	<T> boolean isGreater(T src1, T src2);
	
	/***
	 * check, whether the src is equal zero
	 * @param src
	 * @return
	 */
	<T> boolean isEuqalZero(T src);
	
	/***
	 * check, whether the src is greater than zero
	 * @param src
	 * @return
	 */
	<T> boolean isGreaterThanZero(T src);
	
	/***
	 * check, whether the src is less than zero
	 * @param src
	 * @return
	 */
	<T> boolean isLessThanZero(T src);
	
	/***
	 * convert double to float
	 * @param src -- double
	 * @return -- float
	 */
	float convert_d_f(final double src);
	
	/***
	 * convert float to double
	 * @param src -- float
	 * @return -- double
	 */
	double convert_f_d(final float src);
	
	/***
	 * convert src to minus*src
	 * @param src
	 * @return -src
	 */
	 double convert_d_minus(final double src);
	 
	 String convert_byte_String(final byte [] byteArray);
	 double roundD_OneBit(double src);
}
