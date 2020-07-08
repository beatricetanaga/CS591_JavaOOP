package account;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import bankATM.*;
import database.*;

public class SecurityAccount extends Account {

	public SecurityAccount(String id, Client client, Status status, Money balance, Date created) {
		super(id, client, status, balance, created);
		setType(Type.SecurityAccount);
	}

	public SecurityAccount(Client client) {
		super(client);
		setType(Type.SecurityAccount);
		updateDB();
	}
	
	public ArrayList<PurchasedStock> getPurchasedStocks() throws SQLException {
		DBPurchasedStocks dbObj = new DBPurchasedStocks();
		ArrayList<PurchasedStock> stocks = dbObj.retrieveAccountStocks(this);
		for (PurchasedStock stock : stocks ) {
			if(stock.getQuantity() <=0) {
				stocks.remove(stock);
			} else {
				stock.deleteDB(); //delete from purchased stocks (because already in Sold)
			}
		}
		return stocks;
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