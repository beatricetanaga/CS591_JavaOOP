package manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import account.*;
import bankATM.*;
import database.*;

public class Manager implements Settings, LoanController, StockController, ServiceFeeController, InterestController {

	private String id;
	private Person person;
	private String email;
	private String password;
	private Bank bank;

	public Manager(String id, Person person, String email, String password) throws SQLException {
		this.setId(id);
		this.setPerson(person);
		this.setEmail(email);
		this.setPassword(password);
		setBank();
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

	@Override
	public void setWithdrawServiceFee(Money fee) {
		bank.setWithdrawFee(fee);
		bank.updateDB();
	}

	@Override
	public void setTranserServiceFee(Money fee) {
		bank.setTransferFee(fee);
		bank.updateDB();
	}

	@Override
	public void setDepositServiceFee(Money fee) {
		bank.setDepositFee(fee);
		bank.updateDB();
	}

	@Override
	public void setCloseAccountFee(Money fee) {
		bank.setCloseAccountFee(fee);
		bank.updateDB();
	}

	@Override
	public void setOpenAccountFee(Money fee) {
		bank.setOpenAccountFee(fee);
		bank.updateDB();
	}

	@Override
	public void addStock(Stock stock) {
		if (!stock.getName().isEmpty() && stock.getQuantity() > 0) {
			stock.addToDB();
		} else {
			System.out.println("Invalid Stock created.");
		}
	}

	@Override
	public void setStatus(Stock stock, Status status) {
		stock.setStatus(Status.Closed);
		stock.updateDB();
	}

	@Override
	public void setPrice(Stock stock, Money price) {
		stock.setPrice(price);
		stock.updateDB();
	}

	@Override
	public void setQuantity(Stock stock, int quantity) {
		stock.setQuantity(quantity);
		stock.updateDB();
	}

	@Override
	public void setSavingsInterest(float interest) {
		// TODO Auto-generated method stub
		bank.updateDB();
	}

	@Override
	public void setLoanInterest(float interest) {
		// TODO Auto-generated method stub
		bank.updateDB();
	}

	/*
	 * Pay interest rate to all Savings Account.
	 */
	@Override
	public void payAllInterests() {
		float savingsInterest = bank.getSavingsInterest();
		Type type = Type.SavingsAccount;
		ArrayList<Account> accounts = this.getAccountsOfType(type);
		for (Account account : accounts) {
			((SavingsAccount) account).applyInterest();
		}
	}

	@Override
	public boolean approveLoan(Loan loan) {
		loan.setStatus(Status.Approved);
		loan.setApproved(bank.getCurrentDate());
		return true;
	}

	@Override
	public Loan issueLoan(Loan loan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Loan> getLoans() {
		ArrayList<Loan> loans = null;
		DBLoans dbObj = new DBLoans();
		try {
			loans = dbObj.retrieveLoans();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loans;
	}
	
	public ArrayList<Loan> getRequestedLoans() {
		ArrayList<Loan> loans = null;
		DBLoans dbObj = new DBLoans();
		try {
			loans = dbObj.retrieveRequestedLoans();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loans;
	}

	/*
	 * Auto Collect Payments for loans that didn't pay last 30 days. Auto-Collect
	 * from Deposit Account.
	 */
	@Override
	public void collectLoanPayment(Date date) throws SQLException {
		DBLoans dbObj = new DBLoans();
		ArrayList<Loan> loans = null;
		try {
			loans = dbObj.retrieveApprovedLoans();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int currYear = date.getYear();
		int currMonth = date.getMonth();
		if (currMonth == 1) {
			currYear--;
		}
		Money totalAmountDue = new Money(0, Currency.USD);
		date.setMonth(currMonth - 1);
		Date dueDate = new Date(currYear, currMonth, date.getDate());

		for (Loan loan : loans) {
			if (loan.getLastPay().before(dueDate)) { // Collect if last payment was more than 30 days ago
				Client client = loan.getAccount().getClient();

				
				
				Money amountDue = new Money(loan.getAmount().getValue() * (float) 0.05, Currency.USD);
				// if deposit balance <= 0, then apply more interest
				if (client.getDepositAccount().getBalance().getValue() <= 0) {
					loan.setAmount(new Money(loan.getAmount().getValue() * (float) 1.05, Currency.USD));
				} // if 5% of loan <= deposit balance, then apply more interest and empty Deposit
					// Account
				else if (client.getDepositAccount().getBalance().getValue() < amountDue.getValue()) {
					amountDue = client.getDepositAccount().getBalance();
					loan.payLoan(amountDue);
					loan.setAmount(new Money(loan.getAmount().getValue() * (float) 1.05, Currency.USD));
				} // collect 5% of loan due, and apply more interest
				else {
					loan.payLoan(amountDue);
					loan.setAmount(new Money(loan.getAmount().getValue() * (float) 1.05, Currency.USD));
				}

				new Money(loan.getAmount().getValue(), Currency.USD);

				client.payBank(client.getDepositAccount(), amountDue);
				loan.payLoan(amountDue);
				
				totalAmountDue.add(amountDue);
				
			}
		}
		/*
		 * if client didnt make month payment: collect payment else
		 */
		bank.updateDB();
	}

	public String toString() {
		return "Manager: " + person;
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

	@Override
	public void setCurrentDate(Date date) throws SQLException {
		bank.setCurrentDate(date);
		bank.updateDB();
	}

	/*
	 * Returns List of Specified Type Accounts
	 * Checking/Savings/Deposit/Security/Security/Loans)
	 */
	public ArrayList<Client> getClients() {
		DBClient dbObj = new DBClient();
		ArrayList<Client> clients = null;
		try {
			clients = dbObj.retrieveClients();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (clients.isEmpty()) {
			System.out.println("No clients accounts found associated with this Client.");
			return null;
		}
		return clients;
	}
	
	/*
	 * Returns List of Specified Type Accounts
	 * Checking/Savings/Deposit/Security/Security/Loans)
	 */
	public ArrayList<Account> getAccountsOfType(Type type) {
		DBAccount dbObj = new DBAccount();
		ArrayList<Account> accounts = new ArrayList<Account>();
		try {
			for (Account account : dbObj.retrieveAccounts()) {
				if (account.getType().equals(type)) {
					accounts.add(account);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (accounts.isEmpty()) {
			System.out.println("No " + type + " accounts found associated with this Client.");
			return null;
		}
		return accounts;
	}

}
