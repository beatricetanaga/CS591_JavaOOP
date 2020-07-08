package database;

import java.sql.*;
import java.util.UUID;

import bankATM.Person;

public class DataBaseConnection {
	/** The name of the MySQL account to use (or empty for anonymous) */
	final static String userName = "root";

	/** The password for the MySQL account (or empty for anonymous) */
	final static String password = "password";

	/** The name of the computer running MySQL */
	final static String server = "localhost";

	/** The port of the MySQL server3306(default is 3306) */
	final static int port = 3306;

	/** The name of the database using (this default is installed with MySQL) */
	final static String dbName = "bankDB";

	/** The timezone of the database */
	final static String timezone = "userTimezone=true&serverTimezone=UTC";

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection connection = null;

		String host = "jdbc:mysql://" + server + ":" + port + "/" + dbName + "?" + timezone;

		try {
			connection = DriverManager.getConnection(host, userName, password);
			System.out.println("Connection established.");
		} catch (SQLException e) {
			System.out.println("Connection failed.");
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//			e.printStackTrace();
		}
		return connection;
	}
	 

	public static void main(String[] args) throws SQLException {
		// Testing connection
		getConnection();
		
		DBPerson testObj = new DBPerson();
		String id = UUID.randomUUID().toString();
		Date date = new Date(2001, 12, 1);
		
		Person testPerson = new Person(id, "Bob", "Sponge", date, "000-crasty-phone", "bikini", "bottom");
		testObj.create(testPerson);
		testObj.delete(testPerson);
		
	}

}
