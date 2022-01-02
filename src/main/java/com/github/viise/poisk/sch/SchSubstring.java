package com.github.viise.poisk.sch;

import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Search list of strings that have a substring
 */
public final class SchSubstring implements SearchList<String> {

    private final Wall<Object> vdrNotNull;
    private final Wall<List<String>> vdrNotEmptyList;

    private final String substring;

    /**
     * Ctor.
     * @param substring substring.
     */
    public SchSubstring(String substring) {
        this.substring = substring;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding list of strings that have a substring.
     * @param items Items to find.
     * @return List of strings that have a substring.
     * @throws NotFoundException If substring is null, items is null or empty, or items not found.
     */
    @Override
    public List<String> find(List<String> items) throws NotFoundException {
        try {
            vdrNotNull.protect("substring", substring);
            vdrNotEmptyList.protect("items", items);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }

        List<String> result = new ArrayList<>();

        for (String item : items) {
            if (item != null) {
                if (item.contains(substring)) {
                    result.add(item);
                }
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException("Substring in items not found.");
        }

        return result;
    }
}
