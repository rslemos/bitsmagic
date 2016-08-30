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
package br.eti.rslemos.bitsmagic.stream;

import java.io.EOFException;
import java.io.IOException;

public abstract class AbstractArrayIntStream<T> {

	final T buffer;
	final int to;
	final int width;
	final int mask;
	int from;

	AbstractArrayIntStream (int width, T buffer, int from, int to) {
		if (!(width > 0 && width <= Integer.SIZE))
			throw new IllegalArgumentException();
		
		this.width = width;
		this.buffer = buffer;
		this.from = from;
		this.to = to;
		this.mask = (int) (-1L << width);
	}

	int readInt() throws IOException {
		if (from < to)
			try {
				int result = readInt0();
				
				if ((to - from) < Integer.SIZE)
					result &= ~(-1 << (to - from));
				
				result &= ~mask;
				
				return result;
			} finally {
				from += width;
			}
		else
			throw new EOFException();
	}

	void writeInt(int v) throws IOException {
		if (from < to)
			try {
				v &= ~mask;
				if (to - from < Integer.SIZE) {
					int ubermask = -1 << (to - from);
					v &= ~ubermask;
					v |= readInt0() & (mask | ubermask);
				} else
					v |= readInt0() & mask;
				writeInt0(v);
			} finally {
				from += width;
			}
		else
			throw new EOFException();
	}
	
	abstract int readInt0();
	
	abstract void writeInt0(int v);
}
