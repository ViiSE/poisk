package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class SchCharsFrequencyTestNG {

    private final List<String> items = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        items.add("HELLO World");
        items.add("HELLO");
    }

    @Test
    public void find() throws NotFoundException {
        Search<Map<Character, Integer>, List<String>> schCharsFreq = new SchCharsFrequency();
        Map<Character, Integer> result = schCharsFreq.find(items);

        assertEquals(result.size(), 10);
        assertEquals(result.get('H'), Integer.valueOf(2));
        assertEquals(result.get('E'), Integer.valueOf(2));
        assertEquals(result.get('L'), Integer.valueOf(4));
        assertEquals(result.get('O'), Integer.valueOf(2));
        assertEquals(result.get(' '), Integer.valueOf(1));
        assertEquals(result.get('W'), Integer.valueOf(1));
        assertEquals(result.get('o'), Integer.valueOf(1));
        assertEquals(result.get('r'), Integer.valueOf(1));
        assertEquals(result.get('l'), Integer.valueOf(1));
        assertEquals(result.get('d'), Integer.valueOf(1));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchCharsFrequency().find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchCharsFrequency().find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_schFreqIsNull() throws NotFoundException {
        new SchCharsFrequency(null).find(items);
    }
}
