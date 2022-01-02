package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.SearchList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchByQueryTestNG {

    private final List<String> sentences = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        sentences.add("HELLO WORLD!");
        sentences.add("Hello Winner!");
        sentences.add("Winner, Hello!");
        sentences.add("Hello, Adam");
        sentences.add("Hi Adam");
        sentences.add("Hi Eva");
        sentences.add("Hi Edward");
        sentences.add("Hi Eddy");
        sentences.add("The quick brown fox jumps over the lazy dog");
        sentences.add("You are so lazy!");
        sentences.add("!!!Hello, world!!!");
    }

    @Test
    public void test_broken() {
        try {
            SearchList<String> schQ = new SchByQuery("Adam Hi");
            List<String> result = schQ.find(sentences);
            assertEquals(result.size(), 1);
            assertTrue(result.contains("Hi Adam"));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void find() throws NotFoundException {
        SearchList<String> schQuery = new SchByQuery("Hello, w");
        List<String> result = schQuery.find(sentences);

        assertEquals(result.size(), 4);
        assertTrue(result.contains("HELLO WORLD!"));
        assertTrue(result.contains("!!!Hello, world!!!"));
        assertTrue(result.contains("Hello Winner!"));
        assertTrue(result.contains("Winner, Hello!"));
    }

    @Test
    public void find_oneSentence() throws NotFoundException {
        SearchList<String> schQuery = new SchByQuery("Hi Adam");
        List<String> result = schQuery.find(sentences);

        assertEquals(result.size(), 1);
        assertTrue(result.contains("Hi Adam"));
    }

    @Test
    public void find_ctor2() throws NotFoundException {
        SearchList<String> schQuery = new SchByQuery("Hi;Ed", ";");
        List<String> result = schQuery.find(sentences);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("Hi Eddy"));
        assertTrue(result.contains("Hi Edward"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        new SchByQuery("Cat").find(sentences);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sentencesIsEmpty() throws NotFoundException {
        new SchByQuery("Hi Adam").find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sentencesIsNull() throws NotFoundException {
        new SchByQuery("Hi Adam").find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_searchQueryIsEmpty() throws NotFoundException {
        new SchByQuery("").find(sentences);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_searchQueryIsNull() throws NotFoundException {
        new SchByQuery(null).find(sentences);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_querySepsIsNull() throws NotFoundException {
        new SchByQuery("Hi", null).find(sentences);
    }
}
