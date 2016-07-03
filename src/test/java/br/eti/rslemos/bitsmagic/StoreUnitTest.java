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

import br.eti.rslemos.bitsmagic.StorageBuilder.ByteArrayBuilder;
import br.eti.rslemos.bitsmagic.StorageBuilder.CharArrayBuilder;
import br.eti.rslemos.bitsmagic.StorageBuilder.IntArrayBuilder;
import br.eti.rslemos.bitsmagic.StorageBuilder.LongArrayBuilder;
import br.eti.rslemos.bitsmagic.StorageBuilder.ShortArrayBuilder;

@RunWith(Enclosed.class)
public class StoreUnitTest {
	@Ignore
	public static abstract class Cases {
		protected abstract static class Fixture<T> implements StorageBuilder<T> {
			//                         3210987654321098765432109876543210987654321098765432109876543210
			public T subject = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
			public T source  = build(0b1100110011001001011111110111111011111011110111011101101101010101L);
		}
	
		public static abstract class Bit<T> extends Fixture<T> {
			// for bits we are only interested in the basic 8 + extreme cases
			
			protected abstract boolean readBit(T data, int i);
			protected abstract void writeBit(T data, int i, boolean v);

			@Test public void read_9() {
				assertThat(readBit(subject, -9), is(equalTo(false)));
			}
	
			@Test public void read_1() {
				assertThat(readBit(subject, -1), is(equalTo(false)));
			}
	
			@Test public void read0() {
				assertThat(readBit(subject, 0), is(equalTo(false)));
			}
	
			@Test public void read1() {
				assertThat(readBit(subject, 1), is(equalTo(true)));
			}
	
			@Test public void read2() {
				assertThat(readBit(subject, 2), is(equalTo(false)));
			}
	
			@Test public void read3() {
				assertThat(readBit(subject, 3), is(equalTo(true)));
			}
	
			@Test public void read4() {
				assertThat(readBit(subject, 4), is(equalTo(false)));
			}
	
			@Test public void read5() {
				assertThat(readBit(subject, 5), is(equalTo(true)));
			}
	
			@Test public void read6() {
				assertThat(readBit(subject, 6), is(equalTo(false)));
			}
	
			@Test public void read7() {
				assertThat(readBit(subject, 7), is(equalTo(true)));
			}
	
			@Test public void read8() {
				assertThat(readBit(subject, 8), is(equalTo(false)));
			}
	
			@Test public void read16() {
				assertThat(readBit(subject, 16), is(equalTo(false)));
			}
	
			@Test public void read17() {
				assertThat(readBit(subject, 17), is(equalTo(true)));
			}
	
			@Test public void read26() {
				assertThat(readBit(subject, 26), is(equalTo(true)));
			}
	
			@Test public void read32() {
				assertThat(readBit(subject, 32), is(equalTo(true)));
			}
	
			@Test public void read64() {
				assertThat(readBit(subject, 64), is(equalTo(false)));
			}
	
			@Test public void read65() {
				assertThat(readBit(subject, 65), is(equalTo(false)));
			}
	
			@Test public void write_9() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeBit(subject, -9, true);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_1() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeBit(subject, -1, true);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write0() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101011L);
				//                                                                                  ^
	
				writeBit(subject, 0, true);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write1() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101000L);
				//                                                                                 ^
	
