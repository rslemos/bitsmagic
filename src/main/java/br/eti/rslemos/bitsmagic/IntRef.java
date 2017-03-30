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
 * Integer to pass by reference. An object of this class allows to pass 
 * integers to called methods and have changes to their values reflected back 
 * at the callee. Since objects of this class are not immutable, both methods 
 * {@code equals} and {@code hashCode} compute their results on object 
 * references, that is, {@code Object}'s default implementations are not 
 * overridden.
 */
public class IntRef {
	/**
	 * Integer value held by this reference.
	 */
	public int i;

	/**
	 * Create a new reference with 0 as initial content.
	 */
	public IntRef() { };

	/**
	 * Create a new reference with {@code i} as initial content.
	 */
	public IntRef(int i) { this.i = i; }

	/**
	 * Create a new reference with {@code i} as initial content. Syntatic sugar 
	 * to {@code new IntRef(i)}.
	 */
	public static IntRef byRef(int i) { return new IntRef(i); }
	
	@Override public String toString() {
		return String.format("%d&", i);
	}
}

