package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchByRegexTestNG {

    @Test
    public void find() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        SearchList<String> schRegex = new SchByRegex("T.*");
        List<String> result = schRegex.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("Two"));
        assertTrue(result.contains("Three"));
    }

    @Test
    public void find_oneItemIsNull() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add(null);
            add("Four");
        }};

        SearchList<String> schRegex = new SchByRegex("T.*");
        List<String> result = schRegex.find(items);

        assertEquals(result.size(), 1);
        assertTrue(result.contains("Two"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        List<String> items = new ArrayList<>();
        new SchByRegex("T.*").find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchByRegex("T.*").find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_regexIsEmpty() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchByRegex("").find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_regexIsNull() throws NotFoundException {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchByRegex(null).find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        List<String> firstItems = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Three");
            add("Four");
        }};

        new SchByRegex("Z.*").find(firstItems);
    }
}
