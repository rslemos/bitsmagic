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

public interface StorageBuilder<T> {
	T build(long... d);
	
	static class ByteArrayBuilder {
		protected static byte[] build0(long...d) {
			byte[] result = new byte[d.length * 8];
			for (int i = 0; i < d.length; i++) {
				result[i * 8 + 0] = (byte) (d[d.length - i - 1] >>>  0);
				result[i * 8 + 1] = (byte) (d[d.length - i - 1] >>>  8);
				result[i * 8 + 2] = (byte) (d[d.length - i - 1] >>> 16);
				result[i * 8 + 3] = (byte) (d[d.length - i - 1] >>> 24);
				result[i * 8 + 4] = (byte) (d[d.length - i - 1] >>> 32);
				result[i * 8 + 5] = (byte) (d[d.length - i - 1] >>> 40);
				result[i * 8 + 6] = (byte) (d[d.length - i - 1] >>> 48);
				result[i * 8 + 7] = (byte) (d[d.length - i - 1] >>> 56);
			}
		
			return result;
		}
	}
	
	static class CharArrayBuilder {
		protected static char[] build0(long...d) {
			char[] result = new char[d.length * 4];
			for (int i = 0; i < d.length; i++) {
				result[i * 4 + 0] = (char) (d[d.length - i - 1] >>>  0);
				result[i * 4 + 1] = (char) (d[d.length - i - 1] >>> 16);
				result[i * 4 + 2] = (char) (d[d.length - i - 1] >>> 32);
				result[i * 4 + 3] = (char) (d[d.length - i - 1] >>> 48);
			}
		
			return result;
		}
	}
	
	static class ShortArrayBuilder {
		protected static short[] build0(long...d) {
			short[] result = new short[d.length * 4];
			for (int i = 0; i < d.length; i++) {
				result[i * 4 + 0] = (short) (d[d.length - i - 1] >>>  0);
				result[i * 4 + 1] = (short) (d[d.length - i - 1] >>> 16);
				result[i * 4 + 2] = (short) (d[d.length - i - 1] >>> 32);
				result[i * 4 + 3] = (short) (d[d.length - i - 1] >>> 48);
			}
		
			return result;
		}
	}
	
	static class IntArrayBuilder {
		protected static int[] build0(long...d) {
			int[] result = new int[d.length * 2];
			for (int i = 0; i < d.length; i++) {
				result[i * 2 + 0] = (int) (d[d.length - i - 1] >>>  0);
				result[i * 2 + 1] = (int) (d[d.length - i - 1] >>> 32);
			}
		
			return result;
		}
	}
}