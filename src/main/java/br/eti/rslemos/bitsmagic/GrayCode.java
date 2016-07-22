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

public class GrayCode {
	public static byte toGray(byte v) {
		int i = v & 0xff;
		return (byte) (i ^ i >>> 1);
	}

	public static byte fromGray(byte g) {
		int i = g & 0xff;
		i ^= i >> 1;
		i ^= i >> 2;
		i ^= i >> 4;
		
		return (byte)i;
	}

	public static char toGray(char v) {
		int i = v & 0xffff;
		return (char) (i ^ i >>> 1);
	}

	public static char fromGray(char g) {
		int i = g & 0xffff;
		i ^= i >> 1;
		i ^= i >> 2;
		i ^= i >> 4;
		i ^= i >> 8;
		
		return (char)i;
	}

	public static short toGray(short v) {
		int i = v & 0xffff;
		return (short) (i ^ i >>> 1);
	}

	public static short fromGray(short g) {
		int i = g & 0xffff;
		i ^= i >> 1;
		i ^= i >> 2;
		i ^= i >> 4;
		i ^= i >> 8;
		
		return (short)i;
	}

	public static int toGray(int v) {
		return v ^ v >>> 1;
	}

	public static int fromGray(int g) {
		g ^= g >>> 1;
		g ^= g >>> 2;
		g ^= g >>> 4;
		g ^= g >>> 8;
		g ^= g >>> 16;
		
		return g;
	}

	public static long toGray(long v) {
		return v ^ v >>> 1;
	}

	public static long fromGray(long g) {
		g ^= g >>> 1;
		g ^= g >>> 2;
		g ^= g >>> 4;
		g ^= g >>> 8;
		g ^= g >>> 16;
		g ^= g >>> 32;
		
		return g;
	}
}
