import java.util.ArrayList;
import java.util.List;

public class Potion extends Item {
	
	private PotionType attribute;
	private int skillPoints;
	
	Potion(String name, int price, int level, PotionType attribute, int sp) {
		super(name, price, level);
		this.attribute = attribute;
		this.skillPoints = sp;
	}
	
	public PotionType getAttribute() {
		return attribute;
	}
	public int getAttributePoints() {
		return skillPoints;
	}
	
	public String toString() {
		return String.format("Potion Name: %s Price: %d Level Requirement: %d Potion Type: %s Skill Points: %d \n",
                getName(), getPrice(), getLevel(), getAttribute(), getAttributePoints());
  }

	@Override
	public Item newInstance() {
		return new Potion(this.getName(), this.getPrice(), this.getLevel(), this.getAttribute(), this.getAttributePoints());
	}

	@Override
	public List<String> printItemInfo() {
		List<String> itemInfo = new ArrayList<>();
		
		itemInfo.add("Level: " + this.getLevel());
		itemInfo.add("Attribute: " + this.getAttribute().getAttributeName());
		itemInfo.add("Boost: " + this.getAttributePoints());
		return itemInfo;
	}

}
