package config;

import com.martiansoftware.jsap.*;
import utility.Modifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
                .setDefault("java.lang.String").setRequired(true).setGreedy(true);
        opt1.setHelp("desc: space separated list of the name of the classes you want the UML for\n");
        addOption(opt1);

        FlaggedOption opt2 = new FlaggedOption("path").setStringParser(JSAP.STRING_PARSER).setDefault("dot")
                .setRequired(false).setShortFlag('e').setLongFlag("executable");
        opt2.setHelp("desc: the name of the executable path for graphviz on your machine\n");
        addOption(opt2);

        FlaggedOption opt3 = new FlaggedOption("outputDirectory").setStringParser(JSAP.STRING_PARSER)
                .setDefault("output").setRequired(true).setShortFlag('d').setLongFlag("directory");
        opt3.setHelp("desc: the name of the directory which you want output to go to\n");
        addOption(opt3);

        FlaggedOption opt4 = new FlaggedOption("outputfile").setStringParser(JSAP.STRING_PARSER).setDefault("output")
                .setRequired(true).setShortFlag('o').setLongFlag("outputfile");
        opt4.setHelp("desc: the name of the output file\n");
        addOption(opt4);

        FlaggedOption opt5 = new FlaggedOption("extension").setStringParser(JSAP.STRING_PARSER).setDefault("svg")
                .setRequired(false).setShortFlag('x').setLongFlag("extension");
        opt5.setHelp("desc: the name extension of the output file without the dot\n");
        addOption(opt5);

        FlaggedOption opt6 = new FlaggedOption("filters").setShortFlag('f').setLongFlag("filters").setRequired(false)
                .setStringParser(JSAP.STRING_PARSER).setDefault("private");
        opt6.setHelp(
                "desc: use this flag if you want to filter out\n" + "if public, you filter out protected and private\n"
                        + "if protected, you filter out private\n" + "if blank or private, you filter out nothing\n");
        addOption(opt6);

        FlaggedOption opt7 = new FlaggedOption("nodeseparationvalue").setStringParser(JSAP.DOUBLE_PARSER)
                .setRequired(false).setShortFlag('n').setLongFlag("nodesep").setDefault("1");
        opt7.setHelp("desc: the node seperation value which is greater than 0\n");
        addOption(opt7);

        Switch opt8 = new Switch("recursive").setShortFlag('r').setLongFlag("recursive");
        opt8.setHelp("desc: use this flag if you want to recursively create the UML\n");
        addOption(opt8);

        Switch opt9 = new Switch("rankdir").setShortFlag('k').setLongFlag("direction");
        opt9.setHelp("desc: use this flag if you want the UML to be outputed Top down");
        addOption(opt9);
    }

    /**
     * This method creates a new configuration based on the arguments passed
     * into the constructor
     */
    @Override
    public Configuration create() {

        JSAPResult config = jsap.parse(this.args);

        if (!config.success()) {

            System.err.println();

            // print out specific error messages describing the problems
            // with the command line, THEN print usage, THEN print full
            // help. This is called "beating the user with a clue stick."
            for (@SuppressWarnings("rawtypes")
                 Iterator errs = config.getErrorMessageIterator(); errs.hasNext(); ) {
                System.err.println("Error: " + errs.next());
            }

            System.err.println();
            System.err.println("Usage: java " + CommandLineParser.class.getName());
            System.err.println("                " + jsap.getUsage());
            System.err.println();
            System.err.println(jsap.getHelp());
            System.exit(1);
        }

        Configuration conf = Configuration.getInstance();
        conf.setClasses(Arrays.asList(config.getStringArray("class")));
        conf.setExecutablePath(config.getString("path"));
        conf.setOutputFormat(config.getString("extension"));
        conf.setOutputDirectory(config.getString("outputDirectory"));
        conf.setFileName(config.getString("outputfile"));
        conf.setNodesep(config.getDouble("nodeseparationvalue"));

        List<Modifier> filters = new ArrayList<Modifier>();
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
        conf.setFilters((d) -> !filters.contains(d));

        conf.setRecursive(config.getBoolean("recursive"));

        if (config.getBoolean("rankdir")) {
            conf.setRankDir("TB");
        } else {
            conf.setRankDir("BT");
        }

        return conf;
    }

    /**
     * Adds the specified option to the commanLineParser
     *
     * @param opt- option to add to the commandLineParser
     */
    public void addOption(Parameter opt) {
        try {
            jsap.registerParameter(opt);
        } catch (JSAPException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
