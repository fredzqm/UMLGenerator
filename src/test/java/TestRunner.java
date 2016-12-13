package test.java;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 * Runs all Tests.
 *
 * Created by lamd on 12/12/2016.
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AllTests.class);

        System.out.println(result.wasSuccessful());

        if (result.wasSuccessful()) {
            System.exit(0);
        } else {
            System.exit(1);
        }
    }
}
