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
import static br.eti.rslemos.bitsmagic.Store.CHAR_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.CHAR_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.CHAR_DATA_LINES;
import static br.eti.rslemos.bitsmagic.Store.CHAR_DATA_MASK;
import static br.eti.rslemos.bitsmagic.Store.INT_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.INT_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.INT_DATA_LINES;
import static br.eti.rslemos.bitsmagic.Store.INT_DATA_MASK;
import static br.eti.rslemos.bitsmagic.Store.SHORT_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.SHORT_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.SHORT_DATA_LINES;
import static br.eti.rslemos.bitsmagic.Store.SHORT_DATA_MASK;

public class Copy {
	private Copy() { /* non-instantiable */ }

	/********** byte[] **********/
	
	public static void copyFrom(byte[] source, int srcPos, byte[] dest, int destPos, int length) {
		if (length == 0)
			return;
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		int[] sIndex  = {srcPos  >> BYTE_ADDRESS_LINES, (srcPos  + length) >> BYTE_ADDRESS_LINES};
		int[] sOffset = {srcPos  & BYTE_ADDRESS_MASK,   (srcPos  + length) & BYTE_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> BYTE_ADDRESS_LINES, (destPos + length) >> BYTE_ADDRESS_LINES};
		int[] dOffset = {destPos & BYTE_ADDRESS_MASK,   (destPos + length) & BYTE_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, copy middle unchanged
			copyParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			copyHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			copyLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyParallelFrom0(byte[] source, byte[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword copy within word and neither offset is zero
			
			final int LOWEST_BITS_FROM = ~(BYTE_DATA_MASK << dOffset[0]);
			final int HIGHEST_BITS_TO = BYTE_DATA_MASK << dOffset[1];

			dest[dIndex[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			dest[dIndex[0]] |= source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);;

			return;
		}
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = BYTE_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= source[sIndex[0]] & HIGHEST_BITS;
			
			// first index already taken care of
			sIndex[0]++;
			dIndex[0]++;
		}

		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = BYTE_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= source[sIndex[1]] & LOWEST_BITS;
		}
	}

	private static void copyHigherFrom0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
		if (d == dIndex[1]) {
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] &= BYTE_DATA_MASK << dOffset[1] | ~(BYTE_DATA_MASK << dOffset[0]);
			dest[d] |= (source[s] << dOffset[0] - sOffset[0]) & ~(BYTE_DATA_MASK << dOffset[1]) & BYTE_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] &= ~(BYTE_DATA_MASK << dOffset[0]);
		dest[d] |= (source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0]; 

