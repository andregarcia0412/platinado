package me.andregarcia0412.pipeline.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateUserDto(
        @Length(min = 1, max = 120, message = "name must be between 1 and 120 characters")
        String name,
        @Email(message = "email must be a valid email")
        @Length(min = 1, max = 160, message = "email must be between 1 and 160 characters")
        String email
) {
}
