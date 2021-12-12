package com.github.viise.poisk;

import java.util.List;

/**
 * Chunk divides an items into chunks.
 * In order to be divided into chunks, <code>items</code> must be a structure that can be divided into chunks
 * {@link List}, {@link java.util.Map}, or something custom, etc.
 * If it is not possible to divide items into chunks, then throw
 * {@link DivideException}.
 * @param <T> Chunk result like {@link List} and items to be chunked for.
 */
public interface Chunk<T> {
    /**
     * Chunk divides an items into chunks.
     * @param items Items to be chunked for.
     * @return Chunk result.
     */
    List<T> divide(T items) throws DivideException;
}
