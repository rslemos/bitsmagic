/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Rodrigo Lemos
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * END COPYRIGHT NOTICE
 *******************************************************************************/
package br.eti.rslemos.bitsmagic;

import static br.eti.rslemos.bitsmagic.Store.BYTE_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.BYTE_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.CHAR_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.CHAR_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.INT_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.INT_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.LONG_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.LONG_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.SHORT_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.SHORT_ADDRESS_MASK;

/**
 * This class consists exclusively of static methods for conversion between 
 * <a href="https://en.wikipedia.org/wiki/Binary_number#Representation">binary</a> 
 * and <a href="https://en.wikipedia.org/wiki/Gray_code">Gray</a> coding on 
 * integral primitive types, or arrays of integral primitive types.
 * 
 * <p>For every method available in this class, the arguments that represent
 * offsets should always be given in bits, and are 0-based. For more 
 * information about bit mapping in arrays of integral primitive types see 
 * {@link Store} class.
 * </p>
 * <p>Binary and Gray coding are the same for data that are 1-bit wide. 
 * Therefore, methods in this class will only operate on data at least 2-bits 
 * in width. Otherwise data will be left unchanged and no exception is thrown.
 * </p>
 * <p>The behavior of offlimits bits is unspecified (yet).
 * </p>
 * <p>{@code NullPointerException} is thrown if the given array is {@code null}.
 * </p>
 * <p>All methods are inherently thread unsafe: in case of more than one thread 
 * acting upon the same storage the results are undefined. Also neither they 
 * acquire nor block on any monitor. Any necessary synchronization should be 
 * done externally.
 * </p>
 * 
 * @author Rodrigo Lemos
 * @since 1.0.0
 * @see Store
 * @see <a href="https://en.wikipedia.org/wiki/Gray_code">Gray</a>
 */
public class GrayCode {
	/**
	 * Returns, in Gray coding, the number specified in binary coding.
	 *
	 * @param v number in binary coding.
	 * @return the specified number in Gray coding.
	 * 
	 * @since 1.0.0
	 */
	public static byte toGray(byte v) {
		int i = v & 0xff;
		return (byte) (i ^ i >>> 1);
	}

	/**
	 * Returns, in binary coding, the number specified in Gray coding.
	 *
	 * @param g number in Gray coding.
	 * @return the specified number in binary coding.
	 * 
	 * @since 1.0.0
	 */
	public static byte fromGray(byte g) {
		int i = g & 0xff;
		i ^= i >> 1;
		i ^= i >> 2;
		i ^= i >> 4;
		
		return (byte)i;
	}

	/**
	 * Returns, in Gray coding, the number specified in binary coding.
	 *
	 * @param v number in binary coding.
	 * @return the specified number in Gray coding.
	 * 
	 * @since 1.0.0
	 */
	public static char toGray(char v) {
		int i = v & 0xffff;
		return (char) (i ^ i >>> 1);
	}

	/**
	 * Returns, in binary coding, the number specified in Gray coding.
	 *
	 * @param g number in Gray coding.
	 * @return the specified number in binary coding.
	 * 
	 * @since 1.0.0
	 */
	public static char fromGray(char g) {
		int i = g & 0xffff;
		i ^= i >> 1;
		i ^= i >> 2;
		i ^= i >> 4;
		i ^= i >> 8;
		
		return (char)i;
	}

	/**
	 * Returns, in Gray coding, the number specified in binary coding. The 
	 * specified number is taken to be unsigned.
	 *
	 * @param v number in binary coding, as an unsigned quantity.
	 * @return the specified number in Gray coding.
	 * 
	 * @since 1.0.0
	 */
	public static short toGray(short v) {
		int i = v & 0xffff;
		return (short) (i ^ i >>> 1);
	}

	/**
	 * Returns, in binary coding, the number specified in Gray coding. The 
	 * returned number should be taken as an unsigned quantity.
	 *
	 * @param g number in Gray coding.
	 * @return the specified number in binary coding, as an unsigned quantity.
	 * 
	 * @since 1.0.0
	 */
	public static short fromGray(short g) {
		int i = g & 0xffff;
		i ^= i >> 1;
		i ^= i >> 2;
		i ^= i >> 4;
		i ^= i >> 8;
		
		return (short)i;
	}

	/**
	 * Returns, in Gray coding, the number specified in binary coding. The 
	 * specified number is taken to be unsigned.
	 *
	 * @param v number in binary coding, as an unsigned quantity.
	 * @return the specified number in Gray coding.
	 * 
	 * @since 1.0.0
	 */
	public static int toGray(int v) {
		return v ^ v >>> 1;
	}

