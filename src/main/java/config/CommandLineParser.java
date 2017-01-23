package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

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

    public CommandLineParser(String[] args) {
        this.args = args;
    }

    /**
     * This method creates a new configuration based on the arguments passed
     * into the constructor
     *
     * @throws Exception
     */
    @Override
    public IConfiguration create() throws Exception {
        IConfiguration config = Configuration.getInstance();
        boolean parsingJson = true;
        Map<String, String> commandParams = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if (s.equals("-c")) {
                parsingJson = true;
                continue;
            } else if (s.equals("--param")) {
                parsingJson = false;
                continue;
            }
            if (parsingJson) {
                JSONObject json = readJsonObject(args[i]);
                config.populateMap("", json.toMap());
            } else {
                String[] sp = s.split("=");
                if (sp.length > 2)
                    throw new RuntimeException("The param map should have two =s");
                commandParams.put(sp[0], sp[1]);
            }
        }
        for (String key : commandParams.keySet()){
            config.set(key, commandParams.get(key));
        }
        return config;
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
