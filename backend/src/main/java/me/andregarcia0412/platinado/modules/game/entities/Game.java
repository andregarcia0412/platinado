package me.andregarcia0412.platinado.modules.game.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "game",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_game_slug", columnNames = "slug")
        }
)
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String slug;

    @Column(columnDefinition = "LONGTEXT")
    private String summary;

    @Column(name = "first_release_date")
    private LocalDateTime firstReleaseDate;

    @Column(name = "cover_image_storage_key", length = 255)
    private String coverImageStorageKey;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(
            name = "game_type_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_game_game_type1")
    )
    private GameType gameType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Game() {}

    public Game(String name, String slug, GameType gameType) {
        this.name = name;
        this.slug = slug;
        this.gameType = gameType;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getSummary() {
        return summary;
    }

    public LocalDateTime getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public String getCoverImageStorageKey() {
        return coverImageStorageKey;
    }

    public GameType getGameType() {
        return gameType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setFirstReleaseDate(LocalDateTime firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public void setCoverImageStorageKey(String coverImageStorageKey) {
        this.coverImageStorageKey = coverImageStorageKey;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