	/**
	 * Returns, in binary coding, the number specified in Gray coding. The 
	 * returned number should be taken as an unsigned quantity.
	 *
	 * @param g number in Gray coding.
	 * @return the specified number in binary coding, as an unsigned quantity.
	 * 
	 * @since 1.0.0
	 */
	public static int fromGray(int g) {
		g ^= g >>> 1;
		g ^= g >>> 2;
		g ^= g >>> 4;
		g ^= g >>> 8;
		g ^= g >>> 16;
		
		return g;
	}

	/**
	 * Returns, in Gray coding, the number specified in binary coding. The 
	 * specified number is taken to be unsigned.
	 *
	 * @param v number in binary coding, as an unsigned quantity.
	 * @return the specified number in Gray coding.
	 * 
	 * @since 1.0.0
	 */
	public static long toGray(long v) {
		return v ^ v >>> 1;
	}

	/**
	 * Returns, in binary coding, the number specified in Gray coding. The 
	 * returned number should be taken as an unsigned quantity.
	 *
	 * @param g number in Gray coding.
	 * @return the specified number in binary coding, as an unsigned quantity.
	 * 
	 * @since 1.0.0
	 */
	public static long fromGray(long g) {
		g ^= g >>> 1;
		g ^= g >>> 2;
		g ^= g >>> 4;
		g ^= g >>> 8;
		g ^= g >>> 16;
		g ^= g >>> 32;
		
		return g;
	}
	
	/********** byte[] **********/

