package database;

import java.sql.*;
import java.util.UUID;

import bankATM.*;
import manager.*;

public class DBManager implements CRUDInterface<Manager> {

	Connection conn = DataBaseConnection.getConnection();

	String tableName = "Managers";
	String columns = " id, person_id, email, password ";

	/*
	 * Client(String id, Person person, Date created, String email, String password)
	 */
	public static void main(String[] args) throws SQLException {
		DBManager testObj = new DBManager();
		DBPerson testObjPers = new DBPerson();
		String id = null;

		id = "55878b5b-b306-4023-a4df-62f3fe6fe42b";
		Person testPerson = testObjPers.retrieveById(id);
		System.out.println("test P " + testPerson.getCity());

		id = UUID.randomUUID().toString();
		id = "d93402af-81ae-42ac-8fbc-7e5b3da170ee";
		Manager testmanager = new Manager(id, testPerson, "testEmail", "testPassword");

//		testObj.create(testmanager);
		testObj.retrieveById(id);
	}

	/*
	 * Insert Account into database table Accounts.
	 */
	@Override
	public void create(Manager manager) throws SQLException {
		String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (?, ?, ?, ?)";

		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setString(1, manager.getId());
		statement.setString(2, manager.getPerson().getId());
		statement.setString(3, manager.getEmail());
		statement.setString(4, manager.getPassword());

		int rowsInserted = statement.executeUpdate();

		if (rowsInserted > 0) {
			System.out.println("A new Manager was created successfully!");
		}

	}

	@Override
	public Manager retrieve(Manager manager) throws SQLException {
		if (manager != null) {
			return retrieveById(manager.getId());
		}
		return null;
	}

	@Override
	public Manager retrieveById(String id) throws SQLException {
		DBPerson dbPersonObj = new DBPerson();

		Manager manager = null;
		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE id = '" + id + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("id").equals(id)) {
				Person person = dbPersonObj.retrieveById(resultSet.getString("person_id"));
				String email = resultSet.getString("email");
				String password = resultSet.getString("password");

				manager = new Manager(id, person, email, password);
			}
		}
		if (manager == null) {
			System.out.println("No manager with id: " + id);
		} else {
			System.out.println("A manager fetched successfully! Client: " + manager);
		}
		return manager;
	}

	public Manager retrieveByEmail(String email) throws SQLException {
		DBPerson dbPersonObj = new DBPerson();

		Manager manager = null;
		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE email = '" + email + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("email").equals(email)) {
				String id = resultSet.getString("id");
				Person person = dbPersonObj.retrieveById(resultSet.getString("person_id"));

				String password = resultSet.getString("password");

				manager = new Manager(id, person, email, password);
			}
		}
		if (manager == null) {
			System.out.println("No manager with id: " + email);
		} else {
			System.out.println("A manager fetched successfully! Client: " + manager);
		}
		return manager;
	}

	@Override
	public void delete(Manager manager) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE id = '" + manager.getId() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Client: " + manager.getId() + " deleted successfully from db");
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
	public void update(Manager manager) throws SQLException {
		delete(manager);
		create(manager);
	}

	@Override
	public void updateById(String id) throws SQLException {
		// TODO Auto-generated method stub

	}

}