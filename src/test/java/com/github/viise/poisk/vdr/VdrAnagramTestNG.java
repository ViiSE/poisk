package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.PairTwin;
import com.github.viise.poisk.pair.PairTwinOf;
import org.testng.annotations.Test;

public class VdrAnagramTestNG {

    private final Validator<PairTwin<String>> vdrAnagram = new VdrAnagram();
    
    @Test
    public void validate_success_1() throws ValidationException {
        vdrAnagram.validate(new PairTwinOf<>("Debit card", "Bad credit"));
    }

    @Test
    public void validate_success_2() throws ValidationException {
        vdrAnagram.validate("superAnagram", new PairTwinOf<>("Debit card", "Bad credit"));
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ValidationException {
        vdrAnagram.validate(null, new PairTwinOf<>("Debit card", "Bad credit"));
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "anagramPair is null.")
    public void validate_anagramPairIsNull() throws ValidationException {
        vdrAnagram.validate(null);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "anagramPair.left is null.")
    public void validate_leftIsNull() throws ValidationException {
        vdrAnagram.validate(new PairTwinOf<>(null, "Bad Credit"));
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "anagramPair.left is empty.")
    public void validate_leftIsEmpty() throws ValidationException {
        vdrAnagram.validate(new PairTwinOf<>("", "Bad Credit"));
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "anagramPair.right is null.")
    public void validate_rightIsNull() throws ValidationException {
        vdrAnagram.validate(new PairTwinOf<>("Debit card", null));
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "anagramPair.right is empty.")
    public void validate_rightIsEmpty() throws ValidationException {
        vdrAnagram.validate(new PairTwinOf<>("Debit card", ""));
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "anagrams must be the same length.")
    public void validate_leftAndRightIsNotTheSameLength() throws ValidationException {
        vdrAnagram.validate(new PairTwinOf<>("Debit card", "Bad credits"));
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = "anagrams must be different words or collocations.")
    public void validate_leftAndRightIsTheSame() throws ValidationException {
        vdrAnagram.validate(new PairTwinOf<>("Debit card", "Debit card"));
    }
}
