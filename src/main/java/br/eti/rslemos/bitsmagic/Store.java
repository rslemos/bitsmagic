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

import java.util.Arrays;

/**
 * This class consists exclusively of static methods that read or write bits 
 * over arrays of integral primitive type.
 * 
 * <p>The number of bits available per array element (element width) varies 
 * according to the storage class used:
 * </p>
 * <ul>
 *   <li>{@code byte}: 8 bits ({@code Byte.SIZE});</li>
 *   <li>{@code char}: 16 bits ({@code Character.SIZE});</li>
 *   <li>{@code short}: 16 bits ({@code Short.SIZE});</li>
 *   <li>{@code int}: 32 bits ({@code Integer.SIZE});</li>
 *   <li>{@code long}: 64 bits ({@code Long.SIZE}).</li>
 * </ul>
 * <p>The total number of bits available (non-offlimits bits) is the product of 
 * {@code arraysize} by {@code element width}.
 * </p>
 * <p>For every method available in this class, the arguments that represent
 * offsets should always be given in bits, and are 0-based. The bit mapping 
 * goes as follows ({@code S} is the element width in bits):
 * </p>
 * <ul>
 *   <li>bits &lt; 0: always offlimits;</li>
 *   <li>bit 0: the least significant bit of first element;</li>
 *   <li>bit 1: the second least significant bit of first element;</li>
 *   <li>bit S-1: the most significant bit of first element (this happens to be 
 *     the sign bit on signed types: {@code short}, {@code int} and 
 *     {@code long});</li>
 *   <li>bit S: the least significant bit of second element;</li>
 *   <li>bit S+1: the second least significant bit of second element;</li>
 *   <li>bit 2S-1: the most significant bit of second element;</li>
 *   <li>bits &gt;= available bits (as previously defined): always offlimits.
 *     </li>
 * </ul>
 * <p>Offlimits bits are hardwired to 0: they always read as 0, and any value 
 * written to them is discarded. Except where otherwise noted, methods in this 
 * class should never throw {@code ArrayIndexOutOfBoundsException}.
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
 */
public class Store {
	private Store() { /* non-instantiable */ }

	/********** byte[] **********/

	static final int BYTE_ADDRESS_LINES = 3;
	static final int BYTE_DATA_LINES = 1 << BYTE_ADDRESS_LINES;
	static final int BYTE_ADDRESS_MASK = ~(-1 << BYTE_ADDRESS_LINES);
	static final int BYTE_DATA_MASK = ~(-1 << BYTE_DATA_LINES);
	
	// we expect this function to be heavily inlined
	private static int read(byte[] data, int index) {
		return index < data.length && index >=0 ? data[index] & BYTE_DATA_MASK : 0;
	}

