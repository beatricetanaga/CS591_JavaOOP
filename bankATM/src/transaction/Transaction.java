package transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.UUID;

import bankATM.*;
import account.*;
import database.*;

public abstract class Transaction implements ServiceFee {

	private String id;
	protected Account account;
	private Client client;
	private Money amount;
	private Status status;
	private Type type;
	private Date created;

	Transaction(String id, Account account, Money amount, Date created, Status status) {
		setId(id);
		setCreated(created);
		setAmount(amount);
		setAccount(account);
		System.out.println(" >> Null account? :"+ account);
		setClient(account.getClient());
		System.out.println(" >> Null client? :"+ client);
		this.status = (status);
	}

	/*
	 * Constructor To create New Transaction. Adds to DB.
	 */
	Transaction(Account account, Money amount, Status status) {
		this(getNewId(), account, amount, getCurrentDate(), status);
	}

	public void addToDB() {
		DBTransaction dbObj = new DBTransaction();
		try {
			dbObj.create(this);
		} catch (SQLException e) {
			System.out.println("Couldn't add this Transaction to DB.");
			e.printStackTrace();
		}
	}
	
	public void updateDB() {
		DBTransaction dbObj = new DBTransaction();
		try {
			dbObj.update(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteDB() {
		DBTransaction dbObj = new DBTransaction();
		try {
			dbObj.delete(this);
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

	private void setCreated(Date created) {
		this.created = created;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public String toString() {
		return "Transaction: " + getType() + " \nAmount: " + amount + " \nDate: " + created;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		System.out.println(" >> Null account? :"+ account);
		this.client = client;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
		updateDB();
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
		updateDB();
	}

	public String getInfo() {
		String info = type + ": " + amount + " (" + created + ")";
		return info;
	}

}
