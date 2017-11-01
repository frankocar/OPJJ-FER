package hr.fer.zemris.java.p07.dretve;

public class PokretanjeDretve4 {
	
	private volatile static int brojac = 0;
	
	public static void main(String[] args) {
		final int brojUvecavanja = 1_000_000;
		final int BROJ_RADNIKA = 5;
		
		Object mutex = new Object();
		
		Posao p = new Posao(brojUvecavanja, mutex);
		
		
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
		private Object mutex;
		
		public Posao(int brojUvecavanja, Object mutex) {
			super();
			this.brojUvecavanja = brojUvecavanja;
			this.mutex = mutex;
		}
		
		@Override
		public void run() {
			for (int i = 0; i < brojUvecavanja; i++) {
				//mutex.udiKO()
				synchronized (mutex) {
					brojac++;
				}
				//mutex.izadiKO()
			}
			
		}
		
	}

}
