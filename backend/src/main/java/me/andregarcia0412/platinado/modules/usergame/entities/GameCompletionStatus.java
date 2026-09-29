package me.andregarcia0412.platinado.modules.usergame.entities;

import jakarta.persistence.*;

@Entity
@Table(
        name = "game_completion_status",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_game_completion_status_status", columnNames = "status")
        }
)
public class GameCompletionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false)
    private String status;

    protected GameCompletionStatus() {}

    public GameCompletionStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
