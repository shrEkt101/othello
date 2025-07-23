package ca.utoronto.utm.assignment1.othello;


public class OthelloControllerHumanVSRandom extends OthelloController{
	
	/**
	 * Run main to play a Human (P1) against the computer P2. 
	 * The computer uses a random strategy, that is, it randomly picks 
	 * one of its possible moves.
	 * The output should be almost identical to that of OthelloControllerHumanVSHuman.
	 */

	public OthelloControllerHumanVSRandom(){
		super(new Othello());
		super.player1 = new PlayerHuman(super.othello, OthelloBoard.P1);
		super.player2 = new PlayerRandom(super.othello, OthelloBoard.P2);
	}

	/**
	 * Run this to have a 1 player game of human player against a greedy algorithm.
	 * @param args
	 */
	public static void main(String[] args) {
		OthelloControllerHumanVSGreedy oc = new OthelloControllerHumanVSGreedy();
		oc.play(); // this should work
	}
}
