package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchWordsTestNG {

    @Test
    public void find() throws NotFoundException {
        Search<List<String>, String> schWords = new SchWords();
        List<String> result = schWords.find("Hello, world!");

        assertEquals(result.size(), 2);
        assertTrue(result.contains("Hello"));
        assertTrue(result.contains("world"));
    }

    @Test
    public void find_ctor2() throws NotFoundException {
        Search<List<String>, String> schWords = new SchWords(";");
        List<String> result = schWords.find("Hello;world!");

        assertEquals(result.size(), 2);
        assertTrue(result.contains("Hello"));
        assertTrue(result.contains("world"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        new SchWords().find("    ");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sentenceIsEmpty() throws NotFoundException {
        new SchWords().find("");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sentenceIsNull() throws NotFoundException {
        new SchWords().find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sepsIsNull() throws NotFoundException {
        new SchWords(null).find("Hello, World!");
    }
}