				writeBit(subject, 1, false);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write2() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101110L);
				//                                                                                ^
	
				writeBit(subject, 2, true);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write3() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010100010L);
				//                                                                               ^
	
				writeBit(subject, 3, false);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write4() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010111010L);
				//                                                                              ^
	
				writeBit(subject, 4, true);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write5() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010001010L);
				//                                                                             ^
	
				writeBit(subject, 5, false);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write6() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010011101010L);
				//                                                                            ^
	
				writeBit(subject, 6, true);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write7() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010000101010L);
				//                                                                           ^
	
				writeBit(subject, 7, false);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write8() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010110101010L);
				//                                                                          ^
	
				writeBit(subject, 8, true);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write16() {
				T expected = build(0b0011001100110110100000001000000100000100001000110010010010101010L);
				//                                                                  ^
	
				writeBit(subject, 16, true);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write17() {
				T expected = build(0b0011001100110110100000001000000100000100001000000010010010101010L);
				//                                                                 ^
	
				writeBit(subject, 17, false);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write26() {
				T expected = build(0b0011001100110110100000001000000100000000001000100010010010101010L);
				//                                                        ^
	
				writeBit(subject, 26, false);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write32() {
				T expected = build(0b0011001100110110100000001000000000000100001000100010010010101010L);
				//                                                  ^
	
				writeBit(subject, 32, false);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeBit(subject, 64, true);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeBit(subject, 65, true);
				assertThat(subject, is(equalTo(expected)));
			}
		}
		
		public static abstract class Byte<T> extends Fixture<T> {
			protected abstract byte readByte(T data, int i);
			protected abstract void writeByte(T data, int i, byte v);

			@Test public void read_65() {
				assertThat(readByte(subject, -65), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_64() {
				assertThat(readByte(subject, -64), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_63() {
				assertThat(readByte(subject, -63), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_49() {
				assertThat(readByte(subject, -49), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_48() {
				assertThat(readByte(subject, -48), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_47() {
				assertThat(readByte(subject, -47), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_33() {
				assertThat(readByte(subject, -33), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_32() {
				assertThat(readByte(subject, -32), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_31() {
				assertThat(readByte(subject, -31), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_25() {
				assertThat(readByte(subject, -25), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_24() {
				assertThat(readByte(subject, -24), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_23() {
				assertThat(readByte(subject, -23), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_17() {
				assertThat(readByte(subject, -17), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_16() {
				assertThat(readByte(subject, -16), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_15() {
				assertThat(readByte(subject, -15), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_13() {
				assertThat(readByte(subject, -13), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_12() {
				assertThat(readByte(subject, -12), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_11() {
				assertThat(readByte(subject, -11), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_9() {
				assertThat(readByte(subject, -9), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_8() {
				assertThat(readByte(subject, -8), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read_7() {
				assertThat(readByte(subject, -7), is(equalTo((byte) 0b00000000)));
			}
	
			@Test public void read_5() {
				assertThat(readByte(subject, -5), is(equalTo((byte) 0b01000000)));
			}
	
			@Test public void read_4() {
				assertThat(readByte(subject, -4), is(equalTo((byte) 0b10100000)));
			}
	
			@Test public void read_3() {
				assertThat(readByte(subject, -3), is(equalTo((byte) 0b01010000)));
			}
	
			@Test public void read_1() {
				assertThat(readByte(subject, -1), is(equalTo((byte) 0b01010100)));
			}
	
			@Test public void read0() {
				assertThat(readByte(subject, 0), is(equalTo((byte) 0b10101010)));
			}
	
			@Test public void read1() {
				assertThat(readByte(subject, 1), is(equalTo((byte) 0b01010101)));
			}
	
			@Test public void read3() {
				assertThat(readByte(subject, 3), is(equalTo((byte) 0b10010101)));
			}
	
			@Test public void read4() {
				assertThat(readByte(subject, 4), is(equalTo((byte) 0b01001010)));
			}
	
			@Test public void read5() {
				assertThat(readByte(subject, 5), is(equalTo((byte) 0b00100101)));
			}
	
			@Test public void read7() {
				assertThat(readByte(subject, 7), is(equalTo((byte) 0b01001001)));
			}
	
			@Test public void read8() {
				assertThat(readByte(subject, 8), is(equalTo((byte) 0b00100100)));
			}
	
			@Test public void read9() {
				assertThat(readByte(subject, 9), is(equalTo((byte) 0b00010010)));
			}
	
			@Test public void read11() {
				assertThat(readByte(subject, 11), is(equalTo((byte) 0b01000100)));
			}
	
			@Test public void read12() {
				assertThat(readByte(subject, 12), is(equalTo((byte) 0b00100010)));
			}
	
			@Test public void read13() {
				assertThat(readByte(subject, 13), is(equalTo((byte) 0b00010001)));
			}
	
			@Test public void read15() {
				assertThat(readByte(subject, 15), is(equalTo((byte) 0b01000100)));
			}
	
			@Test public void read16() {
				assertThat(readByte(subject, 16), is(equalTo((byte) 0b00100010)));
			}
	
			@Test public void read17() {
				assertThat(readByte(subject, 17), is(equalTo((byte) 0b00010001)));
			}
	
			@Test public void read23() {
				assertThat(readByte(subject, 23), is(equalTo((byte) 0b00001000)));
			}
	
			@Test public void read24() {
				assertThat(readByte(subject, 24), is(equalTo((byte) 0b00000100)));
			}
	
			@Test public void read25() {
				assertThat(readByte(subject, 25), is(equalTo((byte) 0b10000010)));
			}
	
			@Test public void read31() {
				assertThat(readByte(subject, 31), is(equalTo((byte) 0b00000010)));
			}
	
			@Test public void read32() {
				assertThat(readByte(subject, 32), is(equalTo((byte) 0b10000001)));
			}
	
			@Test public void read33() {
				assertThat(readByte(subject, 33), is(equalTo((byte) 0b01000000)));
			}
	
			@Test public void read47() {
				assertThat(readByte(subject, 47), is(equalTo((byte) 0b01101101)));
			}
	
			@Test public void read48() {
				assertThat(readByte(subject, 48), is(equalTo((byte) 0b00110110)));
			}
	
			@Test public void read49() {
				assertThat(readByte(subject, 49), is(equalTo((byte) 0b10011011)));
			}
	
			@Test public void read63() {
				assertThat(readByte(subject, 63), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read64() {
				assertThat(readByte(subject, 64), is(equalTo((byte) 0b0)));
			}
	
			@Test public void read65() {
				assertThat(readByte(subject, 65), is(equalTo((byte) 0b0)));
			}
	
			@Test public void write_65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -65, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -64, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_63() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -63, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_49() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -49, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_48() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -48, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_47() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -47, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_33() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -33, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_32() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -32, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_31() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -31, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_25() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -25, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_24() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -24, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_23() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -23, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_17() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -17, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_16() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -16, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_15() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -15, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_13() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -13, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_12() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -12, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_11() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -11, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_9() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -9, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_8() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, -8, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_7() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101011L);
				//                                                                                  ^
	
				writeByte(subject, -7, (byte) 0b10000000);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_5() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101101L);
				//                                                                                ^^^
	
				writeByte(subject, -5, (byte) 0b10100000);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_4() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010100101L);
				//                                                                               ^^^^
	
				writeByte(subject, -4, (byte) 0b01010000);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_3() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010110101L);
				//                                                                              ^^^^^
	
				writeByte(subject, -3, (byte) 0b10101000);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_1() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010011010101L);
				//                                                                            ^^^^^^^
	
				writeByte(subject, -1, (byte) 0b10101010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write0() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010001010101L);
				//                                                                           ^^^^^^^^
	
				writeByte(subject, 0, (byte) 0b01010101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write1() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010101010100L);
				//                                                                          ^^^^^^^^
				
				writeByte(subject, 1, (byte) 0b10101010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write3() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010001101010010L);
				//                                                                        ^^^^^^^^
	
				writeByte(subject, 3, (byte) 0b01101010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write4() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010101101011010L);
				//                                                                       ^^^^^^^^
	
				writeByte(subject, 4, (byte) 0b10110101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write5() {
				T expected = build(0b0011001100110110100000001000000100000100001000100011101101001010L);
				//                                                                      ^^^^^^^^
	
				writeByte(subject, 5, (byte) 0b11011010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write7() {
				T expected = build(0b0011001100110110100000001000000100000100001000100101101100101010L);
				//                                                                    ^^^^^^^^
	
				writeByte(subject, 7, (byte) 0b10110110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write8() {
				T expected = build(0b0011001100110110100000001000000100000100001000101101101110101010L);
				//                                                                   ^^^^^^^^
	
				writeByte(subject, 8, (byte) 0b11011011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write9() {
				T expected = build(0b0011001100110110100000001000000100000100001000111101101010101010L);
				//                                                                  ^^^^^^^^
	
				writeByte(subject, 9, (byte) 0b11101101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write11() {
				T expected = build(0b0011001100110110100000001000000100000100001001011101110010101010L);
				//                                                                ^^^^^^^^
	
				writeByte(subject, 11, (byte) 0b10111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write12() {
				T expected = build(0b0011001100110110100000001000000100000100001011011101010010101010L);
				//                                                               ^^^^^^^^
	
				writeByte(subject, 12, (byte) 0b11011101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write13() {
				T expected = build(0b0011001100110110100000001000000100000100001111011100010010101010L);
				//                                                              ^^^^^^^^
	
				writeByte(subject, 13, (byte) 0b11101110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write15() {
				T expected = build(0b0011001100110110100000001000000100000100010111011010010010101010L);
				//                                                            ^^^^^^^^
	
				writeByte(subject, 15, (byte) 0b10111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write16() {
				T expected = build(0b0011001100110110100000001000000100000100110111010010010010101010L);
				//                                                           ^^^^^^^^
	
				writeByte(subject, 16, (byte) 0b11011101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write17() {
				T expected = build(0b0011001100110110100000001000000100000101110111000010010010101010L);
				//                                                          ^^^^^^^^
	
				writeByte(subject, 17, (byte) 0b11101110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write23() {
				T expected = build(0b0011001100110110100000001000000101111011101000100010010010101010L);
				//                                                    ^^^^^^^^
	
				writeByte(subject, 23, (byte) 0b11110111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write24() {
				T expected = build(0b0011001100110110100000001000000111111011001000100010010010101010L);
				//                                                   ^^^^^^^^
	
				writeByte(subject, 24, (byte) 0b11111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write25() {
				T expected = build(0b0011001100110110100000001000000011111010001000100010010010101010L);
				//                                                  ^^^^^^^^
	
				writeByte(subject, 25, (byte) 0b01111101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write31() {
				T expected = build(0b0011001100110110100000001111111010000100001000100010010010101010L);
				//                                            ^^^^^^^^
	
				writeByte(subject, 31, (byte) 0b11111101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write32() {
				T expected = build(0b0011001100110110100000000111111000000100001000100010010010101010L);
				//                                           ^^^^^^^^
	
				writeByte(subject, 32, (byte) 0b01111110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write33() {
				T expected = build(0b0011001100110110100000010111111100000100001000100010010010101010L);
				//                                          ^^^^^^^^
	
				writeByte(subject, 33, (byte) 0b10111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write47() {
				T expected = build(0b0011001101001001000000001000000100000100001000100010010010101010L);
				//                            ^^^^^^^^
	
				writeByte(subject, 47, (byte) 0b10010010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write48() {
				T expected = build(0b0011001111001001100000001000000100000100001000100010010010101010L);
				//                           ^^^^^^^^
	
				writeByte(subject, 48, (byte) 0b11001001);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write49() {
				T expected = build(0b0011001011001000100000001000000100000100001000100010010010101010L);
				//                          ^^^^^^^^
	
				writeByte(subject, 49, (byte) 0b01100100);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write63() {
				T expected = build(0b1011001100110110100000001000000100000100001000100010010010101010L);
				//                   ^
	
				writeByte(subject, 63, (byte) 0b1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, 64, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeByte(subject, 65, (byte) -1);
				assertThat(subject, is(equalTo(expected)));
			}
		}
		
		public static abstract class Char<T> extends Fixture<T> {
			protected abstract char readChar(T data, int i);
			protected abstract void writeChar(T data, int i, char v);
			
			@Test public void read_65() {
				assertThat(readChar(subject, -65), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_64() {
				assertThat(readChar(subject, -64), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_63() {
				assertThat(readChar(subject, -63), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_49() {
				assertThat(readChar(subject, -49), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_48() {
				assertThat(readChar(subject, -48), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_47() {
				assertThat(readChar(subject, -47), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_33() {
				assertThat(readChar(subject, -33), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_32() {
				assertThat(readChar(subject, -32), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_31() {
				assertThat(readChar(subject, -31), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_25() {
				assertThat(readChar(subject, -25), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_24() {
				assertThat(readChar(subject, -24), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_23() {
				assertThat(readChar(subject, -23), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_17() {
				assertThat(readChar(subject, -17), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_16() {
				assertThat(readChar(subject, -16), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_15() {
				assertThat(readChar(subject, -15), is(equalTo((char) 0b0)));
			}
	
			@Test public void read_13() {
				assertThat(readChar(subject, -13), is(equalTo((char) 0b0100000000000000)));
			}
	
			@Test public void read_12() {
				assertThat(readChar(subject, -12), is(equalTo((char) 0b1010000000000000)));
			}
	
			@Test public void read_11() {
				assertThat(readChar(subject, -11), is(equalTo((char) 0b0101000000000000)));
			}
	
			@Test public void read_9() {
				assertThat(readChar(subject, -9), is(equalTo((char) 0b0101010000000000)));
			}
	
			@Test public void read_8() {
				assertThat(readChar(subject, -8), is(equalTo((char) 0b1010101000000000)));
			}
	
			@Test public void read_7() {
				assertThat(readChar(subject, -7), is(equalTo((char) 0b0101010100000000)));
			}
	
			@Test public void read_5() {
				assertThat(readChar(subject, -5), is(equalTo((char) 0b1001010101000000)));
			}
	
			@Test public void read_4() {
				assertThat(readChar(subject, -4), is(equalTo((char) 0b0100101010100000)));
			}
	
			@Test public void read_3() {
				assertThat(readChar(subject, -3), is(equalTo((char) 0b0010010101010000)));
			}
	
			@Test public void read_1() {
				assertThat(readChar(subject, -1), is(equalTo((char) 0b0100100101010100)));
			}
	
			@Test public void read0() {
				assertThat(readChar(subject, 0), is(equalTo((char) 0b0010010010101010)));
			}
	
			@Test public void read1() {
				assertThat(readChar(subject, 1), is(equalTo((char) 0b0001001001010101)));
			}
	
			@Test public void read3() {
				assertThat(readChar(subject, 3), is(equalTo((char) 0b0100010010010101)));
			}
	
			@Test public void read4() {
				assertThat(readChar(subject, 4), is(equalTo((char) 0b0010001001001010)));
			}
	
			@Test public void read5() {
				assertThat(readChar(subject, 5), is(equalTo((char) 0b0001000100100101)));
			}
	
			@Test public void read7() {
				assertThat(readChar(subject, 7), is(equalTo((char) 0b0100010001001001)));
			}
	
			@Test public void read8() {
				assertThat(readChar(subject, 8), is(equalTo((char) 0b0010001000100100)));
			}
	
			@Test public void read9() {
				assertThat(readChar(subject, 9), is(equalTo((char) 0b0001000100010010)));
			}
	
			@Test public void read11() {
				assertThat(readChar(subject, 11), is(equalTo((char) 0b1000010001000100)));
			}
	
			@Test public void read12() {
				assertThat(readChar(subject, 12), is(equalTo((char) 0b0100001000100010)));
			}
	
			@Test public void read13() {
				assertThat(readChar(subject, 13), is(equalTo((char) 0b0010000100010001)));
			}
	
			@Test public void read15() {
				assertThat(readChar(subject, 15), is(equalTo((char) 0b0000100001000100)));
			}
	
			@Test public void read16() {
				assertThat(readChar(subject, 16), is(equalTo((char) 0b0000010000100010)));
			}
	
			@Test public void read17() {
				assertThat(readChar(subject, 17), is(equalTo((char) 0b1000001000010001)));
			}
	
			@Test public void read23() {
				assertThat(readChar(subject, 23), is(equalTo((char) 0b0000001000001000)));
			}
	
			@Test public void read24() {
				assertThat(readChar(subject, 24), is(equalTo((char) 0b1000000100000100)));
			}
	
			@Test public void read25() {
				assertThat(readChar(subject, 25), is(equalTo((char) 0b0100000010000010)));
			}
	
			@Test public void read31() {
				assertThat(readChar(subject, 31), is(equalTo((char) 0b0000000100000010)));
			}
	
			@Test public void read32() {
				assertThat(readChar(subject, 32), is(equalTo((char) 0b1000000010000001)));
			}
	
			@Test public void read33() {
				assertThat(readChar(subject, 33), is(equalTo((char) 0b0100000001000000)));
			}
	
			@Test public void read47() {
				assertThat(readChar(subject, 47), is(equalTo((char) 0b0110011001101101)));
			}
	
			@Test public void read48() {
				assertThat(readChar(subject, 48), is(equalTo((char) 0b0011001100110110)));
			}
	
			@Test public void read49() {
				assertThat(readChar(subject, 49), is(equalTo((char) 0b001100110011011)));
			}
	
			@Test public void read56() {
				assertThat(readChar(subject, 56), is(equalTo((char) 0b00110011)));
			}
	
			@Test public void read63() {
				assertThat(readChar(subject, 63), is(equalTo((char) 0b0)));
			}
	
			@Test public void read64() {
				assertThat(readChar(subject, 64), is(equalTo((char) 0b0)));
			}
	
			@Test public void read65() {
				assertThat(readChar(subject, 65), is(equalTo((char) 0b0)));
			}
	
			@Test public void write_65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -65, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -64, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_63() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -63, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_49() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -49, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_48() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -48, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_47() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -47, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_33() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -33, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_32() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -32, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_31() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -31, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_25() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -25, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_24() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -24, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_23() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -23, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_17() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -17, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_16() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, -16, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_15() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101011L);
				//                                                                                  ^ 
	
				writeChar(subject, -15, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_13() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101101L);
				//                                                                                ^^^ 
	
				writeChar(subject, -13, (char) 0b1011111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_12() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010100101L);
				//                                                                               ^^^^ 
	
				writeChar(subject, -12, (char) 0b0101111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_11() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010110101L);
				//                                                                              ^^^^^ 
	
				writeChar(subject, -11, (char) 0b1010111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_9() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010011010101L);
				//                                                                            ^^^^^^^ 
	
				writeChar(subject, -9, (char) 0b1010101111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_8() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010001010101L);
				//                                                                           ^^^^^^^^
	
				writeChar(subject, -8, (char) 0b0101010111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_7() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010101010101L);
				//                                                                          ^^^^^^^^^
	
				writeChar(subject, -7, (char) 0b1010101011111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_5() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010001101010101L);
				//                                                                        ^^^^^^^^^^^
	
				writeChar(subject, -5, (char) 0b0110101010111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_4() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010101101010101L);
				//                                                                       ^^^^^^^^^^^^
	
				writeChar(subject, -4, (char) 0b1011010101011111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_3() {
				T expected = build(0b0011001100110110100000001000000100000100001000100011101101010101L);
				//                                                                      ^^^^^^^^^^^^^
	
				writeChar(subject, -3, (char) 0b1101101010101111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_1() {
				T expected = build(0b0011001100110110100000001000000100000100001000100101101111010101L);
				//                                                                    ^^^^^^^^^^^^^^^
	
				writeChar(subject, -1, (char) 0b1011011110101011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write0() {
				T expected = build(0b0011001100110110100000001000000100000100001000101101101101010101L);
				//                                                                   ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 0, (char) 0b1101101101010101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write1() {
				T expected = build(0b0011001100110110100000001000000100000100001000111101101101010100L);
				//                                                                  ^^^^^^^^^^^^^^^^
				
				writeChar(subject, 1, (char) 0b1110110110101010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write3() {
				T expected = build(0b0011001100110110100000001000000100000100001001011101101101010010L);
				//                                                                ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 3, (char) 0b1011101101101010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write4() {
				T expected = build(0b0011001100110110100000001000000100000100001011011101101101011010L);
				//                                                               ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 4, (char) 0b1101110110110101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write5() {
				T expected = build(0b0011001100110110100000001000000100000100001111011101101101001010L);
				//                                                              ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 5, (char) 0b1110111011011010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write7() {
				T expected = build(0b0011001100110110100000001000000100000100010111011101101100101010L);
				//                                                            ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 7, (char) 0b1011101110110110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write8() {
				T expected = build(0b0011001100110110100000001000000100000100110111011101101110101010L);
				//                                                           ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 8, (char) 0b1101110111011011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write9() {
				T expected = build(0b0011001100110110100000001000000100000101110111011101101010101010L);
				//                                                          ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 9, (char) 0b1110111011101101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write11() {
				T expected = build(0b0011001100110110100000001000000100000011110111011101110010101010L);
				//                                                        ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 11, (char) 0b0111101110111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write12() {
				T expected = build(0b0011001100110110100000001000000100001011110111011101010010101010L);
				//                                                       ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 12, (char) 0b1011110111011101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write13() {
				T expected = build(0b0011001100110110100000001000000100011011110111011100010010101010L);
				//                                                      ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 13, (char) 0b1101111011101110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write15() {
				T expected = build(0b0011001100110110100000001000000101111011110111011010010010101010L);
				//                                                    ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 15, (char) 0b1111011110111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write16() {
				T expected = build(0b0011001100110110100000001000000111111011110111010010010010101010L);
				//                                                   ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 16, (char) 0b1111101111011101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write17() {
				T expected = build(0b0011001100110110100000001000000011111011110111000010010010101010L);
				//                                                  ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 17, (char) 0b0111110111101110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write23() {
				T expected = build(0b0011001100110110100000001111111011111011101000100010010010101010L);
				//                                            ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 23, (char) 0b1111110111110111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write24() {
				T expected = build(0b0011001100110110100000000111111011111011001000100010010010101010L);
				//                                           ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 24, (char) 0b0111111011111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write25() {
				T expected = build(0b0011001100110110100000010111111011111010001000100010010010101010L);
				//                                          ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 25, (char) 0b1011111101111101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write31() {
				T expected = build(0b0011001100110110111111110111111010000100001000100010010010101010L);
				//                                    ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 31, (char) 0b1111111011111101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write32() {
				T expected = build(0b0011001100110110011111110111111000000100001000100010010010101010L);
				//                                   ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 32, (char) 0b0111111101111110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write33() {
				T expected = build(0b0011001100110111011111110111111100000100001000100010010010101010L);
				//                                  ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 33, (char) 0b1011111110111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write47() {
				T expected = build(0b0100110011001001000000001000000100000100001000100010010010101010L);
				//                    ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 47, (char) 0b1001100110010010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write48() {
				T expected = build(0b1100110011001001100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^
	
				writeChar(subject, 48, (char) 0b1100110011001001);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write49() {
				T expected = build(0b1100110011001000100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^
	
				writeChar(subject, 49, (char) 0b1110011001100100);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write56() {
				T expected = build(0b1100110000110110100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^
	
				writeChar(subject, 56, (char) 0b11001100);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write63() {
				T expected = build(0b1011001100110110100000001000000100000100001000100010010010101010L);
				//                   ^
	
				writeChar(subject, 63, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, 64, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeChar(subject, 65, (char) -1);
				assertThat(subject, is(equalTo(expected)));
			}
		}
		
		public static abstract class Short<T> extends Fixture<T> {
			protected abstract short readShort(T data, int i);
			protected abstract void writeShort(T data, int i, short v);
			
			@Test public void read_65() {
				assertThat(readShort(subject, -65), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_64() {
				assertThat(readShort(subject, -64), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_63() {
				assertThat(readShort(subject, -63), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_49() {
				assertThat(readShort(subject, -49), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_48() {
				assertThat(readShort(subject, -48), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_47() {
				assertThat(readShort(subject, -47), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_33() {
				assertThat(readShort(subject, -33), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_32() {
				assertThat(readShort(subject, -32), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_31() {
				assertThat(readShort(subject, -31), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_25() {
				assertThat(readShort(subject, -25), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_24() {
				assertThat(readShort(subject, -24), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_23() {
				assertThat(readShort(subject, -23), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_17() {
				assertThat(readShort(subject, -17), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_16() {
				assertThat(readShort(subject, -16), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_15() {
				assertThat(readShort(subject, -15), is(equalTo((short) 0b0)));
			}
	
			@Test public void read_13() {
				assertThat(readShort(subject, -13), is(equalTo((short) 0b0100000000000000)));
			}
	
			@Test public void read_12() {
				assertThat(readShort(subject, -12), is(equalTo((short) 0b1010000000000000)));
			}
	
			@Test public void read_11() {
				assertThat(readShort(subject, -11), is(equalTo((short) 0b0101000000000000)));
			}
	
			@Test public void read_9() {
				assertThat(readShort(subject, -9), is(equalTo((short) 0b0101010000000000)));
			}
	
			@Test public void read_8() {
				assertThat(readShort(subject, -8), is(equalTo((short) 0b1010101000000000)));
			}
	
			@Test public void read_7() {
				assertThat(readShort(subject, -7), is(equalTo((short) 0b0101010100000000)));
			}
	
			@Test public void read_5() {
				assertThat(readShort(subject, -5), is(equalTo((short) 0b1001010101000000)));
			}
	
			@Test public void read_4() {
				assertThat(readShort(subject, -4), is(equalTo((short) 0b0100101010100000)));
			}
	
			@Test public void read_3() {
				assertThat(readShort(subject, -3), is(equalTo((short) 0b0010010101010000)));
			}
	
			@Test public void read_1() {
				assertThat(readShort(subject, -1), is(equalTo((short) 0b0100100101010100)));
			}
	
			@Test public void read0() {
				assertThat(readShort(subject, 0), is(equalTo((short) 0b0010010010101010)));
			}
	
			@Test public void read1() {
				assertThat(readShort(subject, 1), is(equalTo((short) 0b0001001001010101)));
			}
	
			@Test public void read3() {
				assertThat(readShort(subject, 3), is(equalTo((short) 0b0100010010010101)));
			}
	
			@Test public void read4() {
				assertThat(readShort(subject, 4), is(equalTo((short) 0b0010001001001010)));
			}
	
			@Test public void read5() {
				assertThat(readShort(subject, 5), is(equalTo((short) 0b0001000100100101)));
			}
	
			@Test public void read7() {
				assertThat(readShort(subject, 7), is(equalTo((short) 0b0100010001001001)));
			}
	
			@Test public void read8() {
				assertThat(readShort(subject, 8), is(equalTo((short) 0b0010001000100100)));
			}
	
			@Test public void read9() {
				assertThat(readShort(subject, 9), is(equalTo((short) 0b0001000100010010)));
			}
	
			@Test public void read11() {
				assertThat(readShort(subject, 11), is(equalTo((short) 0b1000010001000100)));
			}
	
			@Test public void read12() {
				assertThat(readShort(subject, 12), is(equalTo((short) 0b0100001000100010)));
			}
	
			@Test public void read13() {
				assertThat(readShort(subject, 13), is(equalTo((short) 0b0010000100010001)));
			}
	
			@Test public void read15() {
				assertThat(readShort(subject, 15), is(equalTo((short) 0b0000100001000100)));
			}
	
			@Test public void read16() {
				assertThat(readShort(subject, 16), is(equalTo((short) 0b0000010000100010)));
			}
	
			@Test public void read17() {
				assertThat(readShort(subject, 17), is(equalTo((short) 0b1000001000010001)));
			}
	
			@Test public void read23() {
				assertThat(readShort(subject, 23), is(equalTo((short) 0b0000001000001000)));
			}
	
			@Test public void read24() {
				assertThat(readShort(subject, 24), is(equalTo((short) 0b1000000100000100)));
			}
	
			@Test public void read25() {
				assertThat(readShort(subject, 25), is(equalTo((short) 0b0100000010000010)));
			}
	
			@Test public void read31() {
				assertThat(readShort(subject, 31), is(equalTo((short) 0b0000000100000010)));
			}
	
			@Test public void read32() {
				assertThat(readShort(subject, 32), is(equalTo((short) 0b1000000010000001)));
			}
	
			@Test public void read33() {
				assertThat(readShort(subject, 33), is(equalTo((short) 0b0100000001000000)));
			}
	
			@Test public void read47() {
				assertThat(readShort(subject, 47), is(equalTo((short) 0b0110011001101101)));
			}
	
			@Test public void read48() {
				assertThat(readShort(subject, 48), is(equalTo((short) 0b0011001100110110)));
			}
	
			@Test public void read49() {
				assertThat(readShort(subject, 49), is(equalTo((short) 0b001100110011011)));
			}
	
			@Test public void read56() {
				assertThat(readShort(subject, 56), is(equalTo((short) 0b00110011)));
			}
	
			@Test public void read63() {
				assertThat(readShort(subject, 63), is(equalTo((short) 0b0)));
			}
	
			@Test public void read64() {
				assertThat(readShort(subject, 64), is(equalTo((short) 0b0)));
			}
	
			@Test public void read65() {
				assertThat(readShort(subject, 65), is(equalTo((short) 0b0)));
			}
	
			@Test public void write_65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -65, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -64, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_63() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -63, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_49() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -49, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_48() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -48, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_47() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -47, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_33() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -33, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_32() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -32, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_31() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -31, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_25() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -25, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_24() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -24, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_23() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -23, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_17() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -17, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_16() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, -16, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_15() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101011L);
				//                                                                                  ^ 
	
				writeShort(subject, -15, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_13() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101101L);
				//                                                                                ^^^ 
	
				writeShort(subject, -13, (short) 0b1011111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_12() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010100101L);
				//                                                                               ^^^^ 
	
				writeShort(subject, -12, (short) 0b0101111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_11() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010110101L);
				//                                                                              ^^^^^ 
	
				writeShort(subject, -11, (short) 0b1010111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_9() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010011010101L);
				//                                                                            ^^^^^^^ 
	
				writeShort(subject, -9, (short) 0b1010101111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_8() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010001010101L);
				//                                                                           ^^^^^^^^
	
				writeShort(subject, -8, (short) 0b0101010111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_7() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010101010101L);
				//                                                                          ^^^^^^^^^
	
				writeShort(subject, -7, (short) 0b1010101011111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_5() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010001101010101L);
				//                                                                        ^^^^^^^^^^^
	
				writeShort(subject, -5, (short) 0b0110101010111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_4() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010101101010101L);
				//                                                                       ^^^^^^^^^^^^
	
				writeShort(subject, -4, (short) 0b1011010101011111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_3() {
				T expected = build(0b0011001100110110100000001000000100000100001000100011101101010101L);
				//                                                                      ^^^^^^^^^^^^^
	
				writeShort(subject, -3, (short) 0b1101101010101111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_1() {
				T expected = build(0b0011001100110110100000001000000100000100001000100101101111010101L);
				//                                                                    ^^^^^^^^^^^^^^^
	
				writeShort(subject, -1, (short) 0b1011011110101011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write0() {
				T expected = build(0b0011001100110110100000001000000100000100001000101101101101010101L);
				//                                                                   ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 0, (short) 0b1101101101010101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write1() {
				T expected = build(0b0011001100110110100000001000000100000100001000111101101101010100L);
				//                                                                  ^^^^^^^^^^^^^^^^
				
				writeShort(subject, 1, (short) 0b1110110110101010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write3() {
				T expected = build(0b0011001100110110100000001000000100000100001001011101101101010010L);
				//                                                                ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 3, (short) 0b1011101101101010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write4() {
				T expected = build(0b0011001100110110100000001000000100000100001011011101101101011010L);
				//                                                               ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 4, (short) 0b1101110110110101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write5() {
				T expected = build(0b0011001100110110100000001000000100000100001111011101101101001010L);
				//                                                              ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 5, (short) 0b1110111011011010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write7() {
				T expected = build(0b0011001100110110100000001000000100000100010111011101101100101010L);
				//                                                            ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 7, (short) 0b1011101110110110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write8() {
				T expected = build(0b0011001100110110100000001000000100000100110111011101101110101010L);
				//                                                           ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 8, (short) 0b1101110111011011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write9() {
				T expected = build(0b0011001100110110100000001000000100000101110111011101101010101010L);
				//                                                          ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 9, (short) 0b1110111011101101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write11() {
				T expected = build(0b0011001100110110100000001000000100000011110111011101110010101010L);
				//                                                        ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 11, (short) 0b0111101110111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write12() {
				T expected = build(0b0011001100110110100000001000000100001011110111011101010010101010L);
				//                                                       ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 12, (short) 0b1011110111011101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write13() {
				T expected = build(0b0011001100110110100000001000000100011011110111011100010010101010L);
				//                                                      ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 13, (short) 0b1101111011101110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write15() {
				T expected = build(0b0011001100110110100000001000000101111011110111011010010010101010L);
				//                                                    ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 15, (short) 0b1111011110111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write16() {
				T expected = build(0b0011001100110110100000001000000111111011110111010010010010101010L);
				//                                                   ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 16, (short) 0b1111101111011101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write17() {
				T expected = build(0b0011001100110110100000001000000011111011110111000010010010101010L);
				//                                                  ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 17, (short) 0b0111110111101110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write23() {
				T expected = build(0b0011001100110110100000001111111011111011101000100010010010101010L);
				//                                            ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 23, (short) 0b1111110111110111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write24() {
				T expected = build(0b0011001100110110100000000111111011111011001000100010010010101010L);
				//                                           ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 24, (short) 0b0111111011111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write25() {
				T expected = build(0b0011001100110110100000010111111011111010001000100010010010101010L);
				//                                          ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 25, (short) 0b1011111101111101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write31() {
				T expected = build(0b0011001100110110111111110111111010000100001000100010010010101010L);
				//                                    ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 31, (short) 0b1111111011111101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write32() {
				T expected = build(0b0011001100110110011111110111111000000100001000100010010010101010L);
				//                                   ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 32, (short) 0b0111111101111110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write33() {
				T expected = build(0b0011001100110111011111110111111100000100001000100010010010101010L);
				//                                  ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 33, (short) 0b1011111110111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write47() {
				T expected = build(0b0100110011001001000000001000000100000100001000100010010010101010L);
				//                    ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 47, (short) 0b1001100110010010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write48() {
				T expected = build(0b1100110011001001100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^
	
				writeShort(subject, 48, (short) 0b1100110011001001);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write49() {
				T expected = build(0b1100110011001000100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^
	
				writeShort(subject, 49, (short) 0b1110011001100100);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write56() {
				T expected = build(0b1100110000110110100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^
	
				writeShort(subject, 56, (short) 0b11001100);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write63() {
				T expected = build(0b1011001100110110100000001000000100000100001000100010010010101010L);
				//                   ^
	
				writeShort(subject, 63, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, 64, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeShort(subject, 65, (short) -1);
				assertThat(subject, is(equalTo(expected)));
			}
		}
		
		public static abstract class Int<T> extends Fixture<T> {
			protected abstract int readInt(T data, int i);
			protected abstract void writeInt(T data, int i, int v);
			
			@Test public void read_65() {
				assertThat(readInt(subject, -65), is(equalTo((int) 0b0)));
			}
	
			@Test public void read_64() {
				assertThat(readInt(subject, -64), is(equalTo((int) 0b0)));
			}
	
			@Test public void read_63() {
				assertThat(readInt(subject, -63), is(equalTo((int) 0b0)));
			}
	
			@Test public void read_49() {
				assertThat(readInt(subject, -49), is(equalTo((int) 0b0)));
			}
	
			@Test public void read_48() {
				assertThat(readInt(subject, -48), is(equalTo((int) 0b0)));
			}
	
			@Test public void read_47() {
				assertThat(readInt(subject, -47), is(equalTo((int) 0b0)));
			}
	
			@Test public void read_33() {
				assertThat(readInt(subject, -33), is(equalTo((int) 0b0)));
			}
	
			@Test public void read_32() {
				assertThat(readInt(subject, -32), is(equalTo((int) 0b0)));
			}
	
			@Test public void read_31() {
				assertThat(readInt(subject, -31), is(equalTo((int) 0b00000000000000000000000000000000)));
			}
	
			@Test public void read_25() {
				assertThat(readInt(subject, -25), is(equalTo((int) 0b01010100000000000000000000000000)));
			}
	
			@Test public void read_24() {
				assertThat(readInt(subject, -24), is(equalTo((int) 0b10101010000000000000000000000000)));
			}
	
			@Test public void read_23() {
				assertThat(readInt(subject, -23), is(equalTo((int) 0b01010101000000000000000000000000)));
			}
	
			@Test public void read_17() {
				assertThat(readInt(subject, -17), is(equalTo((int) 0b01001001010101000000000000000000)));
			}
	
			@Test public void read_16() {
				assertThat(readInt(subject, -16), is(equalTo((int) 0b00100100101010100000000000000000)));
			}
	
			@Test public void read_15() {
				assertThat(readInt(subject, -15), is(equalTo((int) 0b00010010010101010000000000000000)));
			}
	
			@Test public void read_13() {
				assertThat(readInt(subject, -13), is(equalTo((int) 0b01000100100101010100000000000000)));
			}
	
			@Test public void read_12() {
				assertThat(readInt(subject, -12), is(equalTo((int) 0b00100010010010101010000000000000)));
			}
	
			@Test public void read_11() {
				assertThat(readInt(subject, -11), is(equalTo((int) 0b00010001001001010101000000000000)));
			}
	
			@Test public void read_9() {
				assertThat(readInt(subject, -9), is(equalTo((int) 0b01000100010010010101010000000000)));
			}
	
			@Test public void read_8() {
				assertThat(readInt(subject, -8), is(equalTo((int) 0b00100010001001001010101000000000)));
			}
	
			@Test public void read_7() {
				assertThat(readInt(subject, -7), is(equalTo((int) 0b00010001000100100101010100000000)));
			}
	
			@Test public void read_5() {
				assertThat(readInt(subject, -5), is(equalTo((int) 0b10000100010001001001010101000000)));
			}
	
			@Test public void read_4() {
				assertThat(readInt(subject, -4), is(equalTo((int) 0b01000010001000100100101010100000)));
			}
	
			@Test public void read_3() {
				assertThat(readInt(subject, -3), is(equalTo((int) 0b00100001000100010010010101010000)));
			}
	
			@Test public void read_1() {
				assertThat(readInt(subject, -1), is(equalTo((int) 0b00001000010001000100100101010100)));
			}
	
			@Test public void read0() {
				assertThat(readInt(subject, 0), is(equalTo((int) 0b00000100001000100010010010101010)));
			}
	
			@Test public void read1() {
				assertThat(readInt(subject, 1), is(equalTo((int) 0b10000010000100010001001001010101)));
			}
	
			@Test public void read3() {
				assertThat(readInt(subject, 3), is(equalTo((int) 0b00100000100001000100010010010101)));
			}
	
			@Test public void read4() {
				assertThat(readInt(subject, 4), is(equalTo((int) 0b00010000010000100010001001001010)));
			}
	
			@Test public void read5() {
				assertThat(readInt(subject, 5), is(equalTo((int) 0b00001000001000010001000100100101)));
			}
	
			@Test public void read7() {
				assertThat(readInt(subject, 7), is(equalTo((int) 0b00000010000010000100010001001001)));
			}
	
			@Test public void read8() {
				assertThat(readInt(subject, 8), is(equalTo((int) 0b10000001000001000010001000100100)));
			}
	
			@Test public void read9() {
				assertThat(readInt(subject, 9), is(equalTo((int) 0b01000000100000100001000100010010)));
			}
	
			@Test public void read11() {
				assertThat(readInt(subject, 11), is(equalTo((int) 0b00010000001000001000010001000100)));
			}
	
			@Test public void read12() {
				assertThat(readInt(subject, 12), is(equalTo((int) 0b00001000000100000100001000100010)));
			}
	
			@Test public void read13() {
				assertThat(readInt(subject, 13), is(equalTo((int) 0b00000100000010000010000100010001)));
			}
	
			@Test public void read15() {
				assertThat(readInt(subject, 15), is(equalTo((int) 0b00000001000000100000100001000100)));
			}
	
			@Test public void read16() {
				assertThat(readInt(subject, 16), is(equalTo((int) 0b10000000100000010000010000100010)));
			}
	
			@Test public void read17() {
				assertThat(readInt(subject, 17), is(equalTo((int) 0b01000000010000001000001000010001)));
			}
	
			@Test public void read23() {
				assertThat(readInt(subject, 23), is(equalTo((int) 0b01101101000000010000001000001000)));
			}
	
			@Test public void read24() {
				assertThat(readInt(subject, 24), is(equalTo((int) 0b00110110100000001000000100000100)));
			}
	
			@Test public void read25() {
				assertThat(readInt(subject, 25), is(equalTo((int) 0b10011011010000000100000010000010)));
			}
	
			@Test public void read31() {
				assertThat(readInt(subject, 31), is(equalTo((int) 0b01100110011011010000000100000010)));
			}
	
			@Test public void read32() {
				assertThat(readInt(subject, 32), is(equalTo((int) 0b00110011001101101000000010000001)));
			}
	
			@Test public void read33() {
				assertThat(readInt(subject, 33), is(equalTo((int) 0b0011001100110110100000001000000)));
			}
	
			@Test public void read40() {
				assertThat(readInt(subject, 40), is(equalTo((int) 0b001100110011011010000000)));
			}
	
			@Test public void read47() {
				assertThat(readInt(subject, 47), is(equalTo((int) 0b00110011001101101)));
			}
	
			@Test public void read48() {
				assertThat(readInt(subject, 48), is(equalTo((int) 0b0011001100110110)));
			}
	
			@Test public void read49() {
				assertThat(readInt(subject, 49), is(equalTo((int) 0b001100110011011)));
			}
	
			@Test public void read56() {
				assertThat(readInt(subject, 56), is(equalTo((int) 0b00110011)));
			}
	
			@Test public void read63() {
				assertThat(readInt(subject, 63), is(equalTo((int) 0b0)));
			}
	
			@Test public void read64() {
				assertThat(readInt(subject, 64), is(equalTo((int) 0b0)));
			}
	
			@Test public void read65() {
				assertThat(readInt(subject, 65), is(equalTo((int) 0b0)));
			}
	
			@Test public void write_65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, -65, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, -64, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_63() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, -63, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_49() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, -49, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_48() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, -48, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_47() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, -47, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_33() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, -33, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_32() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, -32, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_31() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101011L);
				//                                                                                  ^ 
	
				writeInt(subject, -31, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_25() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010011010101L);
				//                                                                            ^^^^^^^ 
	
				writeInt(subject, -25, (int) 0b10101011111111111111111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_24() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010001010101L);
				//                                                                           ^^^^^^^^ 
	
				writeInt(subject, -24, (int) 0b01010101111111111111111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_23() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010101010101L);
				//                                                                          ^^^^^^^^^ 
	
				writeInt(subject, -23, (int) 0b10101010111111111111111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_17() {
				T expected = build(0b0011001100110110100000001000000100000100001000100101101101010101L);
				//                                                                    ^^^^^^^^^^^^^^^ 
	
				writeInt(subject, -17, (int) 0b10110110101010111111111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_16() {
				T expected = build(0b0011001100110110100000001000000100000100001000101101101101010101L);
				//                                                                   ^^^^^^^^^^^^^^^^ 
	
				writeInt(subject, -16, (int) 0b11011011010101011111111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_15() {
				T expected = build(0b0011001100110110100000001000000100000100001000111101101101010101L);
				//                                                                  ^^^^^^^^^^^^^^^^^ 
	
				writeInt(subject, -15, (int) 0b11101101101010101111111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_13() {
				T expected = build(0b0011001100110110100000001000000100000100001001011101101101010101L);
				//                                                                ^^^^^^^^^^^^^^^^^^^ 
	
				writeInt(subject, -13, (int) 0b10111011011010101011111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_12() {
				T expected = build(0b0011001100110110100000001000000100000100001011011101101101010101L);
				//                                                               ^^^^^^^^^^^^^^^^^^^^ 
	
				writeInt(subject, -12, (int) 0b11011101101101010101111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_11() {
				T expected = build(0b0011001100110110100000001000000100000100001111011101101101010101L);
				//                                                              ^^^^^^^^^^^^^^^^^^^^^ 
	
				writeInt(subject, -11, (int) 0b11101110110110101010111111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_9() {
				T expected = build(0b0011001100110110100000001000000100000100010111011101101101010101L);
				//                                                            ^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeInt(subject, -9, (int) 0b10111011101101101010101111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_8() {
				T expected = build(0b0011001100110110100000001000000100000100110111011101101101010101L);
				//                                                           ^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, -8, (int) 0b11011101110110110101010111111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_7() {
				T expected = build(0b0011001100110110100000001000000100000101110111011101101101010101L);
				//                                                          ^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, -7, (int) 0b11101110111011011010101011111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_5() {
				T expected = build(0b0011001100110110100000001000000100000011110111011101101101010101L);
				//                                                        ^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, -5, (int) 0b01111011101110110110101010111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_4() {
				T expected = build(0b0011001100110110100000001000000100001011110111011101101101010101L);
				//                                                       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, -4, (int) 0b10111101110111011011010101011111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_3() {
				T expected = build(0b0011001100110110100000001000000100011011110111011101101101010101L);
				//                                                      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, -3, (int) 0b11011110111011101101101010101111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_1() {
				T expected = build(0b0011001100110110100000001000000101111011110111011101101111010101L);
				//                                                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, -1, (int) 0b11110111101110111011011110101011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write0() {
				T expected = build(0b0011001100110110100000001000000111111011110111011101101101010101L);
				//                                                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 0, (int) 0b11111011110111011101101101010101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write1() {
				T expected = build(0b0011001100110110100000001000000011111011110111011101101101010100L);
				//                                                  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
				
				writeInt(subject, 1, (int) 0b01111101111011101110110110101010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write3() {
				T expected = build(0b0011001100110110100000001000011011111011110111011101101101010010L);
				//                                                ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 3, (int) 0b11011111011110111011101101101010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write4() {
				T expected = build(0b0011001100110110100000001000111011111011110111011101101101011010L);
				//                                               ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 4, (int) 0b11101111101111011101110110110101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write5() {
				T expected = build(0b0011001100110110100000001001111011111011110111011101101101001010L);
				//                                              ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 5, (int) 0b11110111110111101110111011011010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write7() {
				T expected = build(0b0011001100110110100000001111111011111011110111011101101100101010L);
				//                                            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 7, (int) 0b11111101111101111011101110110110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write8() {
				T expected = build(0b0011001100110110100000000111111011111011110111011101101110101010L);
				//                                           ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 8, (int) 0b01111110111110111101110111011011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write9() {
				T expected = build(0b0011001100110110100000010111111011111011110111011101101010101010L);
				//                                          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 9, (int) 0b10111111011111011110111011101101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write11() {
				T expected = build(0b0011001100110110100001110111111011111011110111011101110010101010L);
				//                                        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 11, (int) 0b11101111110111110111101110111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write12() {
				T expected = build(0b0011001100110110100011110111111011111011110111011101010010101010L);
				//                                       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 12, (int) 0b11110111111011111011110111011101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write13() {
				T expected = build(0b0011001100110110100111110111111011111011110111011100010010101010L);
				//                                      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 13, (int) 0b11111011111101111101111011101110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write15() {
				T expected = build(0b0011001100110110111111110111111011111011110111011010010010101010L);
				//                                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 15, (int) 0b11111110111111011111011110111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write16() {
				T expected = build(0b0011001100110110011111110111111011111011110111010010010010101010L);
				//                                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 16, (int) 0b01111111011111101111101111011101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write17() {
				T expected = build(0b0011001100110111011111110111111011111011110111000010010010101010L);
				//                                  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 17, (int) 0b10111111101111110111110111101110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write23() {
				T expected = build(0b0011001101001001011111110111111011111011101000100010010010101010L);
				//                            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 23, (int) 0b10010010111111101111110111110111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write24() {
				T expected = build(0b0011001111001001011111110111111011111011001000100010010010101010L);
				//                           ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 24, (int) 0b11001001011111110111111011111011);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write25() {
				T expected = build(0b0011001011001001011111110111111011111010001000100010010010101010L);
				//                          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 25, (int) 0b01100100101111111011111101111101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write31() {
				T expected = build(0b0100110011001001011111110111111010000100001000100010010010101010L);
				//                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 31, (int) 0b10011001100100101111111011111101);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write32() {
				T expected = build(0b1100110011001001011111110111111000000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 32, (int) 0b11001100110010010111111101111110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write33() {
				T expected = build(0b1100110011001001011111110111111100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 33, (int) 0b11100110011001001011111110111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write40() {
				T expected = build(0b1100110011001001011111111000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 40, (int) 0b110011001100100101111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write47() {
				T expected = build(0b1100110011001001000000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^
	
				writeInt(subject, 47, (int) 0b11111111111111111001100110010010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write48() {
				T expected = build(0b1100110011001001100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^
	
				writeInt(subject, 48, (int) 0b11111111111111111100110011001001);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write49() {
				T expected = build(0b1100110011001000100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^
	
				writeInt(subject, 49, (int) 0b11111111111111111110011001100100);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write56() {
				T expected = build(0b1100110000110110100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^
	
				writeInt(subject, 56, (int) 0b11001100);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write63() {
				T expected = build(0b1011001100110110100000001000000100000100001000100010010010101010L);
				//                   ^
	
				writeInt(subject, 63, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, 64, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeInt(subject, 65, (int) -1);
				assertThat(subject, is(equalTo(expected)));
			}
		}
		
		public static abstract class Long<T> extends Fixture<T> {
			protected abstract long readLong(T data, int i);
			protected abstract void writeLong(T data, int i, long v);
			
			@Test public void read_65() {
				assertThat(readLong(subject, -65), is(equalTo((long) 0b0)));
			}
	
			@Test public void read_64() {
				assertThat(readLong(subject, -64), is(equalTo((long) 0b0)));
			}
	
			@Test public void read_63() {
				assertThat(readLong(subject, -63), is(equalTo((long) 0b0000000000000000000000000000000000000000000000000000000000000000L)));
			}
	
			@Test public void read_49() {
				assertThat(readLong(subject, -49), is(equalTo((long) 0b0100100101010100000000000000000000000000000000000000000000000000L)));
			}
	
			@Test public void read_48() {
				assertThat(readLong(subject, -48), is(equalTo((long) 0b0010010010101010000000000000000000000000000000000000000000000000L)));
			}
	
			@Test public void read_47() {
				assertThat(readLong(subject, -47), is(equalTo((long) 0b0001001001010101000000000000000000000000000000000000000000000000L)));
			}
	
			@Test public void read_33() {
				assertThat(readLong(subject, -33), is(equalTo((long) 0b0000100001000100010010010101010000000000000000000000000000000000L)));
			}
	
			@Test public void read_32() {
				assertThat(readLong(subject, -32), is(equalTo((long) 0b0000010000100010001001001010101000000000000000000000000000000000L)));
			}
	
			@Test public void read_31() {
				assertThat(readLong(subject, -31), is(equalTo((long) 0b1000001000010001000100100101010100000000000000000000000000000000L)));
			}
	
			@Test public void read_25() {
				assertThat(readLong(subject, -25), is(equalTo((long) 0b0000001000001000010001000100100101010100000000000000000000000000L)));
			}
	
			@Test public void read_24() {
				assertThat(readLong(subject, -24), is(equalTo((long) 0b1000000100000100001000100010010010101010000000000000000000000000L)));
			}
	
			@Test public void read_23() {
				assertThat(readLong(subject, -23), is(equalTo((long) 0b0100000010000010000100010001001001010101000000000000000000000000L)));
			}
	
			@Test public void read_17() {
				assertThat(readLong(subject, -17), is(equalTo((long) 0b0000000100000010000010000100010001001001010101000000000000000000L)));
			}
	
			@Test public void read_16() {
				assertThat(readLong(subject, -16), is(equalTo((long) 0b1000000010000001000001000010001000100100101010100000000000000000L)));
			}
	
			@Test public void read_15() {
				assertThat(readLong(subject, -15), is(equalTo((long) 0b0100000001000000100000100001000100010010010101010000000000000000L)));
			}
	
			@Test public void read_13() {
				assertThat(readLong(subject, -13), is(equalTo((long) 0b1101000000010000001000001000010001000100100101010100000000000000L)));
			}
	
			@Test public void read_12() {
				assertThat(readLong(subject, -12), is(equalTo((long) 0b0110100000001000000100000100001000100010010010101010000000000000L)));
			}
	
			@Test public void read_11() {
				assertThat(readLong(subject, -11), is(equalTo((long) 0b1011010000000100000010000010000100010001001001010101000000000000L)));
			}
	
			@Test public void read_9() {
				assertThat(readLong(subject, -9), is(equalTo((long) 0b0110110100000001000000100000100001000100010010010101010000000000L)));
			}
	
			@Test public void read_8() {
				assertThat(readLong(subject, -8), is(equalTo((long) 0b0011011010000000100000010000010000100010001001001010101000000000L)));
			}
	
			@Test public void read_7() {
				assertThat(readLong(subject, -7), is(equalTo((long) 0b1001101101000000010000001000001000010001000100100101010100000000L)));
			}
	
			@Test public void read_5() {
				assertThat(readLong(subject, -5), is(equalTo((long) 0b0110011011010000000100000010000010000100010001001001010101000000L)));
			}
	
			@Test public void read_4() {
				assertThat(readLong(subject, -4), is(equalTo((long) 0b0011001101101000000010000001000001000010001000100100101010100000L)));
			}
	
			@Test public void read_3() {
				assertThat(readLong(subject, -3), is(equalTo((long) 0b1001100110110100000001000000100000100001000100010010010101010000L)));
			}
	
			@Test public void read_1() {
				assertThat(readLong(subject, -1), is(equalTo((long) 0b0110011001101101000000010000001000001000010001000100100101010100L)));
			}
	
			@Test public void read0() {
				assertThat(readLong(subject, 0), is(equalTo((long) 0b0011001100110110100000001000000100000100001000100010010010101010L)));
			}
	
			@Test public void read1() {
				assertThat(readLong(subject, 1), is(equalTo((long) 0b001100110011011010000000100000010000010000100010001001001010101L)));
			}
	
			@Test public void read3() {
				assertThat(readLong(subject, 3), is(equalTo((long) 0b0011001100110110100000001000000100000100001000100010010010101L)));
			}
	
			@Test public void read4() {
				assertThat(readLong(subject, 4), is(equalTo((long) 0b001100110011011010000000100000010000010000100010001001001010L)));
			}
	
			@Test public void read5() {
				assertThat(readLong(subject, 5), is(equalTo((long) 0b00110011001101101000000010000001000001000010001000100100101L)));
			}
	
			@Test public void read7() {
				assertThat(readLong(subject, 7), is(equalTo((long) 0b001100110011011010000000100000010000010000100010001001001L)));
			}
	
			@Test public void read8() {
				assertThat(readLong(subject, 8), is(equalTo((long) 0b00110011001101101000000010000001000001000010001000100100L)));
			}
	
			@Test public void read9() {
				assertThat(readLong(subject, 9), is(equalTo((long) 0b0011001100110110100000001000000100000100001000100010010L)));
			}
	
			@Test public void read11() {
				assertThat(readLong(subject, 11), is(equalTo((long) 0b00110011001101101000000010000001000001000010001000100L)));
			}
	
			@Test public void read12() {
				assertThat(readLong(subject, 12), is(equalTo((long) 0b0011001100110110100000001000000100000100001000100010L)));
			}
	
			@Test public void read13() {
				assertThat(readLong(subject, 13), is(equalTo((long) 0b001100110011011010000000100000010000010000100010001L)));
			}
	
			@Test public void read15() {
				assertThat(readLong(subject, 15), is(equalTo((long) 0b0011001100110110100000001000000100000100001000100L)));
			}
	
			@Test public void read16() {
				assertThat(readLong(subject, 16), is(equalTo((long) 0b001100110011011010000000100000010000010000100010L)));
			}
	
			@Test public void read17() {
				assertThat(readLong(subject, 17), is(equalTo((long) 0b00110011001101101000000010000001000001000010001L)));
			}
	
			@Test public void read23() {
				assertThat(readLong(subject, 23), is(equalTo((long) 0b00110011001101101000000010000001000001000L)));
			}
	
			@Test public void read24() {
				assertThat(readLong(subject, 24), is(equalTo((long) 0b0011001100110110100000001000000100000100L)));
			}
	
			@Test public void read25() {
				assertThat(readLong(subject, 25), is(equalTo((long) 0b001100110011011010000000100000010000010L)));
			}
	
			@Test public void read31() {
				assertThat(readLong(subject, 31), is(equalTo((long) 0b001100110011011010000000100000010L)));
			}
	
			@Test public void read32() {
				assertThat(readLong(subject, 32), is(equalTo((long) 0b00110011001101101000000010000001L)));
			}
	
			@Test public void read33() {
				assertThat(readLong(subject, 33), is(equalTo((long) 0b0011001100110110100000001000000L)));
			}
	
			@Test public void read40() {
				assertThat(readLong(subject, 40), is(equalTo((long) 0b001100110011011010000000L)));
			}
	
			@Test public void read47() {
				assertThat(readLong(subject, 47), is(equalTo((long) 0b00110011001101101L)));
			}
	
			@Test public void read48() {
				assertThat(readLong(subject, 48), is(equalTo((long) 0b0011001100110110L)));
			}
	
			@Test public void read49() {
				assertThat(readLong(subject, 49), is(equalTo((long) 0b001100110011011L)));
			}
	
			@Test public void read56() {
				assertThat(readLong(subject, 56), is(equalTo((long) 0b00110011L)));
			}
	
			@Test public void read63() {
				assertThat(readLong(subject, 63), is(equalTo((long) 0b0L)));
			}
	
			@Test public void read64() {
				assertThat(readLong(subject, 64), is(equalTo((long) 0b0L)));
			}
	
			@Test public void read65() {
				assertThat(readLong(subject, 65), is(equalTo((long) 0b0L)));
			}
	
			@Test public void write_65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeLong(subject, -65, (long) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeLong(subject, -64, (long) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_63() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101011L);
				//                                                                                  ^ 
	
				writeLong(subject, -63, (long) 0b1111111111111111111111111111111111111111111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_49() {
				T expected = build(0b0011001100110110100000001000000100000100001000100101101101010101L);
				//                                                                    ^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -49, (long) 0b1011011010101011111111111111111111111111111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_48() {
				T expected = build(0b0011001100110110100000001000000100000100001000101101101101010101L);
				//                                                                   ^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -48, (long) 0b1101101101010101111111111111111111111111111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_47() {
				T expected = build(0b0011001100110110100000001000000100000100001000111101101101010101L);
				//                                                                  ^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -47, (long) 0b1110110110101010111111111111111111111111111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_33() {
				T expected = build(0b0011001100110110100000001000000101111011110111011101101101010101L);
				//                                                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -33, (long) 0b1111011110111011101101101010101111111111111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_32() {
				T expected = build(0b0011001100110110100000001000000111111011110111011101101101010101L);
				//                                                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
				
				writeLong(subject, -32, (long) 0b1111101111011101110110110101010111111111111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_31() {
				T expected = build(0b0011001100110110100000001000000011111011110111011101101101010101L);
				//                                                  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -31, (long) 0b0111110111101110111011011010101011111111111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_25() {
				T expected = build(0b0011001100110110100000001111111011111011110111011101101101010101L);
				//                                            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -25, (long) 0b1111110111110111101110111011011010101011111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_24() {
				T expected = build(0b0011001100110110100000000111111011111011110111011101101101010101L);
				//                                           ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -24, (long) 0b0111111011111011110111011101101101010101111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_23() {
				T expected = build(0b0011001100110110100000010111111011111011110111011101101101010101L);
				//                                          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -23, (long) 0b1011111101111101111011101110110110101010111111111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_17() {
				T expected = build(0b0011001100110110111111110111111011111011110111011101101101010101L);
				//                                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -17, (long) 0b1111111011111101111101111011101110110110101010111111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_16() {
				T expected = build(0b0011001100110110011111110111111011111011110111011101101101010101L);
				//                                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -16, (long) 0b0111111101111110111110111101110111011011010101011111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_15() {
				T expected = build(0b0011001100110111011111110111111011111011110111011101101101010101L);
				//                                  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -15, (long) 0b1011111110111111011111011110111011101101101010101111111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_13() {
				T expected = build(0b0011001100110001011111110111111011111011110111011101101101010101L);
				//                                ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -13, (long) 0b0010111111101111110111110111101110111011011010101011111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_12() {
				T expected = build(0b0011001100111001011111110111111011111011110111011101101101010101L);
				//                               ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -12, (long) 0b1001011111110111111011111011110111011101101101010101111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_11() {
				T expected = build(0b0011001100101001011111110111111011111011110111011101101101010101L);
				//                              ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -11, (long) 0b0100101111111011111101111101111011101110110110101010111111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_9() {
				T expected = build(0b0011001101001001011111110111111011111011110111011101101101010101L);
				//                            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
	
				writeLong(subject, -9, (long) 0b1001001011111110111111011111011110111011101101101010101111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_8() {
				T expected = build(0b0011001111001001011111110111111011111011110111011101101101010101L);
				//                           ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, -8, (long) 0b1100100101111111011111101111101111011101110110110101010111111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_7() {
				T expected = build(0b0011001011001001011111110111111011111011110111011101101101010101L);
				//                          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, -7, (long) 0b0110010010111111101111110111110111101110111011011010101011111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_5() {
				T expected = build(0b0011010011001001011111110111111011111011110111011101101101010101L);
				//                        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, -5, (long) 0b1001100100101111111011111101111101111011101110110110101010111111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_4() {
				T expected = build(0b0011110011001001011111110111111011111011110111011101101101010101L);
				//                       ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, -4, (long) 0b1100110010010111111101111110111110111101110111011011010101011111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_3() {
				T expected = build(0b0010110011001001011111110111111011111011110111011101101101010101L);
				//                      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, -3, (long) 0b0110011001001011111110111111011111011110111011101101101010101111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write_1() {
				T expected = build(0b0100110011001001011111110111111011111011110111011101101111010101L);
				//                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, -1, (long) 0b01001100110010010111111101111110111110111101110111011011110101011L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write0() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010101L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 0, (long) 0b1100110011001001011111110111111011111011110111011101101101010101L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write1() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010100L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
				
				writeLong(subject, 1, (long) 0b110011001100100101111111011111101111101111011101110110110101010L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write3() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 3, (long) 0b1100110011001001011111110111111011111011110111011101101101010L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write4() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101011010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 4, (long) 0b110011001100100101111111011111101111101111011101110110110101L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write5() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101001010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 5, (long) 0b11001100110010010111111101111110111110111101110111011011010L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write7() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101101100101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 7, (long) 0b110011001100100101111111011111101111101111011101110110110L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write8() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101101110101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 8, (long) 0b11001100110010010111111101111110111110111101110111011011L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write9() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101101010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 9, (long) 0b1100110011001001011111110111111011111011110111011101101L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write11() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101110010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 11, (long) 0b11001100110010010111111101111110111110111101110111011L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write12() {
				T expected = build(0b1100110011001001011111110111111011111011110111011101010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 12, (long) 0b1100110011001001011111110111111011111011110111011101L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write13() {
				T expected = build(0b1100110011001001011111110111111011111011110111011100010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 13, (long) 0b110011001100100101111111011111101111101111011101110L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write15() {
				T expected = build(0b1100110011001001011111110111111011111011110111011010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 15, (long) 0b1100110011001001011111110111111011111011110111011L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write16() {
				T expected = build(0b1100110011001001011111110111111011111011110111010010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 16, (long) 0b110011001100100101111111011111101111101111011101L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write17() {
				T expected = build(0b1100110011001001011111110111111011111011110111000010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 17, (long) 0b11001100110010010111111101111110111110111101110L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write23() {
				T expected = build(0b1100110011001001011111110111111011111011101000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 23, (long) 0b11001100110010010111111101111110111110111L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write24() {
				T expected = build(0b1100110011001001011111110111111011111011001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 24, (long) 0b1100110011001001011111110111111011111011L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write25() {
				T expected = build(0b1100110011001001011111110111111011111010001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 25, (long) 0b110011001100100101111111011111101111101L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write31() {
				T expected = build(0b1100110011001001011111110111111010000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 31, (long) 0b110011001100100101111111011111101L);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write32() {
				T expected = build(0b1100110011001001011111110111111000000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 32, (long) 0b11001100110010010111111101111110);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write33() {
				T expected = build(0b1100110011001001011111110111111100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 33, (long) 0b11100110011001001011111110111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write40() {
				T expected = build(0b1100110011001001011111111000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 40, (long) 0b110011001100100101111111);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write47() {
				T expected = build(0b1100110011001001000000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^^
	
				writeLong(subject, 47, (long) 0b11111111111111111001100110010010);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write48() {
				T expected = build(0b1100110011001001100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^^
	
				writeLong(subject, 48, (long) 0b11111111111111111100110011001001);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write49() {
				T expected = build(0b1100110011001000100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^^^^^^^^
	
				writeLong(subject, 49, (long) 0b11111111111111111110011001100100);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write56() {
				T expected = build(0b1100110000110110100000001000000100000100001000100010010010101010L);
				//                   ^^^^^^^^
	
				writeLong(subject, 56, (long) 0b11001100);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write63() {
				T expected = build(0b1011001100110110100000001000000100000100001000100010010010101010L);
				//                   ^
	
				writeLong(subject, 63, (long) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write64() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeLong(subject, 64, (long) -1);
				assertThat(subject, is(equalTo(expected)));
			}
	
			@Test public void write65() {
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	
				writeLong(subject, 65, (long) -1);
				assertThat(subject, is(equalTo(expected)));
			}
		}

		public static abstract class BitString<T> extends Fixture<T> {
			protected abstract String readBitString(T data, int offset, int length);
			protected abstract void writeBitString(T data, int offset, String v);

			protected abstract String readBitString(T data);
			protected abstract void writeBitString(T data, String v);

			@Test public void readFull() {
				assertThat(readBitString(subject), is(equalTo("0011001100110110100000001000000100000100001000100010010010101010")));
			}

			@Test public void read0_64() {
				String expected = "0011001100110110100000001000000100000100001000100010010010101010";
				String actual = readBitString(subject, 0, 64);
				
				assertThat(actual, is(equalTo(expected)));
			}

			@Test public void read0_32() {
				String expected = "00000100001000100010010010101010";
				String actual = readBitString(subject, 0, 32);
				
				assertThat(actual, is(equalTo(expected)));
			}
			
			@Test public void read32_32() {
				String expected = "00110011001101101000000010000001";
				String actual = readBitString(subject, 32, 32);
				
				assertThat(actual, is(equalTo(expected)));
			}
			
			@Test public void writeFull() {
				writeBitString(subject, "1100110011001001011111110111111011111011110111011101101101010101");
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010101L);
				
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write0_64() {
				writeBitString(subject, 0, "1100110011001001011111110111111011111011110111011101101101010101");
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010101L);
				
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write0_32() {
				writeBitString(subject, 0, "11111011110111011101101101010101");
				T expected = build(0b0011001100110110100000001000000111111011110111011101101101010101L);
				
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write32_32() {
				writeBitString(subject, 32, "11001100110010010111111101111110");
				T expected = build(0b1100110011001001011111110111111000000100001000100010010010101010L);
				
				assertThat(subject, is(equalTo(expected)));
			}

		}
	}
	
	@RunWith(Enclosed.class)
	public static class ByteArray extends ByteArrayBuilder {
		public static class Bit extends Cases.Bit<byte[]> {
			@Override protected boolean readBit(byte[] data, int i) { return Store.readBit(data, i); }
			@Override protected void writeBit(byte[] data, int i, boolean v) { Store.writeBit(data, i, v); }
			@Override public byte[] build(long... d) { return build0(d); }
		}
	
		public static class Byte extends Cases.Byte<byte[]> {
			@Override protected byte readByte(byte[] data, int i) { return Store.readByte(data, i); }
			@Override protected void writeByte(byte[] data, int i, byte v) { Store.writeByte(data, i, v); }
			@Override public byte[] build(long... d) { return build0(d); }
		}
	
		public static class Char extends Cases.Char<byte[]> {
			@Override protected char readChar(byte[] data, int i) { return Store.readChar(data, i); }
			@Override protected void writeChar(byte[] data, int i, char v) { Store.writeChar(data, i, v); }
			@Override public byte[] build(long... d) { return build0(d); }
		}
	
		public static class Short extends Cases.Short<byte[]> {
			@Override protected short readShort(byte[] data, int i) { return Store.readShort(data, i); }
			@Override protected void writeShort(byte[] data, int i, short v) { Store.writeShort(data, i, v); }
			@Override public byte[] build(long... d) { return build0(d); }
		}
	
		public static class Int extends Cases.Int<byte[]> {
			@Override protected int readInt(byte[] data, int i) { return Store.readInt(data, i); }
			@Override protected void writeInt(byte[] data, int i, int v) { Store.writeInt(data, i, v); }
			@Override public byte[] build(long... d) { return build0(d); }
		}
	
		public static class Long extends Cases.Long<byte[]> {
			@Override protected long readLong(byte[] data, int i) { return Store.readLong(data, i); }
			@Override protected void writeLong(byte[] data, int i, long v) { Store.writeLong(data, i, v); }
			@Override public byte[] build(long... d) { return build0(d); }
		}
	
		public static class BitString extends Cases.BitString<byte[]> {
			@Override protected String readBitString(byte[] data, int offset, int length) { return Store.readBitString(data, offset, length); }
			@Override protected void writeBitString(byte[] data, int offset, String v) { Store.writeBitString(data, offset, v); }
			@Override protected String readBitString(byte[] data) { return Store.readBitString(data); }
			@Override protected void writeBitString(byte[] data, String v) { Store.writeBitString(data, v); }
			@Override public byte[] build(long... d) { return build0(d); }
		}
	}

	@RunWith(Enclosed.class)
	public static class CharArray extends CharArrayBuilder {
		public static class Bit extends Cases.Bit<char[]> {
			@Override protected boolean readBit(char[] data, int i) { return Store.readBit(data, i); }
			@Override protected void writeBit(char[] data, int i, boolean v) { Store.writeBit(data, i, v); }
			@Override public char[] build(long... d) { return build0(d); }
		}
	
		public static class Byte extends Cases.Byte<char[]> {
			@Override protected byte readByte(char[] data, int i) { return Store.readByte(data, i); }
			@Override protected void writeByte(char[] data, int i, byte v) { Store.writeByte(data, i, v); }
			@Override public char[] build(long... d) { return build0(d); }
		}
	
		public static class Char extends Cases.Char<char[]> {
			@Override protected char readChar(char[] data, int i) { return Store.readChar(data, i); }
			@Override protected void writeChar(char[] data, int i, char v) { Store.writeChar(data, i, v); }
			@Override public char[] build(long... d) { return build0(d); }
		}
	
		public static class Short extends Cases.Short<char[]> {
			@Override protected short readShort(char[] data, int i) { return Store.readShort(data, i); }
			@Override protected void writeShort(char[] data, int i, short v) { Store.writeShort(data, i, v); }
			@Override public char[] build(long... d) { return build0(d); }
		}
	
		public static class Int extends Cases.Int<char[]> {
			@Override protected int readInt(char[] data, int i) { return Store.readInt(data, i); }
			@Override protected void writeInt(char[] data, int i, int v) { Store.writeInt(data, i, v); }
			@Override public char[] build(long... d) { return build0(d); }
		}
	
		public static class Long extends Cases.Long<char[]> {
			@Override protected long readLong(char[] data, int i) { return Store.readLong(data, i); }
			@Override protected void writeLong(char[] data, int i, long v) { Store.writeLong(data, i, v); }
			@Override public char[] build(long... d) { return build0(d); }
		}
	
		public static class BitString extends Cases.BitString<char[]> {
			@Override protected String readBitString(char[] data, int offset, int length) { return Store.readBitString(data, offset, length); }
			@Override protected void writeBitString(char[] data, int offset, String v) { Store.writeBitString(data, offset, v); }
			@Override protected String readBitString(char[] data) { return Store.readBitString(data); }
			@Override protected void writeBitString(char[] data, String v) { Store.writeBitString(data, v); }
			@Override public char[] build(long... d) { return build0(d); }
		}
	}

	@RunWith(Enclosed.class)
	public static class ShortArray extends ShortArrayBuilder {
		public static class Bit extends Cases.Bit<short[]> {
			@Override protected boolean readBit(short[] data, int i) { return Store.readBit(data, i); }
			@Override protected void writeBit(short[] data, int i, boolean v) { Store.writeBit(data, i, v); }
			@Override public short[] build(long... d) { return build0(d); }
		}
	
		public static class Byte extends Cases.Byte<short[]> {
			@Override protected byte readByte(short[] data, int i) { return Store.readByte(data, i); }
			@Override protected void writeByte(short[] data, int i, byte v) { Store.writeByte(data, i, v); }
			@Override public short[] build(long... d) { return build0(d); }
		}
	
		public static class Char extends Cases.Char<short[]> {
			@Override protected char readChar(short[] data, int i) { return Store.readChar(data, i); }
			@Override protected void writeChar(short[] data, int i, char v) { Store.writeChar(data, i, v); }
			@Override public short[] build(long... d) { return build0(d); }
		}
	
		public static class Short extends Cases.Short<short[]> {
			@Override protected short readShort(short[] data, int i) { return Store.readShort(data, i); }
			@Override protected void writeShort(short[] data, int i, short v) { Store.writeShort(data, i, v); }
			@Override public short[] build(long... d) { return build0(d); }
		}
	
		public static class Int extends Cases.Int<short[]> {
			@Override protected int readInt(short[] data, int i) { return Store.readInt(data, i); }
			@Override protected void writeInt(short[] data, int i, int v) { Store.writeInt(data, i, v); }
			@Override public short[] build(long... d) { return build0(d); }
		}
	
		public static class Long extends Cases.Long<short[]> {
			@Override protected long readLong(short[] data, int i) { return Store.readLong(data, i); }
			@Override protected void writeLong(short[] data, int i, long v) { Store.writeLong(data, i, v); }
			@Override public short[] build(long... d) { return build0(d); }
		}
	}

	@RunWith(Enclosed.class)
	public static class IntArray extends IntArrayBuilder {
		public static class Bit extends Cases.Bit<int[]> {
			@Override protected boolean readBit(int[] data, int i) { return Store.readBit(data, i); }
			@Override protected void writeBit(int[] data, int i, boolean v) { Store.writeBit(data, i, v); }
			@Override public int[] build(long... d) { return build0(d); }
		}
	
		public static class Byte extends Cases.Byte<int[]> {
			@Override protected byte readByte(int[] data, int i) { return Store.readByte(data, i); }
			@Override protected void writeByte(int[] data, int i, byte v) { Store.writeByte(data, i, v); }
			@Override public int[] build(long... d) { return build0(d); }
		}
	
		public static class Char extends Cases.Char<int[]> {
			@Override protected char readChar(int[] data, int i) { return Store.readChar(data, i); }
			@Override protected void writeChar(int[] data, int i, char v) { Store.writeChar(data, i, v); }
			@Override public int[] build(long... d) { return build0(d); }
		}
	
		public static class Short extends Cases.Short<int[]> {
			@Override protected short readShort(int[] data, int i) { return Store.readShort(data, i); }
			@Override protected void writeShort(int[] data, int i, short v) { Store.writeShort(data, i, v); }
			@Override public int[] build(long... d) { return build0(d); }
		}
	
		public static class Int extends Cases.Int<int[]> {
			@Override protected int readInt(int[] data, int i) { return Store.readInt(data, i); }
			@Override protected void writeInt(int[] data, int i, int v) { Store.writeInt(data, i, v); }
			@Override public int[] build(long... d) { return build0(d); }
		}
	
		public static class Long extends Cases.Long<int[]> {
			@Override protected long readLong(int[] data, int i) { return Store.readLong(data, i); }
			@Override protected void writeLong(int[] data, int i, long v) { Store.writeLong(data, i, v); }
			@Override public int[] build(long... d) { return build0(d); }
		}
	}

	@RunWith(Enclosed.class)
	public static class LongArray extends LongArrayBuilder {
		public static class Bit extends Cases.Bit<long[]> {
			@Override protected boolean readBit(long[] data, int i) { return Store.readBit(data, i); }
			@Override protected void writeBit(long[] data, int i, boolean v) { Store.writeBit(data, i, v); }
			@Override public long[] build(long... d) { return build0(d); }
		}
	
		public static class Byte extends Cases.Byte<long[]> {
			@Override protected byte readByte(long[] data, int i) { return Store.readByte(data, i); }
			@Override protected void writeByte(long[] data, int i, byte v) { Store.writeByte(data, i, v); }
			@Override public long[] build(long... d) { return build0(d); }
		}
	
		public static class Char extends Cases.Char<long[]> {
			@Override protected char readChar(long[] data, int i) { return Store.readChar(data, i); }
			@Override protected void writeChar(long[] data, int i, char v) { Store.writeChar(data, i, v); }
			@Override public long[] build(long... d) { return build0(d); }
		}
	
		public static class Short extends Cases.Short<long[]> {
			@Override protected short readShort(long[] data, int i) { return Store.readShort(data, i); }
			@Override protected void writeShort(long[] data, int i, short v) { Store.writeShort(data, i, v); }
			@Override public long[] build(long... d) { return build0(d); }
		}
	
		public static class Int extends Cases.Int<long[]> {
			@Override protected int readInt(long[] data, int i) { return Store.readInt(data, i); }
			@Override protected void writeInt(long[] data, int i, int v) { Store.writeInt(data, i, v); }
			@Override public long[] build(long... d) { return build0(d); }
		}
	
		public static class Long extends Cases.Long<long[]> {
			@Override protected long readLong(long[] data, int i) { return Store.readLong(data, i); }
			@Override protected void writeLong(long[] data, int i, long v) { Store.writeLong(data, i, v); }
			@Override public long[] build(long... d) { return build0(d); }
		}
	}
}
