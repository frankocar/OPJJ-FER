package hr.fer.zemris.java.p07.dretve;

import java.util.concurrent.atomic.AtomicLong;

public class PokretanjeDretve3 {
	
	private static AtomicLong brojac = new AtomicLong(0);
	
	public static void main(String[] args) {
		final int brojUvecavanja = 1_000_000;
		final int BROJ_RADNIKA = 5;
		
		Posao p = new Posao(brojUvecavanja);
		
		
		System.out.printf("Brojac prije uvecavanja je %d. %n", brojac.get());
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
		
		System.out.printf("Brojac nakon uvecavanja je %d. %n", brojac.get());
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
				while (true) {
					long staro = brojac.get();
					long novo = staro + 1;
					if (brojac.compareAndSet(staro, novo)) {
						break;
					}
				}
			}
			
		}
		
	}

}
