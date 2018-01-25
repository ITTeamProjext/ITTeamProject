package commandline;
import java.util.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Game {
	
	private Cards [] cards;
	
	public Game() {
		
		cards=new Cards[40];
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
		
	}
	
}
