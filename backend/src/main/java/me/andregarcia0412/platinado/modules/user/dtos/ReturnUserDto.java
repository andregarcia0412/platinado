package me.andregarcia0412.platinado.modules.user.dtos;

import me.andregarcia0412.platinado.modules.user.entities.User;

import java.time.LocalDateTime;

public record ReturnUserDto(
        Integer id,
        String username,
        String email,
        String bio,
        String storageKey,
        LocalDateTime createdAt
) {
    public static ReturnUserDto fromEntity(User user) {
        return new ReturnUserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getStorageKey(),
                user.getCreatedAt()
        );
    }
}
