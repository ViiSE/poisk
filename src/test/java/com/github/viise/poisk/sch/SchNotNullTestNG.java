package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchNotNullTestNG {

    @Test
    public void find() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add(null);
            add("Four");
        }};

        SearchList<String> schNotNull = new SchNotNull<>();
        List<String> result = schNotNull.find(items);

        assertEquals(result.size(), 3);
        assertTrue(result.contains("One"));
        assertTrue(result.contains("Two"));
        assertTrue(result.contains("Four"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add(null);
            add(null);
        }};

        new SchNotNull<String>().find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchNotNull<>().find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchNotNull<>().find(null);
    }
}
