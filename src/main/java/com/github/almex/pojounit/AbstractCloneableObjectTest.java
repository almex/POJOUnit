package com.github.almex.pojounit;

import org.junit.experimental.theories.Theory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeTrue;

/**
 * If your {@link Object} is {@link Cloneable} then you can test your {@link Object#clone()} method
 * in addition to those already present in {@link AbstractObjectTest}.
 *
 * @author Alexis Soumagne
 * @since 1.1
 */
public abstract class AbstractCloneableObjectTest extends AbstractObjectTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCloneableObjectTest.class);


    /**
     * Seems like a bug to me  :
     * If I don't have a method with two parameters in this class
     * then the first method will always fail due to not matching parameters.
     * I cannot figure out if it's due to JUnit {@link Theory} or Harmcrest assumptions.
     *
     * @param x first parameter
     * @param y second parameter
     */
    @Theory(nullsAccepted = false)
    public void theoryClone(final Object x, final Object y) {
    }

    /**
     * Test that a clone is equal to the original.
     * <p>
     * {@code x.clone().equals(x)}
     * </p>
     *
     * @param x first parameter
     */
    @Theory(nullsAccepted = false)
    public void theoryCloneAreEquals(final Object x) {
        assumeTrue(Cloneable.class.isAssignableFrom(x.getClass()));

        LOGGER.trace("theoryCloneAreEquals({})", x);

        assertThat(callClone(x).equals(x), is(true));
    }

    /**
     * Test that a clone is the same type as the original.
     * <p>
     * {@code x.clone().getClass() == x.getClass()}
     * </p>
     *
     * @param x first parameter
     */
    @Theory(nullsAccepted = false)
    public void theoryCloneAreTheSameType(final Object x) {
        assumeTrue(Cloneable.class.isAssignableFrom(x.getClass()));

        LOGGER.trace("theoryCloneAreTheSameType({})", x);

        assertThat(callClone(x).getClass() == x.getClass(), is(true));
    }

    /**
     * Test that a clone is not the same reference as the original.
     * <p>
     * {@code x.clone() != x}
     * </p>
     *
     * @param x first parameter
     */
    @Theory(nullsAccepted = false)
    public void theoryCloneIsNotTheSameReference(final Object x) {
        assumeTrue(Cloneable.class.isAssignableFrom(x.getClass()));

        LOGGER.trace("theoryCloneIsNotTheSameReference({})", x);

        assertThat(callClone(x) != x, is(true));
    }
}
