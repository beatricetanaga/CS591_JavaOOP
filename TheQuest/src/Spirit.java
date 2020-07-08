
public class Spirit extends Monster{

	public Spirit(String name, int level, int damage, int defense, int dodge) {
		super(name, level, damage, defense, dodge);
	}

	@Override
	public Spirit newInstance() {
		return new Spirit(this.getName(), this.getLevel(), this.getDamage(), this.getDefense(), this.getDodgeChance());
	}
}
