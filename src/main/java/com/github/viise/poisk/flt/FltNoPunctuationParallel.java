package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import com.github.viise.poisk.chk.ChkList;
import com.github.viise.poisk.Task;
import com.github.viise.poisk.tsk.TskFlt;
import com.github.viise.poisk.tsk.TskParallel;

import java.util.ArrayList;
import java.util.List;

public final class FltNoPunctuationParallel implements FilterList<String> {

    private final Integer threadCount;

    public FltNoPunctuationParallel(Integer threadCount) {
        this.threadCount = threadCount;
    }

    public FltNoPunctuationParallel() {
        this(5);
    }

    @Override
    public List<String> apply(final List<String> items) {
        try {
            List<List<String>> chunkedSentencesList = new ChkList<String>(threadCount).divide(items);
            List<Task<List<String>>> tskLists = new ArrayList<>();
            FilterList<String> fltNoPunctuation = new FltNoPunctuation();

            for (List<String> chunkedItems : chunkedSentencesList) {
                tskLists.add(new TskFlt<>(fltNoPunctuation, chunkedItems));
            }

            return new TskParallel<>(tskLists, threadCount).call();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
