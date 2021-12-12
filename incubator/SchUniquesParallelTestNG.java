package com.github.viise.poisk.sch;

import com.github.javafaker.Faker;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchUniquesParallelTestNG {

    private final Faker faker = new Faker();

    private final List<String> sentences_1 = new ArrayList<>();
    private final List<String> sentences_2 = new ArrayList<>();
    private final List<String> sentences_3 = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        for (int i = 0; i < 20_000; i++) {
            sentences_1.add(faker.howIMetYourMother().highFive());
        }

        sentences_1.add("I am the unique");
        sentences_1.add("I am the unique too");

        for (int i = 0; i < 20_000; i++) {
            sentences_1.add(faker.howIMetYourMother().highFive());
        }

        for (int t = 0; t < 4; t++) {
            for (int i = 0; i < 19_999; i++) {
                if (i == 19_997) {
                    sentences_2.add("I am the unique");
                    i++;
                    sentences_2.add("I am the unique too");
                } else {
                    sentences_2.add(faker.howIMetYourMother().highFive());
                }
            }
        }

        sentences_2.add("I am ultra unique");

        for (int t = 0; t < 5; t++) {
            for (int i = 0; i < 10_000; i++) {
                if (i == 9_998) {
                    sentences_3.add("I am the unique");
                    i++;
                    sentences_3.add("I am the unique too");
                } else {
                    sentences_3.add(faker.howIMetYourMother().highFive());
                }
            }
        }
    }

    @Test
    public void find() throws NotFoundException {
        SearchList<String> schWords = new SchUniquesParallel<>();
        List<String> result = schWords.find(sentences_1);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("I am the unique"));
        assertTrue(result.contains("I am the unique too"));
    }

    @Test
    public void find_s() throws NotFoundException {
        SearchList<String> schWords = new SchUniquesFast<>();
        List<String> result = schWords.find(sentences_1);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("I am the unique"));
        assertTrue(result.contains("I am the unique too"));
    }

    @Test
    public void find_2() throws NotFoundException {
        SearchList<String> schWords = new SchUniquesParallel<>();
        List<String> result = schWords.find(sentences_2);

        assertEquals(result.size(), 1);
        assertTrue(result.contains("I am ultra unique"));
    }

    @Test
    public void find_afterFirstRunningParallelTaskItemsSizeIsLessThan10000() throws NotFoundException {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 499; i++) {
            items.add("item " + i);
        }
        items.add("unique");

        for (int i = 500; i < 999; i++) {
            items.add("item " + i);
        }
        items.add("unique");

        SearchList<String> schUniquesParallel = new SchUniquesParallel<>(2);
        List<String> result = schUniquesParallel.find(items);

        assertEquals(result.size(), 998);
    }

    @Test
    public void find_uniquesInTwoPiecesOfList() throws NotFoundException {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 499; i++) {
            items.add("item");
        }
        items.add("unique 1");

        for (int i = 500; i < 999; i++) {
            items.add("item");
        }
        items.add("unique 2");

        SearchList<String> schUniquesParallel = new SchUniquesParallel<>(2);
        List<String> result = schUniquesParallel.find(items);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("unique 1"));
        assertTrue(result.contains("unique 2"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        SearchList<String> schWords = new SchUniquesParallel<>();
        List<String> result = schWords.find(sentences_3);

        assertEquals(result.size(), 2);
        assertTrue(result.contains("I am the unique"));
        assertTrue(result.contains("I am the unique too"));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchUniquesParallel<>().find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchUniquesParallel<>().find(null);
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expectedExceptions = NotFoundException.class)
    public void find_schIsNull() throws NotFoundException {
        SearchList<String> sch = null;
        new SchUniquesParallel<>(sch).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_threadCountIsNull() throws NotFoundException {
        Integer threadCount = null;
        new SchUniquesParallel<String>(threadCount).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_repeatCountIsNull() throws NotFoundException {
        new SchUniquesParallel<String>(5, null).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_threadCountIsNegative() throws NotFoundException {
        new SchUniquesParallel<String>(-1).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_threadCountIsZero() throws NotFoundException {
        new SchUniquesParallel<String>(0).find(sentences_1);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_repeatCountIsNegative() throws NotFoundException {
        new SchUniquesParallel<String>(5, -1).find(sentences_1);
    }
}
