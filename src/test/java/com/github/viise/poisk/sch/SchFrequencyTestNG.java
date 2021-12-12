package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class SchFrequencyTestNG {

    private final List<String> items = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        items.add("HELLO!");
        items.add("Hi");
        items.add("Hi");
        items.add("Hi");
        items.add("The quick brown fox jumps over the lazy dog");
        items.add("You are so lazy!");
        items.add("HELLO!");
    }

    @Test
    public void find() throws NotFoundException {
        Search<Map<String, Integer>, List<String>> schFreq = new SchFrequency<>();
        Map<String, Integer> result = schFreq.find(items);

        assertEquals(result.size(), 4);
        assertEquals(result.get("HELLO!"), Integer.valueOf(2));
        assertEquals(result.get("Hi"), Integer.valueOf(3));
        assertEquals(result.get("The quick brown fox jumps over the lazy dog"), Integer.valueOf(1));
        assertEquals(result.get("You are so lazy!"), Integer.valueOf(1));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchFrequency<>().find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchFrequency<>().find(null);
    }
}
