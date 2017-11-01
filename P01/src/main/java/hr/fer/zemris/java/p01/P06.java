package hr.fer.zemris.java.p01;

import java.util.Scanner;

public class P06 {

	public static void main(String[] args) {
		int suma = 0;
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			if(sc.hasNextInt()) {
				int current = sc.nextInt();
				if(current < 0) {
					break;
				}
				suma += current;
			} else {
				String elem = sc.next();
				System.out.format("Argument '%s' nije broj - zanemarit cemo ga.%n",elem);
			}
		}
		System.out.printf("Suma je %d.%n", suma);
		
		sc.close();
	}
}