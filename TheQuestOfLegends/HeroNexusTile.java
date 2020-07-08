import java.util.Scanner;
// A class that represents the tiles of the Heroes' Nexus 
public class HeroNexusTile extends NexusTile {
	private Market market = new Market();

	public boolean enter(Hero hero, Scanner scan) {
		heroLabel = hero.getHeroLabel();
		System.out.println("Would you like to view the market?");
		String enterMarket = scan.nextLine();

		while (enterMarket.equals("YES") || enterMarket.equals("yes")) {
			market.displayMarket();
			System.out.println();
			System.out.println(hero.getHeroLabel() + " " + hero.getName().trim() + "'s Information:");
			hero.showInformation();
			System.out.println();
			System.out.print("To buy an item for " + hero.getHeroLabel() + " " + hero.getName().trim()
					+ ", enter BUY/buy. To sell an item, enter SELL/sell. To exit, enter anything else: ");
			String action = scan.nextLine();

			if (action.equals("BUY") || action.equals("buy")) {
				System.out.print("Please enter the ID of the item you would like to buy: ");
				int itemID = hero.getMarketItemID(market, scan);
				hero.buyItem(market, itemID);
				System.out.print("Would you like to complete any other transactions for " + hero.getHeroLabel() + " "
						+ hero.getName().trim() + "? Enter YES/yes to do so, or anything else if you are finished: ");
				enterMarket = scan.nextLine();
			}

			else if (action.equals("SELL") || action.equals("sell")) {
				System.out.print("Please enter the ID of the item you would like to sell: ");
				int itemID = hero.getHeroItemID(scan);
				hero.sellItem(market, itemID);
				System.out.print("Would you like to complete any other transactions for " + hero.getHeroLabel() + " "
						+ hero.getName().trim() + "? Enter YES/yes to do so, or anything else if you are finished: ");
				enterMarket = scan.nextLine();
			} else {
				break;
			}
		}
		return true;
	}

	public void exit(Hero hero) {
		heroLabel = "  ";
	}
	
	public boolean enter(Monster monster) {
		monsterLabel = monster.getMonsterLabel(); 
		System.out.println("O H  N O ! The monsters have won. Better luck next time!"); 
		endGameMessage(); 
		return true; 
	}

}
