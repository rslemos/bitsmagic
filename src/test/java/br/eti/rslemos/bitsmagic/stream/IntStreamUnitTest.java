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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.EOFException;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import br.eti.rslemos.bitsmagic.StorageBuilder;

@RunWith(Enclosed.class)
public class IntStreamUnitTest {
	protected abstract static class Fixture<T> implements StorageBuilder<T> {
		//                         3210987654321098765432109876543210987654321098765432109876543210
		public T subject = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
	}
	
	@Ignore
	public static class Cases {
		public static abstract class Read<T> extends Fixture<T> {
			private IntInputStream input;
	
			@Test public void read_4_0_64() throws IOException {
				int[] expected = { 0b1010, 0b1010, 0b0100, 0b0010, 0b0010, 0b0010, 0b0100, 0b0000, 0b0001, 0b1000, 0b0000, 0b1000, 0b0110, 0b0011, 0b0011, 0b0011 };
				
				assertThat(read(4, 0, 64), is(equalTo(expected)));
			}

			@Test public void read_8_0_64() throws IOException {
				int[] expected = { 0b10101010, 0b00100100, 0b00100010, 0b00000100, 0b10000001, 0b10000000, 0b00110110, 0b00110011 };
				
				assertThat(read(8, 0, 64), is(equalTo(expected)));
			}

			@Test public void read_16_0_64() throws IOException {
				int[] expected = { 0b0010010010101010, 0b0000010000100010, 0b1000000010000001, 0b0011001100110110 };
				
				assertThat(read(16, 0, 64), is(equalTo(expected)));
			}

			@Test public void read_24_0_64() throws IOException {
				int[] expected = { 0b001000100010010010101010, 0b100000001000000100000100, 0b000000000011001100110110 };
				
				assertThat(read(24, 0, 64), is(equalTo(expected)));
			}

			@Test public void read_32_0_64() throws IOException {
				int[] expected = { 0b00000100001000100010010010101010, 0b00110011001101101000000010000001 };
				
				assertThat(read(32, 0, 64), is(equalTo(expected)));
			}

			@Test public void read_4_8_56() throws IOException {
				int[] expected = { 0b0100, 0b0010, 0b0010, 0b0010, 0b0100, 0b0000, 0b0001, 0b1000, 0b0000, 0b1000, 0b0110, 0b0011 };
				
				assertThat(read(4, 8, 56), is(equalTo(expected)));
			}

			@Test public void read_8_8_56() throws IOException {
				int[] expected = { 0b00100100, 0b00100010, 0b00000100, 0b10000001, 0b10000000, 0b00110110 };
				
				assertThat(read(8, 8, 56), is(equalTo(expected)));
			}

			@Test public void read_16_8_56() throws IOException {
				int[] expected = { 0b0010001000100100, 0b1000000100000100, 0b0011011010000000 };
				
				assertThat(read(16, 8, 56), is(equalTo(expected)));
			}

			@Test public void read_24_8_56() throws IOException {
				int[] expected = { 0b000001000010001000100100, 0b001101101000000010000001 };
				
				assertThat(read(24, 8, 56), is(equalTo(expected)));
			}

			@Test public void read_32_8_56() throws IOException {
				int[] expected = { 0b10000001000001000010001000100100, 0b00000000000000000011011010000000 };
				
				assertThat(read(32, 8, 56), is(equalTo(expected)));
			}

			@Test public void read_4_16_48() throws IOException {
				int[] expected = { 0b0010, 0b0010, 0b0100, 0b0000, 0b0001, 0b1000, 0b0000, 0b1000 };
				
				assertThat(read(4, 16, 48), is(equalTo(expected)));
			}

			@Test public void read_8_16_48() throws IOException {
				int[] expected = { 0b00100010, 0b00000100, 0b10000001, 0b10000000 };
				
				assertThat(read(8, 16, 48), is(equalTo(expected)));
			}

			@Test public void read_16_16_48() throws IOException {
				int[] expected = { 0b0000010000100010, 0b1000000010000001 };
				
				assertThat(read(16, 16, 48), is(equalTo(expected)));
			}

			@Test public void read_24_16_48() throws IOException {
				int[] expected = { 0b100000010000010000100010, 0b000000000000000010000000 };
				
				assertThat(read(24, 16, 48), is(equalTo(expected)));
			}

			@Test public void read_32_16_48() throws IOException {
				int[] expected = { 0b10000000100000010000010000100010 };
				
				assertThat(read(32, 16, 48), is(equalTo(expected)));
			}

