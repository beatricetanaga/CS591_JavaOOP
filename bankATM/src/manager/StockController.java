package manager;

import bankATM.Money;
import bankATM.Status;
import bankATM.Stock;

public interface StockController {
	
	public abstract void addStock(Stock stock);
	
	public abstract void setStatus(Stock stock, Status status);
	
	public abstract void setPrice(Stock stock, Money price);
	
	public abstract void setQuantity(Stock stock, int quantity);
	
}
