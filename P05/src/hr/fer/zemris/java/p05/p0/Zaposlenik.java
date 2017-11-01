package hr.fer.zemris.java.p05.p0;

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
	
}
