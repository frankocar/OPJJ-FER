package hr.fer.zemris.java.p06.datoteke;

import java.io.File;

public class Prvi {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Očekivao sam jedan argument: stazu.");
			return;
		}
		
		File subtree = new File(args[0]);
		System.out.println(loop(subtree.listFiles()));
		
	}
	
	private static int loop(File[] files) {
		if (files == null) {
			return 0;
		}
		
		int counter = 0;
		for (File file : files) {
			if (file.isDirectory()) {
				counter += loop(file.listFiles());
				continue;
			}
			
//			if (file.getName().toLowerCase().endsWith(".java")) {
//				counter++;
//			}
			
			String ext = odrediEkstenziju(file.getName().toLowerCase());
			if (ext.equals("java")) {
				counter++;
			}
		}
		
		return counter;
	}
	
	private static String odrediEkstenziju(String ime) {
		int pozicija = ime.lastIndexOf('.');
		if (pozicija < 1 || pozicija == ime.length() - 1) return "";
		
		return ime.substring(pozicija + 1);
		
	}
	
}
