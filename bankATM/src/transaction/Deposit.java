package transaction;

import java.sql.Date;
import java.sql.SQLException;

import account.*;
import bankATM.*;
import database.*;

public class Deposit extends Transaction {

	public Deposit(String id, Account account, Money amount, Date created, Status status) {
		super(id, account, amount, created, status);
		setType(Type.Deposit);
	}

	// Constructor with no id, created Date, adding to DB
	public Deposit(Account account, Money amount, Status status) {
		super(account, amount, status);
		setType(Type.Deposit);
		System.out.println("Deposit being created.... ");
		addToDB();
	}

	public Type getType() {
		return Type.Deposit;
	}
	/*
	 * Deposit service fee is 0.
	 */
	@Override
	public Money getServiceFee() {
		return new Money(0, Currency.USD);
	}
	
}
