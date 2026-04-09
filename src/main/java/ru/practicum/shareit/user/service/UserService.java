package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;

public interface UserService {
    UserDto createUser(NewUserRequest request);

    UserDto updateUser(Long userId, UpdateUserRequest request);

    UserDto getUser(Long userId);

    void deleteUser(Long userId);
}
