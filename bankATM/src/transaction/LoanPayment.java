package transaction;

import java.sql.Date;

import account.Account;
import bankATM.*;

public class LoanPayment extends Transaction {

	private Loan loan;

	public LoanPayment(String id, Account account, Money amount, Date created, Status status) {
		super(id, account, amount, created, status);
		setType(getType());
	}
	
//	Transaction(Account account, Money amount, Status status) {
	public LoanPayment(Account account, Money amount, Status status) {
		super(account, amount, status);
		setType(getType());
		addToDB();
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Type getType() {
		return Type.LoanPayment;
	}

	@Override
	public Money getServiceFee() {
		// TODO Auto-generated method stub
		return new Money(0, Currency.USD);
	}

}
