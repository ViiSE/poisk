package com.github.viise.poisk.tsk;

import com.github.viise.poisk.Task;
import com.github.viise.poisk.sch.SchByCondition;
import com.github.viise.poisk.SearchList;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TskSchListTestNG {

    @Test
    public void answer() throws Exception {
        List<Integer> items = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(15);
            add(1);
            add(32);
        }};

        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 10
        );

        Task<List<Integer>> tskSchList = new TskSchList<>(schCond, items);
        List<Integer> result = tskSchList.call();

        assertEquals(result.size(), 2);
        assertTrue(result.contains(15));
        assertTrue(result.contains(32));
    }

    @Test(expectedExceptions = Exception.class)
    public void answer_notFound() throws Exception {
        List<Integer> items = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(15);
            add(1);
            add(32);
        }};

        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 40
        );

        Task<List<Integer>> tskSchList = new TskSchList<>(schCond, items);
        tskSchList.call();
    }

    @Test(expectedExceptions = Exception.class)
    public void answer_itemsIsEmpty() throws Exception {
        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 40
        );

        Task<List<Integer>> tskSchList = new TskSchList<>(schCond, new ArrayList<>());
        tskSchList.call();
    }

    @Test(expectedExceptions = Exception.class)
    public void answer_itemsIsNull() throws Exception {
        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 40
        );

        Task<List<Integer>> tskSchList = new TskSchList<>(schCond, null);
        tskSchList.call();
    }

    @Test(expectedExceptions = Exception.class)
    public void answer_schIsNull() throws Exception {
        List<Integer> items = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(15);
            add(1);
            add(32);
        }};

        new TskSchList<>(null, items).call();
    }
}
