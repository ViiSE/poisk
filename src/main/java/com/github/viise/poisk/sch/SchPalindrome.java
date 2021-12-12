package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchTwin;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyStr;

/**
 * Search palindrome.
 *
 * A palindrome is a word, number, phrase, or other sequence of characters which reads the same backward as forward.
 * For example, <code>level</code>, <code>civic<code/>.
 */
public final class SchPalindrome implements SearchTwin<String> {

    private final Validator<String> vdrNotEmptyStr;

    public SchPalindrome() {
        this.vdrNotEmptyStr = new VdrNotEmptyStr();
    }

    /**
     * Finding palindrome.
     * @param phrase The phrase that is checked for palindrome.
     * @return Palindrome.
     * @throws NotFoundException If phrase is null or empty, or phrase is not a palindrome.
     */
    @Override
    public String find(String phrase) throws NotFoundException {
        try {
            vdrNotEmptyStr.validate("phrase", phrase);
            String purePhrase = phrase
                    .replaceAll("\\p{P}", "")
                    .replace(" ", "")
                    .toLowerCase();

            int endIndex = purePhrase.length() - 1;
            for (int i = 0; i < purePhrase.length() / 2; i++) {
                if (purePhrase.charAt(i) != purePhrase.charAt(endIndex)) {
                    throw new NotFoundException("Phrase '" + phrase + "' is not a palindrome.");
                }
                endIndex--;
            }
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        return phrase;
    }
}
