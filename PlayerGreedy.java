package ca.utoronto.utm.assignment1.othello;

import java.util.ArrayList;



public class PlayerGreedy extends Player{

	public PlayerGreedy(Othello othello, char player){
		super(othello, player);
	}

	/**
	 *
	 * @return a Move calculated with the greedy algorithm
	 */
	@Override
	public Move getMove() {
		int maxFlip = 0;
		int maxIndex = 0;
		ArrayList<int[]> valid = othello.board.validMoves(player);

		// if there are valid moves to choose from, choose
		if (valid.size() > 0){
			for(int index=0; index < valid.size(); index++){
				int row = valid.get(index)[0];
				int col = valid.get(index)[1];
				// check how many flipped at (row, col)
				int currFlip = othello.board.numFlippedAt(row, col, player);

				// if the number of flips exceeds the previous max, update
				if(currFlip > maxFlip){
					maxFlip = currFlip;
					maxIndex = index;
				}
			}
			return new Move(valid.get(maxIndex)[0], valid.get(maxIndex)[1]);
		}

		//if not valid move just return 0,0 regardless of validity to skip turn
		return new Move(0, 0);
	}
}
