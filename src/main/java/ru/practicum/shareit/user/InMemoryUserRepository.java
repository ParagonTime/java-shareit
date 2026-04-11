package ru.practicum.shareit.user;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ConflictEmailException;
import ru.practicum.shareit.exception.NoFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class InMemoryUserRepository {

    private static final String DUPLICATE_EMAIL_MESSAGE = "Такой email уже используется";
    private static final String NOT_FOUND_USER_MESSAGE = "Пользователь с таким id не найден";


    private final HashMap<Long, User> base;
    private final Set<String> emails;
    private Long countUsers;

    public InMemoryUserRepository() {
        this.base = new HashMap<>();
        this.emails = new HashSet<>();
        this.countUsers = 0L;
    }

    public User save(User user) {
        if (emails.contains(user.getEmail())) {
            throw new ConflictEmailException(DUPLICATE_EMAIL_MESSAGE + emails);
        }
        User newUser = new User();
        newUser.setId(++countUsers);
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        base.put(newUser.getId(), newUser);
        emails.add(newUser.getEmail());
        return newUser;
    }

    public User updateUser(Long userId, User user) {
        checkUserExist(userId);
        User userByEmail = getUserByEmail(user.getEmail());
        if (userByEmail != null && !Objects.equals(userByEmail.getId(), userId)) {
            throw new ConflictEmailException(DUPLICATE_EMAIL_MESSAGE + emails);
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
}
