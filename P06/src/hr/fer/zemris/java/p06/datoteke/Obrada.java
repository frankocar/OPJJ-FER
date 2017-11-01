package hr.fer.zemris.java.p06.datoteke;

import java.io.File;

public interface Obrada {

	void ulazimUDirektorij(File dir);
	
	void izlazimIzDirektorija(File dir);
	
	void imamDatoteku(File f);
	
}
