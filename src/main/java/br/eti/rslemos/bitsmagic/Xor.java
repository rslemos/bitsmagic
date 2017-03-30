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
import static br.eti.rslemos.bitsmagic.Store.LONG_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.LONG_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.LONG_DATA_LINES;
import static br.eti.rslemos.bitsmagic.Store.LONG_DATA_MASK;
import static br.eti.rslemos.bitsmagic.Store.SHORT_ADDRESS_LINES;
import static br.eti.rslemos.bitsmagic.Store.SHORT_ADDRESS_MASK;
import static br.eti.rslemos.bitsmagic.Store.SHORT_DATA_LINES;
import static br.eti.rslemos.bitsmagic.Store.SHORT_DATA_MASK;

/**
 * This class consists exclusively of static methods that xor bits over arrays 
 * of integral primitive type.
 * 
 * <p>For every method available in this class, the arguments that represent
 * offsets should always be given in bits, and are 0-based. For more 
 * information about bit mapping in arrays of integral primitive types see 
 * {@link Store} class.
 * </p>
 * <p>The general syntax for methods in this class conforms to that of 
 * {@link System#arraycopy}.
 * </p>
 * <p>Access to offlimits bits, either for reading or for writing, will throw 
 * {@code ArrayIndexOutOfBoundsException}. In this case destination will be 
 * left unchanged.
 * </p>
 * <p>In case of using the same underlying storage for both source and 
 * destination, all methods of this class behave as if copying the bits first 
 * to a temporary location, then xor'ing them over the destination.
 * </p>
 * <p>{@code NullPointerException} is thrown if either given array is 
 * {@code null}.
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
public class Xor {
	private Xor() { /* non-instantiable */ }

	/********** byte[] **********/
	
	/**
	 * Xors bits from the specified source storage, beginning at the specified 
	 * bit, onto the specified bits of the destination storage. A region 
	 * of bits is xor'ed from the source storage referenced by {@code source} 
	 * to the destination storage referenced by {@code dest}. The number of 
	 * bits xor'ed is equal to the {@code length} argument. The bits at offsets 
	 * {@code srcPos} through {@code srcPos+length-1} in the source storage are 
	 * xor'ed into positions {@code destPos} through {@code destPos+length-1}, 
	 * respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be xor'ed.
	 *
	 * @since 1.0.0
	 */
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
		
		if (dIndex[0] < sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			xorParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void xorParallelFromForwards0(byte[] source, byte[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
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

	private static void xorParallelFromBackwards0(byte[] source, byte[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = BYTE_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}

		if (dOffset[0] != 0) {
			// don't xor the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
		
		// unfortunately no fast path available
		// (though this is fast enough without masking and bit shifting)
		for(int i = dIndex[1] - 1, j = sIndex[1] - 1; i >= dIndex[0]; i--, j--)
			dest[i] ^= source[j];

		if (dOffset[0] != 0) {
			// handle "from" end specially
			sIndex[0]--;
			dIndex[0]--;
			
			final int HIGHEST_BITS = BYTE_DATA_MASK << dOffset[0];
			
			dest[dIndex[0]] ^= source[sIndex[0]] & HIGHEST_BITS;
		}
	}

	private static void xorHigherFrom0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
					
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= (source[s] << dOffset[0] - sOffset[0]) & ~(BYTE_DATA_MASK << dOffset[1]) & BYTE_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorHigherFromForwards0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
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

	private static void xorHigherFromBackwards0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
				
		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				dest[d] ^= (source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					dest[d] ^= (source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
				
				s--;
				
				dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
			}
		}

		while(--d > dIndex[0]) {
			dest[d] ^= source[s] << dOffset[0] - sOffset[0];

			--s;

			dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
		}

		dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0]; 
	}

	private static void xorLowerFrom0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> (sOffset[0] - dOffset[0]) & ~(BYTE_DATA_MASK << dOffset[1]) & BYTE_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorLowerFromForwards0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
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

	private static void xorLowerFromBackwards0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					dest[d] ^= (source[s] & ~(BYTE_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
				
				d--;
				
				dest[d] ^= source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]);
			}
		}

		while(--s > sIndex[0]) {
			dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> sOffset[0] - dOffset[0];

			--d;

			dest[d] ^= source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]);
		}
		
		dest[d] ^= (source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0];
	}

	/********** char[] **********/

	/**
	 * Xors bits from the specified source storage, beginning at the specified 
	 * bit, onto the specified bits of the destination storage. A region 
	 * of bits is xor'ed from the source storage referenced by {@code source} 
	 * to the destination storage referenced by {@code dest}. The number of 
	 * bits xor'ed is equal to the {@code length} argument. The bits at offsets 
	 * {@code srcPos} through {@code srcPos+length-1} in the source storage are 
	 * xor'ed into positions {@code destPos} through {@code destPos+length-1}, 
	 * respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be xor'ed.
	 *
	 * @since 1.0.0
	 */
	public static void xorFrom(char[] source, int srcPos, char[] dest, int destPos, int length) {
		if (length == 0)
			return;
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		int[] sIndex  = {srcPos  >> CHAR_ADDRESS_LINES, (srcPos  + length) >> CHAR_ADDRESS_LINES};
		int[] sOffset = {srcPos  & CHAR_ADDRESS_MASK,   (srcPos  + length) & CHAR_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> CHAR_ADDRESS_LINES, (destPos + length) >> CHAR_ADDRESS_LINES};
		int[] dOffset = {destPos & CHAR_ADDRESS_MASK,   (destPos + length) & CHAR_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, xor middle unchanged
			xorParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			xorHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			xorLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorParallelFrom0(char[] source, char[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword xor within word and neither offset is zero
			
			final int LOWEST_BITS_FROM = ~(CHAR_DATA_MASK << dOffset[0]);
			final int HIGHEST_BITS_TO = CHAR_DATA_MASK << dOffset[1];

			dest[dIndex[0]] ^= source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);;

			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			xorParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void xorParallelFromForwards0(char[] source, char[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = CHAR_DATA_MASK << dOffset[0];
			
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
			final int HIGHEST_BITS = CHAR_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}
	}

	private static void xorParallelFromBackwards0(char[] source, char[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = CHAR_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}

		if (dOffset[0] != 0) {
			// don't xor the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
		
		// unfortunately no fast path available
		// (though this is fast enough without masking and bit shifting)
		for(int i = dIndex[1] - 1, j = sIndex[1] - 1; i >= dIndex[0]; i--, j--)
			dest[i] ^= source[j];

		if (dOffset[0] != 0) {
			// handle "from" end specially
			sIndex[0]--;
			dIndex[0]--;
			
			final int HIGHEST_BITS = CHAR_DATA_MASK << dOffset[0];
			
			dest[dIndex[0]] ^= source[sIndex[0]] & HIGHEST_BITS;
		}
	}

	private static void xorHigherFrom0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
					
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= (source[s] << dOffset[0] - sOffset[0]) & ~(CHAR_DATA_MASK << dOffset[1]) & CHAR_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorHigherFromForwards0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0]; 

		while(++d < dIndex[1]) {
			dest[d] ^= source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);

			++s;

			dest[d] ^= source[s] << dOffset[0] - sOffset[0];
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				dest[d] ^= source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
				
				if (sOffset[1] > 0) {
					s++;
					
					dest[d] ^= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
			}
		}
	}

	private static void xorHigherFromBackwards0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
				
		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					dest[d] ^= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
				
				s--;
				
				dest[d] ^= source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
			}
		}

		while(--d > dIndex[0]) {
			dest[d] ^= source[s] << dOffset[0] - sOffset[0];

			--s;

			dest[d] ^= source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
		}

		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0]; 
	}

	private static void xorLowerFrom0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= source[s] >>> (sOffset[0] - dOffset[0]) & ~(CHAR_DATA_MASK << dOffset[1]) & CHAR_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorLowerFromForwards0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0];

		while(++s < sIndex[1]) {
			dest[d] ^= source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]);

			++d;

			dest[d] ^= source[s] >>> sOffset[0] - dOffset[0];
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				dest[d] ^= source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]);
				
				if (dOffset[1] > 0) {
					d++;
					
					dest[d] ^= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
			}
		}
	}

	private static void xorLowerFromBackwards0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					dest[d] ^= (source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
				
				d--;
				
				dest[d] ^= source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]);
			}
		}

		while(--s > sIndex[0]) {
			dest[d] ^= source[s] >>> sOffset[0] - dOffset[0];

			--d;

			dest[d] ^= source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]);
		}
		
		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0];
	}

	/********** short[] **********/
	
	/**
	 * Xors bits from the specified source storage, beginning at the specified 
	 * bit, onto the specified bits of the destination storage. A region 
	 * of bits is xor'ed from the source storage referenced by {@code source} 
	 * to the destination storage referenced by {@code dest}. The number of 
	 * bits xor'ed is equal to the {@code length} argument. The bits at offsets 
	 * {@code srcPos} through {@code srcPos+length-1} in the source storage are 
	 * xor'ed into positions {@code destPos} through {@code destPos+length-1}, 
	 * respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be xor'ed.
	 *
	 * @since 1.0.0
	 */
	public static void xorFrom(short[] source, int srcPos, short[] dest, int destPos, int length) {
		if (length == 0)
			return;
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		int[] sIndex  = {srcPos  >> SHORT_ADDRESS_LINES, (srcPos  + length) >> SHORT_ADDRESS_LINES};
		int[] sOffset = {srcPos  & SHORT_ADDRESS_MASK,   (srcPos  + length) & SHORT_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> SHORT_ADDRESS_LINES, (destPos + length) >> SHORT_ADDRESS_LINES};
		int[] dOffset = {destPos & SHORT_ADDRESS_MASK,   (destPos + length) & SHORT_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, xor middle unchanged
			xorParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			xorHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			xorLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorParallelFrom0(short[] source, short[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword xor within word and neither offset is zero
			
			final int LOWEST_BITS_FROM = ~(SHORT_DATA_MASK << dOffset[0]);
			final int HIGHEST_BITS_TO = SHORT_DATA_MASK << dOffset[1];

			dest[dIndex[0]] ^= source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);;

			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			xorParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void xorParallelFromForwards0(short[] source, short[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = SHORT_DATA_MASK << dOffset[0];
			
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
			final int HIGHEST_BITS = SHORT_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}
	}

	private static void xorParallelFromBackwards0(short[] source, short[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = SHORT_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}

		if (dOffset[0] != 0) {
			// don't xor the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
		
		// unfortunately no fast path available
		// (though this is fast enough without masking and bit shifting)
		for(int i = dIndex[1] - 1, j = sIndex[1] - 1; i >= dIndex[0]; i--, j--)
			dest[i] ^= source[j];

		if (dOffset[0] != 0) {
			// handle "from" end specially
			sIndex[0]--;
			dIndex[0]--;
			
			final int HIGHEST_BITS = SHORT_DATA_MASK << dOffset[0];
			
			dest[dIndex[0]] ^= source[sIndex[0]] & HIGHEST_BITS;
		}
	}

	private static void xorHigherFrom0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
					
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= (source[s] << dOffset[0] - sOffset[0]) & ~(SHORT_DATA_MASK << dOffset[1]) & SHORT_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorHigherFromForwards0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
		dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0]; 

		while(++d < dIndex[1]) {
			dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);

			++s;

			dest[d] ^= source[s] << dOffset[0] - sOffset[0];
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] ^= (source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
				
				if (sOffset[1] > 0) {
					s++;
					
					dest[d] ^= (source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
			}
		}
	}

	private static void xorHigherFromBackwards0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
				
		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				dest[d] ^= (source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					dest[d] ^= (source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
				
				s--;
				
				dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
			}
		}

		while(--d > dIndex[0]) {
			dest[d] ^= source[s] << dOffset[0] - sOffset[0];

			--s;

			dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
		}

		dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0]; 
	}

	private static void xorLowerFrom0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> (sOffset[0] - dOffset[0]) & ~(SHORT_DATA_MASK << dOffset[1]) & SHORT_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorLowerFromForwards0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
		dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0];

		while(++s < sIndex[1]) {
			dest[d] ^= source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]);

			++d;

			dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> sOffset[0] - dOffset[0];
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				dest[d] ^= source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]);
				
				if (dOffset[1] > 0) {
					d++;
					
					dest[d] ^= (source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
			}
		}
	}

	private static void xorLowerFromBackwards0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				dest[d] ^= (source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					dest[d] ^= (source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
				
				d--;
				
				dest[d] ^= source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]);
			}
		}

		while(--s > sIndex[0]) {
			dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> sOffset[0] - dOffset[0];

			--d;

			dest[d] ^= source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]);
		}
		
		dest[d] ^= (source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0];
	}

	/********** int[] **********/

	/**
	 * Xors bits from the specified source storage, beginning at the specified 
	 * bit, onto the specified bits of the destination storage. A region 
	 * of bits is xor'ed from the source storage referenced by {@code source} 
	 * to the destination storage referenced by {@code dest}. The number of 
	 * bits xor'ed is equal to the {@code length} argument. The bits at offsets 
	 * {@code srcPos} through {@code srcPos+length-1} in the source storage are 
	 * xor'ed into positions {@code destPos} through {@code destPos+length-1}, 
	 * respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be xor'ed.
	 *
	 * @since 1.0.0
	 */
	public static void xorFrom(int[] source, int srcPos, int[] dest, int destPos, int length) {
		if (length == 0)
			return;
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		int[] sIndex  = {srcPos  >> INT_ADDRESS_LINES, (srcPos  + length) >> INT_ADDRESS_LINES};
		int[] sOffset = {srcPos  & INT_ADDRESS_MASK,   (srcPos  + length) & INT_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> INT_ADDRESS_LINES, (destPos + length) >> INT_ADDRESS_LINES};
		int[] dOffset = {destPos & INT_ADDRESS_MASK,   (destPos + length) & INT_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, xor middle unchanged
			xorParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			xorHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			xorLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorParallelFrom0(int[] source, int[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword xor within word and neither offset is zero
			
			final int LOWEST_BITS_FROM = ~(INT_DATA_MASK << dOffset[0]);
			final int HIGHEST_BITS_TO = INT_DATA_MASK << dOffset[1];

			dest[dIndex[0]] ^= source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);;

			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			xorParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void xorParallelFromForwards0(int[] source, int[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = INT_DATA_MASK << dOffset[0];
			
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
			final int HIGHEST_BITS = INT_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}
	}

	private static void xorParallelFromBackwards0(int[] source, int[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = INT_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}

		if (dOffset[0] != 0) {
			// don't xor the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
		
		// unfortunately no fast path available
		// (though this is fast enough without masking and bit shifting)
		for(int i = dIndex[1] - 1, j = sIndex[1] - 1; i >= dIndex[0]; i--, j--)
			dest[i] ^= source[j];

		if (dOffset[0] != 0) {
			// handle "from" end specially
			sIndex[0]--;
			dIndex[0]--;
			
			final int HIGHEST_BITS = INT_DATA_MASK << dOffset[0];
			
			dest[dIndex[0]] ^= source[sIndex[0]] & HIGHEST_BITS;
		}
	}

	private static void xorHigherFrom0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
					
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= (source[s] << dOffset[0] - sOffset[0]) & ~(INT_DATA_MASK << dOffset[1]) & INT_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorHigherFromForwards0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0]; 

		while(++d < dIndex[1]) {
			dest[d] ^= source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);

			++s;

			dest[d] ^= source[s] << dOffset[0] - sOffset[0];
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				dest[d] ^= source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
				
				if (sOffset[1] > 0) {
					s++;
					
					dest[d] ^= (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
			}
		}
	}

	private static void xorHigherFromBackwards0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
				
		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				dest[d] ^= (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					dest[d] ^= (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
				
				s--;
				
				dest[d] ^= source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
			}
		}

		while(--d > dIndex[0]) {
			dest[d] ^= source[s] << dOffset[0] - sOffset[0];

			--s;

			dest[d] ^= source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
		}

		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0]; 
	}

	private static void xorLowerFrom0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= source[s] >>> (sOffset[0] - dOffset[0]) & ~(INT_DATA_MASK << dOffset[1]) & INT_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorLowerFromForwards0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0];

		while(++s < sIndex[1]) {
			dest[d] ^= source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);

			++d;

			dest[d] ^= source[s] >>> sOffset[0] - dOffset[0];
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				dest[d] ^= source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);
				
				if (dOffset[1] > 0) {
					d++;
					
					dest[d] ^= (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
			}
		}
	}

	private static void xorLowerFromBackwards0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				dest[d] ^= (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					dest[d] ^= (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
				
				d--;
				
				dest[d] ^= source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);
			}
		}

		while(--s > sIndex[0]) {
			dest[d] ^= source[s] >>> sOffset[0] - dOffset[0];

			--d;

			dest[d] ^= source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);
		}
		
		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0];
	}

	/********** long[] **********/

	/**
	 * Xors bits from the specified source storage, beginning at the specified 
	 * bit, onto the specified bits of the destination storage. A region 
	 * of bits is xor'ed from the source storage referenced by {@code source} 
	 * to the destination storage referenced by {@code dest}. The number of 
	 * bits xor'ed is equal to the {@code length} argument. The bits at offsets 
	 * {@code srcPos} through {@code srcPos+length-1} in the source storage are 
	 * xor'ed into positions {@code destPos} through {@code destPos+length-1}, 
	 * respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be xor'ed.
	 *
	 * @since 1.0.0
	 */
	public static void xorFrom(long[] source, int srcPos, long[] dest, int destPos, int length) {
		if (length == 0)
			return;
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		int[] sIndex  = {srcPos  >> LONG_ADDRESS_LINES, (srcPos  + length) >> LONG_ADDRESS_LINES};
		int[] sOffset = {srcPos  & LONG_ADDRESS_MASK,   (srcPos  + length) & LONG_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> LONG_ADDRESS_LINES, (destPos + length) >> LONG_ADDRESS_LINES};
		int[] dOffset = {destPos & LONG_ADDRESS_MASK,   (destPos + length) & LONG_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, xor middle unchanged
			xorParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			xorHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			xorLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorParallelFrom0(long[] source, long[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword xor within word and neither offset is zero
			
			final long LOWEST_BITS_FROM = ~(LONG_DATA_MASK << dOffset[0]);
			final long HIGHEST_BITS_TO = LONG_DATA_MASK << dOffset[1];

			dest[dIndex[0]] ^= source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);;

			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			xorParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void xorParallelFromForwards0(long[] source, long[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final long HIGHEST_BITS = LONG_DATA_MASK << dOffset[0];
			
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
			final long HIGHEST_BITS = LONG_DATA_MASK << dOffset[1];
			final long LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}
	}

	private static void xorParallelFromBackwards0(long[] source, long[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final long HIGHEST_BITS = LONG_DATA_MASK << dOffset[1];
			final long LOWEST_BITS = ~HIGHEST_BITS;
			
			dest[dIndex[1]] ^= source[sIndex[1]] & LOWEST_BITS;
		}

		if (dOffset[0] != 0) {
			// don't xor the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
		
		// unfortunately no fast path available
		// (though this is fast enough without masking and bit shifting)
		for(int i = dIndex[1] - 1, j = sIndex[1] - 1; i >= dIndex[0]; i--, j--)
			dest[i] ^= source[j];

		if (dOffset[0] != 0) {
			// handle "from" end specially
			sIndex[0]--;
			dIndex[0]--;
			
			final long HIGHEST_BITS = LONG_DATA_MASK << dOffset[0];
			
			dest[dIndex[0]] ^= source[sIndex[0]] & HIGHEST_BITS;
		}
	}

	private static void xorHigherFrom0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
					
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= (source[s] << dOffset[0] - sOffset[0]) & ~(LONG_DATA_MASK << dOffset[1]) & LONG_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorHigherFromForwards0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
				
		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0]; 

		while(++d < dIndex[1]) {
			dest[d] ^= source[s] >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);

			++s;

			dest[d] ^= source[s] << dOffset[0] - sOffset[0];
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(LONG_DATA_MASK << sOffset[1])) >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				dest[d] ^= source[s] >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
				
				if (sOffset[1] > 0) {
					s++;
					
					dest[d] ^= (source[s] & ~(LONG_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
			}
		}
	}

	private static void xorHigherFromBackwards0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
				
		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				dest[d] ^= (source[s] & ~(LONG_DATA_MASK << sOffset[1])) >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					dest[d] ^= (source[s] & ~(LONG_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				}
				
				s--;
				
				dest[d] ^= source[s] >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
			}
		}

		while(--d > dIndex[0]) {
			dest[d] ^= source[s] << dOffset[0] - sOffset[0];

			--s;

			dest[d] ^= source[s] >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
		}

		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0]; 
	}

	private static void xorLowerFrom0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to xor more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			dest[d] ^= source[s] >>> (sOffset[0] - dOffset[0]) & ~(LONG_DATA_MASK << dOffset[1]) & LONG_DATA_MASK << dOffset[0];
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			xorLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			xorLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void xorLowerFromForwards0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[0];
		int s = sIndex[0];
		
		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0];

		while(++s < sIndex[1]) {
			dest[d] ^= source[s] << LONG_DATA_LINES - (sOffset[0] - dOffset[0]);

			++d;

			dest[d] ^= source[s] >>> sOffset[0] - dOffset[0];
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				dest[d] ^= (source[s] & ~(LONG_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				dest[d] ^= source[s] << LONG_DATA_LINES - (sOffset[0] - dOffset[0]);
				
				if (dOffset[1] > 0) {
					d++;
					
					dest[d] ^= (source[s] & ~(LONG_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
			}
		}
	}

	private static void xorLowerFromBackwards0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				dest[d] ^= (source[s] & ~(LONG_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					dest[d] ^= (source[s] & ~(LONG_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
				}
				
				d--;
				
				dest[d] ^= source[s] << LONG_DATA_LINES - (sOffset[0] - dOffset[0]);
			}
		}

		while(--s > sIndex[0]) {
			dest[d] ^= source[s] >>> sOffset[0] - dOffset[0];

			--d;

			dest[d] ^= source[s] << LONG_DATA_LINES - (sOffset[0] - dOffset[0]);
		}
		
		dest[d] ^= source[s] >>> sOffset[0] << dOffset[0];
	}
}
