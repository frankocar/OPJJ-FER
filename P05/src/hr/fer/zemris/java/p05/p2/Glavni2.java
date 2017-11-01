package hr.fer.zemris.java.p05.p2;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Glavni2 {
	
//	static class LazuciUsporednik<T> implements Comparator<T> {
//		private Comparator<T> original;
//
//		public LazuciUsporednik(Comparator<T> original) {
//			super();
//			this.original = original;
//		}
//
//		@Override
//		public int compare(T o1, T o2) {
//			return -original.compare(o1, o2);
//		}		
//	}
	
	static class PrezimeImeKomp implements Comparator<Zaposlenik> {
		@Override
		public int compare(Zaposlenik o1, Zaposlenik o2) {
			int r = o1.getPrezime().compareTo(o2.getPrezime());
			if (r != 0) return r;
			return o1.getIme().compareTo(o2.getIme());
		}		
	}

	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		Comparator<Zaposlenik> usporednik = new Comparator<Zaposlenik>() {
			@Override
			public int compare(Zaposlenik o1, Zaposlenik o2) {
				return o1.getPrezime().compareTo(o2.getPrezime());
			}
		};
		
//		Set<Zaposlenik> kolekcija = new TreeSet<>(new LazuciUsporednik<>(usporednik));
//		Set<Zaposlenik> kolekcija = new TreeSet<>(Collections.reverseOrder(usporednik));
//		Set<Zaposlenik> kolekcija = new TreeSet<>(usporednik.reversed());
		Set<Zaposlenik> kolekcija = new TreeSet<>(new PrezimeImeKomp());

		kolekcija.add(new Zaposlenik("1", "Ante", "Antić", 5000.0));
		kolekcija.add(new Zaposlenik("2", "Sanja", "Sanjić", 5000.0));
		kolekcija.add(new Zaposlenik("3", "Janko", "Jankić", 5000.0));
		kolekcija.add(new Zaposlenik("4", "Kristina", "Kristić", 5000.0));
		kolekcija.add(new Zaposlenik("5", "Ana", "Jankić", 5000.0));
		
		Zaposlenik sanja = new Zaposlenik("2", "Sanja", "Sanjić", 5000.0);
		
		kolekcija.forEach(System.out::println);
		System.out.println(kolekcija.contains(sanja));
	}
	
}
