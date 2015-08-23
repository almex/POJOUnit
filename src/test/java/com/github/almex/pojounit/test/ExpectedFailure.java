package com.github.almex.pojounit.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Almex
 * @since 1.0
 */
public class ExpectedFailure implements TestRule {

    private boolean expected = false;

    private ExpectedFailure() {
        // Nothing to do here
    }

    /**
     * Returns a {@linkplain TestRule rule} that expects no failure to
     * be thrown (identical to behavior without this rule).
     */
    public static ExpectedFailure none() {
        return new ExpectedFailure();
    }

    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();

                    if (expected) {
                        throw new AssertionError("We expected that this test failed but it did not!");
                    }
                } catch (Throwable e) {
                    if (expected) {
                        System.out.println("A failure occurred, but that's expected!");
                    } else {
                        throw e;
                    }
                }
            }
        };
    }

    public void markAsExpected() {
        this.expected = true;
    }
}
