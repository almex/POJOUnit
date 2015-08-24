# POJOUnit
[![Build Status](https://travis-ci.org/almex/POJOUnit.svg)](https://travis-ci.org/almex/POJOUnit) [![Coverage Status](https://coveralls.io/repos/almex/POJOUnit/badge.svg?branch=master&service=github)](https://coveralls.io/github/almex/POJOUnit?branch=master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.almex/pojo-unit/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.almex/pojo-unit)

This is a simple Java Framework to generify unit testing of POJO (Plain Old Java Objects) and JavaBean.
To do so we implemented a generic class by using JUnit `Theories` (read its [Javadoc](http://junit.org/apidocs/org/junit/experimental/theories/Theories.html) and the [documentation](https://github.com/junit-team/junit/wiki/Theories)) making it easy to test combination of different `DataPoint`. 

Combined with Harmcrest framework, we can describe easily some assumption on the behaviour of some methods.
By reading [Javadoc](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html) of the `Object` class for methods `hashCode()`, `equals(Object)` and `toString()` we were able to retranscript that documentation in Java code.

Here is a snipped of the `Theory` used to test the  `hashCode()` method :

```java
    /**
     * Whenever it is invoked on the same object more than once during an
     * execution of a Java application, the hashCode method must consistently
     * return the same integer, provided no information used in equals
     * comparisons on the object is modified. This integer need not remain
     * consistent from one execution of an application to another execution of
     * the same application.
     *
     * @param x object to test
     */
    @Theory
    public void theoryHashCodeIsSelfConsistent(final Object x) {
        assumeThat(x, is(notNullValue()));

        final int theSame = x.hashCode();

        for (int i = 0; i < NB_CONSISTENT_LOOP; i++) {
            assertThat(x.hashCode(), is(equalTo(theSame)));
        }
    }
```

Reading the Javadoc of that method (copied from the `Object` Javadoc) and comparing with the Java code, those two should sound identical.

# Usage

Assuming that you have a POJO named `Person` and that you want to test methods `hashCode()`, `equals(Object)` and `toString()` 

```java
public class Person {

    protected Long id;

    protected String firstName;

    protected String lastName;

    protected Address address;

    public Long getId() {
        return id;
    }

    // ... Removed for readability
}
```

Then the test would be :

```java
public class PersonTest extends AbstractObjectTest {

    @DataPoint
    public static Person DATA_POINT1;

    // ... Removed for readability

    @Before
    @Override
    public void setUp() throws Exception {
        DATA_POINT1 = new Person();
        setIdFor(DATA_POINT1, 1L);
        DATA_POINT1.setFirstName("foo");
        DATA_POINT1.setLastName("bar");

        DATA_POINT3 = new Person();
        setIdFor(DATA_POINT3, 2L);
        DATA_POINT3.setFirstName("foo");
        DATA_POINT3.setLastName("bar");
    }
}
```

Note here that we have created 3 `DataPoint` :
* The first one as a common instance of that type
* The second one is `null` because we want to test against null references of type `Person` 
* The third one has the same content as the first one but with another `id`

As an extra, `AbstractObjectTest` contains also two `DataPoint` :
* An instance of `Object` making some possible comparison with at least one different type that the one you are testing.
* A null reference to `Object` to never forget to test `null` value.
 
So, running this test will try any combination of those 5 `DataPoint` and run each `Theory` against them.

# 3rd Party Framework
* JUnit : as the base framework for unit-tests
* Hamcrest : for its DSL
