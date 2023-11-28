package mancala.api.models;

public class PitDTO {

    private int index;
    private int nrOfStones;

    public PitDTO(int index, int nrOfStones) {
        this.index = index;
        this.nrOfStones = nrOfStones;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNrOfStones() {
        return nrOfStones;
    }

    public void setNrOfStones(int nrOfStones) {
        this.nrOfStones = nrOfStones;
    }
}
