package bankATM;

public enum Currency {

	USD("USD", (float) 1.0000), 
	EUR("EUR", (float) 0.87),
	GBP("GBP", (float) 0.75), 
	JPY("JPY", (float) 102.68);

	public String currency;
	public float rate;

	Currency(String currency, float rate) {
		this.currency = currency;
		this.rate = rate;
	}

	public float getRate() {
		return rate;
	}

	public String toString() {
		return currency;
	}
}
