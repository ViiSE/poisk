package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import com.github.viise.poisk.ProtectException;
import com.github.viise.poisk.Wall;
import com.github.viise.poisk.vdr.VdrNotEmptyList;
import com.github.viise.poisk.vdr.VdrNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Search items by regex.
 */
public final class SchByRegex implements SearchList<String> {

    private final Wall<Object> vdrNotNull;
    private final Wall<List<String>> vdrNotEmptyList;

    private final String regex;

    /**
     * Ctor.
     * @param regex Regex.
     */
    public SchByRegex(String regex) {
        this.regex = regex;

        this.vdrNotNull = new VdrNotNull();
        this.vdrNotEmptyList = new VdrNotEmptyList<>();
    }

    /**
     * Finding item by regex.
     * @param items Items to find.
     * @return Founded items by regex.
     * @throws NotFoundException If regex is null, items is null or empty, or items not found.
     */
    @Override
    public List<String> find(List<String> items) throws NotFoundException {
        try {
            vdrNotNull.protect("regex", regex);
            vdrNotEmptyList.protect("items", items);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }

        List<String> result = new ArrayList<>();

        Pattern pattern = Pattern.compile(regex);
        for (String item : items) {
            if (item != null) {
                if (pattern.matcher(item).matches()) {
                    result.add(item);
                }
            }
        }

        if (result.isEmpty()) {
            throw new NotFoundException("Items by regex " + regex + " not found.");
        }

        return result;
    }
}
