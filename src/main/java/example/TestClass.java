package example;

import java.util.Iterator;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;

public class TestClass {
	public static void main(String[] args) throws Exception {
		JSAP jsap = new JSAP();
		
		FlaggedOption opt1 = new FlaggedOption("count")
				.setStringParser(JSAP.STRING_PARSER)
				.setDefault("Configs/default.txt")
				.setShortFlag('p')
				.setLongFlag("path")
				.setRequired(true);
		
		opt1.setHelp("desc: path to configuration file\n");
		jsap.registerParameter(opt1);
		
		JSAPResult config = jsap.parse(args);
		
		if (!config.success()) {
            
            System.err.println();

            // print out specific error messages describing the problems
            // with the command line, THEN print usage, THEN print full
            // help.  This is called "beating the user with a clue stick."
            for (Iterator errs = config.getErrorMessageIterator();
                    errs.hasNext();) {
                System.err.println("Error: " + errs.next());
            }
            
            System.err.println();
            System.err.println("Usage: java "
                                + TestClass.class.getName());
            System.err.println("                "
                                + jsap.getUsage());
            System.err.println();
            System.err.println(jsap.getHelp());
            System.exit(1);
        }
	}
}
