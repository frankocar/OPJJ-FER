package hr.fer.zemris.java.p01;

public class P03 {

	static class MyInteger {
		int value;
	}

	public static void main(String[] args) {
		MyInteger i1 = null;
		i1 = new MyInteger();
		i1.value = 7;
		m(i1);
		int current = i1.value;
		System.out.println(current);		
	}	
	
	private static void m(MyInteger i) {
		i.value = i.value * 2;
	}
}