package es.us.dp1.chess.tournament.challenge;

import es.us.dp1.chess.tournament.user.User;

public class RankingEntry {

    private User user;
    private Integer points;

    public RankingEntry(User user, Integer points) {
        this.user = user;
        this.points = points;
    }

    public User getUser() {
        return user;
    }

    public Integer getPoints() {
        return points;
    }

}
