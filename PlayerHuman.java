package ca.utoronto.utm.assignment1.othello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class PlayerHuman extends Player{
	
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));


	/**
	 * Constructor for PlayerHuman
	 *
	 * @param othello
	 * @param player
	 */
	public PlayerHuman(Othello othello, char player) {
		super(othello, player);
	}

	/**
	 *
	 * @return a Move that is legal on the board given board is empty
	 */
	@Override
	public Move getMove() {
		int row = getMove("row: ");
		int col = getMove("col: ");
		return new Move(row, col);
	}

	/**
	 *
	 * @param message message to be printed out when prompting
	 * @return the entered valid coordinate
	 */
	private int getMove(String message) {
		int move, lower = 0, upper = 7;
		while (true) {
			try {
				System.out.print(message);
				String line = PlayerHuman.stdin.readLine();
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
				break;
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return -1;
	}
}
