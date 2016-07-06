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

	private abstract static class Fixture<T> implements StorageBuilder<T> {
		//                         3210987654321098765432109876543210987654321098765432109876543210
		public T subject = build(0b0011001100110110100000001000000100000100001000100010010010101010L,
		                         0b1100110011001001011111110111111011111011110111011101101101010101L,
		                         0b0101010101011011100000011000001100001100011001100110110111111110L,
		                         0b0101010101010010001111110011111001111001110011001100100100000000L,
		                         0b0110001111001001111110000010111001110001000001111110111000000010L,
		                         0b0011111101111100001001000010111111101110001000001000001110110101L);
	}
	
	@Ignore
	public abstract static class Cases<T> extends Fixture<T> {
		protected abstract int ones(T data, int from, int to);

		@Test public void ones_0_0()    { assertThat(ones(subject,  0,        0), is(equalTo(  0))); }
		@Test public void ones_0_4()    { assertThat(ones(subject,  0,        4), is(equalTo(  2))); }
		@Test public void ones_0_6()    { assertThat(ones(subject,  0,        6), is(equalTo(  4))); }
		@Test public void ones_0_7()    { assertThat(ones(subject,  0,        7), is(equalTo(  4))); }
		@Test public void ones_0_8()    { assertThat(ones(subject,  0,        8), is(equalTo(  5))); }
		@Test public void ones_0_12()   { assertThat(ones(subject,  0,       12), is(equalTo(  7))); }
		@Test public void ones_0_14()   { assertThat(ones(subject,  0,       14), is(equalTo(  7))); }
		@Test public void ones_0_15()   { assertThat(ones(subject,  0,       15), is(equalTo(  7))); }
		@Test public void ones_0_16()   { assertThat(ones(subject,  0,       16), is(equalTo(  8))); }
		@Test public void ones_0_20()   { assertThat(ones(subject,  0,       20), is(equalTo(  8))); }
		@Test public void ones_0_22()   { assertThat(ones(subject,  0,       22), is(equalTo(  9))); }
		@Test public void ones_0_23()   { assertThat(ones(subject,  0,       23), is(equalTo(  9))); }
		@Test public void ones_0_24()   { assertThat(ones(subject,  0,       24), is(equalTo(  9))); }
		@Test public void ones_0_28()   { assertThat(ones(subject,  0,       28), is(equalTo( 12))); }
		@Test public void ones_0_30()   { assertThat(ones(subject,  0,       30), is(equalTo( 13))); }
		@Test public void ones_0_31()   { assertThat(ones(subject,  0,       31), is(equalTo( 14))); }
		@Test public void ones_0_32()   { assertThat(ones(subject,  0,       32), is(equalTo( 15))); }
		@Test public void ones_0_36()   { assertThat(ones(subject,  0,       36), is(equalTo( 19))); }
		@Test public void ones_0_40()   { assertThat(ones(subject,  0,       40), is(equalTo( 20))); }
		@Test public void ones_0_44()   { assertThat(ones(subject,  0,       44), is(equalTo( 21))); }
		@Test public void ones_0_46()   { assertThat(ones(subject,  0,       46), is(equalTo( 22))); }
		@Test public void ones_0_48()   { assertThat(ones(subject,  0,       48), is(equalTo( 22))); }
		@Test public void ones_0_56()   { assertThat(ones(subject,  0,       56), is(equalTo( 27))); }
		@Test public void ones_0_60()   { assertThat(ones(subject,  0,       60), is(equalTo( 31))); }
		@Test public void ones_0_62()   { assertThat(ones(subject,  0,       62), is(equalTo( 33))); }
		@Test public void ones_0_64()   { assertThat(ones(subject,  0,       64), is(equalTo( 33))); }
		@Test public void ones_0_72()   { assertThat(ones(subject,  0,       72), is(equalTo( 34))); }
		@Test public void ones_0_80()   { assertThat(ones(subject,  0,       80), is(equalTo( 40))); }
		@Test public void ones_0_88()   { assertThat(ones(subject,  0,       88), is(equalTo( 43))); }
		@Test public void ones_0_92()   { assertThat(ones(subject,  0,       92), is(equalTo( 44))); }
		@Test public void ones_0_96()   { assertThat(ones(subject,  0,       96), is(equalTo( 47))); }
		@Test public void ones_0_112()  { assertThat(ones(subject,  0,      112), is(equalTo( 56))); }
		@Test public void ones_0_120()  { assertThat(ones(subject,  0,      120), is(equalTo( 60))); }
		@Test public void ones_0_124()  { assertThat(ones(subject,  0,      124), is(equalTo( 62))); }
		@Test public void ones_0_128()  { assertThat(ones(subject,  0,      128), is(equalTo( 64))); }
		@Test public void ones_0_144()  { assertThat(ones(subject,  0,      144), is(equalTo( 68))); }
		@Test public void ones_0_160()  { assertThat(ones(subject,  0,      160), is(equalTo( 77))); }
		@Test public void ones_0_176()  { assertThat(ones(subject,  0,      176), is(equalTo( 88))); }
		@Test public void ones_0_184()  { assertThat(ones(subject,  0,      184), is(equalTo( 91))); }
		@Test public void ones_0_192()  { assertThat(ones(subject,  0,      192), is(equalTo( 95))); }
		@Test public void ones_0_224()  { assertThat(ones(subject,  0,      224), is(equalTo(113))); }
		@Test public void ones_0_240()  { assertThat(ones(subject,  0,      240), is(equalTo(118))); }
		@Test public void ones_0_248()  { assertThat(ones(subject,  0,      248), is(equalTo(123))); }
		@Test public void ones_0_288()  { assertThat(ones(subject,  0,      288), is(equalTo(150))); }
		@Test public void ones_2_4()    { assertThat(ones(subject,  2,  2 +   4), is(equalTo(  3))); }
		@Test public void ones_2_6()    { assertThat(ones(subject,  2,  2 +   6), is(equalTo(  4))); }
		@Test public void ones_2_7()    { assertThat(ones(subject,  2,  2 +   7), is(equalTo(  5))); }
		@Test public void ones_2_12()   { assertThat(ones(subject,  2,  2 +  12), is(equalTo(  6))); }
		@Test public void ones_2_14()   { assertThat(ones(subject,  2,  2 +  14), is(equalTo(  7))); }
		@Test public void ones_2_15()   { assertThat(ones(subject,  2,  2 +  15), is(equalTo(  7))); }
		@Test public void ones_2_20()   { assertThat(ones(subject,  2,  2 +  20), is(equalTo(  8))); }
		@Test public void ones_2_22()   { assertThat(ones(subject,  2,  2 +  22), is(equalTo(  8))); }
		@Test public void ones_2_23()   { assertThat(ones(subject,  2,  2 +  23), is(equalTo(  8))); }
		@Test public void ones_2_28()   { assertThat(ones(subject,  2,  2 +  28), is(equalTo( 12))); }
		@Test public void ones_2_30()   { assertThat(ones(subject,  2,  2 +  30), is(equalTo( 14))); }
		@Test public void ones_2_31()   { assertThat(ones(subject,  2,  2 +  31), is(equalTo( 15))); }
		@Test public void ones_2_36()   { assertThat(ones(subject,  2,  2 +  36), is(equalTo( 19))); }
		@Test public void ones_4_8()    { assertThat(ones(subject,  4,  4 +   8), is(equalTo(  5))); }
		@Test public void ones_4_12()   { assertThat(ones(subject,  4,  4 +  12), is(equalTo(  6))); }
		@Test public void ones_4_14()   { assertThat(ones(subject,  4,  4 +  14), is(equalTo(  6))); }
		@Test public void ones_4_24()   { assertThat(ones(subject,  4,  4 +  24), is(equalTo( 10))); }
		@Test public void ones_4_28()   { assertThat(ones(subject,  4,  4 +  28), is(equalTo( 13))); }
		@Test public void ones_4_30()   { assertThat(ones(subject,  4,  4 +  30), is(equalTo( 15))); }
		@Test public void ones_4_40()   { assertThat(ones(subject,  4,  4 +  40), is(equalTo( 19))); }
		@Test public void ones_4_44()   { assertThat(ones(subject,  4,  4 +  44), is(equalTo( 20))); }
		@Test public void ones_4_46()   { assertThat(ones(subject,  4,  4 +  46), is(equalTo( 20))); }
		@Test public void ones_4_48()   { assertThat(ones(subject,  4,  4 +  48), is(equalTo( 22))); }
		@Test public void ones_4_56()   { assertThat(ones(subject,  4,  4 +  56), is(equalTo( 29))); }
		@Test public void ones_4_60()   { assertThat(ones(subject,  4,  4 +  60), is(equalTo( 31))); }
		@Test public void ones_4_62()   { assertThat(ones(subject,  4,  4 +  62), is(equalTo( 32))); }
		@Test public void ones_4_72()   { assertThat(ones(subject,  4,  4 +  72), is(equalTo( 35))); }
		@Test public void ones_8_16()   { assertThat(ones(subject,  8,  8 +  16), is(equalTo(  4))); }
		@Test public void ones_8_24()   { assertThat(ones(subject,  8,  8 +  24), is(equalTo( 10))); }
		@Test public void ones_8_28()   { assertThat(ones(subject,  8,  8 +  28), is(equalTo( 14))); }
		@Test public void ones_8_48()   { assertThat(ones(subject,  8,  8 +  48), is(equalTo( 22))); }
		@Test public void ones_8_56()   { assertThat(ones(subject,  8,  8 +  56), is(equalTo( 28))); }
		@Test public void ones_8_60()   { assertThat(ones(subject,  8,  8 +  60), is(equalTo( 29))); }
		@Test public void ones_8_80()   { assertThat(ones(subject,  8,  8 +  80), is(equalTo( 38))); }
		@Test public void ones_8_88()   { assertThat(ones(subject,  8,  8 +  88), is(equalTo( 42))); }
		@Test public void ones_8_92()   { assertThat(ones(subject,  8,  8 +  92), is(equalTo( 45))); }
		@Test public void ones_8_112()  { assertThat(ones(subject,  8,  8 + 112), is(equalTo( 55))); }
		@Test public void ones_8_120()  { assertThat(ones(subject,  8,  8 + 120), is(equalTo( 59))); }
		@Test public void ones_8_124()  { assertThat(ones(subject,  8,  8 + 124), is(equalTo( 59))); }
		@Test public void ones_8_144()  { assertThat(ones(subject,  8,  8 + 144), is(equalTo( 67))); }
		@Test public void ones_16_32()  { assertThat(ones(subject, 16, 16 +  32), is(equalTo( 14))); }
		@Test public void ones_16_48()  { assertThat(ones(subject, 16, 16 +  48), is(equalTo( 25))); }
		@Test public void ones_16_56()  { assertThat(ones(subject, 16, 16 +  56), is(equalTo( 26))); }
		@Test public void ones_16_96()  { assertThat(ones(subject, 16, 16 +  96), is(equalTo( 48))); }
		@Test public void ones_16_112() { assertThat(ones(subject, 16, 16 + 112), is(equalTo( 56))); }
		@Test public void ones_16_120() { assertThat(ones(subject, 16, 16 + 120), is(equalTo( 56))); }
		@Test public void ones_16_160() { assertThat(ones(subject, 16, 16 + 160), is(equalTo( 80))); }
		@Test public void ones_16_176() { assertThat(ones(subject, 16, 16 + 176), is(equalTo( 87))); }
		@Test public void ones_16_184() { assertThat(ones(subject, 16, 16 + 184), is(equalTo( 94))); }
		@Test public void ones_16_224() { assertThat(ones(subject, 16, 16 + 224), is(equalTo(110))); }
		@Test public void ones_16_240() { assertThat(ones(subject, 16, 16 + 240), is(equalTo(119))); }
		@Test public void ones_16_248() { assertThat(ones(subject, 16, 16 + 248), is(equalTo(123))); }
		@Test public void ones_16_288() { assertThat(ones(subject, 16, 16 + 288), is(equalTo(155))); }
	}
	
	public static class ByteArray extends Cases<byte[]> {
		@Override public byte[] build(long... d) { return ByteArrayBuilder.build0(d); }
		@Override protected int ones(byte[] data, int from, int to) { return Ones.ones(data, from, to); }
	}
}
