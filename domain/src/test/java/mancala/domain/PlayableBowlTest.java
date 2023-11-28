package mancala.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class PlayableBowlTest {

    @Test
    public void playableBowlShouldKnowItsType() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals("Playable Bowl", bowl.getType());
    }

    @Test
    public void firstPlayableBowlShouldGenerateFullLoopingPlayingBoard() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals(bowl, bowl.moveThroughBoard(14));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 7, 8, 9, 10, 11, 12 })
    public void playableBowlsShouldBePlacedAtCorrectLocationsOnBoard(int playableBowlPositions) {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals("Playable Bowl", bowl.moveThroughBoard(playableBowlPositions).getType());
    }

    @ParameterizedTest
    @ValueSource(ints = { 6, 13 })
    public void kalahaBowlsShouldBePlacedAtCorrectLocationsOnBoard(int kalahaPositions) {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals("Kalaha", bowl.moveThroughBoard(kalahaPositions).getType());
    }

    @Test
    public void playableBowlShouldStartWithFourSeedsInsideIt() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals(4, bowl.getSeeds());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6 })
    public void bowlsUpToFirstKalahaShouldBelongToFirstPlayer(int firstPlayerPositions) {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals(player, bowl.moveThroughBoard(firstPlayerPositions).getPlayer());
    }

    @ParameterizedTest
    @ValueSource(ints = { 7, 8, 9, 10, 11, 12, 13 })
    public void bowlsAfterFirstKalahaShouldBelongToSecondPlayer(int opponentPlayerPositions) {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals(player.getOpponent(), bowl.moveThroughBoard(opponentPlayerPositions).getPlayer());
    }

    @Test
    public void playableBowlShouldBeEmptyAfterMakingFirstMove() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.doMove();
        assertEquals(0, bowl.getSeeds());
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4 })
    public void afterMakingMoveTheNeighboringBowlsShouldTakeOneSeedAndPassTheRemainderUntilNoneRemainFromOriginalBowl(
            int bowlsAfterFirst) {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.doMove();
        assertEquals(5, bowl.moveThroughBoard(bowlsAfterFirst).getSeeds());
    }

    @Test
    public void neighborsTakingOneSeedAndPassingRemainderShouldAlsoWorkCorrectlyWhenTheyHaveDifferentCurrentContents() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(1).setSeeds(7);
        bowl.moveThroughBoard(2).setSeeds(2);
        bowl.moveThroughBoard(5).setSeeds(1);
        bowl.doMove();
        assertEquals(8, bowl.moveThroughBoard(1).getSeeds());
        assertEquals(3, bowl.moveThroughBoard(2).getSeeds());
        assertEquals(1, bowl.moveThroughBoard(5).getSeeds());
    }

    @Test
    public void afterMakingMoveTheBowlBeyondTheAmountOfSeedsShouldRemainTheSame() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.doMove();
        assertEquals(4, bowl.moveThroughBoard(5).getSeeds());
    }

    @Test
    public void ifSeedsInPlayedBowlIsHighEnoughTheSeedsShouldLoopAroundAndAlsoPlaceOneSeedBackInThatBowl() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.setSeeds(15);
        bowl.doMove();
        assertEquals(1, bowl.getSeeds());
    }

    @ParameterizedTest
    @ValueSource(ints = { 7, 8, 9, 10, 11, 12 })
    public void tryingToDoAMoveFromBowlsOfOpponentResultsInException(int opponentPlayerPositions) {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertThrows(IllegalMoveException.class, () -> {
            bowl.moveThroughBoard(opponentPlayerPositions).doMove();
        });
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 3, 4, 5 })
    public void afterMakingAMoveTurnSwitchesAndFirstPlayerBowlsBecomeUnavailable(int firstPlayerPositions) {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.doMove();
        assertThrows(IllegalMoveException.class, () -> {
            bowl.moveThroughBoard(firstPlayerPositions).doMove();
        });
    }

    @ParameterizedTest
    @ValueSource(ints = { 7, 8, 9, 10, 11, 12 })
    public void afterMakingAMoveTurnSwitchesAndOpponentBowlsBecomeAvailableForPlaying(int opponentPlayerPositions) {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.doMove();
        assertDoesNotThrow(() -> {
            bowl.moveThroughBoard(opponentPlayerPositions).doMove();
        });
    }

    @Test
    public void tryingToDoAMoveFromAnEmptyBowlResultsInException() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.setSeeds(0);
        assertThrows(IllegalMoveException.class, () -> {
            bowl.doMove();
        });
    }

    @Test
    public void afterAMoveThatEndsInEmptyBowlOfActivePlayerTheSeedForThatBowlGoesImmediatelyToThePlayersKalaha() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(4).setSeeds(0);
        bowl.moveThroughBoard(8).setSeeds(0);
        bowl.doMove();
        assertEquals(0, bowl.moveThroughBoard(4).getSeeds());
        assertEquals(1, bowl.moveThroughBoard(6).getSeeds());
    }

    @Test
    public void afterAMoveThatEndsInEmptyBowlOfOppposingPlayerTheSeedForThatBowlShouldRemainThere() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(8).setSeeds(0);
        bowl.moveThroughBoard(4).doMove();
        assertEquals(1, bowl.moveThroughBoard(8).getSeeds());
        assertEquals(1, bowl.moveThroughBoard(6).getSeeds());
    }

    @Test
    public void afterAMoveThatEndsInEmptyBowlOfActivePlayerTheSeedsInOpponentBowlAcrossShouldBeStolenToActivePlayerKalaha() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(4).setSeeds(0);
        bowl.doMove();
        assertEquals(0, bowl.moveThroughBoard(8).getSeeds());
        assertEquals(5, bowl.moveThroughBoard(6).getSeeds());
    }

    @Test
    public void stealingShouldNotOccurWhenLandingFinalSeedInEmptyBowlOfInactivePlayer() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(9).setSeeds(0);
        bowl.moveThroughBoard(5).doMove();
        assertEquals(1, bowl.moveThroughBoard(6).getSeeds());
        assertEquals(1, bowl.moveThroughBoard(9).getSeeds());
    }

    @Test
    public void stealingOfSeedsShouldResultInCorrectAmountInKalahaAlsoWhenThereAreAlreadySeedsThere() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(4).setSeeds(0);
        bowl.moveThroughBoard(6).setSeeds(5);
        bowl.doMove();
        assertEquals(0, bowl.moveThroughBoard(8).getSeeds());
        assertEquals(10, bowl.moveThroughBoard(6).getSeeds());
    }
}