package me.andregarcia0412.pipeline.modules.game.entities;

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

    @Column()
    private String summary;

    @Column(name = "first_release_date")
    private LocalDateTime firstReleaseDate;

    @Column(name = "cover_image_storage_key", length = 255)
    private String coverImageStorageKey;

    @Column(name = "game_type_id", nullable = false)
    private Integer gameTypeId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Game() {}

    public Game(String name, String slug, Integer gameTypeId) {
        this.name = name;
        this.slug = slug;
        this.gameTypeId = gameTypeId;
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

    public Integer getGameTypeId() {
        return gameTypeId;
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

    public void setGameTypeId(Integer gameTypeId) {
        this.gameTypeId = gameTypeId;
    }
}