	/**
	 * Reads the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static boolean readBit(byte[] data, int i) {
		int index = i >> BYTE_ADDRESS_LINES;

		if (index >= data.length || index < 0)
			return false;
		
		int offset = i & BYTE_ADDRESS_MASK;
		return readBit0(data, index, offset);
	}
	
	private static boolean readBit0(byte[] data, int index, int offset) {
		return (data[index] << ~offset) < 0;
	}
	
	/**
	 * Writes the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBit(byte[] data, int i, boolean v) {
		int index = i >> BYTE_ADDRESS_LINES;

		if (index < data.length && index >= 0)
			writeBit0(data, index, i & BYTE_ADDRESS_MASK, v);
	}

	private static void writeBit0(byte[] data, int index, int offset, boolean v) {
		if (v)
			data[index] |= 1 << offset;
		else
			data[index] &= ~(1 << offset);
	}

	/**
	 * Assigns the specified bit value to each bit of the specified range of 
	 * the given storage. The range to be filled extends from offset 
	 * {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} this method does nothing.
	 * 
	 * <p>This method behaves as the following code:
	 * <pre>
	 *   for(int i = from; i &lt; to; i++)
	 *     Store.writeBit(data, i, v);
	 * </pre>
	 * </p>
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        filled with the specified value.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        filled with the specified value.
	 * @param v value whose contents will be used to fill the specified region 
	 *        into {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void fill(byte[] data, int from, int to, boolean v) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << BYTE_ADDRESS_LINES)
			to = data.length << BYTE_ADDRESS_LINES;
		
		if (!(to > from))
			return;

		int[] index  = {from  >> BYTE_ADDRESS_LINES, to >> BYTE_ADDRESS_LINES};
		int[] offset = {from  & BYTE_ADDRESS_MASK,   to & BYTE_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final long LOWEST_BITS_FROM = ~(BYTE_DATA_MASK << offset[0]);
			final long HIGHEST_BITS_TO = BYTE_DATA_MASK << offset[1];

			if (v)
				data[index[0]] |= ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);
			else
				data[index[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			
			return;
		}
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final long HIGHEST_BITS = BYTE_DATA_MASK << offset[0];

			if (v)
				data[index[0]] |= HIGHEST_BITS;
			else
				data[index[0]] &= ~HIGHEST_BITS;
			
			// first index already taken care of
			index[0]++;
		}

		if (index[1] > index[0])
			Arrays.fill(data, index[0], index[1], (byte) (v ? -1 : 0));

		if (offset[1] != 0) {
			final long LOWEST_BITS = ~(BYTE_DATA_MASK << offset[1]);

			if (v)
				data[index[1]] |= LOWEST_BITS;
			else
				data[index[1]] &= ~LOWEST_BITS;
		}
	}

	/**
	 * Reads as {@code byte} the 8 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static byte readByte(byte[] data, int i) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & BYTE_ADDRESS_MASK;
		if (offset == 0)
			return (byte) d0;

		d0 >>>= offset;
		
		int d1 = read(data, ++index);
		d1 <<= BYTE_DATA_LINES - offset;
		
		return (byte) (d1 | d0);
	}

	/**
	 * Writes 8 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeByte(byte[] data, int i, byte v) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & BYTE_ADDRESS_MASK;
		
		int mask = ~(BYTE_DATA_MASK << Byte.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (offset == 0)
			return;
		
		if (++index >= data.length) return;
		
		mask = ~(BYTE_DATA_MASK << Byte.SIZE) >>> BYTE_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= (v >> (BYTE_DATA_LINES-offset)) & mask;
	}

	/**
	 * Reads as {@code char} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static char readChar(byte[] data, int i) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		int d0 = read(data, index);
		int d1 = read(data, ++index);
	
		int offset = i & BYTE_ADDRESS_MASK;
		if (offset == 0)
			return (char) (d1 << BYTE_DATA_LINES | d0);
		
		int d2 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= BYTE_DATA_LINES - offset;
		d2 <<= 2*BYTE_DATA_LINES - offset;
		
		return (char) (d2 | d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeChar(byte[] data, int i, char v) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -2) return;
		
		int offset = i & BYTE_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (byte)(v >> 0);
	
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> BYTE_DATA_LINES);
			
			return;
		}
	
		int mask = BYTE_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (byte)(v >> 2*BYTE_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Reads as {@code short} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static short readShort(byte[] data, int i) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		int d0 = read(data, index);
		int d1 = read(data, ++index);
	
		int offset = i & BYTE_ADDRESS_MASK;
		if (offset == 0)
			return (short) (d1 << BYTE_DATA_LINES | d0);
		
		int d2 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= BYTE_DATA_LINES - offset;
		d2 <<= 2*BYTE_DATA_LINES - offset;
		
		return (short) (d2 | d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeShort(byte[] data, int i, short v) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -2) return;
		
		int offset = i & BYTE_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (byte)(v >> 0);
	
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> BYTE_DATA_LINES);
			
			return;
		}
	
		int mask = BYTE_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (byte)(v >> 2*BYTE_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Reads as {@code int} the 32 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static int readInt(byte[] data, int i) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		int d0 = read(data, index);
		int d1 = read(data, ++index);
		int d2 = read(data, ++index);
		int d3 = read(data, ++index);
	
		int offset = i & BYTE_ADDRESS_MASK;
		if (offset == 0)
			return (int) (d3 << 3*BYTE_DATA_LINES | d2 << 2*BYTE_DATA_LINES | d1 << BYTE_DATA_LINES | d0);
		
		long d4 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= BYTE_DATA_LINES - offset;
		d2 <<= 2*BYTE_DATA_LINES - offset;
		d3 <<= 3*BYTE_DATA_LINES - offset;
		d4 <<= 4*BYTE_DATA_LINES - offset;
		
		return (int) (d4 | d3 | d2 | d1 | d0);
	}

	/**
	 * Writes 32 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeInt(byte[] data, int i, int v) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -4) return;
		
		int offset = i & BYTE_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (byte)(v >> 0);
	
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> BYTE_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> 2*BYTE_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> 3*BYTE_DATA_LINES);
			
			return;
		}
	
		int mask = BYTE_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> 2*BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> 3*BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (byte)(v >> 4*BYTE_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Reads as {@code long} the 64 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static long readLong(byte[] data, int i) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		int d0 = read(data, index);
		int d1 = read(data, ++index);
		int d2 = read(data, ++index);
		long d3 = read(data, ++index);
		long d4 = read(data, ++index);
		long d5 = read(data, ++index);
		long d6 = read(data, ++index);
		long d7 = read(data, ++index);
	
		int offset = i & BYTE_ADDRESS_MASK;
		if (offset == 0)
			return d7 << 7*BYTE_DATA_LINES | d6 << 6*BYTE_DATA_LINES | d5 << 5*BYTE_DATA_LINES | d4 << 4*BYTE_DATA_LINES
					| d3 << 3*BYTE_DATA_LINES | d2 << 2*BYTE_DATA_LINES | d1 << BYTE_DATA_LINES | d0;
		
		long d8 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= BYTE_DATA_LINES - offset;
		d2 <<= 2*BYTE_DATA_LINES - offset;
		d3 <<= 3*BYTE_DATA_LINES - offset;
		d4 <<= 4*BYTE_DATA_LINES - offset;
		d5 <<= 5*BYTE_DATA_LINES - offset;
		d6 <<= 6*BYTE_DATA_LINES - offset;
		d7 <<= 7*BYTE_DATA_LINES - offset;
		d8 <<= 8*BYTE_DATA_LINES - offset;
		
		return d8 | d7 | d6 | d5 | d4 | d3 | d2 | d1 | d0;
	}

	/**
	 * Writes 64 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeLong(byte[] data, int i, long v) {
		int index = i >> BYTE_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -8) return;
		
		int offset = i & BYTE_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (byte)(v >> 0);
	
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> BYTE_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> 2*BYTE_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> 3*BYTE_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> 4*BYTE_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> 5*BYTE_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> 6*BYTE_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (byte)(v >> 7*BYTE_DATA_LINES);
			
			return;
		}
	
		int mask = BYTE_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> 2*BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> 3*BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> 4*BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> 5*BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> 6*BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (byte)(v >> 7*BYTE_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (byte)(v >> 8*BYTE_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Returns a string representation of the contents of the given storage. 
	 * The string representation consists of digits '0' and '1' for all 
	 * non-offlimits bits. The first character of the returned string 
	 * represents the 0<sup>th</sup> bit.
	 * 
	 * @param data storage array.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(byte[] data) {
		return readBitString(data, 0, data.length * BYTE_DATA_LINES - 0);
	}
	
	/**
	 * Returns a string representation of a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1' for 
	 * bits ranging from {@code offset}, inclusive, to {@code offset + length}, 
	 * exclusive. If any offlimits bit is touched, this method will throw 
	 * {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param length number of digits returned.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(byte[] data, int offset, int length) {
		char[] dest = new char[length];
		return new String(readBitString(data, offset, dest, 0, length));
	}
	
	private static char[] readBitString(byte[] src, int srcPos, char[] dest, int destPos, int length) {
		Arrays.fill(dest, destPos, length, '0');
		
		for (int i = destPos + length - 1, index = 0; i >= destPos; i--, srcPos++) {
			index += srcPos >> BYTE_ADDRESS_LINES;
			srcPos &= BYTE_ADDRESS_MASK;

			if (readBit0(src, index, srcPos))
				dest[i] = '1';
		}
		
		return dest;
	}

	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code 0}, inclusive, to 
	 * {@code string's length}, exclusive.
	 * 
	 * @param data storage array.
	 * @param v string whose contents are stored into given storage.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(byte[] data, String v) {
		writeBitString(data, 0, v);
	}
	
	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code offset}, inclusive, to 
	 * {@code offset + length}, exclusive. If any offlimits bit is touched, 
	 * this method will throw {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param v string whose contents are stored into given storage.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(byte[] data, int offset, String v) {
		writeBitString(data, offset, v.length(), v);
	}
	
	private static void writeBitString(byte[] data, int offset, int length, String v) {
		writeBitString(data, offset, v.toCharArray(), 0, length);
	}
	
	private static void writeBitString(byte[] dest, int destPos, char[] src, int srcPos, int length) {
		for (int i = srcPos + length - 1, index = 0; i >= srcPos; i--, destPos++) {
			index += destPos >> BYTE_ADDRESS_LINES;
			destPos &= BYTE_ADDRESS_MASK;
			
			writeBit0(dest, index, destPos, src[i] == '1');
		}
	}

	/********** char[] **********/

	static final int CHAR_ADDRESS_LINES = 4;
	static final int CHAR_DATA_LINES = 1 << CHAR_ADDRESS_LINES;
	static final int CHAR_ADDRESS_MASK = ~(-1 << CHAR_ADDRESS_LINES);
	static final int CHAR_DATA_MASK = ~(-1 << CHAR_DATA_LINES);
	
	// we expect this function to be heavily inlined
	private static int read(char[] data, int index) {
		return index < data.length && index >=0 ? data[index] & CHAR_DATA_MASK : 0;
	}

