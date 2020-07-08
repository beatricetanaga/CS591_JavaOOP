package bankATM;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

import account.*;
import bankATM.Currency;
import database.*;
import transaction.*;

/*
 *  Note: Loan amount is negative.
 */
public class Loan implements Interest {

	private String id;
	private Account account;
	private Money amount;
	private Date requested;
	private Date approved;
	private Date lastPay;
	private float interest;
	private Status status;

	// Constructor for loan
	public Loan(String id, Account account, Money amount, Date requested, Date lastPay, float interest, Status status) {
		this.id = (id);
		this.account = (account);
		this.amount = (amount);
		this.interest = (interest);
		this.requested= (requested);
		this.lastPay =(lastPay);
		this.setStatus(status);
	}

	// Constructing and Creating new Loan (Adding to DBs)
	public Loan(Account account, Money amount, Date requested, float interest) {
		this(getNewId(), account, amount, getCurrentDate(), getCurrentDate(), interest, Status.Requested);
		if (amount.getValue() > 1000) { // Approve loans without request if amount < 1000
			this.setStatus(Status.Approved);
			this.setApproved(requested);
		}
		addToDB(); // request from Manager
	}

	public void addToDB() {
		DBLoans dbObj = new DBLoans();
		try {
			dbObj.create(this);
		} catch (SQLException e) {
			System.out.println("Couldn't add this Loan to DB.");
			e.printStackTrace();
		}
	}

	public void updateDB() {
		DBLoans dbObj = new DBLoans();
		try {
			dbObj.update(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteDB() {
		DBLoans dbObj = new DBLoans();
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

	public void setId(String id) {
		this.id = id;
	}

	private static String getNewId() {
		return UUID.randomUUID().toString();
	}

	public Account getAccount() {
		if(account == null) {
			System.out.print(" Account is null ");
			
		}
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/*
	 * Sets fixed Loans interest. So, in future if bank raise Loans Interest - the
	 * Client will still have his initial loans interest rate.
	 */
	public void setInterest(float interest) {
		DBBank dbObj = new DBBank();
		Bank bank = null;
		try {
			bank = dbObj.retrieveById("testBank");
			this.interest = bank.getLoansInterest();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public float getInterest() {
		return this.interest;
	}

	public Money getAmount() {
		return this.amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
		updateDB();
	}

	public Date getRequested() {
		return requested;
	}

	public void setRequested(Date requested) {
		this.requested = requested;
	}

	private static Date getCurrentDate() {
		DBBank dbObj = new DBBank();
		Bank bank = null;
		Date now = null;
		try {
			bank = dbObj.retrieveById("testBank");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (bank != null) {
			now = bank.getCurrentDate();
		} else {
			now = new Date(System.currentTimeMillis());
		}
		return now;
	}

	public Date getApproved() {
		return approved;
	}

	public void setApproved(Date approved) {
		this.approved = approved;
		updateDB();
		setStatus(Status.Approved);
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
		updateDB();
	}

	/*
	 * Calculate amount Due with interest included.
	 */
	@Override
	public void applyInterest() {
		Date today = getCurrentDate();

		int numTimesInterestToApply = 0;

		int currYear = today.getYear();
		int currMonth = today.getMonth();
		int lastPayYear = lastPay.getYear();
		int lastPayMonth = lastPay.getMonth();

		if (currYear < lastPayYear) {
			return;
		} else if (currMonth < lastPayMonth) {
			return;
		}

		while (!(currYear == lastPayYear && currMonth == lastPayMonth)) {
			if (lastPayYear == 12) {
				lastPayYear++;
				lastPayMonth = 1;
			} else {
				lastPayMonth++;
			}
			numTimesInterestToApply++;
		}
		float totalDue = amount.getValue();

		while (numTimesInterestToApply != 0) {
			totalDue = totalDue * interest;
		}
		setAmount(new Money(totalDue, Currency.USD));
	}

	/*
	 * Pay loan from given account to Bank.
	 */
	public boolean payLoan(Money amountToPay) throws SQLException {
		if (amountToPay.getValue() > 0 && amountToPay.getValue() <= getAmount().getValue()) {
			this.setAmount(getAmount().subtract(amountToPay));
			this.setLastPay(getCurrentDate());
			Transaction loanPayment = new LoanPayment(account, amountToPay, Status.Completed);
			updateDB();
			return true;
		}
		return false;
	}

	public Date getLastPay() {
		return lastPay;
	}

	public void setLastPay(Date lastPay) {
		this.lastPay = lastPay;
	}

}
