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

public abstract class AnyBaseArithmeticCodec {

	// parameters
	final int BASE;
	
	// computed constants (functions of base, itself constant)
	final int HIGHEST_OUTPUT;
	final long SHIFT_MASK;
	final long UNDERFLOW_MASK;
	final long UNDERFLOW_LOWEST;
	
	long low;
	long range;
	
	AnyBaseArithmeticCodec(int base) {
		BASE = base;
		HIGHEST_OUTPUT = BASE - 1;
		
		long shiftMask = 1;
		
		while (shiftMask < Long.MAX_VALUE/BASE)
			shiftMask *= BASE;
		
		SHIFT_MASK = shiftMask/BASE;
		UNDERFLOW_MASK = shiftMask/BASE/BASE;
		UNDERFLOW_LOWEST = HIGHEST_OUTPUT*UNDERFLOW_MASK;
	}

	void advance(int symbol, int... cumulativeCount) throws IOException {
		int start = symbol == 0 ? 0 : cumulativeCount[symbol - 1];
		int size = cumulativeCount[symbol] - start;
		int total = cumulativeCount[cumulativeCount.length - 1];
		
		scale(start, size, total);
		
		while (peek(low) == peek(low + range - 1))
			shiftOut(shift());
		
		while (range < UNDERFLOW_MASK)
			underflow();
	}
	
	abstract int peek(long v);
	
	abstract int shift() throws IOException;

	private void scale(int start, int size, int total) {
		if (size == 0)
			throw new IllegalArgumentException("Unexpected symbol");
		
		range /= total;
		if (range == 0)
			throw new IllegalStateException("Irrecoverable underflow: range is zero");
		
		low += start * range;
		range *= size;
	}

	int getSymbol(long code, int... cumulativeCount) {
		int total = cumulativeCount[cumulativeCount.length - 1];
		int count = (int) ((code - low)/(range / total));
		
		int symbol;
		for(symbol = 0; cumulativeCount[symbol] <= count; symbol++);
		
		return symbol;
	}
	
	void shiftOut(int v) throws IOException {
	}

	int underflow() throws IOException {
		low -= UNDERFLOW_LOWEST;
		return shift();
	}

	public String toString() {
		return String.format("range = [%s, %s)", Long.toString(low, BASE), Long.toString(low + range - 1, BASE));
	}
}