			@Test public void read_4_24_40() throws IOException {
				int[] expected = { 0b0100, 0b0000, 0b0001, 0b1000 };
				
				assertThat(read(4, 24, 40), is(equalTo(expected)));
			}

			@Test public void read_8_24_40() throws IOException {
				int[] expected = { 0b00000100, 0b10000001 };
				
				assertThat(read(8, 24, 40), is(equalTo(expected)));
			}

			@Test public void read_16_24_40() throws IOException {
				int[] expected = { 0b1000000100000100 };
				
				assertThat(read(16, 24, 40), is(equalTo(expected)));
			}

			@Test public void read_24_24_40() throws IOException {
				int[] expected = { 0b000000001000000100000100 };
				
				assertThat(read(24, 24, 40), is(equalTo(expected)));
			}

			@Test public void read_32_24_40() throws IOException {
				int[] expected = { 0b00000000000000001000000100000100 };
				
				assertThat(read(32, 24, 40), is(equalTo(expected)));
			}

			@Test public void read_4_28_36() throws IOException {
				int[] expected = { 0b0000, 0b0001 };
				
				assertThat(read(4, 28, 36), is(equalTo(expected)));
			}

			@Test public void read_8_28_36() throws IOException {
				int[] expected = { 0b00010000 };
				
				assertThat(read(8, 28, 36), is(equalTo(expected)));
			}

			@Test public void read_16_28_36() throws IOException {
				int[] expected = { 0b0000000000010000 };
				
				assertThat(read(16, 28, 36), is(equalTo(expected)));
			}

			@Test public void read_24_28_36() throws IOException {
				int[] expected = { 0b000000000000000000010000 };
				
				assertThat(read(24, 28, 36), is(equalTo(expected)));
			}

			@Test public void read_32_28_36() throws IOException {
				int[] expected = { 0b00000000000000000000000000010000 };
				
				assertThat(read(32, 28, 36), is(equalTo(expected)));
			}

			@Test public void read_4_32_32() throws IOException {
				int[] expected = {  };
				
				assertThat(read(4, 32, 32), is(equalTo(expected)));
			}

			@Test public void read_8_32_32() throws IOException {
				int[] expected = {  };
				
				assertThat(read(8, 32, 32), is(equalTo(expected)));
			}

			@Test public void read_16_32_32() throws IOException {
				int[] expected = {  };
				
				assertThat(read(16, 32, 32), is(equalTo(expected)));
			}

			@Test public void read_24_32_32() throws IOException {
				int[] expected = {  };
				
				assertThat(read(24, 32, 32), is(equalTo(expected)));
			}

			@Test public void read_32_32_32() throws IOException {
				int[] expected = {  };
				
				assertThat(read(32, 32, 32), is(equalTo(expected)));
			}

			@Test public void read_4_48_16() throws IOException {
				int[] expected = {  };
				
				assertThat(read(4, 48, 16), is(equalTo(expected)));
			}

			@Test public void read_8_48_16() throws IOException {
				int[] expected = {  };
				
				assertThat(read(8, 48, 16), is(equalTo(expected)));
			}

			@Test public void read_16_48_16() throws IOException {
				int[] expected = {  };
				
				assertThat(read(16, 48, 16), is(equalTo(expected)));
			}

			@Test public void read_24_48_16() throws IOException {
				int[] expected = {  };
				
				assertThat(read(24, 48, 16), is(equalTo(expected)));
			}

			@Test public void read_32_48_16() throws IOException {
				int[] expected = {  };
				
				assertThat(read(32, 48, 16), is(equalTo(expected)));
			}
			
			@Test(expected = EOFException.class)
			public void readPast() throws IOException {
				read(32, 48, 16, 1);
			}

			@Test(expected = IllegalArgumentException.class)
			public void read__8_0_0() throws IOException {
				read(-8, 0, 0, 0);
			}
			
			@Test(expected = IllegalArgumentException.class)
			public void read_0_0_0() throws IOException {
				read(0, 0, 0, 0);
			}
			
			@Test(expected = IllegalArgumentException.class)
			public void read_40_0_0() throws IOException {
				read(40, 0, 0, 0);
			}
			
			private int[] read(int width, int from, int to) throws IOException {
				int n = ((to - from) + (width - 1)) / width;
				return read(width, from, to, Math.max(0, n));
			}

