package hr.fer.zemris.java.p01;

public class P05 {

	public static void main(String[] args) {
		int suma = 0;
		
		for(int i = 0; i < args.length; i++) {
			try {
				suma += Integer.parseInt(args[i]);
			} catch(NumberFormatException ex) {
				System.out.printf("%s ne mogu pretvoriti u broj! Zanemarujem. %n", args[i]);
			}
		}
		
		System.out.printf("Suma je %d.%n", suma);
	}
}