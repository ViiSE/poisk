package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.Search;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Search words frequency by list of sentences.
 *
 * For example:
 * <code>
 *     (pseudo-code)
 *
 *     List sentences = new List("Hello world", "Hello, Adam");
 *     Map res = new SchWordsFrequency().find(sentences);
 *
 *     res:
 *     Hello -> 2
 *     world -> 1
 *     Adam  -> 1
 * </code>
 */
public final class SchWordsFrequency implements Search<Map<String, Integer>, List<String>> {

    private final Validator<Object> vdrNotNull;
    private final Validator<List<String>> vdrNotEmptyList;

    private final String separators;
    private final Search<Map<String, Integer>, List<String>> schFreq;

    /**
     * Ctor.
     * @param separators Words separators (default value [\s,:_;\-]).
     * @param schFreq Search impl for finding frequency
     *                (default value {@link com.github.viise.poisk.sch.SchFrequency}).
     */
    public SchWordsFrequency(String separators, Search<Map<String, Integer>, List<String>> schFreq) {
        this.separators = separators;
        this.schFreq = schFreq;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Ctor.
     * @param separators Words separators (default value regex <code>[\s,:_;\-]</code>).
     */
    public SchWordsFrequency(String separators) {
        this(separators, new SchFrequency<>());
    }

    /**
     * Ctor.
     */
    public SchWordsFrequency() {
        this("[\\s,:_;\\-]");
    }

    /**
     * Finding words frequency by sentences.
     * @param sentences Sentences to find.
     * @return Words frequency.
     * @throws NotFoundException If schFreq is null, separators is null, or sentences is null or empty.
     */
    @Override
    public Map<String, Integer> find(List<String> sentences) throws NotFoundException {
        try {
            vdrNotNull.validate("separators", separators);
            vdrNotNull.validate("schFreq", schFreq);
            vdrNotEmptyList.validate("sentences", sentences);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        List<String> words = new ArrayList<>();
        for (String sentence: sentences) {
            words.addAll(Arrays.asList(sentence.split(separators)));
        }

        return schFreq.find(words);
    }
}
