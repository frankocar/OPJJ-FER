package hr.fer.zemris.java.p01;

/**
 * Program koji ilustrira "Hello world" u Javi.
 *
 * @author Marko Čupić
 */

public class P01 {

    /**
	 * Metoda od koje kreće izvođenje programa.
	 *
	 * @param args argumenti zadani preko naredbenog retka
	 */

	public static void main(String[] args) {
		System.out.println("Hello world!");
		
		if(args.length == 1) {
			System.out.println("Hello, " + args[0] + "!");
			System.out.printf("Hello, %s!%n", args[0]);
			String ispis = String.format("Hello, %s!%n", args[0]);
			System.out.print(ispis);
		}
	}	
}
