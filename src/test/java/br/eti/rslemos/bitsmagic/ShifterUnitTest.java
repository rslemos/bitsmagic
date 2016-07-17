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
public class ShifterUnitTest {
	@Ignore
	public abstract static class Cases<T> implements StorageBuilder<T> {
		protected abstract void shr(T data, int i, int length, int amount);
		
		@Test public void shr__72_64__206() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -72,   64, -206);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__72_64__70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -72,   64,  -70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__72_64_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -72,   64,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__72_64_202() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -72,   64,  202);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__72_64_338() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -72,   64,  338);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_64__200() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   64, -200);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_64__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   64,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_64_68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   64,   68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_64_202() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   64,  202);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_64_336() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   64,  336);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_66__204() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   66, -204);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_66__68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   66,  -68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_66_68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   66,   68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_66_204() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   66,  204);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__70_66_340() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -70,   66,  340);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66__202() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66, -202);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66__200() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66, -200);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66__68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66,  -68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66_68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66,   68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66_200() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66,  200);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66_202() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66,  202);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66_334() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66,  334);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__68_66_336() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -68,   66,  336);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64__196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64, -196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64__194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64, -194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64_194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64,  194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64_196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64,  196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64_324() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64,  324);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_64_326() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   64,  326);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_66__198() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   66, -198);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_66__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   66,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_66_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   66,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_66_198() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   66,  198);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__66_66_330() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -66,   66,  330);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__65_63__192() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -65,   63, -192);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__65_63__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -65,   63,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__65_63_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -65,   63,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__65_63_192() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -65,   63,  192);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__65_63_320() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -65,   63,  320);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63__191() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63, -191);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63__190() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63, -190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63__64() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63__63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63_63() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63,   63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63_190() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63,  190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63_191() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63,  191);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63_317() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63,  317);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_63_318() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   63,  318);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_64__192() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   64, -192);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_64__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   64,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_64_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   64,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_64_192() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   64,  192);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_64_320() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   64,  320);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_66__196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   66, -196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_66__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   66,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_66_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   66,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_66_194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   66,  194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__64_66_324() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -64,   66,  324);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63__190() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63, -190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63__189() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63, -189);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63__64() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63__63() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63_62() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63,   62);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63_63() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63,   63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63_188() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63,  188);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63_189() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63,  189);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63_314() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63,  314);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__63_63_315() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -63,   63,  315);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_63__188() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   63, -188);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_63__63() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   63,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_63_62() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   63,   62);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_63_187() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   63,  187);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_63_312() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000010L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   63,  312);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_64__190() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   64, -190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_64__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   64,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_64_62() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   64,   62);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_64_188() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   64,  188);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__62_64_314() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,  -62,   64,  314);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__34_34__72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111110000000000000000000000000000000000L);
			
			shr(subject,  -34,   34,  -72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__34_34__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111110000000000000000000000000000000000L);
			
			shr(subject,  -34,   34,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__34_34_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111110000000000000000000000000000000000L);
			
			shr(subject,  -34,   34,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__34_34_132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111110000000000000000000000000000000000L);
			
			shr(subject,  -34,   34,  132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__34_34_200() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111110000000000000000000000000000000000L);
			
			shr(subject,  -34,   34,  200);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0__16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,  -16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0__12() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,  -12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0__8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,   -8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0_0() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,    0);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0_8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0_12() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,   12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0_16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,   16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_0_20() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -8,    0,   20);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2__18() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,  -18);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2__17() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,  -17);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2__16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,  -16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2__8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,   -8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2__7() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,   -7);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2__6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,   -6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2_3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2_12() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,   12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2_13() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,   13);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2_14() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,   14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2_22() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,   22);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2_23() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,   23);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_2_24() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -8,    2,   24);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4__22() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110011L);
			
			shr(subject,   -8,    4,  -22);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4__20() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -8,    4,  -20);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4__10() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110011L);
			
			shr(subject,   -8,    4,  -10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4__8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -8,    4,   -8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110011L);
			
			shr(subject,   -8,    4,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -8,    4,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4_14() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110011L);
			
			shr(subject,   -8,    4,   14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4_16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -8,    4,   16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4_26() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110011L);
			
			shr(subject,   -8,    4,   26);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_4_28() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -8,    4,   28);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_8__22() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -8,    8,  -22);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_8__6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -8,    8,   -6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_8_10() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -8,    8,   10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_8_26() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -8,    8,   26);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_8_42() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -8,    8,   42);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66__147() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66, -147);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66__146() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66, -146);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66__145() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66, -145);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66__144() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66, -144);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66__73() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,  -73);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66__72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,  -72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66__71() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,  -71);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66__70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,  -70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_75() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,   75);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_76() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,   76);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_77() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,   77);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_78() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,   78);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_149() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,  149);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_150() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,  150);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_151() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,  151);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__8_66_152() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -8,   66,  152);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_8__20() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -6,    8,  -20);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_8__6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -6,    8,   -6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_8_8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -6,    8,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_8_22() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -6,    8,   22);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_8_36() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000000L);
			
			shr(subject,   -6,    8,   36);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_63__130() {
			T subject  = build(0b0000000011111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   63, -130);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_63__61() {
			T subject  = build(0b0000000011111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   63,  -61);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_63_8() {
			T subject  = build(0b0000000011111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   63,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_63_77() {
			T subject  = build(0b0000000011111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   63,   77);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_63_146() {
			T subject  = build(0b0000000011111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   63,  146);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66__136() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   66, -136);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66__84() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,   -6,   66,  -84);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66__78() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,  -78);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66__76() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,  -76);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   66,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66__12() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,   -6,   66,  -12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66__6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,   -6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   66,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,   -6,   66,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,   68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_80() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   66,   80);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,   -6,   66,  132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_138() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,  138);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_140() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,  140);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_152() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -6,   66,  152);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_204() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,   -6,   66,  204);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_210() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,  210);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__6_66_212() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -6,   66,  212);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__5_66__74() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -5,   66,  -74);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__5_66__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -5,   66,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__5_66_68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -5,   66,   68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__5_66_139() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -5,   66,  139);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__5_66_210() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -5,   66,  210);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_2__8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -4,    2,   -8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_2__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -4,    2,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_2_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -4,    2,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_2_10() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -4,    2,   10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_2_16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111100L);
			
			shr(subject,   -4,    2,   16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_4__12() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -4,    4,  -12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_4__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -4,    4,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_4_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -4,    4,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_4_12() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -4,    4,   12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_4_20() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111000L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111110000L);
			
			shr(subject,   -4,    4,   20);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_6__16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111100110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111000010L);
			
			shr(subject,   -4,    6,  -16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_6__6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111100110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111000010L);
			
			shr(subject,   -4,    6,   -6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_6_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111100110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111000010L);
			
			shr(subject,   -4,    6,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_6_14() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111100110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111000010L);
			
			shr(subject,   -4,    6,   14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_6_24() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111100110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111000010L);
			
			shr(subject,   -4,    6,   24);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64__134() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -4,   64, -134);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64__72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,  -72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64__70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,  -70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -4,   64,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -4,   64,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64_70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -4,   64,   70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64_132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,  132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64_134() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,  134);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64_138() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -4,   64,  138);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64_200() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,  200);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_64_202() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   64,  202);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_66__72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   66,  -72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_66__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   66,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_66_68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   66,   68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_66_138() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   66,  138);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__4_66_208() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -4,   66,  208);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_64__68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   64,  -68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_64__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   64,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_64_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   64,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_64_133() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   64,  133);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_64_200() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   64,  200);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66__135() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -3,   66, -135);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66__72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   66,  -72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -3,   66,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   66,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66_3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -3,   66,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   66,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66_72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -3,   66,   72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66_135() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   66,  135);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66_141() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -3,   66,  141);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__3_66_204() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -3,   66,  204);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8__16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110011110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100001001L);
			
			shr(subject,   -2,    8,  -16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8__14() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,   -2,    8,  -14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8__6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110011110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100001001L);
			
			shr(subject,   -2,    8,   -6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,   -2,    8,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110011110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100001001L);
			
			shr(subject,   -2,    8,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8_6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,   -2,    8,    6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8_14() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110011110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100001001L);
			
			shr(subject,   -2,    8,   14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8_16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,   -2,    8,   16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8_24() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110011110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100001001L);
			
			shr(subject,   -2,    8,   24);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_8_26() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,   -2,    8,   26);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63__70() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,  -70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63__5() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,   -5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63_60() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63_125() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,  125);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63_129() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,  129);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63_190() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,  190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_63_194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   63,  194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64__132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   64, -132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64__131() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64, -131);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64__124() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64, -124);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64__70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   64,  -70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   64,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64__58() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64,  -58);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   64,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_0() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   64,    0);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_62() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   64,   62);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   64,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_67() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64,   67);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_74() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64,   74);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_128() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   64,  128);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   64,  132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_133() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64,  133);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_140() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   64,  140);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_64_194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   64,  194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66__134() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66, -134);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66__133() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66, -133);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66__70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   66,  -70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66__68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   66,  -68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   66,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_0() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   66,    0);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   66,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   66,   68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66,   70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_71() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66,   71);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_134() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   66,  134);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_136() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   66,  136);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_138() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66,  138);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_139() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -2,   66,  139);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_202() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,   66,  202);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_66_204() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -2,   66,  204);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128__195() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128, -195);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128__194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128, -194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128_65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128,   65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128_195() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128,  195);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128_196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128,  196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128_325() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128,  325);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_128_326() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  128,  326);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_129__196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  129, -196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_129__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  129,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_129_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  129,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_129_197() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  129,  197);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_129_328() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  129,  328);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_130__198() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  130, -198);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_130__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  130,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_130_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  130,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_130_198() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  130,  198);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_130_330() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  130,  330);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_132__202() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  132, -202);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_132__68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  132,  -68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_132_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  132,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_132_200() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  132,  200);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__2_132_334() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -2,  132,  334);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63__128() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -1,   63, -128);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63__65() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   63,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -1,   63,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63__1() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   63,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63_0() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -1,   63,    0);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63_63() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   63,   63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -1,   63,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63_127() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   63,  127);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63_128() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,   -1,   63,  128);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_63_191() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   63,  191);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64__129() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   64, -129);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   64,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   64,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   64,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   64,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   64,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   64,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64_129() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   64,  129);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64_131() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   64,  131);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_64_194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   64,  194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__133() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66, -133);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66, -132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,  -70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__69() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,  -69);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,  -68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,   65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66,   68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_69() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66,   69);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_131() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,  131);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,  132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_133() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,  133);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_135() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66,  135);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_136() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   66,  136);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_198() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,  198);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_199() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,  199);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_66_200() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   66,  200);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68__136() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   68, -136);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68__72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   68,  -72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68__67() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   68,  -67);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   68,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   68,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   68,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68_71() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   68,   71);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68_135() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   68,  135);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68_140() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   -1,   68,  140);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_68_204() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,   68,  204);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128__194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128, -194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128__192() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128, -192);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128__63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128_193() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128,  193);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128_195() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128,  195);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128_322() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128,  322);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_128_324() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  128,  324);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_129__194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  129, -194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_129__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  129,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_129_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  129,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_129_196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  129,  196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_129_326() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  129,  326);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_130__196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  130, -196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_130__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  130,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_130_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  130,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_130_197() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  130,  197);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_130_328() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  130,  328);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_132__200() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  132, -200);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_132__67() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  132,  -67);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_132_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  132,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_132_199() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  132,  199);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr__1_132_332() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,   -1,  132,  332);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8__16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,    8,  -16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8__10() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,    0,    8,  -10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8__8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,    8,   -8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,    0,    8,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8_0() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,    8,    0);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8_6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,    0,    8,    6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8_8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,    8,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8_14() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,    0,    8,   14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8_16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,    8,   16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_8_22() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111110000110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111100000010L);
			
			shr(subject,    0,    8,   22);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63__116() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   63, -116);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63__66() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   63,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63__53() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   63,  -53);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63__3() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   63,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63_10() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   63,   10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63_60() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   63,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63_73() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   63,   73);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63_123() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   63,  123);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63_136() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   63,  136);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_63_186() {
			T subject  = build(0b0000000000000000000000000000000000000000000000000000000000001110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   63,  186);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__128() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   64, -128);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__127() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64, -127);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__120() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64, -120);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    0,   64,  -68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   64,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   64,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__56() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64,  -56);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    0,   64,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   64,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_0() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   64,    0);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    0,   64,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   64,   63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   64,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64,   65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64,   72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_124() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    0,   64,  124);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_127() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   64,  127);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_128() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   64,  128);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_129() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64,  129);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_136() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   64,  136);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_188() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    0,   64,  188);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_64_191() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   64,  191);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66__132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   66, -132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66__69() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,  -69);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66__68() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,  -68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   66,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66_0() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   66,    0);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66_63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,   63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   66,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66_129() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,  129);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66_130() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,  130);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66_132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			
			shr(subject,    0,   66,  132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66_195() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,  195);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_66_196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,   66,  196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68__133() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68, -133);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68__132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68, -132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68__131() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68, -131);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68__128() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68, -128);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68__63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68__60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,  -60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_5() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,    5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_71() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,   71);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,   72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_73() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,   73);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_76() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,   76);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_139() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,  139);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_140() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,  140);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_141() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,  141);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_68_144() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    0,   68,  144);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126__189() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126, -189);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126__188() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126, -188);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126__63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126__62() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126,  -62);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126_63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126,   63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126_189() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126,  189);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126_190() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126,  190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126_315() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126,  315);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_126_316() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  126,  316);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127__191() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127, -191);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127__190() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127, -190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127__63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127_63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127,   63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127_190() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127,  190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127_191() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127,  191);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127_317() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127,  317);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_127_318() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  127,  318);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128__193() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128, -193);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128__192() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128, -192);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128__190() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128, -190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128__65() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,  -65);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128__62() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,  -62);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128_63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,   63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128_191() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,  191);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128_192() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,  192);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128_194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,  194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128_319() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,  319);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128_320() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,  320);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_128_322() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  128,  322);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_129__192() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  129, -192);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_129__63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  129,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_129_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  129,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_129_195() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  129,  195);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_129_324() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  129,  324);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130__196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130, -196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130__194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130, -194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130_64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130,   64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130_194() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130,  194);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130_196() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130,  196);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130_324() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130,  324);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_130_326() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  130,  326);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_132__198() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  132, -198);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_132__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  132,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_132_66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  132,   66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_132_198() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  132,  198);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_0_132_330() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111110L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000000L);
			
			shr(subject,    0,  132,  330);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_10__14() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110011111111L);
			
			shr(subject,    2,   10,  -14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_10__6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110011111111L);
			
			shr(subject,    2,   10,   -6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_10_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110011111111L);
			
			shr(subject,    2,   10,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_10_10() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110011111111L);
			
			shr(subject,    2,   10,   10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_10_18() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110011111111L);
			
			shr(subject,    2,   10,   18);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63__112() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   63, -112);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63__90() {
			T subject  = build(0b0000000000000000000000000000000011111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   63,  -90);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63__51() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   63,  -51);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63__29() {
			T subject  = build(0b0000000000000000000000000000000011111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   63,  -29);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63_10() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   63,   10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63_32() {
			T subject  = build(0b0000000000000000000000000000000011111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   63,   32);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63_71() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   63,   71);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63_93() {
			T subject  = build(0b0000000000000000000000000000000011111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   63,   93);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63_132() {
			T subject  = build(0b0000000000111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000011111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   63,  132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_63_154() {
			T subject  = build(0b0000000000000000000000000000000011111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   63,  154);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64__123() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64, -123);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64__116() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64, -116);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64__64() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   64,  -64);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64__61() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64,  -61);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64__54() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64,  -54);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   64,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64_8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   64,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64_63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64,   63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64_70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64,   70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64_122() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   64,  122);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64_125() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64,  125);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64_132() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   64,  132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_64_184() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   64,  184);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_66__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   66,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_66__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   66,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_66_62() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   66,   62);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_66_126() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   66,  126);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_66_190() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   66,  190);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__129() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68, -129);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__128() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68, -128);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__127() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68, -127);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__126() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000011111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68, -126);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   68,  -72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__63() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,  -63);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__62() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,  -62);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__61() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,  -61);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000011111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,  -60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68__6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   68,   -6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_5() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,    5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_6() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000011111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,    6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   68,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_69() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,   69);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_70() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,   70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_71() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,   71);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_72() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000011111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,   72);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_126() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   68,  126);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_135() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000011111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,  135);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_136() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000001111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,  136);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_137() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,  137);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_138() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000011111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    2,   68,  138);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_2_68_192() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111011L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000011L);
			
			shr(subject,    2,   68,  192);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_64__62() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   64,  -62);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_64__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   64,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_64_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   64,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_64_121() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   64,  121);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_64_182() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   64,  182);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_66__66() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   66,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_66__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   66,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_66_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   66,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_66_123() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   66,  123);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_3_66_186() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111110111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000000111L);
			
			shr(subject,    3,   66,  186);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_10__8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111001101111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110000101111L);
			
			shr(subject,    4,   10,   -8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_10__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111001101111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110000101111L);
			
			shr(subject,    4,   10,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_10_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111001101111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110000101111L);
			
			shr(subject,    4,   10,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_10_10() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111001101111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110000101111L);
			
			shr(subject,    4,   10,   10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_10_16() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111001101111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111110000101111L);
			
			shr(subject,    4,   10,   16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_122__176() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  122, -176);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_122__58() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  122,  -58);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_122_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  122,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_122_178() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  122,  178);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_122_296() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  122,  296);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_123__178() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  123, -178);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_123__59() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  123,  -59);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_123_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  123,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_123_179() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  123,  179);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_123_298() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  123,  298);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_124__180() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  124, -180);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_124__60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  124,  -60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_124_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  124,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_124_180() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  124,  180);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_124_300() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  124,  300);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_125__182() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  125, -182);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_125__61() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  125,  -61);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_125_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  125,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_125_181() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  125,  181);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_4_125_302() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111101111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000000001111L);
			
			shr(subject,    4,  125,  302);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_66__56() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    8,   66,  -56);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_66_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    8,   66,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_66_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    8,   66,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_66_118() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    8,   66,  118);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_66_176() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,    8,   66,  176);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_122__168() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  122, -168);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_122__54() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  122,  -54);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_122_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  122,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_122_174() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  122,  174);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_122_288() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  122,  288);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_123__170() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  123, -170);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_123__55() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  123,  -55);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_123_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  123,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_123_175() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  123,  175);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_123_290() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  123,  290);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_124__172() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  124, -172);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_124__56() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  124,  -56);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_124_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  124,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_124_176() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  124,  176);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_124_292() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  124,  292);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_126__176() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  126, -176);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_126__58() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  126,  -58);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_126_60() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  126,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_126_178() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  126,  178);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_8_126_296() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111011111111L);
			T expected = build(0b0000000000000000000000000000000000000000000000000000000011111111L);
			
			shr(subject,    8,  126,  296);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_64__10() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   64,  -10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_64__1() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   64,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_64_8() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   64,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_64_17() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   64,   17);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_64_26() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   64,   26);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_66__14() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   66,  -14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_66__3() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   66,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_66_8() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   66,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_66_19() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   66,   19);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_55_66_30() {
			T subject  = build(0b0011111101111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000000001111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   55,   66,   30);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66__9() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   -9);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66__8() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   -8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66__4() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66__3() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66__2() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_2() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_3() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_4() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_8() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_9() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,    9);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_10() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_14() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_15() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   15);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_16() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_66_20() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   66,   20);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_67__10() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   67,  -10);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_67__3() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   67,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_67_4() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   67,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_67_11() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   67,   11);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_67_18() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   67,   18);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_68__12() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   68,  -12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_68__4() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   68,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_68_4() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   68,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_68_12() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   68,   12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_68_20() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   68,   20);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_69__14() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   69,  -14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_69__5() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   69,   -5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_69_4() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   69,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_69_13() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   69,   13);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_69_22() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   69,   22);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_71__14() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   71,  -14);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_71__3() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   71,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_71_8() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   71,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_71_19() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   71,   19);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_71_30() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   71,   30);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_72__16() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   72,  -16);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_72__4() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   72,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_72_8() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   72,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_72_20() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   72,   20);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_72_32() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   72,   32);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_74__20() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   74,  -20);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_74__6() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   74,   -6);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_74_8() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   74,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_74_22() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   74,   22);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_74_36() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,   74,   36);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_122__116() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  122, -116);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_122__54() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  122,  -54);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_122_8() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  122,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_122_70() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  122,   70);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_122_132() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  122,  132);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_123__66() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  123,  -66);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_123__3() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  123,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_123_60() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  123,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_123_123() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  123,  123);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_123_186() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  123,  186);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_124__68() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  124,  -68);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_124__4() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  124,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_124_60() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  124,   60);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_124_124() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  124,  124);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_60_124_188() {
			T subject  = build(0b0010111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0000111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   60,  124,  188);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_61_66__8() {
			T subject  = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   61,   66,   -8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_61_66__3() {
			T subject  = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   61,   66,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_61_66_2() {
			T subject  = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   61,   66,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_61_66_7() {
			T subject  = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   61,   66,    7);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_61_66_12() {
			T subject  = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0001111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   61,   66,   12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_64__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   64,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_64__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   64,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_64_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   64,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_64_3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   64,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_64_5() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   64,    5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_66__7() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   66,   -7);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_66__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   66,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_66_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   66,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_66_5() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   66,    5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_62_66_9() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   62,   66,    9);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_64__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   64,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_64__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   64,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_64_0() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   64,    0);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_64_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   64,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_64_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   64,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_65__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   65,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_65__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   65,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_65_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   65,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_65_3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   65,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_65_5() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   65,    5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66__5() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,   -5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66_5() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,    5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66_7() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,    7);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_66_8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   66,    8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_68__8() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   68,   -8);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_68__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   68,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_68_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   68,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_68_7() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   68,    7);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_63_68_12() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   63,   68,   12);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_66__4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   66,   -4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_66__2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   66,   -2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_66_0() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   66,    0);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_66_2() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   66,    2);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_66_4() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   66,    4);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_68__7() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   68,   -7);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_68__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   68,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_68_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   68,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_68_5() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   68,    5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_64_68_9() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   64,   68,    9);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_66_68__3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   66,   68,   -3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_66_68__1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   66,   68,   -1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_66_68_1() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   66,   68,    1);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_66_68_3() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   66,   68,    3);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_66_68_5() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   66,   68,    5);
			assertThat(subject, is(equalTo(expected)));
		}

		@Test public void shr_66_68_7() {
			T subject  = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			T expected = build(0b0011111111111111111111111111111111111111111111111111111111111111L);
			
			shr(subject,   66,   68,    7);
			assertThat(subject, is(equalTo(expected)));
		}
	}
	
	public static class Byte extends Cases<byte[]> {
		@Override protected void shr(byte[] data, int i, int length, int amount) { Shifter.shr(data, i, length, amount); }
		@Override public byte[] build(long... d) { return ByteArrayBuilder.build0(d); }
	}
	
	public static class Char extends Cases<char[]> {
		@Override protected void shr(char[] data, int i, int length, int amount) { Shifter.shr(data, i, length, amount); }
		@Override public char[] build(long... d) { return CharArrayBuilder.build0(d); }
	}
	
	public static class Short extends Cases<short[]> {
		@Override protected void shr(short[] data, int i, int length, int amount) { Shifter.shr(data, i, length, amount); }
		@Override public short[] build(long... d) { return ShortArrayBuilder.build0(d); }
	}
	
	public static class Int extends Cases<int[]> {
		@Override protected void shr(int[] data, int i, int length, int amount) { Shifter.shr(data, i, length, amount); }
		@Override public int[] build(long... d) { return IntArrayBuilder.build0(d); }
	}
}
