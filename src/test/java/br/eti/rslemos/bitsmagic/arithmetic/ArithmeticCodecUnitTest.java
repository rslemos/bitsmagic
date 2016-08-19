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
package br.eti.rslemos.bitsmagic.arithmetic;

import static br.eti.rslemos.bitsmagic.arithmetic.Distribution.even;
import static br.eti.rslemos.bitsmagic.arithmetic.Distribution.toCumulative;
import static java.util.Arrays.sort;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import br.eti.rslemos.bitsmagic.arithmetic.ArithmeticCodec.Decoder;
import br.eti.rslemos.bitsmagic.arithmetic.ArithmeticCodec.Encoder;

@Ignore
public class ArithmeticCodecUnitTest {
	final ArithmeticCodecFactory factory;
	ArithmeticCodecUnitTest(ArithmeticCodecFactory factory) { this.factory = factory; }
	
	public abstract static class LiteratureCases extends ArithmeticCodecUnitTest {
		LiteratureCases(ArithmeticCodecFactory factory) { super(factory); }

		@Test public void wikipediaEncode() {
			int base = 10;
			int[] cumulativeCount = {6, 8, 10};
			int[] input = {0, 0, 1, 0, 2};
			int[] expected = {2, 5, 1};
			
			assertThat(encode(base, input, cumulativeCount), is(equalTo(expected)));
		}
		
		@Test public void wikipediaDecode() {
			int base = 10;
			int[] cumulativeCount = {6, 8, 10};
			int[] input = {2, 5, 1};
			int[] expected = {0, 0, 1, 0, 2};
			
			assertThat(decode(base, input, expected.length, cumulativeCount), is(equalTo(expected)));
		}
		
		@Test public void paperEncode() {
			int base = 10;
			int[] cumulativeCount = {10, 31, 58, 100};
			int[] input = {3, 2, 1, 3, 3, 3, 0, 0, 3, 2, 1};
			int[] expected = {7, 4, 3, 6, 0, 2, 4};
			
			assertThat(encode(base, input, cumulativeCount), is(equalTo(expected)));
		}
		
		@Test public void paperDecode() {
			int base = 10;
			int[] cumulativeCount = {10, 31, 58, 100};
			int[] input = {7, 4, 3, 6, 0, 2, 4};
			int[] expected = {3, 2, 1, 3, 3, 3, 0, 0, 3, 2, 1};
			
			assertThat(decode(base, input, expected.length, cumulativeCount), is(equalTo(expected)));
		}

		private int[] encode(int outbase, int[] input, int[] cumulativeCount) {
			try {
				IntegerListOutputStream stream = new IntegerListOutputStream();
				Encoder encoder = factory.encoder(stream, outbase);
				
				for (int i = 0; i < input.length; i++)
					encoder.write(input[i], cumulativeCount);
				
				encoder.flush();
				return stream.toIntArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int[] decode(int inbase, int[] input, int n, int[] cumulativeCount) {
			try {
				IntArrayInputStream stream = new IntArrayInputStream(input);
				Decoder decoder = factory.decoder(stream, inbase);
				
				int[] result = new int[n];
				
				for (int i = 0; i < result.length; i++)
					result[i] = decoder.read(cumulativeCount);
				
				return result;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public abstract static class Permutation extends ArithmeticCodecUnitTest {

		Permutation(ArithmeticCodecFactory factory) { super(factory); }
		
		@Test public void permutationOf2()  { permutationOf( 2); }
		@Test public void permutationOf3()  { permutationOf( 3); }
		@Test public void permutationOf4()  { permutationOf( 4); }
		@Test public void permutationOf5()  { permutationOf( 5); }
		@Test public void permutationOf6()  { permutationOf( 6); }
		@Test public void permutationOf7()  { permutationOf( 7); }
		@Test public void permutationOf8()  { permutationOf( 8); }
		@Test public void permutationOf9()  { permutationOf( 9); }
		
		@Ignore("ETA: ~40 seconds")
		@Test public void permutationOf10() { permutationOf(10); }
		
		@Ignore("ETA: ~15 minutes")
		@Test public void permutationOf11() { permutationOf(11); }
		
		@Ignore("ETA: ~2.5 hours")
		@Test public void permutationOf12() { permutationOf(12); }
		
		@Ignore("ETA: ~1.3 day")
		@Test public void permutationOf13() { permutationOf(13); }
		
		@Ignore("ETA: ~19 days")
		@Test public void permutationOf14() { permutationOf(14); }
		
		@Ignore("ETA: ~9 months")
		@Test public void permutationOf15() { permutationOf(15); }
		
		// for more than 15, n^n would not fit 64-bit longs (let alone the ETA of 12 years)
		// needed for the fast encoding function used to test if a permutation was already seen
	
		private void permutationOf(final int n) {
			try {
				int base = 1;
				
				for(int i = 1; i <= n; i++)
					base *= i;
		
				int[] sorted;
				
				// this will produce 0, 1, 2 ... n-1
				{
					sorted = even(n);
					sorted[0]--;
					sorted = toCumulative(sorted);
				}
		
				long prev = 0;
				for (int i = 0; i < base; i++) {
					IntArrayInputStream stream = new IntArrayInputStream(i);
					
					Decoder decoder = factory.decoder(stream, base);
					int[] count = even(n);
							
					int[] x = new int[n];
					for (int j = 0; j < n; j++) {
						int m = decoder.read(toCumulative(count));
						count[m]--;
						x[j] = m;
					}
					
					// assert that "x" is a new permutation not previously seen
					{
						// fast, holey encoding of "x" in base n... 
						long result = 0;
						for (int j = 0; j < n; j++) {
							result *= n;
							result += x[j];
						}
						
						// ...which nonetheless is guaranteed to be monotonically increasing function of "i"
						assertThat(result, is(greaterThan(prev)));
						prev = result;
					}
					
					// assert that "x" is a permutation
					{
						if (i == 0) {
							// the very first MUST BE sorted already (if not, the test will fail ON PURPOSE)
						} else if (i == base - 1) {
							// the very last MUST BE reverse sorted (if not, the test will fail ON PURPOSE)
							// since this is the last time using "sorted" we will reverse it mercilessly
							reverse(sorted);
						} else
							sort(x);
						
						assertThat(x, is(equalTo(sorted)));
					}
					
					// this also asserts that "x" is a permutation
					// since no element was returned more than once 
					assertThat(count, is(equalTo(new int[n])));
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private static void reverse(int... is) {
			for (int i = 0; i < is.length/2; i++) {
				int t = is[i];
				is[i] = is[is.length - i - 1];
				is[is.length - i - 1] = t;
			}
		}
	}
}
