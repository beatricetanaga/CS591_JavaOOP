import java.util.*; 

public class HeroCreationHelper {
	//strength, dexterity, agility 
	
	// skill objects, one created for each hero 
	Skill skillSet1 = new Skill(80, 100, 160);
	Skill skillSet2 = new Skill(100, 120, 170); 
	Skill skillSet3 = new Skill(40, 110, 140); 
	Skill skillSet4 = new Skill(55, 110, 200); 
	Skill skillSet5 = new Skill(75, 130, 150); 
	
	Skill skillSet6 = new Skill(95, 180, 190); 
	Skill skillSet7 = new Skill(85, 150, 170); 
	Skill skillSet8 = new Skill(120, 160, 130); 
	Skill skillSet9 = new Skill(130, 190, 180); 
	Skill skillSet10 = new Skill(60, 150, 150); 
	
	Skill skillSet11 = new Skill(80, 130, 120); 
	Skill skillSet12 = new Skill(60, 150, 100); 
	Skill skillSet13 = new Skill(45, 120, 110); 
	Skill skillSet14 = new Skill(75, 130, 100); 
	Skill skillSet15 = new Skill(100, 180, 130); 
	
	
	//adds a single starting weapon to an empty set of weapons for each hero 
	public HashSet<Item> addItem(Item item) {
		HashSet<Item> itemSet = new HashSet<>();
		itemSet.add(item); 
		return itemSet; 
	}

	//super(id, name, level, hp, mana, skill, money, experience); 
	// all of the heroes that the player can choose from 
	Warrior yudhishtir = new Warrior(1, "Yudhishtir ", 1, 100, 350, skillSet1, 550, 6);
	Warrior bhim = new Warrior(2, "Bhim       ", 1, 100, 300, skillSet2, 600, 2);
	Warrior arjun = new Warrior(3, "Arjun      ", 1, 100, 250, skillSet3, 400, 8); 
	Warrior nakul = new Warrior(4, "Nakul      ", 1, 100, 200, skillSet4, 450, 2); 
	Warrior sahadev = new Warrior(5, "Sahadev    ", 1, 100, 200, skillSet5, 700, 4); 

	Paladin bhishma = new Paladin(6, "Bhishma    ", 1, 100, 320, skillSet6, 500, 2); 
	Paladin dronacharya = new Paladin(7, "Dronacharya", 1, 100, 340, skillSet7, 400, 6); 
	Paladin ashwathama = new Paladin(8, "Ashwathama ", 1, 100, 270, skillSet8, 350, 2); 
	Paladin karna = new Paladin(9, "Karna      ", 1, 100, 240, skillSet9, 580, 4); 
	Paladin parshuram = new Paladin(10, "Parshuram  ", 1, 100, 220, skillSet10, 600, 4); 
	
	Sorcerer krishna = new Sorcerer(11, "Krishna    ", 1, 100, 340, skillSet11, 420, 2); 
	Sorcerer hanuman = new Sorcerer(12, "Hanuman    ", 1, 100, 350, skillSet12, 540, 4); 
	Sorcerer ram = new Sorcerer(13, "Ram        ", 1, 100, 260, skillSet13, 500, 2); 
	Sorcerer shiva = new Sorcerer(14, "Shiva      ", 1, 100, 290, skillSet14, 590, 8); 
	Sorcerer ganesh = new Sorcerer(15, "Ganesh     ", 1, 100, 270, skillSet15, 550, 2); 
	
	// ArrayLists for each type of hero 
	private ArrayList<Warrior> warriors = new ArrayList<>();  
	private ArrayList<Paladin> paladins = new ArrayList<>(); 
	private ArrayList<Sorcerer> sorcerers = new ArrayList<>(); 
	private HashSet<Hero> allHeroes = new HashSet<>();  // ArrayList with all the heroes 

	// method that adds all of the heroes created to their respective ArrayLists
	public void createHeroes() {
		addWarriors(); 
		addPaladins(); 
		addSorcerers();
	}
	
	public void addWarriors() {
		warriors.add(yudhishtir); 
		warriors.add(bhim);
		warriors.add(arjun);
		warriors.add(nakul);
		warriors.add(sahadev);
		
		for (Warrior w : warriors) {
			allHeroes.add(w); 
		}
	}
	
	public void addPaladins() {
		paladins.add(bhishma); 
		paladins.add(dronacharya);
		paladins.add(ashwathama);
		paladins.add(karna);
		paladins.add(parshuram);
		
		for (Paladin p : paladins) {
			allHeroes.add(p); 
		}
	}
	
	public void addSorcerers() {
		sorcerers.add(krishna); 
		sorcerers.add(hanuman);
		sorcerers.add(ram);
		sorcerers.add(shiva);
		sorcerers.add(ganesh);
		
		for (Sorcerer s : sorcerers) {
			allHeroes.add(s); 
		}
	}
	
	// method to display all of the available heroes at the beginning of a game
	public void displayHeroes() {
		System.out.println("C H O O S E  Y O U R  H E R O E S ! "); 
		
		System.out.println("Available Warriors: "); 
		System.out.println("ID\t Name\t\tHealth Power\t Start Money \tMana\t Experience\t Strength\t Dexterity\tAgility\t");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------"); 
		for (Warrior w : warriors) {
			System.out.print(w.getId() + "\t" + w.getName() + "\t" + (int)w.getHp() + "\t\t "  + (int)w.getMoney() + "\t\t" + (int)w.getMana() + "\t " + (int)w.getExperience() + "\t\t ");
			w.getSkill().printSkillSet(); 
		}
		System.out.println();
		
		System.out.println("Available Paladins: "); 
		System.out.println("ID\t Name\t\tHealth Power\t Start Money \tMana\t Experience\t Strength\t Dexterity\tAgility\t");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------"); 
		for (Paladin p : paladins) {
			System.out.print(p.getId() + "\t" + p.getName() + "\t" + (int)p.getHp() + "\t\t "  + (int)p.getMoney() + "\t\t" + (int)p.getMana() + "\t " + (int)p.getExperience() + "\t\t ");
			p.getSkill().printSkillSet(); 
		}
		System.out.println();
		
		System.out.println("Available Sorcerers: "); 
		System.out.println("ID\t Name\t\tHealth Power\t Start Money \tMana\t Experience\t Strength\t Dexterity\tAgility\t");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------"); 
		for (Sorcerer s : sorcerers) {
			System.out.print(s.getId() + "\t" + s.getName() + "\t" + (int)s.getHp() + "\t\t "  + (int)s.getMoney() + "\t\t" + (int)s.getMana() + "\t " + (int)s.getExperience() + "\t\t ");
			s.getSkill().printSkillSet(); 
		}
		System.out.println(); 
	}
	
	public ArrayList<Warrior> getWarriors() {
		return warriors;
	}

	public void setWarriors(ArrayList<Warrior> warriors) {
		this.warriors = warriors;
	}

	public ArrayList<Paladin> getPaladins() {
		return paladins;
	}

	public void setPaladins(ArrayList<Paladin> paladins) {
		this.paladins = paladins;
	}

	public ArrayList<Sorcerer> getSorcerers() {
		return sorcerers;
	}

	public void setSorcerers(ArrayList<Sorcerer> sorcerers) {
		this.sorcerers = sorcerers;
	}
	
	public HashSet<Hero> getAllHeroes() {
		return allHeroes;
	}

	public void setAllHeroes(HashSet<Hero> allHeroes) {
		this.allHeroes = allHeroes;
	}
}
