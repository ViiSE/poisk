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
 * Search startsWith list of strings.
 */
public final class SchStartsWith implements SearchList<String> {

    private final Wall<Object> vdrNotNull;
    private final Wall<List<String>> vdrNotEmptyList;

    private final String startsWith;

    /**
     * Ctor.
     * @param startsWith startsWith.
     */
    public SchStartsWith(String startsWith) {
        this.startsWith = startsWith;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding startsWith list of strings.
     * @param items Items to find.
     * @return List of strings starts with <code>startsWith</code>.
     * @throws NotFoundException If startsWith is null, items is null or empty, or items not found.
     */
    @Override
    public List<String> find(List<String> items) throws NotFoundException {
        try {
            vdrNotNull.protect("startsWith", startsWith);
            vdrNotEmptyList.protect("items", items);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }

        List<String> result = new ArrayList<>();

        for (String item : items) {
            if (item != null) {
                if (item.startsWith(startsWith)) {
                    result.add(item);
                }
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException("StartsWith items not found.");
        }

        return result;
    }
}
