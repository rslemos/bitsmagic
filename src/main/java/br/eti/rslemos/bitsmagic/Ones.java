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
import static br.eti.rslemos.bitsmagic.Store.BYTE_DATA_MASK;
import static br.eti.rslemos.bitsmagic.Store.CHAR_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.CHAR_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.CHAR_DATA_MASK;
import static br.eti.rslemos.bitsmagic.Store.INT_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.INT_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.INT_DATA_MASK;
import static br.eti.rslemos.bitsmagic.Store.LONG_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.LONG_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.LONG_DATA_MASK;
import static br.eti.rslemos.bitsmagic.Store.SHORT_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.SHORT_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.SHORT_DATA_MASK;

/**
 * This class consists exclusively of static methods that count set bits on 
 * integral primitive types, or arrays of integral primitive types.
 * 
 * <p>For every method available in this class, the arguments that represent
 * offsets should always be given in bits, and are 0-based. For more 
 * information about bit mapping in arrays of integral primitive types see 
 * {@link Store} class.
 * </p>
 * <p>Offlimits bits are hardwired to 0: they never increment the count of set 
 * bits. Methods in this class should never throw 
 * {@code ArrayIndexOutOfBoundsException}.
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
 */
public class Ones {
	private Ones() { /* non-instantiable */ }

	/**
	 * Returns the count of bits set in specified byte.
	 * 
	 * @param x byte whose bits are to be counted
	 * 
	 * @since 1.0.0
	 */
	public static int ones(byte x) {
		return ones(x & BYTE_DATA_MASK);
	}

	/**
	 * Returns the count of bits set in specified char.
	 * 
	 * @param x char whose bits are to be counted
	 * 
	 * @since 1.0.0
	 */
	public static int ones(char x) {
		return ones(x & CHAR_DATA_MASK);
	}

	/**
	 * Returns the count of bits set in specified short.
	 * 
	 * @param x short whose bits are to be counted
	 * 
	 * @since 1.0.0
	 */
	public static int ones(short x) {
		return ones(x & SHORT_DATA_MASK);
	}

	/**
	 * Returns the count of bits set in specified int.
	 * 
	 * @param x int whose bits are to be counted
	 * 
	 * @since 1.0.0
	 */
	public static int ones(int x) {
		/*
		 * 32-bit recursive reduction using SWAR... but first step is mapping
		 * 2-bit values into sum of 2 1-bit values in sneaky way
		 */
		x -= ((x >> 1) & 0x55555555);
		x = (((x >> 2) & 0x33333333) + (x & 0x33333333));
		x = (((x >> 4) + x) & 0x0f0f0f0f);
		x += (x >> 8);
		x += (x >> 16);
		return (x & 0x0000003f);
	}

	/**
	 * Returns the count of bits set in specified long.
	 * 
	 * @param x long whose bits are to be counted
	 * 
	 * @since 1.0.0
	 */
	public static int ones(long x) {
		/*
		 * 64-bit recursive reduction using SWAR... but first step is mapping
		 * 2-bit values into sum of 2 1-bit values in sneaky way
		 */
		x -= ((x >> 1) & 0x5555555555555555L);
		x = (((x >> 2) & 0x3333333333333333L) + (x & 0x3333333333333333L));
		x = (((x >> 4) + x) & 0x0f0f0f0f0f0f0f0fL);
		x += (x >> 8);
		x += (x >> 16);
		x += (x >> 32);
		return (int) (x & 0x0000007f);
	}

	/********** byte[] **********/
	
