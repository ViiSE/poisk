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

package com.github.viise.poisk.sch;

import com.github.viise.poisk.Search;
import com.github.viise.poisk.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public final class SchWordsLowered implements Search<List<String>, String> {

    private final String seps;

    public SchWordsLowered(String seps) {
        this.seps = seps;
    }

    public SchWordsLowered() {
        this("[\\s,:_;\\-]");
    }

    @Override
    public List<String> find(String sentence) throws NotFoundException {
        List<String> words = new SchWords(seps).find(sentence);
        words = words.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        if (words.isEmpty()) {
            throw new NotFoundException("sentence " + sentence + " has not words.");
        }

        return words;
    }
}
