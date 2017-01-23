package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONObject;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.Switch;
import com.martiansoftware.jsap.UnflaggedOption;

/**
 * Created by fineral on 12/11/2016 Edited by fineral on 12/13/2016
 * <p>
 * Usage: java config.CommandLineParser class1 class2 ... classN
 * [(-e|--executable) <path>] (-d|--directory) <outputDirectory>
 * (-o|--outputfile) <outputfile> [(-x|--extension) <extension>] [(-f|--filters)
 * <filters>] [(-n|--nodesep) <nodeseparationvalue>] [-r|--recursive]
 * [-k|--direction]
 * <p>
 * class1 class2 ... classN desc: space separated list of the name of the
 * classes you want the UML for (default: java.lang.String)
 * <p>
 * [(-e|--executable) <path>] desc: the name of the executable path for graphviz
 * on your machine (default: dot)
 * <p>
 * (-d|--directory) <outputDirectory> desc: the name of the directory which you
 * want output to go to (default: output)
 * <p>
 * (-o|--outputfile) <outputfile> desc: the name of the output file (default:
 * output)
 * <p>
 * [(-x|--extension) <extension>] desc: the name extension of the output file
 * without the dot (default: svg)
 * <p>
 * [(-f|--filters) <filters>] desc: use this flag if you want to filter out if
 * public, you filter out protected and private if protected, you filter out
 * private if blank or private, you filter out nothing (default: private)
 * <p>
 * [(-n|--nodesep) <nodeseparationvalue>] desc: the node seperation value which
 * is greater than 0 (default: 1)
 * <p>
 * [-r|--recursive] desc: use this flag if you want to recursively create the
 * UML
 * <p>
 * [-k|--direction] desc: use this flag if you want the UML to be outputed Top
 * down
 */
public class CommandLineParser implements ConfigurationFactory {

    private String[] args;
    private JSAP jsap;

    public CommandLineParser(String[] args) {
        this.args = args;

        jsap = new JSAP();

        UnflaggedOption opt1 = new UnflaggedOption("class").setStringParser(JSAP.STRING_PARSER)
                .setDefault("java.lang.String").setGreedy(true);
        opt1.setHelp("desc: space separated list of the name of the classes you want the UML for\n");
        addOption(opt1);

        FlaggedOption opt2 = new FlaggedOption("path").setStringParser(JSAP.STRING_PARSER).setDefault("dot")
                .setRequired(false).setShortFlag('e').setLongFlag("executable");
        opt2.setHelp("desc: the name of the executable path for graphviz on your machine\n");
        addOption(opt2);

        FlaggedOption opt3 = new FlaggedOption("outputDirectory").setStringParser(JSAP.STRING_PARSER)
                .setDefault("output").setShortFlag('d').setLongFlag("directory");
        opt3.setHelp("desc: the name of the directory which you want output to go to\n");
        addOption(opt3);

        FlaggedOption opt4 = new FlaggedOption("outputfile").setStringParser(JSAP.STRING_PARSER).setDefault("output")
                .setShortFlag('o').setLongFlag("outputfile");
        opt4.setHelp("desc: the name of the output file\n");
        addOption(opt4);

        FlaggedOption opt5 = new FlaggedOption("extension").setStringParser(JSAP.STRING_PARSER).setDefault("svg")
                .setShortFlag('x').setLongFlag("extension");
        opt5.setHelp("desc: the name extension of the output file without the dot\n");
        addOption(opt5);

        FlaggedOption opt6 = new FlaggedOption("filters").setShortFlag('f').setLongFlag("filters").setRequired(false)
                .setStringParser(JSAP.STRING_PARSER).setDefault("private");
        opt6.setHelp(
                "desc: use this flag if you want to filter out\n" + "if public, you filter out protected and private\n"
                        + "if protected, you filter out private\n" + "if blank or private, you filter out nothing\n");
        addOption(opt6);

        FlaggedOption opt7 = new FlaggedOption("nodeseparationvalue").setStringParser(JSAP.DOUBLE_PARSER)
                .setShortFlag('n').setLongFlag("nodesep").setDefault("1");
        opt7.setHelp("desc: the node seperation value which is greater than 0\n");
        addOption(opt7);

        Switch opt8 = new Switch("recursive").setShortFlag('r').setLongFlag("recursive");
        opt8.setHelp("desc: use this flag if you want to recursively create the UML\n");
        addOption(opt8);

        Switch opt9 = new Switch("rankdir").setShortFlag('k').setLongFlag("direction");
        opt9.setHelp("desc: use this flag if you want the UML to be outputed Top down");
        addOption(opt9);

        FlaggedOption opt10 = new FlaggedOption("JSONfile").setLongFlag("config").setShortFlag('j').setDefault("none")
                .setStringParser(JSAP.STRING_PARSER);
        opt10.setHelp("include this to specify a configuration file to use instead of command" + "line arguments.");
        addOption(opt10);
    }

