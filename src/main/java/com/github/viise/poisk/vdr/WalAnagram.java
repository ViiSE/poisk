package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Wall;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.PairTwin;

/**
 * Anagram validation.
 */
public final class WalAnagram implements Wall<PairTwin<String>> {

    private final Wall<Object> vdrNotNull = new VdrNotNull();
    private final Wall<String> vdrNotEmptyStr = new VdrNotEmptyStr();

    /**
     * Validate anagram. <code>checkedValueName</code> is "anagramPair".
     * @param checkedValueName Checked anagram name.
     * @param anagramPair Anagram pair.
     * @throws ProtectException If anagramPair is null, anagramPair.left is null and empty, anagramPair.right is null
     * and empty, anagramPair.left and anagramPair.right is the same words or collocations, or anagramPair.left and
     * anagramPair.right is not the same length.
     */
    @Override
    public void protect(String checkedValueName, PairTwin<String> anagramPair) throws ProtectException {
        String valName = checkedValueName == null ? "anagramPair" : checkedValueName;
        vdrNotNull.protect(valName, anagramPair);
        vdrNotEmptyStr.protect(checkedValueName + ".left", anagramPair.left());
        vdrNotEmptyStr.protect(checkedValueName + ".right", anagramPair.right());

        if (anagramPair.left().equals(anagramPair.right())) {
            throw new ProtectException("anagrams must be different words or collocations.");
        }

        if (anagramPair.left().replace(" ", "").length()
                != anagramPair.right().replace(" ", "").length()) {
            throw new ProtectException("anagrams must be the same length.");
        }
    }

    /**
     * Validate anagram. <code>checkedValueName</code> is "anagramPair".
     * @param anagramPair Anagram pair.
     * @throws ProtectException If anagram is not validated.
     */
    @Override
    public void protect(PairTwin<String> anagramPair) throws ProtectException {
        protect("anagramPair", anagramPair);
    }
}
