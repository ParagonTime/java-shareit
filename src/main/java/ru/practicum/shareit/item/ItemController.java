package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    private static final String HEADER = "X-Sharer-User-Id";

    // @RequestHeader("X-Later-User-Id") long userId
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(@RequestHeader("X-Sharer-User-Id") Long userId,
                              @Valid @RequestBody NewItemRequest request) {
        return itemService.createItem(userId, request);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader(HEADER) Long userId,
                              @Positive(message = "itemId должен быть больше 0") @PathVariable("itemId") Long itemId,
                              @Valid @RequestBody UpdateItemRequest request
    ) {
        return itemService.updateItem(userId, itemId, request);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader(HEADER) Long userId,
                           @Positive(message = "itemId должен быть больше 0") @PathVariable("itemId") Long itemId
    ) {
        return itemService.getItem(userId, itemId);
    }

    @GetMapping
    public List<ItemDto> getUserItems(@RequestHeader(HEADER) long userId) {
        return itemService.getUserItems(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchItems(@RequestHeader(HEADER) long userId,
                                     @RequestParam("text") String text
    ) {
        return itemService.searchItems(userId, text);
    }
}
