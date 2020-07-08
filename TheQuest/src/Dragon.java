
public class Dragon extends Monster{

	public Dragon(String name, int level, int damage, int defense, int dodge) {
		super(name, level, damage, defense, dodge);
	}

	@Override
	public Dragon newInstance() {
		return new Dragon(this.getName(), this.getLevel(), this.getDamage(), this.getDefense(), this.getDodgeChance());
	}
}
