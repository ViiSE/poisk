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
 * For searching.
 * If <code>items</code> is null or a empty, needs throw {@link NotFoundException}.
 * Use {@link Validator} for this.
 * @param <T> Search result.
 * @param <I> Objects to be searched for.
 */
public interface Search<T, I> {

    /**
     * @return Founded result.
     * @throws NotFoundException If search did not return any result.
     */
    T find(I items) throws NotFoundException;
}