	/**
	 * Returns the count of bits set in the specified region of the given 
	 * storage. The range considered extends from offset {@code from}, 
	 * inclusive, to offset {@code to}, exclusive. If {@code to <= from} this 
	 * method returns 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        counted.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        counted.
	 * 
	 * @since 1.0.0
	 */
	public static int ones(byte[] data, int from, int to) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << BYTE_ADDRESS_LINES)
			to = data.length << BYTE_ADDRESS_LINES;
		
		if (!(to > from))
			return 0;

		int[] index  = {from  >> BYTE_ADDRESS_LINES, to >> BYTE_ADDRESS_LINES};
		int[] offset = {from  & BYTE_ADDRESS_MASK,   to & BYTE_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final int LOWEST_BITS_FROM = ~(BYTE_DATA_MASK << offset[0]);
			final int HIGHEST_BITS_TO = BYTE_DATA_MASK << offset[1];

			return ones((byte)(data[index[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO)));
		}
		
		int count = 0;
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = BYTE_DATA_MASK << offset[0];

			count += ones((byte)(data[index[0]] & HIGHEST_BITS));
			
			// first index already taken care of
			index[0]++;
		}

		for (int j = index[0]; j < index[1]; j++)
			count += ones(data[j]);

		if (offset[1] != 0) {
			final int LOWEST_BITS = ~(BYTE_DATA_MASK << offset[1]);
			
			count += ones((byte)(data[index[1]] & LOWEST_BITS));
		}
		
		return count;
	}

	/********** char[] **********/
	
	/**
	 * Returns the count of bits set in the specified region of the given 
	 * storage. The range considered extends from offset {@code from}, 
	 * inclusive, to offset {@code to}, exclusive. If {@code to <= from} this 
	 * method returns 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        counted.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        counted.
	 * 
	 * @since 1.0.0
	 */
	public static int ones(char[] data, int from, int to) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << CHAR_ADDRESS_LINES)
			to = data.length << CHAR_ADDRESS_LINES;
		
		if (!(to > from))
			return 0;

		int[] index  = {from  >> CHAR_ADDRESS_LINES, to >> CHAR_ADDRESS_LINES};
		int[] offset = {from  & CHAR_ADDRESS_MASK,   to & CHAR_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final int LOWEST_BITS_FROM = ~(CHAR_DATA_MASK << offset[0]);
			final int HIGHEST_BITS_TO = CHAR_DATA_MASK << offset[1];

			return ones((char)(data[index[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO)));
		}
		
		int count = 0;
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = CHAR_DATA_MASK << offset[0];

			count += ones((char)(data[index[0]] & HIGHEST_BITS));
			
			// first index already taken care of
			index[0]++;
		}

		for (int j = index[0]; j < index[1]; j++)
			count += ones(data[j]);

		if (offset[1] != 0) {
			final int LOWEST_BITS = ~(CHAR_DATA_MASK << offset[1]);
			
			count += ones((char)(data[index[1]] & LOWEST_BITS));
		}
		
		return count;
	}

	/********** short[] **********/
	
	/**
	 * Returns the count of bits set in the specified region of the given 
	 * storage. The range considered extends from offset {@code from}, 
	 * inclusive, to offset {@code to}, exclusive. If {@code to <= from} this 
	 * method returns 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        counted.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        counted.
	 * 
	 * @since 1.0.0
	 */
	public static int ones(short[] data, int from, int to) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << SHORT_ADDRESS_LINES)
			to = data.length << SHORT_ADDRESS_LINES;
		
		if (!(to > from))
			return 0;

		int[] index  = {from  >> SHORT_ADDRESS_LINES, to >> SHORT_ADDRESS_LINES};
		int[] offset = {from  & SHORT_ADDRESS_MASK,   to & SHORT_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final int LOWEST_BITS_FROM = ~(SHORT_DATA_MASK << offset[0]);
			final int HIGHEST_BITS_TO = SHORT_DATA_MASK << offset[1];

			return ones((short)(data[index[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO)));
		}
		
		int count = 0;
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = SHORT_DATA_MASK << offset[0];

			count += ones((short)(data[index[0]] & HIGHEST_BITS));
			
			// first index already taken care of
			index[0]++;
		}

		for (int j = index[0]; j < index[1]; j++)
			count += ones(data[j]);

		if (offset[1] != 0) {
			final int LOWEST_BITS = ~(SHORT_DATA_MASK << offset[1]);
			
			count += ones((short)(data[index[1]] & LOWEST_BITS));
		}
		
		return count;
	}

	/********** int[] **********/
	
	/**
	 * Returns the count of bits set in the specified region of the given 
	 * storage. The range considered extends from offset {@code from}, 
	 * inclusive, to offset {@code to}, exclusive. If {@code to <= from} this 
	 * method returns 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        counted.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        counted.
	 * 
	 * @since 1.0.0
	 */
	public static int ones(int[] data, int from, int to) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << INT_ADDRESS_LINES)
			to = data.length << INT_ADDRESS_LINES;
		
		if (!(to > from))
			return 0;

		int[] index  = {from  >> INT_ADDRESS_LINES, to >> INT_ADDRESS_LINES};
		int[] offset = {from  & INT_ADDRESS_MASK,   to & INT_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final int LOWEST_BITS_FROM = ~(INT_DATA_MASK << offset[0]);
			final int HIGHEST_BITS_TO = INT_DATA_MASK << offset[1];

			return ones(data[index[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO));
		}
		
		int count = 0;
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = INT_DATA_MASK << offset[0];

			count += ones(data[index[0]] & HIGHEST_BITS);
			
			// first index already taken care of
			index[0]++;
		}

		for (int j = index[0]; j < index[1]; j++)
			count += ones(data[j]);

		if (offset[1] != 0) {
			final int LOWEST_BITS = ~(INT_DATA_MASK << offset[1]);
			
			count += ones(data[index[1]] & LOWEST_BITS);
		}
		
		return count;
	}

	/********** long[] **********/
	
	/**
	 * Returns the count of bits set in the specified region of the given 
	 * storage. The range considered extends from offset {@code from}, 
	 * inclusive, to offset {@code to}, exclusive. If {@code to <= from} this 
	 * method returns 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        counted.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        counted.
	 * 
	 * @since 1.0.0
	 */
	public static int ones(long[] data, int from, int to) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << LONG_ADDRESS_LINES)
			to = data.length << LONG_ADDRESS_LINES;
		
		if (!(to > from))
			return 0;

		int[] index  = {from  >> LONG_ADDRESS_LINES, to >> LONG_ADDRESS_LINES};
		int[] offset = {from  & LONG_ADDRESS_MASK,   to & LONG_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final long LOWEST_BITS_FROM = ~(LONG_DATA_MASK << offset[0]);
			final long HIGHEST_BITS_TO = LONG_DATA_MASK << offset[1];

			return ones(data[index[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO));
		}
		
		int count = 0;
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final long HIGHEST_BITS = LONG_DATA_MASK << offset[0];

			count += ones(data[index[0]] & HIGHEST_BITS);
			
			// first index already taken care of
			index[0]++;
		}

		for (int j = index[0]; j < index[1]; j++)
			count += ones(data[j]);

		if (offset[1] != 0) {
			final long LOWEST_BITS = ~(LONG_DATA_MASK << offset[1]);
			
			count += ones(data[index[1]] & LOWEST_BITS);
		}
		
		return count;
	}
}
