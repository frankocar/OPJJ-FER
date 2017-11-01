package hr.fer.zemris.java.p04.kolekcije;

import java.util.HashSet;
import java.util.Set;

public class BezDuplikata {

	public static void main(String[] args) {
		
		Set<String> vecVidjenElement = new HashSet<>();
		for (String elem : args) {
			if (vecVidjenElement.add(elem)) {
				System.out.println(elem);
			}
		}
		
	}
	
}
