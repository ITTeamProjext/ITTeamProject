package commandline;

import java.util.*;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that
	 * they want to run in command line mode. The contents of args[0] is whether we
	 * should write game logs to a file.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		boolean writeGameLogsToFile = false; // Should we write game logs to file?
		if (args[0].equalsIgnoreCase("true"))
			writeGameLogsToFile = true; // Command line selection

		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application

		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {
			// add players
			System.out.println("How many AI players do you want to play with? (Max:4)");
			Scanner scanner1 = new Scanner(System.in);
			int numPlayer = scanner1.nextInt() + 1; // AIs and player

			// re-enter the number until it is between 0 and 5
			while (numPlayer > 5 || numPlayer < 2) {
				System.out.println("The AI players number should be more than 0 and less than 5, please enter again: ");
				Scanner scanner2 = new Scanner(System.in);
				numPlayer = scanner2.nextInt() + 1; // AIs and player
			}

			// start the game
			Game game = new Game(numPlayer);

			// ----------------------------------------------------
			// Add your game logic here based on the requirements
			// ----------------------------------------------------

			userWantsToQuit = true; // use this when the user wants to exit the game

		}

	}

}
