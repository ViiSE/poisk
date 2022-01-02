package com.github.viise.poisk.chk;

import com.github.viise.poisk.Chunk;
import com.github.viise.poisk.DivideException;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNaturalInt;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Chunk divides an {@link java.util.List} of items into chunks.
 * Divides into lists, the number of which is <code>chunkCount<code/>. List of items must be not null or not empty.
 * <code>chunkCount</code> must be natural int.
 * When dividing, the remainder is put in the last list size.
 * For example, we have a list of 100 items. We want divide it to 10 lists. Then result will be like this:
 * <pre />
 * list 1-10 -> 10 items
 * <pre />
 * Each list has a 10 items.
 * <pre />
 * We want divide a list of 43 items to 3 lists. Then result will be like this:
 * <pre />
 * list 1 -> 14 items
 * <pre />
 * list 2 - 14 items
 * <pre />
 * list 3 - 15 items
 *
 * @param <T> Chunk result like lists of {@link java.util.List} and list of items to be chunked for.
 */
public final class ChkList<T> implements Chunk<List<T>> {

    private final Wall<Integer> vdrNaturalInt;
    private final Wall<List<T>> vdrNotEmptyList;

    private final Integer chunkCount;

    /**
     * Ctor.
     * @param chunkCount count of chunks must be natural int.
     */
    public ChkList(Integer chunkCount) {
        this.chunkCount = chunkCount;
        this.vdrNaturalInt = new VdrNaturalInt();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Ctor. Default value of <code>chunkCount</code> - 10.
     */
    public ChkList() {
        this(10);
    }

    /**
     * Chunk divides an list of items into a list of items lists.
     * @param items list of items.
     * @return List of chunked items lists.
     * @throws DivideException If <code>chunkCount</code> is not natural int or <code>items</code> is null or empty.
     */
    @Override
    public List<List<T>> divide(final List<T> items) throws DivideException {
        try {
            vdrNaturalInt.protect("chunkCount", chunkCount);
            vdrNotEmptyList.protect("items", items);

            List<List<T>> chunkedLists = new ArrayList<>();
            int chunkSize = items.size() / chunkCount;
            int remainder = items.size() - (chunkSize * chunkCount);
            List<T> chunkedItems = new ArrayList<>();
            chunkedItems.add(items.get(0));
            for (int i = 1; i < items.size(); i++) {
                if (i >= (items.size() - remainder)) {
                    chunkedItems.add(items.get(i));
                } else if (i % chunkSize == 0) {
                    chunkedLists.add(chunkedItems);
                    chunkedItems = new ArrayList<>();
                    chunkedItems.add(items.get(i));
                } else {
                    chunkedItems.add(items.get(i));
                }
            }
            chunkedLists.add(chunkedItems);

            return chunkedLists;
        } catch (ProtectException e) {
            throw new DivideException(e);
        }
    }
}
