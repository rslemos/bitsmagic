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
	
	public abstract static class AutomaticCases {
		private static final int MAX_SAMPLES = 1 << 9;
		
		private final AutomaticOp op;
		
		AutomaticCases(AutomaticOp op) { this.op = op; }
		
		void runSingle(int externalBase, int internalBase, int... externalData) {
			try { runSingle0(externalBase, internalBase, externalData); }
			catch (IOException e) { throw new RuntimeException(e); }
		}

		private void runSingle0(int externalBase, int internalBase, int... externalData) throws IOException {
			op.run(internalBase, externalBase, externalData);
		}
		
		void runMultiple(int externalDigitCount, int externalBase, int internalBase) {
			try { runMultiple0(externalDigitCount, externalBase, internalBase); }
			catch (IOException e) { throw new RuntimeException(e); }
		}

		private void runMultiple0(int externalDigitCount, int externalBase, int internalBase) throws IOException {
			int[] externalData = new int[externalDigitCount];
			
			double fullRange = Math.pow(externalBase, externalData.length);
			
			if (fullRange <= MAX_SAMPLES)
				do op.run(internalBase, externalBase, externalData);
				while (inc(externalData, externalBase) == 0);
			else {
				int amount = (int)((fullRange / MAX_SAMPLES)) | 7;
				
				if (amount <= Integer.MAX_VALUE / 2) {
					do op.run(internalBase, externalBase, externalData);
					while (add(externalData, externalBase, amount) == 0);
				}
			}
		}
		
		public abstract static class Base2 extends AutomaticCases {
			Base2(AutomaticOp op) { super(op); }
			
			@Test public void from_1x_base1M_to_20x_base2_0b00000000000000000000() { runSingle(1 << 20, 2, 0b00000000000000000000); }
			@Test public void from_1x_base1M_to_20x_base2_0b10011100001111100000() { runSingle(1 << 20, 2, 0b10011100001111100000); }
			@Test public void from_1x_base1M_to_20x_base2_0b10011100001111100001() { runSingle(1 << 20, 2, 0b10011100001111100001); }
			@Test public void from_1x_base1M_to_20x_base2_0b11111111111111111111() { runSingle(1 << 20, 2, 0b11111111111111111111); }
			
			@Test public void from_4x_base1M_to_80x_base2_0b00000000000000000000_0b10011100001111100000_0b10011100001111100001_0b11111111111111111111() { 
				runSingle(1 << 20, 2, 0b00000000000000000000, 0b10011100001111100000, 0b10011100001111100001, 0b11111111111111111111);
			}
			
			@Test public void from_4x_base1M_to_80x_base2_0b10011100001111100000_0b10011100001111100001_0b11111111111111111111_0b00000000000000000000() { 
				runSingle(1 << 20, 2, 0b10011100001111100000, 0b10011100001111100001, 0b11111111111111111111, 0b00000000000000000000);
			}
			
			@Test public void from_4x_base1M_to_80x_base2_0b10011100001111100001_0b11111111111111111111_0b00000000000000000000_0b10011100001111100000() { 
				runSingle(1 << 20, 2, 0b10011100001111100001, 0b11111111111111111111, 0b00000000000000000000, 0b10011100001111100000);
			}
			
			@Test public void from_4x_base1M_to_80x_base2_0b11111111111111111111_0b00000000000000000000_0b10011100001111100000_0b10011100001111100001() { 
				runSingle(1 << 20, 2, 0b11111111111111111111, 0b00000000000000000000, 0b10011100001111100000, 0b10011100001111100001);
			}
			
			@Test public void from_48x_base2_to_48x_base2_010101010011001100001111101010101100110011110000() { 
				runSingle(2, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0); 
			}
		
			@Test public void from_1x_base16_to_4x_base2() { runMultiple(1, 16, 2); }
			@Test public void from_1x_base8_to_3x_base2()  { runMultiple(1, 8, 2); }
			@Test public void from_1x_base4_to_2x_base2()  { runMultiple(1, 4, 2); }
			@Test public void from_1x_base2_to_1x_base2()  { runMultiple(1, 2, 2); }
		}
	
		public abstract static class IntegralPowerOf2 extends AutomaticCases {
			IntegralPowerOf2(AutomaticOp op) { super(op); }
			
			@Test public void from_1x_base64k_to_4x_base16_0b1100001111000001()    { runSingle(1 << 16, 16, 0b1100001111000001); }
			@Test public void from_1x_base256k_to_6x_base8_0b011100001101100100()  { runSingle(1 << 18, 8, 0b011100001101100100); }
			@Test public void from_1x_base1M_to_10x_base4_0b10011100000111110000() { runSingle(1 << 20, 4, 0b10011100000111110000); }
	
			@Test public void from_1x_base64_to_2x_base8() { runMultiple(1, 64, 8); }
			@Test public void from_1x_base16_to_2x_base4() { runMultiple(1, 16, 4); }
			@Test public void from_2x_base8_to_3x_base4()  { runMultiple(2, 8, 4); }
			
			@Test public void from_2x_base2_to_1x_base4()  { runMultiple(2, 2,  4); }
			@Test public void from_3x_base2_to_1x_base8()  { runMultiple(3, 2,  8); }
			@Test public void from_3x_base4_to_2x_base8()  { runMultiple(3, 4,  8); }
			@Test public void from_4x_base2_to_1x_base16() { runMultiple(4, 2, 16); }
			@Test public void from_2x_base4_to_1x_base16() { runMultiple(2, 4, 16); }
			@Test public void from_2x_base8_to_1x_base64() { runMultiple(2, 8, 64); }
	
			
			@Test public void from_1x_base256_to_4x_base4() { runMultiple(1, 256, 4); }
			@Test public void from_1x_base64k_to_8x_base4() { runMultiple(1, 1 << 16, 4); }
	
			@Test public void from_1x_base512_to_3x_base8() { runMultiple(1, 512, 8); }
	
			@Test public void from_1x_base256_to_2x_base16() { runMultiple(1, 256, 16); }
			
			@Test public void from_1x_base1k_to_2x_base32() { runMultiple(1, 1 << 10, 1 << 5); }
			@Test public void from_1x_base32k_to_3x_base32() { runMultiple(1, 1 << 15, 1 << 5); }
			
			@Test public void from_1x_base4k_to_2x_base64() { runMultiple(1, 1 << 12, 1 << 6); }
			@Test public void from_1x_base256k_to_3x_base64() { runMultiple(1, 1 << 18, 1 << 6); }
			
			@Test public void from_1x_base16k_to_2x_base128() { runMultiple(1, 1 << 14, 1 << 7); }
			@Test public void from_1x_base2M_to_3x_base128() { runMultiple(1, 1 << 21, 1 << 7); }
			
			@Test public void from_1x_base64k_to_2x_base256() { runMultiple(1, 1 << 16, 1 << 8); }
			
			@Test public void from_1x_base256k_to_2x_base512() { runMultiple(1, 1 << 18, 1 << 9); }
			
			@Test public void from_1x_base1M_to_2x_base1024() { runMultiple(1, 1 << 20, 1 << 10); }
		}
		
		public abstract static class NonIntegralPowerOf2 extends AutomaticCases {
			NonIntegralPowerOf2(AutomaticOp op) { super(op); }
			
			@Test public void from_1x_base1000_to_3x_base10_762()         { runSingle(   1000,  10,    762); }
			@Test public void from_1x_base1000000_to_3x_base100_348921()  { runSingle(1000000, 100, 348921); }
	
			@Test public void from_1x_base100_to_2x_base10() { runMultiple(1, 100, 10); }
			@Test public void from_1x_base81_to_2x_base9()   { runMultiple(1, 81, 9); }
			@Test public void from_1x_base49_to_2x_base7()   { runMultiple(1, 49, 7); }
			@Test public void from_1x_base36_to_2x_base6()   { runMultiple(1, 36, 6); }
			@Test public void from_1x_base25_to_2x_base5()   { runMultiple(1, 25, 5); }
			@Test public void from_1x_base9_to_2x_base3()    { runMultiple(1, 9, 3); }
			
			@Test public void from_2x_base3_to_1x_base9()    { runMultiple(2,   3,   9); }
			@Test public void from_2x_base5_to_1x_base25()   { runMultiple(2,   5,  25); }
			@Test public void from_2x_base6_to_1x_base36()   { runMultiple(2,   6,  36); }
			@Test public void from_2x_base7_to_1x_base49()   { runMultiple(2,   7,  49); }
			@Test public void from_2x_base9_to_1x_base81()   { runMultiple(2,   9,  81); }
			@Test public void from_2x_base10_to_1x_base100() { runMultiple(2,  10, 100); }
	
			@Test public void from_1x_base1000_to_3x_base10()  { runMultiple(1, 1000, 10); }
			@Test public void from_3x_base10_to_1x_base1000()  { runMultiple(3,   10, 1000); }
			
			@Test public void from_1x_base100_to_2x_base10_42()  { runSingle(100, 10, 42); }
			
			@Test public void from_2x_base13_to_4x_base_5() { runMultiple(2, 13, 5); }
			@Test public void from_3x_base17_to_8x_base_3() { runMultiple(3, 17, 3); }
		}
		
		public static int inc(int[] digits, int base) {
			for (int i = 0; i < digits.length; i++) {
				if (++digits[i] < base) return 0;
				digits[i] = 0;
			}
			
			return 1;
		}
		
		public static int add(int[] digits, int base, int amount) {
			for (int i = 0; i < digits.length; i++) {
				digits[i] += amount;
				amount = digits[i] / base;
				if (amount == 0) return 0;
				digits[i] %= base;
			}
			
			return amount;
		}
	}

	private abstract static class AutomaticOp extends ArithmeticCodecUnitTest {
		AutomaticOp(ArithmeticCodecFactory factory) { super(factory); }

		abstract void run(int internalBase, int externalBase, int[] externalData) throws IOException;
	}
	
	public static final class Roundtrip extends AutomaticOp {
		private final Operation op;

		Roundtrip(ArithmeticCodecFactory factory, Operation op) {
			super(factory);
			this.op = op;
		}

		@Override void run(int internalBase, int externalBase, int[] externalData) throws IOException {
			if (externalData[externalData.length - 1] == 0)
				return;
			
			int[] internalData = op.encode(factory, internalBase, externalBase, externalData);
			int[] result = op.decode(factory, internalBase, externalData.length, externalBase, internalData);
			
			assertThat(result, is(equalTo(externalData)));
		}
		
		public static abstract class Operation {
			public static class NoUnderflow extends Operation {
				public static final NoUnderflow NO_UNDERFLOW = new NoUnderflow();
				
				@Override public int[] encode(ArithmeticCodecFactory factory, int internalBase, int externalBase, int[] externalData) throws IOException {
					IntArrayOutputStream sink = new IntArrayOutputStream(internalDigits(internalBase, externalBase, externalData.length));
					Encoder encoder = factory.encoder(sink, internalBase);
					int[] cumulativeCount = toCumulative(even(externalBase));
					for (int d : externalData)
						encoder.write(d, cumulativeCount);
					encoder.flush();
					return sink.toIntArray();
				}

				@Override public int[] decode(ArithmeticCodecFactory factory, int internalBase, int externalDigits, int externalBase, int[] internalData) throws IOException {
					int[] externalData = new int[externalDigits];
					IntArrayInputStream source = new IntArrayInputStream(internalData);
					Decoder decoder = factory.decoder(source, internalBase);
					int[] cumulativeCount = toCumulative(even(externalBase));
					for (int i = 0; i < externalData.length; i++) {
						externalData[i] = decoder.read(cumulativeCount);
					}
					return externalData;
				}
				
				public static int internalDigits(int internalBase, int externalBase, int externalDigits) {
					return (int) Math.ceil(externalDigits*Math.log(externalBase)/Math.log(internalBase));
				}
			}
			
			public static class Underflow extends Operation {
				private static final int[] CUMULATIVE_COUNT_RESOLVE = toCumulative(1, 1);
				
				public static enum Resolve {
					LOW(0), HIGH(1);
					
					public final int resolve;

					private Resolve(int resolve) { this.resolve = resolve; }
				}

				public static enum ForceUnderflow {
					MILD(1, 1000),
					SEVERE(29, 1L << 8),
					EXTREME(29, 1L << 32),
					;
					
					public final long underflowLength;
					public final int[] underflowCumulativeCount;
					
					private ForceUnderflow(int underflowScale, long underflowLength) {
						this.underflowLength = underflowLength;
						this.underflowCumulativeCount = toCumulative((1 << underflowScale) - 1, 2, (1 << underflowScale) - 1);
					}
					
					public Underflow resolve(Resolve resolve) { return new Underflow(this, resolve); }
				}
				
				private final ForceUnderflow underflow;
				private final Resolve resolve;
				
				private Underflow(ForceUnderflow underflow, Resolve resolve) {
					this.underflow = underflow;
					this.resolve = resolve;
				}
				
				@Override public int[] encode(ArithmeticCodecFactory factory, int internalBase, int externalBase, int[] externalData) throws IOException {
					IntArrayOutputStream sink = new IntArrayOutputStream(internalDigits(internalBase, externalBase, externalData.length));
					Encoder encoder = factory.encoder(sink, internalBase);

					encodeHeadPrefix(encoder, externalBase, externalData);
					encodeHeadLastSymbol(encoder, externalBase, externalData);
					encodeTailPrefix(encoder);
					encodeTailLastSymbol(encoder);
					
					encoder.flush();
					return sink.toIntArray();
				}

				private void encodeHeadPrefix(Encoder encoder, int externalBase, int[] externalData) throws IOException {
					int[] cumulativeCount = toCumulative(even(externalBase));
					for (int i = 0; i < externalData.length - 1; i++)
						encoder.write(externalData[i], cumulativeCount);
				}

				private void encodeHeadLastSymbol(Encoder encoder, int externalBase, int[] externalData) throws IOException {
					int[] cumulativeSplitCount = splitCountOver(externalBase);
					encoder.write(externalData[externalData.length - 1], cumulativeSplitCount);
				}

				private void encodeTailPrefix(Encoder encoder) throws IOException {
					for (long l = 0; l < underflow.underflowLength; l++)
						encoder.write(1, underflow.underflowCumulativeCount);
				}

				private void encodeTailLastSymbol(Encoder encoder) throws IOException {
					encoder.write(resolve.resolve, CUMULATIVE_COUNT_RESOLVE);
				}

				@Override public int[] decode(ArithmeticCodecFactory factory, int internalBase, int externalDigits, int externalBase, int[] internalData) throws IOException {
					int[] externalData = new int[externalDigits];
					
					IntArrayInputStream source = new IntArrayInputStream(internalData);
					Decoder decoder = factory.decoder(source, internalBase);
					
					decodeHeadPrefix(decoder, externalBase, externalData);
					decodeHeadLastSymbol(decoder, externalBase, externalData);
					decodeTailPrefix(decoder);
					decodeTailLastSymbol(decoder);
					
					return externalData;
				}

				private void decodeHeadPrefix(Decoder decoder, int externalBase, int[] externalData) throws IOException {
					int[] cumulativeCount = toCumulative(even(externalBase));
					for (int i = 0; i < externalData.length - 1; i++)
						externalData[i] = decoder.read(cumulativeCount);
				}

				private void decodeHeadLastSymbol(Decoder decoder, int externalBase, int[] externalData) throws IOException {
					int[] cumulativeSplitCount = splitCountOver(externalBase);
					externalData[externalData.length - 1] = decoder.read(cumulativeSplitCount);
				}

				private void decodeTailPrefix(Decoder decoder) throws IOException {
					for (long l = 0; l < underflow.underflowLength; l++)
						assertThat(decoder.read(underflow.underflowCumulativeCount), is(equalTo(1)));
				}

				private void decodeTailLastSymbol(Decoder decoder) throws IOException {
					assertThat(decoder.read(CUMULATIVE_COUNT_RESOLVE), is(equalTo(resolve.resolve)));
				}
				
				private static int[] splitCountOver(int base) {
					int[] count = even(base + 1, 2);
					count[0] = count[count.length - 1] = 1;
					return toCumulative(count);
				}
				
				private int internalDigits(int internalBase, int externalBase, int externalDigits) {
					// https://en.wikipedia.org/wiki/Entropy_(information_theory)#Definition
					
					double digits = 0;
					
					// head prefix
					digits += (externalDigits - 1)*(Math.log(externalBase));
					// head last symbol
					digits += 1*(Math.log(externalBase+1));
					// tail prefix
					int p_1 = underflow.underflowCumulativeCount[2]/(underflow.underflowCumulativeCount[1] - underflow.underflowCumulativeCount[0]);
					digits += underflow.underflowLength*(Math.log(p_1));
					// tail last symbol
					digits += 1*(Math.log(2));
					
					digits /= Math.log(internalBase);
					
					return (int) Math.ceil(digits);
				}
			}
			
			public abstract int[] encode(ArithmeticCodecFactory factory, int internalBase, int externalBase, int[] externalData) throws IOException;
			public abstract int[] decode(ArithmeticCodecFactory factory, int internalBase, int externalDigits, int externalBase, int[] internalData) throws IOException;
		}
	}
}
