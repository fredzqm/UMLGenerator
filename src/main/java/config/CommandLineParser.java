package main.java.config;

import java.util.Arrays;
import java.util.Iterator;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.UnflaggedOption;

public class CommandLineParser implements ConfigurationFactory {
	
	private String[] args;
	
	public CommandLineParser(String[] args){
		this.args = args;
	}

	@Override
	public Configuration create() throws Exception {
		
		JSAP jsap = new JSAP();
		
		UnflaggedOption opt1 = new UnflaggedOption("class")
				.setStringParser(JSAP.STRING_PARSER)
				.setDefault("java.lang.String")
				.setRequired(true)
				.setGreedy(true);
		
		opt1.setHelp("desc: the name of the classes you want the UML for\n");
		jsap.registerParameter(opt1);
		
		FlaggedOption opt2 = new FlaggedOption("path")
				.setStringParser(JSAP.STRING_PARSER)
				.setDefault("")
				.setRequired(false)
				.setShortFlag('p')
				.setLongFlag("path");
		
		opt2.setHelp("desc: the name of the build path for the classes you provide\n");
		jsap.registerParameter(opt2);
		
		FlaggedOption opt3 = new FlaggedOption("executionPath")
				.setStringParser(JSAP.STRING_PARSER)
				.setDefault("")
				.setRequired(true)
				.setShortFlag('e')
				.setLongFlag("executionPath");
		
		opt3.setHelp("desc: the name of the execution path\n");
		jsap.registerParameter(opt3);
		
		FlaggedOption opt4 = new FlaggedOption("outputfile")
				.setStringParser(JSAP.STRING_PARSER)
				.setDefault("output.png")
				.setRequired(true)
				.setShortFlag('o')
				.setLongFlag("file");
		
		opt4.setHelp("desc: the name of the output file in the for of <name>.<extention>\n");
		jsap.registerParameter(opt4);
		
		JSAPResult config = jsap.parse(this.args);
		
		
		if (!config.success()) {
            
            System.err.println();

            // print out specific error messages describing the problems
            // with the command line, THEN print usage, THEN print full
            // help.  This is called "beating the user with a clue stick."
            for (@SuppressWarnings("rawtypes")
			Iterator errs = config.getErrorMessageIterator();
                    errs.hasNext();) {
                System.err.println("Error: " + errs.next());
            }
            
            System.err.println();
            System.err.println("Usage: java "
                                + CommandLineParser.class.getName());
            System.err.println("                "
                                + jsap.getUsage());
            System.err.println();
            System.err.println(jsap.getHelp());
            System.exit(1);
        }
		
		Configuration conf = Configuration.getInstance();
		conf.setClasses(Arrays.asList(config.getStringArray("class")));
		conf.setBuildPath(config.getString("path"));
		conf.setExecutionPath(config.getString("executionPath"));
		
		String[] output = config.getString("outputfile").split("\\.");
		if(output.length != 2)
			throw new RuntimeException("outputfile was not in the proper format of <name>.<extension>");
		conf.setOutputName(output[0]);
		conf.setOutputExtension(output[1]);
		
		return conf;
	}
}
