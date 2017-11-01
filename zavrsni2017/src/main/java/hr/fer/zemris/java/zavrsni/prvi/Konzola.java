package hr.fer.zemris.java.zavrsni.prvi;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

public class Konzola {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.exit(1);
		}
		
		try {
			Files.walkFileTree(Paths.get(args[0]), new Walker());
		} catch (IOException e) {
			System.out.println("Greška");
		}
	}
	
	private static class Walker extends SimpleFileVisitor<Path> {
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			
			if (file.toString().endsWith("-in.txt")) {
				FileParser fp = new FileParser(file.toString());
				List<String> out = fp.getOutputList();
				
				
				FileWriter writer = new FileWriter(file.toString().replace("-in.txt", "-out.txt")); 
				for(String str: out) {
				  writer.write(str);
				  writer.write(String.format("%n"));
				}
				writer.close();				
			}
			
			return FileVisitResult.CONTINUE;
		}
	}
}
