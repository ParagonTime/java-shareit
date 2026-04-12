package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewUserRequest {
    @NotBlank(message = "имя не должно быть пустым")
    private String name;
    @NotBlank(message = "email не должен быть пустой")
    @Email(message = "неверный формат email")
    private String email;
}
