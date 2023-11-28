package mancala.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void creatingAPlayerAlsoCreatesAnOpponent() {
        Player player = new Player();
        assertNotNull(player.getOpponent());
    }

    @Test
    public void creatingAnOpponentAlsoMakesFirstPlayerItsOpponent() {
        Player player = new Player();
        assertEquals(player, player.getOpponent().getOpponent());
    }

    @Test
    public void firstPlayerStartsWithHavingATurn() {
        Player player = new Player();
        assertTrue(player.getTurnStatus());
    }

    @Test
    public void secondPlayerStartsWithNotHavingTurn() {
        Player player = new Player();
        assertFalse(player.getOpponent().getTurnStatus());
    }

    @Test
    public void afterSwitchingTurnTheFirstPlayerDoesNotHAveTurnAndSecondPlayerDoesHaveTurn() {
        Player player = new Player();
        player.switchTurn();
        assertFalse(player.getTurnStatus());
        assertTrue(player.getOpponent().getTurnStatus());
    }
}