package bankATM;

public class Money implements Comparable<Money> {

	private float value; // USD
	private Currency currency;

	public Money(float value, Currency currency) {
		this.setValue(value);
		this.setCurrency(currency);
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Money add(Money other) {
		return new Money(this.getValue() + other.getValue(), Currency.USD);
	}
	
	public Money subtract(Money other) {
		return new Money(this.getValue() - other.getValue(), Currency.USD);
	}
	
	public float getConversion(Currency otherCurrency) {
		return value * otherCurrency.getRate();
	}

	public String toString() {
		float total = value * currency.getRate();
		return "" + String.format("%.02f", total) + " " + currency;
	}

	@Override
	public int compareTo(Money o) {
		// TODO Auto-generated method stub
		return (int) (this.getValue() - o.getValue());
	}
}