		while(++d < dIndex[1]) {
			dest[d] &= ~(BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= (source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);

			++s;

			dest[d] &= BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= source[s] << dOffset[0] - sOffset[0];
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] &= BYTE_DATA_MASK << dOffset[1];
				dest[d] |= (source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				dest[d] &= ~(BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= (source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
				
				if (sOffset[1] > 0) {
					s++;
					
					dest[d] &= BYTE_DATA_MASK << dOffset[1] | BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= (source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
			}
		}
	}

	private static void copyLowerFrom0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
		if (s == sIndex[1]) {
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] &= BYTE_DATA_MASK << dOffset[1] | ~(BYTE_DATA_MASK << dOffset[0]);
			dest[d] |= (source[s] & BYTE_DATA_MASK) >>> (sOffset[0] - dOffset[0]) & ~(BYTE_DATA_MASK << dOffset[1]) & BYTE_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] &= ~((BYTE_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= (source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0];

		while(++s < sIndex[1]) {
			dest[d] &= BYTE_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]);

			++d;

			dest[d] &= ~(BYTE_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= (source[s] & BYTE_DATA_MASK) >>> sOffset[0] - dOffset[0];
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] &= ~(~(BYTE_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= (source[s] & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				dest[d] &= BYTE_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]);
				
				if (dOffset[1] > 0) {
					d++;
					
					dest[d] &= ~(~(BYTE_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= (source[s] & ~(BYTE_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
			}
		}
	}

	/********** char[] **********/

	public static void copyFrom(char[] source, int srcPos, char[] dest, int destPos, int length) {
		if (length == 0)
			return;
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		int[] sIndex  = {srcPos  >> CHAR_ADDRESS_LINES, (srcPos  + length) >> CHAR_ADDRESS_LINES};
		int[] sOffset = {srcPos  & CHAR_ADDRESS_MASK,   (srcPos  + length) & CHAR_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> CHAR_ADDRESS_LINES, (destPos + length) >> CHAR_ADDRESS_LINES};
		int[] dOffset = {destPos & CHAR_ADDRESS_MASK,   (destPos + length) & CHAR_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, copy middle unchanged
			copyParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			copyHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			copyLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyParallelFrom0(char[] source, char[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword copy within word and neither offset is zero
			
			final int LOWEST_BITS_FROM = ~(CHAR_DATA_MASK << dOffset[0]);
			final int HIGHEST_BITS_TO = CHAR_DATA_MASK << dOffset[1];

			dest[dIndex[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			dest[dIndex[0]] |= source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);;

			return;
		}
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = CHAR_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= source[sIndex[0]] & HIGHEST_BITS;
			
			// first index already taken care of
			sIndex[0]++;
			dIndex[0]++;
		}

		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = CHAR_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= source[sIndex[1]] & LOWEST_BITS;
		}
	}

	private static void copyHigherFrom0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
		if (d == dIndex[1]) {
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] &= CHAR_DATA_MASK << dOffset[1] | ~(CHAR_DATA_MASK << dOffset[0]);
			dest[d] |= (source[s] << dOffset[0] - sOffset[0]) & ~(CHAR_DATA_MASK << dOffset[1]) & CHAR_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] &= ~(CHAR_DATA_MASK << dOffset[0]);
		dest[d] |= source[s] >>> sOffset[0] << dOffset[0]; 

		while(++d < dIndex[1]) {
			dest[d] &= ~(CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);

			++s;

			dest[d] &= CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= source[s] << dOffset[0] - sOffset[0];
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] &= CHAR_DATA_MASK << dOffset[1];
				dest[d] |= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				dest[d] &= ~(CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
				
				if (sOffset[1] > 0) {
					s++;
					
					dest[d] &= CHAR_DATA_MASK << dOffset[1] | CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
			}
		}
	}

	private static void copyLowerFrom0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
		if (s == sIndex[1]) {
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] &= CHAR_DATA_MASK << dOffset[1] | ~(CHAR_DATA_MASK << dOffset[0]);
			dest[d] |= source[s] >>> (sOffset[0] - dOffset[0]) & ~(CHAR_DATA_MASK << dOffset[1]) & CHAR_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] &= ~((CHAR_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= source[s] >>> sOffset[0] << dOffset[0];

		while(++s < sIndex[1]) {
			dest[d] &= CHAR_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]);

			++d;

			dest[d] &= ~(CHAR_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= source[s] >>> sOffset[0] - dOffset[0];
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] &= ~(~(CHAR_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				dest[d] &= CHAR_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]);
				
				if (dOffset[1] > 0) {
					d++;
					
					dest[d] &= ~(~(CHAR_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
			}
		}
	}

	/********** short[] **********/
	
	public static void copyFrom(short[] source, int srcPos, short[] dest, int destPos, int length) {
		if (length == 0)
			return;
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		int[] sIndex  = {srcPos  >> SHORT_ADDRESS_LINES, (srcPos  + length) >> SHORT_ADDRESS_LINES};
		int[] sOffset = {srcPos  & SHORT_ADDRESS_MASK,   (srcPos  + length) & SHORT_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> SHORT_ADDRESS_LINES, (destPos + length) >> SHORT_ADDRESS_LINES};
		int[] dOffset = {destPos & SHORT_ADDRESS_MASK,   (destPos + length) & SHORT_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, copy middle unchanged
			copyParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			copyHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			copyLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyParallelFrom0(short[] source, short[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword copy within word and neither offset is zero
			
			final int LOWEST_BITS_FROM = ~(SHORT_DATA_MASK << dOffset[0]);
			final int HIGHEST_BITS_TO = SHORT_DATA_MASK << dOffset[1];

			dest[dIndex[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			dest[dIndex[0]] |= source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);;

			return;
		}
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = SHORT_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= source[sIndex[0]] & HIGHEST_BITS;
			
			// first index already taken care of
			sIndex[0]++;
			dIndex[0]++;
		}

		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = SHORT_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= source[sIndex[1]] & LOWEST_BITS;
		}
	}

	private static void copyHigherFrom0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
		if (d == dIndex[1]) {
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] &= SHORT_DATA_MASK << dOffset[1] | ~(SHORT_DATA_MASK << dOffset[0]);
			dest[d] |= (source[s] << dOffset[0] - sOffset[0]) & ~(SHORT_DATA_MASK << dOffset[1]) & SHORT_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] &= ~(SHORT_DATA_MASK << dOffset[0]);
		dest[d] |= (source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0]; 

		while(++d < dIndex[1]) {
			dest[d] &= ~(SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= (source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);

			++s;

			dest[d] &= SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= source[s] << dOffset[0] - sOffset[0];
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] &= SHORT_DATA_MASK << dOffset[1];
				dest[d] |= (source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				dest[d] &= ~(SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= (source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
				
				if (sOffset[1] > 0) {
					s++;
					
					dest[d] &= SHORT_DATA_MASK << dOffset[1] | SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= (source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
			}
		}
	}

	private static void copyLowerFrom0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
		if (s == sIndex[1]) {
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] &= SHORT_DATA_MASK << dOffset[1] | ~(SHORT_DATA_MASK << dOffset[0]);
			dest[d] |= (source[s] & SHORT_DATA_MASK) >>> (sOffset[0] - dOffset[0]) & ~(SHORT_DATA_MASK << dOffset[1]) & SHORT_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] &= ~((SHORT_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= (source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0];

		while(++s < sIndex[1]) {
			dest[d] &= SHORT_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]);

			++d;

			dest[d] &= ~(SHORT_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= (source[s] & SHORT_DATA_MASK) >>> sOffset[0] - dOffset[0];
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] &= ~(~(SHORT_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= (source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				dest[d] &= SHORT_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]);
				
				if (dOffset[1] > 0) {
					d++;
					
					dest[d] &= ~(~(SHORT_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= (source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
			}
		}
	}

	/********** int[] **********/

	public static void copyFrom(int[] source, int srcPos, int[] dest, int destPos, int length) {
		if (length == 0)
			return;
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		int[] sIndex  = {srcPos  >> INT_ADDRESS_LINES, (srcPos  + length) >> INT_ADDRESS_LINES};
		int[] sOffset = {srcPos  & INT_ADDRESS_MASK,   (srcPos  + length) & INT_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> INT_ADDRESS_LINES, (destPos + length) >> INT_ADDRESS_LINES};
		int[] dOffset = {destPos & INT_ADDRESS_MASK,   (destPos + length) & INT_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, copy middle unchanged
			copyParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			copyHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			copyLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyParallelFrom0(int[] source, int[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword copy within word and neither offset is zero
			
			final int LOWEST_BITS_FROM = ~(INT_DATA_MASK << dOffset[0]);
			final int HIGHEST_BITS_TO = INT_DATA_MASK << dOffset[1];

			dest[dIndex[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			dest[dIndex[0]] |= source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);;

			return;
		}
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = INT_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= source[sIndex[0]] & HIGHEST_BITS;
			
			// first index already taken care of
			sIndex[0]++;
			dIndex[0]++;
		}

		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = INT_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= source[sIndex[1]] & LOWEST_BITS;
		}
	}

	private static void copyHigherFrom0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
		if (d == dIndex[1]) {
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] &= INT_DATA_MASK << dOffset[1] | ~(INT_DATA_MASK << dOffset[0]);
			dest[d] |= (source[s] << dOffset[0] - sOffset[0]) & ~(INT_DATA_MASK << dOffset[1]) & INT_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] &= ~(INT_DATA_MASK << dOffset[0]);
		dest[d] |= source[s] >>> sOffset[0] << dOffset[0]; 

		while(++d < dIndex[1]) {
			dest[d] &= ~(INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);

			++s;

			dest[d] &= INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= source[s] << dOffset[0] - sOffset[0];
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] &= INT_DATA_MASK << dOffset[1];
				dest[d] |= (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				dest[d] &= ~(INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
				
				if (sOffset[1] > 0) {
					s++;
					
					dest[d] &= INT_DATA_MASK << dOffset[1] | INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
			}
		}
	}

	private static void copyLowerFrom0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
		if (s == sIndex[1]) {
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] &= INT_DATA_MASK << dOffset[1] | ~(INT_DATA_MASK << dOffset[0]);
			dest[d] |= source[s] >>> (sOffset[0] - dOffset[0]) & ~(INT_DATA_MASK << dOffset[1]) & INT_DATA_MASK << dOffset[0];
			return;
		}
		
		dest[d] &= ~((INT_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= source[s] >>> sOffset[0] << dOffset[0];

		while(++s < sIndex[1]) {
			dest[d] &= INT_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);

			++d;

			dest[d] &= ~(INT_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= source[s] >>> sOffset[0] - dOffset[0];
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] &= ~(~(INT_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				dest[d] &= INT_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);
				
				if (dOffset[1] > 0) {
					d++;
					
					dest[d] &= ~(~(INT_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
			}
		}
	}
}