	/**
	 * Reads the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static boolean readBit(char[] data, int i) {
		int index = i >> CHAR_ADDRESS_LINES;

		if (index >= data.length || index < 0)
			return false;
		
		int offset = i & CHAR_ADDRESS_MASK;
		return readBit0(data, index, offset);
	}

	private static boolean readBit0(char[] data, int index, int offset) {
		return (data[index] << ~offset) < 0;
	}
	
	/**
	 * Writes the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBit(char[] data, int i, boolean v) {
		int index = i >> CHAR_ADDRESS_LINES;

		if (index < data.length && index >= 0)
			writeBit0(data, index, i & CHAR_ADDRESS_MASK, v);
	}

	private static void writeBit0(char[] data, int index, int offset, boolean v) {
		if (v)
			data[index] |= 1 << offset;
		else
			data[index] &= ~(1 << offset);
	}

	/**
	 * Assigns the specified bit value to each bit of the specified range of 
	 * the given storage. The range to be filled extends from offset 
	 * {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} this method does nothing.
	 * 
	 * <p>This method behaves as the following code:
	 * </p>
	 * <pre>
	 *   for(int i = from; i < to; i++)
	 *     Store.writeBit(data, i, v);
	 * </pre>
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        filled with the specified value.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        filled with the specified value.
	 * @param v value whose contents will be used to fill the specified region 
	 *        into {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void fill(char[] data, int from, int to, boolean v) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << CHAR_ADDRESS_LINES)
			to = data.length << CHAR_ADDRESS_LINES;

		if (!(to > from))
			return;

		int[] index  = {from  >> CHAR_ADDRESS_LINES, to >> CHAR_ADDRESS_LINES};
		int[] offset = {from  & CHAR_ADDRESS_MASK,   to & CHAR_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final long LOWEST_BITS_FROM = ~(CHAR_DATA_MASK << offset[0]);
			final long HIGHEST_BITS_TO = CHAR_DATA_MASK << offset[1];

			if (v)
				data[index[0]] |= ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);
			else
				data[index[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			
			return;
		}
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final long HIGHEST_BITS = CHAR_DATA_MASK << offset[0];

			if (v)
				data[index[0]] |= HIGHEST_BITS;
			else
				data[index[0]] &= ~HIGHEST_BITS;
			
			// first index already taken care of
			index[0]++;
		}

		if (index[1] > index[0])
			Arrays.fill(data, index[0], index[1], (char) (v ? -1 : 0));

		if (offset[1] != 0) {
			final long LOWEST_BITS = ~(CHAR_DATA_MASK << offset[1]);

			if (v)
				data[index[1]] |= LOWEST_BITS;
			else
				data[index[1]] &= ~LOWEST_BITS;
		}
	}

	/**
	 * Reads as {@code byte} the 8 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static byte readByte(char[] data, int i) {
		int index = i >> CHAR_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & CHAR_ADDRESS_MASK;
		if (offset == 0)
			return (byte) d0;

		d0 >>>= offset;
		
		if (offset + Byte.SIZE <= CHAR_DATA_LINES)
			return (byte)d0;

		int d1 = read(data, ++index);
		d1 <<= CHAR_DATA_LINES - offset;
		
		return (byte) (d1 | d0);
	}

	/**
	 * Writes 8 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeByte(char[] data, int i, byte v) {
		int index = i >> CHAR_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & CHAR_ADDRESS_MASK;
		
		int mask = ~(CHAR_DATA_MASK << Byte.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (offset + Byte.SIZE <= CHAR_DATA_LINES)
			return;
		
		if (++index >= data.length) return;
		
		mask = ~(CHAR_DATA_MASK << Byte.SIZE) >>> CHAR_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= (v >> (CHAR_DATA_LINES-offset)) & mask;
	}

	/**
	 * Reads as {@code char} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static char readChar(char[] data, int i) {
		int index = i >> CHAR_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & CHAR_ADDRESS_MASK;
		if (offset == 0)
			return (char) d0;

		d0 >>>= offset;
		
		int d1 = read(data, ++index);
		d1 <<= CHAR_DATA_LINES - offset;
		
		return (char) (d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeChar(char[] data, int i, char v) {
		int index = i >> CHAR_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & CHAR_ADDRESS_MASK;
		
		int mask = ~(CHAR_DATA_MASK << Character.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= v << offset & mask;
		}
		
		if (offset + Character.SIZE <= CHAR_DATA_LINES)
			return;
		
		if (++index >= data.length) return;

		mask = ~(CHAR_DATA_MASK << Character.SIZE) >> CHAR_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= v >> CHAR_DATA_LINES - offset & mask;
	}

	/**
	 * Reads as {@code short} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static short readShort(char[] data, int i) {
		int index = i >> CHAR_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & CHAR_ADDRESS_MASK;
		if (offset == 0)
			return (short) d0;

		d0 >>>= offset;
		
		int d1 = read(data, ++index);
		d1 <<= CHAR_DATA_LINES - offset;
		
		return (short) (d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeShort(char[] data, int i, short v) {
		int index = i >> CHAR_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & CHAR_ADDRESS_MASK;
		
		int mask = ~(CHAR_DATA_MASK << Short.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= v << offset & mask;
		}
		
		if (offset + Short.SIZE <= CHAR_DATA_LINES)
			return;
		
		if (++index >= data.length) return;

		mask = ~(CHAR_DATA_MASK << Short.SIZE) >> CHAR_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= v >> CHAR_DATA_LINES - offset & mask;
	}

	/**
	 * Reads as {@code int} the 32 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static int readInt(char[] data, int i) {
		int index = i >> CHAR_ADDRESS_LINES;
		
		int d0 = read(data, index);
		int d1 = read(data, ++index);
	
		int offset = i & CHAR_ADDRESS_MASK;
		if (offset == 0)
			return d1 << CHAR_DATA_LINES | d0;
		
		int d2 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= CHAR_DATA_LINES - offset;
		d2 <<= 2*CHAR_DATA_LINES - offset;
		
		return d2 | d1 | d0;
	}

	/**
	 * Writes 32 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeInt(char[] data, int i, int v) {
		int index = i >> CHAR_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -2) return;
		
		int offset = i & CHAR_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (char)(v >> 0);
	
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (char)(v >> CHAR_DATA_LINES);
			
			return;
		}
	
		int mask = CHAR_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (char)(v >> CHAR_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (char)(v >> 2*CHAR_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Reads as {@code long} the 64 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static long readLong(char[] data, int i) {
		int index = i >> CHAR_ADDRESS_LINES;
		
		long d0 = read(data, index);
		long d1 = read(data, ++index);
		long d2 = read(data, ++index);
		long d3 = read(data, ++index);
	
		int offset = i & CHAR_ADDRESS_MASK;
		if (offset == 0)
			return d3 << 3*CHAR_DATA_LINES | d2 << 2*CHAR_DATA_LINES | d1 << CHAR_DATA_LINES | d0;
		
		long d4 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= CHAR_DATA_LINES - offset;
		d2 <<= 2*CHAR_DATA_LINES - offset;
		d3 <<= 3*CHAR_DATA_LINES - offset;
		d4 <<= 4*CHAR_DATA_LINES - offset;
		
		return d4 | d3 | d2 | d1 | d0;
	}

	/**
	 * Writes 64 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeLong(char[] data, int i, long v) {
		int index = i >> CHAR_ADDRESS_LINES;
	
		if (index >= data.length) return;
		if (index < -4) return;
		
		int offset = i & CHAR_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (char)(v >> 0);
	
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (char)(v >> CHAR_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (char)(v >> 2*CHAR_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (char)(v >> 3*CHAR_DATA_LINES);
			
			return;
		}
	
		int mask = CHAR_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (char)(v >> CHAR_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (char)(v >> 2*CHAR_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (char)(v >> 3*CHAR_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (char)(v >> 4*CHAR_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Returns a string representation of the contents of the given storage. 
	 * The string representation consists of digits '0' and '1' for all 
	 * non-offlimits bits. The first character of the returned string 
	 * represents the 0<sup>th</sup> bit.
	 * 
	 * @param data storage array.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(char[] data) {
		return readBitString(data, 0, data.length * CHAR_DATA_LINES - 0);
	}
	
	/**
	 * Returns a string representation of a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1' for 
	 * bits ranging from {@code offset}, inclusive, to {@code offset + length}, 
	 * exclusive. If any offlimits bit is touched, this method will throw 
	 * {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param length number of digits returned.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(char[] data, int offset, int length) {
		char[] dest = new char[length];
		return new String(readBitString(data, offset, dest, 0, length));
	}
	
	private static char[] readBitString(char[] src, int srcPos, char[] dest, int destPos, int length) {
		Arrays.fill(dest, destPos, length, '0');
		
		for (int i = destPos + length - 1, index = 0; i >= destPos; i--, srcPos++) {
			index += srcPos >> CHAR_ADDRESS_LINES;
			srcPos &= CHAR_ADDRESS_MASK;

			if (readBit0(src, index, srcPos))
				dest[i] = '1';
		}
		
		return dest;
	}

	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code 0}, inclusive, to 
	 * {@code string's length}, exclusive.
	 * 
	 * @param data storage array.
	 * @param v string whose contents are stored into given storage.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(char[] data, String v) {
		writeBitString(data, 0, v);
	}
	
	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code offset}, inclusive, to 
	 * {@code offset + length}, exclusive. If any offlimits bit is touched, 
	 * this method will throw {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param v string whose contents are stored into given storage.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(char[] data, int offset, String v) {
		writeBitString(data, offset, v.length(), v);
	}
	
	private static void writeBitString(char[] data, int offset, int length, String v) {
		writeBitString(data, offset, v.toCharArray(), 0, length);
	}
	
	private static void writeBitString(char[] dest, int destPos, char[] src, int srcPos, int length) {
		for (int i = srcPos + length - 1, index = 0; i >= srcPos; i--, destPos++) {
			index += destPos >> CHAR_ADDRESS_LINES;
			destPos &= CHAR_ADDRESS_MASK;
			
			writeBit0(dest, index, destPos, src[i] == '1');
		}
	}

	/********** short[] **********/

