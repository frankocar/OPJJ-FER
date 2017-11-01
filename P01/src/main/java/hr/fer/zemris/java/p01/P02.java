package hr.fer.zemris.java.p01;

/**
 * Program koji redom ispisuje sve zadane argumente.
 *
 * @author Franko Car
 */

public class P02 {

    /**
	 * Metoda od koje krece izvodenje programa.
	 *
	 * @param args argumenti zadani preko naredbenog retka
	 */

	public static void main(String[] args) {
		
		for(int i = 0; i < args.length; i++) {
			System.out.printf("%d. %s%n", i + 1, args[i]);
		}
	}	
}