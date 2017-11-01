package hr.fer.zemris.java.p05.p0;

import java.util.ArrayList;
import java.util.List;

public class Glavni {

	public static void main(String[] args) {
		List<Zaposlenik> kolekcija = new ArrayList<>();
		
		kolekcija.add(new Zaposlenik("1", "Ante", "Antić", 5000.0));
		kolekcija.add(new Zaposlenik("2", "Sanja", "Sanjić", 5000.0));
		kolekcija.add(new Zaposlenik("3", "Janko", "Jankić", 5000.0));
		kolekcija.add(new Zaposlenik("4", "Kristina", "Kristić", 5000.0));
		
		Zaposlenik sanja = new Zaposlenik("2", "Sanja", "Sanjić", 5000.0);
		
		kolekcija.forEach(System.out::println);
		System.out.println(kolekcija.contains(sanja));
	}
	
}
