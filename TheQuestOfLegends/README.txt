README FILE for The Quest of Legends Assignment

Team #8 
Names of Team Members: 
Priya Kumari (BU ID: U21518661)
Beatrice Tanaga (BU ID: U90936184)

**We have chosen to build entirely from Priya's Quest assignment as we felt that this code followed OOP principles well and was more easily reusable and extendable to the Quest of Legends assignment.** 





Instructions on Compilation and Execution of the Program: 
To compile and execute this program, use the following two commands - 

javac PlayGameMain.java
java PlayGameMain





General Instructions and Information on this Implementation of the Game: 
- The game is played with 3 heroes chosen by the player. 

- The same hero cannot be selected more than once.

- The hero cannot quit while they are inside their Nexus or in the middle of a fight. We have intentionally implemented it this way so that heroes cannot "quit while they're ahead" in the middle of a fight or in the middle of a transaction as this would be a way for a user to hack the game and keep using the quit option to their advantage to upgrade their heroes and never face any losses if persistence ever becomes a part of the game in the future (if the heroes' stats are saved after they quit).							

- The hero can display the map, display their own information, teleport to a different lane, return to their Nexus, or make a move in any direction when they are prompted to make a move. 

- The hero cannot teleport inside of the lane they are currently in.

- The hero cannot teleport past a monster in the lane they are teleporting to.

- The hero returns to the Nexus of the lane they started off in, not necessarily the lane they are currently in, when they use the 'b' key. 

- If the hero chooses to return to their Nexus to buy or sell items in the market, they can make another move as the buying/selling does not count as their move. 

- Whenever a hero makes a move, a fight will be initiated between all of the heroes and all of the monsters within the neighboring cells of that hero. In the diagram below, a fight will occur between the hero and all of the heroes in the places marked with an X below and all of the monsters in the places marked with an X below: 

X X X
X H X
X X X 

- In a fight, all of the heroes fight all of the monsters. They do not get to choose who they want to fight; the fight is based on all of the creatures in the hero's area. We have implemented it this way to allow for more complicated fights rather than simple 1v1 fights and to allow heroes to help other heroes in a fight. There can be anywhere between 1-3 heroes and 1-6 monsters participating in the fight, since there are only 3 heroes total in the game and the remaining X positions could be filled by monsters. 

- When a hero makes a move and a fight is initiated, the fight is fully completed before the next hero is prompted to make their move on the board. We have implemented it this way to make the game more user-friendly so that the user is not simultaneously fighting for one hero while making moves on the board for another hero as this can get difficult to follow and is less enjoyable. 

- When a hero dies, they are respawned on the Nexus of the lane they started off in. When a monster dies, it is removed from the game.  

- New monsters are created every 8 rounds of the game, not the fight.  

- Heroes regain 10% of their HP and 10% of their mana after every round of the game, not the fight. 

- Heroes' skills boost depending on the type of tile they are in after every round of the fight in which they remain in that tile. 

- The game ends when a monster enters the Heroes' Nexus or a hero enters the Monsters' Nexus.

- All other details relating to the fight, the heroes' stats, and the market are exactly the same as the previous assignment (please refer to the previous README file submitted by Priya for The Quest assignment for clarification if needed)! 





Instructions on the Usability of Each Class: 

PlayGameMain: 
PlayGameMain is the class that contains the main method. This main method simply creates an instance of GameDriver and executes the game by calling the executeGame method on this instance.  

GameDriver: 
GameDriver is a class that contains the logic of the game. Through the methods of this class, the game is started, the player selects 3 heroes they want to play with, the map is created, and the structure that allows the game to continue until it is ended is implemented. 

HeroCreationHelper: 
HeroCreationHelper is a class that contains 15 Hero objects and 15 Skill objects, one for each hero. These heroes represent the available heroes that the player can choose from. The class contains a method to display information on all the available heroes. 

Fightable: 
Fightable is an interface that specifies specific methods used in the game to fight, including a method to attack and a method to dodge an attack. It is implemented by the LivingCreature class. 

LivingCreature: 
LivingCreature is an abstract class that represents the living creatures in the game (heroes and monsters). Its subclasses include Hero and Monster. It implements the Fightable interface since living creatures have fight behaviors. It contains attributes common to both monsters and heroes like a name, a level, HP, a map, and a row and column position on the map. It contains methods common to both monsters and heroes, like checking if their moves are valid. It also contains abstract methods to make moves and check if the creature has won which are implemented differently by heroes and monsters. 

Team: 
Team is a class that represents a team in the game. This class contains a team ArrayList that can store the heroes of a team or the monsters of a team. It also contains a method to check if that team has won by checking if any of the members of the team have reached the opponent's nexus. 

Hero: 
Hero is an abstract class that represents a hero in the game. It extends LivingCreature. Its subclasses include Paladin, Sorcerer, and Warrior. Heroes have an id, an amount of mana, skill, experience, money, and items, which distinguish them from monsters. Methods in this class include methods that allow the hero to make a move on the map, respawn, teleport, return to their nexus, buy items in the market, sell items in the market, attack monsters in a fight, dodge attacks in a fight, gain money and experience after a fight, and more. This class also has an upgradeSkills method that is implemented differently by each of its subclasses. 

Skill: 
Skill is a class that represents a hero's skill set. Each hero has a Skill object. The attributes of Skill include strength, dexterity, and agility. Each hero is favored on different skills; two of the Skill attributes increase more than the third when the hero levels up depending on the type of hero they are. 

Paladin: 
Paladin is a subclass of Hero that represents a paladin in the game. When paladins level up, their agility increases by 5% while their strength and dexterity increase by 10%. 

Sorcerer: 
Sorcerer is a subclass of Hero that represents a sorcerer in the game. When sorcerers level up, their strength increases by 5% while their dexterity and agility increase by 10%. 

Warrior: 
Warrior is a subclass of Hero that represents a warrior in the game. When warriors level up, their dexterity increases by 5% while their strength and agility increase by 10%.

QuestMap: 
QuestMap is a class that represents the map used in the game. It contains a 2D array of Tile objects as its grid. This class is used to create the map used in the game, with a specific percentage of plain, koulou, cave, and bush cells. It also contains methods to create the lanes in the map set the starting position of the heroes in their nexus and to print the map with all of the cells' borders and border labels. 

Lane: 
Lane is a class that represents a lane in the map. It contains a 2D array of Tile objects as its grid. The grid of a QuestMap consists of the grids of the lanes that it is composed of.  

Tile: 
Tile is an abstract class that represents a single cell of the QuestMap. It contains a label. Its subclasses include NexusTile, BlockedTile, BushTile, CaveTile, KoulouTile, and PlainTile. It also contains methods for heroes and monsters to enter and exit a tile which are called in its subclasses. 

BlockedTile: 
BlockedTile is a subclass of Tile that represents tiles on the map where the player cannot move to. This type of tile makes up two of the columns of the map. Its label is "X X X" and its borderLabel is "I". 

BushTile: 
BushTile is a subclass of Tile that represents bush cells. Its enter method calls the superclass' enter method and additionally increases the dexterity of the hero inside it by 10%. Its borderLabel is "B". 

CaveTile: 
CaveTile is a subclass of Tile that represents cave cells. Its enter method calls the superclass' enter method and additionally increases the agility of the hero inside it by 10%. Its borderLabel is "C". 

KoulouTile: 
KoulouTile is a subclass of Tile that represents koulou cells. Its enter method calls the superclass' enter method and additionally increases the strength of the hero inside it by 10%. Its borderLabel is "K". 

PlainTile: 
PlainTile is a subclass of Tile that represents plain cells. Its borderLabel is "P". 

NexusTile:
NexusTile is a subclass of Tile that represents nexus cells. Its subclasses include HeroNexusTile and MonsterNexusTile. It contains a method common to both these classes that displays a message when the game has ended, since the game ends when either of these tiles are entered by their opponent. 

HeroNexusTile: 
HeroNexusTile is a subclass of NexusTile that represents the heroes' nexus cells. It contains a market and its enter method allows the heroes to enter the market and perform multiple buying and selling transactions. Its enter method for monsters signifies that the monsters have won and calls the superclass' method to display a message that the game has ended. 

MonsterNexusTile: 
MonsterNexusTile is a subclass of NexusTile that represents the monsters' nexus cells. Its enter method for heroes signifies that the heroes have won and calls the superclass' method to display a message that the game has ended. 

Market: 
Market is a class that represents the market contained in the HeroNexusTiles where the hero can purchase items to use against monsters in a fight. Markets contain infinite supplies of all of the items in the game, including weapons, armors, potions, ice spells, fire spells, and lightning spells. This class contains a method to display all the items available in the market. 

ItemConstants: 
ItemConstants is a class that contains all of the static items that are available in the market. It is called ItemConstants to emphasize the fact that the items stay constant throughout the game; their stats do not change.

Sellable: 
Sellable is an interface implemented by Item that enforces the ability of items to be bought and sold. Its methods implemented by the items are a method to get and set the price of an item as setting a price indicates that the item can be bought and sold.  

Item: 
Item is a class that represents an item used in the game. Its subclasses include Armor, Potion, Spell, and Weapon. It implements the Sellable interface as items can be bought and sold. The attributes common to all items include an ID, a name, a price, and a min hero level. Each item has a method to display information about the item and a method that is called when the item is used in a fight. 

Armor: 
Armor is a subclass of Item that represents an armor in the game. In addition to the attributes of an item, it includes an integer defense variable that protects the hero wearing it. The armor reduces the HP reduction invoked by the monster by 0.025*defense. 

Potion: 
Potion is a subclass of Item that represents a potion in the game. In addition to the attributes of an item, it includes an integer incrementStat variable. When a hero consumes a potion, their strength increases by the incrementStat.

Spell: 
Spell is an abstract subclass of Item that represents a spell in the game. Its subclasses include IceSpell, FireSpell, and LightningSpell. In addition to the attributes of an item, it includes an integer manaDeduction variable and an integer damage variable. When a hero casts a spell, a certain amount of damage is inflicted on the monster, and a certain amount of the hero's mana is deducted. This class also contains an abstract method called deteriorateSkill which is implemented differently in each of its subclasses. 

IceSpell: 
IceSpell is a subclass of Spell that represents an ice spell. When a hero casts it on a monster, it reduces the damage level of the monster by 10% for the remainder of the game. 

FireSpell: 
FireSpell is a subclass of Spell that represents a fire spell. When a hero casts it on a monster, it reduces the defense level of the monster by 10% for the remainder of the game. 

LightningSpell:
LightningSpell is a subclass of Spell that represents a lightning spell. When a hero casts it on a monster, it reduces the probability that the monster will dodge the hero's attack by 10% for the remainder of the game. 

Weapon: 
Weapon is a subclass of item that represents an weapon in the game. In addition to the attributes of an item, it includes an integer damage variable and an integer numHands variable, which represents the number of hands required by the hero to hold the weapon. The hero can only hold one weapon at a time. When a hero uses a weapon against a monster, the damage of the weapon is added to the HP reduction caused by hero's attack on the monster. 

Fight: 
Fight is a class that represents a fight that takes place in the game. A fight occurs when a hero makes a move and ends up in a cell that neighbors a monster. Fights have a set of heroes, a set of monsters, and rounds. The heroes of the fight are all the heroes in the neighboring cells of the hero that just made a move. The monsters of a fight are all the monsters in the neighboring cells of the hero that just made a move. This class contains a method that executes an entire fight. The user is prompted to enter an integer corresponding to the ID of the item they would like each hero to use, or 0 to perform a regular attack. The fight will end if all of the heroes die (each hero has an HP of 0) or all of the monsters die. When a monster dies, it is removed from the game and the map. When a hero dies, they are respawned to their nexus with their HP reset to its initial amount. Heroes that don't die in the fight gain money and experience.  

MonsterCreationHelper:
MonsterCreationHelper is a class that contains 15 monsters (3 monsters of each level from levels 1 through 5) that can appear in fights. This class also contains an array for each set of monsters of the same level that is referenced when the game is set up. 

Monster: 
Monster is an abstract class that represents a monster in the game. It extends LivingCreature. Its subclasses include Dragon, Exoskeleton, and Spirit. Monsters have an amount of damage, an amount of defense, and a probability of dodging an attack. Heroes encounter monsters in a fight. This class contains methods to check if a monster has dodged an attack, to attack a hero, and to check if the monster has died based on their HP. Unlike heroes, monsters do not level up. When a hero levels up, they fight monsters of higher levels. 

Dragon: 
Dragon is a subclass of Monster that represents a dragon in the game. Dragons have a higher damage than other types of monsters. 

Exoskeleton: 
Exoskeleton is a subclass of Monster that represents an exoskeleton in the game. Exoskeletons have a higher defense level than other types of monsters. 

Spirit:
Spirit is a subclass of Monster that represents a spirit in the game. Spirits have a higher probability of dodging a hero's attack than other types of monsters. 

UtilityHelper:
This is a class only used to ensure that the user inputs an integer when the program asks them to. For example, it is called when the user is inputing the number of heroes they want to play with, the ID of the hero they want to play with, the ID of the item they want a hero to buy, the ID of the item they want the hero to use when fighting a monster, and more. 
























