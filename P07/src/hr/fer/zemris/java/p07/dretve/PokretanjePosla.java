package hr.fer.zemris.java.p07.dretve;

public class PokretanjePosla {

	public static void main(String[] args) {
		Posao p = new Posao();
		
		Thread radnik = new Thread(p);
		
		radnik.start();
		
		try {
			radnik.join();
		} catch (InterruptedException e) {
		}
		
		System.out.println("Radnik je odradio zadani posao");
		
	}
	
}
