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

import static br.eti.rslemos.bitsmagic.arithmetic.ArithmeticCodecUnitTest.Roundtrip.Operation.NoUnderflow.NO_UNDERFLOW;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

import java.io.IOException;

import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import br.eti.rslemos.bitsmagic.Ones;
import br.eti.rslemos.bitsmagic.arithmetic.ArithmeticCodec.Decoder;
import br.eti.rslemos.bitsmagic.arithmetic.ArithmeticCodec.Encoder;
import br.eti.rslemos.bitsmagic.arithmetic.ArithmeticCodecUnitTest.AutomaticCases;
import br.eti.rslemos.bitsmagic.stream.IntInputStream;
import br.eti.rslemos.bitsmagic.stream.IntOutputStream;

@RunWith(Enclosed.class)
public class IntegralPowerOf2BaseArithmeticCodecUnitTest {
	@RunWith(Enclosed.class)
	public static class Roundtrip {
		@RunWith(Enclosed.class)
		public static class NoUnderflow {
			public static class Base2 extends AutomaticCases.Base2 { public Base2() { super(new ArithmeticCodecUnitTest.Roundtrip(FACTORY, NO_UNDERFLOW)); } }
			public static class IntegralPowerOf2 extends AutomaticCases.IntegralPowerOf2 { public IntegralPowerOf2() { super(new ArithmeticCodecUnitTest.Roundtrip(FACTORY, NO_UNDERFLOW)); } }
		}
	}

	public static final ArithmeticCodecFactory FACTORY = new ArithmeticCodecFactory() {
		@Override public void isValidBase(int base) { assumeThat(Ones.ones(base), is(equalTo(1))); }
		
		@Override public Encoder encoder(IntOutputStream sink, int outbase) {
			isValidBase(outbase);
			return new IntegralPowerOf2BaseArithmeticCodec.Encoder(sink, (int) (Math.log(outbase)/Math.log(2)));
		}

		@Override public Decoder decoder(IntInputStream source, int inbase) throws IOException { 
			isValidBase(inbase);
			return new IntegralPowerOf2BaseArithmeticCodec.Decoder(source, (int) (Math.log(inbase)/Math.log(2)));
		}
	};
}
