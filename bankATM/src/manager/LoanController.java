package manager;

import bankATM.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

import account.*;

public interface LoanController {
	
	public abstract ArrayList<Loan> getLoans();

	public abstract boolean approveLoan(Loan loan);

	public abstract Loan issueLoan(Loan loan);
	
	public abstract void collectLoanPayment(Date date) throws SQLException;
}
