package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

//kod s ferka/predavanja kao što je zadano u uputi, pa je javadoc onakav kakav je profesor napisao, bez izmjena s obzirom da nije moj kod
/**
 * Pohrana veza prema bazi podataka u ThreadLocal object. ThreadLocal je zapravo
 * mapa čiji su ključevi identifikator dretve koji radi operaciju nad mapom.
 * 
 * @author marcupic
 *
 */
public class SQLConnectionProvider {

	/**
	 * ThreadLocal objekt koji čuva veze
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Postavi vezu za trenutnu dretvu (ili obriši zapis iz mape ako je argument <code>null</code>).
	 * 
	 * @param con veza prema bazi
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Dohvati vezu koju trenutna dretva (pozivatelj) smije koristiti.
	 * 
	 * @return vezu prema bazi podataka
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}