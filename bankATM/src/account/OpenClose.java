package account;

import java.sql.SQLException;

import bankATM.*;

public interface OpenClose {
	
	public abstract void open(Client client) throws SQLException;

	public abstract void close(Client client) throws SQLException;
	
}
