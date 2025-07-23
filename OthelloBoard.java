package ca.utoronto.utm.assignment1.othello;

import java.util.ArrayList;

public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;

	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		// set up the middle 4 squares of the board
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	/**
	 *
	 * @return the dimension of the board
	 */
	public int getDimension() {
		return this.dim;
	}

	/**
	 * 
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
		if (player == EMPTY) return EMPTY;
		if (player == P1){
			return P2;
		}
		return P1;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		if (this.validCoordinate(row, col)) return this.board[row][col];
		return EMPTY;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) {
		if (row < this.dim && col < this.dim && row>=0 && col>=0) return true;
		return false;
	}

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol), you encounter a sequence of at least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */

	private char alternation(int row, int col, int drow, int dcol) {
		// start at either X or O (e.g. X)
		// if you keep going in the direction of (drow, dcol) and keep finding O without EMPTY,
		// and you eventually find X, return X.
		// e.g. XOOOOOOOOX where (row, int) = (0,0) and (drow dcol) = (0,1), returns X.
		// e.g. XOOOOOOOOO returns EMPTY


		if (drow == 0 && dcol == 0) return EMPTY;
		if (this.validCoordinate(row, col)){
			char originalCoordinate = get(row, col);
			if(originalCoordinate == EMPTY) return EMPTY;

			// the coordinate has an X or O

			int tempRow = row + drow;
			int tempCol = col + dcol;

			// row + drow and col + dcol to upadte coordinate

			while(this.validCoordinate(tempRow, tempCol)){
				char curr = get(tempRow, tempCol);
				if (curr != originalCoordinate){
					return curr;
				}
				tempRow += drow;
				tempCol += dcol;
			}
		}

		// if out of bounds then automatically return empty
		return EMPTY;
	}

	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOOO
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) {
		// OOOX -> XXXX
		// if drow dcol invalid return -1
		if (drow == 0 && dcol == 0) return -1;
		if (this.validCoordinate(row, col)){
			//if starting coordinate is other player, which it needs to be
			if (this.get(row, col) == otherPlayer(player)){
				// if there is another player if you keep going along (drow, dcol)
				if(this.alternation(row, col, drow, dcol) == player){
					// we move in the direction of (drow, dcol) until
					// we find the other player at the end of this line.
					// we fill all tiles along the way.
					int counter = 0;
					int tempRow = row;
					int tempCol = col;
					while (this.get(tempRow, tempCol) != player){
						this.board[tempRow][tempCol] = player;
						tempRow += drow;
						tempCol += dcol;
						counter += 1;
					}
					return counter;
				}
			}else return 0; // if curr coordinate is the same as player, cannot flip
		}
		return -1;
	}

	private int numFlippedHelper(int row, int col, int drow, int dcol, char player) {
		if (drow == 0 && dcol == 0) return 0;
		if (this.validCoordinate(row, col)) {
			//if starting coordinate is other player, which it needs to be
			if (this.get(row, col) == otherPlayer(player)) {
				if (this.alternation(row, col, drow, dcol) == player) {
					int counter = 0;
					int tempRow = row;
					int tempCol = col;
					while (this.get(tempRow, tempCol) != player) {
						tempRow += drow;
						tempCol += dcol;
						counter += 1;
					}
					return counter;
				}
			}
			return 0; // if curr coordinate is the same as player, cannot flip
		}
		return 0; // if not valid coordinate
	}

	/**
	 *
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return the number flipped by player at (row, col)
	 */
	public int numFlippedAt(int row, int col, char player){
		int maxFlipped = 0;
		for(int drow = -1; drow <= 1; drow++){
			for(int dcol = -1; dcol <= 1; dcol++){
				maxFlipped += numFlippedHelper(row+drow, col+dcol, drow, dcol, player);
			}
		}
		return maxFlipped;
	}


	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) {
		if(this.validCoordinate(row, col)){
			// if there is no player on this coordinate
			if(this.get(row, col) == EMPTY){
				//tempPlayer is the player at the end of the flip line
				// e.g. EOOOX's tempPlayer at (0,0) with (drow, dcol) = (0, 1) is X
				// because alternation(0,1,0,1) == X.

				// since our curr coordinate is empty, we cannot yet apply alternation
				// to verify that there is X or O at the end of the line.
				// thus we must first move in the direction of (drow, dcol) (see example above)
				char tempPlayer = alternation(row+drow, col+dcol, drow, dcol);
				if(tempPlayer != EMPTY){
					//if there is X or O in the line of (drow, dcol)
					return tempPlayer;
				}
			}
		}
		return EMPTY;
	}

	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {
		boolean p1 = false;
		boolean p2 = false;

		// for each coordinate, we check each direction for if it has any moves for X or O.
		for(int row=0; row < this.dim; row ++){
			for(int col=0; col < this.dim; col++){
				for(int drow = -1; drow <= 1; drow++){
					for(int dcol = -1; dcol <= 1; dcol++){
						char temp = this.hasMove(row, col, drow, dcol);
						if (temp == P1) p1 = true;
						if (temp == P2) p2 = true;
					}
				}
			}
		}
		if (p1 && p2) return BOTH;
		if (p1) return P1;
		if (p2) return P2;
		return EMPTY;
	}

	private char hasMove2(int row, int col){
		//helper to verify a move can be made at (row, col)

		boolean p1 = false;
		boolean p2 = false;

		for(int drow = -1; drow <= 1; drow++){
			for(int dcol = -1; dcol <= 1; dcol++){
				char temp = this.hasMove(row, col, drow, dcol);
				if (temp == P1) p1 = true;
				if (temp == P2) p2 = true;
			}
		}

		if (p1 && p2) return BOTH;
		if (p1) return P1;
		if (p2) return P2;
		return EMPTY;
	}

	/**
	 *
	 * @param player P1 or P2
	 * @return an ArrayList of all coordinates that would be valid moves
	 */
	public ArrayList<int[]> validMoves(char player) {
		ArrayList<int[]> valid = new ArrayList<>();

		//iterate through the rows first
		// e.g. (2,4), (2,5), (3, 2) instead of (3,2), (2,4), (2,5)
		// this is important for our greedy implementation as if we have two or more
		// coordinates that flips the same amount, we want to pick the one that has the
		// lowest row and column

		// for each coordinate, we check each direction for if it has any moves for X or O.
		for(int row=0; row < this.dim; row ++){
			for(int col=0; col < this.dim; col++){
				char temp = hasMove2(row, col);
				if (temp == player || temp == BOTH){
					int[] toAdd = {row, col};
					valid.add(toAdd);
				}
			}
		}
		return valid;
	}

	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		// HINT: Use some of the above helper methods to get this methods
		// job done!!
		char temp = this.hasMove();
		//if player can make a move at all
		if (temp == 'B' || temp == player){
			// if player can play at (row, col)
			// must be empty to be able to play
//			System.out.println("this.hasMove2 "+this.hasMove2(row, col));
			if (this.get(row, col) == EMPTY && (this.hasMove2(row, col) == BOTH || this.hasMove2(row, col) == player)){
				// fill current coordinate as player
				board[row][col] = player;

				// flip in every direction
				for (int drow = -1; drow <= 1; drow++) {
					for (int dcol = -1; dcol <= 1; dcol++) {
						// we have to move one step in the direction of (drow, dcol) to flip
						this.flip(row+drow, col+dcol, drow, dcol, player);
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
		int count = 0;

		for(int row =0; row< this.dim; row++){
			for(int col = 0; col< this.dim; col++){
				if (this.board[row][col] == player) count ++;
			}
		}
		return count;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		/**
		 * See assignment web page for sample output.
		 */
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard. Output is on assignment page.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
//		OthelloBoard ob = new OthelloBoard(8);
//		System.out.println(ob.toString());
//		System.out.println("getCount(P1)=" + ob.getCount(P1));
//		System.out.println("getCount(P2)=" + ob.getCount(P2));
//
//
//		ArrayList<int[]> temp = ob.validMoves(P1);
//		for(int i =0; i < temp.size(); i++){
//			int row = temp.get(i)[0];
//			int col = temp.get(i)[1];
//			System.out.print(row + " ");
//			System.out.println(col);
//
//			System.out.println(ob.numFlippedAt(row, col, P1));
//		}



		// test flip
		// flips (3, 4) from o to X
//		System.out.println(ob.flip(3,4,1,0,P1));
//		System.out.println(ob.toString());


//		for (int row = 0; row < ob.dim; row++) {
//			for (int col = 0; col < ob.dim; col++) {
//				ob.board[row][col] = P1;
//			}
//		}
//		System.out.println(ob.toString());
//		System.out.println("getCount(P1)=" + ob.getCount(P1));
//		System.out.println("getCount(P2)=" + ob.getCount(P2));
//
//		// Should all be blank
//		for (int drow = -1; drow <= 1; drow++) {
//			for (int dcol = -1; dcol <= 1; dcol++) {
//				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
//			}
//		}
//
//		for (int row = 0; row < ob.dim; row++) {
//			for (int col = 0; col < ob.dim; col++) {
//				if (row == 0 || col == 0) {
//					ob.board[row][col] = P2;
//				}
//			}
//		}
//		System.out.println(ob.toString());
//
//		// Should all be P2 (O) except drow=0,dcol=0
//		for (int drow = -1; drow <= 1; drow++) {
//			for (int dcol = -1; dcol <= 1; dcol++) {
//				System.out.println("direction=(" + drow + "," + dcol + ")");
//				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
//			}
//		}
//
//		// Can't move to (4,4) since the square is not empty
//		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
//
//		ob.board[4][4] = EMPTY;
//		ob.board[2][4] = EMPTY;
//
//		System.out.println(ob.toString());
//
//		for (int drow = -1; drow <= 1; drow++) {
//			for (int dcol = -1; dcol <= 1; dcol++) {
//				System.out.println("direction=(" + drow + "," + dcol + ")");
//				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
//			}
//		}
//		System.out.println("who has a move=" + ob.hasMove());
//		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
//		System.out.println(ob.toString());
//
		OthelloBoard board;
		Move[] moves = {new Move(2, 4), new Move(2, 5), new Move(2, 6), new Move(2, 3), new Move(2, 2), new Move(3, 2),
				new Move(4, 2), new Move(5, 4), new Move(6, 4)};


		board = new OthelloBoard(Othello.DIMENSION); // 8x8 board

		System.out.println(board.toString());

		ArrayList<int[]> temp = board.validMoves(P1);
		for(int i =0; i < temp.size(); i++){
			int row = temp.get(i)[0];
			int col = temp.get(i)[1];
			System.out.print(row + " ");
			System.out.println(col);

			System.out.println(board.numFlippedAt(row, col, P1));
		}

//		System.out.println(board.hasMove(2,4,1,0));
//		System.out.println(board.hasMove2(2,4));

//		board.flip(3,4,1,0,P1);


		board.move(2, 4, OthelloBoard.P1);
		board.move(2, 5, OthelloBoard.P2);
		board.move(2, 6, OthelloBoard.P1);
		board.move(2, 3, OthelloBoard.P2);

		System.out.println(board.toString());

		temp = board.validMoves(P1);
		for(int i =0; i < temp.size(); i++){
			int row = temp.get(i)[0];
			int col = temp.get(i)[1];
			System.out.print(row + " ");
			System.out.println(col);

			System.out.println(board.numFlippedAt(row, col, P1));
		}

	}
}
