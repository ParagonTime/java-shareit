package ru.practicum.shareit.item;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NoFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.InMemoryUserRepository;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final InMemoryItemRepository itemRepository;
    private final InMemoryUserRepository userRepository;

    private static final String NO_FOUND_ITEM = "Item не найден с id:";

    @Override
    public ItemDto createItem(Long userId, NewItemRequest request) {
        User user = userRepository.getUser(userId);
        Item item = new Item();
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setAvailable(request.getAvailable());
        item.setOwner(user.getId());
        Item newItem = itemRepository.saveItem(item);
        return ItemMapper.toItemDto(newItem);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, UpdateItemRequest request) {
        if (userId == null || userId < 0) {
            throw new ValidationException("Invalid parameter: " + userId);
        }
        if (itemId == null || itemId < 0) {
            throw new ValidationException("Invalid parameter: " + itemId);
        }

        User user = userRepository.getUser(userId);
        Item item = itemRepository.getItem(itemId);
        if (!Objects.equals(item.getOwner(), user.getId())) {
            throw new NoFoundException(NO_FOUND_ITEM + itemId);
        }
        if (request.getName() != null) {
            item.setName(request.getName());
        }
        if (request.getAvailable() != null) {
            item.setAvailable(request.getAvailable());
        }
        if (request.getDescription() != null) {
            item.setDescription(request.getDescription());
        }
        Item updatedItem = itemRepository.updateItem(userId, itemId, item);
        return ItemMapper.toItemDto(updatedItem);
    }

    @Override
    public ItemDto getItem(Long userId, Long itemId) {
        User user = userRepository.getUser(userId);
        Item item = itemRepository.getItem(itemId);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getUserItems(Long userId) {
        User user = userRepository.getUser(userId);
        return itemRepository.getUserItems(userId).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> searchItems(Long userId, String text) {
        User user = userRepository.getUser(userId);
        return itemRepository.searchItems(userId, text).stream()
                .map(ItemMapper::toItemDto)
                .toList();
    }
}
