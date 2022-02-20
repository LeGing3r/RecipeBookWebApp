package com.example.RecipeBook.item;

import com.example.RecipeBook.measurement.Measurement;
import com.example.RecipeBook.measurement.Unit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Item item = getItem();
        itemRepository.save(item);
        assertEquals(1, itemRepository.getTodoItems().size());
    }

    @Test
    public void testDeletingFromRepo() {
        Item item = getItem();
        itemRepository.save(item);
        itemRepository.removeItem(item.publicId);
        assertTrue(itemRepository.getTodoItems().isEmpty());
    }

    @Test
    public void testGetItemById() {
        Item item = getItem();
        itemRepository.save(item);
        assertEquals(item, itemRepository.getItemByUUID(item.publicId));
    }

    @Test
    public void testGetStaticItems() {

    }

    private Item getItem() {
        var item = new Item("Banana");
        item.staticItem = new StaticItem();
        item.staticItem.aliases.add("Banana");
        item.measurement = new Measurement(1, Unit.NONE);
        return item;
    }


}
