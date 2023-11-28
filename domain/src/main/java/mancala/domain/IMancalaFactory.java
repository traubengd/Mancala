package mancala.domain;

public interface IMancalaFactory {
    /**
     * Method to create a new Mancala game
     * 
     * @param namePlayer1 The name of the first player
     * @param namePlayer2 The name of the second player
     * @return A new Mancala game
     */
    IMancala createNewGame(String namePlayer1, String namePlayer2);
}
