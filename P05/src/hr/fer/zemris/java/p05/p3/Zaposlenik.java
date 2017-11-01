package hr.fer.zemris.java.p05.p3;

public class Zaposlenik {

	private String ime;
	private String prezime;
	private String sifra;
	private double placa;
	
	public Zaposlenik(String sifra, String ime, String prezime, double placa) {
		super();
		this.sifra = sifra;
		this.ime = ime;
		this.prezime = prezime;
		this.placa = placa;
	}
	
	@Override
	public String toString() {
		return String.format("[%s] %s %s (%f)", sifra, ime, prezime, placa);
	}

	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getSifra() {
		return sifra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sifra == null) ? 0 : sifra.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Zaposlenik))
			return false;
		Zaposlenik other = (Zaposlenik) obj;
		if (sifra == null) {
			if (other.sifra != null)
				return false;
		} else if (!sifra.equals(other.sifra))
			return false;
		return true;
	}

	
	
//	@Override
//	public int hashCode() {
//		return this.sifra.hashCode();
//	}
//	
//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof Zaposlenik)) return false;		
//		Zaposlenik drugi = (Zaposlenik)obj;
//		
//		return this.sifra.equals(drugi.sifra);
// 	}
	
	
	
}
