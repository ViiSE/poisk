package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchLessCommonItemsTestNG {

    private final List<String> items_1 = new ArrayList<>();
    private final List<String> items_2 = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        items_1.add("HELLO!");
        items_1.add("Hi");
        items_1.add("Hi");
        items_1.add("Hi");
        items_1.add("The quick brown fox jumps over the lazy dog");
        items_1.add("You are so lazy!");
        items_1.add("HELLO!");

        items_2.add("HELLO!");
        items_2.add("Hi");
        items_2.add("Hi");
        items_2.add("Hi");
        items_2.add("The quick brown fox jumps over the lazy dog");
        items_2.add("HELLO!");
    }

    @Test
    public void find() throws NotFoundException {
        SearchList<String> schLessCmnItems = new SchLessCommonItems<>();
        List<String> result = schLessCmnItems.find(items_1);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("You are so lazy!"));
        assertTrue(result.contains("The quick brown fox jumps over the lazy dog"));
    }

    @Test
    public void find_ctor2() throws NotFoundException {
        SearchList<String> schLessCmnItems = new SchLessCommonItems<>(new SchFrequency<>());
        List<String> result = schLessCmnItems.find(items_2);

        assertEquals(result.size(), 1);
        assertTrue(result.contains("The quick brown fox jumps over the lazy dog"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchLessCommonItems<>().find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchLessCommonItems<>().find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_schFreqIsNull() throws NotFoundException {
        new SchLessCommonItems<String>(null).find(items_1);
    }
}
