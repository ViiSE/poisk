package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchItemsTestNG {

    @Test
    public void find_string() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        List<String> lookingItems = new ArrayList<String>() {{
            add("Two");
            add("Four");
            add("Five");
        }};

        SearchList<String> schItems = new SchItems<>(lookingItems);
        List<String> result = schItems.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("Two"));
        assertTrue(result.contains("Four"));
    }

    @Test
    public void find_integer() throws NotFoundException {
        List<Integer> items = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }};

        List<Integer> lookingItems = new ArrayList<Integer>() {{
            add(2);
            add(4);
            add(5);
        }};

        SearchList<Integer> schItems = new SchItems<>(lookingItems);
        List<Integer> result = schItems.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains(2));
        assertTrue(result.contains(4));
    }

    @Test
    public void find_boolean() throws NotFoundException {
        List<Boolean> items = new ArrayList<Boolean>() {{
            add(true);
            add(false);
        }};

        List<Boolean> lookingItems = new ArrayList<Boolean>() {{
            add(false);
        }};

        SearchList<Boolean> schItems = new SchItems<>(lookingItems);
        List<Boolean> result = schItems.find(items);

        assertEquals(result.size(), 1);
        assertTrue(result.contains(false));
    }

    @Test
    public void find_ctor2() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        SearchList<String> schItems = new SchItems<>("Two", "Four", "Five");
        List<String> result = schItems.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("Two"));
        assertTrue(result.contains("Four"));
    }

    @Test
    public void find_withNull() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add(null);
            add("One");
            add("Two");
            add("Three");
            add(null);
            add("Four");
        }};

        List<String> lookingItems = new ArrayList<String>() {{
            add(null);
            add("Two");
            add("Four");
            add(null);
            add("Five");
        }};

        SearchList<String> schItems = new SchItems<>(lookingItems);
        List<String> result = schItems.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("Two"));
        assertTrue(result.contains("Four"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_lookingItemsIsEmpty() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        List<String> lookingItems = new ArrayList<>();

        new SchItems<>(items).find(lookingItems);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_lookingItemsIsNull() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("Two");
            add("Four");
            add("Five");
        }};

        new SchItems<>(items).find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        List<String> items = null;

        List<String> lookingItems = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchItems<>(items).find(lookingItems);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        List<String> lookingItems = new ArrayList<String>() {{
            add("Five");
        }};

        new SchItems<>(lookingItems).find(items);
    }
}
