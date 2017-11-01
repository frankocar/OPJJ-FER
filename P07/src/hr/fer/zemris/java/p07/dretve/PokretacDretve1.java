package hr.fer.zemris.java.p07.dretve;

public class PokretacDretve1 {

	public static void main(String[] args) {
		Thread t = new Dretva1();
		
		System.out.println("Idem pokrenuti dretvu");
		t.start();
		System.out.println("Idem čekati da završi");
		
		while (t.isAlive()) {
			try {
				t.join();
			} catch (InterruptedException e) {

			}
		}
		
		System.out.println("Dretva je gotova");

	}

}
