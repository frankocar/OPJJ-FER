package hr.fer.zemris.java.p03.verzija2;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public abstract class GeometrijskiLik {

	public abstract boolean sadrziTocku(int x, int y);
	
	public void popuniLik(Slika slika){
		for(int y = 0, h = slika.getVisina(), w = slika.getSirina(); y < h; y++) {
			for(int x = 0; x < w; x++) {
				if(sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}
	
}
