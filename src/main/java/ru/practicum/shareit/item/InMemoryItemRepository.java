package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class InMemoryItemRepository {
    
    private final Map<Long, Item> base;
    private Long countItems;

    public InMemoryItemRepository() {
        this.base = new HashMap<>();
        this.countItems = 0L;
    }
    
    public Item saveItem(Item item) {
        item.setId(++countItems);
        base.put(item.getId(), item);
        return base.get(item.getId());
    }

    public Item getItem(Long itemId) {
        Item item = base.get(itemId);
        if (item == null) {
            throw new RuntimeException(); //не найден
        }
        return item;
    }

    public Item updateItem(Long userId, Long itemId, Item item) {
        base.put(item.getId(), item);
        return base.get(item.getId());
    }

    public List<Item> getUserItems(Long userId) {
        return base.values().stream()
                .filter(itm -> Objects.equals(itm.getOwner(), userId))
                .toList();
    }

    public List<Item> searchItems(Long userId, String text) {
        return base.values().stream()
                .filter(itm -> itm.getName().contains(text) || itm.getDescription().contains(text))
                .toList();
    }
}

