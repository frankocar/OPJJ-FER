package hr.fer.zemris.bf.checker;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * An application to check program outputs against given examples
 * 
 * @author Franko Car
 *
 */
public class Checker {

	/**
	 * Main method
	 * 
	 * @param args aren't required
	 */
	public static void main(String[] args) {
		for (int i = 1; i <= 3; i++) {
			check(i);
			System.out.println();
		}

	}

	/**
	 * A method to check files. Takes in a test number, opens files named izrazi[number].txt
	 * and izrazi[number].out in resources folder and compares them. Ignores empty lines in the end.
	 * 
	 * @param test
	 */
	private static void check(int test) {
		System.out.printf("Test %d output:%n", test);
		List<String> lines1 = null;
		List<String> lines2 = null;
		
		try {
			lines1 = Files.readAllLines(
				Paths.get(String.format("./src/main/resources/izrazi%d.txt", test)),  //file to compare against
				StandardCharsets.UTF_8
			);
			
			lines2 = Files.readAllLines(
					Paths.get(String.format("./src/main/resources/izrazi%d.out", test)),   //program output
					StandardCharsets.UTF_8
			);
		} catch (IOException e) {
			throw new IllegalArgumentException("Couldn't read input file.", e);
		}
		
		boolean correct = true;
		boolean mismatch = false;
		
		int n = Math.max(lines1.size(), lines2.size());		
		for (int i = 0; i < n; i++) {
			
			if (i >= lines1.size() || i >= lines2.size()) {	
				
				if ((i >= lines1.size() && lines2.get(i).matches(" *")) 
						|| (i >= lines2.size() && lines1.get(i).matches(" *"))) { //we can ignore an empty line at the end
					continue;
				} 
				
				mismatch = true;
				correct = false;
				continue;
			}
			
			if (!lines1.get(i).equals(lines2.get(i))) {
				System.out.printf("  DIFFERENT LINE: \"%s\" != \"%s\"%n", lines1.get(i), lines2.get(i));
				correct = false;
			}
			
		}
		
		if (mismatch) System.out.println("  FILE SIZE MISMATCH");
		if (correct) System.out.println("  PASS");
	}
}
