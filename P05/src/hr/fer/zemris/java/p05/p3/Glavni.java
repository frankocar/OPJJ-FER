package hr.fer.zemris.java.p05.p3;

import java.util.HashMap;
import java.util.Map;

public class Glavni {

	public static void main(String[] args) {
		Map<Zaposlenik, Double> kolekcija = new HashMap<>();
		
		kolekcija.put(new Zaposlenik("1", "Ante", "Antić", 5000.0), 11.0);
		kolekcija.put(new Zaposlenik("2", "Sanja", "Sanjić", 5000.0), 11.0);
		kolekcija.put(new Zaposlenik("3", "Janko", "Jankić", 5000.0), 11.0);
		kolekcija.put(new Zaposlenik("4", "Kristina", "Kristić", 5000.0), 11.0);
		
		Zaposlenik sanja = new Zaposlenik("2", "Sanja", "Sanjić", 5000.0);
		
		kolekcija.forEach((k,v)->System.out.format("%s=%f%n", k, v));
		System.out.println(kolekcija.get(sanja));
	}
	
}
