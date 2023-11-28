package mancala.domain;

import org.junit.jupiter.api.Test;

import mancala.domain.AbstractBowl.DomainWinner;
import static org.junit.jupiter.api.Assertions.*;

public class BowlTest {

    @Test
    public void bowlShouldKnowItsPlayer() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals(player, bowl.getPlayer());
    }

    @Test
    public void bowlShouldHaveANeighbor() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertNotNull(bowl.getNeighbor());
    }

    @Test
    public void bowlShouldKnowItsContents() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals(4, bowl.getSeeds());
    }

    @Test
    public void bowlShouldBeAbleToIterateMultipleStepsThroughPlayingBoard() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals(bowl.getNeighbor().getNeighbor(), bowl.moveThroughBoard(2));
    }

    @Test
    public void afterMakingMoveThatDoesNotEndInPlayerKalahaTheTurnIsSwitched() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.doMove();
        assertFalse(player.getTurnStatus());
        assertTrue(player.getOpponent().getTurnStatus());
    }

    @Test
    public void afterMakingMoveThatEndsInActivePlayerKalahaTheTurnIsNotSwitched() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(2).doMove();
        assertTrue(player.getTurnStatus());
        assertFalse(player.getOpponent().getTurnStatus());
    }

    @Test
    public void ifAfterAMoveIsMadeThePlayableBowlsOfTheNowActivePlayerAreAllEmptyTheGameShouldEndAndBothPlayersAreInactive() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(7).setSeeds(0);
        bowl.moveThroughBoard(8).setSeeds(0);
        bowl.moveThroughBoard(9).setSeeds(0);
        bowl.moveThroughBoard(10).setSeeds(0);
        bowl.moveThroughBoard(11).setSeeds(0);
        bowl.moveThroughBoard(12).setSeeds(0);
        bowl.doMove();
        assertFalse(player.getTurnStatus());
        assertFalse(player.getOpponent().getTurnStatus());
    }

    @Test
    public void ifAfterAMoveIsMadeThatEndsInPlayerKalahaAndPlayableBowlsOfActivePlayerAreAllEmptyTheGameShouldEndAndBothPlayersAreInactive() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(0).setSeeds(0);
        bowl.moveThroughBoard(1).setSeeds(0);
        bowl.moveThroughBoard(2).setSeeds(0);
        bowl.moveThroughBoard(3).setSeeds(0);
        bowl.moveThroughBoard(4).setSeeds(0);
        bowl.moveThroughBoard(5).setSeeds(1);
        bowl.moveThroughBoard(5).doMove();
        assertFalse(player.getTurnStatus());
        assertFalse(player.getOpponent().getTurnStatus());
    }

    @Test
    public void afterAMoveIsMadeThatLeavesPlayersBowlsEmptyButDoesNotEndInKalahaGameShouldContinue() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(0).setSeeds(0);
        bowl.moveThroughBoard(1).setSeeds(0);
        bowl.moveThroughBoard(2).setSeeds(0);
        bowl.moveThroughBoard(3).setSeeds(0);
        bowl.moveThroughBoard(4).setSeeds(0);
        bowl.moveThroughBoard(5).setSeeds(2);
        bowl.moveThroughBoard(5).doMove();
        assertFalse(player.getTurnStatus());
        assertTrue(player.getOpponent().getTurnStatus());
    }

    @Test
    public void whenCheckForWinnerIsMadeButGameIsNotOverWithAnActivePlayerGameDeclaresNoOneWinsYet() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals(DomainWinner.CONTINUE_GAME, bowl.checkWinner());
    }

    @Test
    public void whenBothPlayersAreInactiveAndPlayer1HasMoreSeedsInKalahaGameShouldIndicatePlayer1Wins() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(6).setSeeds(14);
        bowl.moveThroughBoard(13).setSeeds(10);
        player.deactivatePlayer();
        assertEquals(DomainWinner.FIRST_PLAYER, bowl.checkWinner());
    }

    @Test
    public void whenBothPlayersAreInactiveAndPlayer2HasMoreSeedsInKalahaGameShouldIndicatePlayer2Wins() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(6).setSeeds(12);
        bowl.moveThroughBoard(13).setSeeds(14);
        player.deactivatePlayer();
        assertEquals(DomainWinner.SECOND_PLAYER, bowl.checkWinner());
    }

    @Test
    public void whenBothPlayersAreInactiveAndHaveEqualSeedsInKalahaGameShouldIndicateDraw() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(6).setSeeds(14);
        bowl.moveThroughBoard(13).setSeeds(14);
        player.deactivatePlayer();
        assertEquals(DomainWinner.EQUAL, bowl.checkWinner());
    }
}