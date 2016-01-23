package com.github.almex.pojounit;

import org.junit.experimental.theories.Theory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * If your {@link Object} is {@link Cloneable} then you can test your {@link Object#clone()} method
 * in addition to those already present in {@link AbstractObjectTest}.
 *
 * @author Alexis Soumagne
 * @since 1.1
 */
public abstract class AbstractCloneableObjectTest extends AbstractObjectTest {
    /**
     * Test that a clone is equal to the original.
     * <p>
     * {@code x.clone().equals(x)}
     * </p>
     *
     * @param x first parameter
     */
    @Theory
    public void theoryCloneAreEquals(final Object x) {
        assumeThat(x, is(notNullValue()));
        assumeThat(Cloneable.class.isAssignableFrom(x.getClass()), is(true));

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
    @Theory
    public void theoryCloneAreTheSameType(final Object x) {
        assumeThat(x, is(notNullValue()));
        assumeThat(Cloneable.class.isAssignableFrom(x.getClass()), is(true));

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
    @Theory
    public void theoryCloneIsNotTheSameReference(final Object x) {
        assumeThat(x, is(notNullValue()));
        assumeThat(Cloneable.class.isAssignableFrom(x.getClass()), is(true));

        LOGGER.trace("theoryCloneIsNotTheSameReference({})", x);

        assertThat(callClone(x) != x, is(true));
    }
}