	/**
	 * Converts to Gray coding the specified region of the given storage taken 
	 * as an unsigned binary coded quantity. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to Gray coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to Gray coding.
	 *
	 * @since 1.0.0
	 */
	public static void toGray(byte[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		if (to - from < 2)
			return;
		
		int[] index  = {from  >> BYTE_ADDRESS_LINES, to >> BYTE_ADDRESS_LINES};
		int[] offset = {from  & BYTE_ADDRESS_MASK,   to & BYTE_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		byte[] aux = new byte[index[1] - index[0]];
		System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
		Shifter.shr(aux, offset[0], offset[0] + to - from, 1);
		Xor.xorFrom(aux, offset[0], data, from, to - from);
	}

	/**
	 * Converts to an unsigned binary coded quantity the specified region of 
	 * the given storage taken as Gray coded. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to binary coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to binary coding.
	 *
	 * @since 1.0.0
	 */
	public static void fromGray(byte[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		int[] index  = {from  >> BYTE_ADDRESS_LINES, to >> BYTE_ADDRESS_LINES};
		int[] offset = {from  & BYTE_ADDRESS_MASK,   to & BYTE_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		byte[] aux = new byte[index[1] - index[0]];
		
		int length = to - from;
		for(int i = 1; i < length; i <<= 1) {
			System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
			Shifter.shr(aux, offset[0], offset[0] + length, i);
			Xor.xorFrom(aux, offset[0], data, from, length);
		}
	}
	
	/********** char[] **********/

	/**
	 * Converts to Gray coding the specified region of the given storage taken 
	 * as an unsigned binary coded quantity. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to Gray coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to Gray coding.
	 *
	 * @since 1.0.0
	 */
	public static void toGray(char[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		if (to - from < 2)
			return;
		
		int[] index  = {from  >> CHAR_ADDRESS_LINES, to >> CHAR_ADDRESS_LINES};
		int[] offset = {from  & CHAR_ADDRESS_MASK,   to & CHAR_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		char[] aux = new char[index[1] - index[0]];
		System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
		Shifter.shr(aux, offset[0], offset[0] + to - from, 1);
		Xor.xorFrom(aux, offset[0], data, from, to - from);
	}

	/**
	 * Converts to an unsigned binary coded quantity the specified region of 
	 * the given storage taken as Gray coded. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to binary coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to binary coding.
	 *
	 * @since 1.0.0
	 */
	public static void fromGray(char[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		int[] index  = {from  >> CHAR_ADDRESS_LINES, to >> CHAR_ADDRESS_LINES};
		int[] offset = {from  & CHAR_ADDRESS_MASK,   to & CHAR_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		char[] aux = new char[index[1] - index[0]];
		
		int length = to - from;
		for(int i = 1; i < length; i <<= 1) {
			System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
			Shifter.shr(aux, offset[0], offset[0] + length, i);
			Xor.xorFrom(aux, offset[0], data, from, length);
		}
	}
	
	/********** short[] **********/

	/**
	 * Converts to Gray coding the specified region of the given storage taken 
	 * as an unsigned binary coded quantity. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to Gray coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to Gray coding.
	 *
	 * @since 1.0.0
	 */
	public static void toGray(short[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		if (to - from < 2)
			return;
		
		int[] index  = {from  >> SHORT_ADDRESS_LINES, to >> SHORT_ADDRESS_LINES};
		int[] offset = {from  & SHORT_ADDRESS_MASK,   to & SHORT_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		short[] aux = new short[index[1] - index[0]];
		System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
		Shifter.shr(aux, offset[0], offset[0] + to - from, 1);
		Xor.xorFrom(aux, offset[0], data, from, to - from);
	}

	/**
	 * Converts to an unsigned binary coded quantity the specified region of 
	 * the given storage taken as Gray coded. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to binary coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to binary coding.
	 *
	 * @since 1.0.0
	 */
	public static void fromGray(short[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		int[] index  = {from  >> SHORT_ADDRESS_LINES, to >> SHORT_ADDRESS_LINES};
		int[] offset = {from  & SHORT_ADDRESS_MASK,   to & SHORT_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		short[] aux = new short[index[1] - index[0]];
		
		int length = to - from;
		for(int i = 1; i < length; i <<= 1) {
			System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
			Shifter.shr(aux, offset[0], offset[0] + length, i);
			Xor.xorFrom(aux, offset[0], data, from, length);
		}
	}
	
	/********** int[] **********/

	/**
	 * Converts to Gray coding the specified region of the given storage taken 
	 * as an unsigned binary coded quantity. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to Gray coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to Gray coding.
	 *
	 * @since 1.0.0
	 */
	public static void toGray(int[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		if (to - from < 2)
			return;
		
		int[] index  = {from  >> INT_ADDRESS_LINES, to >> INT_ADDRESS_LINES};
		int[] offset = {from  & INT_ADDRESS_MASK,   to & INT_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		int[] aux = new int[index[1] - index[0]];
		System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
		Shifter.shr(aux, offset[0], offset[0] + to - from, 1);
		Xor.xorFrom(aux, offset[0], data, from, to - from);
	}

	/**
	 * Converts to an unsigned binary coded quantity the specified region of 
	 * the given storage taken as Gray coded. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to binary coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to binary coding.
	 *
	 * @since 1.0.0
	 */
	public static void fromGray(int[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		int[] index  = {from  >> INT_ADDRESS_LINES, to >> INT_ADDRESS_LINES};
		int[] offset = {from  & INT_ADDRESS_MASK,   to & INT_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		int[] aux = new int[index[1] - index[0]];
		
		int length = to - from;
		for(int i = 1; i < length; i <<= 1) {
			System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
			Shifter.shr(aux, offset[0], offset[0] + length, i);
			Xor.xorFrom(aux, offset[0], data, from, length);
		}
	}
	
	/********** long[] **********/

	/**
	 * Converts to Gray coding the specified region of the given storage taken 
	 * as an unsigned binary coded quantity. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to Gray coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to Gray coding.
	 *
	 * @since 1.0.0
	 */
	public static void toGray(long[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		if (to - from < 2)
			return;
		
		int[] index  = {from  >> LONG_ADDRESS_LINES, to >> LONG_ADDRESS_LINES};
		int[] offset = {from  & LONG_ADDRESS_MASK,   to & LONG_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		long[] aux = new long[index[1] - index[0]];
		System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
		Shifter.shr(aux, offset[0], offset[0] + to - from, 1);
		Xor.xorFrom(aux, offset[0], data, from, to - from);
	}

	/**
	 * Converts to an unsigned binary coded quantity the specified region of 
	 * the given storage taken as Gray coded. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If the
	 * region contains less than 2 bits, the storage is left unchanged.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        converted to binary coding.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        converted to binary coding.
	 *
	 * @since 1.0.0
	 */
	public static void fromGray(long[] data, int from, int to) {
		if (data == null)
			throw new NullPointerException();

		if (to - from < 0)
			throw new IllegalArgumentException();
		
		int[] index  = {from  >> LONG_ADDRESS_LINES, to >> LONG_ADDRESS_LINES};
		int[] offset = {from  & LONG_ADDRESS_MASK,   to & LONG_ADDRESS_MASK  };
		
		if (offset[1] > 0)
			index[1]++;
		
		long[] aux = new long[index[1] - index[0]];
		
		int length = to - from;
		for(int i = 1; i < length; i <<= 1) {
			System.arraycopy(data, index[0], aux, 0, index[1] - index[0]);
			Shifter.shr(aux, offset[0], offset[0] + length, i);
			Xor.xorFrom(aux, offset[0], data, from, length);
		}
	}
}
