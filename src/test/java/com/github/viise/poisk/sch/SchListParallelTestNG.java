package com.github.viise.poisk.sch;

import com.github.javafaker.Faker;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class SchListParallelTestNG {

    private final List<String> sentences = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        Faker faker = new Faker();
        for (int i = 0; i < 100_000; i++) {
            sentences.add(faker.howIMetYourMother().highFive());
        }
    }

    @Test
    public void find() throws NotFoundException {
        SearchList<String> schByQuery = new SchListParallel<>(
                new SchByQuery("High five")
        );
        List<String> result = schByQuery.find(sentences);

        for (String res: result) {
            assertTrue(res.toLowerCase().contains("high") && res.toLowerCase().contains("five"));
        }
    }

    @Test
    public void find_ctor2() throws NotFoundException {
        SearchList<String> schByQuery = new SchListParallel<>(
                new SchByQuery("High five"),
                10);
        List<String> result = schByQuery.find(sentences);

        for (String res: result) {
            assertTrue(res.toLowerCase().contains("high") && res.toLowerCase().contains("five"));
        }
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        new SchListParallel<>(new SchByQuery("Cat")).find(sentences);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sentencesIsEmpty() throws NotFoundException {
        new SchListParallel<>(new SchByQuery("Hi Adam")).find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_sentencesIsNull() throws NotFoundException {
        new SchListParallel<>(new SchByQuery("Hi Adam")).find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_threadCountIsNull() throws NotFoundException {
        new SchListParallel<>(new SchByQuery("Hi Adam"), null).find(sentences);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_threadCountIsNegative() throws NotFoundException {
        new SchListParallel<>(new SchByQuery("Hi Adam"), -2).find(sentences);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_threadCountIsZero() throws NotFoundException {
        new SchListParallel<>(new SchByQuery("Hi Adam"), 0).find(sentences);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_schIsNull() throws NotFoundException {
        new SchListParallel<String>(null, 5).find(sentences);
    }
}
