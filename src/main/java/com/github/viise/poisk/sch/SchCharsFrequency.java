package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Search chars frequency by list of sentences.
 *
 * For example:
 * <code>
 *     (pseudo-code)
 *
 *     List sentences = new List("Hello world!", "Hi, Adam!");
 *     Map res = new SchCharsFrequency().find(sentences);
 *
 *     res:
 *     H   -> 2
 *     e   -> 1
 *     l   -> 3
 *     o   -> 2
 *     ' ' -> 2
 *     w   -> 1
 *     r   -> 1
 *     d   -> 2
 *     !   -> 2
 *     ,   -> 1
 *     i   -> 1
 *     A   -> 1
 *     a   -> 1
 *     m   -> 1
 * </code>
 */
public final class SchCharsFrequency implements Search<Map<Character, Integer>, List<String>> {

    private final Wall<Object> vdrNotNull;
    private final Wall<List<String>> vdrNotEmptyList;

    private final Search<Map<Character, Integer>, List<Character>> schFreq;

    /**
     * Ctor.
     * @param schFreq Search impl for finding frequency
     *                (default value {@link com.github.viise.poisk.sch.SchFrequency}).
     */
    public SchCharsFrequency(Search<Map<Character, Integer>, List<Character>> schFreq) {
        this.schFreq = schFreq;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Ctor.
     */
    public SchCharsFrequency() {
        this(new SchFrequency<>());
    }

    /**
     * Finding chars frequency by sentences.
     * @param sentences Sentences to find.
     * @return Chars frequency.
     * @throws NotFoundException If schFreq is null, sentences is null or empty.
     */
    @Override
    public Map<Character, Integer> find(List<String> sentences) throws NotFoundException {
        try {
            vdrNotNull.protect("schFreq", schFreq);
            vdrNotEmptyList.protect("sentences", sentences);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }

        List<Character> chars = new ArrayList<>();
        for (String sentence: sentences) {
            for (int i = 0; i < sentence.length(); i++) {
                chars.add(sentence.charAt(i));
            }
        }

        return schFreq.find(chars);
    }
}
