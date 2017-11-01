package hr.fer.zemris.java.p04.pairs;

public class Pair<K1, K2> {

	private K1 first;
	private K2 second;
	
	public Pair(K1 first, K2 second) {
		this.first = first;
		this.second = second;
	}
	
	public K1 getFirst() {
		return first;
	}
	
	public K2 getSecond() {
		return second;
	}
	
}
