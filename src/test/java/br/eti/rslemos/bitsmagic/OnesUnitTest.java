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

import static br.eti.rslemos.bitsmagic.Ones.ones;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class OnesUnitTest {
	@Ignore
	public abstract static class PrimitiveCases<T> {
		private int size;
		
		protected PrimitiveCases(int size) { this.size = size; }

		protected abstract T cast(long x);
		protected abstract int ones(T x);
		
		@Test public void b0000xxx0()     { assertThat(ones(cast(0b0L)),        is(equalTo(0))); }
		@Test public void b000xxx01()     { assertThat(ones(cast(0b1L)),        is(equalTo(1))); }
		@Test public void b00xxx010()     { assertThat(ones(cast(0b10L)),       is(equalTo(1))); }
		@Test public void b0xxx0100()     { assertThat(ones(cast(0b100L)),      is(equalTo(1))); }
		@Test public void b0xx01000()     { assertThat(ones(cast(0b1000L)),     is(equalTo(1))); }
		@Test public void b0x010000()     { assertThat(ones(cast(0b10000L)),    is(equalTo(1))); }
		@Test public void b0x0100000()    { assertThat(ones(cast(0b100000L)),   is(equalTo(1))); }
		@Test public void b0x01000000()   { assertThat(ones(cast(0b1000000L)),  is(equalTo(1))); }
		@Test public void b0x010000000()  { assertThat(ones(cast(0b10000000L)), is(equalTo(1))); }
		@Test public void b00xxx011()     { assertThat(ones(cast(0b11L)),       is(equalTo(2))); }
		@Test public void b0xxx0110()     { assertThat(ones(cast(0b110L)),      is(equalTo(2))); }
		@Test public void b0xx01100()     { assertThat(ones(cast(0b1100L)),     is(equalTo(2))); }
		@Test public void b0x011000()     { assertThat(ones(cast(0b11000L)),    is(equalTo(2))); }
		@Test public void b0x0110000()    { assertThat(ones(cast(0b110000L)),   is(equalTo(2))); }
		@Test public void b0x01100000()   { assertThat(ones(cast(0b1100000L)),  is(equalTo(2))); }
		@Test public void b0x011000000()  { assertThat(ones(cast(0b11000000L)), is(equalTo(2))); }
		@Test public void b0xxx0111()     { assertThat(ones(cast(0b111L)),      is(equalTo(3))); }
		@Test public void b0xx01110()     { assertThat(ones(cast(0b1110L)),     is(equalTo(3))); }
		@Test public void b0x011100()     { assertThat(ones(cast(0b11100L)),    is(equalTo(3))); }
		@Test public void b0x0111000()    { assertThat(ones(cast(0b111000L)),   is(equalTo(3))); }
		@Test public void b0x01110000()   { assertThat(ones(cast(0b1110000L)),  is(equalTo(3))); }
		@Test public void b0x011100000()  { assertThat(ones(cast(0b11100000L)), is(equalTo(3))); }
		@Test public void b0xx01111()     { assertThat(ones(cast(0b1111L)),     is(equalTo(4))); }
		@Test public void b0x011110()     { assertThat(ones(cast(0b11110L)),    is(equalTo(4))); }
		@Test public void b0x0111100()    { assertThat(ones(cast(0b111100L)),   is(equalTo(4))); }
		@Test public void b0x01111000()   { assertThat(ones(cast(0b1111000L)),  is(equalTo(4))); }
		@Test public void b0x011110000()  { assertThat(ones(cast(0b11110000L)), is(equalTo(4))); }
		@Test public void b0x011111()     { assertThat(ones(cast(0b11111L)),    is(equalTo(5))); }
		@Test public void b0x0111110()    { assertThat(ones(cast(0b111110L)),   is(equalTo(5))); }
		@Test public void b0x01111100()   { assertThat(ones(cast(0b1111100L)),  is(equalTo(5))); }
		@Test public void b0x011111000()  { assertThat(ones(cast(0b11111000L)), is(equalTo(5))); }
		@Test public void b0x01111111()   { assertThat(ones(cast(0b111111L)),   is(equalTo(6))); }
		@Test public void b0x011111110()  { assertThat(ones(cast(0b1111110L)),  is(equalTo(6))); }
		@Test public void b0x0111111100() { assertThat(ones(cast(0b11111100L)), is(equalTo(6))); }
		@Test public void b0x011111111()  { assertThat(ones(cast(0b1111111L)),  is(equalTo(7))); }
		@Test public void b0x0111111110() { assertThat(ones(cast(0b11111110L)), is(equalTo(7))); }
		@Test public void b0x0111111111() { assertThat(ones(cast(0b11111111L)), is(equalTo(8))); }

		@Test public void b1000xxx0()     { assertThat(ones(cast(0b1L << (size-1))),        is(equalTo(1))); }
		@Test public void b0100xxx0()     { assertThat(ones(cast(0b1L << (size-2))),        is(equalTo(1))); }
		@Test public void b0010xxx0()     { assertThat(ones(cast(0b1L << (size-3))),        is(equalTo(1))); }
		@Test public void b00010xx0()     { assertThat(ones(cast(0b1L << (size-4))),        is(equalTo(1))); }
		@Test public void b000010x0()     { assertThat(ones(cast(0b1L << (size-5))),        is(equalTo(1))); }
		@Test public void b0000010x0()    { assertThat(ones(cast(0b1L << (size-6))),        is(equalTo(1))); }
		@Test public void b00000010x0()   { assertThat(ones(cast(0b1L << (size-7))),        is(equalTo(1))); }
		@Test public void b1100xxx0()     { assertThat(ones(cast(0b11L << (size-2))),       is(equalTo(2))); }
		@Test public void b0110xxx0()     { assertThat(ones(cast(0b11L << (size-3))),       is(equalTo(2))); }
		@Test public void b00110xx0()     { assertThat(ones(cast(0b11L << (size-4))),       is(equalTo(2))); }
		@Test public void b000110x0()     { assertThat(ones(cast(0b11L << (size-5))),       is(equalTo(2))); }
		@Test public void b0000110x0()    { assertThat(ones(cast(0b11L << (size-6))),       is(equalTo(2))); }
		@Test public void b1110xxx0()     { assertThat(ones(cast(0b111L << (size-3))),      is(equalTo(3))); }
		@Test public void b01110xx0()     { assertThat(ones(cast(0b111L << (size-4))),      is(equalTo(3))); }
		@Test public void b001110x0()     { assertThat(ones(cast(0b111L << (size-5))),      is(equalTo(3))); }
		@Test public void b11110xx0()     { assertThat(ones(cast(0b1111L << (size-4))),     is(equalTo(4))); }
		@Test public void b111110x0()     { assertThat(ones(cast(0b11111L << (size-5))),    is(equalTo(5))); }
		@Test public void b1111110x0()    { assertThat(ones(cast(0b111111L << (size-6))),   is(equalTo(6))); }
		@Test public void b11111110x0()   { assertThat(ones(cast(0b1111111L << (size-7))),  is(equalTo(7))); }
		@Test public void b11111111xxx0() { assertThat(ones(cast(0b11111111L << (size-8))), is(equalTo(8))); }

		@Test public void b1111xxx1()     { assertThat(ones(cast(-1L)), is(equalTo(size))); }
		
		// ¬
		@Test public void _b0000xxx0()     { assertThat(ones(cast(~0b0L)),        is(equalTo(size-0))); }
		@Test public void _b000xxx01()     { assertThat(ones(cast(~0b1L)),        is(equalTo(size-1))); }
		@Test public void _b00xxx010()     { assertThat(ones(cast(~0b10L)),       is(equalTo(size-1))); }
		@Test public void _b0xxx0100()     { assertThat(ones(cast(~0b100L)),      is(equalTo(size-1))); }
		@Test public void _b0xx01000()     { assertThat(ones(cast(~0b1000L)),     is(equalTo(size-1))); }
		@Test public void _b0x010000()     { assertThat(ones(cast(~0b10000L)),    is(equalTo(size-1))); }
		@Test public void _b0x0100000()    { assertThat(ones(cast(~0b100000L)),   is(equalTo(size-1))); }
		@Test public void _b0x01000000()   { assertThat(ones(cast(~0b1000000L)),  is(equalTo(size-1))); }
		@Test public void _b0x010000000()  { assertThat(ones(cast(~0b10000000L)), is(equalTo(size-1))); }
		@Test public void _b00xxx011()     { assertThat(ones(cast(~0b11L)),       is(equalTo(size-2))); }
		@Test public void _b0xxx0110()     { assertThat(ones(cast(~0b110L)),      is(equalTo(size-2))); }
		@Test public void _b0xx01100()     { assertThat(ones(cast(~0b1100L)),     is(equalTo(size-2))); }
		@Test public void _b0x011000()     { assertThat(ones(cast(~0b11000L)),    is(equalTo(size-2))); }
		@Test public void _b0x0110000()    { assertThat(ones(cast(~0b110000L)),   is(equalTo(size-2))); }
		@Test public void _b0x01100000()   { assertThat(ones(cast(~0b1100000L)),  is(equalTo(size-2))); }
		@Test public void _b0x011000000()  { assertThat(ones(cast(~0b11000000L)), is(equalTo(size-2))); }
		@Test public void _b0xxx0111()     { assertThat(ones(cast(~0b111L)),      is(equalTo(size-3))); }
		@Test public void _b0xx01110()     { assertThat(ones(cast(~0b1110L)),     is(equalTo(size-3))); }
		@Test public void _b0x011100()     { assertThat(ones(cast(~0b11100L)),    is(equalTo(size-3))); }
		@Test public void _b0x0111000()    { assertThat(ones(cast(~0b111000L)),   is(equalTo(size-3))); }
		@Test public void _b0x01110000()   { assertThat(ones(cast(~0b1110000L)),  is(equalTo(size-3))); }
		@Test public void _b0x011100000()  { assertThat(ones(cast(~0b11100000L)), is(equalTo(size-3))); }
		@Test public void _b0xx01111()     { assertThat(ones(cast(~0b1111L)),     is(equalTo(size-4))); }
		@Test public void _b0x011110()     { assertThat(ones(cast(~0b11110L)),    is(equalTo(size-4))); }
		@Test public void _b0x0111100()    { assertThat(ones(cast(~0b111100L)),   is(equalTo(size-4))); }
		@Test public void _b0x01111000()   { assertThat(ones(cast(~0b1111000L)),  is(equalTo(size-4))); }
		@Test public void _b0x011110000()  { assertThat(ones(cast(~0b11110000L)), is(equalTo(size-4))); }
		@Test public void _b0x011111()     { assertThat(ones(cast(~0b11111L)),    is(equalTo(size-5))); }
		@Test public void _b0x0111110()    { assertThat(ones(cast(~0b111110L)),   is(equalTo(size-5))); }
		@Test public void _b0x01111100()   { assertThat(ones(cast(~0b1111100L)),  is(equalTo(size-5))); }
		@Test public void _b0x011111000()  { assertThat(ones(cast(~0b11111000L)), is(equalTo(size-5))); }
		@Test public void _b0x01111111()   { assertThat(ones(cast(~0b111111L)),   is(equalTo(size-6))); }
		@Test public void _b0x011111110()  { assertThat(ones(cast(~0b1111110L)),  is(equalTo(size-6))); }
		@Test public void _b0x0111111100() { assertThat(ones(cast(~0b11111100L)), is(equalTo(size-6))); }
		@Test public void _b0x011111111()  { assertThat(ones(cast(~0b1111111L)),  is(equalTo(size-7))); }
		@Test public void _b0x0111111110() { assertThat(ones(cast(~0b11111110L)), is(equalTo(size-7))); }
		@Test public void _b0x0111111111() { assertThat(ones(cast(~0b11111111L)), is(equalTo(size-8))); }

		@Test public void _b1000xxx0()     { assertThat(ones(cast(~(0b1L << (size-1)))),        is(equalTo(size-1))); }
		@Test public void _b0100xxx0()     { assertThat(ones(cast(~(0b1L << (size-2)))),        is(equalTo(size-1))); }
		@Test public void _b0010xxx0()     { assertThat(ones(cast(~(0b1L << (size-3)))),        is(equalTo(size-1))); }
		@Test public void _b00010xx0()     { assertThat(ones(cast(~(0b1L << (size-4)))),        is(equalTo(size-1))); }
		@Test public void _b000010x0()     { assertThat(ones(cast(~(0b1L << (size-5)))),        is(equalTo(size-1))); }
		@Test public void _b0000010x0()    { assertThat(ones(cast(~(0b1L << (size-6)))),        is(equalTo(size-1))); }
		@Test public void _b00000010x0()   { assertThat(ones(cast(~(0b1L << (size-7)))),        is(equalTo(size-1))); }
		@Test public void _b1100xxx0()     { assertThat(ones(cast(~(0b11L << (size-2)))),       is(equalTo(size-2))); }
		@Test public void _b0110xxx0()     { assertThat(ones(cast(~(0b11L << (size-3)))),       is(equalTo(size-2))); }
		@Test public void _b00110xx0()     { assertThat(ones(cast(~(0b11L << (size-4)))),       is(equalTo(size-2))); }
		@Test public void _b000110x0()     { assertThat(ones(cast(~(0b11L << (size-5)))),       is(equalTo(size-2))); }
		@Test public void _b0000110x0()    { assertThat(ones(cast(~(0b11L << (size-6)))),       is(equalTo(size-2))); }
		@Test public void _b1110xxx0()     { assertThat(ones(cast(~(0b111L << (size-3)))),      is(equalTo(size-3))); }
		@Test public void _b01110xx0()     { assertThat(ones(cast(~(0b111L << (size-4)))),      is(equalTo(size-3))); }
		@Test public void _b001110x0()     { assertThat(ones(cast(~(0b111L << (size-5)))),      is(equalTo(size-3))); }
		@Test public void _b11110xx0()     { assertThat(ones(cast(~(0b1111L << (size-4)))),     is(equalTo(size-4))); }
		@Test public void _b111110x0()     { assertThat(ones(cast(~(0b11111L << (size-5)))),    is(equalTo(size-5))); }
		@Test public void _b1111110x0()    { assertThat(ones(cast(~(0b111111L << (size-6)))),   is(equalTo(size-6))); }
		@Test public void _b11111110x0()   { assertThat(ones(cast(~(0b1111111L << (size-7)))),  is(equalTo(size-7))); }
		@Test public void _b11111111xxx0() { assertThat(ones(cast(~(0b11111111L << (size-8)))), is(equalTo(size-8))); }

		@Test public void _b1111xxx1()     { assertThat(ones(cast(~-1L)), is(equalTo(size-size))); }
	}
	
	public static class Byte extends PrimitiveCases<java.lang.Byte> {
		public Byte() { super(java.lang.Byte.SIZE); }
		@Override protected java.lang.Byte cast(long x) { return (byte)x; }
		@Override protected int ones(java.lang.Byte x) { return Ones.ones(x); }
	}
	
	public static class Char extends PrimitiveCases<java.lang.Character> {
		public Char() { super(java.lang.Character.SIZE); }
		@Override protected java.lang.Character cast(long x) { return (char)x; }
		@Override protected int ones(java.lang.Character x) { return Ones.ones(x); }
	}
	
	public static class Short extends PrimitiveCases<java.lang.Short> {
		public Short() { super(java.lang.Short.SIZE); }
		@Override protected java.lang.Short cast(long x) { return (short)x; }
		@Override protected int ones(java.lang.Short x) { return Ones.ones(x); }
	}
	
	public static class Int extends PrimitiveCases<java.lang.Integer> {
		public Int() { super(java.lang.Integer.SIZE); }
		@Override protected java.lang.Integer cast(long x) { return (int)x; }
		@Override protected int ones(java.lang.Integer x) { return Ones.ones(x); }
	}
	
	public static class Long extends PrimitiveCases<java.lang.Long> {
		public Long() { super(java.lang.Long.SIZE); }
		@Override protected java.lang.Long cast(long x) { return (long)x; }
		@Override protected int ones(java.lang.Long x) { return Ones.ones(x); }
	}
}
