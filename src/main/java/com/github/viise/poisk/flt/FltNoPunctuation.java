package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;

public final class FltNoPunctuation implements FilterList<String> {

    private final Validator<Object> vdrNotNull = new VdrNotNull();

    @Override
    public List<String> apply(final List<String> items) {
        try {
            vdrNotNull.validate("items", items);

            List<String> result = new ArrayList<>();
            for (String sentence : items) {
                result.add(sentence.replaceAll("\\p{P}", ""));
            }

            return result;
        } catch (ValidationException e) {
            return new ArrayList<>();
        }
    }
}
