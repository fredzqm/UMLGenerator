package config;

import com.martiansoftware.jsap.*;
import generator.IModifier;
import model.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fineral on 12/11/2016
 * Edited by fineral on 12/13/2016
 */
public class CommandLineParser implements ConfigurationFactory {

    private String[] args;
    private JSAP jsap;

    public CommandLineParser(String[] args) {
        this.args = args;

        jsap = new JSAP();

        UnflaggedOption opt1 = new UnflaggedOption("class")
                .setStringParser(JSAP.STRING_PARSER)
                .setDefault("java.lang.String")
                .setRequired(true)
                .setGreedy(true);
        opt1.setHelp("desc: space separated list of the name of the classes you want the UML for\n");
        try {
            jsap.registerParameter(opt1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlaggedOption opt2 = new FlaggedOption("path")
                .setStringParser(JSAP.STRING_PARSER)
                .setDefault("")
                .setRequired(false)
                .setShortFlag('e')
                .setLongFlag("executablepath");
        opt2.setHelp("desc: the name of the executable path for graphviz on your machine\n");
        try {
            jsap.registerParameter(opt2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlaggedOption opt3 = new FlaggedOption("outputDirectory")
                .setStringParser(JSAP.STRING_PARSER)
                .setDefault("")
                .setRequired(true)
                .setShortFlag('d')
                .setLongFlag("directory");

        opt3.setHelp("desc: the name of the directory which you want output to go to\n");
        try {
            jsap.registerParameter(opt3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlaggedOption opt4 = new FlaggedOption("outputfile")
                .setStringParser(JSAP.STRING_PARSER)
                .setDefault("output.png")
                .setRequired(true)
                .setShortFlag('o')
                .setLongFlag("outputfile");
        opt4.setHelp("desc: the name of the output file\n");
        try {
            jsap.registerParameter(opt4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlaggedOption opt5 = new FlaggedOption("extension")
                .setStringParser(JSAP.STRING_PARSER)
                .setDefault("output.png")
                .setRequired(true)
                .setShortFlag('x')
                .setLongFlag("extension");
        opt5.setHelp("desc: the name extension of the output file without the dot\n");
        try {
            jsap.registerParameter(opt5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlaggedOption opt6 = new FlaggedOption("filters")
                .setShortFlag('f')
                .setLongFlag("filters")
                .setRequired(false)
                .setStringParser(JSAP.STRING_PARSER)
                .setDefault("private");
        opt6.setHelp("desc: use this flag if you want to filter out\n"
                + "if public, you filter out protected and private\n"
                + "if protected, you filter out private\n"
                + "if blank or private, you filter out nothing\n");
        try {
            jsap.registerParameter(opt6);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlaggedOption opt7 = new FlaggedOption("nodeseparationvalue")
                .setStringParser(JSAP.DOUBLE_PARSER)
                .setRequired(true)
                .setShortFlag('n')
                .setLongFlag("nodesep")
                .setDefault("1");
        opt7.setHelp("desc: the node seperation value\n");
        try {
            jsap.registerParameter(opt7);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Switch opt8 = new Switch("recursive")
                .setShortFlag('r')
                .setLongFlag("recursive");
        opt8.setHelp("desc: use this flag if you want to recursively create the UML\n");
        try {
            jsap.registerParameter(opt8);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Configuration create() {

        JSAPResult config = jsap.parse(this.args);


        if (!config.success()) {

            System.err.println();

            // print out specific error messages describing the problems
            // with the command line, THEN print usage, THEN print full
            // help.  This is called "beating the user with a clue stick."
            for (@SuppressWarnings("rawtypes")
                 Iterator errs = config.getErrorMessageIterator();
                 errs.hasNext(); ) {
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
        conf.setClasses(Arrays.asList(config.getString("class").split(",")));
        conf.setExecutablePath(config.getString("path"));
        conf.setOutputFormat(config.getString("extension"));
        conf.setOutputDirectory(config.getString("outputDirectory"));
        conf.setFileName(config.getString("outputfile"));
        conf.setNodesep(config.getDouble("nodeseparationvalue"));

        List<IModifier> filters = new ArrayList<IModifier>();
        switch (config.getString("filters")) {
            case "public":
                filters.add(Modifier.PRIVATE);
                filters.add(Modifier.PROTECTED);
                break;
            case "protected":
                filters.add(Modifier.PRIVATE);
                break;
            case "private":
                break;
            default:
                System.err.println("modifier not found");
        }
        conf.setFilters(filters);
        conf.setRecursive(config.getBoolean("recursive"));

        return conf;
    }

    public void addOption(UnflaggedOption opt) {
        try {
            jsap.registerParameter(opt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOption(FlaggedOption opt) {
        try {
            jsap.registerParameter(opt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
