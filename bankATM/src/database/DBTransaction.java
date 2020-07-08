package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import account.Account;
import account.CheckingAccount;
import bankATM.*;
import transaction.*;

public class DBTransaction implements CRUDInterface<Transaction> {

	Connection conn = DataBaseConnection.getConnection();

	String tableName = "Transactions";
	String columns = " id, account_id, client_id, amount, created, status, type, destination_account_id ";

	public static void main(String[] args) throws SQLException {

		String id = UUID.randomUUID().toString();
		Date date = new Date(2001, 12, 1);

		DBAccount testObjAcc = new DBAccount();
		id = "6bf61a1e-0697-4b08-a0ff-86d6cb3d70b9";
		Account testAcc = testObjAcc.retrieveById(id);

		id = UUID.randomUUID().toString();
		id = "df215b5d-066b-462b-91e4-462ee2395980";
		DBTransaction testObj = new DBTransaction();
//		Transaction testTransaction = new Withdraw(id, testAcc, new Money(121, Currency.USD), date, Status.Pending);

		// testObj.create(testTransaction);

		id = UUID.randomUUID().toString();
		Transaction testTransaction2 = new Transfer(id, testAcc, new Money(-12121, Currency.USD), date, Status.Pending,
				testAcc);

		id = "c7577a78-ec82-4e04-85c6-468f029617e6";
		id = "c7577a78-ec82-4e04-85c6-468f02961800";
		DBClient dc = new DBClient();
		Client client = dc.retrieveById(id);
		ArrayList<Transaction> ts = testObj.retrieveTransactions(client);
		System.out.println(" TSSS = "+ts.size());
//		testObj.create(testTransaction2);
		testObj.retrieveById(id);

	}

	/*
	 * Insert Transaction into database table Transactions.
	 */
	@Override
	public void create(Transaction transaction) throws SQLException {
		String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = conn.prepareStatement(sql);
		
		statement.setString(1, transaction.getId());
		statement.setString(2, transaction.getAccount().getId());
		statement.setString(3, transaction.getAccount().getClient().getId());
		statement.setFloat(4, transaction.getAmount().getValue());
		statement.setDate(5, transaction.getCreated());
		statement.setString(6, transaction.getStatus().str);
		System.out.println("A new creating + "+transaction.getStatus().str );
		System.out.println("A new creating + "+transaction.getType().str );
		statement.setString(7, transaction.getType().str);
		
		System.out.println("A new creating + "+transaction.getType().str );
		if (transaction instanceof Transfer) {
			statement.setString(8, ((Transfer) transaction).getDestination().getId());
		} else {
			statement.setString(8, "N/A");
		}

		int rowsInserted = statement.executeUpdate();

		if (rowsInserted > 0) {
			System.out.println("A new Transaction was created successfully!");
		}
	}

	@Override
	public Transaction retrieve(Transaction transaction) throws SQLException {
		if (transaction != null) {
			return retrieveById(transaction.getId());
		}
		return null;
	}

	@Override
	public Transaction retrieveById(String id) throws SQLException {
		DBAccount dbAccObj = new DBAccount();
		Transaction transaction = null;
		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE id = '" + id + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("id").equals(id)) {
				Account account = dbAccObj.retrieveById(resultSet.getString("account_id"));
				
				System.out.println(" >> dbAccObj Null account? :"+ account);
				if (account == null ) {
					break;
				}
				Money amount = new Money(resultSet.getFloat("amount"), Currency.USD);
				Date created = resultSet.getDate("created");
				String statusStr = resultSet.getString("status");
				String type = resultSet.getString("type");

				Status status = null;

				if (Status.Completed.equals(statusStr)) {
					status = Status.Completed;
				} else if (Status.Pending.equals(statusStr)) {
					status = Status.Pending;
				}

				
				if (Type.Withdraw.equals(type)) {
					transaction = new Withdraw(id, account, amount, created, status);
				} else if (Type.Transfer.equals(type)) {
					Account destination = dbAccObj.retrieveById(resultSet.getString("destination_account_id"));
					transaction = new Transfer(id, account, amount, created, status, destination);
				} else if (Type.Deposit.equals(type)) {
					transaction = new Deposit(id, account, amount, created, status);
				} else if (Type.LoanPayment.equals(type)) {
					transaction = new LoanPayment(id, account, amount, created, status);
				}
			}
		}
		if (transaction == null) {
			System.out.println("No transaction with id: " + id);
		} else {
			System.out.println("A transaction fetched successfully! Transaction: " + transaction);
		}
		return transaction;
	}

	/*
	 * Returns this All transactions from DB
	 */
	public ArrayList<Transaction> retrieveTransactions() throws SQLException {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Transaction transaction = null;
		PreparedStatement statement = conn.prepareStatement(
				"SELECT " + columns + " FROM " + tableName + " ;");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			String id = resultSet.getString("id");
			transaction = retrieveById(id);
			transactions.add(transaction);
		}

		return transactions;
	}

	/*
	 * Returns this Client's All transactions from DB
	 */
	public ArrayList<Transaction> retrieveTransactions(Client client) throws SQLException {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		String client_id = client.getId();
		if (client != null) {

			Transaction transaction = null;
			PreparedStatement statement = conn.prepareStatement(
					"SELECT " + columns + " FROM " + tableName + " WHERE client_id = '" + client_id + "';");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				transaction = retrieveById(id);
				transactions.add(transaction);
			}

		}
		return transactions;
	}

	/*
	 * Returns this Account's All transactions from DB
	 */
	public ArrayList<Transaction> retrieveTransactions(Account account) throws SQLException {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		String account_id = account.getId();
		if (account != null) {

			Transaction transaction = null;
			PreparedStatement statement = conn.prepareStatement(
					"SELECT " + columns + " FROM " + tableName + " WHERE account_id = '" + account_id + "';");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				transaction = retrieveById(id);
				transactions.add(transaction);
			}

		}
		return transactions;
	}

	@Override
	public void delete(Transaction transaction) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE id = '" + transaction.getId() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Transaction: " + transaction.getId() + " deleted successfully from db");
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
			System.out.println("Transaction: " + id + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Transaction transaction) throws SQLException {
		delete(transaction);
		create(transaction);
	}

	@Override
	public void updateById(String id) throws SQLException {
		update(retrieveById(id));

	}
}