	static final int SHORT_ADDRESS_LINES = 4;
	static final int SHORT_DATA_LINES = 1 << SHORT_ADDRESS_LINES;
	static final int SHORT_ADDRESS_MASK = ~(-1 << SHORT_ADDRESS_LINES);
	static final int SHORT_DATA_MASK = ~(-1 << SHORT_DATA_LINES);
	
	// we expect this function to be heavily inlined
	private static int read(short[] data, int index) {
		return index < data.length && index >=0 ? data[index] & SHORT_DATA_MASK : 0;
	}

	/**
	 * Reads the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static boolean readBit(short[] data, int i) {
		int index = i >> SHORT_ADDRESS_LINES;

		if (index >= data.length || index < 0)
			return false;
		
		int offset = i & SHORT_ADDRESS_MASK;
		return readBit0(data, index, offset);
	}

	private static boolean readBit0(short[] data, int index, int offset) {
		return (data[index] << ~offset) < 0;
	}
	
	/**
	 * Writes the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBit(short[] data, int i, boolean v) {
		int index = i >> SHORT_ADDRESS_LINES;

		if (index < data.length && index >= 0)
			writeBit0(data, index, i & SHORT_ADDRESS_MASK, v);
	}

	private static void writeBit0(short[] data, int index, int offset, boolean v) {
		if (v)
			data[index] |= 1 << offset;
		else
			data[index] &= ~(1 << offset);
	}

	/**
	 * Assigns the specified bit value to each bit of the specified range of 
	 * the given storage. The range to be filled extends from offset 
	 * {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} this method does nothing.
	 * 
	 * <p>This method behaves as the following code:
	 * </p>
	 * <pre>
	 *   for(int i = from; i < to; i++)
	 *     Store.writeBit(data, i, v);
	 * </pre>
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        filled with the specified value.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        filled with the specified value.
	 * @param v value whose contents will be used to fill the specified region 
	 *        into {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void fill(short[] data, int from, int to, boolean v) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << SHORT_ADDRESS_LINES)
			to = data.length << SHORT_ADDRESS_LINES;

		if (!(to > from))
			return;

		int[] index  = {from  >> SHORT_ADDRESS_LINES, to >> SHORT_ADDRESS_LINES};
		int[] offset = {from  & SHORT_ADDRESS_MASK,   to & SHORT_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final long LOWEST_BITS_FROM = ~(SHORT_DATA_MASK << offset[0]);
			final long HIGHEST_BITS_TO = SHORT_DATA_MASK << offset[1];

			if (v)
				data[index[0]] |= ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);
			else
				data[index[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			
			return;
		}
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final long HIGHEST_BITS = SHORT_DATA_MASK << offset[0];

			if (v)
				data[index[0]] |= HIGHEST_BITS;
			else
				data[index[0]] &= ~HIGHEST_BITS;
			
			// first index already taken care of
			index[0]++;
		}

		if (index[1] > index[0])
			Arrays.fill(data, index[0], index[1], (short) (v ? -1 : 0));

		if (offset[1] != 0) {
			final long LOWEST_BITS = ~(SHORT_DATA_MASK << offset[1]);

			if (v)
				data[index[1]] |= LOWEST_BITS;
			else
				data[index[1]] &= ~LOWEST_BITS;
		}
	}

	/**
	 * Reads as {@code byte} the 8 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static byte readByte(short[] data, int i) {
		int index = i >> SHORT_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & SHORT_ADDRESS_MASK;
		if (offset == 0)
			return (byte) d0;

		d0 >>>= offset;
		
		if (offset + Byte.SIZE <= SHORT_DATA_LINES)
			return (byte)d0;

		int d1 = read(data, ++index);
		d1 <<= SHORT_DATA_LINES - offset;
		
		return (byte) (d1 | d0);
	}

	/**
	 * Writes 8 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeByte(short[] data, int i, byte v) {
		int index = i >> SHORT_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & SHORT_ADDRESS_MASK;
		
		int mask = ~(SHORT_DATA_MASK << Byte.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (offset + Byte.SIZE <= SHORT_DATA_LINES)
			return;
		
		if (++index >= data.length) return;
		
		mask = ~(SHORT_DATA_MASK << Byte.SIZE) >>> SHORT_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= (v >> (SHORT_DATA_LINES-offset)) & mask;
	}

	/**
	 * Reads as {@code char} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static char readChar(short[] data, int i) {
		int index = i >> SHORT_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & SHORT_ADDRESS_MASK;
		if (offset == 0)
			return (char) d0;
		
		int d1 = read(data, ++index);

		d0 >>>= offset;
		d1 <<= SHORT_DATA_LINES - offset;
		
		return (char) (d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeChar(short[] data, int i, char v) {
		int index = i >> SHORT_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & SHORT_ADDRESS_MASK;
		
		int mask = ~(SHORT_DATA_MASK << Character.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= v << offset & mask;
		}
		
		if (offset + Character.SIZE <= SHORT_DATA_LINES)
			return;
		
		if (++index >= data.length) return;

		mask = ~(SHORT_DATA_MASK << Character.SIZE) >> SHORT_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= v >> SHORT_DATA_LINES - offset & mask;
	}

	/**
	 * Reads as {@code short} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static short readShort(short[] data, int i) {
		int index = i >> SHORT_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & SHORT_ADDRESS_MASK;
		if (offset == 0)
			return (short) d0;

		int d1 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= SHORT_DATA_LINES - offset;
		
		return (short) (d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeShort(short[] data, int i, short v) {
		int index = i >> SHORT_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & SHORT_ADDRESS_MASK;
		
		int mask = ~(SHORT_DATA_MASK << Short.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= v << offset & mask;
		}
		
		if (offset + Short.SIZE <= SHORT_DATA_LINES)
			return;
		
		if (++index >= data.length) return;

		mask = ~(SHORT_DATA_MASK << Short.SIZE) >> SHORT_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= v >> SHORT_DATA_LINES - offset & mask;
	}

	/**
	 * Reads as {@code int} the 32 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static int readInt(short[] data, int i) {
		int index = i >> SHORT_ADDRESS_LINES;
		
		int d0 = read(data, index);
		int d1 = read(data, ++index);
	
		int offset = i & SHORT_ADDRESS_MASK;
		if (offset == 0)
			return d1 << SHORT_DATA_LINES | d0;
		
		int d2 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= SHORT_DATA_LINES - offset;
		d2 <<= 2*SHORT_DATA_LINES - offset;
		
		return d2 | d1 | d0;
	}

	/**
	 * Writes 32 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeInt(short[] data, int i, int v) {
		int index = i >> SHORT_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -2) return;
		
		int offset = i & SHORT_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (short)(v >> 0);
	
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (short)(v >> SHORT_DATA_LINES);
			
			return;
		}
	
		int mask = SHORT_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (short)(v >> SHORT_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (short)(v >> 2*SHORT_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Reads as {@code long} the 64 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static long readLong(short[] data, int i) {
		int index = i >> SHORT_ADDRESS_LINES;
		
		long d0 = read(data, index);
		long d1 = read(data, ++index);
		long d2 = read(data, ++index);
		long d3 = read(data, ++index);
	
		int offset = i & SHORT_ADDRESS_MASK;
		if (offset == 0)
			return d3 << 3*SHORT_DATA_LINES | d2 << 2*SHORT_DATA_LINES | d1 << SHORT_DATA_LINES | d0;
		
		long d4 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= SHORT_DATA_LINES - offset;
		d2 <<= 2*SHORT_DATA_LINES - offset;
		d3 <<= 3*SHORT_DATA_LINES - offset;
		d4 <<= 4*SHORT_DATA_LINES - offset;
		
		return d4 | d3 | d2 | d1 | d0;
	}

	/**
	 * Writes 64 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeLong(short[] data, int i, long v) {
		int index = i >> SHORT_ADDRESS_LINES;
	
		if (index >= data.length) return;
		if (index < -4) return;
		
		int offset = i & SHORT_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (short)(v >> 0);
	
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (short)(v >> SHORT_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (short)(v >> 2*SHORT_DATA_LINES);
			
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (short)(v >> 3*SHORT_DATA_LINES);
			
			return;
		}
	
		int mask = SHORT_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (short)(v >> SHORT_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (short)(v >> 2*SHORT_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (short)(v >> 3*SHORT_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (short)(v >> 4*SHORT_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Returns a string representation of the contents of the given storage. 
	 * The string representation consists of digits '0' and '1' for all 
	 * non-offlimits bits. The first character of the returned string 
	 * represents the 0<sup>th</sup> bit.
	 * 
	 * @param data storage array.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(short[] data) {
		return readBitString(data, 0, data.length * SHORT_DATA_LINES - 0);
	}
	
	/**
	 * Returns a string representation of a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1' for 
	 * bits ranging from {@code offset}, inclusive, to {@code offset + length}, 
	 * exclusive. If any offlimits bit is touched, this method will throw 
	 * {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param length number of digits returned.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(short[] data, int offset, int length) {
		char[] dest = new char[length];
		return new String(readBitString(data, offset, dest, 0, length));
	}
	
	private static char[] readBitString(short[] src, int srcPos, char[] dest, int destPos, int length) {
		Arrays.fill(dest, destPos, length, '0');
		
		for (int i = destPos + length - 1, index = 0; i >= destPos; i--, srcPos++) {
			index += srcPos >> SHORT_ADDRESS_LINES;
			srcPos &= SHORT_ADDRESS_MASK;

			if (readBit0(src, index, srcPos))
				dest[i] = '1';
		}
		
		return dest;
	}

	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code 0}, inclusive, to 
	 * {@code string's length}, exclusive.
	 * 
	 * @param data storage array.
	 * @param v string whose contents are stored into given storage.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(short[] data, String v) {
		writeBitString(data, 0, v);
	}
	
	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code offset}, inclusive, to 
	 * {@code offset + length}, exclusive. If any offlimits bit is touched, 
	 * this method will throw {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param v string whose contents are stored into given storage.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(short[] data, int offset, String v) {
		writeBitString(data, offset, v.length(), v);
	}
	
	private static void writeBitString(short[] data, int offset, int length, String v) {
		writeBitString(data, offset, v.toCharArray(), 0, length);
	}
	
	private static void writeBitString(short[] dest, int destPos, char[] src, int srcPos, int length) {
		for (int i = srcPos + length - 1, index = 0; i >= srcPos; i--, destPos++) {
			index += destPos >> SHORT_ADDRESS_LINES;
			destPos &= SHORT_ADDRESS_MASK;
			
			writeBit0(dest, index, destPos, src[i] == '1');
		}
	}

	/********** int[] **********/

