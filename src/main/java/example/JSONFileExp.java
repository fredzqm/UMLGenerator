package example;

import config.CommandLineFileInput;
import config.ConfigFileParser;

public class JSONFileExp {

	public static void main(String[] args) throws Exception {
		CommandLineFileInput com = new CommandLineFileInput(args);
		ConfigFileParser parser = new ConfigFileParser(com.getJson());

		System.out.println(parser.create().toString());
	}

}
