package de.micromata.labs.todo.frontend;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;

import de.micromata.labs.todo.frontend.TodoApplication;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage {
    private WicketTester tester;

    @Before
    public void setUp() {
        this.tester = new WicketTester(new TodoApplication());
    }

}
