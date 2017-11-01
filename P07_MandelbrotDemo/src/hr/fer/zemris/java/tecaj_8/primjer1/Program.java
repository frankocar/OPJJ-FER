package hr.fer.zemris.java.tecaj_8.primjer1;

public class Program {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int brojProizvodaca = 2;
		final int brojPotrosaca = 3;
		final int brojPoslovaPoProizvodacu = 5;
		final int kapacitetReda = 2;
		
		final MyBlockingQueue queue = new MyBlockingQueue(kapacitetReda);
		for(int i = 0; i < brojPotrosaca; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						Runnable job = queue.takeJob();
						job.run();
					}
				}
			}, "Potrosac_"+i);
			t.start();
		}
		
		
		for(int i = 0; i < brojProizvodaca; i++) {
			final int trenutniProizvodac = i;
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j = 0; j < brojPoslovaPoProizvodacu; j++) {
						Posao p = new Posao(trenutniProizvodac*brojPoslovaPoProizvodacu + j);
						queue.addJob(p);
					}
				}
			}, "Proizvodac_"+i);
			t.start();
		}
		
	}

	private static class Posao implements Runnable {
		private int brojPosla;
		
		public Posao(int brojPosla) {
			super();
			this.brojPosla = brojPosla;
		}

		@Override
		public void run() {
			System.out.println("Radi se posao "+brojPosla+"...");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			System.out.println("Posao "+brojPosla+" je gotov...");
		};

	}
}
