package es.us.dp1.chess.tournament.challenge;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.chess.tournament.exceptions.AccessDeniedException;
import es.us.dp1.chess.tournament.exceptions.ResourceNotFoundException;
import es.us.dp1.chess.tournament.user.User;
import es.us.dp1.chess.tournament.user.UserService;

@Service
public class FriendlyChallengeService {

    private final FriendlyChallengeRepository repo;
    private final UserService userService;

    @Autowired
    public FriendlyChallengeService(FriendlyChallengeRepository repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public List<FriendlyChallenge> findAll() {
        return (List<FriendlyChallenge>) repo.findAll();
    }

    @Transactional(readOnly = true)
    public FriendlyChallenge findById(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("FriendlyChallenge", "id", id));
    }

    @Transactional
    public FriendlyChallenge save(FriendlyChallenge challenge) {
        return repo.save(challenge);
    }

    @Transactional(readOnly = true)
    public List<RankingEntry> getRanking(Integer challengeId) {
        FriendlyChallenge challenge = findById(challengeId);
        User currentUser = userService.findCurrentUser();
        if (!challenge.getParticipants().contains(currentUser)) {
            throw new AccessDeniedException("You are not a participant of this challenge!");
        }
        List<Object[]> points = repo.findParticipantPoints(challengeId);
        if (points.isEmpty()) {
            throw new ResourceNotFoundException("No finished matches found for this challenge yet");
        }
        return points.stream()
            .map(row -> new RankingEntry((User) row[0], ((Number) row[1]).intValue()))
            .sorted(Comparator.comparingInt(RankingEntry::getPoints).reversed())
            .collect(Collectors.toList());
    }

}