			private int[] read(int width, int from, int to, int n) throws IOException {
				input = build(width, subject, from, to);
				
				int[] result = new int[n];
				for (int i = 0; i < result.length; i++)
					result[i] = input.readInt();
				
				return result;
			}
	
			abstract IntInputStream build(int width, T subject, int from, int to);
		}
		
		public static abstract class Write<T> extends Fixture<T> {
			@Test public void write_4_0_64() throws IOException {
				int[] write = { 0b11111111111111111111111111110101,
				                0b11111111111111111111111111110101,
				                0b11111111111111111111111111111011,
				                0b11111111111111111111111111111101,
				                0b11111111111111111111111111111101,
				                0b11111111111111111111111111111101,
				                0b11111111111111111111111111111011,
				                0b11111111111111111111111111111111,
				                0b11111111111111111111111111111110,
				                0b11111111111111111111111111110111,
				                0b11111111111111111111111111111111,
				                0b11111111111111111111111111110111,
				                0b11111111111111111111111111111001,
				                0b11111111111111111111111111111100,
				                0b11111111111111111111111111111100,
				                0b11111111111111111111111111111100 };
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010101L);
				
				write(4, 0, 64, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_8_0_64() throws IOException {
				int[] write = { 0b11111111111111111111111101010101,
				                0b11111111111111111111111111011011,
				                0b11111111111111111111111111011101,
				                0b11111111111111111111111111111011,
				                0b11111111111111111111111101111110,
				                0b11111111111111111111111101111111,
				                0b11111111111111111111111111001001,
				                0b11111111111111111111111111001100 };
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010101L);
				
				write(8, 0, 64, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_16_0_64() throws IOException {
				int[] write = { 0b11111111111111111101101101010101,
				                0b11111111111111111111101111011101,
				                0b11111111111111110111111101111110,
				                0b11111111111111111100110011001001 };
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010101L);
				
				write(16, 0, 64, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_24_0_64() throws IOException {
				int[] write = { 0b11111111110111011101101101010101,
				                0b11111111011111110111111011111011,
				                0b11111111111111111100110011001001 };
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010101L);
				
				write(24, 0, 64, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_32_0_64() throws IOException {
				int[] write = { 0b11111011110111011101101101010101,
				                0b11001100110010010111111101111110 };
				T expected = build(0b1100110011001001011111110111111011111011110111011101101101010101L);
				
				write(32, 0, 64, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_4_8_56() throws IOException {
				int[] write = { 0b11111111111111111111111111111011,
				                0b11111111111111111111111111111101,
				                0b11111111111111111111111111111101,
				                0b11111111111111111111111111111101,
				                0b11111111111111111111111111111011,
				                0b11111111111111111111111111111111,
				                0b11111111111111111111111111111110,
				                0b11111111111111111111111111110111,
				                0b11111111111111111111111111111111,
				                0b11111111111111111111111111110111,
				                0b11111111111111111111111111111001,
				                0b11111111111111111111111111111100 };
				T expected = build(0b0011001111001001011111110111111011111011110111011101101110101010L);
				
				write(4, 8, 56, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_8_8_56() throws IOException {
				int[] write = { 0b11111111111111111111111111011011,
				                0b11111111111111111111111111011101,
				                0b11111111111111111111111111111011,
				                0b11111111111111111111111101111110,
				                0b11111111111111111111111101111111,
				                0b11111111111111111111111111001001 };
				T expected = build(0b0011001111001001011111110111111011111011110111011101101110101010L);
				
				write(8, 8, 56, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_16_8_56() throws IOException {
				int[] write = { 0b11111111111111111101110111011011,
				                0b11111111111111110111111011111011,
				                0b11111111111111111100100101111111 };
				T expected = build(0b0011001111001001011111110111111011111011110111011101101110101010L);
				
				write(16, 8, 56, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_24_8_56() throws IOException {
				int[] write = { 0b11111111111110111101110111011011,
				                0b11111111110010010111111101111110 };
				T expected = build(0b0011001111001001011111110111111011111011110111011101101110101010L);
				
				write(24, 8, 56, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_32_8_56() throws IOException {
				int[] write = { 0b01111110111110111101110111011011,
				                0b11111111111111111100100101111111 };
				T expected = build(0b0011001111001001011111110111111011111011110111011101101110101010L);
				
				write(32, 8, 56, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_4_16_48() throws IOException {
				int[] write = { 0b11111111111111111111111111111101,
				                0b11111111111111111111111111111101,
				                0b11111111111111111111111111111011,
				                0b11111111111111111111111111111111,
				                0b11111111111111111111111111111110,
				                0b11111111111111111111111111110111,
				                0b11111111111111111111111111111111,
				                0b11111111111111111111111111110111 };
				T expected = build(0b0011001100110110011111110111111011111011110111010010010010101010L);
				
				write(4, 16, 48, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_8_16_48() throws IOException {
				int[] write = { 0b11111111111111111111111111011101,
				                0b11111111111111111111111111111011,
				                0b11111111111111111111111101111110,
				                0b11111111111111111111111101111111 };
				T expected = build(0b0011001100110110011111110111111011111011110111010010010010101010L);
				
				write(8, 16, 48, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_16_16_48() throws IOException {
				int[] write = { 0b11111111111111111111101111011101,
				                0b11111111111111110111111101111110 };
				T expected = build(0b0011001100110110011111110111111011111011110111010010010010101010L);
				
				write(16, 16, 48, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_24_16_48() throws IOException {
				int[] write = { 0b11111111011111101111101111011101,
				                0b11111111111111111111111101111111 };
				T expected = build(0b0011001100110110011111110111111011111011110111010010010010101010L);
				
				write(24, 16, 48, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_32_16_48() throws IOException {
				int[] write = { 0b01111111011111101111101111011101 };
				T expected = build(0b0011001100110110011111110111111011111011110111010010010010101010L);
				
				write(32, 16, 48, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_4_24_40() throws IOException {
				int[] write = { 0b11111111111111111111111111111011,
				                0b11111111111111111111111111111111,
				                0b11111111111111111111111111111110,
				                0b11111111111111111111111111110111 };
				T expected = build(0b0011001100110110100000000111111011111011001000100010010010101010L);
				
				write(4, 24, 40, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_8_24_40() throws IOException {
				int[] write = { 0b11111111111111111111111111111011,
				                0b11111111111111111111111101111110 };
				T expected = build(0b0011001100110110100000000111111011111011001000100010010010101010L);
				
				write(8, 24, 40, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_16_24_40() throws IOException {
				int[] write = { 0b11111111111111110111111011111011 };
				T expected = build(0b0011001100110110100000000111111011111011001000100010010010101010L);
				
				write(16, 24, 40, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_24_24_40() throws IOException {
				int[] write = { 0b11111111111111110111111011111011 };
				T expected = build(0b0011001100110110100000000111111011111011001000100010010010101010L);
				
				write(24, 24, 40, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_32_24_40() throws IOException {
				int[] write = { 0b11111111111111110111111011111011 };
				T expected = build(0b0011001100110110100000000111111011111011001000100010010010101010L);
				
				write(32, 24, 40, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_4_28_36() throws IOException {
				int[] write = { 0b11111111111111111111111111111111,
				                0b11111111111111111111111111111110 };
				T expected = build(0b0011001100110110100000001000111011110100001000100010010010101010L);
				
				write(4, 28, 36, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_8_28_36() throws IOException {
				int[] write = { 0b11111111111111111111111111101111 };
				T expected = build(0b0011001100110110100000001000111011110100001000100010010010101010L);
				
				write(8, 28, 36, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_16_28_36() throws IOException {
				int[] write = { 0b11111111111111111111111111101111 };
				T expected = build(0b0011001100110110100000001000111011110100001000100010010010101010L);
				
				write(16, 28, 36, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_24_28_36() throws IOException {
				int[] write = { 0b11111111111111111111111111101111 };
				T expected = build(0b0011001100110110100000001000111011110100001000100010010010101010L);
				
				write(24, 28, 36, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_32_28_36() throws IOException {
				int[] write = { 0b11111111111111111111111111101111 };
				T expected = build(0b0011001100110110100000001000111011110100001000100010010010101010L);
				
				write(32, 28, 36, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_4_32_32() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(4, 32, 32, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_8_32_32() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(8, 32, 32, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_16_32_32() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(16, 32, 32, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_24_32_32() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(24, 32, 32, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_32_32_32() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(32, 32, 32, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_4_48_16() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(4, 48, 16, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_8_48_16() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(8, 48, 16, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_16_48_16() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(16, 48, 16, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_24_48_16() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(24, 48, 16, write);
				assertThat(subject, is(equalTo(expected)));
			}

			@Test public void write_32_48_16() throws IOException {
				int[] write = {  };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101010L);
				
				write(32, 48, 16, write);
				assertThat(subject, is(equalTo(expected)));
			}
			
			@Test public void write_2_0_64_short() throws IOException {
				int[] write = { 0b11111011110111011101101101010101 };
				T expected = build(0b0011001100110110100000001000000100000100001000100010010010101001L);
				
				write(2, 0, 64, write);
				assertThat(subject, is(equalTo(expected)));
			}
			
			@Test(expected = EOFException.class)
			public void writePast() throws IOException {
				write(32, 48, 16, -1);
			}

			@Test(expected = IllegalArgumentException.class)
			public void write__8_0_0() throws IOException {
				write(-8, 0, 0, 0);
			}
			
			@Test(expected = IllegalArgumentException.class)
			public void write_0_0_0() throws IOException {
				write(0, 0, 0, 0);
			}
			
			@Test(expected = IllegalArgumentException.class)
			public void write_40_0_0() throws IOException {
				write(40, 0, 0, 0);
			}
			
			private void write(int width, int from, int to, int... write) throws IOException {
				IntOutputStream output = build(width, subject, from, to);
				
				for (int w : write)
					output.writeInt(w);
			}
	
			abstract IntOutputStream build(int width, T subject, int from, int to);
		}
	}
	
	@RunWith(Enclosed.class)
	public static class ByteArray {
		public static class Read extends Cases.Read<byte[]> {
			@Override IntInputStream build(int width, byte[] subject, int from, int to) { return new ByteArrayIntStream.Input(width, subject, from, to); }
			@Override public byte[] build(long... d) { return ByteArrayBuilder.build0(d); }
		}
		
		public static class Write extends Cases.Write<byte[]> {
			@Override IntOutputStream build(int width, byte[] subject, int from, int to) { return new ByteArrayIntStream.Output(width, subject, from, to); }
			@Override public byte[] build(long... d) { return ByteArrayBuilder.build0(d); }
		}
	}
	
	@RunWith(Enclosed.class)
	public static class CharArray {
		public static class Read extends Cases.Read<char[]> {
			@Override IntInputStream build(int width, char[] subject, int from, int to) { return new CharArrayIntStream.Input(width, subject, from, to); }
			@Override public char[] build(long... d) { return CharArrayBuilder.build0(d); }
		}
		
		public static class Write extends Cases.Write<char[]> {
			@Override IntOutputStream build(int width, char[] subject, int from, int to) { return new CharArrayIntStream.Output(width, subject, from, to); }
			@Override public char[] build(long... d) { return CharArrayBuilder.build0(d); }
		}
	}
	
	@RunWith(Enclosed.class)
	public static class ShortArray {
		public static class Read extends Cases.Read<short[]> {
			@Override IntInputStream build(int width, short[] subject, int from, int to) { return new ShortArrayIntStream.Input(width, subject, from, to); }
			@Override public short[] build(long... d) { return ShortArrayBuilder.build0(d); }
		}
		
		public static class Write extends Cases.Write<short[]> {
			@Override IntOutputStream build(int width, short[] subject, int from, int to) { return new ShortArrayIntStream.Output(width, subject, from, to); }
			@Override public short[] build(long... d) { return ShortArrayBuilder.build0(d); }
		}
	}
	
	@RunWith(Enclosed.class)
	public static class IntArray {
		public static class Read extends Cases.Read<int[]> {
			@Override IntInputStream build(int width, int[] subject, int from, int to) { return new IntArrayIntStream.Input(width, subject, from, to); }
			@Override public int[] build(long... d) { return IntArrayBuilder.build0(d); }
		}
		
		public static class Write extends Cases.Write<int[]> {
			@Override IntOutputStream build(int width, int[] subject, int from, int to) { return new IntArrayIntStream.Output(width, subject, from, to); }
			@Override public int[] build(long... d) { return IntArrayBuilder.build0(d); }
		}
	}
	
	@RunWith(Enclosed.class)
	public static class LongArray {
		public static class Read extends Cases.Read<long[]> {
			@Override IntInputStream build(int width, long[] subject, int from, int to) { return new LongArrayIntStream.Input(width, subject, from, to); }
			@Override public long[] build(long... d) { return LongArrayBuilder.build0(d); }
		}
		
		public static class Write extends Cases.Write<long[]> {
			@Override IntOutputStream build(int width, long[] subject, int from, int to) { return new LongArrayIntStream.Output(width, subject, from, to); }
			@Override public long[] build(long... d) { return LongArrayBuilder.build0(d); }
		}
	}
}
