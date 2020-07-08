package database;

import java.sql.*;
import java.util.UUID;

import bankATM.*;

public class DBPerson implements CRUDInterface<Person> {

	Connection conn = DataBaseConnection.getConnection();

	String tableName = "Person";
	String columns = " id, first_name, last_name, birth_date, phone, city, country ";

	public static void main(String[] args) throws SQLException {
		DBPerson testObj = new DBPerson();
		String id = UUID.randomUUID().toString();
		id = "55878b5b-b306-4023-a4df-62f3fe6fe42b";
		Date date = new Date(2001, 12, 1);
		
		Person testPerson = new Person(id, "testName", "testLast", date, "000-test-phone", "testCity", "testCountry");
		testObj.create(testPerson);
		
//		testObj.delete(testPerson);
	}

	/*
	 * Insert Person into database table Person.
	 */
	@Override
	public void create(Person person) throws SQLException {
		String sql = "INSERT INTO " + tableName +" ("+ columns + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setString(1, person.getId());
		statement.setString(2, person.getFirstName());
		statement.setString(3, person.getLastName());
		statement.setDate(4, person.getBirthDate());
		statement.setString(5, person.getPhone());
		statement.setString(6, person.getCity());
		statement.setString(7, person.getCountry());

		int rowsInserted = statement.executeUpdate();

		if (rowsInserted > 0) {
			System.out.println("A new Person was created successfully!");
		}
	}

	@Override
	public Person retrieve(Person person) throws SQLException {
		if (person != null ) {
			return retrieveById(person.getId());
		}
		return null;
	}

	@Override
	public Person retrieveById(String id) throws SQLException {
		Person person = null;
		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE id = '" + id + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			if (resultSet.getString("id").equals(id)) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				Date birthDate = resultSet.getDate("birth_date");
				String phone = resultSet.getString("phone");
				String city = resultSet.getString("city");
				String country = resultSet.getString("country");

				person = new Person(id, firstName, lastName, birthDate, phone, city, country);
			}
		}
		if (person == null) {
			System.out.println("No person with id: " + id);
		} else {
			System.out.println("A person fetched successfully! Person: " + person);
		}
		return person;
	}

	@Override
	public void delete(Person person) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE id = '" + person.getId() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Person: " + person.getId() + " deleted successfully from db");
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
			System.out.println("Person: " + id + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Person person) throws SQLException {
		delete(person);
		create(person);
	}

	@Override
	public void updateById(String id) throws SQLException {
		update(retrieveById(id));
	}
}
