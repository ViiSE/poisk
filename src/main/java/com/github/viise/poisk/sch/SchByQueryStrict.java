package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotEmptyStr;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Strict search by query.
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
 * two strings jointly, i.e. sentence should contain both <code>Hello</code> and <code>w</code> and in the same order:
 * <code>Winner, Hello</code> is not validated.
 * Field <code>separators</code> determine separators for <code>searchQuery</code>. If <code>separators</code> is empty,
 * then searchQuery split by whitespace (in our example, <code>Hello,</code> and <code>w</code>). If you want find all
 * sentences that start with <code>Hello, w</code>, use {@link SchStartsWith} instead.
 */
public final class SchByQueryStrict implements SearchList<String> {

    private final Wall<Object> vdrNotNull;
    private final Wall<List<String>> vdrNotEmptyList;
    private final Wall<String> vdrNotEmptyStr;

    private final String searchQuery;
    private final String querySeps;
    private final String sentenceSeps;

    /**
     * Ctor.
     *
     * @param searchQuery Search query.
     * @param querySeps   Separators for searchQuery (default value regex <code>[\s,:_;\-]</code>).
     */
    public SchByQueryStrict(String searchQuery, String querySeps, String sentenceSeps) {
        this.searchQuery = searchQuery;
        this.querySeps = querySeps;
        this.sentenceSeps = sentenceSeps;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
        this.vdrNotEmptyStr = new VdrNotEmptyStr();
    }

    /**
     * Ctor.
     *
     * @param searchQuery Search query.
     */
    public SchByQueryStrict(String searchQuery) {
        this(searchQuery, "[\\s,:_;\\-]", "[\\s,:_;\\-]");
    }

    /**
     * Ctor.
     *
     * @param searchQuery Search query.
     */
    public SchByQueryStrict(String searchQuery, String querySeps) {
        this(searchQuery, querySeps, "[\\s,:_;\\-]");
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
            vdrNotEmptyList.protect("sentences", sentences);
            vdrNotEmptyStr.protect("searchQuery", searchQuery);
            vdrNotNull.protect("querySeps", querySeps);
            vdrNotNull.protect("sentenceSeps", sentenceSeps);

            List<String> words = new SchWordsLowered(querySeps).find(searchQuery);
            List<String> result = new ArrayList<>();
            for (String sentence : sentences) {
                if (sentence != null) {
                    String pureSentence = sentence.toLowerCase().replaceAll("[\\p{P}]", "");
                    List<String> sentenceWords = new SchWordsLowered(sentenceSeps).find(pureSentence);

                    StringBuilder sentenceSubstr = new StringBuilder();
                    boolean isFounded = true;
                    for (String word : words) {
                        if (!sentence.toLowerCase().contains(word)) {
                            isFounded = false;
                            break;
                        } else {
                            for (String sentenceWord : sentenceWords) {
                                if (sentenceWord.contains(word)) {
                                    sentenceSubstr.append(sentenceWord).append(" ");
                                    break;
                                }
                            }
                        }
                    }

                    if (isFounded && pureSentence.contains(sentenceSubstr.toString().trim())) {
                        result.add(sentence);
                    }
                }
            }

            if (result.isEmpty()) {
                throw new NotFoundException("No sentences found for search query " + searchQuery);
            }

            return result;
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }
    }
}
