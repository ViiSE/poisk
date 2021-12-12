package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchDuplicatesFastTestNG {

    @Test
    public void find() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("One");
            add("Two");
            add("Three");
            add("Four");
            add("Four");
        }};

        SearchList<String> schDuplicates = new SchDuplicatesFast<>();
        List<String> result = schDuplicates.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("One"));
        assertTrue(result.contains("Four"));
    }

    @Test
    public void find_ctor2() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("One");
            add("Two");
            add("Three");
            add("Four");
            add("Four");
        }};

        SearchList<String> schDuplicates = new SchDuplicatesFast<>(new SchFrequency<>());
        List<String> result = schDuplicates.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("One"));
        assertTrue(result.contains("Four"));
    }

    @Test
    public void find_withNull() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add(null);
            add("One");
            add("One");
            add("Two");
            add("Three");
            add(null);
            add("Four");
        }};

        SearchList<String> schDuplicates = new SchDuplicatesFast<>(new SchFrequency<>());
        List<String> result = schDuplicates.find(items);

        assertEquals(result.size(), 1);
        assertTrue(result.contains("One"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchDuplicatesFast<>().find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchDuplicatesFast<>().find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_schIsNull() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{ add("One"); }};
        new SchDuplicatesFast<String>(null).find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchDuplicatesFast<String>().find(items);
    }
}
