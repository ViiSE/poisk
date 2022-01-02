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
 * Data validation.
 * <code>checkedValue</code> is data to be validated. <code>checkedValueName</code> is the name of
 * <code>checkedValue</code>. <code>checkedValueName</code> is "value", if it is not specified, or a it passed to
 * {@link Wall#protect(String, T)} as <code>null</code>. <code>checkedValueName<code/> used for exception message.
 * Pay attention: this logic must be implemented in every implementation.
 * NOTE: Needs to be transferred to Stena project.
 * @param <T> Value to be validated.
 */
public interface Wall<T> {
    /**
     * @param checkedValueName Name of checked value.
     * @param checkedValue Checked value.
     * @throws ProtectException If value is not validated.
     */
    void protect(String checkedValueName, T checkedValue) throws ProtectException;
    /**
     * checkedValueName is "value"
     * @param checkedValue Checked value.
     * @throws ProtectException If value is not validated.
     */
    void protect(T checkedValue) throws ProtectException;
}
