package hr.fer.zemris.java.p05.vjezba;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;

public class Primjer1 {
		
	static class ParanBroj implements IntPredicate {
		@Override
		public boolean test(int elem) {
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
		
		ispis(lista, new IntPredicate() {
			@Override
			public boolean test(int elem) {
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
		
		for (int i = 0; i < 10; i++) {
			int granica = i;
			ispis(lista, elem -> elem > granica );
		}
		
//		System.out.println();
//		
//		ispis2(lista, elem -> elem > 25, elem -> System.out.println(elem));
		
		System.out.println();
		
		ispis2(lista, elem -> elem > 25, System.out::println, e -> e*e);
		
		
		
	}

	private static void ispis(List<Integer> lista, IntPredicate u) {
		for (int broj : lista) {
			if (u.test(broj)) {
				System.out.println(broj);
			}
		}
	}
	
	private static void ispis2(List<Integer> lista, IntPredicate uvjet, IntConsumer obrada, IntUnaryOperator transformacija) {
		for (int broj : lista) {
			if (uvjet.test(broj)) {
				obrada.accept(transformacija.applyAsInt(broj));
			}
		}
	}
	
}
