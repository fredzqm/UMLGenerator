package example;

import configuration.Configuration;
import parser.CommandLineParser;

public class TestingCLP {
	
	public static void main(String[] args) throws Exception {
		CommandLineParser c = new CommandLineParser(args);
		Configuration conf = c.create();
		System.out.println(conf.getBuildPath() + " " 
				+ conf.getExecutionPath() + " " 
				+ conf.getOutputExtension() + " " 
				+ conf.getOutputName() + " " 
				+ conf.getClasses().iterator().next());
		
	}
}
