package hr.fer.zemris.java.p06.datoteke;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Treci {

	private static class SubTreeStats {
		Map<String, Integer> extensions = new HashMap<>();
		long totalSize;
		int dirCount;
	}
	
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Očekivao sam jedan argument: stazu.");
			return;
		}
		
		File subtree = new File(args[0]);
		
		if (!subtree.isDirectory()) {
			return;
		}
		
		SubTreeStats stats = odredi(subtree);
		
		System.out.println("Broj direktorija = " + stats.dirCount);
		System.out.println("Veličina datoteka = " + stats.totalSize);
		
		
	}
	
	private static SubTreeStats odredi(File root) {
		SubTreeStats stats = new SubTreeStats();
		odrediRekurzivno(root, stats);
		return stats;
	}
	
	private static void odrediRekurzivno(File root, SubTreeStats stats) {
		File[] files = root.listFiles();
		
		if (files == null) {
			return;
		}
		
		for (File file : files) {
			if (file.isDirectory()) {
				odrediRekurzivno(file, stats);
				stats.dirCount++;
			} else if (file.isFile()) {						
				String ext = odrediEkstenziju(file.getName().toLowerCase());
				if (!ext.isEmpty()) {
					stats.extensions.merge(ext, 1, (k, v) -> v = v + 1);
				}
				
				stats.totalSize += file.length();
			}
		}
	}
	
	private static String odrediEkstenziju(String ime) {
		int pozicija = ime.lastIndexOf('.');
		if (pozicija < 1 || pozicija == ime.length() - 1) return "";
		
		return ime.substring(pozicija + 1);
		
	}
	
}
