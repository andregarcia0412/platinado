package me.andregarcia0412.pipeline.modules.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UpdateUserDto(
        @Pattern(regexp = "^[a-zA-Z0-9_.]+$", message = "username may only contain letters, digits, underscore and dot")
        @Length(min = 1, max = 120, message = "username must be between 1 and 120 characters")
        String username,
        @Email(message = "email must be a valid email")
        @Length(min = 1, max = 120, message = "email must be between 1 and 120 characters")
        String email,
        @Length(min = 1, max = 512, message = "bio must be between 1 and 512 characters")
        String bio
) {
}
