package hw2.Trouble;

import java.io.IOException;
import java.util.Scanner;

public class Trouble {
	BoardGraphics gameBoardGraphics;
	Peg[][] pegs;
	int playerTurn;

	Dice die;

	final String[] color = {"Red", "Blue", "Yellow", "Green"};

	// Used in conditions for checking states of pegs.
	final int PEG_IN_HOME = 0;
	final int PEG_IN_CIRCLE = 1;
	final int PEG_IN_FINISH = 2;

	// variable for player turns.
	final int RED_TURN = 0;
	final int BLUE_TURN = 1;
	final int YELLOW_TURN = 2;
	final int GREEN_TURN = 2;

	// initial position of pegs
	final int RED_START = 0;
	final int BLUE_START = 7;
	final int YELLOW_START = 14;
	final int GREEN_START = 21;

	final int NUM_PLAYERS = 4;
	final int NUM_PEGS = 4;

	// starting coordinates of each player
	final int[] playerStart = {RED_START, BLUE_START, YELLOW_START, GREEN_START};

	public Trouble() {
		gameBoardGraphics = new BoardGraphics();
		pegs = new Peg[NUM_PLAYERS][NUM_PEGS];
		die = new Dice();
		playerTurn = RED_TURN; // 0 represents player red



		// creating pegs and intializing their attributes
		char[] symbol = {'R', 'B', 'Y', 'G'};
		for(int i = 0; i < NUM_PLAYERS; i++) {
			for(int j = 0; j < NUM_PEGS; j++) {
				pegs[i][j] = new Peg((symbol[i])+Integer.toString(j+1));
				pegs[i][j].updateState(PEG_IN_HOME);
				pegs[i][j].setCurrentIndex(j);
			}
		}


		// Initializing all pegs to their home cells
		gameBoardGraphics.insertInHomeCell(BoardGraphics.RED_INDEX, 0, "R1");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.RED_INDEX, 1, "R2");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.RED_INDEX, 2, "R3");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.RED_INDEX, 3, "R4");

		gameBoardGraphics.insertInHomeCell(BoardGraphics.BLUE_INDEX, 0, "B1");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.BLUE_INDEX, 1, "B2");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.BLUE_INDEX, 2, "B3");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.BLUE_INDEX, 3, "B4");

		gameBoardGraphics.insertInHomeCell(BoardGraphics.YELLOW_INDEX, 0, "Y1");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.YELLOW_INDEX, 1, "Y2");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.YELLOW_INDEX, 2, "Y3");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.YELLOW_INDEX, 3, "Y4");

		gameBoardGraphics.insertInHomeCell(BoardGraphics.GREEN_INDEX, 0, "G1");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.GREEN_INDEX, 1, "G2");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.GREEN_INDEX, 2, "G3");
		gameBoardGraphics.insertInHomeCell(BoardGraphics.GREEN_INDEX, 3, "G4");

	}

	public void move() {
		// roll the dice
		// ask for input
		// check if the move is valid. User can move it its a valid move

		System.out.println(color[playerTurn]+" Player's turn");
		int diceValue = die.roll();
		System.out.println("Dice Rolled: "+diceValue);
		Scanner input = new Scanner(System.in);
		// for manual testing
		//System.out.print("Input custon die value: ");
		//diceValue = input.nextInt();
		int playerChosenPeg;
		do {
			System.out.print("Input Peg Number (e.g. 1, 2, 3, 4): ");
			playerChosenPeg = input.nextInt();
			if(playerChosenPeg == 0) {
				break;
			}
		}while(!applyMove(playerChosenPeg, diceValue));
		if(diceValue != 6)
			playerTurn = (playerTurn+1)%4;

	}


	/*
		applyMove returns true if the move was successful.
		otherwise false
		It takes arguemtn pegNumber which we are trying to move and diceValue to add in the peg.
	 */
	public boolean applyMove(int pegNumber, int diceValue) {
		String errorMessage = "Not a valid move. Kindly try again. (Input 0 if you want to pass your turn)";

		if(pegNumber < 0 || pegNumber > NUM_PEGS) {
			System.out.println("Error: no peg found having this id.");
			return false;
		}
		if(pegs[playerTurn][pegNumber-1].currentState == PEG_IN_HOME && diceValue == 6) {
			/*
				If the peg is at home and we want to get it out in open.
				In this condition we will check if there is a peg of same color at our start pos.
				If there is a peg of same color, then we generate an error.
				Or else if there is a peg of different color, we simplye eliminiate that.
		 	*/
			for(int player = 0; player < NUM_PLAYERS; player++) {
				for (int i = 0; i < NUM_PEGS; i++) {
					if (i != (pegNumber-1) && player == playerTurn) {
						if (pegs[player][i].currentState == PEG_IN_CIRCLE && pegs[player][i].currentIndex == playerStart[playerTurn]) {
							return false;
						}
					} else if(player != playerTurn) {
						if (pegs[player][i].currentState == PEG_IN_CIRCLE && pegs[player][i].currentIndex == playerStart[playerTurn]) {

							movePegToHome(pegs[player][i], player);
							movePeg(pegs[playerTurn][pegNumber-1], playerTurn, playerStart[playerTurn], PEG_IN_CIRCLE);
							return true;
						}
					}
				}
			}
			movePeg(pegs[playerTurn][pegNumber-1], playerTurn, playerStart[playerTurn], PEG_IN_CIRCLE);
			return true;

		}
		else if(pegs[playerTurn][pegNumber-1].currentState == PEG_IN_CIRCLE) {
			/*
				If we are out of our home cell and moving in circular path, we check these conditions for movement:
					If peg is about to move into a finsh cell
						Calculation of red color pegs are done separately but of circular property
					If peg is about to move into a regular cell

					In all cases we check that our targeted cell doesn't contain the peg of same type.
			 */
			int oldIndex = pegs[playerTurn][pegNumber-1].currentIndex;
			boolean insideFinish = false;
			int newIndex = oldIndex+diceValue;
			if(playerTurn != RED_TURN) {
				if(oldIndex < playerStart[playerTurn] && newIndex >= playerStart[playerTurn]) {
					newIndex = (newIndex-playerStart[playerTurn]);
					insideFinish = true;
				}
			}
			else{
				// we do separate calculations for red pegs.
				if(oldIndex > (newIndex%28)) {
					newIndex = (newIndex-27-1);
					insideFinish = true;
				}
			}
			// checking for out of bounds in finish cells
			if(insideFinish && newIndex >= 4)
			{
				System.out.println("Error: Out of bounds. Kindly try again with valid move.");
				return false;
			}

			for(int player = 0; player < NUM_PLAYERS; player++) {
				for(int pegNum = 0; pegNum < NUM_PEGS; pegNum++) {
					// looking at the circular cells
					if(insideFinish != true && player == playerTurn  && pegs[player][pegNum].currentState == PEG_IN_CIRCLE &&  pegs[player][pegNum].currentIndex == newIndex) {

						return false;
					}
					// when we need to look at the finish cells
					else if(insideFinish == true && player == playerTurn && pegs[player][pegNum].currentState == PEG_IN_FINISH && pegs[player][pegNum].currentIndex == newIndex) {

						return false;
					}
					// if pegs are different. we can eliminate those in our way.
					else if(player != playerTurn && pegs[player][pegNum].currentState == PEG_IN_CIRCLE && pegs[player][pegNum].currentIndex == newIndex) {
						movePegToHome(pegs[player][pegNum], player);
						if(!insideFinish) {
							movePeg(pegs[playerTurn][pegNumber - 1], playerTurn, newIndex % 28, PEG_IN_CIRCLE);
						} else {
							movePeg(pegs[playerTurn][pegNumber - 1], playerTurn, newIndex, PEG_IN_FINISH);
						}
						return true;
					}
				}
			}
			if(!insideFinish) {
				movePeg(pegs[playerTurn][pegNumber - 1], playerTurn, newIndex % 28, PEG_IN_CIRCLE);
			} else {
				movePeg(pegs[playerTurn][pegNumber - 1], playerTurn, newIndex, PEG_IN_FINISH);
			}
			return true;
		}
		else if(pegs[playerTurn][pegNumber-1].currentState == PEG_IN_FINISH) {
			/*
				If we are inside finish cell area, we just have to check that we remain in the bounds.
				This condition like above two also checks that our targeted cell contains a peg of same type or not
				If targeted cell where we want to move already contains a peg, we generate an error.
			 */
			int oldIndex = pegs[playerTurn][pegNumber-1].currentIndex;
			int newIndex = oldIndex+diceValue;

			if(newIndex >= 4)
			{
				System.out.println("Error: Out of bounds. Kindly try again with valid move.");
				return false;
			}

			if(newIndex > 4) {
				System.out.println(errorMessage);
				return false;
			} else {
				for(int pegNum = 0; pegNum < NUM_PEGS; pegNum++) {
					if(pegNum != pegNumber-1 && pegs[playerTurn][pegNum].currentIndex == newIndex) {
						return false;
					}
					else {
						movePeg(pegs[playerTurn][pegNumber-1], playerTurn, newIndex, pegs[playerTurn][pegNumber-1].currentState);
						return true;
					}
				}
			}
		}
		System.out.println(errorMessage);
		return false;
	}

	void movePeg(Peg peg, int playerID, int newIndex, int newState) {
		/*
			This function is used to move pegs from one place to another
			It simply deletes the existing peg in boardgraphics and insert sybmot at new location
			We also update the states of peg such as its "currentState" and "currentIndex".
		 */
		if(peg.currentState == PEG_IN_HOME) {
			gameBoardGraphics.insertInHomeCell(playerID, peg.currentIndex, "  ");
		}
		else if(peg.currentState == PEG_IN_FINISH) {
			gameBoardGraphics.insertInFinishCell(playerID, peg.currentIndex, "  ");
		}
		else {
			gameBoardGraphics.insertInCircularCell(peg.currentIndex, "  ");
		}


		peg.updateState(newState);
		peg.setCurrentIndex(newIndex);

		if(newState == PEG_IN_HOME) {
			gameBoardGraphics.insertInHomeCell(playerID, newIndex, peg.getSymbol());
		}
		else if(newState == PEG_IN_FINISH) {
			gameBoardGraphics.insertInFinishCell(playerID, newIndex, peg.getSymbol());
		}
		else {
			gameBoardGraphics.insertInCircularCell(newIndex, peg.getSymbol());
		}
	}

	public boolean checkWinner() {
		/*
			This function simply counts the number of pegs in each group having currentState == PEG_IN_FINISH
		 */
		for(int player = 0; player < NUM_PLAYERS; player++) {
			int count = 0;
			for(int i = 0; i < NUM_PEGS; i++) {
				if(pegs[player][i].currentState == PEG_IN_FINISH)
					count++;
			}
			if(count == NUM_PEGS)
			{
				System.out.println(color[player]+" player has won the game.");
				return true;
			}
		}
		return false;
	}

	void movePegToHome(Peg peg, int playerID) {
		/*
			similar to movePeg but for a special purpose
		 	it simply moves given peg to its home cell
		*/
		int cellIndex = Character.getNumericValue(peg.getSymbol().charAt(1));
		gameBoardGraphics.insertInHomeCell(playerID, cellIndex-1, peg.getSymbol());
		peg.currentState = PEG_IN_HOME;
		peg.currentIndex = cellIndex-1;
	}



	public void drawBoard() {
		gameBoardGraphics.draw();
		try {
			Runtime.getRuntime().exec("cmd.exe /c cls");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		Trouble game = new Trouble();
		while(true) {
			game.drawBoard();
			game.move();
			if(game.checkWinner()) break;
		}
	}

}