	static final int INT_ADDRESS_LINES = 5;
	static final int INT_DATA_LINES = 1 << INT_ADDRESS_LINES;
	static final int INT_ADDRESS_MASK = ~(-1 << INT_ADDRESS_LINES);
	static final int INT_DATA_MASK = ~0;
	static final long INT_DATA_MASKL = ~(-1L << INT_DATA_LINES);
	
	// we expect this function to be heavily inlined
	private static int read(int[] data, int index) {
		return index < data.length && index >=0 ? data[index] : 0;
	}

	private static long readl(int[] data, int index) {
		return index < data.length && index >=0 ? (long)data[index] & INT_DATA_MASKL : 0;
	}

	/**
	 * Reads the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static boolean readBit(int[] data, int i) {
		int index = i >> INT_ADDRESS_LINES;

		if (index >= data.length || index < 0)
			return false;
		
		int offset = i & INT_ADDRESS_MASK;
		return readBit0(data, index, offset);
	}

	private static boolean readBit0(int[] data, int index, int offset) {
		return (data[index] << ~offset) < 0;
	}
	
	/**
	 * Writes the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBit(int[] data, int i, boolean v) {
		int index = i >> INT_ADDRESS_LINES;

		if (index < data.length && index >= 0)
			writeBit0(data, index, i & INT_ADDRESS_MASK, v);
	}

	private static void writeBit0(int[] data, int index, int offset, boolean v) {
		if (v)
			data[index] |= 1 << offset;
		else
			data[index] &= ~(1 << offset);
	}

	/**
	 * Assigns the specified bit value to each bit of the specified range of 
	 * the given storage. The range to be filled extends from offset 
	 * {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} this method does nothing.
	 * 
	 * <p>This method behaves as the following code:
	 * </p>
	 * <pre>
	 *   for(int i = from; i < to; i++)
	 *     Store.writeBit(data, i, v);
	 * </pre>
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        filled with the specified value.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        filled with the specified value.
	 * @param v value whose contents will be used to fill the specified region 
	 *        into {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void fill(int[] data, int from, int to, boolean v) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << INT_ADDRESS_LINES)
			to = data.length << INT_ADDRESS_LINES;

		if (!(to > from))
			return;

		int[] index  = {from  >> INT_ADDRESS_LINES, to >> INT_ADDRESS_LINES};
		int[] offset = {from  & INT_ADDRESS_MASK,   to & INT_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final long LOWEST_BITS_FROM = ~(INT_DATA_MASK << offset[0]);
			final long HIGHEST_BITS_TO = INT_DATA_MASK << offset[1];

			if (v)
				data[index[0]] |= ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);
			else
				data[index[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			
			return;
		}
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final long HIGHEST_BITS = INT_DATA_MASK << offset[0];

			if (v)
				data[index[0]] |= HIGHEST_BITS;
			else
				data[index[0]] &= ~HIGHEST_BITS;
			
			// first index already taken care of
			index[0]++;
		}

		if (index[1] > index[0])
			Arrays.fill(data, index[0], index[1], v ? -1 : 0);

		if (offset[1] != 0) {
			final long LOWEST_BITS = ~(INT_DATA_MASK << offset[1]);

			if (v)
				data[index[1]] |= LOWEST_BITS;
			else
				data[index[1]] &= ~LOWEST_BITS;
		}
	}

	/**
	 * Reads as {@code byte} the 8 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static byte readByte(int[] data, int i) {
		int index = i >> INT_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & INT_ADDRESS_MASK;
		if (offset == 0)
			return (byte) d0;

		d0 >>>= offset;
		
		if (offset + Byte.SIZE <= INT_DATA_LINES)
			return (byte)d0;

		int d1 = read(data, ++index);
		d1 <<= INT_DATA_LINES - offset;
		
		return (byte) (d1 | d0);
	}

	/**
	 * Writes 8 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeByte(int[] data, int i, byte v) {
		int index = i >> INT_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & INT_ADDRESS_MASK;
		
		int mask = ~(INT_DATA_MASK << Byte.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (offset + Byte.SIZE <= INT_DATA_LINES)
			return;
		
		if (++index >= data.length) return;
		
		mask = ~(INT_DATA_MASK << Byte.SIZE) >>> INT_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= (v >> (INT_DATA_LINES-offset)) & mask;
	}

	/**
	 * Reads as {@code char} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static char readChar(int[] data, int i) {
		int index = i >> INT_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & INT_ADDRESS_MASK;
		if (offset == 0)
			return (char) d0;

		d0 >>>= offset;
		
		if (offset + Character.SIZE <= INT_DATA_LINES)
			return (char)d0;

		int d1 = read(data, ++index);
		d1 <<= INT_DATA_LINES - offset;
		
		return (char) (d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeChar(int[] data, int i, char v) {
		int index = i >> INT_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & INT_ADDRESS_MASK;
		
		int mask = ~(INT_DATA_MASK << Character.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= v << offset & mask;
		}
		
		if (offset + Character.SIZE <= INT_DATA_LINES)
			return;
		
		if (++index >= data.length) return;

		mask = ~(INT_DATA_MASK << Character.SIZE) >> INT_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= v >> INT_DATA_LINES - offset & mask;
	}

	/**
	 * Reads as {@code short} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static short readShort(int[] data, int i) {
		int index = i >> INT_ADDRESS_LINES;
		
		int d0 = read(data, index);
		
		int offset = i & INT_ADDRESS_MASK;
		if (offset == 0)
			return (short) d0;

		d0 >>>= offset;
		
		if (offset + Short.SIZE <= INT_DATA_LINES)
			return (short)d0;

		int d1 = read(data, ++index);
		d1 <<= INT_DATA_LINES - offset;
		
		return (short) (d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeShort(int[] data, int i, short v) {
		int index = i >> INT_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & INT_ADDRESS_MASK;
		
		int mask = ~(INT_DATA_MASK << Short.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= v << offset & mask;
		}
		
		if (offset + Short.SIZE <= INT_DATA_LINES)
			return;
		
		if (++index >= data.length) return;

		mask = ~(INT_DATA_MASK << Short.SIZE) >> INT_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= v >> INT_DATA_LINES - offset & mask;
	}

	/**
	 * Reads as {@code int} the 32 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static int readInt(int[] data, int i) {
		int index = i >> INT_ADDRESS_LINES;
	
		int d0 = read(data, index);
	
		int offset = i & INT_ADDRESS_MASK;
		if (offset == 0)
			return d0;
		
		int d1 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= INT_DATA_LINES - offset;
		
		return d1 | d0;
	}

	/**
	 * Writes 32 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeInt(int[] data, int i, int v) {
		int index = i >> INT_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & INT_ADDRESS_MASK;
		
		if (offset == 0) {
			if (index >= 0)
				data[index] = v;
			
			return;
		}
		
		int mask = INT_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= v << offset & mask;
		}
		
		if (++index >= data.length) return;
		mask = INT_DATA_MASK >>> INT_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= (v >> (INT_DATA_LINES-offset)) & mask;
	}

	/**
	 * Reads as {@code long} the 64 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static long readLong(int[] data, int i) {
		int index = i >> INT_ADDRESS_LINES;
		
		long d0 = readl(data, index);
		long d1 = readl(data, ++index);
	
		int offset = i & INT_ADDRESS_MASK;
		if (offset == 0)
			return d1 << INT_DATA_LINES | d0;
		
		long d2 = readl(data, ++index);
		
		d0 >>>= offset;
		d1 <<= INT_DATA_LINES - offset;
		d2 <<= 2*INT_DATA_LINES - offset;
		
		return d2 | d1 | d0;
	}

	/**
	 * Writes 64 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeLong(int[] data, int i, long v) {
		int index = i >> INT_ADDRESS_LINES;
	
		if (index >= data.length) return;
		if (index < -2) return;
		
		int offset = i & INT_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (int)(v >> 0);
	
			if (++index >= data.length) return;
			if (index >= 0)
				data[index] = (int)(v >> INT_DATA_LINES);
			
			return;
		}
	
		int mask = INT_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		if (index >= 0)
			data[index] = (int)(v >> INT_DATA_LINES - offset);
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (int)(v >> 2*INT_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Returns a string representation of the contents of the given storage. 
	 * The string representation consists of digits '0' and '1' for all 
	 * non-offlimits bits. The first character of the returned string 
	 * represents the 0<sup>th</sup> bit.
	 * 
	 * @param data storage array.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(int[] data) {
		return readBitString(data, 0, data.length * INT_DATA_LINES - 0);
	}
	
	/**
	 * Returns a string representation of a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1' for 
	 * bits ranging from {@code offset}, inclusive, to {@code offset + length}, 
	 * exclusive. If any offlimits bit is touched, this method will throw 
	 * {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param length number of digits returned.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(int[] data, int offset, int length) {
		char[] dest = new char[length];
		return new String(readBitString(data, offset, dest, 0, length));
	}
	
	private static char[] readBitString(int[] src, int srcPos, char[] dest, int destPos, int length) {
		Arrays.fill(dest, destPos, length, '0');
		
		for (int i = destPos + length - 1, index = 0; i >= destPos; i--, srcPos++) {
			index += srcPos >> INT_ADDRESS_LINES;
			srcPos &= INT_ADDRESS_MASK;

			if (readBit0(src, index, srcPos))
				dest[i] = '1';
		}
		
		return dest;
	}

	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code 0}, inclusive, to 
	 * {@code string's length}, exclusive.
	 * 
	 * @param data storage array.
	 * @param v string whose contents are stored into given storage.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(int[] data, String v) {
		writeBitString(data, 0, v);
	}
	
	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code offset}, inclusive, to 
	 * {@code offset + length}, exclusive. If any offlimits bit is touched, 
	 * this method will throw {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param v string whose contents are stored into given storage.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(int[] data, int offset, String v) {
		writeBitString(data, offset, v.length(), v);
	}
	
	private static void writeBitString(int[] data, int offset, int length, String v) {
		writeBitString(data, offset, v.toCharArray(), 0, length);
	}
	
	private static void writeBitString(int[] dest, int destPos, char[] src, int srcPos, int length) {
		for (int i = srcPos + length - 1, index = 0; i >= srcPos; i--, destPos++) {
			index += destPos >> INT_ADDRESS_LINES;
			destPos &= INT_ADDRESS_MASK;
			
			writeBit0(dest, index, destPos, src[i] == '1');
		}
	}

	/********** long[] **********/

