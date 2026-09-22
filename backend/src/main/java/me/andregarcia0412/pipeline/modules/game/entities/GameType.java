package me.andregarcia0412.pipeline.modules.game.entities;

import jakarta.persistence.*;

@Entity
@Table(
        name = "game_type",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_game_type_type", columnNames = "type")
        }
)
public class GameType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false)
    private String type;

    protected GameType() {}

    public GameType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
