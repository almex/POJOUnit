package com.github.almex.pojounit.model;

/**
 * This example POJO class use method coming from Object.
 *
 * @author Almex
 * @since 1.0
 */
public class Person {

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
}
