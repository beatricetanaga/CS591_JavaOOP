package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import account.*;
import bankATM.*;
import manager.*;

public class DBClient implements CRUDInterface<Client> {

	Connection conn = DataBaseConnection.getConnection();

	String tableName = "Clients";
	String columns = " id, email, password, created, person_id ";

	/*
	 * Client(String id, Person person, Date created, String email, String password)
	 */
	public static void main(String[] args) throws SQLException {

		String id = UUID.randomUUID().toString();
		Date date = new Date(2001, 12, 1);

		DBPerson testObjPerson = new DBPerson();
		id = "55878b5b-b306-4023-a4df-62f3fe6fe42b";
		Person testPerson = testObjPerson.retrieveById(id);

		DBClient testObj = new DBClient();
		id = UUID.randomUUID().toString();
		id = "c7577a78-ec82-4e04-85c6-468f029617e6";

		Client testClient = new Client(id, testPerson, date, "testEmail", "testPassword");

		id = "c7577a78-ec82-4e04-85c6-468f02961888";
		testClient = new Client(id, testPerson, date, "anotheremail", "testPassword");

		testObj.retrieveById(id);
		ArrayList<Client> clients = testObj.retrieveClients();
		for (Client client : clients) {
			System.out.println("Client: " + client.getEmail());
		}

//		testObj.create(testClient);
//		testObj.delete(testClient);
	}

	/*
	 * Insert Account into database table Accounts.
	 */
	@Override
	public void create(Client client) throws SQLException {

		String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (?, ?, ?, ?, ?)";

		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setString(1, client.getId());
		statement.setString(2, client.getEmail());
		statement.setString(3, client.getPassword());
		statement.setDate(4, client.getCreated());
		statement.setString(5, client.getPerson().getId());

		int rowsInserted = statement.executeUpdate();

		if (rowsInserted > 0) {
			System.out.println("A new Client was created successfully!");
		}

	}

	@Override
	public Client retrieve(Client client) throws SQLException {
		return retrieveById(client.getId());
	}

	public Client retrieveByEmail(String email) throws SQLException {
		DBPerson dbPersonObj = new DBPerson();

		Client client = null;
		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE email = '" + email + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("email").equals(email)) {
				Person person = dbPersonObj.retrieveById(resultSet.getString("person_id"));
				Date created = resultSet.getDate("created");
				String id = resultSet.getString("id");
				String password = resultSet.getString("password");

				client = new Client(id, person, created, email, password);
			}
		}
		if (client == null) {
			System.out.println("No client with email: " + email);
		} else {
			System.out.println("A client fetched successfully! Client: " + client);
		}
		return client;
	}

	@Override
	public Client retrieveById(String id) throws SQLException {
		DBPerson dbPersonObj = new DBPerson();

		Client client = null;
		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE id = '" + id + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("id").equals(id)) {
				Person person = dbPersonObj.retrieveById(resultSet.getString("person_id"));
				Date created = resultSet.getDate("created");
				String email = resultSet.getString("email");
				String password = resultSet.getString("password");

				client = new Client(id, person, created, email, password);
			}
		}
		if (client == null) {
			System.out.println("No client with id: " + id);
		} else {
			System.out.println("A client fetched successfully! Client: " + client);
		}
		return client;
	}

	/*
	 * Returns this All Clients from DB
	 */
	public ArrayList<Client> retrieveClients() throws SQLException {
		ArrayList<Client> clients = new ArrayList<Client>();

		Client client = null;
		PreparedStatement statement = conn.prepareStatement("SELECT " + columns + " FROM " + tableName + ";");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			String id = resultSet.getString("id");
			client = retrieveById(id);
			clients.add(client);
		}

		return clients;
	}

	@Override
	public void delete(Client client) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE id = '" + client.getId() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Client: " + client.getId() + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(String id) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE id = '" + id + "';";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Client: " + id + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Client client) throws SQLException {

		delete(client);
		create(client);
	}

	@Override
	public void updateById(String id) throws SQLException {
		update(retrieveById(id));
	}
}