
public class Exoskeleton extends Monster{

	public Exoskeleton(String name, int level, int damage, int defense, int dodge) {
		super(name, level, damage, defense, dodge);
	}

	@Override
	public Exoskeleton newInstance() {
		return new Exoskeleton(this.getName(), this.getLevel(), this.getDamage(), this.getDefense(), this.getDodgeChance());
	}
}
