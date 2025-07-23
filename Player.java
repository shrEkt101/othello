package ca.utoronto.utm.assignment1.othello;

public abstract class Player {
    protected Othello othello;
    protected char player;


    public Player(Othello othello, char player) {
        this.othello = othello;
        this.player = player;
    }

    /**
     * To be implemented in each subclass.
     * @return null for the abstract class.
     */
    public Move getMove(){
        return null;
    };
}
