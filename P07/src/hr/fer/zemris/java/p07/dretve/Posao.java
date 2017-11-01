package hr.fer.zemris.java.p07.dretve;

public class Posao implements Runnable {

	@Override
	public void run() {
		System.out.println("Posao započinje s izvođenjem. Idem ga radit");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		System.out.println("Posao je obavljen.");
		
	}

	
}
