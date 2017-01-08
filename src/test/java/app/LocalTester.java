package app;
import config.Configuration;
import dummy.RelDummyManyClass;
import dummy.RelOtherDummyClass;
import runner.IRunner;

import java.util.Arrays;

import app.AbstractUMLEngine;
import app.UMLEngine;

/**
 * A Test Class that will generate files for local inspection.
 * <p>
 * Created by lamd on 12/15/2016.
 */
public class LocalTester {
	public static void main(String[] args) {
		// Set up the config.
		Configuration config = Configuration.getInstance();
		config.setClasses(Arrays.asList(RelDummyManyClass.class.getName(), RelOtherDummyClass.class.getName()));
		config.setRecursive(true);
		config.setNodesep(1.0);
		config.setRecursive(true);
		config.setRankDir("BT");
		config.setOutputDirectory("./output");
		config.setFileName("localTest");
		config.setExecutablePath("dot");
		config.setOutputFormat("png");

		Runnable engine = UMLEngine.getInstance(config);
		engine.run();
	}

}
