package com.example.shoppinglist.item;

import com.example.shoppinglist.errors.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void testGetTodoItems() {
        clearDb();
        assertTrue(itemRepository.getTodoItems().isEmpty());
    }

    private void clearDb() {
        itemRepository.getTodoItems().forEach(item -> itemRepository.removeItem(item.publicId));
    }

    @Test
    public void testAddingToRepo() {
        var item = getItem();

        assertEquals(item, itemRepository.getItemByUUID(item.publicId));
    }

    @Test
    public void testDeletingFromRepo() {
        var item = getItem();

        itemRepository.removeItem(item.publicId);

        assertThrows(ItemNotFoundException.class, () -> itemRepository.getItemByUUID(item.publicId));
    }

    @Test
    public void testGetItemById() {
        var item = getItem();

        assertEquals(item, itemRepository.getItemByUUID(item.publicId));
    }

    @Test
    public void testGetStaticItems() {
        getItem();

        assertFalse(itemRepository.getStaticItemsFromAlias("banana").isEmpty());
        assertFalse(itemRepository.getStaticItemsFromAlias("bananas").isEmpty());
        assertFalse(itemRepository.getStaticItemsFromAlias("Banana").isEmpty());
    }

    @Test
    public void testGetSimilarItems() {
        var item = getItem();

        assertTrue(itemRepository.getSimilarItemsFromAlias("bananas").contains(item));
    }

    private Item getItem() {
        var item = new Item("Banana");
        item.measurement = new Measurement(1, Unit.NONE);
        item.staticItem = (StaticItem) itemRepository.getStaticItemsFromAlias("banana").toArray()[0];
        itemRepository.save(item);
        return item;
    }


}
