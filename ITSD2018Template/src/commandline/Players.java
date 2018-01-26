package commandline;

public class Players {
	private Cards [] myCards;
	private String playerName;
	
	public Players(String name, int numCard) {
		playerName=name;
		myCards = new Cards[numCard];
		
	}
	
	 public void setMyCards(int index, Cards cards){
		  myCards[index] = cards;
	 }
	 public String getName() { return playerName;}
	 
	 public void showMyCards(){
		  //StringBuilder showcard = new StringBuilder();
		 String showcard=playerName+"==>";
		  for(int i = 0 ; i < myCards.length ; i++){
			 
		  showcard += " "+myCards[i].getDescription();
		  }	 
		System.out.println(showcard);
	 }
		  
		 
}
