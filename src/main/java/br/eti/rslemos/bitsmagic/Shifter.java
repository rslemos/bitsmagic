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

/**
 * This class consists exclusively of static methods that implement a barrel 
 * shifter over arrays of integral primitive types.
 * 
 * <p>For every method available in this class, the arguments that represent
 * offsets should always be given in bits, and are 0-based. For more 
 * information about bit mapping in arrays of integral primitive types see 
 * {@link Store} class.
 * </p>
 * <p>The operations implemented in this class try to mimic Java shift 
 * operators: the shift amount is always taken modulo shift region width.
 * </p>
 * <p>Offlimits bits are hardwired to 0: when shifted in they appear as 0; 
 * when shifted out they are simply discarded. Methods in this class should 
 * never throw {@code ArrayIndexOutOfBoundsException}.
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
public class Shifter {
	private Shifter() { /* non-instantiable */ }

	private static int fixAmount(int length, int amount) {
		// correct amount greater than length
		amount %= length;
		if (amount < 0)
			// correct for negative amount
			amount += length;
		
		return amount;
	}
	
	/********** byte[] **********/
	
	/**
	 * Shifts to the right (towards LSB) the specified region of the given 
	 * storage by the specified amount. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} the storage is left unchanged. The {@code amount} is 
	 * taken modulo {@code to - from}. If {@code amount} is negative, it is 
	 * added {@code to - from} until positive. The real amount of bits shifted 
	 * will be between 0, inclusive, and {@code to - from}, exclusive. If the 
	 * real amount of bits shifted is 0, the storage is left unchanged. The 
	 * shifted in bits to the left (on MSB) are hardwired to 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        shifted to the right.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        shifted to the right.
	 * @param amount number of bits to shift to right. This number is taken 
	 *        modulo {@code to - from}. If negative, it is added 
	 *        {@code to - from} until positive.
	 * 
	 * @since 1.0.0
	 */
	public static void shr(byte[] data, int from, int to, int amount) {
		if (data == null)
			throw new NullPointerException();

		if (to < from)
			throw new IllegalArgumentException();
		
		amount = fixAmount(to - from, amount);
		
		Copy.safeCopyFrom(data, from + amount, data, from, to - from - amount);
		Store.fill(data, to - amount, to, false);
	}
	
	/********** char[] **********/
	
	/**
	 * Shifts to the right (towards LSB) the specified region of the given 
	 * storage by the specified amount. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} the storage is left unchanged. The {@code amount} is 
	 * taken modulo {@code to - from}. If {@code amount} is negative, it is 
	 * added {@code to - from} until positive. The real amount of bits shifted 
	 * will be between 0, inclusive, and {@code to - from}, exclusive. If the 
	 * real amount of bits shifted is 0, the storage is left unchanged. The 
	 * shifted in bits to the left (on MSB) are hardwired to 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        shifted to the right.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        shifted to the right.
	 * @param amount number of bits to shift to right. This number is taken 
	 *        modulo {@code to - from}. If negative, it is added 
	 *        {@code to - from} until positive.
	 * 
	 * @since 1.0.0
	 */
	public static void shr(char[] data, int from, int to, int amount) {
		if (data == null)
			throw new NullPointerException();

		if (to < from)
			throw new IllegalArgumentException();
		
		amount = fixAmount(to - from, amount);
		
		Copy.safeCopyFrom(data, from + amount, data, from, to - from - amount);
		Store.fill(data, to - amount, to, false);
	}
	
	/********** short[] **********/
	
	/**
	 * Shifts to the right (towards LSB) the specified region of the given 
	 * storage by the specified amount. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} the storage is left unchanged. The {@code amount} is 
	 * taken modulo {@code to - from}. If {@code amount} is negative, it is 
	 * added {@code to - from} until positive. The real amount of bits shifted 
	 * will be between 0, inclusive, and {@code to - from}, exclusive. If the 
	 * real amount of bits shifted is 0, the storage is left unchanged. The 
	 * shifted in bits to the left (on MSB) are hardwired to 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        shifted to the right.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        shifted to the right.
	 * @param amount number of bits to shift to right. This number is taken 
	 *        modulo {@code to - from}. If negative, it is added 
	 *        {@code to - from} until positive.
	 * 
	 * @since 1.0.0
	 */
	public static void shr(short[] data, int from, int to, int amount) {
		if (data == null)
			throw new NullPointerException();

		if (to < from)
			throw new IllegalArgumentException();
		
		amount = fixAmount(to - from, amount);
		
		Copy.safeCopyFrom(data, from + amount, data, from, to - from - amount);
		Store.fill(data, to - amount, to, false);
	}
	
	/********** int[] **********/
	
	/**
	 * Shifts to the right (towards LSB) the specified region of the given 
	 * storage by the specified amount. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} the storage is left unchanged. The {@code amount} is 
	 * taken modulo {@code to - from}. If {@code amount} is negative, it is 
	 * added {@code to - from} until positive. The real amount of bits shifted 
	 * will be between 0, inclusive, and {@code to - from}, exclusive. If the 
	 * real amount of bits shifted is 0, the storage is left unchanged. The 
	 * shifted in bits to the left (on MSB) are hardwired to 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        shifted to the right.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        shifted to the right.
	 * @param amount number of bits to shift to right. This number is taken 
	 *        modulo {@code to - from}. If negative, it is added 
	 *        {@code to - from} until positive.
	 * 
	 * @since 1.0.0
	 */
	public static void shr(int[] data, int from, int to, int amount) {
		if (data == null)
			throw new NullPointerException();

		if (to < from)
			throw new IllegalArgumentException();
		
		amount = fixAmount(to - from, amount);
		
		Copy.safeCopyFrom(data, from + amount, data, from, to - from - amount);
		Store.fill(data, to - amount, to, false);
	}
	
	/********** long[] **********/
	
	/**
	 * Shifts to the right (towards LSB) the specified region of the given 
	 * storage by the specified amount. The range considered extends from 
	 * offset {@code from}, inclusive, to offset {@code to}, exclusive. If 
	 * {@code to <= from} the storage is left unchanged. The {@code amount} is 
	 * taken modulo {@code to - from}. If {@code amount} is negative, it is 
	 * added {@code to - from} until positive. The real amount of bits shifted 
	 * will be between 0, inclusive, and {@code to - from}, exclusive. If the 
	 * real amount of bits shifted is 0, the storage is left unchanged. The 
	 * shifted in bits to the left (on MSB) are hardwired to 0.
	 * 
	 * @param data storage array.
	 * @param from offset, in bits, 0-based, of the first bit (inclusive) to be 
	 *        shifted to the right.
	 * @param to offset, in bits, 0-based, of the last bit (exclusive) to be 
	 *        shifted to the right.
	 * @param amount number of bits to shift to right. This number is taken 
	 *        modulo {@code to - from}. If negative, it is added 
	 *        {@code to - from} until positive.
	 * 
	 * @since 1.0.0
	 */
	public static void shr(long[] data, int from, int to, int amount) {
		if (data == null)
			throw new NullPointerException();

		if (to < from)
			throw new IllegalArgumentException();
		
		amount = fixAmount(to - from, amount);
		
		Copy.safeCopyFrom(data, from + amount, data, from, to - from - amount);
		Store.fill(data, to - amount, to, false);
	}
}
