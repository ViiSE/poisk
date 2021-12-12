package com.github.viise.poisk.ord;

import com.github.viise.poisk.Order;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class OrdAscParallelTestNG {

    @Test
    public void apply() {
        List<Integer> items = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(5);
            add(4);
            add(3);
        }};

        Order<List<Integer>> ordAscParallel = new OrdAscParallel<>();
        List<Integer> result = ordAscParallel.sort(items);

        assertEquals(items.size(), 5);
        assertEquals(items.get(0), Integer.valueOf(1));
        assertEquals(items.get(1), Integer.valueOf(2));
        assertEquals(items.get(2), Integer.valueOf(5));
        assertEquals(items.get(3), Integer.valueOf(4));
        assertEquals(items.get(4), Integer.valueOf(3));

        assertEquals(result.size(), 5);
        assertEquals(result.get(0), Integer.valueOf(1));
        assertEquals(result.get(1), Integer.valueOf(2));
        assertEquals(result.get(2), Integer.valueOf(3));
        assertEquals(result.get(3), Integer.valueOf(4));
        assertEquals(result.get(4), Integer.valueOf(5));
    }

    @Test
    public void apply_itemsIsNull() {
        assertTrue(new OrdAscParallel<>().sort(null).isEmpty());
    }

    @Test
    public void apply_itemsIsEmpty() {
        List<Integer> items = new ArrayList<>();
        List<Integer> result = new OrdAscParallel<Integer>().sort(items);
        assertEquals(items, result);
    }
}
