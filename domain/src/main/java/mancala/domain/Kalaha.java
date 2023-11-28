package mancala.domain;

public class Kalaha extends AbstractBowl {

    public Kalaha(Player player, int counter, PlayableBowl startingBowl) {
        this.setSeeds(0);
        this.setPlayer(player);
        this.setType("Kalaha");
        if (counter == 8) {
            counter--;
            this.setNeighbor(new PlayableBowl(player.getOpponent(), counter, startingBowl));
        } else if (counter == 1) {
            this.setNeighbor(startingBowl);
        }
    }

    void addMultipleToKalaha(int stolenSeeds) {
        this.setSeeds(this.getSeeds() + stolenSeeds);
    }

    public void takeOneAndPassRemainder(int seedsToPass) {
        if (checkIfInactivePlayerKalaha()) {
            this.getNeighbor().takeOneAndPassRemainder(seedsToPass);
        } else {
            seedsToPass--;
            this.incrementSeeds();
            if (seedsToPass > 0) {
                this.getNeighbor().takeOneAndPassRemainder(seedsToPass);
            }      
        }
    }

    private boolean checkIfInactivePlayerKalaha() {
        return this.getType() == "Kalaha" && this.getPlayer().getTurnStatus() == false;
    }

    public void doMove() {
        throw new IllegalMoveException("that bowl is a Kalaha");
    }
}