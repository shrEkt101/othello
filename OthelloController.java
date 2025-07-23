package ca.utoronto.utm.assignment1.othello;

public abstract class OthelloController {
    protected Othello othello;
    protected Player player1;
    protected Player player2;

    /**
     *
     * @param othello the othello game the controller should control
     */
    public OthelloController(Othello othello){
        this.othello = othello;

        // player1 and player2 must be instantiated in the subclasses
    }

    /**
     * run the game until the game ends
     */
    public void play(){
        System.out.println("USING THE SUPERCLASS");
        while (!othello.isGameOver()) {
            this.report();

            Move move = null;
            char whosTurn = othello.getWhosTurn();

            if (whosTurn == OthelloBoard.P1)
                move = player1.getMove();
            if (whosTurn == OthelloBoard.P2)
                move = player2.getMove();

            this.reportMove(whosTurn, move);
            othello.move(move.getRow(), move.getCol());
        }

        this.reportFinal();
    }

    /**
     * Play out the game but without any of the reports or displays to speed up process.
     */
    public void playMute() {
        while (!othello.isGameOver()) {
            Move move = null;
            char whosTurn = othello.getWhosTurn();

            if (whosTurn == OthelloBoard.P1)
                move = player1.getMove();
            if (whosTurn == OthelloBoard.P2)
                move = player2.getMove();

            othello.move(move.getRow(), move.getCol());
        }
    }

    private void report() {

        String s = othello.getBoardString() + OthelloBoard.P1 + ":"
                + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
                + othello.getWhosTurn() + " moves next";
        System.out.println(s);
    }

    private void reportFinal() {

        String s = othello.getBoardString() + OthelloBoard.P1 + ":"
                + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2)
                + "  " + othello.getWinner() + " won\n";
        System.out.println(s);
    }

    public char finalWinner(){
        return othello.getWinner();
    }

    private void reportMove(char whosTurn, Move move) {
        System.out.println(whosTurn + " makes move " + move + "\n");
    }
}
