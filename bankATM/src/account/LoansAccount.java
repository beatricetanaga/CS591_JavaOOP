package account;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import bankATM.*;
import database.*;

public class LoansAccount extends Account {

	/*
	 * Note: Loans balance is negative.
	 */
	public LoansAccount(String id, Client client, Status status, Money balance, Date created) {
		super(id, client, status, balance, created);
		setType(Type.LoansAccount);
	}

	public LoansAccount(Client client, Money balance) {
		super(client);
		setType(Type.LoansAccount);
		setBalance(balance);
		addToDB();
	}
	
	public ArrayList<Loan> getAllLoans() throws SQLException {
		DBLoans dbObj = new DBLoans();
		ArrayList<Loan> loans = dbObj.retrieveClientLoans(getClient());
		return loans;
	}

	public ArrayList<Loan> getNotPaidLoans() throws SQLException {
		DBLoans dbObj = new DBLoans();
		Money totalToPay = new Money(0, Currency.USD);
		ArrayList<Loan> loans = dbObj.retrieveClientLoans(getClient());
		for (Loan loan : loans) {
			if (loan.getAmount().getValue() < 0) {
				totalToPay.add(loan.getAmount());
			} else {
				loans.remove(loan);
			}
		}
		this.setBalance(new Money(totalToPay.getValue(), Currency.USD));
		return loans;
	}

	@Override
	public void setBalance(Money amount) {
		if(amount.getValue() < 0) {
			super.setBalance(amount);
		} else {
			super.setBalance(new Money(-1 * amount.getValue(), Currency.USD));
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