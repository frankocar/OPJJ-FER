package hr.fer.zemris.java.p06.datoteke;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Drugi {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Očekivao sam jedan argument: stazu.");
			return;
		}
		
//		List<File> paths = new ArrayList<>();
		File subtree = new File(args[0]);
//		System.out.println(loop(subtree.listFiles(), paths));
//		paths.forEach(System.out::println);
		
		List<File> paths = pronadi(subtree);
		paths.forEach(System.out::println);
		
		/*int sum = 0;
		for (File file : paths) {
			List<String> input;		
			try {
				input = Files.readAllLines(Paths.get(file.getPath()));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Unable to open given file");
				return;
			}
			sum += input.size();
		}
		System.out.println(sum);*/
		
	}
	
	private static List<File> pronadi(File root) {
		File[] files = root.listFiles();
		
		if (files == null) {
			return Collections.emptyList();
		}
		
		List<File> list = new ArrayList<>();
		for (File file : files) {
			if (file.isDirectory() /*&& file.getAbsolutePath().toString().contains("hw")*/) {
				list.addAll(pronadi(file));
			}
			
			String ext = odrediEkstenziju(file.getName().toLowerCase());
			if (ext.equals("java")) {
				list.add(file);
			}
		}
		
		return list;
	}
	
//	private static int loop(File[] files, List<File> paths) {
//		if (files == null) {
//			return 0;
//		}
//		
//		int counter = 0;
//		for (File file : files) {
//			if (file.isDirectory()) {
//				counter += loop(file.listFiles(), paths);
//				continue;
//			}
//			
//			String ext = odrediEkstenziju(file.getName().toLowerCase());
//			if (ext.equals("java")) {
//				paths.add(file);
//				counter++;
//			}
//		}
//		
//		return counter;
//	}
	
	private static String odrediEkstenziju(String ime) {
		int pozicija = ime.lastIndexOf('.');
		if (pozicija < 1 || pozicija == ime.length() - 1) return "";
		
		return ime.substring(pozicija + 1);		
	}
	
}
