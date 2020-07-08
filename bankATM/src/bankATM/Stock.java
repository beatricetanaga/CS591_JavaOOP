package bankATM;

import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;

import database.*;

public class Stock {

	private String id;
	private String name;
	private Money price;
	private int quantity;
	private Status status;
	private Date created;

	public Stock(String id, String name, Money price, int quantity, Date created, Status status) {
		setId(id);
		setName(name);
		setPrice(price);
		setQuantity(quantity);
		setCreated(created);
		setStatus(status);
	}
	
	public Stock( String name, Money price, int quantity) {
		this(getNewId(), name, price, quantity, getCurrentDate(), Status.Open);
		addToDB();
	}

	public void addToDB() {
		DBStocks dbObj = new DBStocks();
		try {
			dbObj.create(this);
		} catch (SQLException e) {
			System.out.println("Couldn't add this Purchased Stock to DB.");
			e.printStackTrace();
		}
	}

	public void deleteDB() {
		DBStocks dbObj = new DBStocks();
		try {
			dbObj.delete(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateDB() {
		DBStocks dbObj = new DBStocks();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public String toString() {
		return name;
	}
	
}
