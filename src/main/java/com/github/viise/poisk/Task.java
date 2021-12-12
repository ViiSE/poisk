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

import com.github.viise.poisk.Search;

import java.util.concurrent.Callable;

/**
 * Task for searching (mostly used for parallel searches).
 * Task is not reused, rather than {@link Search}, i.e. Task is one-time
 * (called only once). Of course, it can be reused, but only with the data that was specified in constructor
 * implementation.
 * @param <T> Task result and objects to be tasked for.
 */
public interface Task<T> extends Callable<T> {
}
