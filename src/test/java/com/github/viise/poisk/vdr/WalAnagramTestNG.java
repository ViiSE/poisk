package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.PairTwin;
import com.github.viise.poisk.pair.PairTwinOf;
import org.testng.annotations.Test;

public class WalAnagramTestNG {

    private final Wall<PairTwin<String>> vdrAnagram = new WalAnagram();
    
    @Test
    public void validate_success_1() throws ProtectException {
        vdrAnagram.protect(new PairTwinOf<>("Debit card", "Bad credit"));
    }

    @Test
    public void validate_success_2() throws ProtectException {
        vdrAnagram.protect("superAnagram", new PairTwinOf<>("Debit card", "Bad credit"));
    }

    @Test
    public void validate_success_checkedValueNameIsNull() throws ProtectException {
        vdrAnagram.protect(null, new PairTwinOf<>("Debit card", "Bad credit"));
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "anagramPair is null.")
    public void validate_anagramPairIsNull() throws ProtectException {
        vdrAnagram.protect(null);
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "anagramPair.left is null.")
    public void validate_leftIsNull() throws ProtectException {
        vdrAnagram.protect(new PairTwinOf<>(null, "Bad Credit"));
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "anagramPair.left is empty.")
    public void validate_leftIsEmpty() throws ProtectException {
        vdrAnagram.protect(new PairTwinOf<>("", "Bad Credit"));
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "anagramPair.right is null.")
    public void validate_rightIsNull() throws ProtectException {
        vdrAnagram.protect(new PairTwinOf<>("Debit card", null));
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "anagramPair.right is empty.")
    public void validate_rightIsEmpty() throws ProtectException {
        vdrAnagram.protect(new PairTwinOf<>("Debit card", ""));
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "anagrams must be the same length.")
    public void validate_leftAndRightIsNotTheSameLength() throws ProtectException {
        vdrAnagram.protect(new PairTwinOf<>("Debit card", "Bad credits"));
    }

    @Test(expectedExceptions = ProtectException.class, expectedExceptionsMessageRegExp = "anagrams must be different words or collocations.")
    public void validate_leftAndRightIsTheSame() throws ProtectException {
        vdrAnagram.protect(new PairTwinOf<>("Debit card", "Debit card"));
    }
}
