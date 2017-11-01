package hr.fer.zemris.java.p03.verzija1;

import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Demo {

	public static void main(String[] args) {
		GeometrijskiLik[] likovi = new GeometrijskiLik[] {
				new Pravokutnik(0, 0, 11, 1),
				new Pravokutnik(12, 13, 5, 4)
		};
		
		Slika slika = new Slika(20, 20);
		
		for(GeometrijskiLik lik : likovi) {
			lik.popuniLik(slika);
		}
		
		slika.nacrtajSliku(System.out);
		
		PrikaznikSlike.prikaziSliku(slika, 50);

	}

}
