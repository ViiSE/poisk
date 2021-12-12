package com.github.viise.poisk.sch;

import com.github.javafaker.Faker;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class SchDuplicatesParallelTestNG {

    private final Faker faker = new Faker();

    private final List<String> sentences_1 = new ArrayList<>();
    private final List<String> sentences_2 = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        for (int i = 0; i < 5_000; i++) {
            sentences_1.add(faker.howIMetYourMother().highFive());
        }

        sentences_1.add("I am the unique");
        sentences_1.add("I am the unique too");

        for (int i = 0; i < 5_000; i++) {
            sentences_1.add(faker.howIMetYourMother().highFive());
        }


        for (int i = 0; i < 5_000; i++) {
            sentences_1.add("I am the unique " + i);
        }
    }

    @Test
    public void find() throws NotFoundException {
        SearchList<String> schDuplicatesParallel = new SchDuplicatesParallel<>();
        List<String> result = schDuplicatesParallel.find(sentences_1);

        assertFalse(result.contains("I am the unique"));
        assertFalse(result.contains("I am the unique too"));
    }

    @Test
    public void find_ctor2() throws NotFoundException {
        SearchList<String> schDuplicatesParallel = new SchDuplicatesParallel<>(new SchDuplicatesFast<>());
        List<String> result = schDuplicatesParallel.find(sentences_1);

        assertFalse(result.contains("I am the unique"));
        assertFalse(result.contains("I am the unique too"));
    }

    @Test
    public void find_ctor3() throws NotFoundException {
        SearchList<String> schDuplicatesParallel = new SchDuplicatesParallel<>(10);
        List<String> result = schDuplicatesParallel.find(sentences_1);

        assertFalse(result.contains("I am the unique"));
        assertFalse(result.contains("I am the unique too"));
    }

    @Test
    public void find_ctor4() throws NotFoundException {
        SearchList<String> schDuplicatesParallel = new SchDuplicatesParallel<>(10, 10);
        List<String> result = schDuplicatesParallel.find(sentences_1);

        assertFalse(result.contains("I am the unique"));
        assertFalse(result.contains("I am the unique too"));
    }

    @Test
    public void find_ctor5() throws NotFoundException {
        SearchList<String> schDuplicatesParallel = new SchDuplicatesParallel<>(new SchDuplicatesFast<>(), 10, 10);
        List<String> result = schDuplicatesParallel.find(sentences_1);

        assertFalse(result.contains("I am the unique"));
        assertFalse(result.contains("I am the unique too"));
    }

    @Test
    public void find_afterFirstRunningParallelTaskItemsSizeIsLessThan1000() throws NotFoundException {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 997; i++) {
            items.add("item " + i);
        }
        items.add("Duplicate");
        items.add("Duplicate");


        SearchList<String> schDuplicatesParallel = new SchDuplicatesParallel<>();
        List<String> result = schDuplicatesParallel.find(items);

        assertEquals(result.size(), 1);
        assertTrue(result.contains("Duplicate"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        new SchDuplicatesParallel<String>().find(sentences_2);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchDuplicatesParallel<>().find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchDuplicatesParallel<>().find(null);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expectedExceptions = NotFoundException.class)
    public void find_schIsNull() throws NotFoundException {
        SearchList<String> sch = null;
        new SchDuplicatesParallel<>(sch).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_threadCountIsNull() throws NotFoundException {
        Integer threadCount = null;
        new SchDuplicatesParallel<String>(threadCount).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_repeatCountIsNull() throws NotFoundException {
        new SchDuplicatesParallel<String>(5, null).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_threadCountIsNegative() throws NotFoundException {
        new SchDuplicatesParallel<String>(-1).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_threadCountIsZero() throws NotFoundException {
        new SchDuplicatesParallel<String>(0).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_repeatCountIsNegative() throws NotFoundException {
        new SchDuplicatesParallel<String>(5, -1).find(sentences_1);
    }
}
