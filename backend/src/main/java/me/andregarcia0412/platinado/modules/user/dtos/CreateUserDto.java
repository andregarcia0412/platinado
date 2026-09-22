package me.andregarcia0412.platinado.modules.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record CreateUserDto(
        @NotBlank(message = "username can't be blank")
        @Pattern(regexp = "^[a-zA-Z0-9_.]+$", message = "username may only contain letters, digits, underscore and dot")
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
