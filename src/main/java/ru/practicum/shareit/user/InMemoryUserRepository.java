package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ConflictEmailException;
import ru.practicum.shareit.exception.NoFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class InMemoryUserRepository {

    private static final String DUPLICATE_EMAIL_MESSAGE = "Такой email уже используется";
    private static final String NOT_FOUND_USER_MESSAGE = "Пользователь с таким id не найден";


    private final HashMap<Long, User> base;
    private Long countUsers;

    public InMemoryUserRepository() {
        this.base = new HashMap<>();
        this.countUsers = 0L;
    }

    public User save(User user) {
        checkEmailExist(user.getEmail());
        User newUser = new User();
        newUser.setId(++countUsers);
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        base.put(newUser.getId(), newUser);
        return newUser;
    }

    public User updateUser(Long userId, User user) {
        checkUserExist(userId);
        User userByEmail = getUserByEmail(user.getEmail());
        if (userByEmail != null && !Objects.equals(userByEmail.getId(), userId)) {
            throw new ConflictEmailException(DUPLICATE_EMAIL_MESSAGE + userByEmail.getId());
        }
        base.put(userId, user);
        return base.get(userId);
    }

    public User getUser(Long userId) {
        checkUserExist(userId);
        return base.get(userId);
    }

    public void deleteUser(Long userId) {
        checkUserExist(userId);
        base.remove(userId);
    }

    private User getUserByEmail(String email) {
        return base.values().stream()
                .filter(usr -> usr.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    private void checkUserExist(Long userId) {
        if (!base.containsKey(userId)) {
            throw new NoFoundException(NOT_FOUND_USER_MESSAGE + userId);
        }
    }

    private void checkEmailExist(String email) {
        List<String> emails = base.values().stream()
                .map(User::getEmail)
                .toList();
        if (emails.contains(email)) {
            throw new ConflictEmailException(DUPLICATE_EMAIL_MESSAGE + email);
        }
    }
}