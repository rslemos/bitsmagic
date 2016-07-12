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
import static br.eti.rslemos.bitsmagic.Store.BYTE_DATA_LINES;
import static br.eti.rslemos.bitsmagic.Store.BYTE_DATA_MASK;

public class Xor {
	private Xor() { /* non-instantiable */ }

	/********** byte[] **********/
	
	public static void xorFrom(byte[] source, int srcPos, byte[] dest, int destPos, int length) {
		if (length == 0)
			return;
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		int[] sIndex  = {srcPos  >> BYTE_ADDRESS_LINES, (srcPos  + length) >> BYTE_ADDRESS_LINES};
		int[] sOffset = {srcPos  & BYTE_ADDRESS_MASK,   (srcPos  + length) & BYTE_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> BYTE_ADDRESS_LINES, (destPos + length) >> BYTE_ADDRESS_LINES};
		int[] dOffset = {destPos & BYTE_ADDRESS_MASK,   (destPos + length) & BYTE_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, xor middle unchanged
			xorParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			xorHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			xorLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorParallelFrom0(byte[] source, byte[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword xor within word and neither offset is zero
			
			final int LOWEST_BITS_FROM = ~(BYTE_DATA_MASK << dOffset[0]);
			final int HIGHEST_BITS_TO = BYTE_DATA_MASK << dOffset[1];

			dest[dIndex[0]] ^= source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);;

			return;
		}
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = BYTE_DATA_MASK << dOffset[0];
			
			dest[dIndex[0]] ^= source[sIndex[0]] & HIGHEST_BITS;
			
			// first index already taken care of
			sIndex[0]++;
			dIndex[0]++;
		}

		// unfortunately no fast path available
		// (though this is fast enough without masking and bit shifting)
		for(int i = dIndex[0], j = sIndex[0]; i < dIndex[1]; i++, j++)
			dest[i] ^= source[j];

		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = BYTE_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}
	}

	private static void xorHigherFrom0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
		if (d == dIndex[1]) {
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= (source[s] << dOffset[0] - sOffset[0]) & ~(BYTE_DATA_MASK << dOffset[1]) & BYTE_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0]; 

		while(++d < dIndex[1]) {
			dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);

			++s;

			dest[d] ^= source[s] << dOffset[0] - sOffset[0];
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] ^= (source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
				
				if (sOffset[1] > 0) {
					s++;
					
					dest[d] ^= (source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
			}
		}
	}

	private static void xorLowerFrom0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
		if (s == sIndex[1]) {
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> (sOffset[0] - dOffset[0]) & ~(BYTE_DATA_MASK << dOffset[1]) & BYTE_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0];

		while(++s < sIndex[1]) {
			dest[d] ^= source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]);

			++d;

			dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> sOffset[0] - dOffset[0];
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				dest[d] ^= source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]);

				if (dOffset[1] > 0) {
					d++;
					
					dest[d] ^= (source[s] & ~(BYTE_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
			}
		}
	}
}
