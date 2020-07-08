package account;

import java.sql.Date;
import java.sql.SQLException;

import bankATM.*;
import database.*;
import transaction.Transaction;
import transaction.Transfer;

public class SavingsAccount extends Account implements Interest {

	public SavingsAccount(String id, Client client, Status status, Money balance, Date created) {
		super(id, client, status, balance, created);
		setType(Type.SavingsAccount);
	}

	public SavingsAccount(Client client) {
		super(client);
		setType(Type.SavingsAccount);
		addToDB();
	}

	@Override
	public void applyInterest() {
		DBBank dbObj = new DBBank();
		Bank bank = null;
		try {
			bank = dbObj.retrieveById("testBank");
			this.setBalance(new Money(getBalance().getValue() * (float) (1 + bank.getSavingsInterest()), Currency.USD));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void open(Client client) throws SQLException {
		DBAccount dbObj = new DBAccount();
		dbObj.create(this);
	}

	@Override
	public void close(Client client) throws SQLException {
		DBAccount dbObj = new DBAccount();
		dbObj.delete(this);
	}

}