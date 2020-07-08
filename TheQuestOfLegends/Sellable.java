// Interface implemented by Item that enforces their ability to be bought and sold by defining a price
public interface Sellable {
	public int getPrice(); 
	public void setPrice(int price); 
}
