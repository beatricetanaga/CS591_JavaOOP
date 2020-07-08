package bankATM;

import java.sql.Date;
import java.sql.SQLException;

import database.DBAccount;
import database.DBBank;
import database.DBClient;
import manager.*;

public class Bank {

	private String name;
	private Money checkingAccountFee;
	private Money withdrawFee;
	private Money transferFee;
	private Money depositFee;
	private float savingsInterest;
	private Money closeAccountFee;
	private Money openAccountFee;
	private float loansInterest;
	private Money balance;
	private Money allowedBalanceToOpenSecurityAccount;
	private Date currentDate;

	public Bank(String name, Money checkingAccountFee, Money withdrawFee, float savingsInterest, float loansInterest,
			Money closeAccount, Money openAccount, Money depositFee, Money balance, Date currentDate) {
		setName(name);
		setCheckingAccountFee(checkingAccountFee);
		setWithdrawFee(withdrawFee);
		setSavingsInterest(savingsInterest);
		setLoansInterest(loansInterest);
		setBalance(balance);
		setCurrentDate(currentDate);
		setCloseAccountFee(closeAccount);
		setOpenAccountFee(openAccount);
		setDepositFee(depositFee);
	}

	public void addToDB() {
		DBBank dbObj = new DBBank();
		try {
			dbObj.create(this);
		} catch (SQLException e) {
			System.out.println("Couldn't add this Account to DB.");
			e.printStackTrace();
		}
	}

	public void updateDB() {
		DBBank dbObj = new DBBank();
		try {
			dbObj.update(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteDB() {
		DBBank dbObj = new DBBank();
		try {
			dbObj.delete(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Money getCheckingAccountFee() {
		return checkingAccountFee;
	}

	public void setCheckingAccountFee(Money checkingAccountFee) {
		this.checkingAccountFee = checkingAccountFee;
//		updateDB();
	}

	public Money getWithdrawFee() {
		return withdrawFee;
	}

	public void setWithdrawFee(Money withdrawFee) {
		this.withdrawFee = withdrawFee;
//		 updateDB();
	}

	public float getSavingsInterest() {
		return savingsInterest;
	}

	public void setSavingsInterest(float savingsInterest) {
		this.savingsInterest = savingsInterest;
//		updateDB();
	}

	public float getLoansInterest() {
		return loansInterest;
	}

	public void setLoansInterest(float loansInterest) {
		this.loansInterest = loansInterest;
//		updateDB();
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
//		updateDB();
	}

	public void addToBalance(Money balance) {
		setBalance(this.balance.add(balance));
//		updateDB();
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
//		updateDB();
	}

	public Money getCloseAccountFee() {
		return closeAccountFee;
	}

	public void setCloseAccountFee(Money closeAccountFee) {
		this.closeAccountFee = closeAccountFee;
//		updateDB();
	}

	public Money getOpenAccountFee() {
		return openAccountFee;
	}

	public void setOpenAccountFee(Money openAccountFee) {
		this.openAccountFee = openAccountFee;
//		updateDB();
	}

	public Money getTransferFee() {
		Money transferFee = new Money(0, Currency.USD);
		return transferFee;
	}

	public void setTransferFee(Money transferFee) {
		this.transferFee = transferFee;
//		updateDB();
	}

	/*
	 * Retrieve client with email. If Client not found return null.
	 */
	public Client findClientByEmail(String email) throws SQLException {
		DBClient dbObj = new DBClient();
		Client client = dbObj.retrieveByEmail(email);
		return client;
	}

	public Money getDepositFee() {
		return depositFee;
	}

	public void setDepositFee(Money depositFee) {
		this.depositFee = depositFee;
		updateDB();
	}

	public Money getAllowedBalanceToOpenSecurityAccount() {
//		return allowedBalanceToOpenSecurityAccount;
		return new Money(100000, Currency.USD);
	}

	public void setAllowedBalanceToOpenSecurityAccount(Money allowedBalanceToOpenSecurityAccount) {
		this.allowedBalanceToOpenSecurityAccount = allowedBalanceToOpenSecurityAccount;
//		updateDB();
	}
}
