package ca.utoronto.utm.assignment1.othello;

import java.util.ArrayList;
import java.util.Random;


public class PlayerRandom extends Player{

	public PlayerRandom(Othello othello, char player){
		super(othello, player);
	}
	
	private Random rand = new Random();

	/**
	 *
	 * @return a random valid Move on the board
	 */
	@Override
	public Move getMove() {
		int row=0;
		int col=0;
		ArrayList<int[]> p1Moves = othello.board.validMoves(player);

		// if there are valid moves to choose from, choose.
		if (p1Moves.size()>0){
			int chosen = rand.nextInt(p1Moves.size());
			row = p1Moves.get(chosen)[0];
			col = p1Moves.get(chosen)[1];
		}
		// if there are no legal moves just move(0,0) to skip turn
		return new Move(row, col);
	}
}
