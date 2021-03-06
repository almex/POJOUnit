package com.github.almex.pojounit.test;

import com.github.almex.pojounit.model.Person;

/**
 * This example POJO class use method coming from Object.
 *
 * @author Almex
 * @since 1.0
 */
public class PersonWithFaultyHashcode extends Person {

    @Override
    public int hashCode() {
        return Double.hashCode(Math.random());
    }
}
