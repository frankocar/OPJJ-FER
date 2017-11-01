package hr.fer.zemris.java.p06.datoteke;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TreciBolji {

	private static class SubTreeStats implements Obrada{
		private Map<String, Integer> extensions = new HashMap<>();
		private long totalSize;
		private int dirCount;
		
		@Override
		public void ulazimUDirektorij(File dir) {
			dirCount++;			
		}
		
		@Override
		public void izlazimIzDirektorija(File dir) {	
		}
		
		@Override
		public void imamDatoteku(File f) {
			String ext = odrediEkstenziju(f.getName().toLowerCase());
			if (!ext.isEmpty()) {
				extensions.merge(ext, 1, (k, v) -> v = v + 1);
			}
			
			totalSize += f.length();
		}
		
		private static String odrediEkstenziju(String ime) {
			int pozicija = ime.lastIndexOf('.');
			if (pozicija < 1 || pozicija == ime.length() - 1) return "";
			
			return ime.substring(pozicija + 1);
			
		}
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
		
		SubTreeStats stats = new SubTreeStats();
		FileUtil.obradiPodstablo(subtree, stats);
		
		System.out.println("Broj direktorija = " + stats.dirCount);
		System.out.println("Veličina datoteka = " + stats.totalSize);
		
		
	}
	
}
