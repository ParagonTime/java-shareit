package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewItemRequest {
    @NotBlank(message = "Имя должно быть не пустое")
    private String name;
    @NotBlank(message = "У Item должно быть описание")
    private String description;
    @NotNull(message = "Укажите статус Item")
    private Boolean available;
}
