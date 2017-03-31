/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2017 Rodrigo Lemos
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

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@Fork(2)
public class StoreBenchmark {
	
	@Param({"1", "2", "4", "8", "16", "32", "64"})
	public static int width;
	
	@State(Scope.Benchmark)
	public static class ByteArray {
		public static byte[] data = new byte[16];
			
		public static class Bit {
			public static class Write {
				public static class Aligned extends StoreBenchmark {
					
					@Benchmark
					public byte[] run() {
						for(int i=0; i<width; i++)
							Store.writeBit(data, i, true);
						
						return data;
					}
				}
				
				public static class MisAligned extends StoreBenchmark {
					
					@Benchmark
					public byte[] run() {
						for(int i=1; i<width + 1; i++)
							Store.writeBit(data, i, true);
						
						return data;
					}
				}
			}
			
			public static class Read {
				public static class Aligned extends StoreBenchmark {
					
					@Benchmark
					public boolean run() {
						boolean result = false;
						for(int i=0; i<width; i++)
							result ^= Store.readBit(data, i);
						
						return result;
					}
				}
				
				public static class MisAligned extends StoreBenchmark {
					
					@Benchmark
					public boolean run() {
						boolean result = false;
						for(int i=1; i<width + 1; i++)
							result ^= Store.readBit(data, i);
						
						return result;
					}
				}
			}
		}
	}
}
