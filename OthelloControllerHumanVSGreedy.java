package ca.utoronto.utm.assignment1.othello;

public class OthelloControllerHumanVSGreedy extends OthelloController{
	
	/**
	 * Run main to play a Human (P1) against the computer P2. 
	 * The computer uses a greedy strategy, that is, it picks the first
	 * move which maximizes its number of token on the board.
	 * The output should be almost identical to that of OthelloControllerHumanVSHuman.
	 */

	public OthelloControllerHumanVSGreedy(){
		super(new Othello());
		super.player1 = new PlayerHuman(super.othello, OthelloBoard.P1);
		super.player2 = new PlayerGreedy(super.othello, OthelloBoard.P2);
	}

	/**
	 * Run main to have a 2 player game between two humans.
	 * @param args
	 */
	public static void main(String[] args) {
		OthelloControllerHumanVSGreedy oc = new OthelloControllerHumanVSGreedy();
		 oc.play(); // this should work
	}
}
