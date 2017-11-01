package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * A listener that will initialize required tables in a database and load 
 * options from configuration files. Connection configuration should be 
 * given in a {@code /WEB-INF/dbsettings.properties} file. It uses
 * Polls and PollOptions tables which will be created and filled with poll 
 * data if they don't already exist.
 * 
 * @author Franko Car
 *
 */
@WebListener
public class Initialization implements ServletContextListener {
	
	/**
	 * Path to the configuration file
	 */
	private static final String CONFIG = "/WEB-INF/dbsettings.properties";
	/**
	 * Path to the folder containing poll data
	 */
	private static final String POLLS_PATH = "/WEB-INF/polls/";
	
	/**
	 * SQL command to create a Polls table
	 */
	private static final String CREATE_POLLS_SQL = "CREATE TABLE Polls "
			+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
			+ "title VARCHAR(150) NOT NULL, "
			+ "message CLOB(2048) NOT NULL"
			+ ")";
	
	/**
	 * SQL command to create a PollOptions table
	 */
	private static final String CREATE_POLLOPTIONS_SQL = "CREATE TABLE PollOptions "
			+ "(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
			+ "optionTitle VARCHAR(100) NOT NULL, "
			+ "optionLink VARCHAR(150) NOT NULL, "
			+ "pollID BIGINT, votesCount BIGINT, "
			+ "FOREIGN KEY (pollID) REFERENCES Polls(id)"
			+ ")";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties properties = loadProp(sce.getServletContext().getRealPath(CONFIG), true);
		
		String dbHost = properties.getProperty("host");
		String dbPort = properties.getProperty("port");
		String dbName = properties.getProperty("name");
		String dbUser = properties.getProperty("user");
		String dbPassword = properties.getProperty("password");
		
		if (dbHost == null || dbPort == null || dbName == null || dbUser == null || dbPassword == null) {
			throw new IllegalArgumentException("Invalid configuration file");
		}

		String connectionURL = "jdbc:derby://" + dbHost + ":" + dbPort + "/" + dbName + ";user=" + dbUser + ";password=" + dbPassword + "";
		System.out.println(connectionURL);
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException ex) {
			throw new RuntimeException("Pool initialization error.", ex);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		createTables(cpds);
		inputData(cpds, sce);
		
	}

	/**
	 * A method that will read a file "polls.txt" from POLLS_PATH directory
	 * and add a poll for each of the requested polls.
	 * 
	 * @param cpds ComboPooledDataSource used to connect to the database
	 * @param sce the ServletContextEvent containing the ServletContext
     * that is being used
	 */
	private void inputData(ComboPooledDataSource cpds, ServletContextEvent sce) {
		try {
			Connection connection = cpds.getConnection();
			
			ResultSet data = connection.prepareStatement("SELECT COUNT(*) FROM Polls").executeQuery();
			
			data.next();
			if (!data.getString(1).equals("0")) {
				return;
			}
			
			Path path = Paths.get(sce.getServletContext().getRealPath(POLLS_PATH + "polls.txt"));
			
			if (!Files.exists(path)) {
				System.err.println("Polls.txt does not exist");
			}
			
			for (String poll : Files.readAllLines(path)) {
				addPoll(poll, connection, sce);
			}
			
			
		} catch (SQLException ex) {
			System.err.println("Unable to create tables: " + ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
		} catch (IOException ex) {
			System.err.println("Unable to read poll data: " + ex.getMessage());
			System.exit(1);
		}
		
	}

	/**
	 * Adds a poll and its options to the database
	 * 
	 * @param poll String poll configuration name
	 * @param connection Connection object used
	 * @param sce the ServletContextEvent containing the ServletContext
     * that is being used
	 * @throws SQLException if a database access error occurs; this method is called on a closed PreparedStatement or an argument is supplied to this method
	 * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read
	 */
	private void addPoll(String poll, Connection connection, ServletContextEvent sce) throws SQLException, IOException {
		Properties properties = loadProp(sce.getServletContext().getRealPath(POLLS_PATH + poll + ".properties"), false);
		if (properties == null) return;
		
		String pollName = properties.getProperty("name");
		String pollMsg = properties.getProperty("message");
		String pollData = properties.getProperty("data");
		
		Path pollPath = Paths.get(
				sce.getServletContext().getRealPath(
						POLLS_PATH + pollData)
				);
		
		if (!Files.exists(pollPath)) {
			System.err.println(pollData + " does not exist");
			return;
		}
		
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO Polls (title, message) VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		
		statement.setString(1, pollName);
		statement.setString(2, pollMsg);
		statement.execute();
		
		ResultSet result = statement.getGeneratedKeys();
		if (result == null) return;
		
		result.next();
		long id = result.getLong(1);
		
		loadOptions(pollPath, connection, id);		
		
	}

	/**
	 * Loads all options for a poll from a given file.
	 * 
	 * @param pollPath path to the file containing all options in form "name[\t]exampleLink"
	 * @param connection Connection object used
	 * @param pollID long poll ID of a poll to add options to
	 * @throws SQLException if a database access error occurs; this method is called on a closed PreparedStatement or an argument is supplied to this method
	 * @throws IOException if an I/O error occurs reading from the file or a malformed or unmappable byte sequence is read
	 */
	private void loadOptions(Path pollPath, Connection connection, long pollID) throws SQLException, IOException {
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		
		statement.setLong(3, pollID);
		statement.setString(4, "0");
		
		
		for (String line : Files.readAllLines(pollPath)) {
			String[] tmp = line.split("\t+");
			
			if (tmp.length != 2) {
				System.err.println("Invalid option, skipped: " + line);
				continue;
			}
			
			statement.setString(1, tmp[0]);
			statement.setString(2, tmp[1]);
			
			statement.execute();
		}
		
	}

	/**
	 * Checks if required tables already exist and create them if they don't.
	 * 
	 * @param cpds ComboPooledDataSource used to connect to the database
	 */
	private void createTables(ComboPooledDataSource cpds) {
		try {
			Connection connection = cpds.getConnection();
			
			boolean pollsExists = connection.getMetaData().getTables(null, null, "Polls".toUpperCase(), null).next();
			boolean pollOptionsExists = connection.getMetaData().getTables(null, null, "PollOptions".toUpperCase(), null).next();
			
			if (!pollsExists) {
				connection.prepareStatement(CREATE_POLLS_SQL).execute();
			}
			
			if (!pollOptionsExists) {
				connection.prepareStatement(CREATE_POLLOPTIONS_SQL).execute();
			}
		} catch (SQLException ex) {
			System.err.println("Unable to create tables: " + ex.getMessage());
			System.exit(1);
		}
	}
	
	/**
	 * Loads {@link Properties} from a given file
	 * 
	 * @param configName File of type .properties
	 * @param kill the app will be killed if there is an error
	 * @return Properties object with loaded data
	 */
	private Properties loadProp(String configName, boolean kill) {
		Properties prop = new Properties();
		
		try (InputStream is = new FileInputStream(configName)) {
			prop.load(is);
		} catch (FileNotFoundException ex) {
			System.out.println("File not found: " + ex.getMessage());
			if (kill) {
				System.exit(1);
			}
		} catch (IOException ex) {
			System.out.println("File unreadable: " + ex.getMessage());
			if (kill) {
				System.exit(1);
			}
		}
		
		return prop;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

}