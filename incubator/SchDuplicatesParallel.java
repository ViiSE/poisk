package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNaturalInt;
import com.github.viise.poisk.vdr.VdrNotNull;
import com.github.viise.poisk.vdr.VdrPositiveInt;

import java.util.ArrayList;
import java.util.List;

/**
 * Parallel search duplicates in list of items.
 * The peculiarity of impl is that if it's necessary to find duplicates from a very large list, then one run will not be
 * enough, therefore impl has a <code>repeatCount</code> field that determines the number of runs of task.
 * If size of items after running parallel task changed to a size less than 1000,
 * {@link SearchList} implementation for finding duplicates defined through constructor
 * will be used.
 *
 * DEPRECATION. Implementation is not stable :(. For example, we have a list of numbers: 1, 2, 3, 1, 2, 3, 1, 2, 3
 * All elements in list is a duplicates: 1, 2, and 3, but if we set threadCount to 3, then each task get a sublist
 * 1, 2, 3: task1 - search duplicates in 1, 2, 3; task2 - search duplicates in 1, 2, 3; task3 - search duplicates in
 * 1, 2, 3. Obviously, this return duplicates not found, but it's not true.
 * @param <T> Search result and list of items to be searched for.
 *           Override {@link java.lang.Object#equals(T)} in custom classes for correct behaviour.
 */
@Deprecated
public final class SchDuplicatesParallel<T> implements SearchList<T> {

    private final Validator<Object> vdrNotNull;
    private final Validator<Integer> vdrNaturalInt;
    private final Validator<Integer> vdrPositiveInt;

    private final SearchList<T> sch;
    private final Integer threadCount;
    private final Integer repeatCount;

    /**
     * Ctor.
     * @param sch {@link SearchList} impl for finding duplicates
     *            (default value {@link com.github.viise.poisk.sch.SchDuplicates}).
     * @param threadCount Thread count (default value 5).
     * @param repeatCount Repeat count of task (default value 5).
     */
    public SchDuplicatesParallel(SearchList<T> sch, Integer threadCount, Integer repeatCount) {
        this.sch = sch;
        this.threadCount = threadCount;
        this.repeatCount = repeatCount;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNaturalInt = new VdrNaturalInt();
        this.vdrPositiveInt = new VdrPositiveInt();
    }

    /**
     * Ctor.
     * @param threadCount Thread count (default value 5).
     */
    public SchDuplicatesParallel(Integer threadCount) {
        this(threadCount, 5);
    }

    /**
     * Ctor.
     * @param threadCount Thread count (default value 5).
     * @param repeatCount Repeat count of task (default value 5).
     */
    public SchDuplicatesParallel(Integer threadCount, Integer repeatCount) {
        this(new SchDuplicates<>(), threadCount, repeatCount);
    }

    /**
     * Ctor.
     * @param sch Search impl for finding duplicates
     *            (default value {@link com.github.viise.poisk.sch.SchDuplicates}).
     */
    public SchDuplicatesParallel(SearchList<T> sch) {
        this(sch, 5, 5);
    }

    /**
     * Ctor.
     */
    public SchDuplicatesParallel() {
        this(new SchDuplicates<>());
    }

    /**
     * Finding duplicates in items.
     * @param items Items to find.
     * @return List of duplicates.
     * @throws NotFoundException If sch is null, threadCount is not natural, repeatCount is negative, items is null or
     * empty, or duplicates not found.
     */
    @Override
    public List<T> find(List<T> items) throws NotFoundException {
        try {
            vdrNotNull.validate("sch", sch);
            vdrNaturalInt.validate("threadCount", threadCount);
            vdrPositiveInt.validate("repeatCount", repeatCount);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        SearchList<T> schListParallel = new SchListParallel<>(sch, threadCount);
        SearchList<T> schUniques = new SchUniques<>();
        List<T> result = new ArrayList<>();
        int currentRepeatCount = 0;
        do {
            if (!result.isEmpty() && result.size() < 1000) {
                try {
                    List<T> mayBeFoundedDuplicates = schUniques.find(result);
                    if (mayBeFoundedDuplicates.size() == result.size() && mayBeFoundedDuplicates.containsAll(result)) {
                        break;
                    } else {
                        result = sch.find(result);
                        break;
                    }
                } catch (NotFoundException ignored) {
                    result = sch.find(result);
                    break;
                }
            } else {
                if (currentRepeatCount == 0) {
                    result = schListParallel.find(items);
                } else {
                    result = schListParallel.find(result);
                }
            }
            currentRepeatCount++;
        } while (currentRepeatCount <= repeatCount);

        return result;
    }
}
