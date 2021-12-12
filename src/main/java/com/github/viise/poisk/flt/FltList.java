package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;

public final class FltList<T> implements FilterList<T> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();

    private final SearchList<T> sch;

    public FltList(SearchList<T> sch) {
        this.sch = sch;
    }

    @Override
    public List<T> apply(final List<T> data) {
        try {
            vdrNotNull.validate("sch", sch);
            return sch.find(data);
        } catch (ValidationException | NotFoundException e) {
            return new ArrayList<>();
        }
    }
}
