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
public class StoreBenchmark {
	
	public static enum StorageClass {
		BYTE() {
			private byte[] data = new byte[16];
		
			@Override public long readBit(int i)               { return Store.readBit(data, i) ? 1 : -1; }
			@Override public Object writeBit(int i, boolean v) { Store.writeBit(data, i, v); return data; }
			@Override public long readByte(int i)              { return Store.readByte(data, i); }
			@Override public Object writeByte(int i, byte v)   { Store.writeByte(data, i, v); return data; }
			@Override public long readChar(int i)              { return Store.readChar(data, i); }
			@Override public Object writeChar(int i, char v)   { Store.writeChar(data, i, v); return data; }
			@Override public long readShort(int i)             { return Store.readChar(data, i); }
			@Override public Object writeShort(int i, short v) { Store.writeShort(data, i, v); return data; }
			@Override public long readInt(int i)               { return Store.readShort(data, i); }
			@Override public Object writeInt(int i, int v)     { Store.writeInt(data, i, v); return data; }
			@Override public long readLong(int i)              { return Store.readLong(data, i); }
			@Override public Object writeLong(int i, long v)   { Store.writeLong(data, i, v); return data; }
		}, 

		CHAR() {
			private char[] data = new char[8];
		
			@Override public long readBit(int i)               { return Store.readBit(data, i) ? 1 : -1; }
			@Override public Object writeBit(int i, boolean v) { Store.writeBit(data, i, v); return data; }
			@Override public long readByte(int i)              { return Store.readByte(data, i); }
			@Override public Object writeByte(int i, byte v)   { Store.writeByte(data, i, v); return data; }
			@Override public long readChar(int i)              { return Store.readChar(data, i); }
			@Override public Object writeChar(int i, char v)   { Store.writeChar(data, i, v); return data; }
			@Override public long readShort(int i)             { return Store.readChar(data, i); }
			@Override public Object writeShort(int i, short v) { Store.writeShort(data, i, v); return data; }
			@Override public long readInt(int i)               { return Store.readShort(data, i); }
			@Override public Object writeInt(int i, int v)     { Store.writeInt(data, i, v); return data; }
			@Override public long readLong(int i)              { return Store.readLong(data, i); }
			@Override public Object writeLong(int i, long v)   { Store.writeLong(data, i, v); return data; }
		}, 

		SHORT() {
			private short[] data = new short[8];
		
			@Override public long readBit(int i)               { return Store.readBit(data, i) ? 1 : -1; }
			@Override public Object writeBit(int i, boolean v) { Store.writeBit(data, i, v); return data; }
			@Override public long readByte(int i)              { return Store.readByte(data, i); }
			@Override public Object writeByte(int i, byte v)   { Store.writeByte(data, i, v); return data; }
			@Override public long readChar(int i)              { return Store.readChar(data, i); }
			@Override public Object writeChar(int i, char v)   { Store.writeChar(data, i, v); return data; }
			@Override public long readShort(int i)             { return Store.readChar(data, i); }
			@Override public Object writeShort(int i, short v) { Store.writeShort(data, i, v); return data; }
			@Override public long readInt(int i)               { return Store.readShort(data, i); }
			@Override public Object writeInt(int i, int v)     { Store.writeInt(data, i, v); return data; }
			@Override public long readLong(int i)              { return Store.readLong(data, i); }
			@Override public Object writeLong(int i, long v)   { Store.writeLong(data, i, v); return data; }
		}, 

		INT() {
			private int[] data = new int[4];
		
			@Override public long readBit(int i)               { return Store.readBit(data, i) ? 1 : -1; }
			@Override public Object writeBit(int i, boolean v) { Store.writeBit(data, i, v); return data; }
			@Override public long readByte(int i)              { return Store.readByte(data, i); }
			@Override public Object writeByte(int i, byte v)   { Store.writeByte(data, i, v); return data; }
			@Override public long readChar(int i)              { return Store.readChar(data, i); }
			@Override public Object writeChar(int i, char v)   { Store.writeChar(data, i, v); return data; }
			@Override public long readShort(int i)             { return Store.readChar(data, i); }
			@Override public Object writeShort(int i, short v) { Store.writeShort(data, i, v); return data; }
			@Override public long readInt(int i)               { return Store.readShort(data, i); }
			@Override public Object writeInt(int i, int v)     { Store.writeInt(data, i, v); return data; }
			@Override public long readLong(int i)              { return Store.readLong(data, i); }
			@Override public Object writeLong(int i, long v)   { Store.writeLong(data, i, v); return data; }
		}, 

