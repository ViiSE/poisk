package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.ValidationException;
import com.github.viise.poisk.Validator;
import com.github.viise.poisk.vdr.VdrNotEmptyList;

import java.util.ArrayList;
import java.util.List;

/**
 * Search not empty strings in list of strings.
 */
public final class SchNotEmpty implements SearchList<String> {

    private final Validator<List<String>> vdrNotEmptyList;

    public SchNotEmpty() {
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding not empty strings.
     * @param items Items to find.
     * @return Not empty strings.
     * @throws NotFoundException If items is null or empty, or not empty strings not found.
     */
    @Override
    public List<String> find(List<String> items) throws NotFoundException {
        try {
            vdrNotEmptyList.validate("items", items);
        } catch (ValidationException e) {
            throw new NotFoundException(e);
        }

        List<String> result = new ArrayList<>();

        for (String item : items) {
            if (item != null) {
                if (!item.isEmpty()) {
                    result.add(item);
                }
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException("Not empty items not found.");
        }

        return result;
    }
}
