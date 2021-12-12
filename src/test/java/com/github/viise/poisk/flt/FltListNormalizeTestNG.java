package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FltListNormalizeTestNG {

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

        FilterList<String> fltNorm = new FltListNormalize<>();

        List<String> result = fltNorm.apply(items);

        assertEquals(result.size(), 5);
        assertTrue(result.contains("One"));
        assertTrue(result.contains("Two"));
        assertTrue(result.contains("Three"));
        assertTrue(result.contains("Four"));
        assertTrue(result.contains("Five"));
    }

    @Test
    public void apply_itemsIsNull() {
        FilterList<String> fltNorm = new FltListNormalize<>();
        List<String> result = fltNorm.apply(null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void apply_itemsIsEmpty() {
        FilterList<String> fltNorm = new FltListNormalize<>();
        List<String> result = fltNorm.apply(new ArrayList<>());
        assertTrue(result.isEmpty());
    }
}
