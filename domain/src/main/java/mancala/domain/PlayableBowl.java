package mancala.domain;

public class PlayableBowl extends AbstractBowl {

    public PlayableBowl(Player player) {
        this.setSeeds(4);
        this.setPlayer(player);
        this.setType("Playable Bowl");
        this.setNeighbor(new PlayableBowl(player, 13, this));
    }

    public PlayableBowl(Player player, int counter, PlayableBowl startingBowl) {
        this.setSeeds(4);
        this.setPlayer(player);
        this.setType("Playable Bowl");
        if (counter == 9) {
            counter--;
            this.setNeighbor(new Kalaha(player, counter, startingBowl));
        } else if (counter == 2) {
            counter--;
            this.setNeighbor(new Kalaha(player, counter, startingBowl));
        } else {
            counter--;
            this.setNeighbor(new PlayableBowl(player, counter, startingBowl));
        }
    }

    public void doMove() {
        if (checkIfActivePlayerBowlAndNotEmpty()) {
            int seedsToMove = this.getSeeds();
            this.setSeeds(0);
            this.getNeighbor().takeOneAndPassRemainder(seedsToMove);
            this.moveThroughBoard(findKalahaDistance() + 1).deactivatePlayerIfBowlsEmpty(); // deactivate opponent if
                                                                                            // bowls empty
            this.moveThroughBoard(findKalahaDistance() + 8).deactivatePlayerIfBowlsEmpty(); // deactive self if bowls
                                                                                            // empty
        } else if (this.getSeeds() == 0) {
            throw new IllegalMoveException("that bowl is empty");
        } else {
            throw new IllegalMoveException("that bowl belongs to the opponent");
        }
    }

    public void takeOneAndPassRemainder(int seedsToPass) {
        seedsToPass--;
        if (checkIfLastSeedIntoActivePlayerEmptyBowl(seedsToPass)) {
            this.moveThroughBoard(findKalahaDistance()).incrementSeeds();
            ((PlayableBowl) this.moveThroughBoard(findKalahaDistance() * 2)).stealContentToActivePlayerKalaha();
            this.getPlayer().switchTurn();
        } else {
            this.incrementSeeds();
            if (seedsToPass > 0) {
                this.getNeighbor().takeOneAndPassRemainder(seedsToPass);
            } else {
                this.getPlayer().switchTurn();
            }
        }
    }

    private void stealContentToActivePlayerKalaha() {
        int stolenSeeds = this.getSeeds();
        this.setSeeds(0);
        ((Kalaha) this.moveThroughBoard(findKalahaDistance() + 7)).addMultipleToKalaha(stolenSeeds);
    }

    private boolean checkIfActivePlayerBowlAndNotEmpty() {
        return this.getPlayer().getTurnStatus() == true && this.getSeeds() > 0;
    }

    private boolean checkIfLastSeedIntoActivePlayerEmptyBowl(int seedsToPass) {
        return seedsToPass == 0 && this.getSeeds() == 0 && this.getPlayer().getTurnStatus();
    }
}