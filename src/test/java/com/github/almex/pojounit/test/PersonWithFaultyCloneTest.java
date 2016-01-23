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

package com.github.almex.pojounit.test;

import com.github.almex.pojounit.AbstractCloneableObjectTest;
import com.github.almex.pojounit.model.Address;
import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoint;

/**
 * @author Alexis Soumagne
 * @since 1.1
 */
public class PersonWithFaultyCloneTest extends AbstractCloneableObjectTest {

    @DataPoint
    public static PersonWithFaultyClone DATA_POINT1;

    @DataPoint
    public static PersonWithFaultyClone DATA_POINT2;

    @DataPoint
    public static PersonWithFaultyClone DATA_POINT3;

    @Rule
    public ExpectedFailure expectedFailure = ExpectedFailure.none();

    @Before
    @Override
    public void setUp() throws Exception {
        Address address = new Address();

        address.setCity("city");
        address.setCountry("country");
        address.setCounty("county");
        address.setStreetName("streetName");
        address.setStreetNumber("12");

        DATA_POINT1 = new PersonWithFaultyClone();
        setIdFor(DATA_POINT1, 1L);
        DATA_POINT1.setAddress(address);
        DATA_POINT1.setFirstName("foo");
        DATA_POINT1.setLastName("bar");

        DATA_POINT3 = new PersonWithFaultyClone();
        setIdFor(DATA_POINT3, 2L);
        DATA_POINT3.setAddress(address);
        DATA_POINT3.setFirstName("foo");
        DATA_POINT3.setLastName("bar");
    }

    @Override
    public void theoryCloneIsNotTheSameReference(Object x) {
        expectedFailure.markAsExpected();
        super.theoryCloneIsNotTheSameReference(x);
    }
}