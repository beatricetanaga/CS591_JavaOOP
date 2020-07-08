package account;

import java.sql.Date;
import java.sql.SQLException;

import bankATM.*;
import database.*;
import manager.*;

public class CheckingAccount extends Account implements ServiceFee {

	public CheckingAccount(String id, Client client, Status status, Money balance, Date created) {
		super(id, client, status, balance, created);
		setType(Type.CheckingAccount);
	}
	
	public CheckingAccount(Client client) {
		super(client);
		setType(Type.CheckingAccount);
		addToDB();
	}

	@Override
	public Money getServiceFee() {
		// TODO Auto-generated method stub
		DBBank DBbankObj = new DBBank();
		Bank bank = null;
		try {
			bank = DBbankObj.retrieveById("testBank");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bank.getCheckingAccountFee();
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