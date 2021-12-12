package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotEmptyStr;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Search by query.
 * <p>
 * For example:
 * <code>
 * (pseudo-code)
 * <p>
 * List sentences = new List("Hello, World", "!!!HELLO WORLD!!", "Hello, Winner!", "Hello, Adam!", "Hi, Eva!");
 * Map res = new SchByQuery("Hello, w").find(sentences);
 * <p>
 * res:
 * Hello, World
 * !!!HELLO WORLD!!
 * Hello, Winner!
 * </code>
 * In our example, <code>searchQuery</code> split into <code>Hello</code> and <code>w</code>, then it searches those
 * two strings jointly, i.e. sentence should contain both <code>Hello</code> and <code>w</code>.
 * Field <code>separators</code> determine separators for <code>searchQuery</code>. If <code>separators</code> is empty,
 * then searchQuery split by whitespace (in our example, <code>Hello,</code> and <code>w</code>). If you want find all
 * sentences that start with <code>Hello, w</code>, use {@link com.github.viise.poisk.sch.SchStartsWith} instead.
 */
public final class SchByQuery implements SearchList<String> {

    private final Validator<Object> vdrNotNull;
    private final Validator<List<String>> vdrNotEmptyList;
    private final Validator<String> vdrNotEmptyStr;

    private final String searchQuery;
    private final String querySeps;

    /**
     * Ctor.
     *
     * @param searchQuery Search query.
     * @param querySeps   Separators for searchQuery (default value regex <code>[\s,:_;\-]</code>).
     */
    public SchByQuery(String searchQuery, String querySeps) {
        this.searchQuery = searchQuery;
        this.querySeps = querySeps;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
        this.vdrNotEmptyStr = new VdrNotEmptyStr();
    }

    /**
     * Ctor.
     *
     * @param searchQuery Search query.
     */
    public SchByQuery(String searchQuery) {
        this(searchQuery, "[\\s,:_;\\-]");
    }

    /**
     * Finding sentences by search query.
     *
     * @param sentences Sentences to find.
     * @return Sentences satisfying query.
     * @throws NotFoundException If sentences is null or empty, search query is null or empty, separators is null, or
     *                           sentences satisfying query not found.
     */
    @Override
    public List<String> find(List<String> sentences) throws NotFoundException {
        try {
            vdrNotEmptyList.validate("sentences", sentences);
            vdrNotEmptyStr.validate("searchQuery", searchQuery);
            vdrNotNull.validate("querySeps", querySeps);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        List<String> words = new SchWordsLowered(querySeps).find(searchQuery);
        List<String> result = new ArrayList<>();
        for (String sentence : sentences) {
            if (sentence != null) {
                boolean isFounded = true;
                for (String word : words) {
                    if (!sentence.toLowerCase().contains(word)) {
                        isFounded = false;
                        break;
                    }
                }

                if (isFounded) {
                    result.add(sentence);
                }
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException("No sentences found for search query " + searchQuery);
        }

        return result;
    }
}
