package mancala.domain;

public class Mancala implements IMancala {
    private String namePlayer1;
    private String namePlayer2;
    private Player firstPlayer;
    private PlayableBowl firstBowl;

    public Mancala(String namePlayer1, String namePlayer2) {
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
        firstPlayer = new Player();
        firstBowl = new PlayableBowl(firstPlayer);
    }

    @Override
    public String getNameOfPlayerOne() {
        return namePlayer1;
    }

    @Override
    public String getNameOfPlayerTwo() {
        return namePlayer2;
    }

    @Override
    public boolean isPlayersTurn(String name) {
        if (name != namePlayer1 && name != namePlayer2) {
            throw new IllegalArgumentException("Unrecognized player");
        } else {
            return (name.equals(namePlayer1) && firstPlayer.getTurnStatus())
                    || (name.equals(namePlayer2) && firstPlayer.getOpponent().getTurnStatus());
        }
    }

    @Override
    public void playPit(int index) {
        firstBowl.moveThroughBoard(index).doMove();
    }

    @Override
    public int getStonesForPit(int index) {
        return firstBowl.moveThroughBoard(index).getSeeds();
    }

    @Override
    public boolean isEndOfGame() {
        return firstPlayer.checkIfBothPlayersInactive();
    }

    @Override
    public Winner getWinner() {
        switch (firstBowl.checkWinner()) {
            case FIRST_PLAYER:
                return Winner.PLAYER_1;
            case SECOND_PLAYER:
                return Winner.PLAYER_2;
            case EQUAL:
                return Winner.DRAW;
            default:
                return Winner.NO_ONE;
        }
    }
}
