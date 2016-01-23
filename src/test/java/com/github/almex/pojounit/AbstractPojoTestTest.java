package com.github.almex.pojounit;

import com.github.almex.pojounit.model.Person;
import org.junit.Assert;
import org.junit.Test;

import java.security.PrivilegedActionException;

/**
 * @author Almex
 * @since 1.2
 */
public class AbstractPojoTestTest {

    @Test
    public void testSetIdFor() throws Exception {
        Person person = new Person();

        AbstractPojoTest.setIdFor(person, 1L);

        Assert.assertEquals(1L, person.getId().longValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIdForNoSuchFieldException() throws Exception {
        AbstractPojoTest.setIdFor(new Object(), 1L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIdForIllegalAccessException() throws Exception {
        AbstractPojoTest.setIdFor(new FinalId(), 1L);
    }

    @Test
    public void testCallClone() throws Exception {
        Person expected = new Person();
        Object actual = AbstractPojoTest.callClone(expected);

        Assert.assertEquals(expected, actual);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCallCloneWCloneNotSupportedException() throws Exception {
        AbstractPojoTest.callClone(new PersonWithoutClone());
    }

    @Test
    public void testCallCloneFromSuper() throws Exception {
        SubPerson expected = new SubPerson();
        Object actual = AbstractPojoTest.callClone(expected);

        Assert.assertEquals(expected, actual);
    }

    private class PersonWithoutClone implements Cloneable {

        @Override
        protected Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }
    }

    private class SubPerson extends Person {}

    private static class FinalId {

        /**
         * Example of non accessible field.
         */
        private static final Long id = 0L;
    }
}