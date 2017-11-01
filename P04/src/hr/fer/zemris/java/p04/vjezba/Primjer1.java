package hr.fer.zemris.java.p04.vjezba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Primjer1 {
	
	interface Uvjet {
		boolean jeLiPrihvatljiv(int elem);
	}
	
	static class ParanBroj implements Uvjet {
		@Override
		public boolean jeLiPrihvatljiv(int elem) {
			return elem % 2 == 0;
		}
		
	}

	public static void main(String[] args) {
		List<Integer> lista = new ArrayList<>(
			Arrays.asList(10, 13, 25, 37, 42, 53, 64, 71, 87, 93)	
		);
		
//		ispis(lista, broj -> broj % 2 == 0);
		ispis(lista, new ParanBroj());
		
		System.out.println();
		
		ispis(lista, new Uvjet() {
			@Override
			public boolean jeLiPrihvatljiv(int elem) {
				return elem > 25;
			}
		});
		
		System.out.println();
		
		ispis(lista, (int elem) -> { return elem > 25; });
		
		System.out.println();
		
		ispis(lista, (elem) -> { return elem > 25; });
		
		System.out.println();
		
		ispis(lista, elem -> { return elem > 25; });
		
		System.out.println();
		
		ispis(lista, elem -> elem > 25 );
		
		
		
	}

	private static void ispis(List<Integer> lista, Uvjet u) {
		for (int broj : lista) {
			if (u.jeLiPrihvatljiv(broj)) {
				System.out.println(broj);
			}
		}
	}
	
}