	static final int LONG_ADDRESS_LINES = 6;
	static final int LONG_DATA_LINES = 1 << LONG_ADDRESS_LINES;
	static final int LONG_ADDRESS_MASK = ~(-1 << LONG_ADDRESS_LINES);
	static final long LONG_DATA_MASK = ~0L;
	
	// we expect this function to be heavily inlined
	private static long read(long[] data, int index) {
		return index < data.length && index >=0 ? data[index] : 0;
	}

	/**
	 * Reads the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static boolean readBit(long[] data, int i) {
		int index = i >> LONG_ADDRESS_LINES;

		if (index >= data.length || index < 0)
			return false;
		
		int offset = i & LONG_ADDRESS_MASK;
		return readBit0(data, index, offset);
	}

	private static boolean readBit0(long[] data, int index, int offset) {
		return (data[index] << ~offset) < 0;
	}
	
	/**
	 * Writes the {@code i}<sup>th</sup> bit of the given storage.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBit(long[] data, int i, boolean v) {
		int index = i >> LONG_ADDRESS_LINES;

		if (index < data.length && index >= 0)
			writeBit0(data, index, i & LONG_ADDRESS_MASK, v);
	}

	private static void writeBit0(long[] data, int index, int offset, boolean v) {
		if (v)
			data[index] |= 1L << offset;
		else
			data[index] &= ~(1L << offset);
	}

	/**
	 * Assigns the specified bit value to each bit of the specified range of 
	 * the given storage. The range to be filled extends from offset 
	 * {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} this method does nothing.
	 * 
	 * <p>This method behaves as the following code:
	 * </p>
	 * <pre>
	 *   for(int i = from; i < to; i++)
	 *     Store.writeBit(data, i, v);
	 * </pre>
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        filled with the specified value.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        filled with the specified value.
	 * @param v value whose contents will be used to fill the specified region 
	 *        into {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void fill(long[] data, int from, int to, boolean v) {
		// clamp
		if (from < 0)
			from = 0;
		
		if (to > data.length << LONG_ADDRESS_LINES)
			to = data.length << LONG_ADDRESS_LINES;

		if (!(to > from))
			return;

		int[] index  = {from  >> LONG_ADDRESS_LINES, to >> LONG_ADDRESS_LINES};
		int[] offset = {from  & LONG_ADDRESS_MASK,   to & LONG_ADDRESS_MASK  };
		
		if (index[1] == index[0]) {
			// special case: subword count
			
			final long LOWEST_BITS_FROM = ~(LONG_DATA_MASK << offset[0]);
			final long HIGHEST_BITS_TO = LONG_DATA_MASK << offset[1];

			if (v)
				data[index[0]] |= ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);
			else
				data[index[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			
			return;
		}
		
		if (offset[0] != 0) {
			// handle "from" end specially
			
			final long HIGHEST_BITS = LONG_DATA_MASK << offset[0];

			if (v)
				data[index[0]] |= HIGHEST_BITS;
			else
				data[index[0]] &= ~HIGHEST_BITS;
			
			// first index already taken care of
			index[0]++;
		}

		if (index[1] > index[0])
			Arrays.fill(data, index[0], index[1], v ? -1L : 0L);

		if (offset[1] != 0) {
			final long LOWEST_BITS = ~(LONG_DATA_MASK << offset[1]);

			if (v)
				data[index[1]] |= LOWEST_BITS;
			else
				data[index[1]] &= ~LOWEST_BITS;
		}
	}

	/**
	 * Reads as {@code byte} the 8 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static byte readByte(long[] data, int i) {
		int index = i >> LONG_ADDRESS_LINES;
		
		long d0 = read(data, index);
		
		int offset = i & LONG_ADDRESS_MASK;
		if (offset == 0)
			return (byte) d0;

		d0 >>>= offset;
		
		if (offset + Byte.SIZE <= LONG_DATA_LINES)
			return (byte)d0;

		long d1 = read(data, ++index);
		d1 <<= LONG_DATA_LINES - offset;
		
		return (byte) (d1 | d0);
	}

	/**
	 * Writes 8 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+8}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeByte(long[] data, int i, byte v) {
		int index = i >> LONG_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & LONG_ADDRESS_MASK;
		
		long mask = ~(LONG_DATA_MASK << Byte.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (long)v << offset & mask;
		}
		
		if (offset + Byte.SIZE <= LONG_DATA_LINES)
			return;
		
		if (++index >= data.length) return;
		
		mask = ~(LONG_DATA_MASK << Byte.SIZE) >>> LONG_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= (v >>> (LONG_DATA_LINES-offset)) & mask;
	}

	/**
	 * Reads as {@code char} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static char readChar(long[] data, int i) {
		int index = i >> LONG_ADDRESS_LINES;
		
		long d0 = read(data, index);
		
		int offset = i & LONG_ADDRESS_MASK;
		if (offset == 0)
			return (char) d0;

		d0 >>>= offset;
		
		if (offset + Character.SIZE <= LONG_DATA_LINES)
			return (char)d0;

		long d1 = read(data, ++index);
		d1 <<= LONG_DATA_LINES - offset;
		
		return (char) (d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeChar(long[] data, int i, char v) {
		int index = i >> LONG_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & LONG_ADDRESS_MASK;
		
		long mask = ~(LONG_DATA_MASK << Character.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (long)v << offset & mask;
		}
		
		if (offset + Character.SIZE <= LONG_DATA_LINES)
			return;
		
		if (++index >= data.length) return;

		mask = ~(LONG_DATA_MASK << Character.SIZE) >>> LONG_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= v >>> LONG_DATA_LINES - offset & mask;
	}

	/**
	 * Reads as {@code short} the 16 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static short readShort(long[] data, int i) {
		int index = i >> LONG_ADDRESS_LINES;
		
		long d0 = read(data, index);
		
		int offset = i & LONG_ADDRESS_MASK;
		if (offset == 0)
			return (short) d0;

		d0 >>>= offset;
		
		if (offset + Short.SIZE <= LONG_DATA_LINES)
			return (short)d0;

		long d1 = read(data, ++index);
		d1 <<= LONG_DATA_LINES - offset;
		
		return (short) (d1 | d0);
	}

	/**
	 * Writes 16 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+16}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeShort(long[] data, int i, short v) {
		int index = i >> LONG_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & LONG_ADDRESS_MASK;
		
		long mask = ~(LONG_DATA_MASK << Short.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (long)v << offset & mask;
		}
		
		if (offset + Short.SIZE <= LONG_DATA_LINES)
			return;
		
		if (++index >= data.length) return;

		mask = ~(LONG_DATA_MASK << Short.SIZE) >>> LONG_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= v >>> LONG_DATA_LINES - offset & mask;
	}

	/**
	 * Reads as {@code int} the 32 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static int readInt(long[] data, int i) {
		int index = i >> LONG_ADDRESS_LINES;
		
		long d0 = read(data, index);
		
		int offset = i & LONG_ADDRESS_MASK;
		if (offset == 0)
			return (int) d0;

		d0 >>>= offset;
		
		if (offset + Integer.SIZE <= LONG_DATA_LINES)
			return (int)d0;

		long d1 = read(data, ++index);
		d1 <<= LONG_DATA_LINES - offset;
		
		return (int) (d1 | d0);
	}

	/**
	 * Writes 32 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+32}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeInt(long[] data, int i, int v) {
		int index = i >> LONG_ADDRESS_LINES;
		
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & LONG_ADDRESS_MASK;
		
		long mask = ~(LONG_DATA_MASK << Integer.SIZE) << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (long)v << offset & mask;
		}
		
		if (offset + Integer.SIZE <= LONG_DATA_LINES)
			return;
		
		if (++index >= data.length) return;

		mask = ~(LONG_DATA_MASK << Integer.SIZE) >>> LONG_DATA_LINES - offset;
		
		data[index] &= ~mask;
		data[index] |= v >>> LONG_DATA_LINES - offset & mask;
	}

	/**
	 * Reads as {@code long} the 64 bits of the given storage starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits read extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * 
	 * @since 1.0.0
	 */
	public static long readLong(long[] data, int i) {
		int index = i >> LONG_ADDRESS_LINES;
		
		long d0 = read(data, index);
	
		int offset = i & LONG_ADDRESS_MASK;
		if (offset == 0)
			return d0;
		
		long d1 = read(data, ++index);
		
		d0 >>>= offset;
		d1 <<= LONG_DATA_LINES - offset;
		
		return d1 | d0;
	}

