package com.github.almex.pojounit.test;

import com.github.almex.pojounit.AbstractObjectTest;
import org.junit.Before;
import org.junit.experimental.theories.DataPoint;

/**
 * @author Almex
 * @since 1.2
 */
public class PersonTest extends AbstractObjectTest {

    @DataPoint
    public static Person DATA_POINT1;

    @DataPoint
    public static Person DATA_POINT2;

    @DataPoint
    public static Person DATA_POINT3;

    @Before
    @Override
    public void setUp() throws Exception {
        Address address = new Address();
        address.setCity("city");
        address.setCountry("country");
        address.setCounty("county");
        address.setStreetName("streetName");
        address.setStreetNumber("12");
        DATA_POINT1 = new Person();
        DATA_POINT1.setAddress(address);
        DATA_POINT1.setFirstName("foo");
        DATA_POINT1.setLastName("bar");

        DATA_POINT3 = new Person();
        DATA_POINT3.setAddress(address);
        DATA_POINT3.setFirstName("foo");
        DATA_POINT3.setLastName("bar");
    }
}