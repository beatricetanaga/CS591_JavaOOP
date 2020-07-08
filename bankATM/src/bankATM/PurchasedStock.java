package bankATM;

import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;

import account.*;
import database.*;

public class PurchasedStock {

	private String id;
	private Stock stock;
	private Account account;
	private Money purchasedPrice;
	private int quantity;
	private Type type;
	private Date created;

	public PurchasedStock(String id, Stock stock, Account account, Money purchasedPrice, int quantity, Date created) {
		setId(id);
		setStock(stock);
		setAccount(account);
		setPurchasedPrice(purchasedPrice);
		this.quantity = (quantity);
		setType(Type.PurchasedStock);
		setCreated(created);
	}

	public PurchasedStock(Stock stock, Account account, int quantity) {
		this(getNewId(), stock, account, stock.getPrice(), quantity, getCurrentDate());
		addToDB();
	}

	public void addToDB() {
		DBPurchasedStocks dbObj = new DBPurchasedStocks();
		try {
			dbObj.create(this);
		} catch (SQLException e) {
			System.out.println("Couldn't add this Purchased Stock to DB.");
			e.printStackTrace();
		}
	}

	public void deleteDB() {
		DBPurchasedStocks dbObj = new DBPurchasedStocks();
		try {
			dbObj.delete(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateDB() {
		DBPurchasedStocks dbObj = new DBPurchasedStocks();
		try {
			dbObj.update(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getId() {
		return id;
	}

	private static String getNewId() {
		return UUID.randomUUID().toString();
	}

	private void setId(String id) {
		this.id = id;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Money getPurchasedPrice() {
		return purchasedPrice;
	}

	public void setPurchasedPrice(Money purchasedPrice) {
		this.purchasedPrice = purchasedPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		updateDB();
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getCreated() {
		return created;
	}

	private static Date getCurrentDate() {
		DBBank dbObj = new DBBank();
		Bank bank = null;
		Date now = null;
		try {
			bank = dbObj.retrieveById("testBank");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bank != null) {
			now = bank.getCurrentDate();
		} else {
			now = new Date(System.currentTimeMillis());
		}
		return now;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/*
	 * Sell this stock. Sells indicated quanitity of this Stock. Deletes this stock
	 * if quantity reaches 0.
	 */
	public boolean sell(int quantity) throws SQLException {
		if (quantity > this.quantity || quantity <= 0) {
			return false; // invalid quantity of stocks!
		}
		//Create Sold Stock Object, add to DB
		SoldStock soldStock = new SoldStock(stock, account, purchasedPrice, quantity);

		this.setQuantity(this.getQuantity() - quantity);

		Money totalSoldPrice = new Money(this.getStock().getPrice().getValue() * quantity, Currency.USD);
		account.deposit(totalSoldPrice);

		if (this.getQuantity() == 0) {
			deleteDB();
		}
		return true;
	}

	public String toString() {
		return "Purchased Stock: " + stock.getName() + ", price : " + getPurchasedPrice();
	}

}
