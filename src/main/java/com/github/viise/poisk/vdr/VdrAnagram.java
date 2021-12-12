package com.github.viise.poisk.vdr;

import com.github.viise.poisk.Validator;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.PairTwin;

/**
 * Anagram validation.
 */
public final class VdrAnagram implements Validator<PairTwin<String>> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();
    private final Validator<String> vdrNotEmptyStr = new VdrNotEmptyStr();

    /**
     * Validate anagram. <code>checkedValueName</code> is "anagramPair".
     * @param checkedValueName Checked anagram name.
     * @param anagramPair Anagram pair.
     * @throws ValidationException If anagramPair is null, anagramPair.left is null and empty, anagramPair.right is null
     * and empty, anagramPair.left and anagramPair.right is the same words or collocations, or anagramPair.left and
     * anagramPair.right is not the same length.
     */
    @Override
    public void validate(String checkedValueName, PairTwin<String> anagramPair) throws ValidationException {
        String valName = checkedValueName == null ? "anagramPair" : checkedValueName;
        vdrNotNull.validate(valName, anagramPair);
        vdrNotEmptyStr.validate(checkedValueName + ".left", anagramPair.left());
        vdrNotEmptyStr.validate(checkedValueName + ".right", anagramPair.right());

        if (anagramPair.left().equals(anagramPair.right())) {
            throw new ValidationException("anagrams must be different words or collocations.");
        }

        if (anagramPair.left().replace(" ", "").length()
                != anagramPair.right().replace(" ", "").length()) {
            throw new ValidationException("anagrams must be the same length.");
        }
    }

    /**
     * Validate anagram. <code>checkedValueName</code> is "anagramPair".
     * @param anagramPair Anagram pair.
     * @throws ValidationException If anagram is not validated.
     */
    @Override
    public void validate(PairTwin<String> anagramPair) throws ValidationException {
        validate("anagramPair", anagramPair);
    }
}
