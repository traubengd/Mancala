package mancala.domain;

public class Player {
    private Player opponent;
    private boolean turnStatus;

    public Player() {
        this.turnStatus = true;
        Player secondPlayer = new Player(this);
        this.opponent = secondPlayer;
    }

    public Player(Player firstPlayer) {
        this.turnStatus = false;
        this.opponent = firstPlayer;
    }

    public Player getOpponent() {
        return opponent;
    }

    public boolean getTurnStatus() {
        return turnStatus;
    }

    public void switchTurn() {
        this.turnStatus = !this.turnStatus;
        this.opponent.turnStatus = !this.opponent.turnStatus;
    }

    public void deactivatePlayer() {
        this.turnStatus = false;
    }

    public boolean checkIfBothPlayersInactive() {
        if (!this.getTurnStatus() && !this.getOpponent().getTurnStatus()) {
            return true;
        } else {
            return false;
        }
    }
}