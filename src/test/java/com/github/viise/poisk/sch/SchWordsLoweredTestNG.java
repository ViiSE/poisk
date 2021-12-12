package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchWordsLoweredTestNG {

    @Test
    public void find() throws NotFoundException {
        Search<List<String>, String> schWords = new SchWordsLowered();
        List<String> result = schWords.find("Hello, world!");

        assertEquals(result.size(), 2);
        assertTrue(result.contains("hello"));
        assertTrue(result.contains("world"));
    }

    @Test
    public void find_ctor2() throws NotFoundException {
        Search<List<String>, String> schWords = new SchWordsLowered(";");
        List<String> result = schWords.find("Hello;world!");

        assertEquals(result.size(), 2);
        assertTrue(result.contains("hello"));
        assertTrue(result.contains("world"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        new SchWordsLowered().find("    ");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sentenceIsEmpty() throws NotFoundException {
        new SchWordsLowered().find("");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sentenceIsNull() throws NotFoundException {
        new SchWordsLowered().find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sepsIsNull() throws NotFoundException {
        new SchWordsLowered(null).find("Hello, World!");
    }
}
