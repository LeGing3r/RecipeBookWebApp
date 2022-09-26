package com.example.shoppinglist.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    StaticItemRepository staticItemRepository;

    @Test
    public void testGetTodoItems() {
        clearDb();
        assertTrue(itemRepository.getTodoItems().isEmpty());
    }

    private void clearDb() {
        itemRepository.getTodoItems().forEach(item -> itemRepository.delete(item));
    }

    @Test
    public void testAddingToRepo() {
        var item = getItem();

        assertEquals(item, itemRepository.findByPublicId(item.publicId));
    }

    @Test
    public void testDeletingFromRepo() {
        var item = getItem();

        itemRepository.delete(item);

        assertNull(itemRepository.findByPublicId(item.publicId));
    }

    @Test
    public void testGetItemById() {
        var item = getItem();

        assertEquals(item, itemRepository.findByPublicId(item.publicId));
    }

    @Test
    public void testGetStaticItems() {
        getItem();

        assertFalse(staticItemRepository.findWhereAliasContains("banana").isEmpty());
        assertFalse(staticItemRepository.findWhereAliasContains("bananas").isEmpty());
        assertFalse(staticItemRepository.findWhereAliasContains("Banana").isEmpty());
    }

    @Test
    public void testGetSimilarItems() {
        var item = getItem();
        var bananaItem = itemRepository.findItemsByStaticItemAliases("bananas");

        assertTrue(bananaItem.contains(item));
    }

    private Item getItem() {
        var item = new Item("Banana");

        item.measurement = new Measurement(1, Unit.NONE);
        item.staticItem = staticItemRepository.findWhereAliasContains("banana").iterator().next();
        item.publicId = UUID.randomUUID();
        itemRepository.save(item);

        return item;
    }


}
