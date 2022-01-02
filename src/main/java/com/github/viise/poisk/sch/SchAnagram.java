package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.PairTwin;
import com.github.viise.poisk.SearchTwin;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.WalAnagram;

/**
 * Search anagram.
 *
 * Anagram is a word, phrase, or name formed by rearranging the letters of another.
 * For example, <code>race</code> and <code>care</code>, <code>debit card</code> and <code>bad credit</code>.
 */
public final class SchAnagram implements SearchTwin<PairTwin<String>> {

    private final Wall<PairTwin<String>> vdrAnagram;

    public SchAnagram() {
        vdrAnagram = new WalAnagram();
    }

    /**
     * Finding anagram. <code>pair</code> is a couple of words or collocations for check whether they are anagrams in
     * relation to each other or not.
     * @param pair Couple of words or collocations.
     * @return <code>pair</code> like search result.
     * @throws NotFoundException If <code>pair</code> is null or left and right parts are null or empty,
     * left and right is the same words or collocations, or couple of words or collocations is not anagram.
     */
    @Override
    public PairTwin<String> find(final PairTwin<String> pair) throws NotFoundException {
        String left = pair.left().replace(" ", "").toLowerCase();
        String right = pair.right().replace(" ", "").toLowerCase();

        int charSumLeft = 0;
        int charSumRight = 0;

        for (int i = 0; i < left.length(); i++) {
            charSumLeft += left.charAt(i);
            charSumRight += right.charAt(i);
        }

        if (charSumLeft != charSumRight) {
            throw new NotFoundException(String.format(
                    "Words '%s' and '%s' are not anagrams",
                    pair.left(), pair.right()
            ));
        }

        return pair;
    }
}
