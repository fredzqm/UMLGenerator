import config.CommandLineParser;
import config.Configuration;

public class Main {

    public static void main(String[] args) throws Exception {
        CommandLineParser c = new CommandLineParser(args);
        Configuration conf = c.create();
        
        UMLEngine engine = new UMLEngine(conf);
		engine.run();
    }

}