    /**
     * This method creates a new configuration based on the arguments passed
     * into the constructor
     *
     * @throws Exception
     */
    @Override
    public IConfiguration create() throws Exception {

        JSAPResult config = jsap.parse(this.args);

        if (!config.success()) {

            System.err.println();

            // print out specific error messages describing the problems
            // with the command line, THEN print usage, THEN print full
            // help. This is called "beating the user with a clue stick."
            for (@SuppressWarnings("rawtypes")
            Iterator errs = config.getErrorMessageIterator(); errs.hasNext();) {
                System.err.println("Error: " + errs.next());
            }

            System.err.println();
            System.err.println("Usage: java " + CommandLineParser.class.getName());
            System.err.println("                " + jsap.getUsage());
            System.err.println();
            System.err.println(jsap.getHelp());
            System.exit(1);
        }

        IConfiguration conf = Configuration.getInstance();

        String configJ = config.getString("JSONfile");
        if (!configJ.equals("none")) {
            JSONObject json = readJsonObject(configJ);
            conf = Configuration.getInstance();
            conf.populateMap("", json.toMap());
        }

        conf.add(ModelConfiguration.CLASSES_KEY, config.getStringArray("class"));
        conf.set(RunnerConfiguration.EXECUTABLE_PATH, config.getString("path"));
        conf.set(RunnerConfiguration.OUTPUT_FORMAT, config.getString("extension"));
        conf.set(RunnerConfiguration.OUTPUT_DIRECTORY, config.getString("outputDirectory"));
        conf.set(RunnerConfiguration.FILE_NAME, config.getString("outputfile"));
        conf.set(GeneratorConfiguration.NODE_SEP, Double.toString(config.getDouble("nodeseparationvalue")));
        conf.set(ClassParserConfiguration.MODIFIER_FILTER, config.getString("filters"));

        conf.set(ModelConfiguration.IS_RECURSIVE_KEY, Boolean.toString(config.getBoolean("recursive")));

        if (config.getBoolean("rankdir")) {
            conf.set(GeneratorConfiguration.RANK_DIR, "TB");
        } else {
            conf.set(GeneratorConfiguration.RANK_DIR, "BT");
        }

        return conf;
    }

    /**
     * Adds the specified option to the commanLineParser
     *
     * @param opt-
     *            option to add to the commandLineParser
     */
    public void addOption(Parameter opt) {
        try {
            jsap.registerParameter(opt);
        } catch (JSAPException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to register parameter: " + opt.toString(), e);
        }
    }

    static JSONObject readJsonObject(String arg) {
        Scanner scanner = null;
        try {
            if (arg.length() <= 0) {
                return new JSONObject();
            }
            scanner = new Scanner(new File(arg));

            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }

            return new JSONObject(json.toString());
        } catch (FileNotFoundException e) {
            return new JSONObject();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
