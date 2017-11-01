package hr.fer.zemris.java.p04.kolekcije;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Prosjek {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
			
		double suma = 0;
		int brojac = 0;
		
		List<Double> brojevi = new ArrayList<>();
		
		while (true) {
			if (sc.hasNextDouble()){
				double broj = sc.nextDouble();
				suma += broj;
				brojac++;
				brojevi.add(broj);				
			} else {
				if (sc.next().equals("quit")) {
					break;
				}
				System.out.println("Dragi korisniče, daj se saberi!");
			}
		}
		
		if (brojac == 0) {
			System.out.println("Nema brojeva");
			sc.close();
			return;
		}
		
		Collections.sort(brojevi);
		double prosjek = suma / brojac;
		double prag = prosjek * 1.2;
		
		for (double broj : brojevi) {
			if (broj > prag) {
				System.out.println(broj);
			}
		}
		sc.close();
	}
	
}
