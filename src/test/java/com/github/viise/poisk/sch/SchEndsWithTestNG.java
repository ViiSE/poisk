package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchEndsWithTestNG {

    @Test
    public void find() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add(null);
            add("Four");
        }};

        SearchList<String> schEndsWith = new SchEndsWith("e");
        List<String> result = schEndsWith.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("One"));
        assertTrue(result.contains("Three"));
    }

    @Test
    public void find_endsWithIsEmpty() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        List<String> result = new SchEndsWith("").find(items);
        assertTrue(result.containsAll(Arrays.asList("One", "Two", "Three", "Four")));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        List<String> items = new ArrayList<>();
        new SchEndsWith("e").find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchEndsWith("e").find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_endsWithIsNull() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchEndsWith(null).find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        List<String> firstItems = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchEndsWith("z").find(firstItems);
    }
}
