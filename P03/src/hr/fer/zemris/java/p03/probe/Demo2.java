package hr.fer.zemris.java.p03.probe;

import java.util.Iterator;

public class Demo2 {

	public static void main(String[] args) {
		SlijedBrojeva sb = new SlijedBrojeva(1, 2, 10);
		
		for(int i = 0, n = sb.getCount(); i < n; i++) {
			System.out.println(sb.getElement(i));
		}
		
		Iterator<Integer> it = sb.iterator();
		while(it.hasNext()){
			Integer elem = it.next();
			System.out.println(elem);
		}
		
		for(Integer elem : sb) {
			System.out.println(elem);
		}
	}
	
}
