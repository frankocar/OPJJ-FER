package hr.fer.zemris.java.p07.dretve;

public class PokretanjeDretve {
	
	private volatile static int brojac = 0;
	
	public static void main(String[] args) {
		final int brojUvecavanja = 1_000_000;
		
		Posao p = new Posao(brojUvecavanja);
		
		
		System.out.printf("Brojac prije uvecavanja je %d. %n", brojac);
		Thread radnik = new Thread(p);
		radnik.start();
		
		while (radnik.isAlive()){
			try {
				radnik.join();
			} catch (InterruptedException e) {
			}
		}
		
		System.out.printf("Brojac nakon uvecavanja je %d. %n", brojac);
	}
	
	private static class Posao implements Runnable {

		private int brojUvecavanja;
		
		public Posao(int brojUvecavanja) {
			super();
			this.brojUvecavanja = brojUvecavanja;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < brojUvecavanja; i++) {
				brojac++;
			}
			
		}
		
	}

}
