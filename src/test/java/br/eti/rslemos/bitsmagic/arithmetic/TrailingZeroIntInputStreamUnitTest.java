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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.EOFException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.eti.rslemos.bitsmagic.stream.IntInputStream;

public class TrailingZeroIntInputStreamUnitTest {
	private IntInputStream source;
	private TrailingZeroIntInputStream trailingZeroSource;

	@Before public void setup() {
		source = mock(IntInputStream.class);
		trailingZeroSource = new TrailingZeroIntInputStream(source);
	}
	
	@After public void teardown() {
		verifyNoMoreInteractions(source);
	}
	
	@Test public void readOnce() throws IOException {
		when(source.readInt()).thenReturn(1);
		assertThat(trailingZeroSource.readInt(), is(equalTo(1)));
		verify(source).readInt();
	}

	@Test public void readPastEOF() throws IOException {
		when(source.readInt()).thenThrow(new EOFException());
		assertThat(trailingZeroSource.readInt(), is(equalTo(0)));
		verify(source).readInt();
	}
}