/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Almex
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package com.github.almex.pojounit;

import org.junit.Before;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Objects;

/**
 * <p>
 * <b>How to use it</b>:<br/>
 * Just extend this abstract class and add some class variable of type of the
 * class that you want to test. Annotate these variables with
 * <code>@Datapoint</code>
 * </p>
 * <p>
 * <i>It's advised to only log tests that respect assumptions.</i>
 * </p>
 *
 * @author Alexis SOUMAGNE.
 * @see DataPoint
 * @see Theories
 */
@RunWith(Theories.class)
public abstract class AbstractPojoTest {

    /**
     * Null reference of a generic type
     */
    @DataPoint
    public static final Object NULL_OBJECT = null;
    /**
     * Reference to a generic type
     */
    @DataPoint
    public static final Object OBJECT = new Object();

    /**
     * Use reflection trick to set the {@code id} field that is
     * private in your entity and has no setter.
     *
     * @param <K>    type of the id
     * @param entity to modify
     * @param id     to set
     * @return the {@code entity} parameter
     * @throws IllegalArgumentException if the field {@code id}
     *                                  does not exists either in {@code this} or its {@code super}
     */
    protected static <K> Object setIdFor(final Object entity, final K id) {
        Objects.requireNonNull(entity);

        try {
            final Field field = getIdField(entity.getClass());

            // doPrivileged block
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                public Object run() {
                    field.setAccessible(true);
                    return null;
                }
            });

            field.set(entity, id);

            return entity;
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Field 'id' does not exists.", e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Field 'id' is not accessible.", e);
        }
    }

    private static Field getIdField(Class<?> clazz) throws NoSuchFieldException {
        Field result;

        try {
            result = clazz.getDeclaredField("id");
        } catch (NoSuchFieldException e) {
            Class superClass = clazz.getSuperclass();

            if (superClass != null) {
                result = getIdField(superClass);
            } else {
                throw e;
            }
        }

        return result;
    }

    /**
     * Override this method to provide initialize your data points.
     */
    @Before
    public abstract void setUp() throws Exception;

}
