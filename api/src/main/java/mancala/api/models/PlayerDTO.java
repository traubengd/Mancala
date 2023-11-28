package mancala.api.models;

import mancala.domain.IMancala;

public class PlayerDTO {

    public String name;
    public String type;
    public boolean hasTurn;
    public PitDTO[] pits = new PitDTO[7];

    public PlayerDTO(
            IMancala mancala,
            String name) {
        this.name = name;
        this.hasTurn = mancala.isPlayersTurn(name);
        int firstHoleIndex = this.name == mancala.getNameOfPlayerOne() ? 0 : 7;
        for (int i = 0; i < 7; ++i) {
            this.pits[i] = new PitDTO(
                    firstHoleIndex + i,
                    mancala.getStonesForPit(i + firstHoleIndex));
        }
    }

    public String getName() {
        return name;
    }

    public boolean getHasTurn() {
        return hasTurn;
    }

    public PitDTO[] getPits() {
        return pits;
    }
}
