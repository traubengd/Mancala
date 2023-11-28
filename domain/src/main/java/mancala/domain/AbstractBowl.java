package mancala.domain;

abstract class AbstractBowl {
    public enum DomainWinner {
        CONTINUE_GAME,
        FIRST_PLAYER,
        SECOND_PLAYER,
        EQUAL
    }

    private int seeds;
    private Player player;
    private AbstractBowl neighborBowl;
    private String bowlType;

    public Integer getSeeds() {
        return seeds;
    }

    void setSeeds(int seeds) {
        this.seeds = seeds;
    }

    void incrementSeeds() {
        this.seeds++;
    }

    public Player getPlayer() {
        return player;
    }

    void setPlayer(Player player) {
        this.player = player;
    }

    public AbstractBowl getNeighbor() {
        return neighborBowl;
    }

    void setNeighbor(AbstractBowl neighborBowl) {
        this.neighborBowl = neighborBowl;
    }

    public String getType() {
        return this.bowlType;
    }

    void setType(String bowlType) {
        this.bowlType = bowlType;
    }

    abstract void takeOneAndPassRemainder(int seedsToPass);

    abstract void doMove();

    public AbstractBowl moveThroughBoard(int stepsToTake) {
        AbstractBowl currentBowl = this;
        for (int i = 0; i < stepsToTake; i++) {
            currentBowl = currentBowl.getNeighbor();
        }
        return currentBowl;
    }

    DomainWinner checkWinner() {
        if (this.getPlayer().checkIfBothPlayersInactive()) {
            int firstPlayerSeeds = this.moveThroughBoard(findKalahaDistance()).getSeeds();
            int secondPlayerSeeds = this.moveThroughBoard(findKalahaDistance() + 7).getSeeds();
            if (firstPlayerSeeds > secondPlayerSeeds) {
                return DomainWinner.FIRST_PLAYER;
            } else if (secondPlayerSeeds > firstPlayerSeeds) {
                return DomainWinner.SECOND_PLAYER;
            } else {
                return DomainWinner.EQUAL;
            }
        } else {
            return DomainWinner.CONTINUE_GAME;
        }
    }

    int findKalahaDistance() {
        AbstractBowl currentBowl = this;
        int stepsToKalaha = 0;
        while (currentBowl.getType() != "Kalaha") {
            currentBowl = currentBowl.moveThroughBoard(1);
            stepsToKalaha++;
        }
        return stepsToKalaha;
    }

    void deactivatePlayerIfBowlsEmpty() {
        if (checkBowlsEmpty()) {
            this.getPlayer().deactivatePlayer();
        }
    }

    private boolean checkBowlsEmpty() {
        if (this.bowlType.equals("Kalaha")) {
            return true;
        } else if (this.getSeeds() > 0) {
            return false;
        } else {
            return this.neighborBowl.checkBowlsEmpty();
        }
    }
}
