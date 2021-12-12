package com.github.viise.poisk.chk;

import com.github.viise.poisk.Chunk;
import com.github.viise.poisk.DivideException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.testng.Assert.assertEquals;

public class ChkListTestNG {

    private final List<Integer> integers = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        for (int i = 0; i < 143; i++) {
            integers.add(i);
        }
    }

    @Test
    public void divide() throws DivideException {
        Chunk<List<Integer>> chkList = new ChkList<>(15);
        List<List<Integer>> chunkedIntegers = chkList.divide(integers);

        AtomicInteger totalSize = new AtomicInteger(0);
        chunkedIntegers.forEach(igs -> totalSize.addAndGet(igs.size()));

        assertEquals(chunkedIntegers.size(), 15);
        assertEquals(totalSize.get(), 143);
        assertEquals(chunkedIntegers.get(chunkedIntegers.size() - 1).size(), 17);
    }

    @Test(expectedExceptions = DivideException.class)
    public void divide_chunkCountIsNull() throws DivideException {
        new ChkList<Integer>(null).divide(integers);
    }

    @Test(expectedExceptions = DivideException.class)
    public void divide_chunkCountIsNegative() throws DivideException {
        new ChkList<Integer>(-1).divide(integers);
    }

    @Test(expectedExceptions = DivideException.class)
    public void divide_itemsIsEmpty() throws DivideException {
        new ChkList<Integer>().divide(new ArrayList<>());
    }

    @Test(expectedExceptions = DivideException.class)
    public void divide_itemsIsNull() throws DivideException {
        new ChkList<Integer>().divide(null);
    }
}
