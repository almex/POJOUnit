package com.github.almex.pojounit.test;

import com.github.almex.pojounit.AbstractObjectTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.theories.DataPoint;

/**
 * @author Almex
 * @since 1.0
 */
public class PersonWithFaultyHashcodeTest extends AbstractObjectTest {

    @DataPoint
    public static PersonWithFaultyHashcode DATA_POINT1;

    @DataPoint
    public static PersonWithFaultyHashcode DATA_POINT2;

    @DataPoint
    public static PersonWithFaultyHashcode DATA_POINT3;

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

        DATA_POINT1 = new PersonWithFaultyHashcode();
        setIdFor(DATA_POINT1, 1L);
        DATA_POINT1.setAddress(address);
        DATA_POINT1.setFirstName("foo");
        DATA_POINT1.setLastName("bar");

        DATA_POINT3 = new PersonWithFaultyHashcode();
        setIdFor(DATA_POINT3, 2L);
        DATA_POINT3.setAddress(address);
        DATA_POINT3.setFirstName("foo");
        DATA_POINT3.setLastName("bar");
    }

    @Override
    public void theoryHashCodeIsSelfConsistent(Object x) {
        expectedFailure.markAsExpected();
        super.theoryHashCodeIsSelfConsistent(x);
    }

    @Override
    public void theoryHashCodeIsConsistentWithEquals(Object x, Object y) {
        expectedFailure.markAsExpected();
        super.theoryHashCodeIsConsistentWithEquals(x, y);
    }
}