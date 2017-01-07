package app;
import config.CommandLineParser;
import config.Configuration;
import display.Display;

public class Main {

    public static void main(String[] args) throws Exception {
        CommandLineParser c = new CommandLineParser(args);
        Configuration conf = c.create();

        Runnable engine = UMLEngine.getInstance(conf);
        engine.run();
        
        Display.showWindow(conf);
    }

}
