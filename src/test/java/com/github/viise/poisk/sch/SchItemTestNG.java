package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class SchItemTestNG {

    @Test
    public void find() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add(null);
            add("Two");
            add("Three");
            add("Four");
        }};

        Search<String, String> schItem = new SchItem<>(items);
        String item = schItem.find("Three");

        assertEquals(item, "Three");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        List<String> items = new ArrayList<>();
        new SchItem<>(items).find("Two");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchItem<>(null).find("One");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_lookingItemIsNull() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchItem<>(items).find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        List<String> firstItems = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchItem<>(firstItems).find("Five");
    }
}
