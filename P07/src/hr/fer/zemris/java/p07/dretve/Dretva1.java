package hr.fer.zemris.java.p07.dretve;

public class Dretva1 extends Thread{

	@Override
	public void run() {
		System.out.println("Dretva započinje s poslom. Idem spavati.");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ignorable) {
		}
		System.out.println("Dretva je završila s poslom");
	}
	
}
