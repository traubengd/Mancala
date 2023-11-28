package mancala.domain;

public interface IMancala {

    public enum Winner {
        NO_ONE,
        PLAYER_1,
        PLAYER_2,
        DRAW
    }

    /**
     * Method returning the name of the first player
     * 
     * @return The name of the first player.
     */
    String getNameOfPlayerOne();

    /**
     * Method returning the name of the opponent
     * 
     * @return The name of the first player.
     */
    String getNameOfPlayerTwo();

    /**
     * Method indicating if the first player has the next turn or not.
     * 
     * @param name The player which you want to know the turn for.
     * @return True if the player has the turn. False if it's the turn of the other
     *         player.
     */
    boolean isPlayersTurn(String name);

    /**
     * Method for playing the specified recess. Index is as specified below:
     * 
     * 12 11 10 9 8 7
     * 13 6
     * 0 1 2 3 4 5
     * 
     * @param index Index of the recess to be played.
     */
    void playPit(int index);

    /**
     * Method for returning the amount of stones in de specified pit. Index is as
     * specified below:
     * 
     * 12 11 10 9 8 7
     * 13 6
     * 0 1 2 3 4 5
     * 
     * @param index Index of the pit.
     * @return Amount of stone.
     */
    int getStonesForPit(int index);

    /**
     * Method for retrieving whether the game has ended or not.
     * 
     * @return True is the game has ended otherwise False.
     */
    boolean isEndOfGame();

    /**
     * Method for retrieving the player that has won the game.
     * 
     * @return Enum value representing which player(s) (if any) won the game.
     *         0 : No Winner
     *         1 : Player 1
     *         2 : Player 2
     *         3 : Draw
     */
    Winner getWinner();

}
