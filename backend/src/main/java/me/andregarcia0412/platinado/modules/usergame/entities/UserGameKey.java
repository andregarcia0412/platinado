package me.andregarcia0412.platinado.modules.usergame.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserGameKey implements Serializable {
    @Column(name = "id_user")
    private Integer userId;

    @Column(name = "id_game")
    private Integer gameId;

    protected UserGameKey() {}

    public UserGameKey(Integer userId, Integer gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getGameId() {
        return gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserGameKey that)) return false;
        return Objects.equals(userId, that.userId) && Objects.equals(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, gameId);
    }
}
