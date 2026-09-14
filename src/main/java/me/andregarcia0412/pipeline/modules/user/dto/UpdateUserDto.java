package me.andregarcia0412.pipeline.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateUserDto(
        @NotBlank(message = "name can't be blank")
        @Length(max = 120 )
        String name,
        @NotBlank(message = "email can't be blank")
        @Email(message = "email must be a valid email")
        @Length(max = 160)
        String email
) {
}
