package hr.fer.zemris.java.p03.probe;

public class Demo {

	public static void main(String[] args) {
		R r1 = new R(7);
		R r2 = new R(9);
		
		System.out.println(r1);
		System.out.println(r2);
		
		r1.increment();
		r2.increment();
		
		System.out.println(r1);
		System.out.println(r2);	
		
		Integer.valueOf(42);
	}

}
