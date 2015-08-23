package com.github.almex.pojounit;

import com.github.almex.pojounit.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Almex
 * @since 1.2
 */
public class AbstractPojoTestTest {

    private AbstractPojoTest runner;

    @Before
    public void setUp() throws Exception {
        runner = new AbstractPojoTest() {
            @Override
            public void setUp() throws Exception {
                // nothing to do here
            }
        };
    }

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

    private static class FinalId {

        private static final Long id = 0L;
    }
}