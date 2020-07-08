package account;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import bankATM.*;
import database.*;
import transaction.*;

public abstract class Account implements OpenClose {

	private String id;
	private Client client;
	private Status status;
	private Type type;
	private Money balance;
	private Date created;

	protected Account(String id, Client client, Status status, Money balance, Date created) {
		setId(id);
		setClient(client);
		this.status = (status);
		this.balance = (balance);
		setCreated(created);
	}

	// Create new account for this client. Add to DB;
	public Account(Client client) {
		this(getNewId(), client, Status.Open, new Money(0, Currency.USD), getNewCreated());
	}

	public void addToDB() {
		DBAccount dbObj = new DBAccount();
		try {
			dbObj.create(this);
		} catch (SQLException e) {
			System.out.println("Couldn't add this Account to DB.");
			e.printStackTrace();
		}
	}

	public void updateDB() {
		DBAccount dbObj = new DBAccount();
		try {
			dbObj.update(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteDB() {
		DBAccount dbObj = new DBAccount();
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

	public Client getClient() {
		if (client == null) {
			System.out.print(" Account is null ");
		}
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
		updateDB();
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
		updateDB();
	}

	public Date getCreated() {
		return created;
	}

	private static Date getNewCreated() {
		return new Date(System.currentTimeMillis());
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String toString() {
//		return "id: " + id + " Balance: " + balance;
		return type.str;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	/*
	 * Deposit money to this Account.
	 */
	public Deposit deposit(Money amount)  {
		Deposit deposit = null;
		if (amount.getValue() > this.getBalance().getValue()) {
			deposit = new Deposit(this, amount, Status.Completed);
			Money serviceFee = deposit.getServiceFee();
			if (amount.compareTo(serviceFee) > 0) { // check if enough amount to pay service fee
				try {
					client.payBank(this, serviceFee);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.setBalance(this.getBalance().add(amount));
				
			} else {
				deposit.setStatus(Status.Cancelled); // invalid amount of money, not enough to pay service fee
			}
		} else {
			System.out.println("Error! Deposit Failed.");
		}
//		this.updateDB();
		return deposit;
	}

	/*
	 * Transfer money from this Account to destination Account.
	 */
	public Transfer transfer(Money amount, Account destination)  {
		Transfer transfer = null;
		// TODO: service fee,
		if (amount.getValue() <= this.getBalance().getValue()) {
			transfer = new Transfer(this, amount, destination, Status.Completed);
			Money serviceFee = transfer.getServiceFee();
			try {
				client.payBank(this, serviceFee);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.setBalance(this.getBalance().subtract(amount));
			destination.setBalance(destination.getBalance().add(amount));
			destination.updateDB();
			this.updateDB();
		} else {
			transfer = new Transfer(this, amount, destination, Status.Cancelled);
			System.out.println("Invalid Amount of money to transfer! Exceeds your Account balance.");
		}
		return transfer;
	}

	/*
	 * Deposit money to this Account.
	 */
	public Withdraw withdraw(Money amount) {
		Withdraw withdraw = null;
		if (amount.getValue() > this.getBalance().getValue()) {
			System.out.println("Invalid Amount of money to withdraw! Exceeds your Account balance.");
		} else {

			withdraw = new Withdraw(this, amount, Status.Pending);
			System.out.println("A new Transaction + " + amount + " " +withdraw );
			Money serviceFee = withdraw.getServiceFee();

			if (balance.subtract(amount).compareTo(serviceFee) > 0) { // check if enough amount to pay service fee
				try {
					client.payBank(this, serviceFee);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.setBalance(this.getBalance().subtract(amount));
				System.out.println("A new Status Transaction + " + amount + " " + withdraw.getStatus() );

				withdraw.setStatus(Status.Completed);
			} else {
				withdraw.setStatus(Status.Cancelled); // not enough to pay service fee
			}
		}
		return withdraw; // if null -> failed Withdraw: Invalid amount
	}

	/*
	 * Get all transactions associated with this account
	 */
	public ArrayList<Transaction> getTransactions() throws SQLException {
		DBTransaction dbObj = new DBTransaction();
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		transactions = dbObj.retrieveTransactions(this);

		if (transactions.isEmpty()) {
			System.out.println("No transaction found associated with this Account.");
			return null;
		}
		return transactions;
	}

	public String getInfo() {
		String info = type + ": " + "\nBalance: " + balance + "\nStatus: " + status;
		return info;
	}

}