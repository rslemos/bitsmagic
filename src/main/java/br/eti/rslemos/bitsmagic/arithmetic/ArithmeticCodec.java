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
package br.eti.rslemos.bitsmagic.arithmetic;

import java.io.IOException;

/**
 * An implementation of arithmetic coding.
 * 
 * <p>Arithmetic coding (or range coding) is a form of entropy encoding.
 * </p>
 * <p>The subinterfaces of this interface provide access to the {@link Encoder} 
 * and {@link Decoder} aspects of the algorithm.
 * </p>
 * <p>Encoding usage as follows:
 * </p>
 * <pre>
 *   {@link ArithmeticCodec.Encoder} encoder = ...;
 *   encoder.write(symbol, cumulativeFrequencies);
 *   ...
 *   encoder.flush();
 * </pre>
 *
 * <p>Decoding usage as follows:
 * </p>
 * <pre>
 *   {@link ArithmeticCodec.Decoder} decoder = ...;
 *   int symbol = read(cumulativeFrequencies);
 *   ...
 * </pre>
 *
 * @author Rodrigo Lemos
 * @since 1.0.0
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Arithmetic_coding">Arithmetic 
 *   coding</a>
 */
public interface ArithmeticCodec {
	/**
	 * Encoding interface of an {@code ArithmeticCodec}.
	 *
	 * @since 1.0.0
	 */
	public interface Encoder {
		/**
		 * Writes a symbol of an alphabet with a given input probability 
		 * distribution.
		 *
		 * @param v symbol to be written, between 0, inclusive, and 
		 *        {@code cumulativeCount.length}, exclusive.
		 * @param cumulativeCount discrete cumulative count function; 
		 *        non-decreasing count of symbols {@code f(i)} up to symbol 
		 *        {@code i}. Probability of each symbol {@code i} can be 
		 *        computed as {@code (f(i) - f(i-1)) / (f(f.length - 1)) } 
		 *        ({@code f(-1) = 0}). If {@code f(i) == f(i - 1)} then symbol 
		 *        {@code i} is unexpected; in this case 
		 *        {@code IllegalArgumentException} is thrown.
		 * 
		 * @since 1.0.0
		 */
		void write(int v, int... cumulativeCount) throws IOException;

		/**
		 * Flushes whatever internal state is stored in this Encoder. This 
		 * Encoder should not be used anymore. Any other call to this Encoder 
		 * invokes undefined behavior.
		 * 
		 * @since 1.0.0
		 */
		void flush() throws IOException;
	}

	/**
	 * Decoding interface of an {@code ArithmeticCodec}.
	 *
	 * @since 1.0.0
	 */
	public interface Decoder {
		/**
		 * Reads a symbol of an alphabet with a given input probability 
		 * distribution.
		 *
		 * @param cumulativeCount discrete cumulative count function; 
		 *        non-decreasing count of symbols {@code f(i)} up to symbol 
		 *        {@code i}. Probability of each symbol {@code i} can be 
		 *        computed as {@code (f(i) - f(i-1)) / (f(f.length - 1)) } 
		 *        ({@code f(-1) = 0}). If {@code f(i) == f(i - 1)} then symbol 
		 *        {@code i} is unexpected; in this case 
		 *        {@code IllegalArgumentException} is thrown.
		 * @return symbol between 0, inclusive, and 
		 *        {@code cumulativeCount.length}, exclusive.
		 * 
		 * @since 1.0.0
		 */
		int read(int... cumulativeCount) throws IOException;
	}
}
