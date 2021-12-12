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

package com.github.viise.poisk;

/**
 * Filtering data.
 * The difference between Filter and {@link Search} is that filter not throw exception.
 * Filter should return empty object, if something wrong with data (for example, null value or empty list).
 * @param <I> Data to be ordered for.
 * @param <T> Order result.
 */
public interface Filter<T, I> {
    /**
     * Filtering data.
     * @param data Data to be ordered for.
     * @return Ordered data.
     */
    T apply(I data);
}
