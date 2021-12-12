package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FltNoPunctuationParallelTestNG {

    @Test
    public void apply() {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two, ");
            add("Three:_");
            add("!Four!");
            add("-.Five;{}");
        }};

        FilterList<String> fltNoPctParallel = new FltNoPunctuationParallel();

        List<String> result = fltNoPctParallel.apply(items);

        assertEquals(result.size(), 5);
        assertTrue(result.contains("One"));
        assertTrue(result.contains("Two "));
        assertTrue(result.contains("Three"));
        assertTrue(result.contains("Four"));
        assertTrue(result.contains("Five"));
    }

    @Test
    public void apply_ctor2() {
        List<String> items = new ArrayList<String>() {{
            add("One");
            add("Two, ");
            add("Three:_");
            add("!Four!");
            add("-.Five;{}");
        }};

        FilterList<String> fltNoPctParallel = new FltNoPunctuationParallel(3);

        List<String> result = fltNoPctParallel.apply(items);

        assertEquals(result.size(), 5);
        assertTrue(result.contains("One"));
        assertTrue(result.contains("Two "));
        assertTrue(result.contains("Three"));
        assertTrue(result.contains("Four"));
        assertTrue(result.contains("Five"));
    }

    @Test
    public void apply_itemsIsNull() {
        FilterList<String> fltNoPctParallel = new FltNoPunctuationParallel();
        List<String> result = fltNoPctParallel.apply(null);
        assertTrue(result.isEmpty());
    }

    @Test
    public void apply_itemsIsEmpty() {
        FilterList<String> fltNoPctParallel = new FltNoPunctuationParallel();
        List<String> result = fltNoPctParallel.apply(new ArrayList<>());
        assertTrue(result.isEmpty());
    }

    @Test
    public void apply_threadCountIsNull() {
        List<String> items = new ArrayList<String>() {{
            add("One");
        }};

        FilterList<String> fltNoPctParallel = new FltNoPunctuationParallel(null);
        List<String> result = fltNoPctParallel.apply(items);
        assertTrue(result.isEmpty());
    }

    @Test
    public void apply_threadCountIsNegative() {
        List<String> items = new ArrayList<String>() {{
            add("One");
        }};

        FilterList<String> fltNoPctParallel = new FltNoPunctuationParallel(-1);
        List<String> result = fltNoPctParallel.apply(items);
        assertTrue(result.isEmpty());
    }

    @Test
    public void apply_threadCountIsZero() {
        List<String> items = new ArrayList<String>() {{
            add("One");
        }};

        FilterList<String> fltNoPctParallel = new FltNoPunctuationParallel(0);
        List<String> result = fltNoPctParallel.apply(items);
        assertTrue(result.isEmpty());
    }
}
