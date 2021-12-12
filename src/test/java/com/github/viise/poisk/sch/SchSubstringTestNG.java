package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchSubstringTestNG {

    private final List<String> sentences = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        sentences.add("Hello, world!");
        sentences.add("Hello");
        sentences.add("world!");
        sentences.add("cat!");
        sentences.add("Cat, Hello!");
        sentences.add(null);
    }

    @Test
    public void find() throws NotFoundException {
        SearchList<String> schSubstr = new SchSubstring("Hello");
        List<String> result = schSubstr.find(sentences);

        assertEquals(result.size(), 3);
        assertTrue(result.contains("Hello, world!"));
        assertTrue(result.contains("Hello"));
        assertTrue(result.contains("Cat, Hello!"));
    }

    @Test
    public void find_substringIsEmpty() throws NotFoundException {
        List<String> result = new SchSubstring("").find(sentences);
        List<String> expected = new ArrayList<>(sentences);
        expected.removeIf(Objects::isNull);
        assertTrue(result.containsAll(expected));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        List<String> items = new ArrayList<>();
        new SchSubstring("food").find(items);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchSubstring("food").find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_substringIsNull() throws NotFoundException {
        new SchStartsWith(null).find(sentences);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        new SchSubstring("food").find(sentences);
    }
}
