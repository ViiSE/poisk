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

package com.github.viise.poisk.pair;

import com.github.viise.poisk.Pair;

public class PairOf<T, V> implements Pair<T, V> {

    private final T left;
    private final V right;

    public PairOf(T left, V right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public T left() {
        return left;
    }

    @Override
    public V right() {
        return right;
    }
}
