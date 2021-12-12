package com.github.viise.poisk.tsk;

import com.github.viise.poisk.Task;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNaturalInt;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Parallel task for searching list of items.
 * Tasks come in a list, which are then executed in parallel.
 * @param <T> Parallel task result and items to be tasked for.
 */
public final class TskParallel<T> implements Task<List<T>> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();
    private final Validator<Integer> vdrNaturalInt = new VdrNaturalInt();

    private final List<Task<List<T>>> tskLists;
    private final Integer threadCount;

    /**
     * Ctor.
     * @param tskLists {@link Task} List of tasks.
     * @param threadCount Threads count.
     */
    public TskParallel(List<Task<List<T>>> tskLists, Integer threadCount) {
        this.tskLists = tskLists;
        this.threadCount = threadCount;
    }

    public TskParallel(List<Task<List<T>>> tskLists) {
        this(tskLists, 2);
    }

    /**
     * Call task.
     * @return Task result.
     * @throws Exception if <code>threadCount</code> is not natural int and <code>tskLists</code> is null.
     */
    @Override
    public List<T> call() throws Exception {
        vdrNaturalInt.validate("threadCount", threadCount);
        vdrNotNull.validate("tskLists", tskLists);

        ExecutorService execService = Executors.newFixedThreadPool(threadCount);
        List<T> result = new ArrayList<>();
        List<Future<List<T>>> futuresRes = new ArrayList<>();
        for (Task<List<T>> tskList : tskLists) {
            if (tskList != null) {
                Future<List<T>> futureRes = execService.submit(tskList);
                futuresRes.add(futureRes);
            }
        }

        Exception lastE = null;
        for (Future<List<T>> futureRes : futuresRes) {
            try {
                if (futureRes != null) {
                    List<T> ftrRes = futureRes.get();
                    result.addAll(ftrRes);
                }
            } catch (InterruptedException | ExecutionException e) {
                lastE = e;
            }
        }
        execService.shutdown();

        if (result.isEmpty()) {
            throw new Exception(lastE);
        }

        return result;
    }
}
