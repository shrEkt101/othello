package ca.utoronto.utm.assignment1.othello;



public class Move {

	private int row, col;

	/**
	 * Constructor for Move
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 */
	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 *
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 *
	 * @return row
	 */
	public int getCol() {
		return col;
	}

	/**
	 *
	 * @return string formatted as (row, col)
	 */
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
