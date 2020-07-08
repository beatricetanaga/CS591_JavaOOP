// Interface implemented by LivingCreature that specifies some methods they must implement to fight 
public interface Fightable {
	public void attack(Hero hero); 
	public void attack(Monster monster); 
	public boolean dodgedAttack(); 
}
