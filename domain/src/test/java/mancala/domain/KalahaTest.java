package mancala.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class KalahaTest {

    @Test
    public void kalahaShouldKnowItsType() {
        Player player = new Player();
        Kalaha kalaha = new Kalaha(player, 0, null);
        assertEquals("Kalaha", kalaha.getType());
    }

    @ParameterizedTest
    @ValueSource(ints = { 6, 13 })
    public void kalahaShouldStartWithZeroSeeds(int kalahaPositions) {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertEquals(0, bowl.moveThroughBoard(kalahaPositions).getSeeds());
    }

    @Test
    public void whenDividingSeedsTheOwnKalahaShouldBeIncluded() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(5).doMove();
        assertEquals(1, bowl.moveThroughBoard(6).getSeeds());
    }

    @Test
    public void whenDividingSeedsTheOpponentKalahaShouldBeSkipped() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(5).setSeeds(15);
        bowl.moveThroughBoard(5).doMove();
        assertEquals(0, bowl.moveThroughBoard(13).getSeeds());
    }

    @Test
    public void afterSkippingOpponentKalahaTheAmountOfSeedsThatShouldBePassedShouldRemainUnchanged() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        bowl.moveThroughBoard(5).setSeeds(8);
        bowl.moveThroughBoard(5).doMove();
        assertEquals(5, bowl.getSeeds());
        assertEquals(4, bowl.moveThroughBoard(1).getSeeds());
    }

    @Test
    public void tryingToDoAMoveFromAKalahaResultsInException() {
        Player player = new Player();
        PlayableBowl bowl = new PlayableBowl(player);
        assertThrows(IllegalMoveException.class, () -> {
            bowl.moveThroughBoard(6).doMove();
        });
    }
}