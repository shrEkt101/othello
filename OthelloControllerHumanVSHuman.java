package ca.utoronto.utm.assignment1.othello;


public class OthelloControllerHumanVSHuman extends OthelloController{

	/**
	 * Constructs a new OthelloController with a new Othello game, ready to play
	 * with two users at the console.
	 */

	public OthelloControllerHumanVSHuman() {
		super(new Othello());
		super.player1 = new PlayerHuman(super.othello, OthelloBoard.P1);
		super.player2 = new PlayerHuman(super.othello, OthelloBoard.P2);
	}

	/**
	 * Run main to play two Humans against each other at the console.
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloControllerHumanVSHuman oc = new OthelloControllerHumanVSHuman();
		oc.play();
	}

}
