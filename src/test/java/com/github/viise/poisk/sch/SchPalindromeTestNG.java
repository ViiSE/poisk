package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchTwin;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SchPalindromeTestNG {

    @DataProvider(name = "palindromes")
    public Object[][] palindromes() {
        return new Object[][] {
                {"level"},
                {"radar"},
                {"civic"},
                {"Anna"},
                {"Mr. Owl ate my metal worm"},
                {"A man, a plan, a canal--Panama!"},
                {"Able was I ere I saw Elba."},
                {"Do geese see God?"},
                {"Go Hang a Salami! I'm a Lasagna Hog!"}
        };
    }

    @Test(dataProvider = "palindromes")
    public void find(String palindrome) throws NotFoundException {
        SearchTwin<String> schPalindrome = new SchPalindrome();
        String result = schPalindrome.find(palindrome);

        assertEquals(result, palindrome);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_phraseIsEmpty() throws NotFoundException {
        new SchPalindrome().find("");
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_phraseIsNull() throws NotFoundException {
        new SchPalindrome().find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        new SchPalindrome().find("I am not a palindrome!");
    }
}
