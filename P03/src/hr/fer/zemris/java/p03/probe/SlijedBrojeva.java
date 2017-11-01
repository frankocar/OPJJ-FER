package hr.fer.zemris.java.p03.probe;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SlijedBrojeva implements Iterable<Integer> {

	private int n0;
	private int step;
	private int count;
	
	public SlijedBrojeva(int n0, int step, int count) {
		super();
		
		if(step < 1) {
			throw new IllegalArgumentException("Korak mora biti bar 1; was " + step);
		}
		if(count < 1) {
			throw new IllegalArgumentException("Count mora biti bar 1; was " + step);
		}
		
		this.n0 = n0;
		this.step = step;
		this.count = count;
	}
	
	public int getElement(int k) {
		if(k < 0 || k >= count) {
			throw new IllegalArgumentException("Element na poziciji " + k + " ne postoji");
		}
		
		return n0 + k * step;
	}
	
	public int getCount() {
		return count;
	}

	@Override
	public Iterator<Integer> iterator() {
//		return new MojIteratorStaticki(this);
		return new MojIteratorUnutarnji();
	}
	
	@SuppressWarnings("unused")
	private static class MojIteratorStaticki implements Iterator<Integer> {
		private SlijedBrojeva mojSlijed;
		private int trenutniK;
			
		public MojIteratorStaticki(SlijedBrojeva mojSlijed) {
			super();
			this.mojSlijed = mojSlijed;
		}

		@Override
		public boolean hasNext() {
			return trenutniK < mojSlijed.count;
		}

		@Override
		public Integer next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			return mojSlijed.getElement(trenutniK++);
		}
		
	}
	
	private class MojIteratorUnutarnji implements Iterator<Integer> {
//		private SlijedBrojeva SlijedBrojeva.this;
		private int trenutniK;

		@Override
		public boolean hasNext() {
//			return trenutniK < SlijedBrojeva.this.count;
			return trenutniK < count;
		}

		@Override
		public Integer next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
//			return SlijedBrojeva.this.getElement(trenutniK++);
			return getElement(trenutniK++);
		}
		
	}
	
}
