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

import static br.eti.rslemos.bitsmagic.Store.*;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
@Fork(2)
public class StoreBenchmark {
	
	public static enum StorageClass {
		BYTE() {
			private byte[] data = new byte[16];
		
			@Override public boolean readBit(int i)            { return Store.readBit(data, i); }
			@Override public Object writeBit(int i, boolean v) { Store.writeBit(data, i, v); return data; }
			@Override public byte readByte(int i)              { return Store.readByte(data, i); }
			@Override public Object writeByte(int i, byte v)   { Store.writeByte(data, i, v); return data; }
			@Override public char readChar(int i)              { return Store.readChar(data, i); }
			@Override public Object writeChar(int i, char v)   { Store.writeChar(data, i, v); return data; }
		}, 
		CHAR, 
		SHORT, 
		INT, 
		LONG,
		;
		
		public abstract boolean readBit(int i);
		public abstract Object writeBit(int i, boolean v);
		public abstract byte readByte(int i);
		public abstract Object writeByte(int i, byte v);
		public abstract char readChar(int i);
		public abstract Object writeChar(int i, char v);
	}
	
	public static enum AccessClass {
		BIT() {
			@Override public void read(StorageClass storage, int i, Blackhole bh) {
				bh.consume(storage.readBit(i));
			}

			@Override public void write(StorageClass storage, int i, Object v) {
				storage.writeBit(i, (Boolean)v);
			}
		},
		BYTE() {
			@Override public void read(StorageClass storage, int i, Blackhole bh) {
				bh.consume(storage.readByte(i));
			}

			@Override public void write(StorageClass storage, int i, Object v) {
				storage.writeByte(i, (Byte)v);
			}
		},
		CHAR,
		SHORT,
		INT,
		LONG,
		;
		
		public abstract void read(StorageClass storage, int i, Blackhole bh);
		public abstract void write(StorageClass storage, int i, Object v);
	}
	
	public static enum Operation {
		READ() {
			@Override public void run(StorageClass storage, AccessClass access, int i, Blackhole bh, Object v) {
				access.read(storage, i, bh);
			}
		},
		WRITE() {
			@Override public void run(StorageClass storage, AccessClass access, int i, Blackhole bh, Object v) {
				access.write(storage, i, v);
			}
		},
		;
		
		public abstract void run(StorageClass storage, AccessClass access, int i, Blackhole bh, Object v);
	}
	
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
