package hr.fer.zemris.java.p02;

public class Primjer2 {

	public static void main(String[] args) {
		GeometrijskiLik[] likovi = new GeometrijskiLik[] {
				new GeometrijskiLik("Lik1"),
				new Pravokutnik(1, 1, 10, 5),
				new Pravokutnik(20, 25, 1, 1)
		};
		
		for(GeometrijskiLik lik : likovi) {
			double opseg = lik.getOpseg();
			double povrsina = lik.getPovrsina();
			
			System.out.printf("Opseg=%f, povrsina=%f%n", opseg, povrsina);
		}
		
		Pravokutnik p1 = (Pravokutnik)likovi[1];
		Pravokutnik p2 = (Pravokutnik)likovi[2];
		
		boolean isti = p1.equals(p2);
		System.out.println(isti);
	}
	
}
