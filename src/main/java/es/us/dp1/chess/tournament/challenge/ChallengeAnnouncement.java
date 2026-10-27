package es.us.dp1.chess.tournament.challenge;

import java.time.LocalDateTime;
import java.util.List;

import es.us.dp1.chess.tournament.model.BaseEntity;
import es.us.dp1.chess.tournament.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChallengeAnnouncement extends BaseEntity {

    @NotNull
    @Size(min = 10, max = 60)
    String message;

    @NotNull
    @Enumerated(EnumType.STRING)
    AnnouncementStatus status;

    LocalDateTime sentAt;

    @NotNull
    @ManyToOne
    FriendlyChallenge challenge;

    @NotNull
    @Size(min = 2)
    @ManyToMany
    @JoinTable(name = "challenge_announcement_recipients",
        joinColumns = @JoinColumn(name = "challenge_announcement_id"),
        inverseJoinColumns = @JoinColumn(name = "recipient_id"))
    List<User> recipients;

}
