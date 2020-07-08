package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import account.*;
import bankATM.*;

public class DBAccount implements CRUDInterface<Account> {

	static Connection conn = DataBaseConnection.getConnection();

	String tableName = "Accounts";
	String columns = " id, client_id, status, type, balance, created ";

	/*
	 * Account(String id, Client client, boolean status, Money balance, Date
	 * created)
	 */
	public static void main(String[] args) throws SQLException {
		Money amount = new Money(120, Currency.USD);
		System.out.println(amount);
		Money amount1 = new Money(amount.getValue() * (float) 1.1,  Currency.USD);
		System.out.println(amount1);
		
		String id = UUID.randomUUID().toString();

		Date date = new Date(2001, 12, 1);

		DBPerson testObjPerson = new DBPerson();
		id = "55878b5b-b306-4023-a4df-62f3fe6fe42b";
		Person testPerson = testObjPerson.retrieveById(id);

		id = "c7577a78-ec82-4e04-85c6-468f029617e6";
		Client testClient = new Client(id, testPerson, date, "testEmail", "testPassword");

		DBAccount testObj = new DBAccount();
		id = "6bf61a1e-0697-4b08-a0ff-86d6cb3d70b9";
		Account testAcc = new CheckingAccount(id, testClient, Status.Open, new Money(120, Currency.USD), date);

		id = "6bf61a1e-0697-4b08-a0ff-86d6cb3d70b2";
		Account testAcc2 = new SavingsAccount(id, testClient, Status.Open, new Money(120, Currency.USD), date);

		ArrayList<Account> accounts = testObj.retrieveClientAccounts(testClient);
		System.out.println("A ccount" + accounts);
//		for (Account a: accounts) {
//			System.out.println("A ccount" + accounts.size());
//		}
		
		
		id ="6bf61a1e-0697-4b08-a0ff-86d6cb2d70dr";
		Account aa = testObj.retrieveById(id);
		System.out.println(" "+ aa.getClient() + " <<<<<<");
//		testObj.create(testAcc);
//		testObj.delete(testAcc);

	}

	/*
	 * Insert Account into database table Accounts.
	 */
	@Override
	public void create(Account account) throws SQLException {
		String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setString(1, account.getId());
		statement.setString(2, account.getClient().getId());
		statement.setString(3, account.getStatus().str);
		statement.setString(4, account.getType().str);
		statement.setFloat(5, account.getBalance().getValue());
		statement.setDate(6, account.getCreated());

		int rowsInserted = statement.executeUpdate();

		if (rowsInserted > 0) {
			System.out.println("A new Client was created successfully!");
		}

	}

	@Override
	public Account retrieve(Account account) throws SQLException {
		if (account != null) {
			return retrieveById(account.getId());
		}
		return null;
	}

	/*
	 * Returns this Client's All accounts from DB
	 */
	public ArrayList<Account> retrieveClientAccounts(Client client) throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		String client_id = client.getId();
		if (client != null) {

			Account account = null;
			PreparedStatement statement = conn.prepareStatement(
					"SELECT " + columns + " FROM " + tableName + " WHERE client_id = '" + client_id + "';");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				account = retrieveById(id);
				accounts.add(account);
			}

		}
		return accounts;
	}

	/*
	 * Returns this All Accounts from DB
	 */
	public ArrayList<Account> retrieveAccounts() throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();

		Account account = null;
		PreparedStatement statement = conn.prepareStatement("SELECT " + columns + " FROM " + tableName + ";");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			String id = resultSet.getString("id");
			account = retrieveById(id);
			accounts.add(account);
		}
		return accounts;
	}

	@Override
	public Account retrieveById(String id) throws SQLException {
		DBClient dbClientObj = new DBClient();

		Account account = null;
		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE id = '" + id + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("id").equals(id)) {
				
				System.out.println(" >> id account :"+ id);
				
				Client client = dbClientObj.retrieveById(resultSet.getString("client_id"));
				
				
				System.out.println(" >> dbClientObj client account? :"+ client);
				String type = resultSet.getString("type");
				Status status = null;
				String statusStr = resultSet.getString("status");
				if (Status.Open.equals(statusStr)) {
					status = Status.Open;
				} else {
					status = Status.Closed;
				}
				Money balance = new Money(resultSet.getFloat("balance"), Currency.USD);
				Date created = resultSet.getDate("created");

				if (type.equals("Checking Account")) {
					account = new CheckingAccount(id, client, status, balance, created);
				} else if (type.equals("Savings Account")) {
					account = new SavingsAccount(id, client, status, balance, created);
				} else if (type.equals("Loans Account")) {
					account = new LoansAccount(id, client, status, balance, created);
				} else if (type.equals("Security Account")) {
					account = new SecurityAccount(id, client, status, balance, created);
				} else if (type.equals("Deposit Account")) {
					account = new DepositAccount(id, client, status, balance, created);
				}
			}
		}
		if (account == null) {
			System.out.println("No account with id: " + id);
		} else {
			System.out.println("Account fetched successfully! account: " + account);
		}
		return account;
	}

	@Override
	public void delete(Account account) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE id = '" + account.getId() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Account: " + account.getId() + " deleted successfully from db");
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
			System.out.println("Account: " + id + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Account t) throws SQLException {
		delete(t);
		System.out.println("Account: " + t.getInfo());
		create(t);
		System.out.println("NEW Account: " + t.getInfo());
	}

	@Override
	public void updateById(String id) throws SQLException {
		update(retrieveById(id));

	}
}