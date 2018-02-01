package commandline;

import java.util.*;

import org.apache.commons.lang3.ArrayUtils;

import java.io.FileReader;
import java.io.IOException;

//methods of the game

public class Game {
	private Cards[] cards;
	private Players[] player;
	private Statistics statistics;
	private final int max = 40;
	int index = 0;
	int k = 0;// number of winner
	int round = 0;
	int draw=0;
	// create communal pile
	ArrayList<Cards> communalPile = new ArrayList<>();

	public Game(int numPlayer) {
		cards = new Cards[max];
		player = new Players[numPlayer];
		statistics = new Statistics(numPlayer);
		loadCards();

		// randomly select the player to start the game
		k=(int)(Math.random() * (player.length));
		String winner = player[k].getName();
		System.out.println("Randomly select the first player to start the game: "+winner);

		// count the number of player
		int playerNumber = player.length;
		// while there is more than 1 player
		while (playerNumber > 1) {
			int thRound=round+1;
			System.out.print("\r\nRound "+thRound+"!");
			showTop(player[k]);
			
		    //show everyone's number of cards
			System.out.print("Cards left:");
			for(int i=0;i<player.length;i++) {
				if(player[i].myCards.length!=0)
					System.out.print(player[i].getName()+"["+player[i].myCards.length+"] ");
			}
			System.out.println();
			
			// human round
			if (winner.equals("Player")) {

				// run compare
				rules(selectCategory());
				winner = player[k].getName();
				statistics.setPlayerWinRound(k);
				// print winner
				
				System.out.println("Winner of this round: "+ winner);
				round++;
			}

			// AI round
			else {
				int size = player[k].myCards[0].getSize();
				int speed = player[k].myCards[0].getSpeed();
				int range = player[k].myCards[0].getRange();
				int firepower = player[k].myCards[0].getFirepower();
				int cargo = player[k].myCards[0].getCargo();
				int[] number = { size, speed, range, firepower, cargo };

				// get the largest number among them
				int max = 0;
				int c = 0;
				for (int i = 0; i < 5; i++) {
					if (number[i] > max) {
						max = number[i];
						c = i;
					}
				}
				String category = "";

				// the most logical category
				if (c < 1) {
					category = "Size";
				} else if (c < 2) {
					category = "Speed";
				} else if (c < 3) {
					category = "Range";
				} else if (c < 4) {
					category = "Firepower";
				} else {
					category = "Cargo";
				}
				System.out.println(player[k].getName()+" chooses "+category);
				rules(category);
				winner = player[k].getName();
				statistics.setPlayerWinRound(k);
				// print winner
				
				System.out.println("Winner of this round: " + winner);

				
				round++;
			}
		
			// count player number
			playerNumber = 0;
			for (int i = 0; i < player.length; i++) {
				if (player[i].myCards.length > 0) {
					playerNumber++;
				}
			}
		}
		
		statistics.setGameRound(round);
		statistics.setDraw(draw);
		statistics.setWinner(player[k].getName());
		
		System.out.println("\r\nThe winner is " + player[k].getName());
		System.out.println("\r\nIt takes " + round + "rounds to finish the game");
	}

	public void showTop(Players winner) throws NullPointerException {
		// show the top card of player1
		String topCard = winner.myCards[0].getDescription() + "\r\n";
		System.out.println("\r\nThe top card of " + winner.getName() + ": " + topCard + "Size: "
				+ winner.myCards[0].getSize() + " Speed: " + winner.myCards[0].getSpeed() + " Range: "
				+ winner.myCards[0].getRange() + " Firepower: " + winner.myCards[0].getFirepower() + " Cargo: "
				+ winner.myCards[0].getCargo());
		
	}

	// select category to compare
	public String selectCategory() {
		System.out.println("\r\nWhich category do you want to choose?");
		Scanner scanner1 = new Scanner(System.in);
		String category = scanner1.next();

		// loop until have a valid category
		while (!(category.equals("Size") || category.equals("Speed") || category.equals("Range")
				|| category.equals("Firepower") || category.equals("Cargo"))) {
			System.out.println("\r\nInvalid category. Please choose the category you want again?");
			Scanner scanner2 = new Scanner(System.in);
			category = scanner2.next();
		}
		return category;
	}


	

