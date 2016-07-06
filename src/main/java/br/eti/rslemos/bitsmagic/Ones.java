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
import static br.eti.rslemos.bitsmagic.Store.CHAR_DATA_MASK;
import static br.eti.rslemos.bitsmagic.Store.SHORT_DATA_MASK;

public class Ones {
	private Ones() { /* non-instantiable */ }

	public static int ones(byte x) {
		return ones(x & BYTE_DATA_MASK);
	}

	public static int ones(char x) {
		return ones(x & CHAR_DATA_MASK);
	}

	public static int ones(short x) {
		return ones(x & SHORT_DATA_MASK);
	}

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
	
	public static int ones(byte[] data, int from, int to) {
		if (from == to)
			return 0;
		
		if (to < from)
			throw new IllegalArgumentException();

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
}
