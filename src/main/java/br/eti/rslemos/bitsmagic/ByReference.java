/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2019 Rodrigo Lemos
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

/**
 * Pass a value by reference. An object of one of this class' nested classes
 * allows to pass references to called methods and have changes to their values
 * reflected back at the callee. Since objects of this class are not immutable,
 * both methods {@code equals} and {@code hashCode} compute their results on
 * object references, that is, {@code Object}'s default implementations are not 
 * overridden.
 */
public abstract class ByReference<T> {
	/* hashCode pinned to Object's implementation */
	@Override public final int hashCode() { return super.hashCode(); }

	/* equals pinned to Object's implementation */
	@Override public final boolean equals(Object o) { return super.equals(o); }

	@Override public final String toString() {
		return String.format("0x%x(%s)", hashCode(), getValue());
	}

	public abstract T getValue();

	public abstract void setValue(T value);

	/**
	 * Boolean to pass by reference. An object of this class allows to pass 
	 * booleans to called methods and have changes to their values reflected 
	 * back at the callee. Since objects of this class are not immutable, both 
	 * methods {@code equals} and {@code hashCode} compute their results on 
	 * object references, that is, {@code Object}'s default implementations are 
	 * not overridden.
	 */
	public static final class BooleanRef extends ByReference<Boolean> {
		/**
		 * Boolean value held by this reference.
		 */
		public boolean b;

		/**
		 * Create a new reference with 0 as initial content.
		 */
		public BooleanRef() { };

		/**
		 * Create a new reference with {@code b} as initial content.
		 */
		public BooleanRef(boolean b) { this.b = b; }

		@Override public Boolean getValue() { return b; }

		@Override public void setValue(Boolean value) { b = value; }
	}

	/**
	 * Create a new reference with {@code b} as initial content. Syntatic sugar 
	 * to {@code new BooleanRef(b)}.
	 */
	public static BooleanRef byRef(boolean b) { return new BooleanRef(b); }


	/**
	 * Byte to pass by reference. An object of this class allows to pass bytes 
	 * to called methods and have changes to their values reflected back at the 
	 * callee. Since objects of this class are not immutable, both methods 
	 * {@code equals} and {@code hashCode} compute their results on object 
	 * references, that is, {@code Object}'s default implementations are not 
	 * overridden.
	 */
	public static final class ByteRef extends ByReference<Byte> {
		/**
		 * Byte value held by this reference.
		 */
		public byte b;

		/**
		 * Create a new reference with 0 as initial content.
		 */
		public ByteRef() { };

		/**
		 * Create a new reference with {@code b} as initial content.
		 */
		public ByteRef(byte b) { this.b = b; }

		@Override public Byte getValue() { return b; }

		@Override public void setValue(Byte value) { b = value; }
	}

	/**
	 * Create a new reference with {@code b} as initial content. Syntatic sugar 
	 * to {@code new ByteRef(b)}.
	 */
	public static ByteRef byRef(byte b) { return new ByteRef(b); }

	/**
	 * Character to pass by reference. An object of this class allows to pass 
	 * characters to called methods and have changes to their values reflected 
	 * back at the callee. Since objects of this class are not immutable, both 
	 * methods {@code equals} and {@code hashCode} compute their results on 
	 * object references, that is, {@code Object}'s default implementations are 
	 * not overridden.
	 */
	public static final class CharacterRef extends ByReference<Character> {
		/**
		 * Character value held by this reference.
		 */
		public char c;

		/**
		 * Create a new reference with 0 as initial content.
		 */
		public CharacterRef() { };

		/**
		 * Create a new reference with {@code c} as initial content.
		 */
		public CharacterRef(char c) { this.c = c; }

		@Override public Character getValue() { return c; }

		@Override public void setValue(Character value) { c = value; }
	}

	/**
	 * Create a new reference with {@code c} as initial content. Syntatic sugar 
	 * to {@code new CharacterRef(c)}.
	 */
	public static CharacterRef byRef(char c) { return new CharacterRef(c); }

	/**
	 * Short to pass by reference. An object of this class allows to pass shorts 
	 * to called methods and have changes to their values reflected back at the 
	 * callee. Since objects of this class are not immutable, both methods 
	 * {@code equals} and {@code hashCode} compute their results on object 
	 * references, that is, {@code Object}'s default implementations are not 
	 * overridden.
	 */
	public static final class ShortRef extends ByReference<Short> {
		/**
		 * Short value held by this reference.
		 */
		public short s;

		/**
		 * Create a new reference with 0 as initial content.
		 */
		public ShortRef() { };

		/**
		 * Create a new reference with {@code s} as initial content.
		 */
		public ShortRef(short s) { this.s = s; }

		@Override public Short getValue() { return s; }

		@Override public void setValue(Short value) { s = value; }
	}

	/**
	 * Create a new reference with {@code s} as initial content. Syntatic sugar 
	 * to {@code new ShortRef(s)}.
	 */
	public static ShortRef byRef(short s) { return new ShortRef(s); }

