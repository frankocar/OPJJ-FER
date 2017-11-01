package hr.fer.zemris.java.p06.datoteke;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KodneStranice {

	public static void main(String[] args) throws IOException {
		String poruka = "Šiščevapčić šeće šumom i želi Coca-Colu.";
		Charset[] kodneStranice = new Charset[] {
				Charset.forName("windows-1250"),
				StandardCharsets.UTF_8,
				StandardCharsets.UTF_16BE,
				StandardCharsets.UTF_16LE
		};
		
		for (int i = 0; i < kodneStranice.length; i++) {
			zapisi(poruka, kodneStranice[i], "dat"+(i+1)+".txt");
		}
	}

	private static void zapisi(String poruka, Charset charset, String ime) throws IOException {
		byte[] okteti = poruka.getBytes(charset);
		Files.write(Paths.get(ime), okteti);
	}
	
}
