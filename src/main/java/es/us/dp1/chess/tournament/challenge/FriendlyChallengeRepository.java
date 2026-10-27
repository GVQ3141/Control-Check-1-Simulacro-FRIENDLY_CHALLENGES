package es.us.dp1.chess.tournament.challenge;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FriendlyChallengeRepository extends CrudRepository<FriendlyChallenge, Integer> {

    @Query("SELECT u, SUM(CASE WHEN m.winner = u THEN 2 WHEN m.winner IS NULL THEN 1 ELSE 0 END) " +
        "FROM FriendlyChallenge c JOIN c.participants u JOIN c.matches m " +
        "WHERE c.id = :challengeId AND m.finish IS NOT NULL AND (m.white = u OR m.black = u) " +
        "GROUP BY u ORDER BY 2 DESC")
    List<Object[]> findParticipantPoints(@Param("challengeId") Integer challengeId);

}