	/**
	 * Writes 64 bits of the given storage, starting from the 
	 * {@code i}<sup>th</sup> bit. The range of bits written extends from 
	 * {@code i}, inclusive, to offset {@code i+64}, exclusive.
	 * 
	 * @param data storage array.
	 * @param i offset, in bits, 0-based.
	 * @param v value whose contents will be written to {@code data}.
	 * 
	 * @since 1.0.0
	 */
	public static void writeLong(long[] data, int i, long v) {
		int index = i >> LONG_ADDRESS_LINES;
	
		if (index >= data.length) return;
		if (index < -1) return;
		
		int offset = i & LONG_ADDRESS_MASK;
		if (offset == 0) {
			if (index >= 0)
				data[index] = (v >> 0);
	
			return;
		}
	
		long mask = LONG_DATA_MASK << offset;
		
		if (index >= 0) {
			data[index] &= ~mask;
			data[index] |= (v << offset) & mask;
		}
		
		if (++index >= data.length) return;
		
		data[index] &= mask;
		data[index] |= (v >>> LONG_DATA_LINES - offset) & ~mask;
	}

	/**
	 * Returns a string representation of the contents of the given storage. 
	 * The string representation consists of digits '0' and '1' for all 
	 * non-offlimits bits. The first character of the returned string 
	 * represents the 0<sup>th</sup> bit.
	 * 
	 * @param data storage array.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(long[] data) {
		return readBitString(data, 0, data.length * LONG_DATA_LINES - 0);
	}
	
	/**
	 * Returns a string representation of a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1' for 
	 * bits ranging from {@code offset}, inclusive, to {@code offset + length}, 
	 * exclusive. If any offlimits bit is touched, this method will throw 
	 * {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param length number of digits returned.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static String readBitString(long[] data, int offset, int length) {
		char[] dest = new char[length];
		return new String(readBitString(data, offset, dest, 0, length));
	}
	
	private static char[] readBitString(long[] src, int srcPos, char[] dest, int destPos, int length) {
		Arrays.fill(dest, destPos, length, '0');
		
		for (int i = destPos + length - 1, index = 0; i >= destPos; i--, srcPos++) {
			index += srcPos >> LONG_ADDRESS_LINES;
			srcPos &= LONG_ADDRESS_MASK;

			if (readBit0(src, index, srcPos))
				dest[i] = '1';
		}
		
		return dest;
	}

	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code 0}, inclusive, to 
	 * {@code string's length}, exclusive.
	 * 
	 * @param data storage array.
	 * @param v string whose contents are stored into given storage.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(long[] data, String v) {
		writeBitString(data, 0, v);
	}
	
	/**
	 * Stores a string representation into a range of the contents of the given 
	 * storage. The string representation consists of digits '0' and '1'. The 
	 * range of bits to be stored extends from {@code offset}, inclusive, to 
	 * {@code offset + length}, exclusive. If any offlimits bit is touched, 
	 * this method will throw {@code ArrayIndexOutOfBoundsException}.
	 * 
	 * @param data storage array.
	 * @param offset start of range, in bits, 0-based, inclusive.
	 * @param v string whose contents are stored into given storage.
	 * @throws ArrayIndexOutOfBoundsException if offset is negative or if 
	 *   {@code offset + length} is greater than the number of bits available.
	 * 
	 * @since 1.0.0
	 */
	public static void writeBitString(long[] data, int offset, String v) {
		writeBitString(data, offset, v.length(), v);
	}
	
	private static void writeBitString(long[] data, int offset, int length, String v) {
		writeBitString(data, offset, v.toCharArray(), 0, length);
	}
	
	private static void writeBitString(long[] dest, int destPos, char[] src, int srcPos, int length) {
		for (int i = srcPos + length - 1, index = 0; i >= srcPos; i--, destPos++) {
			index += destPos >> LONG_ADDRESS_LINES;
			destPos &= LONG_ADDRESS_MASK;
			
			writeBit0(dest, index, destPos, src[i] == '1');
		}
	}
}
