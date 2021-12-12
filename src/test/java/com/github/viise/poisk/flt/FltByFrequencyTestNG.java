package com.github.viise.poisk.flt;

import com.github.viise.poisk.Filter;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.sch.SchFrequency;
import com.github.viise.poisk.Search;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FltByFrequencyTestNG {

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
    public void apply() throws NotFoundException {
        Search<Map<String, Integer>, List<String>> schFreq = new SchFrequency<>();
        Map<String, Integer> freqMap = schFreq.find(items);

        Filter<List<String>, Map<String, Integer>> fltFreq = new FltByFrequency<>(
                (freq) -> freq >= 2
        );

        List<String> result = fltFreq.apply(freqMap);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("HELLO!"));
        assertTrue(result.contains("Hi"));
    }

    @Test
    public void apply_freqMapIsNull() {
        Filter<List<String>, Map<String, Integer>> fltFreq = new FltByFrequency<>(
                (freq) -> freq >= 2
        );

        List<String> result = fltFreq.apply(null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void apply_freqMapIsEmpty() {
        Filter<List<String>, Map<String, Integer>> fltFreq = new FltByFrequency<>(
                (freq) -> freq >= 2
        );

        List<String> result = fltFreq.apply(new HashMap<>());
        assertTrue(result.isEmpty());
    }

    @Test
    public void apply_conditionIsNull() throws NotFoundException {
        Filter<List<String>, Map<String, Integer>> fltFreq = new FltByFrequency<>(null);

        Search<Map<String, Integer>, List<String>> schFreq = new SchFrequency<>();
        Map<String, Integer> freqMap = schFreq.find(items);

        List<String> result = fltFreq.apply(freqMap);
        assertTrue(result.isEmpty());
    }
}