		LONG() {
			private long[] data = new long[2];
		
			@Override public long readBit(int i)               { return Store.readBit(data, i) ? 1 : -1; }
			@Override public Object writeBit(int i, boolean v) { Store.writeBit(data, i, v); return data; }
			@Override public long readByte(int i)              { return Store.readByte(data, i); }
			@Override public Object writeByte(int i, byte v)   { Store.writeByte(data, i, v); return data; }
			@Override public long readChar(int i)              { return Store.readChar(data, i); }
			@Override public Object writeChar(int i, char v)   { Store.writeChar(data, i, v); return data; }
			@Override public long readShort(int i)             { return Store.readChar(data, i); }
			@Override public Object writeShort(int i, short v) { Store.writeShort(data, i, v); return data; }
			@Override public long readInt(int i)               { return Store.readShort(data, i); }
			@Override public Object writeInt(int i, int v)     { Store.writeInt(data, i, v); return data; }
			@Override public long readLong(int i)              { return Store.readLong(data, i); }
			@Override public Object writeLong(int i, long v)   { Store.writeLong(data, i, v); return data; }
		}, 

		;
		
		public abstract long readBit(int i);
		public abstract Object writeBit(int i, boolean v);
		public abstract long readByte(int i);
		public abstract Object writeByte(int i, byte v);
		public abstract long readChar(int i);
		public abstract Object writeChar(int i, char v);
		public abstract long readShort(int i);
		public abstract Object writeShort(int i, short v);
		public abstract long readInt(int i);
		public abstract Object writeInt(int i, int v);
		public abstract long readLong(int i);
		public abstract Object writeLong(int i, long v);
	}
	
	public static enum AccessClass {
		BIT(1) {
			@Override public long read(StorageClass storage, int i) {
				return storage.readBit(i);
			}

			@Override public Object write(StorageClass storage, int i, long v) {
				return storage.writeBit(i, v != 0);
			}
		},
		
		BYTE(8) {
			@Override public long read(StorageClass storage, int i) {
				return storage.readByte(i);
			}

			@Override public Object write(StorageClass storage, int i, long v) {
				return storage.writeByte(i, (byte)v);
			}
		},
		
		CHAR(16) {
			@Override public long read(StorageClass storage, int i) {
				return storage.readChar(i);
			}

			@Override public Object write(StorageClass storage, int i, long v) {
				return storage.writeChar(i, (char)v);
			}
		},
		
		SHORT(16) {
			@Override public long read(StorageClass storage, int i) {
				return storage.readShort(i);
			}

			@Override public Object write(StorageClass storage, int i, long v) {
				return storage.writeShort(i, (short)v);
			}
		},
		
		INT(32) {
			@Override public long read(StorageClass storage, int i) {
				return storage.readInt(i);
			}

			@Override public Object write(StorageClass storage, int i, long v) {
				return storage.writeInt(i, (int)v);
			}
		},
		
		LONG(64) {
			@Override public long read(StorageClass storage, int i) {
				return storage.readLong(i);
			}

			@Override public Object write(StorageClass storage, int i, long v) {
				return storage.writeLong(i, v);
			}
		},
		;

		public int size;

		private AccessClass(int size) {
			this.size = size;
		}
		
		public abstract long read(StorageClass storage, int i);
		public abstract Object write(StorageClass storage, int i, long v);
	}
	
	@Param
	public StorageClass storage;
	
	@Param
	public AccessClass access;
	
	@Param({"1", "2", "4", "8", "16", "32", "64"})
	public int width;
	
	@Param({"0", "1", "128", "-128"})
	public int offset;
	
	private long v = 20L;
	
	@Benchmark
	@Fork(2)
	public Object write() {
		if (width % access.size != 0)
			throw new IllegalArgumentException();
		
		Object result = null;
		
		for (int i = offset; i < offset + width; i += access.size)
			result = access.write(storage, i, v);
		
		return result;
	}
	
	@Benchmark
	@Fork(2)
	public long read() {
		if (width % access.size != 0)
			throw new IllegalArgumentException();
		
		long result = 0;
		
		for (int i = offset; i < offset + width; i += access.size)
			result += access.read(storage, i);
		
		return result;
	}
}
