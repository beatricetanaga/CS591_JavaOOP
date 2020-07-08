import java.util.List;

public abstract class Item {
	
	private final String name;
	private final int price;
	private final int level;
	
	public Item(String name, int price, int level) {
		this.name = name;
		this.price = price;
		this.level = level;
	}
	
	public final String getName() {
		return name;
	}
	
	public final int getPrice() {
		return price;
	}
	
	public final int getLevel() {
		return level;
	}
	
	public abstract String toString();

	public abstract Item newInstance();
	
	public abstract List<String> printItemInfo();
}
