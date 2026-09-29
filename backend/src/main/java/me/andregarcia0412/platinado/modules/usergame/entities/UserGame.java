package me.andregarcia0412.platinado.modules.usergame.entities;

import jakarta.persistence.*;
import me.andregarcia0412.platinado.modules.game.entities.Game;
import me.andregarcia0412.platinado.modules.user.entities.User;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_game")
public class UserGame {
    @EmbeddedId
    private UserGameKey id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(
            name = "id_user",
            foreignKey = @ForeignKey(name = "fk_User_has_Game_User")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("gameId")
    @JoinColumn(
            name = "id_game",
            foreignKey = @ForeignKey(name = "fk_User_has_Game_Game1")
    )
    private Game game;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
            name = "id_game_completion_status",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_UserGame_GameCompletionStatus1")
    )
    private GameCompletionStatus gameCompletionStatus;

    @Column(name = "hours_played", precision = 18, scale = 2)
    private BigDecimal hoursPlayed;

    @Column(name = "starting_date")
    private LocalDateTime startingDate;

    @Column(name = "finishing_date")
    private LocalDateTime finishingDate;

    @Column(precision = 3, scale = 1)
    private BigDecimal grade;

    @Column(columnDefinition = "LONGTEXT")
    private String note;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected UserGame() {}

    private UserGame(Builder b) {
        id = new UserGameKey(b.user.getId(), b.game.getId());
        user = b.user;
        game = b.game;
        hoursPlayed = b.hoursPlayed;
        startingDate = b.startingDate;
        finishingDate = b.finishingDate;
        grade = b.grade;
        note = b.note;
    }

    public UserGameKey getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Game getGame() {
        return game;
    }

    public GameCompletionStatus getGameCompletionStatus() {
        return gameCompletionStatus;
    }

    public BigDecimal getHoursPlayed() {
        return hoursPlayed;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public LocalDateTime getFinishingDate() {
        return finishingDate;
    }

    public BigDecimal getGrade() {
        return grade;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setGameCompletionStatus(GameCompletionStatus gameCompletionStatus) {
        this.gameCompletionStatus = gameCompletionStatus;
    }

    public void setHoursPlayed(BigDecimal hoursPlayed) {
        this.hoursPlayed = hoursPlayed;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public void setFinishingDate(LocalDateTime finishingDate) {
        this.finishingDate = finishingDate;
    }

    public void setGrade(BigDecimal grade) {
        this.grade = grade;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static class Builder {
        private final User user;
        private final Game game;
        private final GameCompletionStatus gameCompletionStatus;
        private BigDecimal hoursPlayed;
        private LocalDateTime startingDate;
        private LocalDateTime finishingDate;
        private BigDecimal grade;
        private String note;

        private Builder(User user, Game game, GameCompletionStatus gameCompletionStatus) {
            this.user = user;
            this.game = game;
            this.gameCompletionStatus = gameCompletionStatus;
        }

        public Builder hoursPlayed(BigDecimal hoursPlayed) {
            this.hoursPlayed = hoursPlayed;
            return this;
        }

        public Builder startingDate(LocalDateTime startingDate) {
            this.startingDate = startingDate;
            return this;
        }

        public Builder finishingDate(LocalDateTime finishingDate) {
            this.finishingDate = finishingDate;
            return this;
        }

        public Builder grade(BigDecimal grade) {
            this.grade = grade;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }

        public UserGame build() {
            return new UserGame(this);
        }
    }
}
