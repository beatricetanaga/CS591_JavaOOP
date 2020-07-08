package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import account.*;
import bankATM.*;

public class DBPurchasedStocks implements CRUDInterface<PurchasedStock> {

	Connection conn = DataBaseConnection.getConnection();

	String tableName = "Purchased_Stocks";
	String columns = " id, stock_id, account_id, purchased_price, quantity, created ";

	public static void main(String[] args) throws SQLException {
		DBPurchasedStocks testObj = new DBPurchasedStocks();
		String id = UUID.randomUUID().toString();
		Date date = new Date(2001, 12, 1);

		DBAccount testObjAcc = new DBAccount();
		id = "6bf61a1e-0697-4b08-a0ff-86d6cb3d70b9";
		Account testAcc = testObjAcc.retrieveById(id);

		id = "c3d8e51e-bd16-47a2-a2db-30da8b42e6cb";
		Stock stock = new Stock(id, testAcc.getId(), new Money(2222, Currency.USD), 5, date, null);

		PurchasedStock testStock = new PurchasedStock(id, stock, testAcc, new Money(2222, Currency.USD), 2, date);
		testObj.create(testStock);
		testObj.delete(testStock);
	}

	/*
	 * Insert Account into database table Accounts.
	 */

	@Override
	public void create(PurchasedStock stock) throws SQLException {
		String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setString(1, stock.getId());
		statement.setString(2, stock.getStock().getId());
		statement.setString(3, stock.getAccount().getId());
		statement.setFloat(4, stock.getPurchasedPrice().getValue());
		statement.setInt(5, stock.getQuantity());
		statement.setDate(6, stock.getCreated());

		int rowsInserted = statement.executeUpdate();

		if (rowsInserted > 0) {
			System.out.println("A new PurchasedStock was created successfully!");
		}

	}

	@Override
	public PurchasedStock retrieve(PurchasedStock stock) throws SQLException {
		if (stock != null) {
			return retrieveById(stock.getId());
		}
		return null;
	}

	@Override
	public PurchasedStock retrieveById(String id) throws SQLException {
		DBStocks dbStockObj = new DBStocks();
		DBAccount dbAccountObj = new DBAccount();

		PurchasedStock stock = null;

		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE id = '" + id + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("id").equals(id)) {

				Stock stockOriginal = dbStockObj.retrieveById(resultSet.getString("stock_id"));
				Money purchasedPrice = new Money(resultSet.getFloat("purchased_price"), Currency.USD);
				;
				Account account = dbAccountObj.retrieveById(resultSet.getString("account_id"));
				int quantity = resultSet.getInt("quantity");
				Date created = resultSet.getDate("created");

				stock = new PurchasedStock(id, stockOriginal, account, purchasedPrice, quantity, created);
			}
		}
		if (stock == null) {
			System.out.println("No PurchasedStock with id: " + id);
		} else {
			System.out.println("PurchasedStock fetched successfully! account: " + stock);
		}
		return stock;
	}

	public ArrayList<PurchasedStock> retrieveAccountStocks(Account account) throws SQLException {
		ArrayList<PurchasedStock> stocks = new ArrayList<PurchasedStock>();
		String account_id = account.getId();
		if (account != null) {

			PurchasedStock stock = null;
			PreparedStatement statement = conn.prepareStatement(
					"SELECT " + columns + " FROM " + tableName + " WHERE account_id = '" + account_id + "';");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				stock = retrieveById(id);
				stocks.add(stock);
			}
		}
		return stocks;
	}

	public ArrayList<PurchasedStock> retrieveAccountStocks(String account_id) throws SQLException {
		ArrayList<PurchasedStock> stocks = new ArrayList<PurchasedStock>();
		if (account_id != null) {

			PurchasedStock stock = null;
			PreparedStatement statement = conn.prepareStatement(
					"SELECT " + columns + " FROM " + tableName + " WHERE account_id = '" + account_id + "';");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String id = resultSet.getString("id");
				stock = retrieveById(id);
				stocks.add(stock);
			}
		}
		return stocks;
	}
	
	@Override
	public void delete(PurchasedStock stock) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE id = '" + stock.getId() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("PurchasedStock: " + stock.getId() + " deleted successfully from db");
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
			System.out.println("PurchasedStock: " + id + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(PurchasedStock stock) throws SQLException {
		delete(stock);
		create(stock);
	}

	@Override
	public void updateById(String id) throws SQLException {
		update(retrieveById(id));
	}
}