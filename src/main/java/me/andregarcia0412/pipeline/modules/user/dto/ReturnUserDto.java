package me.andregarcia0412.pipeline.modules.user.dto;

import me.andregarcia0412.pipeline.modules.user.entities.User;

import java.time.LocalDateTime;

public record ReturnUserDto(
        Integer id,
        String name,
        String email,
        LocalDateTime createdAt
) {
    public static ReturnUserDto fromEntity(User user) {
        return new ReturnUserDto(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt());
    }
}
