package mancala.api.models;

import mancala.domain.IMancala;

public class GameStatusDTO {

    public boolean endOfGame;
    public String winner;

    public GameStatusDTO(IMancala mancala) {
        this.endOfGame = mancala.isEndOfGame();
        switch (mancala.getWinner()) {
            case PLAYER_1:
                this.winner = mancala.getNameOfPlayerOne();
                break;
            case PLAYER_2:
                this.winner = mancala.getNameOfPlayerTwo();
                break;
            case DRAW:
                this.winner = mancala.getNameOfPlayerOne()
                        + " and "
                        + mancala.getNameOfPlayerTwo();
                break;
            default:
                this.winner = null;
        }
    }

    public boolean getEndOfGame() {
        return endOfGame;
    }

    public String getWinner() {
        return winner;
    }
}
