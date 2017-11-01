package hr.fer.zemris.java.p03.probe;

public class R {

	static int a;
	int b;
	
	public R(int b) {
		this.b = b;
		R.a++;
	}
	
	public void increment() {
		a++;
		b++;
	}
		
	@Override
	public String toString() {
		return String.format("a=%d, b=%d", a, b);
	}
	
}
