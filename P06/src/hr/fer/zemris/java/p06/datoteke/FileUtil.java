package hr.fer.zemris.java.p06.datoteke;

import java.io.File;

public class FileUtil {

	public static void obradiPodstablo(File root, Obrada obrada) {
		obrada.ulazimUDirektorij(root);
		
		File[] files = root.listFiles();		
		if (files == null) {
			obrada.izlazimIzDirektorija(root);
			return;
		}
		
		for (File file : files) {
			if (file.isDirectory()) {
				obradiPodstablo(file, obrada);
			} else if(file.isFile()) {
				obrada.imamDatoteku(file);
			}
		}
		
		obrada.izlazimIzDirektorija(root);
	}
	
}
