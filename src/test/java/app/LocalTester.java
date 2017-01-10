package app;

import java.util.Arrays;

import config.Configuration;
import dummy.Dummy;
import dummy.RelDummyClass;
import dummy.RelDummyManyClass;
import dummy.RelOtherDummyClass;

/**
 * A Test Class that will generate files for local inspection.
 * <p>
 * Created by lamd on 12/15/2016.
 */
public class LocalTester {
    public static void main(String[] args) {
        // Set up the config.
        Configuration config = Configuration.getInstance();
        config.setClasses(Arrays.asList(Dummy.class.getName(), RelDummyManyClass.class.getName(), RelOtherDummyClass.class.getName(), RelDummyClass.class.getName()));
        config.setRecursive(false);
        config.setNodesep(1.0);
        config.setRankDir("BT");
        config.setOutputDirectory("./output");
        config.setFileName("localTest");
        config.setExecutablePath("dot");
        config.setOutputFormat("svg");

        Runnable engine = UMLEngine.getInstance(config);
        engine.run();
    }

}
