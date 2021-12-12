package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchStartsWithTestNG {

    @Test
    public void find() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add(null);
            add("Three");
            add("Four");
        }};

        SearchList<String> schStartsWith = new SchStartsWith("T");
        List<String> result = schStartsWith.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("Two"));
        assertTrue(result.contains("Three"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        List<String> items = new ArrayList<>();
        new SchStartsWith("T").find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchStartsWith("T").find(null);
    }

    @Test
    public void find_startsWithIsEmpty() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        List<String> result = new SchStartsWith("").find(items);
        assertTrue(result.containsAll(Arrays.asList("One", "Two", "Three", "Four")));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_startsWithIsNull() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchStartsWith(null).find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchStartsWith("Z").find(items);
    }
}
