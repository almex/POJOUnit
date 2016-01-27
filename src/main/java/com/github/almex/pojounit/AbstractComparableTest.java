package com.github.almex.pojounit;

import org.junit.experimental.theories.Theory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * <p>
 * This is a generic unit testing for the method <code>compareTo()</code> .
 * These will generate some code to respect contract specification coming from
 * Javadoc of the {@link java.lang.Comparable} interface.
 * <p>
 * <i>We log only tests that respect assumptions.</i>
 * </p>
 *
 * @param <T> represent type of the class to test.
 *
 * @author Alexis SOUMAGNE.
 * @since 1.2
 * @see Comparable
 */
public abstract class AbstractComparableTest<T> extends AbstractPojoTest {

    /**
     * Logger for this class.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractComparableTest.class);

    /**
     * The implementor must ensure
     * <code>sgn(x.compareTo(y)) == -sgn(y.compareTo(x))</code> for all
     * <code>x</code> and <code>y</code>. (This implies that
     * <code>x.compareTo(y)</code> must throw an exception if
     * <code>y.compareTo(x)</code> throws an exception.)
     *
     * @param x first parameter
     * @param y second parameter
     */
    @Theory
    @SuppressWarnings("unchecked") // justification : all casting is checked with an assumeThat() statement
    public void theoryCompareToIsSymetric(final T x, final T y) {
        assumeThat(x, is(notNullValue()));
        assumeThat(y, is(notNullValue()));
        assumeThat(x, is(instanceOf(Comparable.class)));
        Comparable<T> cx = (Comparable<T>) x;
        assumeThat(y, is(instanceOf(Comparable.class)));
        Comparable<T> cy = (Comparable<T>) y;

        // The real provision should say :
        // "If the argument is not of the appropriate type, the compareTo
        // method should throw a ClassCastException."
        assumeThat(x.getClass().equals(y.getClass()), is(true));

        LOGGER.trace("testCompareToSigne({}, {})", x, y);
        assertThat(sgn(cx.compareTo(y)) == -sgn(cy.compareTo(x)),
                is(true));
    }

    /**
     * The implementor must also ensure that the relation is transitive:
     * <code>(x.compareTo(y)>0 && y.compareTo(z)>0)</code> implies
     * <code>x.compareTo(z)>0</code>.
     *
     * @param x first parameter
     * @param y second parameter
     * @param z third parameter
     */
    @Theory
    @SuppressWarnings("unchecked") // justification : all casting is checked with an assumeThat() statement
    public void theoryCompareToIsTransitive(final T x, final T y, final T z) {

        compareToProvisionFor(x, y, z);

        Comparable<T> cx = (Comparable<T>) x;
        Comparable<T> cy = (Comparable<T>) y;

        assumeThat(cx.compareTo(y) > 0, is(true));
        assumeThat(cy.compareTo(z) > 0, is(true));

        LOGGER.trace("testCompareToIsTransitive({}, {}, {})", x, y, z);

        assertThat(cx.compareTo(z) > 0, is(true));
    }

    /**
     * Finally, the implementer must ensure that <code>x.compareTo(y)==0</code>
     * implies that <code>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</code>, for
     * all z.
     *
     * @param x first parameter
     * @param y second parameter
     * @param z third parameter
     */
    @Theory
    @SuppressWarnings("unchecked") // justification : all casting is checked with an assumeThat() statement
    public void theoryCompareToIsTransitiveWithSigne(final T x, final T y, final T z) {

        compareToProvisionFor(x, y, z);

        Comparable<T> cx = (Comparable<T>) x;
        Comparable<T> cy = (Comparable<T>) y;

        assumeThat(cx.compareTo(y) == 0, is(true));

        LOGGER.trace("testCompareToTransitiveSigne({}, {}, {})", x, y, z);
        assertThat(sgn(cx.compareTo(z)) == sgn(cy.compareTo(z)),
                is(true));
    }

    /**
     * List of provision for compareTo <code>Theory</code> with three
     * parameters.
     *
     * @param x first parameter
     * @param y second parameter
     * @param z third parameter
     */
    private void compareToProvisionFor(final T x, final T y, final T z) {
        assumeThat(x, is(notNullValue()));
        assumeThat(y, is(notNullValue()));
        assumeThat(z, is(notNullValue())); // The behavior of o.compareTo(null)
        // is not well documented
        assumeThat(x, is(instanceOf(Comparable.class)));
        assumeThat(y, is(instanceOf(Comparable.class)));

        // The real provision should say :
        // "If the argument is not of the appropriate type, the compareTo
        // method should throw a ClassCastException."
        assumeThat(x.getClass().equals(y.getClass()), is(true));
        assumeThat(x.getClass().equals(z.getClass()), is(true));
        assumeThat(y.getClass().equals(z.getClass()), is(true));
    }

    /**
     * The notation sgn(expression) designates the mathematical
     * <code>signum</code> function, which is defined to return one of -1, 0, or
     * 1 according to whether the value of expression is negative, zero or
     * positive
     *
     * @param expression to evaluate
     * @return -1, 0, or 1
     */
    private static int sgn(final int expression) {
        return (expression < 0 ? -1 : (expression > 0 ? 1 : 0));
    }
}

