package com.github.viise.poisk.tsk;

import com.github.viise.poisk.Task;
import com.github.viise.poisk.sch.SchByCondition;
import com.github.viise.poisk.SearchList;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TskParallelTestNG {

    @Test
    public void answer() throws Exception {
        List<Integer> items_1 = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(15);
            add(1);
            add(32);
        }};

        List<Integer> items_2 = new ArrayList<Integer>() {{
            add(13);
            add(24);
            add(38);
            add(1);
            add(40);
        }};

        List<Integer> items_3 = new ArrayList<Integer>() {{
            add(42);
            add(2);
            add(4);
            add(1);
            add(1);
        }};

        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 10
        );

        List<Task<List<Integer>>> tskLists = new ArrayList<>();
        tskLists.add(new TskSchList<>(schCond, items_1));
        tskLists.add(new TskSchList<>(schCond, items_2));
        tskLists.add(new TskSchList<>(schCond, items_3));

        Task<List<Integer>> tskListParallel = new TskParallel<>(tskLists);
        List<Integer> result = tskListParallel.call();

        assertEquals(result.size(), 7);
        assertTrue(result.contains(15));
        assertTrue(result.contains(32));
        assertTrue(result.contains(13));
        assertTrue(result.contains(24));
        assertTrue(result.contains(38));
        assertTrue(result.contains(40));
        assertTrue(result.contains(42));
    }

    @Test
    public void answer_ctor2() throws Exception {
        List<Integer> items_1 = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(15);
            add(1);
            add(32);
        }};

        List<Integer> items_2 = new ArrayList<Integer>() {{
            add(13);
            add(24);
            add(38);
            add(1);
            add(40);
        }};

        List<Integer> items_3 = new ArrayList<Integer>() {{
            add(42);
            add(2);
            add(4);
            add(1);
            add(1);
        }};

        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 10
        );

        List<Task<List<Integer>>> tskLists = new ArrayList<>();
        tskLists.add(new TskSchList<>(schCond, items_1));
        tskLists.add(new TskSchList<>(schCond, items_2));
        tskLists.add(new TskSchList<>(schCond, items_3));

        Task<List<Integer>> tskListParallel = new TskParallel<>(tskLists, 3);
        List<Integer> result = tskListParallel.call();

        assertEquals(result.size(), 7);
        assertTrue(result.contains(15));
        assertTrue(result.contains(32));
        assertTrue(result.contains(13));
        assertTrue(result.contains(24));
        assertTrue(result.contains(38));
        assertTrue(result.contains(40));
        assertTrue(result.contains(42));
    }

    @Test
    public void answer_oneTaskIsNull() throws Exception {
        List<Integer> items_1 = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(15);
            add(1);
            add(32);
        }};

        List<Integer> items_2 = new ArrayList<Integer>() {{
            add(13);
            add(24);
            add(38);
            add(1);
            add(40);
        }};

        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 10
        );

        List<Task<List<Integer>>> tskLists = new ArrayList<>();
        tskLists.add(new TskSchList<>(schCond, items_1));
        tskLists.add(new TskSchList<>(schCond, items_2));
        tskLists.add(null);

        Task<List<Integer>> tskListParallel = new TskParallel<>(tskLists, 3);
        List<Integer> result = tskListParallel.call();

        assertEquals(result.size(), 6);
        assertTrue(result.contains(15));
        assertTrue(result.contains(32));
        assertTrue(result.contains(13));
        assertTrue(result.contains(24));
        assertTrue(result.contains(38));
        assertTrue(result.contains(40));
    }

    @Test(expectedExceptions = Exception.class)
    public void answer_notFound() throws Exception {
        List<Integer> items_1 = new ArrayList<Integer>() {{
            add(1);
            add(2);
        }};

        List<Integer> items_2 = new ArrayList<Integer>() {{
            add(1);
        }};

        List<Integer> items_3 = new ArrayList<Integer>() {{
            add(2);
            add(4);
            add(1);
        }};

        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 10
        );

        List<Task<List<Integer>>> tskLists = new ArrayList<>();
        tskLists.add(new TskSchList<>(schCond, items_1));
        tskLists.add(new TskSchList<>(schCond, items_2));
        tskLists.add(new TskSchList<>(schCond, items_3));

        new TskParallel<>(tskLists).call();
    }

    @Test(expectedExceptions = Exception.class)
    public void answer_tskListsIsEmpty() throws Exception {
        new TskParallel<>(new ArrayList<>()).call();
    }

    @Test(expectedExceptions = Exception.class)
    public void answer_tskListsIsNull() throws Exception {
        new TskParallel<>(null).call();
    }

    @Test(expectedExceptions = Exception.class)
    public void answer_threadCountIsNegative() throws Exception {
        List<Integer> items_1 = new ArrayList<Integer>() {{ add(1); }};
        List<Integer> items_2 = new ArrayList<Integer>() {{ add(1); }};
        List<Integer> items_3 = new ArrayList<Integer>() {{ add(1); }};

        SearchList<Integer> schCond = new SchByCondition<>(item -> item > 10);

        List<Task<List<Integer>>> tskLists = new ArrayList<>();
        tskLists.add(new TskSchList<>(schCond, items_1));
        tskLists.add(new TskSchList<>(schCond, items_2));
        tskLists.add(new TskSchList<>(schCond, items_3));

        new TskParallel<>(tskLists, -1).call();
    }

    @Test(expectedExceptions = Exception.class)
    public void answer_threadCountIsZero() throws Exception {
        List<Integer> items_1 = new ArrayList<Integer>() {{ add(1); }};
        List<Integer> items_2 = new ArrayList<Integer>() {{ add(1); }};
        List<Integer> items_3 = new ArrayList<Integer>() {{ add(1); }};

        SearchList<Integer> schCond = new SchByCondition<>(item -> item > 10);

        List<Task<List<Integer>>> tskLists = new ArrayList<>();
        tskLists.add(new TskSchList<>(schCond, items_1));
        tskLists.add(new TskSchList<>(schCond, items_2));
        tskLists.add(new TskSchList<>(schCond, items_3));

        new TskParallel<>(tskLists, 0).call();
    }
}
