package bankATM;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

import account.*;
import database.*;
import manager.*;
import transaction.*;

public class Test {

	public String getId() {
		return UUID.randomUUID().toString();
	}

	public Date getDate() {
		Date date = new Date(2001, 12, 1);
		return date;
	}

	public Bank createBank() {
		Money checkingAccountFee = new Money(2222, Currency.USD);
		Money withdrawFee = new Money(3, Currency.USD), depositFee = new Money(4, Currency.USD);
		;
		Money closeAccount = new Money(2, Currency.USD), openAccount = new Money(2, Currency.USD);
		;
		new Money(2222, Currency.USD);
		float savingsInterest = (float) 0.1;
		float loansInterest = (float) 0.05;
		Money balance = new Money(222239322, Currency.USD);
		Date currentDate = getDate();

		Bank bank = new Bank("testBank", checkingAccountFee, withdrawFee, savingsInterest, loansInterest, closeAccount,
				openAccount, depositFee, balance, currentDate);
		return null;
//		return new Bank("BANKATM", checkingAccountFee, withdrawFee, savingsInterest, loansInterest, closeFee, openFee,depositFee  balance,
//				 currentDate);

	}

	public void generateTestObjects() {

		DBBank testObjDBBank = new DBBank();
		DBManager testObjDBManager = new DBManager();
		DBPerson testObjDBPerson = new DBPerson();
		DBClient testObjDBClient = new DBClient();
		DBAccount testObjDBAccount = new DBAccount();
		DBStocks testObjDBStocks = new DBStocks();
		DBSoldStocks testObjDBSoldStocks = new DBSoldStocks();
		DBPurchasedStocks testObjDBPurchasedStocks = new DBPurchasedStocks();
		DBTransaction testObjDBTransaction = new DBTransaction();

		String id = UUID.randomUUID().toString();

		Bank bank = createBank();
		Person personManager = new Person(getId(), "Camilla", "Satte", new Date(1998, 7, 1), "1234456789", "Boston",
				"US");
		Person personClient = new Person(getId(), "ManagerName", "ManagerLastName", new Date(1998, 7, 1), "1234456789",
				"Boston", "US");

//		Account account = new Deposit(  );
	}

	public void openDepositAccount() {

	}

	public boolean openCheckingAccount() {
		return false;
	}

	public boolean openSavingsAccount() {
		return false;
	}

	public boolean openSecurityAccount() {
		return false;
	}

	public boolean openLoansAccount() {
		return false;
	}

	public ArrayList<Account> getAllAccounts() {
		return null;
	}

	public ArrayList<Account> getAccountsOfType(Type type) {
		return null;
	}

	public Account getDepositAccount() {
		return null;
	}

	public Account getLoansAccount() {
		return null;
	}

	public boolean closeAccount(Account account) {
		return false;
	}

	public ArrayList<Transaction> getTransactions() {
		return null;

	}

	public void payBank(Account account, Money amount) {

	}

	public static void main(String[] args) {

		Test test = new Test();

	}
}
