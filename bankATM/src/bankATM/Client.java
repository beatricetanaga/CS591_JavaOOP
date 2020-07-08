package bankATM;

import java.sql.Date;
import java.sql.SQLException;

import account.*;
import java.util.*;
import database.*;
import transaction.*;

public class Client {

	private String id;
	private Person person;
	private Date created;
	private String email;
	private String password;
	private Bank bank;

	public Client(String id, Person person, Date created, String email, String password) {
		this.id = (id);
		this.person = (person);
		this.created = (created);
		this.email = (email);
		this.password =(password);
		this.setBank();
	}

	public void addToDB() {
		DBClient dbObj = new DBClient();
		try {
			dbObj.create(this);
		} catch (SQLException e) {
			System.out.println("Couldn't add this Client to DB.");
			e.printStackTrace();
		}
	}

	public void updateDB() {
		DBClient dbObj = new DBClient();
		try {
			dbObj.update(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteDB() {
		DBClient dbObj = new DBClient();
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

	private void setId(String id) {
		this.id = id;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Person getPerson() {
		return person;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	/*
	 * Creates Deposit Account. Must be used once when new Client signs up. Client
	 * has only one deposit account.
	 */
	public void openDepositAccount() throws SQLException {
		if (getAccountsOfType(Type.DepositAccount) == null) {
			DepositAccount depositAccount = new DepositAccount(this);
			depositAccount.open(this);
		} else {
			System.out.println("Client already have Deposit Account! No new needed!");
		}
	}

	/*
	 * Creates Checking Account. Client can have up to ? Checking accounts. Default:
	 * 1 Checking Account.
	 */
	public String openCheckingAccount() throws SQLException {
		Money serviceFee = bank.getOpenAccountFee();
		String message = "";
		// TODO: Ask Bank the number of Checking accounts this client ALLOWED to have
		if (getAccountsOfType(Type.CheckingAccount) == null) {
			CheckingAccount checkingAccount = new CheckingAccount(this);
			message = "Success!";
		} else {
			System.out.println("Client already have Checking Account.");
			message = "Client already have Checking Account.";
		}
		payBank(getDepositAccount(), serviceFee);
		return message;
	}

	/*
	 * Creates Savings Account. Client can have up to ? Savings accounts. Default: 1
	 * Savings Account.
	 */
	public String openSavingsAccount() throws SQLException {
		DBBank bankObj = new DBBank();
		Bank bank = bankObj.retrieveById("testBank");
		Money serviceFee = bank.getOpenAccountFee();
		String message = "";
		if (getAccountsOfType(Type.SavingsAccount) == null) {
			SavingsAccount savingsAccount = new SavingsAccount(this);
			message = "Success!";
		} else {
			System.out.println("Client already have Savings Account.");
			message = "Client already have Savings Account. Can have only 1 Savings Account.";
		}
		payBank(getDepositAccount(), serviceFee);
		return message;
	}

	/*
	 * Creates Security Account. Client can have up to ? Security accounts. Default:
	 * 1 Security Account.
	 */
	public String openSecurityAccount() throws SQLException {
		Money serviceFee = bank.getOpenAccountFee();
		String message = "";
		if (getAccountsOfType(Type.SavingsAccount) == null) {
			message = "Can't open Security Account becuase you don't have Savings Account.";
		} else {
			SavingsAccount savingsAccount = (SavingsAccount) getAccountsOfType(Type.SavingsAccount).get(0);
			if (savingsAccount.getBalance().compareTo(bank.getAllowedBalanceToOpenSecurityAccount()) < 0) {
				System.out.println("Not enough balance in Savings to qualify opening Security Account");
				message = "Not enough balance in Savings to qualify opening Security Account";
			}
			if (getAccountsOfType(Type.SecurityAccount) == null) {
				SecurityAccount securityAccount = new SecurityAccount(this);
				message = "Success!";
			} else {
				System.out.println("Client already have Security Account. Can have only 1 Security Account");
				message = "Client already have Security Account. Can have only 1 Security Account";
			}
			payBank(getDepositAccount(), serviceFee);
		}

		return message;
	}

	/*
	 * Creates Loans Account. Client can have up to ? Loans accounts. Default: 1
	 * Loans Account.
	 */
	public String openLoansAccount() {
		String message = "";
		try {
			if (getAccountsOfType(Type.LoansAccount) == null) {
				LoansAccount loansAccount = new LoansAccount(this, new Money(0, Currency.USD));
				message = "Success!";
			} else {
				System.out.println("Client already have Loans Account. Can have only 1 Loans Account.");
				message = "Client already have Loans Account. Can have only 1 Loans Account.";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	/*
	 * Returns this Client's list all accounts from DB
	 */
	public ArrayList<Account> getAllAccounts() throws SQLException {
		DBAccount dbObj = new DBAccount();
		ArrayList<Account> accounts = new ArrayList<Account>();
		accounts = dbObj.retrieveClientAccounts(this);

		if (accounts.isEmpty()) {
			System.out.println("No accounts found associated with this Client.");
			return null;
		}
		return accounts;
	}

	/*
	 * Returns this Client's total Balance
	 */
	public float getOverallBalance() {
		ArrayList<Account> accounts = null;
		try {
			accounts = getAllAccounts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float totalBalance = 0;
		if (accounts.isEmpty()) {
			System.out.println("No accounts found associated with this Client.");
		} else {
			for (Account account : accounts) {
				totalBalance += account.getBalance().getValue();
			}
		}
		
		
		return totalBalance;
	}

	/*
	 * Returns this Client's List of Specified Type Accounts
	 * (Checking/Savings/Deposit/Security/Security/Loans)
	 */
	public ArrayList<Account> getAccountsOfType(Type type) throws SQLException {
		ArrayList<Account> accounts = new ArrayList<Account>();
		for (Account account : getAllAccounts()) {
			if (account.getType().equals(type)) {
				accounts.add(account);
			}
		}
		if (accounts.isEmpty()) {
			System.out.println("No " + type + " accounts found associated with this Client.");
			return null;
		}
		return accounts;
	}

	/*
	 * Returns this Client's Deposit Account
	 */
	public Account getDepositAccount()  {
		Account depositAccount = null;
		try {
			depositAccount = getAccountsOfType(Type.DepositAccount).get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return depositAccount;
	}
	
	/*
	 * Returns this Client's savings Account
	 */
	public Account getSavingsAccount(){
		Account account = null;
		try {
			account = getAccountsOfType(Type.SavingsAccount).get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}
	/*
	 * Returns this Client's chekcing Account
	 */
	public Account getCheckingAccount() {
		Account account = null;
		try {
			account = getAccountsOfType(Type.CheckingAccount).get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}
	/*
	 * Returns this Security chekcing Account
	 */
	public Account getSecurityAccount() {
		Account account = null;
		try {
			account = getAccountsOfType(Type.SecurityAccount).get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}
	/*
	 * Returns this Client's Loans Account
	 */
	public Account getLoansAccount() throws SQLException {
		Account account = null;
		ArrayList<Account> loansAccounts = getAccountsOfType(Type.LoansAccount);
		if (!loansAccounts.isEmpty()) {
			openLoansAccount();
			account = getAccountsOfType(Type.LoansAccount).get(0);
		}
		System.out.println(" ");
		System.out.println("loans account id = " +account +"\n");
		return account;
	}

	/*
	 * Close Account. Client can close account if balance >= 0 (remaining balance is
	 * moved to Deposit Account).
	 */
	public String closeAccount(Account account) throws SQLException {
		Money serviceFee = bank.getCloseAccountFee();
		String message = "";
		Account depositAccount = getDepositAccount();
		if (account.getBalance().compareTo(serviceFee) < 0) {
			System.out.println("Not enough balance to pay Close-Account fees.");
			message = "Not enough balance to pay Close-Account fees.";
		}
		if (account.getType().equals(Type.DepositAccount)) {
			System.out.println("Client can't close Deposit Account.");
			message = "Client can't close Deposit Account.";
		} else if (account.getType().equals(Type.CheckingAccount) || account.getType().equals(Type.SavingsAccount)) {

			Money amount = account.getBalance();
			Transfer transfer = account.transfer(amount.subtract(serviceFee), depositAccount);
			if (transfer != null) {
				message = "Success: Account is Closed!";
				account.close(this);
			} else {
				message = "Error: Try again.";
			}
		}
		payBank(depositAccount, serviceFee);
		return message;
	}

	public ArrayList<Transaction> getTransactions() throws SQLException {
		DBTransaction dbObj = new DBTransaction();
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		transactions = dbObj.retrieveTransactions(this);

		if (transactions.isEmpty()) {
			System.out.println("No transaction found associated with this Client.");
			return null;
		}
		return transactions;
	}

	public void requestLoan(Money amount) throws SQLException {

	}

	/*
	 * Pay fees/loans to bank from given account
	 */
	public void payBank(Account account, Money amount) throws SQLException {
		DBBank bankObj = new DBBank();
		Bank bank = bankObj.retrieveById("testBank");
		account.setBalance(account.getBalance().subtract(amount));
		bank.addToBalance(amount);
		bankObj.update(bank);
	}

	public String toString() {
		return person + ", email: " + email;
	}

	public void setBank() {
		DBBank objDB = new DBBank();
		try {
			this.bank = objDB.retrieveById("testBank");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