	/**
	 * Integer to pass by reference. An object of this class allows to pass 
	 * integers to called methods and have changes to their values reflected 
	 * back at the callee. Since objects of this class are not immutable, both 
	 * methods {@code equals} and {@code hashCode} compute their results on 
	 * object references, that is, {@code Object}'s default implementations are 
	 * not overridden.
	 */
	public static final class IntRef extends ByReference<Integer> {
		/**
		 * Integer value held by this reference.
		 */
		public int i;

		/**
		 * Create a new reference with 0 as initial content.
		 */
		public IntRef() { };

		/**
		 * Create a new reference with {@code i} as initial content.
		 */
		public IntRef(int i) { this.i = i; }

		@Override public Integer getValue() { return i; }

		@Override public void setValue(Integer value) { i = value; }
	}

	/**
	 * Create a new reference with {@code i} as initial content. Syntatic sugar 
	 * to {@code new IntRef(i)}.
	 */
	public static IntRef byRef(int i) { return new IntRef(i); }
	
	/**
	 * Long to pass by reference. An object of this class allows to pass longs 
	 * to called methods and have changes to their values reflected back at the 
	 * callee. Since objects of this class are not immutable, both methods 
	 * {@code equals} and {@code hashCode} compute their results on object 
	 * references, that is, {@code Object}'s default implementations are not 
	 * overridden.
	 */
	public static final class LongRef extends ByReference<Long> {
		/**
		 * Long value held by this reference.
		 */
		public long l;

		/**
		 * Create a new reference with 0L as initial content.
		 */
		public LongRef() { };

		/**
		 * Create a new reference with {@code l} as initial content.
		 */
		public LongRef(long l) { this.l = l; }

		@Override public Long getValue() { return l; }

		@Override public void setValue(Long value) { l = value; }
	}

	/**
	 * Float to pass by reference. An object of this class allows to pass floats 
	 * to called methods and have changes to their values reflected back at the 
	 * callee. Since objects of this class are not immutable, both methods 
	 * {@code equals} and {@code hashCode} compute their results on object 
	 * references, that is, {@code Object}'s default implementations are not 
	 * overridden.
	 */
	public static final class FloatRef extends ByReference<Float> {
		/**
		 * Float value held by this reference.
		 */
		public float f;

		/**
		 * Create a new reference with 0f as initial content.
		 */
		public FloatRef() { };

		/**
		 * Create a new reference with {@code f} as initial content.
		 */
		public FloatRef(float f) { this.f = f; }

		@Override public Float getValue() { return f; }

		@Override public void setValue(Float value) { f = value; }
	}

	/**
	 * Create a new reference with {@code f} as initial content. Syntatic sugar 
	 * to {@code new FloatRef(f)}.
	 */
	public static FloatRef byRef(float f) { return new FloatRef(f); }

	/**
	 * Double to pass by reference. An object of this class allows to pass 
	 * doubles to called methods and have changes to their values reflected back 
	 * at the callee. Since objects of this class are not immutable, both 
	 * methods {@code equals} and {@code hashCode} compute their results on 
	 * object references, that is, {@code Object}'s default implementations are 
	 * not overridden.
	 */
	public static final class DoubleRef extends ByReference<Double> {
		/**
		 * Double value held by this reference.
		 */
		public double d;

		/**
		 * Create a new reference with 0d as initial content.
		 */
		public DoubleRef() { };

		/**
		 * Create a new reference with {@code d} as initial content.
		 */
		public DoubleRef(double d) { this.d = d; }

		@Override public Double getValue() { return d; }

		@Override public void setValue(Double value) { d = value; }
	}

	/**
	 * Create a new reference with {@code d} as initial content. Syntatic sugar 
	 * to {@code new DoubleRef(d)}.
	 */
	public static DoubleRef byRef(double d) { return new DoubleRef(d); }

	/**
	 * Object to pass by reference. An object of this class allows to pass 
	 * objects to called methods and have changes to their values reflected back 
	 * at the callee. Since objects of this class are not immutable, both 
	 * methods {@code equals} and {@code hashCode} compute their results on 
	 * object references, that is, {@code Object}'s default implementations are 
	 * not overridden.
	 */
	public static final class ObjectRef<V> extends ByReference<V> {
		/**
		 * Object value held by this reference.
		 */
		public V o;

		/**
		 * Create a new reference with `null` as initial content.
		 */
		public ObjectRef() { };

		/**
		 * Create a new reference with {@code o} as initial content.
		 */
		public ObjectRef(V o) { this.o = o; }

		@Override public V getValue() { return o; }

		@Override public void setValue(V value) { o = value; }
	}

	/**
	 * Create a new reference with {@code o} as initial content. Syntatic sugar 
	 * to {@code new ObjectRef<>(o)}.
	 */
	public static <T> ObjectRef<T> byRef(T o) { return new ObjectRef<T>(o); }

}
