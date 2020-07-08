package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import account.*;
import bankATM.*;

public class DBLoans implements CRUDInterface<Loan> {

	static Connection conn = DataBaseConnection.getConnection();

	String tableName = "Loans";
	String columns = " id, client_id, amount, approved, requested, last_payment, interest, status ";

	/*
	 * Account(String id, Client client, boolean status, Money balance, Date
	 * created)
	 */
	public static void main(String[] args) throws SQLException {

		String id = UUID.randomUUID().toString();

		Date date = new Date(2001, 12, 1);

		DBAccount testObjAcc = new DBAccount();
		id = "6bf61a1e-0697-4b08-a0ff-86d6cb3d70b9";
		Account testAcc = testObjAcc.retrieveById(id);

		id = UUID.randomUUID().toString();

		DBLoans testObj = new DBLoans();
//		Loan loanTest = new Loan(id, testAcc, new Money(120, Currency.USD), date, date, 1);
//		testObj.create(loanTest);
//		Loan loanTest2 = new Loan(testAcc, new Money(120, Currency.USD), date, 1);

		testObj.retrieveRequestedLoans();
//		for (Account a: accounts) {
//			System.out.println("A ccount" + accounts.size());
//		}
//		testObj.create(loanTest);
//		testObj.delete(loanTest);

	}

	/*
	 * Insert Loan into database table Accounts.
	 */
	@Override
	public void create(Loan loan) throws SQLException {
		String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setString(1, loan.getId());
		statement.setString(2, loan.getAccount().getClient().getId());
		statement.setFloat(3, loan.getAmount().getValue());
		statement.setDate(4, loan.getApproved());
		statement.setDate(5, loan.getRequested());
		statement.setDate(6, loan.getLastPay());
		statement.setFloat(7, loan.getInterest());
		statement.setString(8, loan.getStatus().str);

		int rowsInserted = statement.executeUpdate();

		if (rowsInserted > 0) {
			System.out.println("A new Loan was created successfully!");
		}

	}

	@Override
	public Loan retrieve(Loan loan) throws SQLException {
		if (loan != null) {
			return retrieveById(loan.getId());
		}
		return null;
	}

	/*
	 * Returns this All Requested loans from DB
	 */
	public ArrayList<Loan> retrieveRequestedLoans() throws SQLException {
		ArrayList<Loan> loans = new ArrayList<Loan>();
		String status = Status.Requested.str;
		if (status != null) {
			Loan loan = null;
			PreparedStatement statement = conn
					.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE status = '" + status + "';");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				loan = retrieveById(id);
				loans.add(loan);
			}

		}
		System.out.println("Requested Loans: " + loans.size());
		return loans;
	}

	/*
	 * Returns this All Approved loans from DB
	 */
	public ArrayList<Loan> retrieveApprovedLoans() throws SQLException {
		ArrayList<Loan> loans = new ArrayList<Loan>();
		String status = Status.Approved.str;
		if (status != null) {
			Loan loan = null;
			PreparedStatement statement = conn
					.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE status = '" + status + "';");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				loan = retrieveById(id);
				loans.add(loan);
			}

		}
		return loans;
	}

	/*
	 * Returns this Client's All loans from DB
	 */
	public ArrayList<Loan> retrieveClientLoans(Client client) throws SQLException {
		ArrayList<Loan> loans = new ArrayList<Loan>();
		String client_id = client.getId();
		if (client != null) {

			Loan loan = null;
			PreparedStatement statement = conn.prepareStatement(
					"SELECT " + columns + " FROM " + tableName + " WHERE client_id = '" + client_id + "';");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				loan = retrieveById(id);
				loans.add(loan);
			}

		}
		return loans;
	}

	/*
	 * Returns this All Loans from DB
	 */
	public ArrayList<Loan> retrieveLoans() throws SQLException {
		ArrayList<Loan> loans = new ArrayList<Loan>();

		Loan loan = null;
		PreparedStatement statement = conn.prepareStatement("SELECT " + columns + " FROM " + tableName + ";");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			String id = resultSet.getString("id");
			loan = retrieveById(id);
			loans.add(loan);
		}
		return loans;
	}

	@Override
	public Loan retrieveById(String id) throws SQLException {
		DBClient dbClientObj = new DBClient();
		// " id, client_id, amount, approved, requested, interest, status ";

		Loan loan = null;
		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE id = '" + id + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("id").equals(id)) {
				Account account = dbClientObj.retrieveById(resultSet.getString("client_id")).getLoansAccount();
				System.out.println(" >>> Account db CLient "
						+ dbClientObj.retrieveById(resultSet.getString("client_id")).getEmail());
				Money amount = new Money(resultSet.getFloat("amount"), Currency.USD);
				Date requested = resultSet.getDate("requested");
				Float interest = resultSet.getFloat("interest");
				Date lastPay = resultSet.getDate("last_payment");
				Status status = null;
				String statusStr = resultSet.getString("status");
				if (Status.Requested.equals(statusStr)) {
					status = Status.Requested;
					loan = new Loan(id, account, amount, requested, lastPay, interest, status);
				} else {
					status = Status.Approved;
					loan = new Loan(id, account, amount, requested, lastPay, interest, status);
					Date approved = resultSet.getDate("approved");
					loan.setApproved(approved);
				}
				
			}
		}
		if (loan == null) {
			System.out.println("No loan with id: " + id);
		} else {
			System.out.println("Loan fetched successfully! Loan: " + loan);
		}
		return loan;
	}

	@Override
	public void delete(Loan loan) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE id = '" + loan.getId() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Loan: " + loan.getId() + " deleted successfully from db");
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
			System.out.println("Loan: " + id + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Loan loan) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("A ccount" + loan.getAccount());
		delete(loan);
		create(loan);
	}

	@Override
	public void updateById(String id) throws SQLException {
		update(retrieveById(id));

	}
}