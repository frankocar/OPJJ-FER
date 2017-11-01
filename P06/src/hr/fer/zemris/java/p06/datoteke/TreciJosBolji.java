package hr.fer.zemris.java.p06.datoteke;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class TreciJosBolji {

	private static class SubTreeStats implements FileVisitor<Path>{
		private Map<String, Integer> extensions = new HashMap<>();
		private long totalSize;
		private int dirCount;
		
		private static String odrediEkstenziju(String ime) {
			int pozicija = ime.lastIndexOf('.');
			if (pozicija < 1 || pozicija == ime.length() - 1) return "";
			
			return ime.substring(pozicija + 1);
			
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}



		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			dirCount++;		
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			String ext = odrediEkstenziju(file.getFileName().toString().toLowerCase());
			if (!ext.isEmpty()) {
				extensions.merge(ext, 1, (k, v) -> v = v + 1);
			}
			
			totalSize += attrs.size();
			
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Očekivao sam jedan argument: stazu.");
			return;
		}
		
		Path subtree = Paths.get(args[0]);
		
		if (!Files.isDirectory(subtree)) {
			return;
		}
		
		SubTreeStats stats = new SubTreeStats();
		Files.walkFileTree(subtree, stats);
		
		System.out.println("Broj direktorija = " + stats.dirCount);
		System.out.println("Veličina datoteka = " + stats.totalSize);
		
		
	}
	
}
