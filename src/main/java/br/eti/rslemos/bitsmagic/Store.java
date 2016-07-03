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

public class Store {
	private Store() { /* non-instantiable */ }

	/********** byte[] **********/

	private static final int BYTE_ADDRESS_LINES = 3;
	private static final int BYTE_DATA_LINES = 1 << BYTE_ADDRESS_LINES;
	private static final int BYTE_ADDRESS_MASK = ~(-1 << BYTE_ADDRESS_LINES);
	private static final int BYTE_DATA_MASK = ~(-1 << BYTE_DATA_LINES);
	
	// we expect this function to be heavily inlined
	private static int read(byte[] data, int index) {
		return index >=0 && index < data.length ? data[index] & BYTE_DATA_MASK : 0;
	}

	public static boolean readBit(byte[] data, int i) {
		int index = i >> BYTE_ADDRESS_LINES;

		if (index < 0 || index >= data.length)
			return false;
		
		int offset = i & BYTE_ADDRESS_MASK;
		return (data[index] << ~offset) < 0;
	}
	
	public static void writeBit(byte[] data, int i, boolean v) {
		int index = i >> BYTE_ADDRESS_LINES;

		if (index >= 0 && index < data.length) {
			int offset = i & BYTE_ADDRESS_MASK;
			if (v)
				data[index] |= 1 << offset;
			else
				data[index] &= ~(1 << offset);
		}
	}

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
}
