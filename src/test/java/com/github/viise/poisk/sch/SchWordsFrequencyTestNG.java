package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class SchWordsFrequencyTestNG {

    private final List<String> items = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        items.add("HELLO World");
        items.add("Hi Adam");
        items.add("Hi Eva");
        items.add("Hi Adam");
        items.add("You are so lazy");
        items.add("HELLO World");
        items.add("HELLO");
    }

    @Test
    public void find() throws NotFoundException {
        Search<Map<String, Integer>, List<String>> schWordsFreq = new SchWordsFrequency();
        Map<String, Integer> result = schWordsFreq.find(items);

        assertEquals(result.size(), 9);
        assertEquals(result.get("HELLO"), Integer.valueOf(3));
        assertEquals(result.get("World"), Integer.valueOf(2));
        assertEquals(result.get("Hi"), Integer.valueOf(3));
        assertEquals(result.get("Adam"), Integer.valueOf(2));
        assertEquals(result.get("Eva"), Integer.valueOf(1));
        assertEquals(result.get("You"), Integer.valueOf(1));
        assertEquals(result.get("are"), Integer.valueOf(1));
        assertEquals(result.get("so"), Integer.valueOf(1));
        assertEquals(result.get("lazy"), Integer.valueOf(1));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchWordsFrequency().find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchWordsFrequency().find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_separatorsIsNull() throws NotFoundException {
        new SchWordsFrequency(null).find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_schFreqIsNull() throws NotFoundException {
        new SchWordsFrequency(" ", null).find(items);
    }
}