	// choose the category to compare
	public void rules(String category) throws ArrayIndexOutOfBoundsException {

		// if choose size
		if (category.equals("Size")) {

			// boolean flag
			boolean flag = true;
			int largest = 0;
			int num = player[k].myCards[0].getSize();
			int same = 0;

			// get the name and the largest size
			outterLoop: for (int i = 0; i < player.length; i++) {
				if(player[i].myCards.length!=0) {
					// get the largest
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) {
							if (num <= player[j].myCards[0].getSize()) {
								num = player[j].myCards[0].getSize();
								same = j;
							}
						}
					}

					// then check draw
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) { 
							if (player[j].myCards[0].getSize() == num && j != same) {
								System.out.println("We have a draw here.");
								flag = false;
								draw++;
								break outterLoop;
							}
						}
					}
				}
			}

			if (flag) {
				// run compare
				for (int i = 0; i < player.length; i++) {
					if(player[i].myCards.length!=0) {
						int n = player[i].myCards[0].getSize();
						// get larger number
						if (n > largest) {
							largest = n;
							this.k = i;
						}
					}
				}
			}

			communalPile();

			if (flag) {
				moveCards();
			}
			return;
		}

		// if choose speed
		if (category.equals("Speed")) {

			// boolean flag
			boolean flag = true;
			int largest = 0;
			int num = player[k].myCards[0].getSpeed();
			int same = 0;

			// get the name and the largest speed
			outterLoop: for (int i = 0; i < player.length; i++) {
				if(player[i].myCards.length!=0) {
					// get the largest
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) {
							if (num <= player[j].myCards[0].getSpeed()) {
								num = player[j].myCards[0].getSpeed();
								same = j;
							}
						}
					}
	
					// then check draw
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) {
							if (player[j].myCards[0].getSpeed() == num && j != same) {
								System.out.println("We have a draw here.");
								flag = false;
								draw++;
								break outterLoop;
							}
						}
					}
				}
			}

			if (flag) {
				// run compare
				for (int i = 0; i < player.length; i++) {
					if(player[i].myCards.length!=0) {
						int n = player[i].myCards[0].getSpeed();
						// get larger number
						if (n > largest) {
							largest = n;
							this.k = i;
						}
					}
				}
			}

			communalPile();

			if (flag) {
				moveCards();
			}
			return;
		}

		// if choose range
		if (category.equals("Range")) {

			// boolean flag
			boolean flag = true;
			int largest = 0;
			int num = player[k].myCards[0].getRange();
			int same = 0;

			// get the name and the largest range
			outterLoop: for (int i = 0; i < player.length; i++) {
				if(player[i].myCards.length!=0) {
					// get the largest
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) {
							if (num <= player[j].myCards[0].getRange()) {
								num = player[j].myCards[0].getRange();
								same = j;
							}
						}
					}
	
					// then check draw
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) {
							if (player[j].myCards[0].getRange() == num && j != same) {
								System.out.println("We have a draw here.");
								flag = false;
								draw++;
								break outterLoop;
							}
						}
					}
				}
			}

			if (flag) {
				// run compare
				for (int i = 0; i < player.length; i++) {
					if(player[i].myCards.length!=0) {
						int n = player[i].myCards[0].getRange();
						// get larger number
						if (n > largest) {
							largest = n;
							this.k = i;
						}
					}
				}
			}

			communalPile();

			if (flag) {
				moveCards();
			}
			return;
		}

		// if choose fire power
		if (category.equals("Firepower")) {

			// boolean flag
			boolean flag = true;
			int largest = 0;
			int num = player[k].myCards[0].getFirepower();
			int same = 0;

			// get the name and the largest fire power
			outterLoop: for (int i = 0; i < player.length; i++) {
				if(player[i].myCards.length!=0) {
					// get the largest
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) {
							if (num <= player[j].myCards[0].getFirepower()) {
								num = player[j].myCards[0].getFirepower();
								same = j;
							}
						}
					}
	
					// then check draw
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) {
							if (player[j].myCards[0].getFirepower() == num && j != same) {
								System.out.println("We have a draw here.");
								flag = false;
								draw++;
								break outterLoop;
							}
						}
					}
				}
			}

			if (flag) {
				// run compare
				for (int i = 0; i < player.length; i++) {
					if(player[i].myCards.length!=0) {
						int n = player[i].myCards[0].getFirepower();
						// get larger number
						if (n > largest) {
							largest = n;
							this.k = i;
						}
					}
				}
			}

			communalPile();

			if (flag) {
				moveCards();
			}
			return;
		}

		// if choose cargo
		if (category.equals("Cargo")) {

			// boolean flag
			boolean flag = true;
			int largest = 0;
			int num = player[k].myCards[0].getCargo();
			int same = 0;

			// get the name and the largest cargo
			outterLoop: for (int i = 0; i < player.length; i++) {
				if(player[i].myCards.length!=0) {
					// get the largest
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) {
							if (num <= player[j].myCards[0].getCargo()) {
								num = player[j].myCards[0].getCargo();
								same = j;
							}
						}
					}
	
					// then check draw
					for (int j = i + 1; j < player.length; j++) {
						if(player[j].myCards.length!=0) {
							if (player[j].myCards[0].getCargo() == num && j != same) {
								System.out.println("We have a draw here.");
								flag = false;
								draw++;
								break outterLoop;
							}
						}
					}
				}
			}

			if (flag = true) {
				// run compare
				for (int i = 0; i < player.length; i++) {
					if(player[i].myCards.length!=0) {
						int n = player[i].myCards[0].getCargo();
	
						// get larger number
						if (n > largest) {
							largest = n;
							this.k = i;
						}
					}
				}
			} 

			communalPile();

			if (flag = true) {
				moveCards();
			} else {

			}
			return;
		}
	}

	public void communalPile() {
		for (int i = 0; i < player.length; i++) {
			if(player[i].myCards.length!=0) {
			// put cards in communal pile
			communalPile.add(player[i].myCards[0]);
			player[i].myCards = ArrayUtils.remove(player[i].myCards, 0);
		}
		}
	}

	// compare the chosen category
	public void compareTo(int n, int i, int largest) {

	}

	public void moveCards() {
		// move cards from communal pile to winner
		int size = communalPile.size();
		ArrayList<Cards> transfer = new ArrayList<>();
		int length = player[k].myCards.length;
		for (int l = 0; l < length; l++) {
			transfer.add(player[k].myCards[l]);
		}
		for (int j = 0; j < size; j++) {
			transfer.add(communalPile.get(0));
			//player[k].myCards = transfer.toArray(new Cards[max]);
			communalPile.remove(0);
	
		}
		player[k].myCards=new Cards[transfer.size()];
		for(int i=0;i<transfer.size();i++) {
			player[k].myCards[i]=transfer.get(i);
		}
		
		
	}

	// get cards from the text file
	public void loadCards() {
		try {
			FileReader reader = new FileReader("StarCitizenDeck.txt");
			Scanner in = new Scanner(reader);

			int index = 0;
			in.nextLine(); // no need to read fist line

			while (in.hasNextLine()) {

				// read a line from file
				String line = in.nextLine();

				// split the word with space or more times
				String[] tokens = line.split("[ ]+");
				cards[index] = new Cards(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]),
						Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]));
				index++;
			}

			shuffleCards(cards);
		}

		catch (IOException e) {
			System.err.println("Card file not found.");
		}
	}

	// shuffle cards method
	public void shuffleCards(Cards[] cards) {
		Random random = new Random();
		for (int i = cards.length - 1; i > 0; i--) {
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

		for (int i = 0; i < player.length; i++) {

			// give players names
			String playerName = "AI" + i;

			// divide the name of player and AI
			if (playerName != null && playerName.equals("AI0")) {
				playerName = "Player";
			}

			// if can not separate cards equally
			if (cardLeft != 0) {
				cardnum = (max / player.length) + 1;
				cardLeft--;
			} else {
				cardnum = (max / player.length);
			}
			player[i] = new Players(playerName, cardnum);
			setPlayerCard(player[i], cardnum);
			player[i].showMyCards();
		}
	}

	// set cards
	public void setPlayerCard(Players player, int cardNum) {
		for (int i = 0; i < cardNum; i++) {
			player.setMyCards(i, cards[index++]);
		}
	}


}
