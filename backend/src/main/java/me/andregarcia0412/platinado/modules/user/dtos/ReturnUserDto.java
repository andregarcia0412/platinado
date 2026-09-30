package me.andregarcia0412.platinado.modules.user.dtos;

import me.andregarcia0412.platinado.modules.user.entities.User;
import me.andregarcia0412.platinado.modules.user.enums.UserRole;

import java.time.LocalDateTime;

public record ReturnUserDto(
        Integer id,
        String username,
        String email,
        UserRole role,
        String bio,
        String storageKey,
        LocalDateTime createdAt
) {
    public static ReturnUserDto fromEntity(User user) {
        return new ReturnUserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getBio(),
                user.getStorageKey(),
                user.getCreatedAt()
        );
    }
}
