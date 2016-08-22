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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.eti.rslemos.bitsmagic.stream.IntOutputStream;

public class DelayedZeroIntOutputStreamUnitTest {
	private IntOutputStream sink;
	private DelayedZeroIntOutputStream delayedZeroSink;

	@Before public void setup() {
		sink = mock(IntOutputStream.class);
		delayedZeroSink = new DelayedZeroIntOutputStream(sink);
	}
	
	@After public void teardown() {
		verifyNoMoreInteractions(sink);
	}
	
	@Test public void forwardNonZero() throws IOException {
		delayNZeros(0);
	}
	
	@Test public void swallowZero() throws IOException {
		delayedZeroSink.writeInt(0);
		verify(sink, never()).writeInt(0);
	}
	
	@Test public void delayZeroOnce() throws IOException {
		delayNZeros(1);
	}
	
	@Test public void delay32KZeros() throws IOException {
		delayNZeros(1 << 15);
	}

	@Ignore("Meaningful only if DelayedZeroIntOutputStream.zeros is changed to short")
	@Test(expected = IllegalStateException.class)
	public void delay128KZeros() throws IOException {
		delayNZeros(1 << 17);
	}

	@Ignore("Meaningful only if DelayedZeroIntOutputStream.zeros is changed to short")
	@Test public void delay64KZeros() throws IOException {
		delayNZeros((1 << 16) - 1);
	}
	
	private void delayNZeros(int n) throws IOException {
		for (long i = 0; i < n; i++)
			delayedZeroSink.writeInt(0);
		delayedZeroSink.writeInt(1);
		
		verify(sink, times(n)).writeInt(0);
		verify(sink).writeInt(1);
	}
}
