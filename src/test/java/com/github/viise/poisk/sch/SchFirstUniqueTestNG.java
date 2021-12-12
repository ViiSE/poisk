package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class SchFirstUniqueTestNG {

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

        Search<String, List<String>> schFirstUnique = new SchFirstUnique<>();
        String result = schFirstUnique.find(items);

        assertEquals(result, "Two");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchFirstUnique<>().find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchFirstUnique<>().find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("One");
            add("Two");
            add("Two");
        }};

        new SchFirstUnique<String>().find(items);
    }
}
