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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
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
}
