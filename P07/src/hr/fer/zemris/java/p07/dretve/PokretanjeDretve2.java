package hr.fer.zemris.java.p07.dretve;

public class PokretanjeDretve2 {
	
	private volatile static int brojac = 0;
	
	public static void main(String[] args) {
		final int brojUvecavanja = 1_000_000;
		final int BROJ_RADNIKA = 5;
		
		Posao p = new Posao(brojUvecavanja);
		
		
		System.out.printf("Brojac prije uvecavanja je %d. %n", brojac);
		Thread[] radnici = new Thread[BROJ_RADNIKA];

		for (int i = 0; i < radnici.length; i++) {
			radnici[i] = new Thread(p);
		}
		
		for (Thread radnik : radnici) {
			radnik.start();
		}
		
		for (Thread radnik : radnici) {
			while (radnik.isAlive()){
				try {
					radnik.join();
				} catch (InterruptedException e) {
				}
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
