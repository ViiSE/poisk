package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchTwin;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.PairTwin;
import com.github.viise.poisk.pair.PairTwinOf;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SchAnagramTestNG {

    @DataProvider(name = "anagrams")
    public Object[][] anagrams() {
        return new Object[][] {
                {"Debit card", "Bad credit"},
                {"Dormitory", "Dirty room"},
                {"Punishment", "Nine thumps"},
                {"School master", "The classroom"}
        };
    }

    @Test(dataProvider = "anagrams")
    public void find(String left, String right) throws NotFoundException {
        SearchTwin<PairTwin<String>> schAnagram = new SchAnagram();
        PairTwin<String> anagrams = schAnagram.find(new PairTwinOf<>(left, right));

        assertEquals(anagrams.left(), left);
        assertEquals(anagrams.right(), right);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_pairIsNull() throws NotFoundException {
        new SchAnagram().find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_pairLeftAndRightIsEmpty() throws NotFoundException {
        PairTwin<String> anagrams = new PairTwinOf<>("", "");
        new SchAnagram().find(anagrams);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_pairLeftAndRightIsNull() throws NotFoundException {
        PairTwin<String> anagrams = new PairTwinOf<>(null, null);
        new SchAnagram().find(anagrams);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_pairLeftAndRightAreNotTheSameLength() throws NotFoundException {
        PairTwin<String> anagrams = new PairTwinOf<>("cat", "doge");
        new SchAnagram().find(anagrams);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        PairTwin<String> anagrams = new PairTwinOf<>("cat", "dog");

        new SchAnagram().find(anagrams);
    }
}
