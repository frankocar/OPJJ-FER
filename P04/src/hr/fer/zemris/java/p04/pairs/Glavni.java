package hr.fer.zemris.java.p04.pairs;

public class Glavni {

	public static void main(String[] args) throws Exception {
//		Pair<String, Integer> ref1 = new Pair<>("Ankica", 17);
//		Pair<String, Long> ref2 = new Pair<String, Long>("Ivana", 17L);
//		
//		ref1.getFirst();
//		
//		Integer i1 = ref1.getSecond();
//		Long i2 = ref2.getSecond();
//		
//		System.out.println(ref1.getClass().getDeclaredField("first"));
		
		Pair<String, Integer> p1 = new Pair<>("Jasna", 5);
		Pair<String, Integer> p2 = new Pair<>("Ankica", 4);
		
		Integer broj = vratiZaManjeIme(p1, p2);
		System.out.println(broj);
		
		Pair<String, Long> r1 = new Pair<>("Jasna", 5L);
		Pair<String, Long> r2 = new Pair<>("Ankica", 4L);
		
		@SuppressWarnings("unused")
		Long broj2 = vratiZaManjeIme(r1, r2);
		System.out.println(broj);
		
		
	}
	
	private static <V, T extends Comparable<T>> V vratiZaManjeIme(Pair<T, V> p1, Pair<T, V> p2) {
		T ime1 = p1.getFirst();
		T ime2 = p2.getFirst();
		
		if (ime1.compareTo(ime2) <= 0) {
			return p1.getSecond();
		}
		return p2.getSecond();
	}
	
//	private static Integer vratiZaManjeIme(Pair<String, Integer> p1, Pair<String, Integer> p2) {
//		String ime1 = p1.getFirst();
//		String ime2 = p2.getFirst();
//		
//		if (ime1.compareTo(ime2) <= 0) {
//			return p1.getSecond();
//		}
//		return p2.getSecond();
//	}
	
}
