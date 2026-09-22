package me.andregarcia0412.pipeline.modules.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterRequestDto(
        @NotBlank(message = "username can't be blank")
        @Length(min = 1, max = 120, message = "username must be between 1 and 120 characters")
        String username,
        @NotBlank(message = "email can't be blank")
        @Email(message = "email must be a valid email")
        @Length(min = 1, max = 120, message = "email must be between 1 and 120 characters")
        String email,
        @NotBlank(message = "password can't be blank")
        @Length(min = 8, max = 32, message = "password length must be between 8 and 32")
        String password
) {
}
