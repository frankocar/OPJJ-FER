package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

//kod s ferka/predavanja kao što je zadano u uputi, pa je javadoc onakav kakav je profesor napisao, bez izmjena s obzirom da nije moj kod
/**
 * Singleton razred koji zna koga treba vratiti kao pružatelja
 * usluge pristupa podsustavu za perzistenciju podataka.
 * Uočite da, iako je odluka ovdje hardkodirana, naziv
 * razreda koji se stvara mogli smo dinamički pročitati iz
 * konfiguracijske datoteke i dinamički učitati -- time bismo
 * implementacije mogli mijenjati bez ikakvog ponovnog kompajliranja
 * koda.
 * 
 * @author marcupic
 *
 */
public class DAOProvider {

	/**
	 * Primjerak DAO objekta
	 */
	private static DAO dao = new SQLDAO();
	
	/**
	 * Dohvat primjerka.
	 * 
	 * @return objekt koji enkapsulira pristup sloju za perzistenciju podataka.
	 */
	public static DAO getDao() {
		return dao;
	}
	
}