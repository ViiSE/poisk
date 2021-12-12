package com.github.viise.poisk.tsk;

import com.github.viise.poisk.FilterList;
import com.github.viise.poisk.Task;
import com.github.viise.poisk.flt.FltList;
import com.github.viise.poisk.sch.SchByCondition;
import com.github.viise.poisk.SearchList;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TskFltTestNG {

    @Test
    public void answer() throws Exception {
        List<Integer> items = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(15);
            add(1);
            add(32);
        }};

        FilterList<Integer> flt = new FltList<>(
                new SchByCondition<>(
                        item -> item > 10
                )
        );

        Task<List<Integer>> tskFltList = new TskFlt<>(flt, items);
        List<Integer> result = tskFltList.call();

        assertEquals(result.size(), 2);
        assertTrue(result.contains(15));
        assertTrue(result.contains(32));
    }

    @Test
    public void answer_notFound() throws Exception {
        List<Integer> items = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(15);
            add(1);
            add(32);
        }};

        FilterList<Integer> flt = new FltList<>(
                new SchByCondition<>(
                        item -> item > 40
                )
        );

        Task<List<Integer>> tskFltList = new TskFlt<>(flt, items);
        List<Integer> result = tskFltList.call();

        assertTrue(result.isEmpty());
    }

    @Test
    public void answer_itemsIsEmpty() throws Exception {
        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 40
        );

        FilterList<Integer> flt = new FltList<>(
                new SchByCondition<>(
                        item -> item > 10
                )
        );

        Task<List<Integer>> tskFltList = new TskFlt<>(flt, new ArrayList<>());
        List<Integer> result = tskFltList.call();

        assertTrue(result.isEmpty());
    }

    @Test
    public void answer_itemsIsNull() throws Exception {
        FilterList<Integer> flt = new FltList<>(
                new SchByCondition<>(
                        item -> item > 10
                )
        );

        Task<List<Integer>> tskFltList = new TskFlt<>(flt, null);
        List<Integer> result = tskFltList.call();

        assertTrue(result.isEmpty());
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
