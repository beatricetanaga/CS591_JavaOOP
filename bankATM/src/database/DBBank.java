package database;

import java.sql.Date;
import java.sql.*;

import bankATM.*;

public class DBBank implements CRUDInterface<Bank> {

	static Connection conn = DataBaseConnection.getConnection();

	String tableName = "Bank";
	String columns = " name, checking_account_fee, withdraw_fee, savings_interest, loans_interest, close_account_fee, "
			+ "open_account_fee, deposit_fee, balance, `current_date` ";

	public static void main(String[] args) throws SQLException {

		DBBank testObj = new DBBank();
		Date date = new Date(2010, 1, 10);
		Bank testBank = new Bank("testBank", new Money(2, Currency.USD), new Money(4, Currency.USD), (float) 0.1,
				(float) 0.2, new Money(2, Currency.USD), new Money(3, Currency.USD), new Money(12, Currency.USD),
				new Money(5000000, Currency.USD), date);
		System.out.println("Current time: " + testBank.getCurrentDate());

		
		testObj.create(testBank);
		
		testBank = new Bank("testBank2", new Money(3, Currency.USD), new Money(4, Currency.USD), (float) 0.1,
				(float) 0.2, new Money(2, Currency.USD), new Money(3, Currency.USD), new Money(12, Currency.USD),
				new Money(5000000, Currency.USD), date);
//		testObj.update(testBank);
		testObj.retrieveById("testBank");
//		testObj.delete(testBank);
	}

	/*
	 * Insert Person into database table Person.
	 */
	@Override
	public void create(Bank bank) throws SQLException {
		String sql = "INSERT INTO `" + tableName + "` (" + columns + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setString(1, bank.getName());
		statement.setFloat(2, bank.getCheckingAccountFee().getValue());
		statement.setFloat(3, bank.getWithdrawFee().getValue());
		statement.setFloat(4, bank.getSavingsInterest());
		statement.setFloat(5, bank.getLoansInterest());
		statement.setFloat(6, bank.getCloseAccountFee().getValue());
		statement.setFloat(7, bank.getOpenAccountFee().getValue());
		statement.setFloat(8, bank.getDepositFee().getValue());
		statement.setFloat(9, bank.getBalance().getValue());
		statement.setDate(10, bank.getCurrentDate());

		statement.executeUpdate();
		int rowsInserted = statement.executeUpdate();
		if (rowsInserted > 0) {
			System.out.println("A new Bank was created successfully!");
		}
	}

	@Override
	public Bank retrieve(Bank bank) throws SQLException {
		if (bank != null) {
			return retrieveById(bank.getName());
		}
		return null;
	}

	@Override
	public Bank retrieveById(String name) throws SQLException {
		Bank bank = null;
		PreparedStatement statement = conn.prepareStatement("SELECT " + columns + "FROM " + tableName);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("name").equals(name)) {
				Money checkingAccountFee = new Money(resultSet.getFloat("checking_account_fee"), Currency.USD);
				Money withdrawFee = new Money(resultSet.getFloat("withdraw_fee"), Currency.USD);
				Money closeAccount = new Money(resultSet.getFloat("close_account_fee"), Currency.USD);
				Money openAccount = new Money(resultSet.getFloat("open_account_fee"), Currency.USD);
				Money depositFee = new Money(resultSet.getFloat("deposit_fee"), Currency.USD);
				float savingsInterest = resultSet.getFloat("savings_interest");
				float loansInterest = resultSet.getFloat("loans_interest");
				Money balance = new Money(resultSet.getFloat("balance"), Currency.USD);
				Date currentDate = resultSet.getDate("current_date");

				bank = new Bank(name, checkingAccountFee, withdrawFee, savingsInterest, loansInterest, closeAccount,
						openAccount, depositFee, balance, currentDate);
			}
		}
		if (bank == null) {
			System.out.println("No bank with id: " + name);
		} else {
			System.out.println("A bank fetched successfully! Bank Name: " + bank);
		}
		return bank;
	}

	@Override
	public void delete(Bank bank) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE name = '" + bank.getName() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Bank: " + bank.getName() + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(String name) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE name = '" + name + "';";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("Bank: " + name + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Bank bank) throws SQLException {
		// TODO Auto-generated method stub
		delete(bank);
		create(bank);
	}

	@Override
	public void updateById(String id) throws SQLException {
		update(retrieveById(id));
	}
}
