/*
 * Copyright 2021 ViiSE
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.viise.poisk.sch.safe;

import com.github.viise.poisk.*;
import com.github.viise.poisk.sch.SchAnagram;
import com.github.viise.poisk.vdr.WalAnagram;

/**
 * Safe search anagram.
 * <p>
 * Anagram is a word, phrase, or name formed by rearranging the letters of another.
 * For example, <code>race</code> and <code>care</code>, <code>debit card</code> and <code>bad credit</code>.
 */
public final class SchSafeAnagram implements SearchTwin<PairTwin<String>> {

    private final Wall<PairTwin<String>> walAnagram;
    private final SearchTwin<PairTwin<String>> schAnagram;

    public SchSafeAnagram(SearchTwin<PairTwin<String>> schAnagram, Wall<PairTwin<String>> walAnagram) {
        this.schAnagram = schAnagram;
        this.walAnagram = walAnagram;
    }

    public SchSafeAnagram() {
        this(new SchAnagram(), new WalAnagram());
    }

    /**
     * Finding anagram. <code>anagramPair</code> is a couple of words or collocations for check whether they are anagrams in
     * relation to each other or not.
     *
     * @param anagramPair Couple of words or collocations.
     * @return <code>anagramPair</code> like search result.
     * @throws NotFoundException If <code>anagramPair</code> is null or left and right parts are null or empty,
     *                           left and right is the same words or collocations, or couple of words or collocations is not anagram.
     */
    @Override
    public PairTwin<String> find(final PairTwin<String> anagramPair) throws NotFoundException {
        try {
            walAnagram.protect(anagramPair);
            return schAnagram.find(anagramPair);
        } catch (ProtectException e) {
            throw new NotFoundException(e);
        }
    }
}
