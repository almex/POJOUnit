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
     * Use reflection trick to use the <code>setId(T id)</code> method that is
     * private in your entity.
     *
     * @param <K>    type of the id
     * @param entity to modify
     * @param id     to set
     * @return the <code>obj</code> parameter
     * @throws IllegalArgumentException if the method <code>setId(T id)</code>
     *                                  does not exists or is not accessible
     * @throws RuntimeException         for unexpected errors
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
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("Field id does not exists.", e);
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
