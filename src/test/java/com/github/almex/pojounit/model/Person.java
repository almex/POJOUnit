package com.github.almex.pojounit.model;

import java.util.Comparator;
import java.util.Objects;

import static java.util.Comparator.comparing;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

/**
 * This example POJO class use method coming from Object.
 *
 * @author Almex
 * @since 1.0
 */
public class Person implements Cloneable {

    protected Long id;

    protected String firstName;

    protected String lastName;

    protected Address address;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj instanceof Person) {
            result = Objects.compare(this, ((Person) obj), (Person lho, Person rho) ->
                    comparing(Person::getFirstName, nullsLast(naturalOrder()))
                            .thenComparing(Person::getLastName, nullsLast(naturalOrder()))
                            .compare(lho, rho)
            ) == 0;
        }

        return result;
    }

    @Override
    public Person clone() {
        Person result = null;

        try {
            result = (Person) super.clone();

            result.setAddress(this.getAddress());
            result.setFirstName(this.getLastName());
            result.setLastName(this.getLastName());
        } catch (CloneNotSupportedException e) {
            // We swallow the exception
        }

        return result;
    }
}
