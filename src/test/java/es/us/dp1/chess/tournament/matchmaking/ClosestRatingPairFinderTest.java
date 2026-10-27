package es.us.dp1.chess.tournament.matchmaking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import es.us.dp1.chess.tournament.challenge.matchmaking.ClosestRatingPairFinder;
import es.us.dp1.chess.tournament.challenge.matchmaking.InsufficientPlayersException;
import es.us.dp1.chess.tournament.challenge.matchmaking.InvalidRatingException;
import es.us.dp1.chess.tournament.challenge.matchmaking.ValidClosestRatingPairFinder;


public class ClosestRatingPairFinderTest {

    // This is your SUT:
    ClosestRatingPairFinder algorithm = new ValidClosestRatingPairFinder();

    @Test
    public void findsTheClosestPair() throws InsufficientPlayersException, InvalidRatingException {
        int[] result = algorithm.findClosestPair(new int[]{1000, 1200, 1050});
        assertArrayEquals(new int[]{0, 2}, result);
    }

    @Test
    public void breaksTiesWithSmallestIndexSum() throws InsufficientPlayersException, InvalidRatingException {
        int[] result = algorithm.findClosestPair(new int[]{0, 10, 14, 100, 200, 4});
        assertArrayEquals(new int[]{1, 2}, result);
    }

    @Test
    public void throwsWhenNotEnoughPlayers() {
        assertThrows(InsufficientPlayersException.class, () -> algorithm.findClosestPair(new int[]{1500}));
    }

    @Test
    public void throwsWhenNegativeRating() {
        assertThrows(InvalidRatingException.class, () -> algorithm.findClosestPair(new int[]{1500, -1}));
    }

    public void setAlgorithm(ClosestRatingPairFinder value){
        this.algorithm=value;
    }

}
