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
 * Search endsWith list of strings.
 */
public final class SchEndsWith implements SearchList<String> {

    private final Wall<Object> vdrNotNull;
    private final Wall<List<String>> vdrNotEmptyList;

    private final String endsWith;

    /**
     * Ctor.
     * @param endsWith endsWith.
     */
    public SchEndsWith(String endsWith) {
        this.endsWith = endsWith;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding endsWith list of strings.
     * @param items Items to find.
     * @return List of strings end of <code>endsWith</code>.
     * @throws NotFoundException If endsWith is null, items is null or empty, or items not found.
     */
    @Override
    public List<String> find(List<String> items) throws NotFoundException {
        try {
            vdrNotNull.protect("endsWith", endsWith);
            vdrNotEmptyList.protect("items", items);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }

        List<String> result = new ArrayList<>();

        for (String item : items) {
            if (item != null) {
                if (item.endsWith(endsWith)) {
                    result.add(item);
                }
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException("EndsWith items not found.");
        }

        return result;
    }
}
