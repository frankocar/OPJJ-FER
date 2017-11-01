package hr.fer.zemris.java.p02;

public class Pravokutnik extends GeometrijskiLik {
	
	private int vrhX;
	private int vrhY;
	private int sirina;
	private int visina;
	
	public Pravokutnik(int vrhX, int vrhY, int sirina, int visina) {
		super("Pravokutnik");
		this.vrhX = vrhX;
		this.vrhY = vrhY;
		this.sirina = sirina;
		this.visina = visina;
	}
	
	@Override
	public double getOpseg() {
		return 2 * (visina + sirina);
	}
	
	@Override
	public double getPovrsina() {
		return visina * sirina;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Pravokutnik)) {
			return false;
		}
		
		Pravokutnik drugi = (Pravokutnik)obj;
		
		return this.vrhX == drugi.vrhX && this.vrhY == drugi.vrhY &&
				this.visina == drugi.visina && this.sirina == drugi.sirina &&
				this.getIme().equals(drugi.getIme());
	}
	
	@Override
	public String toString() {
		return String.format(
				"x=%d, y=%d, w=%d, h=%d",
				vrhX,
				vrhY,
				sirina,
				visina);
	}
}
