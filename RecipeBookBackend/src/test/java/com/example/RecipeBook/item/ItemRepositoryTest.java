package com.example.RecipeBook.item;

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
        assertTrue(itemRepository.getTodoItems().isEmpty());
    }

    @Test
    public void testAddingToRepo() {
        getItem();
        assertEquals(1, itemRepository.getTodoItems().size());
    }

    @Test
    public void testDeletingFromRepo() {
        Item item = getItem();
        itemRepository.removeItem(item.publicId);
        assertTrue(itemRepository.getTodoItems().isEmpty());
    }

    @Test
    public void testGetItemById() {
        Item item = getItem();
        assertEquals(item, itemRepository.getItemByUUID(item.publicId));
    }

    @Test
    public void testGetStaticItems() {
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
