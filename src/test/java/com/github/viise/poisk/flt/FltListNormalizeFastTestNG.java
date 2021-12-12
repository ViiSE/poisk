package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FltListNormalizeFastTestNG {

    @Test
    public void apply() {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two");
            add("Two");
            add("Two");
            add("Three");
            add("Four");
            add("Four");
            add("Four");
            add("Five");
        }};

        FilterList<String> fltNormFast = new FltListNormalizeFast<>();

        List<String> result = fltNormFast.apply(items);

        assertEquals(result.size(), 5);
        assertTrue(result.contains("One"));
        assertTrue(result.contains("Two"));
        assertTrue(result.contains("Three"));
        assertTrue(result.contains("Four"));
        assertTrue(result.contains("Five"));
    }

    @Test
    public void apply_itemsIsNull() {
        FilterList<String> fltNormFast = new FltListNormalizeFast<>();
        List<String> result = fltNormFast.apply(null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void apply_itemsIsEmpty() {
        FilterList<String> fltNormFast = new FltListNormalizeFast<>();
        List<String> result = fltNormFast.apply(new ArrayList<>());
        assertTrue(result.isEmpty());
    }
}
