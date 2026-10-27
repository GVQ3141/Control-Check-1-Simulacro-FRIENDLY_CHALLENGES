package es.us.dp1.chess.tournament;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import es.us.dp1.chess.tournament.challenge.FriendlyChallengeRepository;
import es.us.dp1.chess.tournament.user.User;
import jakarta.persistence.EntityManager;

@DataJpaTest
public class Test6 {

    @Autowired
    FriendlyChallengeRepository fcr;

    @Autowired
    EntityManager em;

    @Test
    void testParticipantPointsForChallenge1() {
        // GIVEN: el desafío con ID=1 está precargado mediante data.sql, con dos
        // partidas finalizadas: la 15 (ganador: usuario 4) y la 20 (empate)
        Integer challengeId = 1;

        List<Object[]> result = fcr.findParticipantPoints(challengeId);

        Map<Integer,Integer> points = new HashMap<>();
        for (Object[] row : result) {
            Integer userId = ((User) row[0]).getId();
            Integer p = ((Number) row[1]).intValue();
            points.put(userId, p);
        }

        // THEN: el usuario 4 gana 2 puntos por la 15 y 1 por el empate de la 20; el
        // usuario 5 solo gana 1 punto, por el empate de la 20
        assertThat(points.get(4)).isEqualTo(3);
        assertThat(points.get(5)).isEqualTo(1);
        assertThat(points).hasSize(2);
    }

    @Test
    void testParticipantPointsForChallengeWithoutFinishedMatches() {
        // GIVEN: el desafío con ID=2 solo tiene una partida (21) sin finalizar
        Integer challengeId = 2;

        List<Object[]> result = fcr.findParticipantPoints(challengeId);

        // No hay partidas finalizadas → la lista debe ser vacía
        assertThat(result).isEmpty();
    }

}
