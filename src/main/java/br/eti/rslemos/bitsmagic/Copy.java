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

import static br.eti.rslemos.bitsmagic.IntRef.byRef;
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
 * This class consists exclusively of static methods that copy bits over arrays 
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
 * <p>For {@link #copyFrom} methods, touching any offlimits bits throws 
 * {@code ArrayIndexOutOfBoundsException}, destination being left unchanged.
 * </p>
 * <p>For {@link #safeCopyFrom} methods, offlimits bits are hardwired to 0: 
 * they always read as 0, and any value written to them is discarded. Those 
 * methods should never throw {@code ArrayIndexOutOfBoundsException}.
 * </p>
 * <p>In case of using the same underlying storage for both source and 
 * destination, all methods of this class behave as if copying the bits first 
 * to a temporary location, then writing them to the destination.
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
public class Copy {
	private Copy() { /* non-instantiable */ }

	static boolean checkSafeIndices(int srcPos, int destPos, int length, int maxSrc, int maxDest) {
		if (srcPos < 0 || srcPos > maxSrc)
			throw new ArrayIndexOutOfBoundsException(srcPos);
		
		if (destPos < 0 || destPos > maxDest)
			throw new ArrayIndexOutOfBoundsException(destPos);
		
		if (srcPos + length < 0 || srcPos + length > maxSrc)
			throw new ArrayIndexOutOfBoundsException(srcPos + length);
		
		if (destPos + length < 0 || destPos + length > maxDest)
			throw new ArrayIndexOutOfBoundsException(destPos + length);
		
		if (length < 0)
			throw new IllegalArgumentException();
		
		return length > 0;
	}
	
	private static boolean prepareSafeCopy(IntRef srcPos, IntRef destPos, IntRef length, IntRef fillLow, IntRef fillHigh, final int maxDest, final int maxSource) {
		if (length.i == 0)
			return false;
		
		if (length.i < 0)
			throw new IllegalArgumentException();
		
		if (!(destPos.i < maxDest && destPos.i + length.i > 0))
			return false;

		// fix destination starting point out of range 
		if (destPos.i < 0) {
			length.i -= -destPos.i;
			srcPos.i += -destPos.i;
			destPos.i += -destPos.i;
		}
		
		// fix destination ending point out of range
		if (destPos.i + length.i > maxDest) {
			length.i -= (destPos.i + length.i) - maxDest;
		}
		
		if (!(srcPos.i < maxSource && srcPos.i + length.i > 0)) {
			fillHigh.i = length.i;
			length.i = 0;
			srcPos.i = 0;
			
			return true;
		}
		
		// fix source starting point out of range
		if (srcPos.i < 0) {
			fillLow.i = -srcPos.i;
			
			length.i -= -srcPos.i;
			destPos.i += -srcPos.i;
			srcPos.i += -srcPos.i;
		}
		
		// fix source ending point out of range
		if (srcPos.i + length.i > maxSource) {
			fillHigh.i = (srcPos.i + length.i) - maxSource;
			length.i -= (srcPos.i + length.i) - maxSource;
		}
		
		return true;
	}

	/********** byte[] **********/
	
	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 * 
	 * <p>Offlimits bits are read as 0, and discarded when written to.
	 * </p>
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void safeCopyFrom(byte[] source, int srcPos, byte[] dest, int destPos, int length) {
		safeCopyFrom0(source, byRef(srcPos), dest, byRef(destPos), byRef(length));
	}

	private static void safeCopyFrom0(byte[] source, IntRef srcPos, byte[] dest, IntRef destPos, IntRef length) {
		IntRef fillLow = byRef(0);
		IntRef fillHigh = byRef(0);
		if (!prepareSafeCopy(srcPos, destPos, length, fillLow, fillHigh, dest.length << BYTE_ADDRESS_LINES, source.length << BYTE_ADDRESS_LINES))
			return;
		
		copyFrom(source, srcPos.i, dest, destPos.i, length.i);
		Store.fill(dest, destPos.i + length.i, destPos.i + length.i + fillHigh.i, false);
		Store.fill(dest, destPos.i - fillLow.i, destPos.i, false);
	}

	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void copyFrom(byte[] source, int srcPos, byte[] dest, int destPos, int length) {
		if (!checkSafeIndices(srcPos, destPos, length, source.length << BYTE_ADDRESS_LINES, dest.length << BYTE_ADDRESS_LINES))
			return;
		
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

			byte save = (byte) (source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO));
			dest[dIndex[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			dest[dIndex[0]] |= save;

			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			copyParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void copyParallelFromForwards0(byte[] source, byte[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		byte save;
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = BYTE_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = (byte) (source[sIndex[0]] & HIGHEST_BITS);
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
			
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
			
			save = (byte) (source[sIndex[1]] & LOWEST_BITS);
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
		}
	}

	private static void copyParallelFromBackwards0(byte[] source, byte[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		byte save;
		
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = BYTE_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = (byte) (source[sIndex[1]] & LOWEST_BITS);
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
			
		}

		if (dOffset[0] != 0) {
			// don't copy the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
		
		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[0] != 0) {
			// handle "from" end specially
			dIndex[0]--;
			sIndex[0]--;
			
			final int HIGHEST_BITS = BYTE_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = (byte) (source[sIndex[0]] & HIGHEST_BITS);
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
		}
	}

	private static void copyHigherFrom0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			byte save = (byte) ((source[s] << dOffset[0] - sOffset[0]) & ~(BYTE_DATA_MASK << dOffset[1]) & BYTE_DATA_MASK << dOffset[0]);
			dest[d] &= BYTE_DATA_MASK << dOffset[1] | ~(BYTE_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}
	
	private static void copyHigherFromForwards0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		byte save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = (byte) ((source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~(BYTE_DATA_MASK << dOffset[0]);
		dest[d] |= save; 

		while(++d < dIndex[1]) {
			save = (byte) ((source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] &= ~(BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;

			++s;

			save = (byte) (source[s] << dOffset[0] - sOffset[0]);
			dest[d] &= BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				save = (byte) ((source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= BYTE_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				save = (byte) ((source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= ~(BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
				
				if (sOffset[1] > 0) {
					s++;
					
					save = (byte) ((source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
					dest[d] &= BYTE_DATA_MASK << dOffset[1] | BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
			}
		}
	}

	private static void copyHigherFromBackwards0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		byte save;
		
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				save = (byte) ((source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= BYTE_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					save = (byte) ((source[s] & BYTE_DATA_MASK & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
					dest[d] &= BYTE_DATA_MASK << dOffset[1] | BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
				
				s--;
				
				save = (byte) ((source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= ~(BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
			}
		}

		while(--d > dIndex[0]) {
			save = (byte) (source[s] << dOffset[0] - sOffset[0]);
			dest[d] &= BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;

			--s;

			save = (byte) ((source[s] & BYTE_DATA_MASK) >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] &= ~(BYTE_DATA_MASK >>> BYTE_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;
		}

		save = (byte) ((source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~(BYTE_DATA_MASK << dOffset[0]);
		dest[d] |= save; 
	}
	
	private static void copyLowerFrom0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			byte save = (byte) ((source[s] & BYTE_DATA_MASK) >>> (sOffset[0] - dOffset[0]) & ~(BYTE_DATA_MASK << dOffset[1]) & BYTE_DATA_MASK << dOffset[0]);
			dest[d] &= BYTE_DATA_MASK << dOffset[1] | ~(BYTE_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}
	
	private static void copyLowerFromForwards0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		byte save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = (byte) ((source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~((BYTE_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;

		while(++s < sIndex[1]) {
			save = (byte) (source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]));
			dest[d] &= BYTE_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;

			++d;

			save = (byte) ((source[s] & BYTE_DATA_MASK) >>> sOffset[0] - dOffset[0]);
			dest[d] &= ~(BYTE_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				save = (byte) ((source[s] & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
				dest[d] &= ~(~(BYTE_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				save = (byte) (source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]));
				dest[d] &= BYTE_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
				
				if (dOffset[1] > 0) {
					d++;
					
					save = (byte) ((source[s] & ~(BYTE_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0]);
					dest[d] &= ~(~(BYTE_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
			}
		}
	}

	private static void copyLowerFromBackwards0(byte[] source, byte[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		byte save;
		
		int d = dIndex[1];
		int s = sIndex[1];

		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				save = (byte) ((source[s] & ~(BYTE_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
				dest[d] &= ~(~(BYTE_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					save = (byte) ((source[s] & ~(BYTE_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0]);
					dest[d] &= ~(~(BYTE_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
				
				d--;
				
				save = (byte) (source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]));
				dest[d] &= BYTE_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
			}
		}

		while(--s > sIndex[0]) {
			save = (byte) ((source[s] & BYTE_DATA_MASK) >>> sOffset[0] - dOffset[0]);
			dest[d] &= ~(BYTE_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;

			--d;

			save = (byte) (source[s] << BYTE_DATA_LINES - (sOffset[0] - dOffset[0]));
			dest[d] &= BYTE_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;
		}
		
		save = (byte) ((source[s] & BYTE_DATA_MASK) >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~((BYTE_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;
	}

	/********** char[] **********/

	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 * 
	 * <p>Offlimits bits are read as 0, and discarded when written to.
	 * </p>
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void safeCopyFrom(char[] source, int srcPos, char[] dest, int destPos, int length) {
		safeCopyFrom0(source, byRef(srcPos), dest, byRef(destPos), byRef(length));
	}

	private static void safeCopyFrom0(char[] source, IntRef srcPos, char[] dest, IntRef destPos, IntRef length) {
		IntRef fillLow = byRef(0);
		IntRef fillHigh = byRef(0);
		if (!prepareSafeCopy(srcPos, destPos, length, fillLow, fillHigh, dest.length << CHAR_ADDRESS_LINES, source.length << CHAR_ADDRESS_LINES))
			return;
		
		copyFrom(source, srcPos.i, dest, destPos.i, length.i);
		Store.fill(dest, destPos.i + length.i, destPos.i + length.i + fillHigh.i, false);
		Store.fill(dest, destPos.i - fillLow.i, destPos.i, false);
	}

	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void copyFrom(char[] source, int srcPos, char[] dest, int destPos, int length) {
		if (!checkSafeIndices(srcPos, destPos, length, source.length << CHAR_ADDRESS_LINES, dest.length << CHAR_ADDRESS_LINES))
			return;
		
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

			char save = (char) (source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO));
			dest[dIndex[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			dest[dIndex[0]] |= save;

			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			copyParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void copyParallelFromForwards0(char[] source, char[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		char save;
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = CHAR_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = (char) (source[sIndex[0]] & HIGHEST_BITS);
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
			
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
			
			save = (char) (source[sIndex[1]] & LOWEST_BITS);
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
		}
	}

	private static void copyParallelFromBackwards0(char[] source, char[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		char save;
		
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = CHAR_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = (char) (source[sIndex[1]] & LOWEST_BITS);
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
		}
		
		if (dOffset[0] != 0) {
			// don't copy the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
		
		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[0] != 0) {
			// handle "from" end specially
			sIndex[0]--;
			dIndex[0]--;
			
			final int HIGHEST_BITS = CHAR_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = (char) (source[sIndex[0]] & HIGHEST_BITS);
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
		}

	}

	private static void copyHigherFrom0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
					
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			char save = (char) ((source[s] << dOffset[0] - sOffset[0]) & ~(CHAR_DATA_MASK << dOffset[1]) & CHAR_DATA_MASK << dOffset[0]);
			dest[d] &= CHAR_DATA_MASK << dOffset[1] | ~(CHAR_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyHigherFromForwards0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		char save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = (char) (source[s] >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~(CHAR_DATA_MASK << dOffset[0]);
		dest[d] |= save; 

		while(++d < dIndex[1]) {
			save = (char) (source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] &= ~(CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;

			++s;

			save = (char) (source[s] << dOffset[0] - sOffset[0]);
			dest[d] &= CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				save = (char) ((source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= CHAR_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				save = (char) (source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= ~(CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
				
				if (sOffset[1] > 0) {
					s++;
					
					save = (char) ((source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
					dest[d] &= CHAR_DATA_MASK << dOffset[1] | CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
			}
		}
	}

	private static void copyHigherFromBackwards0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		char save;
		
		int d = dIndex[1];
		int s = sIndex[1];

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				save = (char) ((source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= CHAR_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					save = (char) ((source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
					dest[d] &= CHAR_DATA_MASK << dOffset[1] | CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
				
				s--;
				
				save = (char) (source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= ~(CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
			}
		}
		
		while(--d > dIndex[0]) {
			save = (char) (source[s] << dOffset[0] - sOffset[0]);
			dest[d] &= CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;

			--s;

			save = (char) (source[s] >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] &= ~(CHAR_DATA_MASK >>> CHAR_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;
		}

		save = (char) (source[s] >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~(CHAR_DATA_MASK << dOffset[0]);
		dest[d] |= save; 
	}

	private static void copyLowerFrom0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			char save = (char) (source[s] >>> (sOffset[0] - dOffset[0]) & ~(CHAR_DATA_MASK << dOffset[1]) & CHAR_DATA_MASK << dOffset[0]);
			dest[d] &= CHAR_DATA_MASK << dOffset[1] | ~(CHAR_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyLowerFromForwards0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		char save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = (char) (source[s] >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~((CHAR_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;

		while(++s < sIndex[1]) {
			save = (char) (source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]));
			dest[d] &= CHAR_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;

			++d;

			save = (char) (source[s] >>> sOffset[0] - dOffset[0]);
			dest[d] &= ~(CHAR_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				save = (char) ((source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
				dest[d] &= ~(~(CHAR_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				save = (char) (source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]));
				dest[d] &= CHAR_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
				
				if (dOffset[1] > 0) {
					d++;
					
					save = (char) ((source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0]);
					dest[d] &= ~(~(CHAR_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
			}
		}
	}

	private static void copyLowerFromBackwards0(char[] source, char[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		char save;
		
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				save = (char) ((source[s] & ~(CHAR_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
				dest[d] &= ~(~(CHAR_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					save = (char) ((source[s] & ~(CHAR_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0]);
					dest[d] &= ~(~(CHAR_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
				
				d--;
				
				save = (char) (source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]));
				dest[d] &= CHAR_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
			}
		}

		while(--s > sIndex[0]) {
			save = (char) (source[s] >>> sOffset[0] - dOffset[0]);
			dest[d] &= ~(CHAR_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;

			--d;

			save = (char) (source[s] << CHAR_DATA_LINES - (sOffset[0] - dOffset[0]));
			dest[d] &= CHAR_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;
		}
		
		save = (char) (source[s] >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~((CHAR_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;
	}

	/********** short[] **********/
	
	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 * 
	 * <p>Offlimits bits are read as 0, and discarded when written to.
	 * </p>
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void safeCopyFrom(short[] source, int srcPos, short[] dest, int destPos, int length) {
		safeCopyFrom0(source, byRef(srcPos), dest, byRef(destPos), byRef(length));
	}

	private static void safeCopyFrom0(short[] source, IntRef srcPos, short[] dest, IntRef destPos, IntRef length) {
		IntRef fillLow = byRef(0);
		IntRef fillHigh = byRef(0);
		if (!prepareSafeCopy(srcPos, destPos, length, fillLow, fillHigh, dest.length << SHORT_ADDRESS_LINES, source.length << SHORT_ADDRESS_LINES))
			return;
		
		copyFrom(source, srcPos.i, dest, destPos.i, length.i);
		Store.fill(dest, destPos.i + length.i, destPos.i + length.i + fillHigh.i, false);
		Store.fill(dest, destPos.i - fillLow.i, destPos.i, false);
	}

	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void copyFrom(short[] source, int srcPos, short[] dest, int destPos, int length) {
		if (!checkSafeIndices(srcPos, destPos, length, source.length << SHORT_ADDRESS_LINES, dest.length << SHORT_ADDRESS_LINES))
			return;
		
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

			short save = (short) (source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO));
			dest[dIndex[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			dest[dIndex[0]] |= save;

			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			copyParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void copyParallelFromForwards0(short[] source, short[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		short save;
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = SHORT_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = (short) (source[sIndex[0]] & HIGHEST_BITS);
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
			
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
			
			save = (short) (source[sIndex[1]] & LOWEST_BITS);
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
		}
	}

	private static void copyParallelFromBackwards0(short[] source, short[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		short save;
		
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = SHORT_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = (short) (source[sIndex[1]] & LOWEST_BITS);
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
		}
		
		if (dOffset[0] != 0) {
			// don't copy the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
				
		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[0] != 0) {
			// handle "from" end specially
			sIndex[0]--;
			dIndex[0]--;
			
			final int HIGHEST_BITS = SHORT_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = (short) (source[sIndex[0]] & HIGHEST_BITS);
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
			
		}
	}

	private static void copyHigherFrom0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
					
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			short save = (short) ((source[s] << dOffset[0] - sOffset[0]) & ~(SHORT_DATA_MASK << dOffset[1]) & SHORT_DATA_MASK << dOffset[0]);
			dest[d] &= SHORT_DATA_MASK << dOffset[1] | ~(SHORT_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyHigherFromForwards0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		short save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = (short) ((source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~(SHORT_DATA_MASK << dOffset[0]);
		dest[d] |= save;

		while(++d < dIndex[1]) {
			save = (short) ((source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] &= ~(SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;

			++s;

			save = (short) (source[s] << dOffset[0] - sOffset[0]);
			dest[d] &= SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				save = (short) ((source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= SHORT_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				save = (short) ((source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= ~(SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
				
				if (sOffset[1] > 0) {
					s++;
					
					save = (short) ((source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
					dest[d] &= SHORT_DATA_MASK << dOffset[1] | SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
			}
		}
	}

	private static void copyHigherFromBackwards0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		short save;
		
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				save = (short) ((source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= SHORT_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					save = (short) ((source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
					dest[d] &= SHORT_DATA_MASK << dOffset[1] | SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
				
				s--;
				
				save = (short) ((source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] &= ~(SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
			}
		}

		while(--d > dIndex[0]) {
			save = (short) (source[s] << dOffset[0] - sOffset[0]);
			dest[d] &= SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;

			--s;

			save = (short) ((source[s] & SHORT_DATA_MASK) >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] &= ~(SHORT_DATA_MASK >>> SHORT_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;
		}

		save = (short) ((source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~(SHORT_DATA_MASK << dOffset[0]);
		dest[d] |= save;
	}

	private static void copyLowerFrom0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			short save = (short) ((source[s] & SHORT_DATA_MASK) >>> (sOffset[0] - dOffset[0]) & ~(SHORT_DATA_MASK << dOffset[1]) & SHORT_DATA_MASK << dOffset[0]);
			dest[d] &= SHORT_DATA_MASK << dOffset[1] | ~(SHORT_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyLowerFromForwards0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		short save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = (short) ((source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~((SHORT_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;

		while(++s < sIndex[1]) {
			save = (short) (source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]));
			dest[d] &= SHORT_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;

			++d;

			save = (short) ((source[s] & SHORT_DATA_MASK) >>> sOffset[0] - dOffset[0]);
			dest[d] &= ~(SHORT_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				save = (short) ((source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
				dest[d] &= ~(~(SHORT_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				save = (short) (source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]));
				dest[d] &= SHORT_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
				
				if (dOffset[1] > 0) {
					d++;
					
					save = (short) ((source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0]);
					dest[d] &= ~(~(SHORT_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
			}
		}
	}

	private static void copyLowerFromBackwards0(short[] source, short[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		short save;
		
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				save = (short) ((source[s] & ~(SHORT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1]);
				dest[d] &= ~(~(SHORT_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					save = (short) ((source[s] & SHORT_DATA_MASK & ~(SHORT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0]);
					dest[d] &= ~(~(SHORT_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
				
				d--;
				
				save = (short) (source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]));
				dest[d] &= SHORT_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
			}
		}

		while(--s > sIndex[0]) {
			save = (short) ((source[s] & SHORT_DATA_MASK) >>> sOffset[0] - dOffset[0]);
			dest[d] &= ~(SHORT_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;

			--d;

			save = (short) (source[s] << SHORT_DATA_LINES - (sOffset[0] - dOffset[0]));
			dest[d] &= SHORT_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;
		}
		
		save = (short) ((source[s] & SHORT_DATA_MASK) >>> sOffset[0] << dOffset[0]);
		dest[d] &= ~((SHORT_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;
	}

	/********** int[] **********/

	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 * 
	 * <p>Offlimits bits are read as 0, and discarded when written to.
	 * </p>
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void safeCopyFrom(int[] source, int srcPos, int[] dest, int destPos, int length) {
		safeCopyFrom0(source, byRef(srcPos), dest, byRef(destPos), byRef(length));
	}

	private static void safeCopyFrom0(int[] source, IntRef srcPos, int[] dest, IntRef destPos, IntRef length) {
		IntRef fillLow = byRef(0);
		IntRef fillHigh = byRef(0);
		if (!prepareSafeCopy(srcPos, destPos, length, fillLow, fillHigh, dest.length << INT_ADDRESS_LINES, source.length << INT_ADDRESS_LINES))
			return;
		
		copyFrom(source, srcPos.i, dest, destPos.i, length.i);
		Store.fill(dest, destPos.i + length.i, destPos.i + length.i + fillHigh.i, false);
		Store.fill(dest, destPos.i - fillLow.i, destPos.i, false);
	}

	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void copyFrom(int[] source, int srcPos, int[] dest, int destPos, int length) {
		if (!checkSafeIndices(srcPos, destPos, length, source.length << INT_ADDRESS_LINES, dest.length << INT_ADDRESS_LINES))
			return;
		
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

			int save = source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);
			dest[dIndex[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			dest[dIndex[0]] |= save;

			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			copyParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void copyParallelFromForwards0(int[] source, int[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		int save;
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final int HIGHEST_BITS = INT_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = source[sIndex[0]] & HIGHEST_BITS;
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
			
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
			
			save = source[sIndex[1]] & LOWEST_BITS;
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
		}
	}

	private static void copyParallelFromBackwards0(int[] source, int[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		int save;
		
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final int HIGHEST_BITS = INT_DATA_MASK << dOffset[1];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = source[sIndex[1]] & LOWEST_BITS;
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
		}

		if (dOffset[0] != 0) {
			// don't copy the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
		
		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[0] != 0) {
			// handle "from" end specially
			sIndex[0]--;
			dIndex[0]--;
			
			final int HIGHEST_BITS = INT_DATA_MASK << dOffset[0];
			final int LOWEST_BITS = ~HIGHEST_BITS;
			
			save = source[sIndex[0]] & HIGHEST_BITS;
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
		}
	}

	private static void copyHigherFrom0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
					
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			int save = (source[s] << dOffset[0] - sOffset[0]) & ~(INT_DATA_MASK << dOffset[1]) & INT_DATA_MASK << dOffset[0];
			dest[d] &= INT_DATA_MASK << dOffset[1] | ~(INT_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyHigherFromForwards0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		int save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = source[s] >>> sOffset[0] << dOffset[0];
		dest[d] &= ~(INT_DATA_MASK << dOffset[0]);
		dest[d] |= save; 

		while(++d < dIndex[1]) {
			save = source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] &= ~(INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;

			++s;

			save = source[s] << dOffset[0] - sOffset[0];
			dest[d] &= INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				save = (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
				dest[d] &= INT_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				save = source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
				dest[d] &= ~(INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
				
				if (sOffset[1] > 0) {
					s++;
					
					save = (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
					dest[d] &= INT_DATA_MASK << dOffset[1] | INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
			}
		}
	}

	private static void copyHigherFromBackwards0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		int save;
		
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				save = (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
				dest[d] &= INT_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					save = (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
					dest[d] &= INT_DATA_MASK << dOffset[1] | INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
				
				s--;
				
				save = source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
				dest[d] &= ~(INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
			}
		}

		while(--d > dIndex[0]) {
			save = source[s] << dOffset[0] - sOffset[0];
			dest[d] &= INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;

			--s;

			save = source[s] >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] &= ~(INT_DATA_MASK >>> INT_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;
		}

		save = source[s] >>> sOffset[0] << dOffset[0];
		dest[d] &= ~(INT_DATA_MASK << dOffset[0]);
		dest[d] |= save; 
	}

	private static void copyLowerFrom0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			int save = source[s] >>> (sOffset[0] - dOffset[0]) & ~(INT_DATA_MASK << dOffset[1]) & INT_DATA_MASK << dOffset[0];
			dest[d] &= INT_DATA_MASK << dOffset[1] | ~(INT_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyLowerFromForwards0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		int save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = source[s] >>> sOffset[0] << dOffset[0];
		dest[d] &= ~((INT_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;

		while(++s < sIndex[1]) {
			save = source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);
			dest[d] &= INT_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;

			++d;

			save = source[s] >>> sOffset[0] - dOffset[0];
			dest[d] &= ~(INT_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				save = (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				dest[d] &= ~(~(INT_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				save = source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);
				dest[d] &= INT_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
				
				if (dOffset[1] > 0) {
					d++;
					
					save = (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
					dest[d] &= ~(~(INT_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
			}
		}
	}

	private static void copyLowerFromBackwards0(int[] source, int[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		int save;
		
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				save = (source[s] & ~(INT_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				dest[d] &= ~(~(INT_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					save = (source[s] & ~(INT_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
					dest[d] &= ~(~(INT_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
				
				d--;
				
				save = source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);
				dest[d] &= INT_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
			}
		}

		while(--s > sIndex[0]) {
			save = source[s] >>> sOffset[0] - dOffset[0];
			dest[d] &= ~(INT_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;

			--d;

			save = source[s] << INT_DATA_LINES - (sOffset[0] - dOffset[0]);
			dest[d] &= INT_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;
		}
		
		save = source[s] >>> sOffset[0] << dOffset[0];
		dest[d] &= ~((INT_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;
	}

	/********** long[] **********/

	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 * 
	 * <p>Offlimits bits are read as 0, and discarded when written to.
	 * </p>
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void safeCopyFrom(long[] source, int srcPos, long[] dest, int destPos, int length) {
		safeCopyFrom0(source, byRef(srcPos), dest, byRef(destPos), byRef(length));
	}

	private static void safeCopyFrom0(long[] source, IntRef srcPos, long[] dest, IntRef destPos, IntRef length) {
		IntRef fillLow = byRef(0);
		IntRef fillHigh = byRef(0);
		if (!prepareSafeCopy(srcPos, destPos, length, fillLow, fillHigh, dest.length << LONG_ADDRESS_LINES, source.length << LONG_ADDRESS_LINES))
			return;
		
		copyFrom(source, srcPos.i, dest, destPos.i, length.i);
		Store.fill(dest, destPos.i + length.i, destPos.i + length.i + fillHigh.i, false);
		Store.fill(dest, destPos.i - fillLow.i, destPos.i, false);
	}

	/**
	 * Copies bits from the specified source storage, beginning at the 
	 * specified bit, to the specified bits of the destination storage. A 
	 * region of bits is copied from the source storage referenced by 
	 * {@code source} to the destination storage referenced by {@code dest}. 
	 * The number of bits copied is equal to the {@code length} argument. The 
	 * bits at offsets {@code srcPos} through {@code srcPos+length-1} in the 
	 * source storage are copied into positions {@code destPos} through 
	 * {@code destPos+length-1}, respectively, of the destination storage.
	 *
	 * @param source the source storage.
	 * @param srcPos starting bit in the source storage.
	 * @param dest the destination storage.
	 * @param destPos starting bit in the destination storage.
	 * @param length the number of bits to be copied.
	 *
	 * @since 1.0.0
	 */
	public static void copyFrom(long[] source, int srcPos, long[] dest, int destPos, int length) {
		if (!checkSafeIndices(srcPos, destPos, length, source.length << LONG_ADDRESS_LINES, dest.length << LONG_ADDRESS_LINES))
			return;
		
		int[] sIndex  = {srcPos  >> LONG_ADDRESS_LINES, (srcPos  + length) >> LONG_ADDRESS_LINES};
		int[] sOffset = {srcPos  & LONG_ADDRESS_MASK,   (srcPos  + length) & LONG_ADDRESS_MASK  };
		
		int[] dIndex  = {destPos >> LONG_ADDRESS_LINES, (destPos + length) >> LONG_ADDRESS_LINES};
		int[] dOffset = {destPos & LONG_ADDRESS_MASK,   (destPos + length) & LONG_ADDRESS_MASK  };
		
		if (sOffset[0] == dOffset[0])
			// FAST PATH: handle both ends specially, copy middle unchanged
			copyParallelFrom0(source, dest, sIndex, dIndex, dOffset);
		else if (sOffset[0] < dOffset[0])
			copyHigherFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else /* if (sOffset[0] > dOffset[0]) */
			copyLowerFrom0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyParallelFrom0(long[] source, long[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		if (dIndex[1] == dIndex[0]) {
			// special case: subword copy within word and neither offset is zero
			
			final long LOWEST_BITS_FROM = ~(LONG_DATA_MASK << dOffset[0]);
			final long HIGHEST_BITS_TO = LONG_DATA_MASK << dOffset[1];

			long save = source[sIndex[0]] & ~(LOWEST_BITS_FROM | HIGHEST_BITS_TO);
			dest[dIndex[0]] &= LOWEST_BITS_FROM | HIGHEST_BITS_TO;
			dest[dIndex[0]] |= save;

			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyParallelFromForwards0(source, dest, sIndex, dIndex, dOffset);
		else
			copyParallelFromBackwards0(source, dest, sIndex, dIndex, dOffset);
	}

	private static void copyParallelFromForwards0(long[] source, long[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		long save;
		
		if (dOffset[0] != 0) {
			// handle "from" end specially
			
			final long HIGHEST_BITS = LONG_DATA_MASK << dOffset[0];
			final long LOWEST_BITS = ~HIGHEST_BITS;
			
			save = source[sIndex[0]] & HIGHEST_BITS;
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
			
			// first index already taken care of
			sIndex[0]++;
			dIndex[0]++;
		}

		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[1] != 0) {
			// handle "to" end specially
			final long HIGHEST_BITS = LONG_DATA_MASK << dOffset[1];
			final long LOWEST_BITS = ~HIGHEST_BITS;
			
			save = source[sIndex[1]] & LOWEST_BITS;
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
		}
	}

	private static void copyParallelFromBackwards0(long[] source, long[] dest, int[] sIndex, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		long save;
		
		if (dOffset[1] != 0) {
			// handle "to" end specially
			final long HIGHEST_BITS = LONG_DATA_MASK << dOffset[1];
			final long LOWEST_BITS = ~HIGHEST_BITS;
			
			save = source[sIndex[1]] & LOWEST_BITS;
			dest[dIndex[1]] &= HIGHEST_BITS;
			dest[dIndex[1]] |= save;
		}

		if (dOffset[0] != 0) {
			// don't copy the first index if partial
			dIndex[0]++;
			sIndex[0]++;
		}
		
		if (dIndex[1] > dIndex[0])
			// main bulk copy (FASTEST PATH if no end should be handled specially)
		    System.arraycopy(source, sIndex[0], dest, dIndex[0], dIndex[1]-dIndex[0]);

		if (dOffset[0] != 0) {
			// handle "from" end specially
			sIndex[0]--;
			dIndex[0]--;
			
			final long HIGHEST_BITS = LONG_DATA_MASK << dOffset[0];
			final long LOWEST_BITS = ~HIGHEST_BITS;
			
			save = source[sIndex[0]] & HIGHEST_BITS;
			dest[dIndex[0]] &= LOWEST_BITS;
			dest[dIndex[0]] |= save;
		}
	}

	private static void copyHigherFrom0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (dIndex[0] == dIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
					
			// At this point dIndex[0] == dIndex[1] AND sOffset[0] < dOffset[0].
			// This implies that sIndex[0] == sIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			long save = (source[s] << dOffset[0] - sOffset[0]) & ~(LONG_DATA_MASK << dOffset[1]) & LONG_DATA_MASK << dOffset[0];
			dest[d] &= LONG_DATA_MASK << dOffset[1] | ~(LONG_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyHigherFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyHigherFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyHigherFromForwards0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		long save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = source[s] >>> sOffset[0] << dOffset[0];
		dest[d] &= ~(LONG_DATA_MASK << dOffset[0]);
		dest[d] |= save; 

		while(++d < dIndex[1]) {
			save = source[s] >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] &= ~(LONG_DATA_MASK >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;

			++s;

			save = source[s] << dOffset[0] - sOffset[0];
			dest[d] &= LONG_DATA_MASK >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;
		}

		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				// implies s == sIndex[1] [proof needed]
				save = (source[s] & ~(LONG_DATA_MASK << sOffset[1])) >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
				dest[d] &= LONG_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				save = source[s] >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
				dest[d] &= ~(LONG_DATA_MASK >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
				
				if (sOffset[1] > 0) {
					s++;
					
					save = (source[s] & ~(LONG_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
					dest[d] &= LONG_DATA_MASK << dOffset[1] | LONG_DATA_MASK >>> LONG_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
			}
		}
	}

	private static void copyHigherFromBackwards0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		long save;
		
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (dOffset[1] > 0) {
			if (dOffset[1] < sOffset[1]) {
				save = (source[s] & ~(LONG_DATA_MASK << sOffset[1])) >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
				dest[d] &= LONG_DATA_MASK << dOffset[1];
				dest[d] |= save;
			} else /* dOffset[1] > sOffset[1] */ {
				if (sOffset[1] > 0) {
					save = (source[s] & ~(LONG_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
					dest[d] &= LONG_DATA_MASK << dOffset[1] | LONG_DATA_MASK >>> LONG_DATA_LINES - (dOffset[1] - sOffset[1]);
					dest[d] |= save;
				}
				
				s--;
				
				save = source[s] >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
				dest[d] &= ~(LONG_DATA_MASK >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]));
				dest[d] |= save;
			}
		}

		while(--d > dIndex[0]) {
			save = source[s] << dOffset[0] - sOffset[0];
			dest[d] &= LONG_DATA_MASK >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] |= save;

			--s;

			save = source[s] >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]);
			dest[d] &= ~(LONG_DATA_MASK >>> LONG_DATA_LINES - (dOffset[0] - sOffset[0]));
			dest[d] |= save;
		}

		save = source[s] >>> sOffset[0] << dOffset[0];
		dest[d] &= ~(LONG_DATA_MASK << dOffset[0]);
		dest[d] |= save; 
	}

	private static void copyLowerFrom0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		if (sIndex[0] == sIndex[1]) {
			int d = dIndex[0];
			int s = sIndex[0];
			
			// At this point sIndex[0] == sIndex[1] AND sOffset[0] > dOffset[0].
			// This implies that dIndex[0] == dIndex[1] (so there is no need to copy more than 1 chunk).
			// I have discovered a truly marvelous proof of this, which this media is too clumsy to contain.
			long save = source[s] >>> (sOffset[0] - dOffset[0]) & ~(LONG_DATA_MASK << dOffset[1]) & LONG_DATA_MASK << dOffset[0];
			dest[d] &= LONG_DATA_MASK << dOffset[1] | ~(LONG_DATA_MASK << dOffset[0]);
			dest[d] |= save;
			return;
		}
		
		if (dIndex[0] < sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else if (dIndex[0] == sIndex[0])
			copyLowerFromForwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
		else
			copyLowerFromBackwards0(source, dest, sIndex, sOffset, dIndex, dOffset);
	}

	private static void copyLowerFromForwards0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		long save;
		
		int d = dIndex[0];
		int s = sIndex[0];
		
		save = source[s] >>> sOffset[0] << dOffset[0];
		dest[d] &= ~((LONG_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;

		while(++s < sIndex[1]) {
			save = source[s] << LONG_DATA_LINES - (sOffset[0] - dOffset[0]);
			dest[d] &= LONG_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;

			++d;

			save = source[s] >>> sOffset[0] - dOffset[0];
			dest[d] &= ~(LONG_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;
		}
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				// implies d == dIndex[1] [proof needed]
				save = (source[s] & ~(LONG_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				dest[d] &= ~(~(LONG_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				save = source[s] << LONG_DATA_LINES - (sOffset[0] - dOffset[0]);
				dest[d] &= LONG_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
				
				if (dOffset[1] > 0) {
					d++;
					
					save = (source[s] & ~(LONG_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
					dest[d] &= ~(~(LONG_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
			}
		}
	}
	
	private static void copyLowerFromBackwards0(long[] source, long[] dest, int[] sIndex, int[] sOffset, int[] dIndex, int[] dOffset) {
		// for the cases where source == dest, so save |red bits, before &ing them
		long save;
		
		int d = dIndex[1];
		int s = sIndex[1];
		
		if (sOffset[1] > 0) {
			if (dOffset[1] > sOffset[1]) {
				save = (source[s] & ~(LONG_DATA_MASK << sOffset[1])) << dOffset[1] - sOffset[1];
				dest[d] &= ~(~(LONG_DATA_MASK << sOffset[1]) << dOffset[1] - sOffset[1]);
				dest[d] |= save;
			} else /* dOffset[1] < sOffset[1] */ {
				if (dOffset[1] > 0) {
					save = (source[s] & ~(LONG_DATA_MASK << sOffset[1])) >>> sOffset[0] - dOffset[0];
					dest[d] &= ~(~(LONG_DATA_MASK << sOffset[1]) >>> sOffset[0] - dOffset[0]);
					dest[d] |= save;
				}
				
				d--;
				
				save = source[s] << LONG_DATA_LINES - (sOffset[0] - dOffset[0]);
				dest[d] &= LONG_DATA_MASK >>> sOffset[0] - dOffset[0];
				dest[d] |= save;
			}
		}

		while(--s > sIndex[0]) {
			save = source[s] >>> sOffset[0] - dOffset[0];
			dest[d] &= ~(LONG_DATA_MASK >>> sOffset[0] - dOffset[0]);
			dest[d] |= save;

			--d;

			save = source[s] << LONG_DATA_LINES - (sOffset[0] - dOffset[0]);
			dest[d] &= LONG_DATA_MASK >>> sOffset[0] - dOffset[0];
			dest[d] |= save;
		}
		
		save = source[s] >>> sOffset[0] << dOffset[0];
		dest[d] &= ~((LONG_DATA_MASK >>> sOffset[0]) << dOffset[0]);
		dest[d] |= save;
	}
}
