package hr.fer.zemirs.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * A program to calculate sha256 file digest or to encrypt or decrypt files. It requires 2 or 3 arguments.
 * First argument has to be the desired operation, 'checksha', 'encrypt' or 'decrypt'.
 * 'checksha' needs to be followed by a single argument, a path of a file to digest.
 * 'encrypt' and 'decrypt' require two arguments, first one being the input file, and the second one the desired output location.
 * 
 * @author Franko Car
 *
 */
public class Crypto {

	/**
	 * Main method
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 2 && args.length != 3) {
			System.out.println("Expected 2 or 3 arguments");
			System.exit(1);
		}
		
		switch (args[0]) {
		case "checksha":
			if (args.length != 2) {
				System.out.println("'checksha' requires 1 argument, file path");
				System.exit(1);
			}
			checksha(args[1]);
			break;
		case "encrypt":
			if (args.length != 3) {
				System.out.println("'encrypt' requires 2 argument, input and output file path");
				System.exit(1);
			}
			encrypt(true, args[1], args[2]);
			return;
		case "decrypt" :
			if (args.length != 3) {
				System.out.println("'decrypt' requires 2 argument, input and output file path");
				System.exit(1);
			}
			encrypt(false, args[1], args[2]);
			return;
		default:
			System.out.println("Unknown operation, please use checksha, encrypt or decrypt as an operation");
			break;
		}
	}

	/**
	 * A method to encrypt or decrypt files, according to the encrypt argument.
	 * It will take in a file, ask user for password and initialization vector and then execute the operation.
	 * 
	 * @param encrypt Boolean value to determine the operation, true to encrypt, false to decrypt
	 * @param input Path to the input file
	 * @param output Path to the output file
	 */
	private static void encrypt(boolean encrypt, String input, String output) {
		Cipher cipher = initCipher(encrypt);
		
		Path inputPath = Paths.get(input);
		Path outputPath = Paths.get(output);
		try (
			InputStream is = new BufferedInputStream(Files.newInputStream(inputPath, StandardOpenOption.READ));
			OutputStream os = new BufferedOutputStream(Files.newOutputStream(outputPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE));
		) {
			byte[] buffer = new byte[4096];
			byte[] encode = null;
			int numRead = 0;
			
			while ((numRead = is.read(buffer, 0, buffer.length)) != -1) {
				encode = cipher.update(buffer, 0, numRead);
				os.write(encode, 0, encode.length);
			}
			
			encode = cipher.doFinal();
			os.write(encode, 0, encode.length);
			
		} catch(IllegalBlockSizeException | BadPaddingException ex) {
			System.out.println("Fatal error, encryption/decryption failed: " + ex.getMessage());
			System.exit(1);
		}catch (FileNotFoundException ex) {
			System.out.println("Fatal error, file couldn't be opened: " + ex.getMessage());
			System.exit(1);
		} catch (IOException ex) {
			System.out.println("Fatal error, input/output error: " + ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		}
		
		if (encrypt) {
			System.out.printf("Encryption completed. Generated file %s based on file %s.%n", output, input);
		} else {
			System.out.printf("Decryption completed. Generated file %s based on file %s.%n", output, input);
		}
		
	}

	/**
	 * A method to initialize a Cipher object. Will terminate the program if something fails.
	 * 
	 * @param encrypt Boolean value to determine the operation, true to encrypt, false to decrypt
	 * @return new Cipher object
	 */
	private static Cipher initCipher(boolean encrypt) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		System.out.print("> ");
		String keyText = sc.nextLine().trim();
		
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		String ivText = sc.nextLine().trim();
		sc.close();
		
		
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
		
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (NoSuchAlgorithmException ex) {
			System.out.println("Fatal error, algorithm not found: " + ex.getMessage());
			System.exit(1);
		} catch (NoSuchPaddingException ex) {
			System.out.println("Fatal error, padding not found: " + ex.getMessage());
			System.exit(1);
		} catch (InvalidAlgorithmParameterException ex) {
			System.out.println("Fatal error, invalid algorithm parameter: " + ex.getMessage());
			System.exit(1);
		} catch (InvalidKeyException ex) {
			System.out.println("Fatal error, invalid key: " + ex.getMessage());
			System.exit(1);
		}
		
		return cipher;
	}

	/**
	 * A method that will ask the user for an expected SHA digest of a given file, calculate it,
	 * and then compare them to check if a file is valid.
	 * 
	 * @param path Path to the file to check
	 */
	private static void checksha(String path) {		
		Scanner sc = new Scanner(System.in);
		System.out.printf("Please provide expected sha-256 digest for %s:%n", path);
		System.out.print("> ");
		String expected = sc.nextLine();
				
	    byte[] hash = null;
	    try (
	    		InputStream is = new BufferedInputStream(new FileInputStream(path));
	    ){
	    	byte[] buffer= new byte[8192];
	    	int count;
		    MessageDigest digest = MessageDigest.getInstance("SHA-256");
		    while ((count = is.read(buffer)) > 0) {
		        digest.update(buffer, 0, count);
		    }
		    hash = digest.digest();
	    } catch (IOException e) {
	    	System.out.println("Input file not found: " + e.getMessage());
	    } catch (NoSuchAlgorithmException e) {
	    	System.out.println("Fatal error, algorithm not found: " + e.getMessage());
	    }
	    
	    String digest = Util.bytetohex(hash);
	    
	    if (digest.equals(expected.toLowerCase())) {
	    	System.out.printf("Digesting completed. Digest of %s matches expected digest.", path);
	    } else {
	    	System.out.printf("Digesting completed. Digest of %s does not match the expected digest. Digest was: %s", path, digest);
	    }
	    
	    sc.close();		
	}
	
}
