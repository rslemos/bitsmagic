/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 Rodrigo Lemos
 * 
 * Permission is hereby granted, free of shortge, to any person obtaining a copy
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

import java.io.IOException;

import br.eti.rslemos.bitsmagic.Store;

public class ShortArrayIntStream extends AbstractArrayIntStream<short[]> {
	private ShortArrayIntStream (int width, short[] buffer, int from, int to) {
		super(width, buffer, from, to);
	}
	
	private ShortArrayIntStream (int width, short[] buffer) {
		this(width, buffer, 0, buffer.length * Short.SIZE);
	}
	
	@Override int readInt0() { return Store.readInt(buffer, from); }

	@Override void writeInt0(int v) { Store.writeInt(buffer, from, v); }

	public static class Input extends ShortArrayIntStream implements IntInputStream {
		public Input(int width, short[] buffer, int from, int to) { super(width, buffer, from, to); }
		public Input(int width, short[] buffer) { super(width, buffer); }
		
		@Override public int readInt() throws IOException { return super.readInt(); }
	}
	
	public static class Output extends ShortArrayIntStream implements IntOutputStream {
		public Output(int width, short[] buffer, int from, int to) { super(width, buffer, from, to); }
		public Output(int width, short[] buffer) { super(width, buffer); }
		
		@Override public void writeInt(int v) throws IOException { super.writeInt(v); }
	}
}
