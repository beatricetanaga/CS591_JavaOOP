
public class Skill {
	private double strength;
	private double dexterity;
	private double agility;
	
	public Skill(double strength, double dexterity, double agility) {
		this.strength = strength;
		this.dexterity = dexterity;
		this.agility = agility;
	}

	public double getStrength() {
		return strength;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	public double getDexterity() {
		return dexterity;
	}

	public void setDexterity(double dexterity) {
		this.dexterity = dexterity;
	}

	public double getAgility() {
		return agility;
	}

	public void setAgility(double agility) {
		this.agility = agility;
	}
	
	// method that prints strength, dexterity, and agility (all the attributes of a hero's skillset) 
	public void printSkillSet() {
		System.out.println((int)strength + "\t\t " + (int)dexterity + "\t\t" + (int)agility);
	}

}
