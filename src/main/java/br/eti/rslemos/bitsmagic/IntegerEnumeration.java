/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2019 Rodrigo Lemos
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
 * @author Rodrigo Lemos
 * @since 1.0.0
 */
public class IntegerEnumeration {
	public static short toNatural(short i) {
		return (short)((i << 1) ^ (i >> -1));
	}

	public static short fromNatural(short n) {
		return (short)((n >>> 1) ^ (n << -1 >> -1));
	}

	public static int toNatural(int i) {
		return (i << 1) ^ (i >> -1);
	}

	public static int fromNatural(int n) {
		return (n >>> 1) ^ (n << -1 >> -1);
	}

	public static long toNatural(long i) {
		return (i << 1) ^ (i >> -1);
	}

	public static long fromNatural(long n) {
		return (n >>> 1) ^ (n << -1 >> -1);
	}
}
