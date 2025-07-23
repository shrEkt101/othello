package ca.utoronto.utm.assignment1.othello;

import java.util.Random;


public class Othello {

	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
	protected OthelloBoard board = new OthelloBoard(DIMENSION);

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {return this.whosTurn;}


	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		char temp = board.hasMove();

		// if <getWhosTurn> can make a move
		if(temp == getWhosTurn() || temp == OthelloBoard.BOTH){
			if (board.move(row, col, getWhosTurn())){
				numMoves++;
				whosTurn = OthelloBoard.otherPlayer(whosTurn);
				return true;
			}return false;
		}

		// if the <getWhosTurn> does not have a move, skip
		whosTurn = OthelloBoard.otherPlayer(whosTurn);
		return false;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return board.getCount(player);
	}

	/**
	 * Returns the winner of the game.
	 *
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		if(isGameOver()){
			if (getCount(OthelloBoard.P1) > getCount(OthelloBoard.P2)){
				return OthelloBoard.P1;
			}else if(getCount(OthelloBoard.P1) < getCount(OthelloBoard.P2)){
				return OthelloBoard.P2;
			}
		}
		return OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		return board.hasMove() == OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString();
	}

	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random rand = new Random();

		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println(o.getWhosTurn() +  " makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}

	}
}