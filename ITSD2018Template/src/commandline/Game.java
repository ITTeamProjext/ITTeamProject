package commandline;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Game {
	
	private Cards [] cards;
	private Players [] player;
	private final int max = 40;
	int index=0;
	public Game(int numPlayer) {
		
		cards=new Cards[max];
		player=new Players[numPlayer];
		loadCards();
	}
	public void loadCards() {
		try {
			FileReader reader = new FileReader("StarCitizenDeck.txt");
			Scanner in = new Scanner(reader);
			
			int index=0;
			in.nextLine(); //no need to read fist line
			while(in.hasNextLine()) {
				// read a line from file
				
				String line=in.nextLine();
				// split the word with space or more times
				String [] tokens = line.split("[ ]+");
				
				cards[index]=new Cards(tokens[0],Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]),Integer.parseInt(tokens[4]),Integer.parseInt(tokens[5]));
				index++;
			
			}		
			shuffleCards(cards);
			}
			

		catch (IOException e) {
			System.err.println("Card file not found.");
			
		}

		
	}
	public void shuffleCards(Cards [] cards) {
		Random random = new Random();
	
		for (int i = cards.length - 1; i > 0; i--)
	    {
	      int index = random.nextInt(i + 1);
	      // Simple swap
	      Cards temp = cards[index];
	      cards[index] = cards[i];
	      cards[i] = temp;
	    }
		divideCards();
	}
	
	public void divideCards() {
		int cardnum = max / player.length;
		int cardLeft = max % player.length;
		System.out.println("每人平均"+cardnum+",多出"+cardLeft+"張給前幾位!");
		for(int i=0;i<player.length;i++) {
			String playerName = "Player"+(i+1);
			if(cardLeft != 0){
			    cardnum = (max / player.length) + 1;
			    cardLeft--;
			   }else{
			    cardnum = (max / player.length);
			   }
			   player[i] = new Players(playerName, cardnum);
			   setPlayerCard(player[i], cardnum);
				    player[i].showMyCards();
		}
		  
		  
	}
	 public void setPlayerCard(Players player, int cardNum){
		  for(int i = 0 ; i < cardNum ; i++){
		   player.setMyCards(i, cards[index++]);

		   }
		  
	 }
	
}
