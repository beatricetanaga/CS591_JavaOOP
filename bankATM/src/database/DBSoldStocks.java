package database;

import java.sql.*;
import java.util.UUID;

import account.*;
import bankATM.*;

public class DBSoldStocks implements CRUDInterface<SoldStock> {

	Connection conn = DataBaseConnection.getConnection();

	String tableName = "Sold_Stocks";
	String columns = " id, stock_id, account_id, purchased_price, quantity, created, sold_price, status ";

	public static void main(String[] args) throws SQLException {

		DBAccount testObjAcc = new DBAccount();
		String id = "6bf61a1e-0697-4b08-a0ff-86d6cb3d70b9";
		Account testAcc = testObjAcc.retrieveById(id);

		DBStocks testObjStock = new DBStocks();
		id = "89121250-47d9-4ff6-bf59-4589e5c5030a";
		Stock stock = testObjStock.retrieveById(id);

		Date date = new Date(1990, 12, 1);

		id = UUID.randomUUID().toString();
		DBSoldStocks DBSoldStocksObj = new DBSoldStocks();
		id = "a8d7dccd-447e-4c66-9d14-62c8e472c51c";
		SoldStock testStock = new SoldStock(id, stock, testAcc, new Money(2222, Currency.USD),
				new Money(2222, Currency.USD), 2, date);
		DBSoldStocksObj.retrieveById(id);
//		DBSoldStocksObj.create(testStock);
//		DBSoldStocksObj.delete(testStock);
	}

	/*
	 * Insert Account into database table Accounts.
	 */

	@Override
	public void create(SoldStock stock) throws SQLException {
		String sql = "INSERT INTO " + tableName + " (" + columns + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement statement = conn.prepareStatement(sql);

		statement.setString(1, stock.getId());
		statement.setString(2, stock.getStock().getId());
		statement.setString(3, stock.getAccount().getId());
		statement.setFloat(4, stock.getPurchasedPrice().getValue());
		statement.setInt(5, stock.getQuantity());
		statement.setDate(6, stock.getCreated());
		statement.setFloat(7, stock.getSoldPrice().getValue());
		statement.setString(8, stock.getStatus().str);

		int rowsInserted = statement.executeUpdate();

		if (rowsInserted > 0) {
			System.out.println("A new PurchasedStock was created successfully!");
		}

	}

	@Override
	public SoldStock retrieve(SoldStock stock) throws SQLException {
		if (stock != null ) {
			return retrieveById(stock.getId());
		}
		return null;
	}

	@Override
	public SoldStock retrieveById(String id) throws SQLException {
		DBStocks dbStockObj = new DBStocks();
		DBAccount dbAccountObj = new DBAccount();

		SoldStock stock = null;

		PreparedStatement statement = conn
				.prepareStatement("SELECT " + columns + " FROM " + tableName + " WHERE id = '" + id + "';");
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {

			if (resultSet.getString("id").equals(id)) {

				Stock stockOriginal = dbStockObj.retrieveById(resultSet.getString("stock_id"));
				Money purchasedPrice = new Money(resultSet.getFloat("purchased_price"), Currency.USD);
				Money soldPrice = new Money(resultSet.getFloat("sold_price"), Currency.USD);
				Account account = dbAccountObj.retrieveById(resultSet.getString("account_id"));
				int quantity = resultSet.getInt("quantity");
				Date created = resultSet.getDate("created");

				stock = new SoldStock(id, stockOriginal, account, purchasedPrice, soldPrice, quantity, created);
			}
		}
		if (stock == null) {
			System.out.println("No SoldStock with id: " + id);
		} else {
			System.out.println("SoldStock fetched successfully! account: " + stock);
		}
		return stock;
	}

	@Override
	public void delete(SoldStock stock) throws SQLException {
		String sql = "DELETE FROM " + tableName + " WHERE id = '" + stock.getId() + "'";
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			System.out.println("SoldStock: " + stock.getId() + " deleted successfully from db");
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
			System.out.println("SoldStock: " + id + " deleted successfully from db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(SoldStock stock) throws SQLException {
		delete(stock);
		create(stock);
	}

	@Override
	public void updateById(String id) throws SQLException {
		update(retrieveById(id));
	}

}