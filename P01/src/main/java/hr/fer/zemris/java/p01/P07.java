package hr.fer.zemris.java.p01;

public class P07 {

	public static void main(String[] args) {
		
	}
	
	public static int broji(String ulaz) {
		char[] znakovi = ulaz.toCharArray();
		int broj = 0;
		for(int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			if(c == 'A'){
				if(i > 0 && znakovi[i-1] == 'B') {
					continue;
				}
				broj++;
			}
		}
		return broj;
	}

}
