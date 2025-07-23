package ca.utoronto.utm.assignment1.othello;

import java.util.ArrayList;
import java.util.Random;

public class OthelloControllerRandomVSRandom extends OthelloController{
	/**
	 * Run main to execute the simulation and print out the two line results.
	 * Output looks like 
	 * Probability P1 wins=.75 
	 * Probability P2 wins=.20
	 */

	public OthelloControllerRandomVSRandom() {
		super(new Othello());
		super.player1 = new PlayerRandom(super.othello, OthelloBoard.P1);
		super.player2 = new PlayerRandom(super.othello, OthelloBoard.P2);
	}

	public static void main(String[] args) {
		
		int p1wins = 0, p2wins = 0, numGames = 10000;
		int p1NullHypothesis = 0, p2NullHypothesis = 0, winnerNullHypothesis = 0;
		int HaCount = 0;
		float calculatedEdge = 0;
		Random rand = new Random();

		// simulate <numGames> games without draws
		int trial = 0;
		while (trial < numGames){
			OthelloControllerRandomVSRandom oc = new OthelloControllerRandomVSRandom();
			oc.playMute();

			if (oc.finalWinner() == OthelloBoard.EMPTY){
				; // restart if there is a draw
			}else {
				if (oc.finalWinner() == OthelloBoard.P1) p1wins++;
				if (oc.finalWinner() == OthelloBoard.P2) p2wins++;
				trial++;
			}
		}

		System.out.println("P1 wins: " + p1wins + " P2 wins: " + p2wins + " total games: " + numGames);
		calculatedEdge = (float)(p2wins - p1wins)/trial;

		System.out.println("The simulated edge P2 has over P1 is: " + calculatedEdge*100 + "%.");

		// assume the chance of p1 is equal to p2 with prob = 0.5.

		// every 1000 flips
		int interval = 1000;
		for (int i=0; i<numGames*interval; i++){
			winnerNullHypothesis = rand.nextInt(2);
			if (winnerNullHypothesis == 0) p1NullHypothesis++;
			if (winnerNullHypothesis == 1) p2NullHypothesis++;

			if (i%interval == 0){
				// if p2 has an edge over p1 of over 5% it supports Ha
				// (p2 - p1/interval) > 0.05 <=> p2 - p1 > 0.05 * interval

				if((p2NullHypothesis-p1NullHypothesis) >= calculatedEdge * interval) HaCount++;

				p1NullHypothesis = 0;
				p2NullHypothesis = 0;
			}
		}

		System.out.println("Out of " + numGames + " instances, there were " + HaCount +" instances where for every " + interval +" games, win%P2 - win%P1 >= " + calculatedEdge*100 +"%.");
		System.out.println("Probability of P2 having an edge of "+calculatedEdge*100+"% is "+(float)(HaCount)/(float)(numGames)*100 +"%");
		System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
		System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
	}
}
