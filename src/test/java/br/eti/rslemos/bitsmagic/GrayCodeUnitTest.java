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

import static br.eti.rslemos.bitsmagic.GrayCode.fromGray;
import static br.eti.rslemos.bitsmagic.GrayCode.toGray;
import static br.eti.rslemos.bitsmagic.Ones.ones;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class GrayCodeUnitTest {
	
	@RunWith(Enclosed.class)
	public static class Primitives {
		public static class Byte {
			@Test public void grayProperty() {
				byte v = java.lang.Byte.MIN_VALUE;
				byte g = toGray(v);
				
				do {
					v++;
					byte g1 = toGray(v);
					assertThat(ones((g ^ g1) & 0xff), is(equalTo(1)));
					g = g1;
				} while (v != java.lang.Byte.MIN_VALUE);
			}
			
			@Test public void grayInverse() {
				byte v = java.lang.Byte.MIN_VALUE;
				do {
					v++;
					assertThat(fromGray(toGray(v)), is(equalTo(v)));
				} while (v != java.lang.Byte.MIN_VALUE);
			}
		}
		
		public static class Char {
			@Test public void grayProperty() {
				char v = Character.MIN_VALUE;
				char g = toGray(v);
				
				do {
					v++;
					char g1 = toGray(v);
					assertThat(ones((g ^ g1) & 0xffff), is(equalTo(1)));
					g = g1;
				} while (v != Character.MIN_VALUE);
			}
		
			@Test public void grayInverse() {
				char v = Character.MIN_VALUE;
				do {
					v++;
					assertThat(fromGray(toGray(v)), is(equalTo(v)));
				} while (v != Character.MIN_VALUE);
			}
		}
		
		public static class Short {
			@Test public void grayProperty() {
				short v = java.lang.Short.MIN_VALUE;
				short g = toGray(v);
				
				do {
					v++;
					short g1 = toGray(v);
					assertThat(ones((g ^ g1) & 0xffff), is(equalTo(1)));
					g = g1;
				} while (v != java.lang.Short.MIN_VALUE);
			}
		
			@Test public void grayInverse() {
				short v = java.lang.Short.MIN_VALUE;
				do {
					v++;
					assertThat(fromGray(toGray(v)), is(equalTo(v)));
				} while (v != java.lang.Short.MIN_VALUE);
			}
		}
		
		public static class Int {
			@Test public void grayProperty() {
				// special cases
				testGrayProperty(Integer.MIN_VALUE);
				testGrayProperty(-1);
				testGrayProperty(0);
				testGrayProperty(1);
				testGrayProperty(Integer.MAX_VALUE-1);
				testGrayProperty(Integer.MAX_VALUE);
		
				int v = -10000000;
				int g = toGray(v);
				
				do {
					v++;
					int g1 = toGray(v);
					assertThat(ones(g ^ g1), is(equalTo(1)));
					g = g1;
				} while (v != 10000000);
			}
			
			private void testGrayProperty(int v) {
				int g0 = toGray(v);
				int g = toGray(v+1);
				assertThat(ones(g0 ^ g), is(equalTo(1)));
			}
		
			@Test public void grayInverse() {
				// special cases
				testGrayInverse(Integer.MIN_VALUE);
				testGrayInverse(Integer.MIN_VALUE+1);
				testGrayInverse(Integer.MAX_VALUE-1);
				testGrayInverse(Integer.MAX_VALUE);
		
				int v = -10000000;
				
				do {
					v++;
					testGrayInverse(v);
				} while (v != 10000000);
			}
			
			private void testGrayInverse(int v) {
				assertThat(fromGray(toGray(v)), is(equalTo(v)));
			}
		}
		
		public static class Long {
			@Test public void grayProperty() {
				// special cases
				testGrayProperty(java.lang.Long.MIN_VALUE);
				testGrayProperty(java.lang.Long.MIN_VALUE+1);
				testGrayProperty(java.lang.Long.MAX_VALUE-1);
				testGrayProperty(java.lang.Long.MAX_VALUE);
		
				long v = -10000000;
				long g = toGray(v);
				
				do {
					v++;
					long g1 = toGray(v);
					assertThat(ones(g ^ g1), is(equalTo(1)));
					g = g1;
				} while (v != 10000000);
			}
			
			private void testGrayProperty(long a) {
				long g0 = toGray(a);
				long g = toGray(a+1);
				assertThat(ones(g0 ^ g), is(equalTo(1)));
			}
			
			@Test public void grayInverse() {
				// special cases
				testGrayInverse(java.lang.Long.MIN_VALUE);
				testGrayInverse(-1);
				testGrayInverse(0);
				testGrayInverse(1);
				testGrayInverse(java.lang.Long.MAX_VALUE-1);
				testGrayInverse(java.lang.Long.MAX_VALUE);
		
				long v = -10000000;
				
				do {
					v++;
					testGrayInverse(v);
				} while (v != 10000000);
			}
			
			private void testGrayInverse(long v) {
				assertThat(fromGray(toGray(v)), is(equalTo(v)));
			}
		}
	}
}
