package ca.utoronto.utm.assignment1.othello;

public class OthelloControllerRandomVSGreedy extends OthelloController{

	/**
	 * Run main to execute the simulation and print out the two line results.
	 * Output looks like: 
	 * Probability P1 wins=.75 
	 * Probability P2 wins=.20
	 */

	public OthelloControllerRandomVSGreedy(){
		super(new Othello());
		super.player1 = new PlayerRandom(super.othello, OthelloBoard.P1);
		super.player2 = new PlayerGreedy(super.othello, OthelloBoard.P2);
	}


	/**
	 * Simulates numGames many games and calculate the successfulness of each strategy.
	 * @param args
	 */
	public static void main(String[] args) {
		int p1wins = 0, p2wins = 0, numGames = 10000;

		for (int i=0; i<numGames; i++) {
			OthelloControllerRandomVSGreedy oc = new OthelloControllerRandomVSGreedy();
			oc.playMute();

			if (oc.finalWinner() == OthelloBoard.P1) p1wins++;
			if (oc.finalWinner() == OthelloBoard.P2) p2wins++;
		}

		System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
		System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
	}
}
