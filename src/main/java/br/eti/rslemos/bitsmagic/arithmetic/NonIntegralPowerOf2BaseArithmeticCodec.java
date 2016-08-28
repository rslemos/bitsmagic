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

import br.eti.rslemos.bitsmagic.stream.IntInputStream;
import br.eti.rslemos.bitsmagic.stream.IntOutputStream;

public class NonIntegralPowerOf2BaseArithmeticCodec extends AnyBaseArithmeticCodec {
	private NonIntegralPowerOf2BaseArithmeticCodec(int base) {
		super(base);
	}
	
	@Override int peek(long v) {
		return (int) (v / SHIFT_MASK);
	}

	@Override int shift() throws IOException {
		int carry = peek(low);
		low %= SHIFT_MASK;
		low *= BASE;
		range *= BASE;
		return carry;
	}
	
	public static class Encoder extends NonIntegralPowerOf2BaseArithmeticCodec implements ArithmeticCodec.Encoder {
		private final DelayedZeroIntOutputStream stream;

		// more state
		int underflowHeadValue;
		long underflowTailCount;

		public Encoder(IntOutputStream stream, int base) {
			super(base);
			this.stream = new DelayedZeroIntOutputStream(stream);
			
			range = SHIFT_MASK*BASE;
		}

		@Override public void write(int symbol, int... cumulativeCount) throws IOException {
			advance(symbol, cumulativeCount);
		}
		
		@Override int underflow() throws IOException {
			if (underflowTailCount == 0)
				underflowHeadValue = super.underflow();
			else
				super.underflow();
			
			underflowTailCount++;
			
			if (underflowTailCount == 0)
				throw new IllegalStateException("Too many underflows");
			
			return underflowHeadValue;
		}
		
		@Override void shiftOut(int v) throws IOException {
			stream.writeInt(v + underflowHeadValue);
			underflowHeadValue = 0;

			for(; underflowTailCount != 0; underflowTailCount--)
				stream.writeInt(v == 0 ? HIGHEST_OUTPUT : 0);
		}
		
		@Override public void flush() throws IOException {
			low += SHIFT_MASK - 1;	
			shiftOut(shift());
		}
		
		public String toString() {
			return String.format("ArithmeticCodec.Encoder{%s}", super.toString());
		}
	}

	public static class Decoder extends NonIntegralPowerOf2BaseArithmeticCodec implements ArithmeticCodec.Decoder {
		private final TrailingZeroIntInputStream stream;
		
		// more state
		private long code = 0;
		
		public Decoder(IntInputStream stream, int base) throws IOException {
			super(base);
			this.stream = new TrailingZeroIntInputStream(stream);
			range = 1;
			
			while (range <= SHIFT_MASK)
				shift();
		}
		
		@Override public int read(int... cumulativeCount) throws IOException {
			int symbol = getSymbol(code, cumulativeCount);
			
			advance(symbol, cumulativeCount);
			
			return symbol;
		}
		
		@Override int shift() throws IOException {
			int carry = peek(code);
			code %= SHIFT_MASK;
			code *= BASE;
			code += shiftIn();
			return carry - super.shift();
		}

		@Override int underflow() throws IOException {
			int carry = super.underflow();
			code %= SHIFT_MASK;
			code += carry * SHIFT_MASK;
			return carry;
		}

		private int shiftIn() throws IOException {
			return stream.readInt();
		}

		public String toString() {
			return String.format("ArithmeticCodec.Decoder{code %s, %s}", Long.toString(code, BASE), super.toString());
		}
	}
}