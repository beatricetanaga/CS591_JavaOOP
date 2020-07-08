
public class Item implements Sellable {
	private int id; 
	private String name;
	private int price; 
	private int minHeroLevel;
	
	// no-arg constructor 
	public Item() {
		
	}
	
	// constructor 
	public Item(int id, String name, int price, int minHeroLevel) {
		this.id = id; 
		this.name = name; 
		this.price = price;
		this.minHeroLevel = minHeroLevel; 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMinHeroLevel() {
		return minHeroLevel;
	}

	public void setMinHeroLevel(int minHeroLevel) {
		this.minHeroLevel = minHeroLevel;
	}
	
	// method overridden by the subclass, called when item is displayed in a fight 
	public void display() {
		System.out.println("Name: " + name); 
	}
	
	// method overridden by the subclasses, called when item is used in a fight
	public void use(Hero hero, Monster monster) {
		System.out.println("This item is being used!"); 
	}

}
