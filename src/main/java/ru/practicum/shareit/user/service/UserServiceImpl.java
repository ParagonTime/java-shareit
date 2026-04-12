package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.InMemoryUserRepository;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final InMemoryUserRepository inMemoryUserRepository;

    @Override
    public UserDto createUser(NewUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        User newUser = inMemoryUserRepository.save(user);
        return UserMapper.userToDto(newUser);
    }

    @Override
    public UserDto updateUser(Long userId, UpdateUserRequest request) {
        User currentUser = inMemoryUserRepository.getUser(userId);
        if (request.getName() != null) {
            currentUser.setName(request.getName());
        }
        if (request.getEmail() != null) {
            currentUser.setEmail(request.getEmail());
        }
        User updated = inMemoryUserRepository.updateUser(userId, currentUser);
        return UserMapper.userToDto(updated);
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = inMemoryUserRepository.getUser(userId);
        return UserMapper.userToDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        inMemoryUserRepository.deleteUser(userId);
    }
}
