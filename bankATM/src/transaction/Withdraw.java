package transaction;

import java.sql.Date;
import java.sql.SQLException;

import account.*;
import bankATM.*;
import database.DBBank;

public class Withdraw extends Transaction implements ServiceFee {

	public Withdraw(String id, Account account, Money amount, Date created, Status status) {
		super(id, account, amount, created, status);
		setType(Type.Withdraw);
		System.out.println(" >>Withdraw Null account? :"+ account);
	}

	// Constructor with no id, created Date, adding to DB
	public Withdraw(Account account, Money amount, Status status) {
		super(account, amount, status);
		setType(Type.Withdraw);
		addToDB();
	}

	@Override
	public Money getServiceFee() {
		DBBank dbObj = new DBBank();
		Bank bank = null;
		Money serviceFee = null;
		try {
			bank = dbObj.retrieveById("testBank");
			serviceFee = bank.getWithdrawFee();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serviceFee = serviceFee.add(bank.getCheckingAccountFee());
		return serviceFee;
	}

}
