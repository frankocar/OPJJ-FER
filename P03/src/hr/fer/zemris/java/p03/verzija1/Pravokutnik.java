package hr.fer.zemris.java.p03.verzija1;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Pravokutnik extends GeometrijskiLik {

	private int vrhX;
	private int vrhY;
	private int sirina;
	private int visina;
	
	public Pravokutnik(int vrhX, int vrhY, int sirina, int visina) {
		super();
		this.vrhX = vrhX;
		this.vrhY = vrhY;
		this.sirina = sirina;
		this.visina = visina;
	}
	
	@Override
	public boolean sadrziTocku(int x, int y) {
		if(x < vrhX) return false;
		if(x >= vrhX + sirina) 	return false;
		if(y < vrhY) return false;
		if(y >= vrhY + visina) return false;

		return true;
	}
	
	@Override
	public void popuniLik(Slika slika) {
		//TODO: implementirati korektan clipping prije crtanja!
		for(int y = vrhY, yEnd = vrhY + visina; y < yEnd; y++) {
			for(int x = vrhX, xEnd = vrhX + sirina; x < xEnd; x++) {
				slika.upaliTocku(x, y);
			}
		}
	}
	
	
	
}
