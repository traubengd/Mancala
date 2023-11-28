package mancala.api.models;

import mancala.domain.IMancala;

public class MancalaDTO {

    private GameStatusDTO gameStatus;
    private PlayerDTO[] players;

    public MancalaDTO(IMancala mancala) {
        players = new PlayerDTO[2];

        players[0] = new PlayerDTO(mancala, mancala.getNameOfPlayerOne());
        players[1] = new PlayerDTO(mancala, mancala.getNameOfPlayerTwo());

        gameStatus = new GameStatusDTO(mancala);
    }

    public PlayerDTO[] getPlayers() {
        return players;
    }

    public void setPlayers(PlayerDTO[] players) {
        this.players = players;
    }

    public GameStatusDTO getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatusDTO gameStatus) {
        this.gameStatus = gameStatus;
    }
}
